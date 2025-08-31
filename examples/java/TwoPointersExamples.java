import java.util.*;

public class TwoPointersExamples {
    
    // 1. Two Sum II - Input Array Is Sorted
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            else if (sum < target) left++;
            else right--;
        }
        return new int[]{};
    }
    
    // 2. 3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) left++;
                else right--;
            }
        }
        return result;
    }
    
    // 3. Container With Most Water
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }
    
    // 4. Valid Palindrome
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }
    
    // 5. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                nums[++slow] = nums[fast];
            }
        }
        return slow + 1;
    }
    
    // 6. Move Zeroes
    public void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast];
            }
        }
        while (slow < nums.length) {
            nums[slow++] = 0;
        }
    }
    
    // 7. Trapping Rain Water
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, water = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) leftMax = height[left];
                else water += leftMax - height[left];
                left++;
            } else {
                if (height[right] >= rightMax) rightMax = height[right];
                else water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }
    
    // 8. Sort Colors
    public void sortColors(int[] nums) {
        int left = 0, right = nums.length - 1, i = 0;
        while (i <= right) {
            if (nums[i] == 0) {
                swap(nums, i++, left++);
            } else if (nums[i] == 2) {
                swap(nums, i, right--);
            } else {
                i++;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // 9. 4Sum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < target) left++;
                    else right--;
                }
            }
        }
        return result;
    }
    
    // 10. Reverse String
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left++] = s[right];
            s[right--] = temp;
        }
    }
    
    public static void main(String[] args) {
        TwoPointersExamples examples = new TwoPointersExamples();
        
        System.out.println("=== Two Pointers Examples ===");
        
        // Test 1: Two Sum II
        System.out.println("Two Sum II([2,7,11,15], 9): " + Arrays.toString(examples.twoSum(new int[]{2,7,11,15}, 9)));
        
        // Test 2: 3Sum
        System.out.println("3Sum([-1,0,1,2,-1,-4]): " + examples.threeSum(new int[]{-1,0,1,2,-1,-4}));
        
        // Test 3: Container With Most Water
        System.out.println("Max Area([1,8,6,2,5,4,8,3,7]): " + examples.maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
        
        // Test 4: Valid Palindrome
        System.out.println("Is Palindrome('A man, a plan, a canal: Panama'): " + examples.isPalindrome("A man, a plan, a canal: Panama"));
        
        // Test 5: Remove Duplicates
        int[] nums = {1,1,2};
        System.out.println("Remove Duplicates([1,1,2]): " + examples.removeDuplicates(nums));
        
        // Test 6: Move Zeroes
        int[] nums2 = {0,1,0,3,12};
        examples.moveZeroes(nums2);
        System.out.println("Move Zeroes([0,1,0,3,12]): " + Arrays.toString(nums2));
        
        // Test 7: Trapping Rain Water
        System.out.println("Trap([0,1,0,2,1,0,1,3,2,1,2,1]): " + examples.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        
        // Test 8: Sort Colors
        int[] colors = {2,0,2,1,1,0};
        examples.sortColors(colors);
        System.out.println("Sort Colors([2,0,2,1,1,0]): " + Arrays.toString(colors));
        
        // Test 9: 4Sum
        System.out.println("4Sum([1,0,-1,0,-2,2], 0): " + examples.fourSum(new int[]{1,0,-1,0,-2,2}, 0));
        
        // Test 10: Reverse String
        char[] str = {'h','e','l','l','o'};
        examples.reverseString(str);
        System.out.println("Reverse String(['h','e','l','l','o']): " + Arrays.toString(str));
    }
}