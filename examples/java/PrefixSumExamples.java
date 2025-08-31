import java.util.*;

public class PrefixSumExamples {
    
    // 1. Subarray Sum Equals K
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, count = 0;
        
        for (int num : nums) {
            sum += num;
            count += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
    
    // 2. Continuous Subarray Sum
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int remainder = sum % k;
            if (map.containsKey(remainder)) {
                if (i - map.get(remainder) > 1) return true;
            } else {
                map.put(remainder, i);
            }
        }
        return false;
    }
    
    // 3. Maximum Size Subarray Sum Equals k
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0, maxLen = 0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                maxLen = Math.max(maxLen, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return maxLen;
    }
    
    // 4. Binary Subarrays With Sum
    public int numSubarraysWithSum(int[] nums, int goal) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, count = 0;
        
        for (int num : nums) {
            sum += num;
            count += map.getOrDefault(sum - goal, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
    
    // 5. Contiguous Array
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0, maxLen = 0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 1 ? 1 : -1;
            if (map.containsKey(sum)) {
                maxLen = Math.max(maxLen, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return maxLen;
    }
    
    // 6. Range Sum Query - Immutable
    static class NumArray {
        private int[] prefixSum;
        
        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }
        
        public int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }
    
    // 7. Product of Array Except Self
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = 1;
        
        for (int i = 1; i < nums.length; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] *= right;
            right *= nums[i];
        }
        
        return result;
    }
    
    // 8. Subarray Sums Divisible by K
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, count = 0;
        
        for (int num : nums) {
            sum += num;
            int remainder = ((sum % k) + k) % k;
            count += map.getOrDefault(remainder, 0);
            map.put(remainder, map.getOrDefault(remainder, 0) + 1);
        }
        return count;
    }
    
    // 9. Path Sum III
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, 1);
        return dfs(root, 0L, targetSum, map);
    }
    
    private int dfs(TreeNode node, long currSum, int target, Map<Long, Integer> map) {
        if (node == null) return 0;
        
        currSum += node.val;
        int count = map.getOrDefault(currSum - target, 0);
        
        map.put(currSum, map.getOrDefault(currSum, 0) + 1);
        count += dfs(node.left, currSum, target, map);
        count += dfs(node.right, currSum, target, map);
        map.put(currSum, map.get(currSum) - 1);
        
        return count;
    }
    
    // 10. Minimum Operations to Reduce X to Zero
    public int minOperations(int[] nums, int x) {
        int target = Arrays.stream(nums).sum() - x;
        if (target < 0) return -1;
        if (target == 0) return nums.length;
        
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0, maxLen = -1;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - target)) {
                maxLen = Math.max(maxLen, i - map.get(sum - target));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        
        return maxLen == -1 ? -1 : nums.length - maxLen;
    }
    
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }
    
    public static void main(String[] args) {
        PrefixSumExamples examples = new PrefixSumExamples();
        
        System.out.println("=== Prefix Sum Examples ===");
        
        // Test 1: Subarray Sum Equals K
        System.out.println("Subarray Sum K([1,1,1], k=2): " + examples.subarraySum(new int[]{1,1,1}, 2));
        
        // Test 2: Continuous Subarray Sum
        System.out.println("Check Subarray Sum([23,2,4,6,7], k=6): " + examples.checkSubarraySum(new int[]{23,2,4,6,7}, 6));
        
        // Test 3: Maximum Size Subarray Sum Equals k
        System.out.println("Max SubArray Len([1,-1,5,-2,3], k=3): " + examples.maxSubArrayLen(new int[]{1,-1,5,-2,3}, 3));
        
        // Test 4: Binary Subarrays With Sum
        System.out.println("Num Subarrays With Sum([1,0,1,0,1], goal=2): " + examples.numSubarraysWithSum(new int[]{1,0,1,0,1}, 2));
        
        // Test 5: Contiguous Array
        System.out.println("Find Max Length([0,1]): " + examples.findMaxLength(new int[]{0,1}));
        
        // Test 6: Range Sum Query
        NumArray numArray = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println("Sum Range(0,2): " + numArray.sumRange(0, 2));
        System.out.println("Sum Range(2,5): " + numArray.sumRange(2, 5));
        
        // Test 7: Product Except Self
        System.out.println("Product Except Self([1,2,3,4]): " + Arrays.toString(examples.productExceptSelf(new int[]{1,2,3,4})));
        
        // Test 8: Subarrays Divisible by K
        System.out.println("Subarrays Div By K([4,5,0,-2,-3,1], k=5): " + examples.subarraysDivByK(new int[]{4,5,0,-2,-3,1}, 5));
        
        // Test 9: Path Sum III
        TreeNode root = new TreeNode(10, new TreeNode(5, new TreeNode(3, new TreeNode(3), new TreeNode(-2)), new TreeNode(2, null, new TreeNode(1))), new TreeNode(-3, null, new TreeNode(11)));
        System.out.println("Path Sum III(targetSum=8): " + examples.pathSum(root, 8));
        
        // Test 10: Min Operations
        System.out.println("Min Operations([1,1,4,2,3], x=5): " + examples.minOperations(new int[]{1,1,4,2,3}, 5));
    }
}