// 1. Subarray Sum Equals K
function subarraySum(nums, k) {
    const map = new Map();
    map.set(0, 1);
    let sum = 0, count = 0;
    
    for (const num of nums) {
        sum += num;
        count += map.get(sum - k) || 0;
        map.set(sum, (map.get(sum) || 0) + 1);
    }
    return count;
}

// 2. Continuous Subarray Sum
function checkSubarraySum(nums, k) {
    const map = new Map();
    map.set(0, -1);
    let sum = 0;
    
    for (let i = 0; i < nums.length; i++) {
        sum += nums[i];
        const mod = sum % k;
        if (map.has(mod)) {
            if (i - map.get(mod) > 1) return true;
        } else {
            map.set(mod, i);
        }
    }
    return false;
}

// 3. Maximum Size Subarray Sum Equals k
function maxSubArrayLen(nums, k) {
    const map = new Map();
    map.set(0, -1);
    let sum = 0, maxLen = 0;
    
    for (let i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (map.has(sum - k)) {
            maxLen = Math.max(maxLen, i - map.get(sum - k));
        }
        if (!map.has(sum)) {
            map.set(sum, i);
        }
    }
    return maxLen;
}

// 4. Binary Subarrays With Sum
function numSubarraysWithSum(nums, goal) {
    const map = new Map();
    map.set(0, 1);
    let sum = 0, count = 0;
    
    for (const num of nums) {
        sum += num;
        count += map.get(sum - goal) || 0;
        map.set(sum, (map.get(sum) || 0) + 1);
    }
    return count;
}

// 5. Contiguous Array
function findMaxLength(nums) {
    const map = new Map();
    map.set(0, -1);
    let sum = 0, maxLen = 0;
    
    for (let i = 0; i < nums.length; i++) {
        sum += nums[i] === 1 ? 1 : -1;
        if (map.has(sum)) {
            maxLen = Math.max(maxLen, i - map.get(sum));
        } else {
            map.set(sum, i);
        }
    }
    return maxLen;
}

// 6. Range Sum Query - Immutable
class NumArray {
    constructor(nums) {
        this.prefixSum = [0];
        for (const num of nums) {
            this.prefixSum.push(this.prefixSum[this.prefixSum.length - 1] + num);
        }
    }
    
    sumRange(left, right) {
        return this.prefixSum[right + 1] - this.prefixSum[left];
    }
}

// 7. Product of Array Except Self
function productExceptSelf(nums) {
    const result = new Array(nums.length);
    
    result[0] = 1;
    for (let i = 1; i < nums.length; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }
    
    let right = 1;
    for (let i = nums.length - 1; i >= 0; i--) {
        result[i] *= right;
        right *= nums[i];
    }
    return result;
}

// 8. Subarray Sums Divisible by K
function subarraysDivByK(nums, k) {
    const map = new Map();
    map.set(0, 1);
    let sum = 0, count = 0;
    
    for (const num of nums) {
        sum += num;
        const mod = ((sum % k) + k) % k;
        count += map.get(mod) || 0;
        map.set(mod, (map.get(mod) || 0) + 1);
    }
    return count;
}

// 9. Path Sum III
class TreeNode {
    constructor(val, left, right) {
        this.val = val === undefined ? 0 : val;
        this.left = left === undefined ? null : left;
        this.right = right === undefined ? null : right;
    }
}

function pathSum(root, targetSum) {
    const map = new Map();
    map.set(0, 1);
    
    function dfs(node, currSum) {
        if (!node) return 0;
        
        const newSum = currSum + node.val;
        const count = map.get(newSum - targetSum) || 0;
        
        map.set(newSum, (map.get(newSum) || 0) + 1);
        const result = count + dfs(node.left, newSum) + dfs(node.right, newSum);
        map.set(newSum, map.get(newSum) - 1);
        
        return result;
    }
    
    return dfs(root, 0);
}

// 10. Minimum Operations to Reduce X to Zero
function minOperations(nums, x) {
    const target = nums.reduce((a, b) => a + b) - x;
    if (target < 0) return -1;
    if (target === 0) return nums.length;
    
    const map = new Map();
    map.set(0, -1);
    let sum = 0, maxLen = -1;
    
    for (let i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (map.has(sum - target)) {
            maxLen = Math.max(maxLen, i - map.get(sum - target));
        }
        if (!map.has(sum)) {
            map.set(sum, i);
        }
    }
    
    return maxLen === -1 ? -1 : nums.length - maxLen;
}

// Test cases
console.log("=== Prefix Sum Examples ===");

console.log("Subarray Sum K([1,1,1], 2):", subarraySum([1,1,1], 2));
console.log("Check Subarray Sum([23,2,4,6,7], 6):", checkSubarraySum([23,2,4,6,7], 6));
console.log("Max SubArray Len([1,-1,5,-2,3], 3):", maxSubArrayLen([1,-1,5,-2,3], 3));
console.log("Num Subarrays With Sum([1,0,1,0,1], 2):", numSubarraysWithSum([1,0,1,0,1], 2));
console.log("Find Max Length([0,1]):", findMaxLength([0,1]));

const numArray = new NumArray([-2,0,3,-5,2,-1]);
console.log("Sum Range(0, 2):", numArray.sumRange(0, 2));

console.log("Product Except Self([1,2,3,4]):", productExceptSelf([1,2,3,4]));
console.log("Subarrays Div By K([4,5,0,-2,-3,1], 5):", subarraysDivByK([4,5,0,-2,-3,1], 5));
console.log("Min Operations([1,1,4,2,3], 5):", minOperations([1,1,4,2,3], 5));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        subarraySum,
        checkSubarraySum,
        maxSubArrayLen,
        numSubarraysWithSum,
        findMaxLength,
        NumArray,
        productExceptSelf,
        subarraysDivByK,
        pathSum,
        minOperations
    };
}