// 1. Two Sum II - Input Array Is Sorted
function twoSum(numbers, target) {
    let left = 0, right = numbers.length - 1;
    while (left < right) {
        const sum = numbers[left] + numbers[right];
        if (sum === target) return [left + 1, right + 1];
        else if (sum < target) left++;
        else right--;
    }
    return [];
}

// 2. 3Sum
function threeSum(nums) {
    nums.sort((a, b) => a - b);
    const result = [];
    for (let i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] === nums[i - 1]) continue;
        let left = i + 1, right = nums.length - 1;
        while (left < right) {
            const sum = nums[i] + nums[left] + nums[right];
            if (sum === 0) {
                result.push([nums[i], nums[left], nums[right]]);
                while (left < right && nums[left] === nums[left + 1]) left++;
                while (left < right && nums[right] === nums[right - 1]) right--;
                left++;
                right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return result;
}

// 3. Container With Most Water
function maxArea(height) {
    let left = 0, right = height.length - 1, maxArea = 0;
    while (left < right) {
        maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
        if (height[left] < height[right]) left++;
        else right--;
    }
    return maxArea;
}

// 4. Valid Palindrome
function isPalindrome(s) {
    let left = 0, right = s.length - 1;
    while (left < right) {
        while (left < right && !isAlphaNumeric(s[left])) left++;
        while (left < right && !isAlphaNumeric(s[right])) right--;
        if (s[left].toLowerCase() !== s[right].toLowerCase()) return false;
        left++;
        right--;
    }
    return true;
}

function isAlphaNumeric(char) {
    return /[a-zA-Z0-9]/.test(char);
}

// 5. Remove Duplicates from Sorted Array
function removeDuplicates(nums) {
    let slow = 0;
    for (let fast = 1; fast < nums.length; fast++) {
        if (nums[fast] !== nums[slow]) {
            nums[++slow] = nums[fast];
        }
    }
    return slow + 1;
}

// 6. Move Zeroes
function moveZeroes(nums) {
    let slow = 0;
    for (let fast = 0; fast < nums.length; fast++) {
        if (nums[fast] !== 0) {
            nums[slow++] = nums[fast];
        }
    }
    while (slow < nums.length) {
        nums[slow++] = 0;
    }
}

// 7. Trapping Rain Water
function trap(height) {
    let left = 0, right = height.length - 1;
    let leftMax = 0, rightMax = 0, water = 0;
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
function sortColors(nums) {
    let left = 0, right = nums.length - 1, i = 0;
    while (i <= right) {
        if (nums[i] === 0) {
            [nums[i++], nums[left++]] = [nums[left], nums[i]];
        } else if (nums[i] === 2) {
            [nums[i], nums[right--]] = [nums[right], nums[i]];
        } else {
            i++;
        }
    }
}

// 9. 4Sum
function fourSum(nums, target) {
    nums.sort((a, b) => a - b);
    const result = [];
    for (let i = 0; i < nums.length - 3; i++) {
        if (i > 0 && nums[i] === nums[i - 1]) continue;
        for (let j = i + 1; j < nums.length - 2; j++) {
            if (j > i + 1 && nums[j] === nums[j - 1]) continue;
            let left = j + 1, right = nums.length - 1;
            while (left < right) {
                const sum = nums[i] + nums[j] + nums[left] + nums[right];
                if (sum === target) {
                    result.push([nums[i], nums[j], nums[left], nums[right]]);
                    while (left < right && nums[left] === nums[left + 1]) left++;
                    while (left < right && nums[right] === nums[right - 1]) right--;
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
function reverseString(s) {
    let left = 0, right = s.length - 1;
    while (left < right) {
        [s[left++], s[right--]] = [s[right], s[left]];
    }
}

// Test cases
console.log("=== Two Pointers Examples ===");

// Test 1 Sum II
console.log("Two Sum II([2,7,11,15], 9):", twoSum([2,7,11,15], 9));

// Test 2
console.log("3Sum([-1,0,1,2,-1,-4]):", threeSum([-1,0,1,2,-1,-4]));

// Test 3 With Most Water
console.log("Max Area([1,8,6,2,5,4,8,3,7]):", maxArea([1,8,6,2,5,4,8,3,7]));

// Test 4 Palindrome
console.log("Is Palindrome('A man, a plan, a canal: Panama'):", isPalindrome("A man, a plan, a canal: Panama"));

// Test 5 Duplicates
const nums = [1,1,2];
console.log("Remove Duplicates([1,1,2]):", removeDuplicates(nums));

// Test 6 Zeroes
const nums2 = [0,1,0,3,12];
moveZeroes(nums2);
console.log("Move Zeroes([0,1,0,3,12]):", nums2);

// Test 7 Rain Water
console.log("Trap([0,1,0,2,1,0,1,3,2,1,2,1]):", trap([0,1,0,2,1,0,1,3,2,1,2,1]));

// Test 8 Colors
const colors = [2,0,2,1,1,0];
sortColors(colors);
console.log("Sort Colors([2,0,2,1,1,0]):", colors);

// Test 9
console.log("4Sum([1,0,-1,0,-2,2], 0):", fourSum([1,0,-1,0,-2,2], 0));

// Test 10 String
const s = ['h','e','l','l','o'];
reverseString(s);
console.log("Reverse String(['h','e','l','l','o']):", s);

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        twoSum,
        threeSum,
        maxArea,
        isPalindrome,
        removeDuplicates,
        moveZeroes,
        trap,
        sortColors,
        fourSum,
        reverseString
    };
}