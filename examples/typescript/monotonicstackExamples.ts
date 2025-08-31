// 1. Next Greater Element I
function nextGreaterElement(nums1: number[], nums2: number[]): number[] {
    const stack: number[] = [];
    const map = new Map<number, number>();
    
    for (const num of nums2) {
        while (stack.length && stack[stack.length - 1] < num) {
            map.set(stack.pop()!, num);
        }
        stack.push(num);
    }
    
    return nums1.map(num => map.get(num) ?? -1);
}

// 2. Daily Temperatures
function dailyTemperatures(temperatures: number[]): number[] {
    const result = new Array(temperatures.length).fill(0);
    const stack: number[] = [];
    
    for (let i = 0; i < temperatures.length; i++) {
        while (stack.length && temperatures[stack[stack.length - 1]] < temperatures[i]) {
            const idx = stack.pop()!;
            result[idx] = i - idx;
        }
        stack.push(i);
    }
    
    return result;
}

// 3. Largest Rectangle in Histogram
function largestRectangleArea(heights: number[]): number {
    const stack: number[] = [];
    let maxArea = 0;
    
    for (let i = 0; i <= heights.length; i++) {
        const h = i === heights.length ? 0 : heights[i];
        while (stack.length && heights[stack[stack.length - 1]] > h) {
            const height = heights[stack.pop()!];
            const width = stack.length === 0 ? i : i - stack[stack.length - 1] - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    
    return maxArea;
}

// 4. Trapping Rain Water
function trap(height: number[]): number {
    const stack: number[] = [];
    let water = 0;
    
    for (let i = 0; i < height.length; i++) {
        while (stack.length && height[i] > height[stack[stack.length - 1]]) {
            const top = stack.pop()!;
            if (stack.length === 0) break;
            const distance = i - stack[stack.length - 1] - 1;
            const boundedHeight = Math.min(height[i], height[stack[stack.length - 1]]) - height[top];
            water += distance * boundedHeight;
        }
        stack.push(i);
    }
    
    return water;
}

// 5. Remove Duplicate Letters
function removeDuplicateLetters(s: string): string {
    const count = new Array(26).fill(0);
    const inStack = new Array(26).fill(false);
    const stack: string[] = [];
    
    for (const c of s) {
        count[c.charCodeAt(0) - 97]++;
    }
    
    for (const c of s) {
        count[c.charCodeAt(0) - 97]--;
        if (inStack[c.charCodeAt(0) - 97]) continue;
        
        while (stack.length && stack[stack.length - 1] > c && count[stack[stack.length - 1].charCodeAt(0) - 97] > 0) {
            inStack[stack.pop()!.charCodeAt(0) - 97] = false;
        }
        stack.push(c);
        inStack[c.charCodeAt(0) - 97] = true;
    }
    
    return stack.join('');
}

// 6. Next Greater Elements II
function nextGreaterElements(nums: number[]): number[] {
    const n = nums.length;
    const result = new Array(n).fill(-1);
    const stack: number[] = [];
    
    for (let i = 0; i < 2 * n; i++) {
        while (stack.length && nums[stack[stack.length - 1]] < nums[i % n]) {
            result[stack.pop()!] = nums[i % n];
        }
        if (i < n) stack.push(i);
    }
    
    return result;
}

// 7. Valid Parentheses
function isValid(s: string): boolean {
    const stack: string[] = [];
    const mapping: { [key: string]: string } = { ')': '(', '}': '{', ']': '[' };
    
    for (const c of s) {
        if (c in mapping) {
            if (stack.length === 0 || stack.pop() !== mapping[c]) return false;
        } else {
            stack.push(c);
        }
    }
    
    return stack.length === 0;
}

// 8. Maximal Rectangle
function maximalRectangle(matrix: string[][]): number {
    if (matrix.length === 0) return 0;
    
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

// 9. Sum of Subarray Minimums
function sumSubarrayMins(arr: number[]): number {
    const MOD = 1_000_000_007;
    const stack: number[] = [];
    let result = 0;
    
    for (let i = 0; i <= arr.length; i++) {
        while (stack.length && (i === arr.length || arr[stack[stack.length - 1]] >= arr[i])) {
            const mid = stack.pop()!;
            const left = stack.length === 0 ? -1 : stack[stack.length - 1];
            const right = i;
            const count = (mid - left) * (right - mid);
            result = (result + count * arr[mid]) % MOD;
        }
        stack.push(i);
    }
    
    return result;
}

// 10. Minimum Stack
class MinStack {
    private stack: number[] = [];
    private minStack: number[] = [];
    
    push(val: number): void {
        this.stack.push(val);
        if (this.minStack.length === 0 || val <= this.minStack[this.minStack.length - 1]) {
            this.minStack.push(val);
        }
    }
    
    pop(): void {
        if (this.stack.pop() === this.minStack[this.minStack.length - 1]) {
            this.minStack.pop();
        }
    }
    
    top(): number {
        return this.stack[this.stack.length - 1];
    }
    
    getMin(): number {
        return this.minStack[this.minStack.length - 1];
    }
}

// Test cases
console.log("=== Monotonic Stack Examples ===");

// Test 1: Next Greater Element I
console.log("Next Greater Element I([4,1,2], [1,3,4,2]):", nextGreaterElement([4,1,2], [1,3,4,2]));

// Test 2: Daily Temperatures
console.log("Daily Temperatures([73,74,75,71,69,72,76,73]):", dailyTemperatures([73,74,75,71,69,72,76,73]));

// Test 3: Largest Rectangle in Histogram
console.log("Largest Rectangle([2,1,5,6,2,3]):", largestRectangleArea([2,1,5,6,2,3]));

// Test 4: Trapping Rain Water
console.log("Trap([0,1,0,2,1,0,1,3,2,1,2,1]):", trap([0,1,0,2,1,0,1,3,2,1,2,1]));

// Test 5: Remove Duplicate Letters
console.log("Remove Duplicate Letters('bcabc'):", removeDuplicateLetters("bcabc"));

// Test 6: Next Greater Elements II
console.log("Next Greater Elements II([1,2,1]):", nextGreaterElements([1,2,1]));

// Test 7: Valid Parentheses
console.log("Valid Parentheses('()[]{}'):", isValid("()[]{}"));

// Test 8: Maximal Rectangle
const matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']];
console.log("Maximal Rectangle:", maximalRectangle(matrix));

// Test 9: Sum of Subarray Minimums
console.log("Sum Subarray Mins([3,1,2,4]):", sumSubarrayMins([3,1,2,4]));

// Test 10: Min Stack
const minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
console.log("Min Stack getMin():", minStack.getMin());
minStack.pop();
console.log("Min Stack top():", minStack.top());
console.log("Min Stack getMin():", minStack.getMin());