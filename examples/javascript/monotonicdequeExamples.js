// 1. Sliding Window Maximum
function maxSlidingWindow(nums, k) {
    const deque = [];
    const result = [];
    
    for (let i = 0; i < nums.length; i++) {
        // Remove indices outside window
        while (deque.length && deque[0] < i - k + 1) {
            deque.shift();
        }
        
        // Remove smaller elements from back
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
    const dp = [...nums];
    
    for (let i = 0; i < nums.length; i++) {
        // Remove indices outside window
        while (deque.length && deque[0] < i - k) {
            deque.shift();
        }
        
        if (deque.length) {
            dp[i] = Math.max(dp[i], nums[i] + dp[deque[0]]);
        }
        
        // Maintain decreasing order in deque
        while (deque.length && dp[deque[deque.length - 1]] <= dp[i]) {
            deque.pop();
        }
        
        deque.push(i);
    }
    
    return Math.max(...dp);
}

// 4. Shortest Subarray with Sum at Least K
function shortestSubarray(nums, k) {
    const deque = [];
    const prefixSum = [0];
    
    for (const num of nums) {
        prefixSum.push(prefixSum[prefixSum.length - 1] + num);
    }
    
    let minLen = Infinity;
    
    for (let i = 0; i < prefixSum.length; i++) {
        // Check if we can form a valid subarray
        while (deque.length && prefixSum[i] - prefixSum[deque[0]] >= k) {
            minLen = Math.min(minLen, i - deque.shift());
        }
        
        // Maintain increasing order of prefix sums
        while (deque.length && prefixSum[deque[deque.length - 1]] >= prefixSum[i]) {
            deque.pop();
        }
        
        deque.push(i);
    }
    
    return minLen === Infinity ? -1 : minLen;
}

// 5. Jump Game VI
function maxResult(nums, k) {
    const deque = [0];
    const dp = [nums[0]];
    
    for (let i = 1; i < nums.length; i++) {
        // Remove indices outside window
        while (deque.length && deque[0] < i - k) {
            deque.shift();
        }
        
        dp[i] = nums[i] + dp[deque[0]];
        
        // Maintain decreasing order in deque
        while (deque.length && dp[deque[deque.length - 1]] <= dp[i]) {
            deque.pop();
        }
        
        deque.push(i);
    }
    
    return dp[nums.length - 1];
}

// 6. Largest Rectangle in Histogram (using deque)
function largestRectangleArea(heights) {
    const deque = [];
    let maxArea = 0;
    
    for (let i = 0; i <= heights.length; i++) {
        const h = i === heights.length ? 0 : heights[i];
        
        while (deque.length && heights[deque[deque.length - 1]] > h) {
            const height = heights[deque.pop()];
            const width = deque.length === 0 ? i : i - deque[deque.length - 1] - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        deque.push(i);
    }
    
    return maxArea;
}

// 7. Maximum Number of Robots Within Budget
function maximumRobots(chargeTimes, runningCosts, budget) {
    const deque = [];
    let left = 0;
    let sum = 0;
    let maxRobots = 0;
    
    for (let right = 0; right < chargeTimes.length; right++) {
        // Add current robot
        sum += runningCosts[right];
        
        // Maintain decreasing order of charge times
        while (deque.length && chargeTimes[deque[deque.length - 1]] <= chargeTimes[right]) {
            deque.pop();
        }
        deque.push(right);
        
        // Shrink window if over budget
        while (left <= right && 
               chargeTimes[deque[0]] + (right - left + 1) * sum > budget) {
            if (deque[0] === left) {
                deque.shift();
            }
            sum -= runningCosts[left];
            left++;
        }
        
        maxRobots = Math.max(maxRobots, right - left + 1);
    }
    
    return maxRobots;
}

// 8. Find the Most Competitive Subsequence
function mostCompetitive(nums, k) {
    const deque = [];
    
    for (let i = 0; i < nums.length; i++) {
        while (deque.length && 
               deque[deque.length - 1] > nums[i] && 
               deque.length + nums.length - i > k) {
            deque.pop();
        }
        
        if (deque.length < k) {
            deque.push(nums[i]);
        }
    }
    
    return deque;
}

// 9. Minimum Window Subsequence
function minWindow(s1, s2) {
    let minLen = Infinity;
    let start = -1;
    
    for (let i = 0; i < s1.length; i++) {
        if (s1[i] === s2[0]) {
            let j = 0;
            let k = i;
            
            while (k < s1.length && j < s2.length) {
                if (s1[k] === s2[j]) {
                    j++;
                }
                k++;
            }
            
            if (j === s2.length) {
                // Found a valid window, now minimize it
                k--;
                j--;
                
                while (j >= 0) {
                    if (s1[k] === s2[j]) {
                        j--;
                    }
                    k--;
                }
                
                k++;
                if (i - k + 1 < minLen) {
                    minLen = i - k + 1;
                    start = k;
                }
            }
        }
    }
    
    return start === -1 ? "" : s1.substring(start, start + minLen);
}

// 10. Sliding Window Median
function medianSlidingWindow(nums, k) {
    const result = [];
    
    for (let i = 0; i <= nums.length - k; i++) {
        const window = nums.slice(i, i + k).sort((a, b) => a - b);
        const median = k % 2 === 1 ? 
            window[Math.floor(k / 2)] : 
            (window[k / 2 - 1] + window[k / 2]) / 2;
        result.push(median);
    }
    
    return result;
}

// Test cases
console.log("=== Monotonic Deque Examples ===");

console.log("Sliding Window Maximum([1,3,-1,-3,5,3,6,7], k=3):", maxSlidingWindow([1,3,-1,-3,5,3,6,7], 3));
console.log("Sliding Window Minimum([1,3,-1,-3,5,3,6,7], k=3):", minSlidingWindow([1,3,-1,-3,5,3,6,7], 3));
console.log("Constrained Subset Sum([10,2,-10,5,20], k=2):", constrainedSubsetSum([10,2,-10,5,20], 2));
console.log("Shortest Subarray([1], k=1):", shortestSubarray([1], 1));
console.log("Max Result([1,-1,-2,4,-7,3], k=2):", maxResult([1,-1,-2,4,-7,3], 2));
console.log("Largest Rectangle([2,1,5,6,2,3]):", largestRectangleArea([2,1,5,6,2,3]));
console.log("Most Competitive([3,5,2,6], k=2):", mostCompetitive([3,5,2,6], 2));
console.log("Median Sliding Window([1,3,-1,-3,5,3,6,7], k=4):", medianSlidingWindow([1,3,-1,-3,5,3,6,7], 4));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        maxSlidingWindow,
        minSlidingWindow,
        constrainedSubsetSum,
        shortestSubarray,
        maxResult,
        largestRectangleArea,
        maximumRobots,
        mostCompetitive,
        minWindow,
        medianSlidingWindow
    };
}