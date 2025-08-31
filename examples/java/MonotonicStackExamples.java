import java.util.*;

public class MonotonicStackExamples {
    
    // 1. Next Greater Element I
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = map.getOrDefault(nums1[i], -1);
        }
        return result;
    }
    
    // 2. Daily Temperatures
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int idx = stack.pop();
                result[idx] = i - idx;
            }
            stack.push(i);
        }
        return result;
    }
    
    // 3. Largest Rectangle in Histogram
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }
    
    // 4. Trapping Rain Water
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int water = 0;
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) break;
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                water += distance * boundedHeight;
            }
            stack.push(i);
        }
        return water;
    }
    
    // 5. Remove Duplicate Letters
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        boolean[] inStack = new boolean[26];
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) count[c - 'a']++;
        
        for (char c : s.toCharArray()) {
            count[c - 'a']--;
            if (inStack[c - 'a']) continue;
            
            while (!stack.isEmpty() && stack.peek() > c && count[stack.peek() - 'a'] > 0) {
                inStack[stack.pop() - 'a'] = false;
            }
            stack.push(c);
            inStack[c - 'a'] = true;
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : stack) result.append(c);
        return result.toString();
    }
    
    // 6. Next Greater Element II
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < 2 * n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
                result[stack.pop()] = nums[i % n];
            }
            if (i < n) stack.push(i);
        }
        return result;
    }
    
    // 7. Valid Parentheses
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = Map.of(')', '(', '}', '{', ']', '[');
        
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != map.get(c)) return false;
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
    
    // 8. Maximal Rectangle
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
    
    // 9. Sum of Subarray Minimums
    public int sumSubarrayMins(int[] arr) {
        int MOD = 1_000_000_007;
        Stack<Integer> stack = new Stack<>();
        long result = 0;
        
        for (int i = 0; i <= arr.length; i++) {
            while (!stack.isEmpty() && (i == arr.length || arr[stack.peek()] >= arr[i])) {
                int mid = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                int right = i;
                long count = (long)(mid - left) * (right - mid) % MOD;
                result = (result + count * arr[mid]) % MOD;
            }
            stack.push(i);
        }
        return (int) result;
    }
    
    // 10. Minimum Stack
    static class MinStack {
        private Stack<Integer> stack = new Stack<>();
        private Stack<Integer> minStack = new Stack<>();
        
        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }
        
        public void pop() {
            if (stack.pop().equals(minStack.peek())) {
                minStack.pop();
            }
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return minStack.peek();
        }
    }
    
    public static void main(String[] args) {
        MonotonicStackExamples examples = new MonotonicStackExamples();
        
        System.out.println("=== Monotonic Stack Examples ===");
        
        // Test 1: Next Greater Element I
        System.out.println("Next Greater Element I([4,1,2], [1,3,4,2]): " + 
            Arrays.toString(examples.nextGreaterElement(new int[]{4,1,2}, new int[]{1,3,4,2})));
        
        // Test 2: Daily Temperatures
        System.out.println("Daily Temperatures([73,74,75,71,69,72,76,73]): " + 
            Arrays.toString(examples.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        
        // Test 3: Largest Rectangle in Histogram
        System.out.println("Largest Rectangle([2,1,5,6,2,3]): " + 
            examples.largestRectangleArea(new int[]{2,1,5,6,2,3}));
        
        // Test 4: Trapping Rain Water
        System.out.println("Trap([0,1,0,2,1,0,1,3,2,1,2,1]): " + 
            examples.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        
        // Test 5: Remove Duplicate Letters
        System.out.println("Remove Duplicate Letters('bcabc'): " + 
            examples.removeDuplicateLetters("bcabc"));
        
        // Test 6: Next Greater Elements II
        System.out.println("Next Greater Elements II([1,2,1]): " + 
            Arrays.toString(examples.nextGreaterElements(new int[]{1,2,1})));
        
        // Test 7: Valid Parentheses
        System.out.println("Valid Parentheses('()[]{}'): " + 
            examples.isValid("()[]{}"));
        
        // Test 8: Maximal Rectangle
        char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        System.out.println("Maximal Rectangle: " + examples.maximalRectangle(matrix));
        
        // Test 9: Sum of Subarray Minimums
        System.out.println("Sum Subarray Mins([3,1,2,4]): " + 
            examples.sumSubarrayMins(new int[]{3,1,2,4}));
        
        // Test 10: Min Stack
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("Min Stack getMin(): " + minStack.getMin());
        minStack.pop();
        System.out.println("Min Stack top(): " + minStack.top());
        System.out.println("Min Stack getMin(): " + minStack.getMin());
    }
}