import java.util.*;

public class SlidingWindowExamples {
    
    // 1. Maximum Subarray Sum of Size K
    public static int maxSubarraySum(int[] nums, int k) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (i >= k - 1) {
                maxSum = Math.max(maxSum, sum);
                sum -= nums[i - k + 1];
            }
        }
        return maxSum;
    }
    
    // 2. Longest Substring Without Repeating Characters
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> charSet = new HashSet<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            while (charSet.contains(s.charAt(right))) {
                charSet.remove(s.charAt(left));
                left++;
            }
            charSet.add(s.charAt(right));
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
    
    // 3. Minimum Window Substring
    public static String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, right = 0, valid = 0;
        int start = 0, minLen = Integer.MAX_VALUE;
        Map<Character, Integer> window = new HashMap<>();
        
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) valid++;
            }
            
            while (valid == need.size()) {
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                char d = s.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
    
    // 4. Sliding Window Maximum
    public static int[] maxSlidingWindow(int[] nums, int k) {
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
    
    // 5. Longest Repeating Character Replacement
    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxCount = 0, maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(right) - 'A']);
            while (right - left + 1 - maxCount > k) {
                count[s.charAt(left++) - 'A']--;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
    
    // 6. Permutation in String
    public static boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>();
        for (char c : s1.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, right = 0, valid = 0;
        Map<Character, Integer> window = new HashMap<>();
        
        while (right < s2.length()) {
            char c = s2.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) valid++;
            }
            
            while (right - left >= s1.length()) {
                if (valid == need.size()) return true;
                char d = s2.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return false;
    }
    
    // 7. Find All Anagrams in a String
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> need = new HashMap<>();
        for (char c : p.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, right = 0, valid = 0;
        Map<Character, Integer> window = new HashMap<>();
        
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) valid++;
            }
            
            while (right - left >= p.length()) {
                if (valid == need.size()) result.add(left);
                char d = s.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return result;
    }
    
    // 8. Longest Subarray with Ones after Replacement
    public static int longestOnes(int[] nums, int k) {
        int left = 0, zeros = 0, maxLen = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;
            while (zeros > k) {
                if (nums[left++] == 0) zeros--;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
    
    // 9. Subarray Product Less Than K
    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;
        int left = 0, product = 1, count = 0;
        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];
            while (product >= k) {
                product /= nums[left++];
            }
            count += right - left + 1;
        }
        return count;
    }
    
    // 10. Minimum Size Subarray Sum
    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0, minLen = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left++];
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Sliding Window Examples ===");
        
        // Test 1: Maximum Subarray Sum
        int[] nums1 = {2, 1, 5, 1, 3, 2};
        System.out.println("Max Subarray Sum([2,1,5,1,3,2], k=3): " + maxSubarraySum(nums1, 3));
        
        // Test 2: Longest Substring Without Repeating
        System.out.println("Longest Substring 'abcabcbb': " + lengthOfLongestSubstring("abcabcbb"));
        
        // Test 3: Minimum Window Substring
        System.out.println("Min Window('ADOBECODEBANC', 'ABC'): " + minWindow("ADOBECODEBANC", "ABC"));
        
        // Test 4: Sliding Window Maximum
        int[] nums4 = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println("Sliding Window Max([1,3,-1,-3,5,3,6,7], k=3): " + Arrays.toString(maxSlidingWindow(nums4, 3)));
        
        // Test 5: Character Replacement
        System.out.println("Character Replacement('ABAB', k=2): " + characterReplacement("ABAB", 2));
        
        // Test 6: Permutation in String
        System.out.println("Check Inclusion('ab', 'eidbaooo'): " + checkInclusion("ab", "eidbaooo"));
        
        // Test 7: Find Anagrams
        System.out.println("Find Anagrams('cbaebabacd', 'abc'): " + findAnagrams("cbaebabacd", "abc"));
        
        // Test 8: Longest Ones
        int[] nums8 = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        System.out.println("Longest Ones([1,1,1,0,0,0,1,1,1,1,0], k=2): " + longestOnes(nums8, 2));
        
        // Test 9: Subarray Product Less Than K
        int[] nums9 = {10, 5, 2, 6};
        System.out.println("Subarray Product([10,5,2,6], k=100): " + numSubarrayProductLessThanK(nums9, 100));
        
        // Test 10: Minimum Size Subarray Sum
        int[] nums10 = {2, 3, 1, 2, 4, 3};
        System.out.println("Min Subarray Len(target=7, [2,3,1,2,4,3]): " + minSubArrayLen(7, nums10));
    }
}