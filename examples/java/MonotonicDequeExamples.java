import java.util.*;

public class MonotonicDequeExamples {
    
    // 1. Sliding Window Maximum
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];
        
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
    
    // 2. Sliding Window Minimum
    public int[] minSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];
        
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] > nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
    
    // 3. Constrained Subsequence Sum
    public int constrainedSubsetSum(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] dp = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            dp[i] = nums[i] + (deque.isEmpty() ? 0 : Math.max(0, dp[deque.peekFirst()]));
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return Arrays.stream(dp).max().getAsInt();
    }
    
    // 4. Shortest Subarray with Sum at Least K
    public int shortestSubarray(int[] nums, int k) {
        long[] prefixSum = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        Deque<Integer> deque = new ArrayDeque<>();
        int minLen = Integer.MAX_VALUE;
        
        for (int i = 0; i <= nums.length; i++) {
            while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] >= k) {
                minLen = Math.min(minLen, i - deque.pollFirst());
            }
            while (!deque.isEmpty() && prefixSum[deque.peekLast()] >= prefixSum[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
    
    // 5. Jump Game VI
    public int maxResult(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        deque.offerLast(0);
        
        for (int i = 1; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            dp[i] = nums[i] + dp[deque.peekFirst()];
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return dp[nums.length - 1];
    }
    
    // 6. Largest Rectangle in Histogram
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        
        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];
            while (!stack.isEmpty() && heights[stack.peekLast()] > h) {
                int height = heights[stack.pollLast()];
                int width = stack.isEmpty() ? i : i - stack.peekLast() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.offerLast(i);
        }
        return maxArea;
    }
    
    // 7. Maximal Rectangle
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int[] heights = new int[matrix[0].length];
        int maxArea = 0;
        
        for (char[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                heights[i] = row[i] == '1' ? heights[i] + 1 : 0;
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }
    
    // 8. Remove K Digits
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        
        for (char digit : num.toCharArray()) {
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > digit) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(digit);
        }
        
        while (k > 0) {
            stack.pollLast();
            k--;
        }
        
        StringBuilder result = new StringBuilder();
        boolean leadingZero = true;
        for (char digit : stack) {
            if (leadingZero && digit == '0') continue;
            leadingZero = false;
            result.append(digit);
        }
        return result.length() == 0 ? "0" : result.toString();
    }
    
    // 9. Create Maximum Number
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] result = new int[k];
        for (int i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
            int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i));
            if (greater(candidate, 0, result, 0)) {
                result = candidate;
            }
        }
        return result;
    }
    
    private int[] maxArray(int[] nums, int k) {
        int[] result = new int[k];
        int drop = nums.length - k;
        int idx = 0;
        for (int num : nums) {
            while (idx > 0 && result[idx - 1] < num && drop > 0) {
                idx--;
                drop--;
            }
            if (idx < k) result[idx++] = num;
            else drop--;
        }
        return result;
    }
    
    private int[] merge(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;
        while (i < nums1.length || j < nums2.length) {
            if (greater(nums1, i, nums2, j)) {
                result[k++] = nums1[i++];
            } else {
                result[k++] = nums2[j++];
            }
        }
        return result;
    }
    
    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }
    
    // 10. Minimum Window Subsequence
    public String minWindow(String s, String t) {
        Deque<Integer>[] positions = new Deque[26];
        for (int i = 0; i < 26; i++) {
            positions[i] = new ArrayDeque<>();
        }
        
        for (int i = 0; i < s.length(); i++) {
            positions[s.charAt(i) - 'a'].offerLast(i);
        }
        
        String result = "";
        int minLen = Integer.MAX_VALUE;
        
        for (int start = 0; start < s.length(); start++) {
            if (s.charAt(start) == t.charAt(0)) {
                int end = findSubsequence(s, t, start, positions);
                if (end != -1 && end - start + 1 < minLen) {
                    minLen = end - start + 1;
                    result = s.substring(start, end + 1);
                }
            }
        }
        return result;
    }
    
    private int findSubsequence(String s, String t, int start, Deque<Integer>[] positions) {
        int pos = start;
        for (int i = 1; i < t.length(); i++) {
            char c = t.charAt(i);
            Deque<Integer> deque = positions[c - 'a'];
            while (!deque.isEmpty() && deque.peekFirst() <= pos) {
                deque.pollFirst();
            }
            if (deque.isEmpty()) return -1;
            pos = deque.peekFirst();
        }
        return pos;
    }
    
    public static void main(String[] args) {
        MonotonicDequeExamples examples = new MonotonicDequeExamples();
        
        System.out.println("=== Monotonic Deque Examples ===");
        
        // Test 1: Sliding Window Maximum
        System.out.println("Max Sliding Window([1,3,-1,-3,5,3,6,7], 3): " + 
            Arrays.toString(examples.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        
        // Test 2: Sliding Window Minimum
        System.out.println("Min Sliding Window([1,3,-1,-3,5,3,6,7], 3): " + 
            Arrays.toString(examples.minSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        
        // Test 3: Constrained Subsequence Sum
        System.out.println("Constrained Subset Sum([10,2,-10,5,20], 2): " + 
            examples.constrainedSubsetSum(new int[]{10,2,-10,5,20}, 2));
        
        // Test 4: Shortest Subarray
        System.out.println("Shortest Subarray([1], 1): " + 
            examples.shortestSubarray(new int[]{1}, 1));
        
        // Test 5: Jump Game VI
        System.out.println("Max Result([1,-1,-2,4,-7,3], 2): " + 
            examples.maxResult(new int[]{1,-1,-2,4,-7,3}, 2));
        
        // Test 6: Largest Rectangle
        System.out.println("Largest Rectangle([2,1,5,6,2,3]): " + 
            examples.largestRectangleArea(new int[]{2,1,5,6,2,3}));
        
        // Test 7: Maximal Rectangle
        char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        System.out.println("Maximal Rectangle: " + examples.maximalRectangle(matrix));
        
        // Test 8: Remove K Digits
        System.out.println("Remove K Digits('1432219', 3): " + examples.removeKdigits("1432219", 3));
        
        // Test 9: Create Maximum Number
        System.out.println("Max Number([3,4,6,5], [9,1,2,5,8,3], 5): " + 
            Arrays.toString(examples.maxNumber(new int[]{3,4,6,5}, new int[]{9,1,2,5,8,3}, 5)));
        
        // Test 10: Minimum Window Subsequence
        System.out.println("Min Window('abcdebdde', 'bde'): " + examples.minWindow("abcdebdde", "bde"));
    }
}