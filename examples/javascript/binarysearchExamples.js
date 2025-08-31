// 1. Binary Search
function search(nums, target) {
    let left = 0, right = nums.length - 1;
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        if (nums[mid] === target) return mid;
        else if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

// 2. Find First and Last Position of Element in Sorted Array
function searchRange(nums, target) {
    const findFirst = () => {
        let left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            if (nums[mid] === target) {
                result = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    };
    
    const findLast = () => {
        let left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            if (nums[mid] === target) {
                result = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    };
    
    return [findFirst(), findLast()];
}

// 3. Search in Rotated Sorted Array
function searchRotated(nums, target) {
    let left = 0, right = nums.length - 1;
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        
        if (nums[mid] === target) return mid;
        
        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    return -1;
}

// 4. Find Minimum in Rotated Sorted Array
function findMin(nums) {
    let left = 0, right = nums.length - 1;
    
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        if (nums[mid] > nums[right]) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    return nums[left];
}

// 5. Search Insert Position
function searchInsert(nums, target) {
    let left = 0, right = nums.length - 1;
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        if (nums[mid] === target) return mid;
        else if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return left;
}

// 6. Find Peak Element
function findPeakElement(nums) {
    let left = 0, right = nums.length - 1;
    
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        if (nums[mid] > nums[mid + 1]) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

// 7. Search a 2D Matrix
function searchMatrix(matrix, target) {
    const m = matrix.length, n = matrix[0].length;
    let left = 0, right = m * n - 1;
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        const midValue = matrix[Math.floor(mid / n)][mid % n];
        
        if (midValue === target) return true;
        else if (midValue < target) left = mid + 1;
        else right = mid - 1;
    }
    return false;
}

// 8. Find K Closest Elements
function findClosestElements(arr, k, x) {
    let left = 0, right = arr.length - k;
    
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        if (x - arr[mid] > arr[mid + k] - x) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    
    return arr.slice(left, left + k);
}

// 9. Sqrt(x)
function mySqrt(x) {
    if (x < 2) return x;
    
    let left = 2, right = Math.floor(x / 2);
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        const square = mid * mid;
        
        if (square === x) return mid;
        else if (square < x) left = mid + 1;
        else right = mid - 1;
    }
    return right;
}

// 10. Valid Perfect Square
function isPerfectSquare(num) {
    if (num < 2) return true;
    
    let left = 2, right = Math.floor(num / 2);
    
    while (left <= right) {
        const mid = Math.floor((left + right) / 2);
        const square = mid * mid;
        
        if (square === num) return true;
        else if (square < num) left = mid + 1;
        else right = mid - 1;
    }
    return false;
}

// Test cases
console.log("=== Binary Search Examples ===");

console.log("Binary Search([1,2,3,4,5], 3):", search([1,2,3,4,5], 3));
console.log("Search Range([5,7,7,8,8,10], 8):", searchRange([5,7,7,8,8,10], 8));
console.log("Search Rotated([4,5,6,7,0,1,2], 0):", searchRotated([4,5,6,7,0,1,2], 0));
console.log("Find Min([3,4,5,1,2]):", findMin([3,4,5,1,2]));
console.log("Search Insert([1,3,5,6], 5):", searchInsert([1,3,5,6], 5));
console.log("Find Peak([1,2,3,1]):", findPeakElement([1,2,3,1]));

const matrix = [[1,4,7,11],[2,5,8,12],[3,6,9,16],[10,13,14,17]];
console.log("Search Matrix(target=5):", searchMatrix(matrix, 5));

console.log("Find Closest Elements([1,2,3,4,5], k=4, x=3):", findClosestElements([1,2,3,4,5], 4, 3));
console.log("Sqrt(8):", mySqrt(8));
console.log("Is Perfect Square(16):", isPerfectSquare(16));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        search,
        searchRange,
        searchRotated,
        findMin,
        searchInsert,
        findPeakElement,
        searchMatrix,
        findClosestElements,
        mySqrt,
        isPerfectSquare
    };
}