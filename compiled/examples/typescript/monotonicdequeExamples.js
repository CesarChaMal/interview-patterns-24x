// 1. Sliding Window Maximum
function maxSlidingWindow(nums, k) {
    const deque = [];
    const result = [];
    for (let i = 0; i < nums.length; i++) {
        while (deque.length && deque[0] < i - k + 1) {
            deque.shift();
        }
        while (deque.length && nums[deque[deque.length - 1]] < nums[i]) {
            deque.pop();
        }
        deque.push(i);
        if (i >= k - 1) {
            result.push(nums[deque[0]]);
        }
    }
    return result;
}
// 2. Sliding Window Minimum
function minSlidingWindow(nums, k) {
    const deque = [];
    const result = [];
    for (let i = 0; i < nums.length; i++) {
        while (deque.length && deque[0] < i - k + 1) {
            deque.shift();
        }
        while (deque.length && nums[deque[deque.length - 1]] > nums[i]) {
            deque.pop();
        }
        deque.push(i);
        if (i >= k - 1) {
            result.push(nums[deque[0]]);
        }
    }
    return result;
}
// 3. Constrained Subsequence Sum
function constrainedSubsetSum(nums, k) {
    const deque = [];
    const dp = new Array(nums.length);
    for (let i = 0; i < nums.length; i++) {
        while (deque.length && deque[0] < i - k) {
            deque.shift();
        }
        dp[i] = nums[i] + (deque.length ? Math.max(0, dp[deque[0]]) : 0);
        while (deque.length && dp[deque[deque.length - 1]] <= dp[i]) {
            deque.pop();
        }
        deque.push(i);
    }
    return Math.max(...dp);
}
// 4. Shortest Subarray with Sum at Least K
function shortestSubarray(nums, k) {
    const prefixSum = [0];
    for (const num of nums) {
        prefixSum.push(prefixSum[prefixSum.length - 1] + num);
    }
    const deque = [];
    let minLen = Infinity;
    for (let i = 0; i < prefixSum.length; i++) {
        while (deque.length && prefixSum[i] - prefixSum[deque[0]] >= k) {
            minLen = Math.min(minLen, i - deque.shift());
        }
        while (deque.length && prefixSum[deque[deque.length - 1]] >= prefixSum[i]) {
            deque.pop();
        }
        deque.push(i);
    }
    return minLen === Infinity ? -1 : minLen;
}
// 5. Jump Game VI
function maxResult(nums, k) {
    const deque = [];
    const dp = new Array(nums.length);
    dp[0] = nums[0];
    deque.push(0);
    for (let i = 1; i < nums.length; i++) {
        while (deque.length && deque[0] < i - k) {
            deque.shift();
        }
        dp[i] = nums[i] + dp[deque[0]];
        while (deque.length && dp[deque[deque.length - 1]] <= dp[i]) {
            deque.pop();
        }
        deque.push(i);
    }
    return dp[nums.length - 1];
}
// 6. Largest Rectangle in Histogram
function largestRectangleArea(heights) {
    const stack = [];
    let maxArea = 0;
    for (let i = 0; i <= heights.length; i++) {
        const h = i === heights.length ? 0 : heights[i];
        while (stack.length && heights[stack[stack.length - 1]] > h) {
            const height = heights[stack.pop()];
            const width = stack.length === 0 ? i : i - stack[stack.length - 1] - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}
// 7. Maximal Rectangle
function maximalRectangle(matrix) {
    if (matrix.length === 0)
        return 0;
    const heights = new Array(matrix[0].length).fill(0);
    let maxArea = 0;
    for (const row of matrix) {
        for (let i = 0; i < row.length; i++) {
            heights[i] = row[i] === '1' ? heights[i] + 1 : 0;
        }
        maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    return maxArea;
}
// 8. Remove K Digits
function removeKdigits(num, k) {
    const stack = [];
    for (const digit of num) {
        while (stack.length && k > 0 && stack[stack.length - 1] > digit) {
            stack.pop();
            k--;
        }
        stack.push(digit);
    }
    while (k > 0) {
        stack.pop();
        k--;
    }
    const result = stack.join('').replace(/^0+/, '');
    return result || '0';
}
// 9. Create Maximum Number
function maxNumber(nums1, nums2, k) {
    function maxArray(nums, k) {
        const result = [];
        let drop = nums.length - k;
        for (const num of nums) {
            while (result.length && result[result.length - 1] < num && drop > 0) {
                result.pop();
                drop--;
            }
            if (result.length < k) {
                result.push(num);
            }
            else {
                drop--;
            }
        }
        return result;
    }
    function merge(nums1, nums2) {
        const result = [];
        let i = 0, j = 0;
        while (i < nums1.length || j < nums2.length) {
            if (greater(nums1, i, nums2, j)) {
                result.push(nums1[i++]);
            }
            else {
                result.push(nums2[j++]);
            }
        }
        return result;
    }
    function greater(nums1, i, nums2, j) {
        while (i < nums1.length && j < nums2.length && nums1[i] === nums2[j]) {
            i++;
            j++;
        }
        return j === nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }
    let result = [];
    for (let i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
        const candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i));
        if (greater(candidate, 0, result, 0)) {
            result = candidate;
        }
    }
    return result;
}
// 10. Minimum Window Subsequence
function minWindow(s, t) {
    function findSubsequence(start) {
        let i = start;
        for (const char of t) {
            while (i < s.length && s[i] !== char) {
                i++;
            }
            if (i === s.length)
                return -1;
            i++;
        }
        return i - 1;
    }
    let minLen = Infinity;
    let result = "";
    for (let i = 0; i < s.length; i++) {
        if (s[i] === t[0]) {
            const end = findSubsequence(i);
            if (end !== -1 && end - i + 1 < minLen) {
                minLen = end - i + 1;
                result = s.substring(i, end + 1);
            }
        }
    }
    return result;
}
// Test cases
console.log("=== Monotonic Deque Examples ===");
// Test 1: Sliding Window Maximum
console.log(`Max Sliding Window([1,3,-1,-3,5,3,6,7], 3): ${maxSlidingWindow([1, 3, -1, -3, 5, 3, 6, 7], 3)}`);
// Test 2: Sliding Window Minimum
console.log(`Min Sliding Window([1,3,-1,-3,5,3,6,7], 3): ${minSlidingWindow([1, 3, -1, -3, 5, 3, 6, 7], 3)}`);
// Test 3: Constrained Subsequence Sum
console.log(`Constrained Subset Sum([10,2,-10,5,20], 2): ${constrainedSubsetSum([10, 2, -10, 5, 20], 2)}`);
// Test 4: Shortest Subarray
console.log(`Shortest Subarray([1], 1): ${shortestSubarray([1], 1)}`);
// Test 5: Jump Game VI
console.log(`Max Result([1,-1,-2,4,-7,3], 2): ${maxResult([1, -1, -2, 4, -7, 3], 2)}`);
// Test 6: Largest Rectangle
console.log(`Largest Rectangle([2,1,5,6,2,3]): ${largestRectangleArea([2, 1, 5, 6, 2, 3])}`);
// Test 7: Maximal Rectangle
const matrix = [['1', '0', '1', '0', '0'], ['1', '0', '1', '1', '1'], ['1', '1', '1', '1', '1'], ['1', '0', '0', '1', '0']];
console.log(`Maximal Rectangle: ${maximalRectangle(matrix)}`);
// Test 8: Remove K Digits
console.log(`Remove K Digits('1432219', 3): ${removeKdigits('1432219', 3)}`);
// Test 9: Create Maximum Number
console.log(`Max Number([3,4,6,5], [9,1,2,5,8,3], 5): ${maxNumber([3, 4, 6, 5], [9, 1, 2, 5, 8, 3], 5)}`);
// Test 10: Minimum Window Subsequence
console.log(`Min Window('abcdebdde', 'bde'): ${minWindow('abcdebdde', 'bde')}`);
