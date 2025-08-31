#!/usr/bin/env bash
set -euo pipefail

# Setup Node.js v22 and Java (JDK) v24 for this repo
# - Uses nvm for Node
# - Uses SDKMAN! for Java (Temurin/Zulu etc.) on all platforms where available (including MSYS/Git Bash)
# - Falls back to winget/Chocolatey for Java (Temurin 24) on MSYS/MINGW/Cygwin only when SDKMAN is unavailable
# - Idempotent: reuses installed versions
#
# Usage:
#   bash setup-env.sh               # install Node 22 + Java 24, install JS deps, build TS
#   SKIP_DEPS=1 bash setup-env.sh   # skip npm install/build
#   USE_SDKMAN=1 bash setup-env.sh  # force using SDKMAN on MSYS/Windows if installed
#   JAVA_VENDOR=fx-zulu bash setup-env.sh  # choose vendor (e.g., tem, fx-zulu); implies JAVA_ID=24.<vendor>
#   JAVA_ID=24.fx-zulu bash setup-env.sh  # choose exact SDKMAN java candidate id

say() { printf "\n[setup] %s\n" "$*"; }
err() { printf "\n[setup][ERROR] %s\n" "$*" 1>&2; }

OS="$(uname -s || echo unknown)"
IS_MAC=0; IS_LINUX=0; IS_MSYS=0
case "$OS" in
  Darwin) IS_MAC=1 ;;
  Linux)  IS_LINUX=1 ;;
  MINGW*|MSYS*|CYGWIN*) IS_MSYS=1 ;;
  *) say "Running on $OS; proceeding generically." ;;
esac

# ---------- Node 22 via nvm ----------
install_or_use_node() {
  REQUIRED_NODE_MAJOR=22

  # Load nvm if present
  if [ -z "${NVM_DIR:-}" ]; then
    export NVM_DIR="$HOME/.nvm"
  fi
  if [ -s "$NVM_DIR/nvm.sh" ]; then
    # shellcheck disable=SC1090
    . "$NVM_DIR/nvm.sh"
  fi

  if ! command -v nvm >/dev/null 2>&1; then
    say "nvm not found; installing nvm..."
    # Latest stable nvm installer
    curl -fsSL https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
    # shellcheck disable=SC1090
    . "$NVM_DIR/nvm.sh"
  fi

  if ! command -v nvm >/dev/null 2>&1; then
    err "nvm installation failed. Please install nvm and rerun. See: https://github.com/nvm-sh/nvm"
    exit 1
  fi

  say "Ensuring Node.js v$REQUIRED_NODE_MAJOR is installed via nvm..."
  if ! nvm ls "$REQUIRED_NODE_MAJOR" >/dev/null 2>&1; then
    nvm install "$REQUIRED_NODE_MAJOR"
  fi
  nvm use "$REQUIRED_NODE_MAJOR"
  nvm alias default "$REQUIRED_NODE_MAJOR" >/dev/null 2>&1 || true
  say "Node version: $(node -v)"
}

# Helper: run SDKMAN's 'sdk' safely under set -u
sdk_wrap() {
  set +u
  sdk "$@"
  local rc=$?
  set -u
  return $rc
}

