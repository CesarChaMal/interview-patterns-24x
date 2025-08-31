class TreeNode {
    constructor(val, left, right) {
        this.val = val ?? 0;
        this.left = left ?? null;
        this.right = right ?? null;
    }
}
// 1. House Robber III
function rob(root) {
    const dfs = (node) => {
        if (!node)
            return [0, 0];
        const left = dfs(node.left);
        const right = dfs(node.right);
        const robNode = node.val + left[1] + right[1];
        const notRobNode = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return [robNode, notRobNode];
    };
    const result = dfs(root);
    return Math.max(result[0], result[1]);
}
// 2. Longest Increasing Path in a Matrix
function longestIncreasingPath(matrix) {
    if (matrix.length === 0)
        return 0;
    const m = matrix.length;
    const n = matrix[0].length;
    const memo = Array.from({ length: m }, () => new Array(n).fill(0));
    let maxPath = 0;
    const dfs = (i, j) => {
        if (memo[i][j] !== 0)
            return memo[i][j];
        const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
        let maxLen = 1;
        for (const [dx, dy] of dirs) {
            const x = i + dx;
            const y = j + dy;
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] > matrix[i][j]) {
                maxLen = Math.max(maxLen, 1 + dfs(x, y));
            }
        }
        memo[i][j] = maxLen;
        return maxLen;
    };
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            maxPath = Math.max(maxPath, dfs(i, j));
        }
    }
    return maxPath;
}
// 3. Longest String Chain
function longestStrChain(words) {
    const sortedWords = words.sort((a, b) => a.length - b.length);
    const memo = new Map();
    const wordSet = new Set(words);
    const dfs = (word) => {
        if (memo.has(word))
            return memo.get(word);
        let maxLen = 1;
        for (let i = 0; i < word.length; i++) {
            const next = word.substring(0, i) + word.substring(i + 1);
            if (wordSet.has(next)) {
                maxLen = Math.max(maxLen, 1 + dfs(next));
            }
        }
        memo.set(word, maxLen);
        return maxLen;
    };
    return Math.max(...sortedWords.map(dfs));
}
// 4. Stone Game II
function stoneGameII(piles) {
    const n = piles.length;
    const memo = Array.from({ length: n }, () => new Array(n + 1).fill(0));
    const suffixSum = new Array(n);
    suffixSum[n - 1] = piles[n - 1];
    for (let i = n - 2; i >= 0; i--)
        suffixSum[i] = suffixSum[i + 1] + piles[i];
    const dfs = (i, M) => {
        if (i >= n)
            return 0;
        if (memo[i][M] !== 0)
            return memo[i][M];
        let maxStones = 0;
        for (let X = 1; X <= Math.min(2 * M, n - i); X++) {
            const current = suffixSum[i] - (i + X < n ? suffixSum[i + X] : 0);
            const remaining = suffixSum[i] - current;
            const opponent = dfs(i + X, Math.max(M, X));
            maxStones = Math.max(maxStones, current + remaining - opponent);
        }
        memo[i][M] = maxStones;
        return maxStones;
    };
    return dfs(0, 1);
}
// 5. Cherry Pickup II
function cherryPickup(grid) {
    const m = grid.length;
    const n = grid[0].length;
    const memo = new Map();
    const dfs = (row, col1, col2) => {
        if (row >= m || col1 < 0 || col1 >= n || col2 < 0 || col2 >= n)
            return 0;
        const key = `${row},${col1},${col2}`;
        if (memo.has(key))
            return memo.get(key);
        let cherries = grid[row][col1];
        if (col1 !== col2)
            cherries += grid[row][col2];
        let maxNext = 0;
        for (let d1 = -1; d1 <= 1; d1++) {
            for (let d2 = -1; d2 <= 1; d2++) {
                maxNext = Math.max(maxNext, dfs(row + 1, col1 + d1, col2 + d2));
            }
        }
        const result = cherries + maxNext;
        memo.set(key, result);
        return result;
    };
    return dfs(0, 0, n - 1);
}
// 6. Stone Game IV
function winnerSquareGame(n) {
    const memo = new Array(n + 1).fill(null);
    const dfs = (num) => {
        if (num === 0)
            return false;
        if (memo[num] !== null)
            return memo[num];
        let canWin = false;
        for (let i = 1; i * i <= num && !canWin; i++) {
            if (!dfs(num - i * i))
                canWin = true;
        }
        memo[num] = canWin;
        return canWin;
    };
    return dfs(n);
}
// 7. Minimum Number of Days to Eat N Oranges
function minDaysToEatOranges(n) {
    const memo = new Map();
    const dfs = (num) => {
        if (num <= 1)
            return num;
        if (memo.has(num))
            return memo.get(num);
        const result = Math.min(num % 2 + 1 + dfs(Math.floor(num / 2)), num % 3 + 1 + dfs(Math.floor(num / 3)));
        memo.set(num, result);
        return result;
    };
    return dfs(n);
}
// 8. Maximum Non Negative Product in a Matrix
function maxProductPath(grid) {
    const m = grid.length;
    const n = grid[0].length;
    const memo = Array.from({ length: m }, () => new Array(n).fill(null));
    const dfs = (i, j) => {
        if (i >= m || j >= n)
            return [Number.MIN_SAFE_INTEGER, Number.MIN_SAFE_INTEGER];
        if (i === m - 1 && j === n - 1)
            return [grid[i][j], grid[i][j]];
        if (memo[i][j] !== null)
            return memo[i][j];
        const right = dfs(i, j + 1);
        const down = dfs(i + 1, j);
        const candidates = [
            right[0] * grid[i][j], right[1] * grid[i][j],
            down[0] * grid[i][j], down[1] * grid[i][j]
        ].filter(x => x !== Number.MIN_SAFE_INTEGER && x !== Number.MAX_SAFE_INTEGER);
        const result = candidates.length === 0 ? [Number.MIN_SAFE_INTEGER, Number.MIN_SAFE_INTEGER] :
            [Math.min(...candidates), Math.max(...candidates)];
        memo[i][j] = result;
        return result;
    };
    const result = dfs(0, 0)[1];
    return result < 0 ? -1 : result % 1000000007;
}
// 9. Number of Ways to Form a Target String Given a Dictionary
function numWaysToFormTarget(words, target) {
    const MOD = 1000000007;
    const m = target.length;
    const n = words[0].length;
    const count = Array.from({ length: n }, () => new Array(26).fill(0));
    for (const word of words) {
        for (let i = 0; i < word.length; i++) {
            count[i][word.charCodeAt(i) - 97]++;
        }
    }
    const memo = Array.from({ length: m }, () => new Array(n).fill(null));
    const dfs = (i, j) => {
        if (i === m)
            return 1;
        if (j === n)
            return 0;
        if (memo[i][j] !== null)
            return memo[i][j];
        let result = dfs(i, j + 1);
        const c = target.charCodeAt(i) - 97;
        result = (result + count[j][c] * dfs(i + 1, j + 1)) % MOD;
        memo[i][j] = result;
        return result;
    };
    return dfs(0, 0);
}
// 10. Maximum Score from Performing Multiplication Operations
function maximumScore(nums, multipliers) {
    const n = nums.length;
    const m = multipliers.length;
    const memo = Array.from({ length: m }, () => new Array(m).fill(null));
    const dfs = (i, left) => {
        if (i === m)
            return 0;
        if (memo[i][left] !== null)
            return memo[i][left];
        const right = n - 1 - (i - left);
        const pickLeft = multipliers[i] * nums[left] + dfs(i + 1, left + 1);
        const pickRight = multipliers[i] * nums[right] + dfs(i + 1, left);
        const result = Math.max(pickLeft, pickRight);
        memo[i][left] = result;
        return result;
    };
    return dfs(0, 0);
}
// Test cases
console.log("=== DFS + Memoization Examples ===");
// Test 1: House Robber III
const root = new TreeNode(3);
root.left = new TreeNode(2);
root.right = new TreeNode(3);
root.left.right = new TreeNode(3);
root.right.right = new TreeNode(1);
console.log("1. House Robber III:", rob(root));
// Test 2: Longest Increasing Path
const matrix = [[9, 9, 4], [6, 6, 8], [2, 1, 1]];
console.log("2. Longest Increasing Path:", longestIncreasingPath(matrix));
// Test 3: Longest String Chain
const words = ["a", "b", "ba", "bca", "bda", "bdca"];
console.log("3. Longest String Chain:", longestStrChain(words));
// Test 4: Stone Game II
const piles = [2, 7, 9, 4, 4];
console.log("4. Stone Game II:", stoneGameII(piles));
// Test 5: Cherry Pickup II
const grid = [[3, 1, 1], [2, 5, 1], [1, 5, 5], [2, 1, 1]];
console.log("5. Cherry Pickup II:", cherryPickup(grid));
// Test 6: Stone Game IV
console.log("6. Stone Game IV (n=1):", winnerSquareGame(1));
// Test 7: Min Days to Eat Oranges
console.log("7. Min Days to Eat 10 Oranges:", minDaysToEatOranges(10));
// Test 8: Max Product Path
const productGrid = [[-1, -2, -3], [-2, -3, -3], [-3, -3, -2]];
console.log("8. Max Product Path:", maxProductPath(productGrid));
// Test 9: Number of Ways to Form Target
const targetWords = ["acca", "bbbb", "caca"];
const target = "aba";
console.log("9. Ways to Form Target:", numWaysToFormTarget(targetWords, target));
// Test 10: Maximum Score from Multiplication
const nums = [1, 2, 3];
const multipliers = [3, 2, 1];
console.log("10. Maximum Score:", maximumScore(nums, multipliers));
