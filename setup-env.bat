@echo off
setlocal EnableExtensions EnableDelayedExpansion

REM Setup Node.js v22 and Java (JDK) v21 on Windows
REM - Uses nvm-windows for Node
REM - Uses winget (or Chocolatey fallback) for Java Temurin 21
REM - Idempotent: reuses installed versions
REM
REM Usage:
REM   setup-env.bat                 ^> install Node 22 + Java 21, install JS deps, build TS
REM   set SKIP_DEPS=1 ^&^& setup-env.bat  ^> skip npm install/build

call :say Starting environment setup
call :ensure_nvm
if errorlevel 1 goto :eof
call :ensure_node22
call :ensure_java21
call :bootstrap_project
call :say Environment setup complete.
exit /b 0

:say
  echo.
  echo [setup] %*
  exit /b 0

:err
  echo.
  echo [setup][ERROR] %* 1>&2
  exit /b 1

:has_cmd
  where %~1 >nul 2>nul && exit /b 0 || exit /b 1

:ensure_nvm
  call :say Checking for nvm (Windows)...
  call :has_cmd nvm
  if not errorlevel 1 (
    for /f "tokens=*" %%V in ('nvm version 2^>nul') do set NVM_VER=%%V
    call :say Found nvm: !NVM_VER!
    exit /b 0
  )

  call :say nvm not found; attempting installation via winget...
  call :has_cmd winget
  if not errorlevel 1 (
    winget install -e --id CoreyButler.NVMforWindows -h --accept-package-agreements --accept-source-agreements
    REM Ensure current session PATH includes nvm
    if exist "C:\Program Files\nvm\nvm.exe" set "PATH=C:\Program Files\nvm;C:\Program Files\nodejs;%PATH%"
    call :has_cmd nvm && exit /b 0
    call :say winget reported success but nvm isn't on PATH yet; try new shell if needed.
  ) else (
    call :say winget not available. Trying Chocolatey...
  )

  call :has_cmd choco
  if not errorlevel 1 (
    choco install -y nvm
    if exist "C:\Program Files\nvm\nvm.exe" set "PATH=C:\Program Files\nvm;C:\Program Files\nodejs;%PATH%"
    call :has_cmd nvm && exit /b 0
  )

  call :err Failed to install nvm automatically. Install manually: https://github.com/coreybutler/nvm-windows
  exit /b 1

:ensure_node22
  call :say Ensuring Node.js 22 is installed via nvm...
  call :has_cmd nvm || ( call :err nvm is required but not found & exit /b 1 )

  REM Install Node 22 if missing
  for /f "tokens=*" %%L in ('nvm list 2^>nul ^| findstr /r /c:"v22\."') do set NODE22=%%L
  if not defined NODE22 (
    nvm install 22
  )

  nvm use 22
  for /f "tokens=*" %%V in ('node -v') do set NODE_VER=%%V
  call :say Node version: !NODE_VER!
  exit /b 0

:ensure_java21
  call :say Ensuring Java 21 (Temurin) is installed...

  REM Check if current java is already 21
  for /f "tokens=*" %%V in ('cmd /c "java -version 2^>^&1 ^| findstr /i \" version\""') do set CUR_JAVA_VER=%%V
  echo !CUR_JAVA_VER! | findstr /r /c:"\b21\b" >nul 2>nul
  if not errorlevel 1 (
    call :say Java already present: !CUR_JAVA_VER!
    goto :java_home_setup
  )

  call :has_cmd winget
  if not errorlevel 1 (
    call :say Installing Eclipse Temurin JDK 21 via winget...
    winget install -e --id EclipseAdoptium.Temurin.21.JDK -h --accept-package-agreements --accept-source-agreements
    goto :java_home_setup
  )

  call :has_cmd choco
  if not errorlevel 1 (
    call :say Installing Temurin JDK 21 via Chocolatey...
    choco install -y temurin --version=21.0.0 || choco install -y temurin21
    goto :java_home_setup
  )

  call :say Neither winget nor Chocolatey available. Please install JDK 21 manually from https://adoptium.net
  goto :java_home_setup

:java_home_setup
  REM Derive JAVA_HOME from the java.exe path when possible
  for /f "tokens=*" %%P in ('powershell -NoProfile -Command "(Get-Command java.exe ^| Select-Object -ExpandProperty Source) 2^>^&1"') do set JAVA_BIN=%%P
  if defined JAVA_BIN (
    REM Strip \bin\java.exe
    for %%I in ("!JAVA_BIN!") do set "JAVA_HOME=%%~dpI.."
    set "PATH=!JAVA_HOME!\bin;!PATH!"
    call :say JAVA_HOME set to: !JAVA_HOME!
    REM Persist for future shells
    setx JAVA_HOME "!JAVA_HOME!" >nul
  ) else (
    call :say Could not determine JAVA_HOME automatically; java should still be on PATH if installed.
  )

  call :say Java version:
  cmd /c "java -version"
  exit /b 0

:bootstrap_project
  if /i "!SKIP_DEPS!"=="1" (
    call :say Skipping npm install/build due to SKIP_DEPS=1
  ) else (
    if exist package.json (
      call :has_cmd npm && (
        call :say Installing Node dependencies (npm ci)...
        npm ci || npm install
        if exist tsconfig.json (
          call :say Building TypeScript (npm run build)...
          npm run -s build
        )
      )
    )
  )

  if exist pom.xml (
    call :has_cmd mvn && (
      call :say Compiling Java/Scala (Maven, skip tests)...
      mvn -q -DskipTests compile
    )
  )
  exit /b 0

