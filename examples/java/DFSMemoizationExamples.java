import java.util.*;

public class DFSMemoizationExamples {
    
    // 1. House Robber III
    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};
        
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        
        int rob = node.val + left[1] + right[1];
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return new int[]{rob, notRob};
    }
    
    // 2. Longest Increasing Path in a Matrix
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] memo = new int[m][n];
        int maxPath = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxPath = Math.max(maxPath, dfs(matrix, i, j, memo));
            }
        }
        return maxPath;
    }
    
    private int dfs(int[][] matrix, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) return memo[i][j];
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        int maxLen = 1;
        
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && 
                matrix[x][y] > matrix[i][j]) {
                maxLen = Math.max(maxLen, 1 + dfs(matrix, x, y, memo));
            }
        }
        
        memo[i][j] = maxLen;
        return maxLen;
    }
    
    // 3. Longest String Chain
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Map<String, Integer> memo = new HashMap<>();
        int maxChain = 0;
        
        for (String word : words) {
            maxChain = Math.max(maxChain, dfs(word, memo, new HashSet<>(Arrays.asList(words))));
        }
        return maxChain;
    }
    
    private int dfs(String word, Map<String, Integer> memo, Set<String> wordSet) {
        if (memo.containsKey(word)) return memo.get(word);
        
        int maxLen = 1;
        for (int i = 0; i < word.length(); i++) {
            String next = word.substring(0, i) + word.substring(i + 1);
            if (wordSet.contains(next)) {
                maxLen = Math.max(maxLen, 1 + dfs(next, memo, wordSet));
            }
        }
        
        memo.put(word, maxLen);
        return maxLen;
    }
    
    // 4. Stone Game II
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[][] memo = new int[n][n + 1];
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        return dfs(piles, 0, 1, memo, suffixSum);
    }
    
    private int dfs(int[] piles, int i, int M, int[][] memo, int[] suffixSum) {
        if (i >= piles.length) return 0;
        if (memo[i][M] != 0) return memo[i][M];
        
        int maxStones = 0;
        for (int X = 1; X <= 2 * M && i + X <= piles.length; X++) {
            int current = suffixSum[i] - (i + X < piles.length ? suffixSum[i + X] : 0);
            int remaining = suffixSum[i] - current;
            int opponent = dfs(piles, i + X, Math.max(M, X), memo, suffixSum);
            maxStones = Math.max(maxStones, current + remaining - opponent);
        }
        
        memo[i][M] = maxStones;
        return maxStones;
    }
    
    // 5. Cherry Pickup II
    public int cherryPickup(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Integer[][][] memo = new Integer[m][n][n];
        return dfs(grid, 0, 0, n - 1, memo);
    }
    
    private int dfs(int[][] grid, int row, int col1, int col2, Integer[][][] memo) {
        if (row >= grid.length || col1 < 0 || col1 >= grid[0].length || 
            col2 < 0 || col2 >= grid[0].length) return 0;
        
        if (memo[row][col1][col2] != null) return memo[row][col1][col2];
        
        int cherries = grid[row][col1];
        if (col1 != col2) cherries += grid[row][col2];
        
        int maxNext = 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                maxNext = Math.max(maxNext, dfs(grid, row + 1, col1 + d1, col2 + d2, memo));
            }
        }
        
        memo[row][col1][col2] = cherries + maxNext;
        return memo[row][col1][col2];
    }
    
    // 6. Stone Game IV
    public boolean winnerSquareGame(int n) {
        Boolean[] memo = new Boolean[n + 1];
        return dfs(n, memo);
    }
    
    private boolean dfs(int n, Boolean[] memo) {
        if (n == 0) return false;
        if (memo[n] != null) return memo[n];
        
        for (int i = 1; i * i <= n; i++) {
            if (!dfs(n - i * i, memo)) {
                memo[n] = true;
                return true;
            }
        }
        
        memo[n] = false;
        return false;
    }
    
    // 7. Minimum Number of Days to Eat N Oranges
    public int minDaysToEatOranges(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return dfs(n, memo);
    }
    
    private int dfs(int n, Map<Integer, Integer> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        
        int result = Math.min(n % 2 + 1 + dfs(n / 2, memo), n % 3 + 1 + dfs(n / 3, memo));
        memo.put(n, result);
        return result;
    }
    
    // 8. Maximum Non Negative Product in a Matrix
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][][] memo = new long[m][n][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(memo[i][j], Long.MIN_VALUE);
            }
        }
        
        long result = dfs(grid, 0, 0, memo)[1];
        return result < 0 ? -1 : (int)(result % 1000000007);
    }
    
    private long[] dfs(int[][] grid, int i, int j, long[][][] memo) {
        if (i >= grid.length || j >= grid[0].length) return new long[]{Long.MIN_VALUE, Long.MIN_VALUE};
        if (i == grid.length - 1 && j == grid[0].length - 1) return new long[]{grid[i][j], grid[i][j]};
        
        if (memo[i][j][0] != Long.MIN_VALUE) return memo[i][j];
        
        long[] right = dfs(grid, i, j + 1, memo);
        long[] down = dfs(grid, i + 1, j, memo);
        
        long[] candidates = {
            right[0] * grid[i][j], right[1] * grid[i][j],
            down[0] * grid[i][j], down[1] * grid[i][j]
        };
        
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (long val : candidates) {
            if (val != Long.MIN_VALUE && val != Long.MAX_VALUE) {
                min = Math.min(min, val);
                max = Math.max(max, val);
            }
        }
        
        memo[i][j] = new long[]{min == Long.MAX_VALUE ? Long.MIN_VALUE : min, 
                               max == Long.MIN_VALUE ? Long.MIN_VALUE : max};
        return memo[i][j];
    }
    
    // 9. Number of Ways to Form a Target String Given a Dictionary
    public int numWaysToFormTarget(String[] words, String target) {
        int MOD = 1000000007;
        int m = target.length(), n = words[0].length();
        
        int[][] count = new int[n][26];
        for (String word : words) {
            for (int i = 0; i < n; i++) {
                count[i][word.charAt(i) - 'a']++;
            }
        }
        
        Long[][] memo = new Long[m][n];
        return (int)dfs(target, 0, 0, count, memo, MOD);
    }
    
    private long dfs(String target, int i, int j, int[][] count, Long[][] memo, int MOD) {
        if (i == target.length()) return 1;
        if (j == count.length) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        long result = dfs(target, i, j + 1, count, memo, MOD);
        char c = target.charAt(i);
        result = (result + count[j][c - 'a'] * dfs(target, i + 1, j + 1, count, memo, MOD)) % MOD;
        
        memo[i][j] = result;
        return result;
    }
    
    // 10. Maximum Score from Performing Multiplication Operations
    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length, m = multipliers.length;
        Integer[][] memo = new Integer[m][m];
        return dfs(nums, multipliers, 0, 0, memo);
    }
    
    private int dfs(int[] nums, int[] multipliers, int i, int left, Integer[][] memo) {
        if (i == multipliers.length) return 0;
        if (memo[i][left] != null) return memo[i][left];
        
        int right = nums.length - 1 - (i - left);
        int pickLeft = multipliers[i] * nums[left] + dfs(nums, multipliers, i + 1, left + 1, memo);
        int pickRight = multipliers[i] * nums[right] + dfs(nums, multipliers, i + 1, left, memo);
        
        memo[i][left] = Math.max(pickLeft, pickRight);
        return memo[i][left];
    }
    
    // TreeNode class for House Robber III
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    public static void main(String[] args) {
        DFSMemoizationExamples examples = new DFSMemoizationExamples();
        
        System.out.println("=== DFS + Memoization Examples ===");
        
        // Test 1: House Robber III
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);
        System.out.println("1. House Robber III: " + examples.rob(root));
        
        // Test 2: Longest Increasing Path
        int[][] matrix = {{9,9,4},{6,6,8},{2,1,1}};
        System.out.println("2. Longest Increasing Path: " + examples.longestIncreasingPath(matrix));
        
        // Test 3: Longest String Chain
        String[] words = {"a","b","ba","bca","bda","bdca"};
        System.out.println("3. Longest String Chain: " + examples.longestStrChain(words));
        
        // Test 4: Stone Game II
        int[] piles = {2,7,9,4,4};
        System.out.println("4. Stone Game II: " + examples.stoneGameII(piles));
        
        // Test 5: Cherry Pickup II
        int[][] grid = {{3,1,1},{2,5,1},{1,5,5},{2,1,1}};
        System.out.println("5. Cherry Pickup II: " + examples.cherryPickup(grid));
        
        // Test 6: Stone Game IV
        System.out.println("6. Winner Square Game (1): " + examples.winnerSquareGame(1));
        System.out.println("   Winner Square Game (2): " + examples.winnerSquareGame(2));
        
        // Test 7: Min Days to Eat Oranges
        System.out.println("7. Min Days to Eat 10 Oranges: " + examples.minDaysToEatOranges(10));
        
        // Test 8: Max Product Path
        int[][] productGrid = {{-1,-2,-3},{-2,-3,-3},{-3,-3,-2}};
        System.out.println("8. Max Product Path: " + examples.maxProductPath(productGrid));
        
        // Test 9: Ways to Form Target
        String[] dictionary = {"acca","bbbb","caca"};
        String target = "aba";
        System.out.println("9. Ways to Form Target: " + examples.numWaysToFormTarget(dictionary, target));
        
        // Test 10: Maximum Score
        int[] nums = {1,2,3};
        int[] multipliers = {3,2,1};
        System.out.println("10. Maximum Score: " + examples.maximumScore(nums, multipliers));
    }
}