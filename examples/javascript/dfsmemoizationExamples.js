// 1. Fibonacci Number (Top-down DP)
function fib(n) {
    const memo = new Map();
    
    function helper(num) {
        if (num <= 1) return num;
        if (memo.has(num)) return memo.get(num);
        
        const result = helper(num - 1) + helper(num - 2);
        memo.set(num, result);
        return result;
    }
    
    return helper(n);
}

// 2. Unique Paths (Top-down DP)
function uniquePaths(m, n) {
    const memo = new Map();
    
    function helper(i, j) {
        if (i === 0 || j === 0) return 1;
        
        const key = `${i},${j}`;
        if (memo.has(key)) return memo.get(key);
        
        const result = helper(i - 1, j) + helper(i, j - 1);
        memo.set(key, result);
        return result;
    }
    
    return helper(m - 1, n - 1);
}

// 3. Longest Common Subsequence
function longestCommonSubsequence(text1, text2) {
    const memo = new Map();
    
    function helper(i, j) {
        if (i === text1.length || j === text2.length) return 0;
        
        const key = `${i},${j}`;
        if (memo.has(key)) return memo.get(key);
        
        let result;
        if (text1[i] === text2[j]) {
            result = 1 + helper(i + 1, j + 1);
        } else {
            result = Math.max(helper(i + 1, j), helper(i, j + 1));
        }
        
        memo.set(key, result);
        return result;
    }
    
    return helper(0, 0);
}

// 4. Edit Distance
function minDistance(word1, word2) {
    const memo = new Map();
    
    function helper(i, j) {
        if (i === word1.length) return word2.length - j;
        if (j === word2.length) return word1.length - i;
        
        const key = `${i},${j}`;
        if (memo.has(key)) return memo.get(key);
        
        let result;
        if (word1[i] === word2[j]) {
            result = helper(i + 1, j + 1);
        } else {
            result = 1 + Math.min(
                helper(i + 1, j),     // delete
                helper(i, j + 1),     // insert
                helper(i + 1, j + 1)  // replace
            );
        }
        
        memo.set(key, result);
        return result;
    }
    
    return helper(0, 0);
}

// 5. Word Break
function wordBreak(s, wordDict) {
    const wordSet = new Set(wordDict);
    const memo = new Map();
    
    function helper(start) {
        if (start === s.length) return true;
        if (memo.has(start)) return memo.get(start);
        
        for (let end = start + 1; end <= s.length; end++) {
            const word = s.substring(start, end);
            if (wordSet.has(word) && helper(end)) {
                memo.set(start, true);
                return true;
            }
        }
        
        memo.set(start, false);
        return false;
    }
    
    return helper(0);
}

// 6. Palindrome Partitioning II
function minCut(s) {
    const n = s.length;
    const memo = new Map();
    
    // Precompute palindrome check
    const isPalindrome = Array.from({ length: n }, () => new Array(n).fill(false));
    for (let i = 0; i < n; i++) {
        for (let j = i; j < n; j++) {
            if (s[i] === s[j] && (j - i <= 2 || isPalindrome[i + 1][j - 1])) {
                isPalindrome[i][j] = true;
            }
        }
    }
    
    function helper(start) {
        if (start === n) return 0;
        if (memo.has(start)) return memo.get(start);
        
        let minCuts = Infinity;
        for (let end = start; end < n; end++) {
            if (isPalindrome[start][end]) {
                minCuts = Math.min(minCuts, 1 + helper(end + 1));
            }
        }
        
        memo.set(start, minCuts);
        return minCuts;
    }
    
    return helper(0) - 1;
}

// 7. Target Sum
function findTargetSumWays(nums, target) {
    const memo = new Map();
    
    function helper(i, sum) {
        if (i === nums.length) return sum === target ? 1 : 0;
        
        const key = `${i},${sum}`;
        if (memo.has(key)) return memo.get(key);
        
        const result = helper(i + 1, sum + nums[i]) + helper(i + 1, sum - nums[i]);
        memo.set(key, result);
        return result;
    }
    
    return helper(0, 0);
}

// 8. Longest Increasing Path in a Matrix
function longestIncreasingPath(matrix) {
    if (!matrix || matrix.length === 0) return 0;
    
    const m = matrix.length, n = matrix[0].length;
    const memo = new Map();
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    
    function dfs(i, j) {
        const key = `${i},${j}`;
        if (memo.has(key)) return memo.get(key);
        
        let maxPath = 1;
        for (const [di, dj] of directions) {
            const ni = i + di, nj = j + dj;
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && matrix[ni][nj] > matrix[i][j]) {
                maxPath = Math.max(maxPath, 1 + dfs(ni, nj));
            }
        }
        
        memo.set(key, maxPath);
        return maxPath;
    }
    
    let result = 0;
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            result = Math.max(result, dfs(i, j));
        }
    }
    
    return result;
}

// 9. Regular Expression Matching
function isMatch(s, p) {
    const memo = new Map();
    
    function helper(i, j) {
        if (j === p.length) return i === s.length;
        
        const key = `${i},${j}`;
        if (memo.has(key)) return memo.get(key);
        
        const firstMatch = i < s.length && (s[i] === p[j] || p[j] === '.');
        
        let result;
        if (j + 1 < p.length && p[j + 1] === '*') {
            result = helper(i, j + 2) || (firstMatch && helper(i + 1, j));
        } else {
            result = firstMatch && helper(i + 1, j + 1);
        }
        
        memo.set(key, result);
        return result;
    }
    
    return helper(0, 0);
}

// 10. Burst Balloons
function maxCoins(nums) {
    const n = nums.length;
    const arr = [1, ...nums, 1];
    const memo = new Map();
    
    function helper(left, right) {
        if (left + 1 === right) return 0;
        
        const key = `${left},${right}`;
        if (memo.has(key)) return memo.get(key);
        
        let maxCoins = 0;
        for (let i = left + 1; i < right; i++) {
            const coins = arr[left] * arr[i] * arr[right] + 
                         helper(left, i) + helper(i, right);
            maxCoins = Math.max(maxCoins, coins);
        }
        
        memo.set(key, maxCoins);
        return maxCoins;
    }
    
    return helper(0, n + 1);
}

// Test cases
console.log("=== DFS + Memoization Examples ===");

console.log("Fibonacci(10):", fib(10));
console.log("Unique Paths(3, 7):", uniquePaths(3, 7));
console.log("LCS('abcde', 'ace'):", longestCommonSubsequence('abcde', 'ace'));
console.log("Edit Distance('horse', 'ros'):", minDistance('horse', 'ros'));
console.log("Word Break('leetcode', ['leet','code']):", wordBreak('leetcode', ['leet','code']));
console.log("Min Cut('aab'):", minCut('aab'));
console.log("Target Sum([1,1,1,1,1], 3):", findTargetSumWays([1,1,1,1,1], 3));

const matrix = [[9,9,4],[6,6,8],[2,1,1]];
console.log("Longest Increasing Path:", longestIncreasingPath(matrix));

console.log("Regex Match('aa', 'a*'):", isMatch('aa', 'a*'));
console.log("Max Coins([3,1,5,8]):", maxCoins([3,1,5,8]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        fib,
        uniquePaths,
        longestCommonSubsequence,
        minDistance,
        wordBreak,
        minCut,
        findTargetSumWays,
        longestIncreasingPath,
        isMatch,
        maxCoins
    };
}