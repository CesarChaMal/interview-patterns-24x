// 1. Maximum Subarray Sum of Size K
function maxSubarraySum(nums: number[], k: number): number {
    let sum = 0;
    let maxSum = -Infinity;
    for (let i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (i >= k - 1) {
            maxSum = Math.max(maxSum, sum);
            sum -= nums[i - k + 1];
        }
    }
    return maxSum;
}

// 2. Longest Substring Without Repeating Characters
function lengthOfLongestSubstring(s: string): number {
    const charSet = new Set<string>();
    let left = 0;
    let maxLen = 0;
    
    for (let right = 0; right < s.length; right++) {
        while (charSet.has(s[right])) {
            charSet.delete(s[left]);
            left++;
        }
        charSet.add(s[right]);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

// 3. Minimum Window Substring
function minWindow(s: string, t: string): string {
    const need = new Map<string, number>();
    for (const c of t) {
        need.set(c, (need.get(c) || 0) + 1);
    }
    
    let left = 0, right = 0, valid = 0;
    let start = 0, minLen = Infinity;
    const window = new Map<string, number>();
    
    while (right < s.length) {
        const c = s[right++];
        if (need.has(c)) {
            window.set(c, (window.get(c) || 0) + 1);
            if (window.get(c) === need.get(c)) valid++;
        }
        
        while (valid === need.size) {
            if (right - left < minLen) {
                start = left;
                minLen = right - left;
            }
            const d = s[left++];
            if (need.has(d)) {
                if (window.get(d) === need.get(d)) valid--;
                window.set(d, window.get(d)! - 1);
            }
        }
    }
    return minLen === Infinity ? "" : s.substring(start, start + minLen);
}

// 4. Sliding Window Maximum
function maxSlidingWindow(nums: number[], k: number): number[] {
    const deque: number[] = [];
    const result: number[] = [];
    
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

// 5. Longest Repeating Character Replacement
function characterReplacement(s: string, k: number): number {
    const count = new Array(26).fill(0);
    let left = 0, maxCount = 0, maxLen = 0;
    
    for (let right = 0; right < s.length; right++) {
        maxCount = Math.max(maxCount, ++count[s.charCodeAt(right) - 65]);
        while (right - left + 1 - maxCount > k) {
            count[s.charCodeAt(left++) - 65]--;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

// 6. Permutation in String
function checkInclusion(s1: string, s2: string): boolean {
    const need = new Map<string, number>();
    for (const c of s1) {
        need.set(c, (need.get(c) || 0) + 1);
    }
    
    let left = 0, right = 0, valid = 0;
    const window = new Map<string, number>();
    
    while (right < s2.length) {
        const c = s2[right++];
        if (need.has(c)) {
            window.set(c, (window.get(c) || 0) + 1);
            if (window.get(c) === need.get(c)) valid++;
        }
        
        while (right - left >= s1.length) {
            if (valid === need.size) return true;
            const d = s2[left++];
            if (need.has(d)) {
                if (window.get(d) === need.get(d)) valid--;
                window.set(d, window.get(d)! - 1);
            }
        }
    }
    return false;
}

// 7. Find All Anagrams in a String
function findAnagrams(s: string, p: string): number[] {
    const result: number[] = [];
    const need = new Map<string, number>();
    for (const c of p) {
        need.set(c, (need.get(c) || 0) + 1);
    }
    
    let left = 0, right = 0, valid = 0;
    const window = new Map<string, number>();
    
    while (right < s.length) {
        const c = s[right++];
        if (need.has(c)) {
            window.set(c, (window.get(c) || 0) + 1);
            if (window.get(c) === need.get(c)) valid++;
        }
        
        while (right - left >= p.length) {
            if (valid === need.size) result.push(left);
            const d = s[left++];
            if (need.has(d)) {
                if (window.get(d) === need.get(d)) valid--;
                window.set(d, window.get(d)! - 1);
            }
        }
    }
    return result;
}

// 8. Longest Subarray with Ones after Replacement
function longestOnes(nums: number[], k: number): number {
    let left = 0, zeros = 0, maxLen = 0;
    for (let right = 0; right < nums.length; right++) {
        if (nums[right] === 0) zeros++;
        while (zeros > k) {
            if (nums[left++] === 0) zeros--;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

// 9. Subarray Product Less Than K
function numSubarrayProductLessThanK(nums: number[], k: number): number {
    if (k <= 1) return 0;
    let left = 0, product = 1, count = 0;
    for (let right = 0; right < nums.length; right++) {
        product *= nums[right];
        while (product >= k) {
            product /= nums[left++];
        }
        count += right - left + 1;
    }
    return count;
}

// 10. Minimum Size Subarray Sum
function minSubArrayLen(target: number, nums: number[]): number {
    let left = 0, sum = 0, minLen = Infinity;
    for (let right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum >= target) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left++];
        }
    }
    return minLen === Infinity ? 0 : minLen;
}

// Test cases
console.log("=== Sliding Window Examples ===");

// Test 1: Maximum Subarray Sum
console.log("Max Subarray Sum([2,1,5,1,3,2], k=3):", maxSubarraySum([2,1,5,1,3,2], 3));

// Test 2: Longest Substring Without Repeating
console.log("Longest Substring 'abcabcbb':", lengthOfLongestSubstring("abcabcbb"));

// Test 3: Minimum Window Substring
console.log("Min Window('ADOBECODEBANC', 'ABC'):", minWindow("ADOBECODEBANC", "ABC"));

// Test 4: Sliding Window Maximum
console.log("Sliding Window Max([1,3,-1,-3,5,3,6,7], k=3):", maxSlidingWindow([1,3,-1,-3,5,3,6,7], 3));

// Test 5: Character Replacement
console.log("Character Replacement('ABAB', k=2):", characterReplacement("ABAB", 2));

// Test 6: Permutation in String
console.log("Check Inclusion('ab', 'eidbaooo'):", checkInclusion("ab", "eidbaooo"));

// Test 7: Find Anagrams
console.log("Find Anagrams('cbaebabacd', 'abc'):", findAnagrams("cbaebabacd", "abc"));

// Test 8: Longest Ones
console.log("Longest Ones([1,1,1,0,0,0,1,1,1,1,0], k=2):", longestOnes([1,1,1,0,0,0,1,1,1,1,0], 2));

// Test 9: Subarray Product Less Than K
console.log("Subarray Product([10,5,2,6], k=100):", numSubarrayProductLessThanK([10,5,2,6], 100));

// Test 10: Minimum Size Subarray Sum
console.log("Min Subarray Len(target=7, [2,3,1,2,4,3]):", minSubArrayLen(7, [2,3,1,2,4,3]));