# ---------- Java 24 via SDKMAN (preferred) or winget/choco (MSYS fallback) ----------
install_or_use_java() {
  # Determine if we should use SDKMAN (preferred when available or forced)
  if [ -z "${SDKMAN_DIR:-}" ]; then
    export SDKMAN_DIR="$HOME/.sdkman"
  fi
  PREFER_SDKMAN=0
  if [ "${USE_SDKMAN:-}" = "1" ]; then PREFER_SDKMAN=1; fi
  if [ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then PREFER_SDKMAN=1; fi

  if [ "$PREFER_SDKMAN" -eq 1 ]; then
    # Initialize SDKMAN safely even under 'set -u'
    if [ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then
      set +u; . "$SDKMAN_DIR/bin/sdkman-init.sh"; set -u
    fi

    if ! command -v sdk >/dev/null 2>&1; then
      say "SDKMAN not found; installing SDKMAN..."
      curl -fsSL "https://get.sdkman.io" | bash
      set +u; . "$SDKMAN_DIR/bin/sdkman-init.sh"; set -u
    fi
    if ! command -v sdk >/dev/null 2>&1; then err "SDKMAN installation failed."; exit 1; fi

    # Seed SDKMAN vars to avoid unbound-variable under 'set -u'
    if [ -z "${SDKMAN_OFFLINE_MODE+x}" ]; then export SDKMAN_OFFLINE_MODE=false; fi

    # If already on any Java 24, keep current vendor
    if sdk_wrap current java | grep -q " 24"; then
      say "Java 24 already current via SDKMAN; leaving vendor as-is."
    else
      TARGET_JAVA_ID="${JAVA_ID:-}"
      if [ -z "$TARGET_JAVA_ID" ] && [ -n "${JAVA_VENDOR:-}" ]; then TARGET_JAVA_ID="24.${JAVA_VENDOR}"; fi
      if [ -z "$TARGET_JAVA_ID" ]; then
        if sdk_wrap list java | grep -Eiq "24[^\n]*fx-zulu"; then TARGET_JAVA_ID="24.fx-zulu";
        elif sdk_wrap list java | grep -Eiq "24[^\n]*zulu"; then TARGET_JAVA_ID="24.zulu";
        else TARGET_JAVA_ID="24-tem"; fi
      fi
      say "Ensuring Java ($TARGET_JAVA_ID) is installed via SDKMAN..."
      sdk_wrap install java "$TARGET_JAVA_ID" >/dev/null 2>&1 || true
      sdk_wrap use java "$TARGET_JAVA_ID" || true
      sdk_wrap default java "$TARGET_JAVA_ID" || true
    fi

    if [ -z "${SDKMAN_CANDIDATES_DIR:-}" ]; then export SDKMAN_CANDIDATES_DIR="$SDKMAN_DIR/candidates"; fi
    if [ -d "$SDKMAN_CANDIDATES_DIR/java/current" ]; then export JAVA_HOME="$SDKMAN_CANDIDATES_DIR/java/current"; fi
    # Ensure SDKMAN java takes precedence on PATH (over any Windows Java 8)
    if [ -n "${JAVA_HOME:-}" ] && ! echo ":$PATH:" | grep -q ":$JAVA_HOME/bin:"; then
      export PATH="$JAVA_HOME/bin:$PATH"
    fi

    say "Java version:"; java -version || true
    if [ -n "${JAVA_HOME:-}" ]; then say "JAVA_HOME set to: $JAVA_HOME"; else say "JAVA_HOME not determined; Java should be on PATH."; fi
    return 0
  fi

  # MSYS/Git Bash fallback to Windows-native installers if SDKMAN not available
  if [ "$IS_MSYS" -eq 1 ]; then
    say "SDKMAN not detected. Using winget/choco to manage JDK 24 on Windows..."

    if command -v winget >/dev/null 2>&1; then
      say "Installing Eclipse Temurin JDK 24 via winget (if not present)..."
      winget install -e --id EclipseAdoptium.Temurin.24.JDK -h --accept-package-agreements --accept-source-agreements >/dev/null 2>&1 || true
    elif command -v choco >/dev/null 2>&1; then
      say "Installing Temurin JDK 24 via Chocolatey (if not present)..."
      choco install -y temurin --version=24.0.0 >/dev/null 2>&1 || choco install -y temurin --pre >/dev/null 2>&1 || true
    else
      say "winget/Chocolatey not found; please install JDK 24 manually from https://adoptium.net"
    fi

    # Derive JAVA_HOME from java.exe using PowerShell if available
    if command -v powershell.exe >/dev/null 2>&1; then
      JAVA_BIN_WIN=$(powershell.exe -NoProfile -Command "(Get-Command java.exe | Select-Object -ExpandProperty Source)" 2>/dev/null | tr -d '\r') || true
      if [ -n "$JAVA_BIN_WIN" ]; then
        JAVA_HOME_WIN=${JAVA_BIN_WIN%\\bin\\java.exe}
        if command -v cygpath >/dev/null 2>&1; then
          export JAVA_HOME="$(cygpath -u "$JAVA_HOME_WIN")"
        else
          export JAVA_HOME="$JAVA_HOME_WIN"
        fi
      fi
    fi

    say "Java version:"
    java -version || true
    if [ -n "${JAVA_HOME:-}" ]; then
      say "JAVA_HOME set to: $JAVA_HOME"
    else
      say "JAVA_HOME not determined; Java should still be available on PATH."
    fi
    return 0
  fi

  # Default: use SDKMAN on macOS/Linux
  if [ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then set +u; . "$SDKMAN_DIR/bin/sdkman-init.sh"; set -u; fi
  if ! command -v sdk >/dev/null 2>&1; then
    say "SDKMAN not found; installing SDKMAN..."; curl -fsSL "https://get.sdkman.io" | bash; set +u; . "$SDKMAN_DIR/bin/sdkman-init.sh"; set -u
  fi
  if ! command -v sdk >/dev/null 2>&1; then err "SDKMAN installation failed."; exit 1; fi
  if [ -z "${SDKMAN_OFFLINE_MODE+x}" ]; then export SDKMAN_OFFLINE_MODE=false; fi

  if sdk_wrap current java | grep -q " 24"; then
    say "Java 24 already current via SDKMAN; leaving vendor as-is."
  else
    TARGET_JAVA_ID="${JAVA_ID:-}"
    if [ -z "$TARGET_JAVA_ID" ] && [ -n "${JAVA_VENDOR:-}" ]; then TARGET_JAVA_ID="24.${JAVA_VENDOR}"; fi
    if [ -z "$TARGET_JAVA_ID" ]; then
      if sdk_wrap list java | grep -Eiq "24[^\n]*fx-zulu"; then TARGET_JAVA_ID="24.fx-zulu";
      elif sdk_wrap list java | grep -Eiq "24[^\n]*zulu"; then TARGET_JAVA_ID="24.zulu";
      else TARGET_JAVA_ID="24-tem"; fi
    fi
    say "Ensuring Java ($TARGET_JAVA_ID) is installed via SDKMAN..."
    sdk_wrap install java "$TARGET_JAVA_ID" >/dev/null 2>&1 || true
    sdk_wrap use java "$TARGET_JAVA_ID" || true
    sdk_wrap default java "$TARGET_JAVA_ID" || true
  fi

  if [ -z "${SDKMAN_CANDIDATES_DIR:-}" ]; then export SDKMAN_CANDIDATES_DIR="$SDKMAN_DIR/candidates"; fi
  if [ -d "$SDKMAN_CANDIDATES_DIR/java/current" ]; then export JAVA_HOME="$SDKMAN_CANDIDATES_DIR/java/current"; fi
  # Ensure SDKMAN java takes precedence on PATH (over any Windows Java 8)
  if [ -n "${JAVA_HOME:-}" ] && ! echo ":$PATH:" | grep -q ":$JAVA_HOME/bin:"; then
    export PATH="$JAVA_HOME/bin:$PATH"
  fi
  say "Java version:"; java -version || true
  if [ -n "${JAVA_HOME:-}" ]; then say "JAVA_HOME set to: $JAVA_HOME"; else say "JAVA_HOME not determined; Java should still be available on PATH."; fi
}

# ---------- Optional project bootstrap ----------
bootstrap_project() {
  if [ "${SKIP_DEPS:-}" = "1" ]; then
    say "Skipping npm install/build due to SKIP_DEPS=1"
    return 0
  fi

  if command -v npm >/dev/null 2>&1; then
    say "Installing Node dependencies (npm ci)..."
    npm ci || npm install

    if [ -f "tsconfig.json" ]; then
      say "Building TypeScript (npm run build)..."
      npm run -s build || true
    fi
  fi

  if command -v mvn >/dev/null 2>&1 && [ -f "pom.xml" ]; then
    say "Compiling Java/Scala (Maven, skip tests)..."
    mvn -q -DskipTests compile || true
  fi
}

# Run steps
say "Starting environment setup"
install_or_use_node
install_or_use_java
#bootstrap_project
say "Environment setup complete."

# If the script was executed (not sourced), remind how to persist changes
if [ "${BASH_SOURCE[0]}" = "$0" ]; then
  say "Tip: to persist environment in current shell, source it: . ./setup-env.sh"
fi
