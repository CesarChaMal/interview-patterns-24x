import java.util.*;

public class DynamicProgrammingExamples {
    
    // 1. Climbing Stairs
    public int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
    
    // 2. House Robber
    public int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int num : nums) {
            int curr = Math.max(prev1, prev2 + num);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
    
    // 3. Coin Change
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    // 4. Longest Increasing Subsequence
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int pos = Arrays.binarySearch(dp, 0, len, num);
            if (pos < 0) pos = -(pos + 1);
            dp[pos] = num;
            if (pos == len) len++;
        }
        return len;
    }
    
    // 5. Maximum Subarray
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0], currSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }
    
    // 6. Unique Paths
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }
    
    // 7. 0/1 Knapsack
    public int knapsack(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }
    
    // 8. Edit Distance
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] dp = new int[n + 1];
        for (int j = 0; j <= n; j++) dp[j] = j;
        
        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] = i;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = 1 + Math.min(prev, Math.min(dp[j], dp[j - 1]));
                }
                prev = temp;
            }
        }
        return dp[n];
    }
    
    // 9. Longest Common Subsequence
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= m; i++) {
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[j] = prev + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }
        return dp[n];
    }
    
    // 10. Word Break
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
    
    public static void main(String[] args) {
        DynamicProgrammingExamples examples = new DynamicProgrammingExamples();
        
        System.out.println("=== Dynamic Programming Examples ===");
        
        // Test 1: Climbing Stairs
        System.out.println("Climb Stairs(5): " + examples.climbStairs(5));
        
        // Test 2: House Robber
        System.out.println("House Robber([2,7,9,3,1]): " + examples.rob(new int[]{2,7,9,3,1}));
        
        // Test 3: Coin Change
        System.out.println("Coin Change([1,3,4], 6): " + examples.coinChange(new int[]{1,3,4}, 6));
        
        // Test 4: Longest Increasing Subsequence
        System.out.println("LIS([10,9,2,5,3,7,101,18]): " + examples.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
        
        // Test 5: Maximum Subarray
        System.out.println("Max Subarray([-2,1,-3,4,-1,2,1,-5,4]): " + examples.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        
        // Test 6: Unique Paths
        System.out.println("Unique Paths(3,7): " + examples.uniquePaths(3, 7));
        
        // Test 7: 0/1 Knapsack
        System.out.println("Knapsack([1,3,4,5], [1,4,5,7], 7): " + examples.knapsack(new int[]{1,3,4,5}, new int[]{1,4,5,7}, 7));
        
        // Test 8: Edit Distance
        System.out.println("Edit Distance('horse', 'ros'): " + examples.minDistance("horse", "ros"));
        
        // Test 9: Longest Common Subsequence
        System.out.println("LCS('abcde', 'ace'): " + examples.longestCommonSubsequence("abcde", "ace"));
        
        // Test 10: Word Break
        System.out.println("Word Break('leetcode', ['leet','code']): " + examples.wordBreak("leetcode", Arrays.asList("leet", "code")));
    }
}