import java.util.*;

public class BinarySearchExamples {
    
    // 1. Binary Search
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    // 2. First Bad Version
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private boolean isBadVersion(int version) {
        return version >= 4; // Mock implementation
    }
    
    // 3. Search Insert Position
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return left;
    }
    
    // 4. Find Peak Element
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    // 5. Search in Rotated Sorted Array
    public int searchRotated(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) right = mid - 1;
                else left = mid + 1;
            } else {
                if (nums[mid] < target && target <= nums[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }
    
    // 6. Find Minimum in Rotated Sorted Array
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) left = mid + 1;
            else right = mid;
        }
        return nums[left];
    }
    
    // 7. Search a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int val = matrix[mid / n][mid % n];
            if (val == target) return true;
            else if (val < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
    
    // 8. Find First and Last Position
    public int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums, target), findLast(nums, target)};
    }
    
    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }
    
    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }
    
    // 9. Sqrt(x)
    public int mySqrt(int x) {
        if (x == 0) return 0;
        int left = 1, right = x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid == x / mid) return mid;
            else if (mid < x / mid) left = mid + 1;
            else right = mid - 1;
        }
        return right;
    }
    
    // 10. Valid Perfect Square
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            if (square == num) return true;
            else if (square < num) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
    
    public static void main(String[] args) {
        BinarySearchExamples examples = new BinarySearchExamples();
        
        System.out.println("=== Binary Search Examples ===");
        
        // Test 1: Binary Search
        System.out.println("Binary Search([-1,0,3,5,9,12], 9): " + examples.search(new int[]{-1,0,3,5,9,12}, 9));
        
        // Test 2: First Bad Version
        System.out.println("First Bad Version(5): " + examples.firstBadVersion(5));
        
        // Test 3: Search Insert Position
        System.out.println("Search Insert([1,3,5,6], 5): " + examples.searchInsert(new int[]{1,3,5,6}, 5));
        
        // Test 4: Find Peak Element
        System.out.println("Find Peak([1,2,3,1]): " + examples.findPeakElement(new int[]{1,2,3,1}));
        
        // Test 5: Search in Rotated Sorted Array
        System.out.println("Search Rotated([4,5,6,7,0,1,2], 0): " + examples.searchRotated(new int[]{4,5,6,7,0,1,2}, 0));
        
        // Test 6: Find Minimum in Rotated Sorted Array
        System.out.println("Find Min([3,4,5,1,2]): " + examples.findMin(new int[]{3,4,5,1,2}));
        
        // Test 7: Search a 2D Matrix
        int[][] matrix = {{1,4,7,11},{2,5,8,12},{3,6,9,16}};
        System.out.println("Search Matrix(matrix, 5): " + examples.searchMatrix(matrix, 5));
        
        // Test 8: Find First and Last Position
        System.out.println("Search Range([5,7,7,8,8,10], 8): " + Arrays.toString(examples.searchRange(new int[]{5,7,7,8,8,10}, 8)));
        
        // Test 9: Sqrt(x)
        System.out.println("Sqrt(4): " + examples.mySqrt(4));
        
        // Test 10: Valid Perfect Square
        System.out.println("Is Perfect Square(16): " + examples.isPerfectSquare(16));
    }
}