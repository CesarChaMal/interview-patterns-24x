"use strict";
// 1. Binary Search
function search(nums, target) {
    let left = 0, right = nums.length - 1;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (nums[mid] === target)
            return mid;
        else if (nums[mid] < target)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return -1;
}
// 2. First Bad Version
function firstBadVersion(n) {
    let left = 1, right = n;
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (isBadVersion(mid))
            right = mid;
        else
            left = mid + 1;
    }
    return left;
}
function isBadVersion(version) {
    return version >= 4; // Mock implementation
}
// 3. Search Insert Position
function searchInsert(nums, target) {
    let left = 0, right = nums.length - 1;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (nums[mid] === target)
            return mid;
        else if (nums[mid] < target)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return left;
}
// 4. Find Peak Element
function findPeakElement(nums) {
    let left = 0, right = nums.length - 1;
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (nums[mid] > nums[mid + 1])
            right = mid;
        else
            left = mid + 1;
    }
    return left;
}
// 5. Search in Rotated Sorted Array
function searchRotated(nums, target) {
    let left = 0, right = nums.length - 1;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (nums[mid] === target)
            return mid;
        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid])
                right = mid - 1;
            else
                left = mid + 1;
        }
        else {
            if (nums[mid] < target && target <= nums[right])
                left = mid + 1;
            else
                right = mid - 1;
        }
    }
    return -1;
}
// 6. Find Minimum in Rotated Sorted Array
function findMin(nums) {
    let left = 0, right = nums.length - 1;
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (nums[mid] > nums[right])
            left = mid + 1;
        else
            right = mid;
    }
    return nums[left];
}
// 7. Search a 2D Matrix
function searchMatrix(matrix, target) {
    const m = matrix.length, n = matrix[0].length;
    let left = 0, right = m * n - 1;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        const val = matrix[Math.floor(mid / n)][mid % n];
        if (val === target)
            return true;
        else if (val < target)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return false;
}
// 8. Find First and Last Position
function searchRange(nums, target) {
    const findFirst = (nums, target) => {
        let left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            const mid = left + Math.floor((right - left) / 2);
            if (nums[mid] === target) {
                result = mid;
                right = mid - 1;
            }
            else if (nums[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return result;
    };
    const findLast = (nums, target) => {
        let left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            const mid = left + Math.floor((right - left) / 2);
            if (nums[mid] === target) {
                result = mid;
                left = mid + 1;
            }
            else if (nums[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return result;
    };
    return [findFirst(nums, target), findLast(nums, target)];
}
// 9. Sqrt(x)
function mySqrt(x) {
    if (x === 0)
        return 0;
    let left = 1, right = x;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (mid === Math.floor(x / mid))
            return mid;
        else if (mid < Math.floor(x / mid))
            left = mid + 1;
        else
            right = mid - 1;
    }
    return right;
}
// 10. Valid Perfect Square
function isPerfectSquare(num) {
    let left = 1, right = num;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        const square = mid * mid;
        if (square === num)
            return true;
        else if (square < num)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return false;
}
// Test cases
console.log("=== Binary Search Examples ===");
// Test 1: Binary Search
console.log("Binary Search([-1,0,3,5,9,12], 9):", search([-1, 0, 3, 5, 9, 12], 9));
// Test 2: First Bad Version
console.log("First Bad Version(5):", firstBadVersion(5));
// Test 3: Search Insert Position
console.log("Search Insert([1,3,5,6], 5):", searchInsert([1, 3, 5, 6], 5));
// Test 4: Find Peak Element
console.log("Find Peak([1,2,3,1]):", findPeakElement([1, 2, 3, 1]));
// Test 5: Search in Rotated Sorted Array
console.log("Search Rotated([4,5,6,7,0,1,2], 0):", searchRotated([4, 5, 6, 7, 0, 1, 2], 0));
// Test 6: Find Minimum in Rotated Sorted Array
console.log("Find Min([3,4,5,1,2]):", findMin([3, 4, 5, 1, 2]));
// Test 7: Search a 2D Matrix
const matrix = [[1, 4, 7, 11], [2, 5, 8, 12], [3, 6, 9, 16]];
console.log("Search Matrix(matrix, 5):", searchMatrix(matrix, 5));
// Test 8: Find First and Last Position
console.log("Search Range([5,7,7,8,8,10], 8):", searchRange([5, 7, 7, 8, 8, 10], 8));
// Test 9: Sqrt(x)
console.log("Sqrt(4):", mySqrt(4));
// Test 10: Valid Perfect Square
console.log("Is Perfect Square(16):", isPerfectSquare(16));
//# sourceMappingURL=binarysearchExamples.js.map