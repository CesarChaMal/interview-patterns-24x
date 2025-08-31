"use strict";
// 1. Climbing Stairs
function climbStairs(n) {
    if (n <= 2)
        return n;
    let prev2 = 1, prev1 = 2;
    for (let i = 3; i <= n; i++) {
        const curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
// 2. House Robber
function rob(nums) {
    let prev2 = 0, prev1 = 0;
    for (const num of nums) {
        const curr = Math.max(prev1, prev2 + num);
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
// 3. Coin Change
function coinChange(coins, amount) {
    const dp = new Array(amount + 1).fill(amount + 1);
    dp[0] = 0;
    for (let i = 1; i <= amount; i++) {
        for (const coin of coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
// 4. Longest Increasing Subsequence
function lengthOfLIS(nums) {
    const dp = [];
    for (const num of nums) {
        let left = 0, right = dp.length;
        while (left < right) {
            const mid = Math.floor((left + right) / 2);
            if (dp[mid] < num)
                left = mid + 1;
            else
                right = mid;
        }
        dp[left] = num;
    }
    return dp.length;
}
// 5. Maximum Subarray
function maxSubArray(nums) {
    let maxSum = nums[0], currSum = nums[0];
    for (let i = 1; i < nums.length; i++) {
        currSum = Math.max(nums[i], currSum + nums[i]);
        maxSum = Math.max(maxSum, currSum);
    }
    return maxSum;
}
// 6. Unique Paths
function uniquePaths(m, n) {
    const dp = new Array(n).fill(1);
    for (let i = 1; i < m; i++) {
        for (let j = 1; j < n; j++) {
            dp[j] += dp[j - 1];
        }
    }
    return dp[n - 1];
}
// 7. 0/1 Knapsack
function knapsack(weights, values, capacity) {
    const dp = new Array(capacity + 1).fill(0);
    for (let i = 0; i < weights.length; i++) {
        for (let w = capacity; w >= weights[i]; w--) {
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    return dp[capacity];
}
// 8. Edit Distance
function minDistance(word1, word2) {
    const m = word1.length, n = word2.length;
    const dp = Array.from({ length: n + 1 }, (_, i) => i);
    for (let i = 1; i <= m; i++) {
        let prev = dp[0];
        dp[0] = i;
        for (let j = 1; j <= n; j++) {
            const temp = dp[j];
            if (word1[i - 1] === word2[j - 1]) {
                dp[j] = prev;
            }
            else {
                dp[j] = 1 + Math.min(prev, dp[j], dp[j - 1]);
            }
            prev = temp;
        }
    }
    return dp[n];
}
// 9. Longest Common Subsequence
function longestCommonSubsequence(text1, text2) {
    const m = text1.length, n = text2.length;
    const dp = new Array(n + 1).fill(0);
    for (let i = 1; i <= m; i++) {
        let prev = 0;
        for (let j = 1; j <= n; j++) {
            const temp = dp[j];
            if (text1[i - 1] === text2[j - 1]) {
                dp[j] = prev + 1;
            }
            else {
                dp[j] = Math.max(dp[j], dp[j - 1]);
            }
            prev = temp;
        }
    }
    return dp[n];
}
// 10. Word Break
function wordBreak(s, wordDict) {
    const wordSet = new Set(wordDict);
    const dp = new Array(s.length + 1).fill(false);
    dp[0] = true;
    for (let i = 1; i <= s.length; i++) {
        for (let j = 0; j < i; j++) {
            if (dp[j] && wordSet.has(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    return dp[s.length];
}
// Test cases
console.log("=== Dynamic Programming Examples ===");
// Test 1: Climbing Stairs
console.log(`Climb Stairs(5): ${climbStairs(5)}`);
// Test 2: House Robber
console.log(`House Robber([2,7,9,3,1]): ${rob([2, 7, 9, 3, 1])}`);
// Test 3: Coin Change
console.log(`Coin Change([1,3,4], 6): ${coinChange([1, 3, 4], 6)}`);
// Test 4: Longest Increasing Subsequence
console.log(`LIS([10,9,2,5,3,7,101,18]): ${lengthOfLIS([10, 9, 2, 5, 3, 7, 101, 18])}`);
// Test 5: Maximum Subarray
console.log(`Max Subarray([-2,1,-3,4,-1,2,1,-5,4]): ${maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4])}`);
// Test 6: Unique Paths
console.log(`Unique Paths(3,7): ${uniquePaths(3, 7)}`);
// Test 7: 0/1 Knapsack
console.log(`Knapsack([1,3,4,5], [1,4,5,7], 7): ${knapsack([1, 3, 4, 5], [1, 4, 5, 7], 7)}`);
// Test 8: Edit Distance
console.log(`Edit Distance('horse', 'ros'): ${minDistance('horse', 'ros')}`);
// Test 9: Longest Common Subsequence
console.log(`LCS('abcde', 'ace'): ${longestCommonSubsequence('abcde', 'ace')}`);
// Test 10: Word Break
console.log(`Word Break('leetcode', ['leet','code']): ${wordBreak('leetcode', ['leet', 'code'])}`);
//# sourceMappingURL=dynamicprogrammingExamples.js.map