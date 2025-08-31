// Segment Tree implementation
class SegmentTree {
    constructor(arr) {
        this.n = arr.length;
        this.tree = new Array(4 * this.n);
        this.build(arr, 0, 0, this.n - 1);
    }
    
    build(arr, node, start, end) {
        if (start === end) {
            this.tree[node] = arr[start];
        } else {
            const mid = Math.floor((start + end) / 2);
            this.build(arr, 2 * node + 1, start, mid);
            this.build(arr, 2 * node + 2, mid + 1, end);
            this.tree[node] = this.tree[2 * node + 1] + this.tree[2 * node + 2];
        }
    }
    
    update(idx, val) {
        this.updateHelper(0, 0, this.n - 1, idx, val);
    }
    
    updateHelper(node, start, end, idx, val) {
        if (start === end) {
            this.tree[node] = val;
        } else {
            const mid = Math.floor((start + end) / 2);
            if (idx <= mid) {
                this.updateHelper(2 * node + 1, start, mid, idx, val);
            } else {
                this.updateHelper(2 * node + 2, mid + 1, end, idx, val);
            }
            this.tree[node] = this.tree[2 * node + 1] + this.tree[2 * node + 2];
        }
    }
    
    query(l, r) {
        return this.queryHelper(0, 0, this.n - 1, l, r);
    }
    
    queryHelper(node, start, end, l, r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return this.tree[node];
        
        const mid = Math.floor((start + end) / 2);
        const leftSum = this.queryHelper(2 * node + 1, start, mid, l, r);
        const rightSum = this.queryHelper(2 * node + 2, mid + 1, end, l, r);
        return leftSum + rightSum;
    }
}

// 1. Range Sum Query - Mutable
class NumArray {
    constructor(nums) {
        this.segTree = new SegmentTree(nums);
    }
    
    update(index, val) {
        this.segTree.update(index, val);
    }
    
    sumRange(left, right) {
        return this.segTree.query(left, right);
    }
}

// 2. Range Minimum Query
class RMQSegmentTree {
    constructor(arr) {
        this.n = arr.length;
        this.tree = new Array(4 * this.n);
        this.build(arr, 0, 0, this.n - 1);
    }
    
    build(arr, node, start, end) {
        if (start === end) {
            this.tree[node] = arr[start];
        } else {
            const mid = Math.floor((start + end) / 2);
            this.build(arr, 2 * node + 1, start, mid);
            this.build(arr, 2 * node + 2, mid + 1, end);
            this.tree[node] = Math.min(this.tree[2 * node + 1], this.tree[2 * node + 2]);
        }
    }
    
    query(l, r) {
        return this.queryHelper(0, 0, this.n - 1, l, r);
    }
    
    queryHelper(node, start, end, l, r) {
        if (r < start || end < l) return Infinity;
        if (l <= start && end <= r) return this.tree[node];
        
        const mid = Math.floor((start + end) / 2);
        const leftMin = this.queryHelper(2 * node + 1, start, mid, l, r);
        const rightMin = this.queryHelper(2 * node + 2, mid + 1, end, l, r);
        return Math.min(leftMin, rightMin);
    }
}

// 3. Count of Smaller Numbers After Self
function countSmaller(nums) {
    const result = new Array(nums.length).fill(0);
    const indices = nums.map((_, i) => i);
    
    function mergeSort(left, right) {
        if (left >= right) return;
        
        const mid = Math.floor((left + right) / 2);
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        
        const temp = [];
        let i = left, j = mid + 1;
        
        while (i <= mid && j <= right) {
            if (nums[indices[i]] <= nums[indices[j]]) {
                result[indices[i]] += j - mid - 1;
                temp.push(indices[i++]);
            } else {
                temp.push(indices[j++]);
            }
        }
        
        while (i <= mid) {
            result[indices[i]] += j - mid - 1;
            temp.push(indices[i++]);
        }
        
        while (j <= right) {
            temp.push(indices[j++]);
        }
        
        for (let k = 0; k < temp.length; k++) {
            indices[left + k] = temp[k];
        }
    }
    
    mergeSort(0, nums.length - 1);
    return result;
}

// 4. Range Sum Query 2D - Mutable
class NumMatrix {
    constructor(matrix) {
        if (!matrix || matrix.length === 0) return;
        
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.matrix = matrix;
        this.trees = [];
        
        for (let i = 0; i < this.m; i++) {
            this.trees.push(new SegmentTree(matrix[i]));
        }
    }
    
    update(row, col, val) {
        this.trees[row].update(col, val);
    }
    
    sumRegion(row1, col1, row2, col2) {
        let sum = 0;
        for (let i = row1; i <= row2; i++) {
            sum += this.trees[i].query(col1, col2);
        }
        return sum;
    }
}

// 5. My Calendar III
class MyCalendarThree {
    constructor() {
        this.events = new Map();
    }
    
    book(start, end) {
        this.events.set(start, (this.events.get(start) || 0) + 1);
        this.events.set(end, (this.events.get(end) || 0) - 1);
        
        let active = 0;
        let maxBookings = 0;
        
        const sortedTimes = [...this.events.keys()].sort((a, b) => a - b);
        
        for (const time of sortedTimes) {
            active += this.events.get(time);
            maxBookings = Math.max(maxBookings, active);
        }
        
        return maxBookings;
    }
}

// 6. Falling Squares
function fallingSquares(positions) {
    const result = [];
    const intervals = [];
    
    for (const [left, sideLength] of positions) {
        const right = left + sideLength;
        let height = sideLength;
        
        // Find max height in overlapping intervals
        for (const [l, r, h] of intervals) {
            if (left < r && right > l) {
                height = Math.max(height, h + sideLength);
            }
        }
        
        intervals.push([left, right, height]);
        result.push(Math.max(...intervals.map(interval => interval[2])));
    }
    
    return result;
}

// 7. The Skyline Problem
function getSkyline(buildings) {
    const events = [];
    
    for (const [left, right, height] of buildings) {
        events.push([left, -height]); // Start event (negative for sorting)
        events.push([right, height]); // End event
    }
    
    events.sort((a, b) => {
        if (a[0] !== b[0]) return a[0] - b[0];
        return a[1] - b[1];
    });
    
    const result = [];
    const heights = [0];
    
    for (const [x, h] of events) {
        if (h < 0) {
            // Start of building
            heights.push(-h);
        } else {
            // End of building
            const index = heights.indexOf(h);
            heights.splice(index, 1);
        }
        
        heights.sort((a, b) => b - a);
        const maxHeight = heights[0];
        
        if (result.length === 0 || result[result.length - 1][1] !== maxHeight) {
            result.push([x, maxHeight]);
        }
    }
    
    return result;
}

// 8. Reverse Pairs
function reversePairs(nums) {
    let count = 0;
    
    function mergeSort(left, right) {
        if (left >= right) return;
        
        const mid = Math.floor((left + right) / 2);
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        
        // Count reverse pairs
        let j = mid + 1;
        for (let i = left; i <= mid; i++) {
            while (j <= right && nums[i] > 2 * nums[j]) {
                j++;
            }
            count += j - mid - 1;
        }
        
        // Merge
        const temp = [];
        let i = left;
        j = mid + 1;
        
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp.push(nums[i++]);
            } else {
                temp.push(nums[j++]);
            }
        }
        
        while (i <= mid) temp.push(nums[i++]);
        while (j <= right) temp.push(nums[j++]);
        
        for (let k = 0; k < temp.length; k++) {
            nums[left + k] = temp[k];
        }
    }
    
    mergeSort(0, nums.length - 1);
    return count;
}

// 9. Lazy Propagation Segment Tree
class LazySegmentTree {
    constructor(n) {
        this.n = n;
        this.tree = new Array(4 * n).fill(0);
        this.lazy = new Array(4 * n).fill(0);
    }
    
    push(node, start, end) {
        if (this.lazy[node] !== 0) {
            this.tree[node] += this.lazy[node] * (end - start + 1);
            if (start !== end) {
                this.lazy[2 * node + 1] += this.lazy[node];
                this.lazy[2 * node + 2] += this.lazy[node];
            }
            this.lazy[node] = 0;
        }
    }
    
    updateRange(l, r, val) {
        this.updateRangeHelper(0, 0, this.n - 1, l, r, val);
    }
    
    updateRangeHelper(node, start, end, l, r, val) {
        this.push(node, start, end);
        if (start > r || end < l) return;
        
        if (start >= l && end <= r) {
            this.lazy[node] += val;
            this.push(node, start, end);
            return;
        }
        
        const mid = Math.floor((start + end) / 2);
        this.updateRangeHelper(2 * node + 1, start, mid, l, r, val);
        this.updateRangeHelper(2 * node + 2, mid + 1, end, l, r, val);
        
        this.push(2 * node + 1, start, mid);
        this.push(2 * node + 2, mid + 1, end);
        this.tree[node] = this.tree[2 * node + 1] + this.tree[2 * node + 2];
    }
    
    query(l, r) {
        return this.queryHelper(0, 0, this.n - 1, l, r);
    }
    
    queryHelper(node, start, end, l, r) {
        if (start > r || end < l) return 0;
        this.push(node, start, end);
        
        if (start >= l && end <= r) return this.tree[node];
        
        const mid = Math.floor((start + end) / 2);
        return this.queryHelper(2 * node + 1, start, mid, l, r) + 
               this.queryHelper(2 * node + 2, mid + 1, end, l, r);
    }
}

// 10. Rectangle Area II
function rectangleArea(rectangles) {
    const MOD = 1000000007;
    const events = [];
    
    for (const [x1, y1, x2, y2] of rectangles) {
        events.push([x1, y1, y2, 1]); // Start
        events.push([x2, y1, y2, -1]); // End
    }
    
    events.sort((a, b) => a[0] - b[0]);
    
    let area = 0;
    let prevX = 0;
    const active = [];
    
    for (const [x, y1, y2, type] of events) {
        area = (area + (x - prevX) * getActiveHeight(active)) % MOD;
        
        if (type === 1) {
            active.push([y1, y2]);
        } else {
            const index = active.findIndex(([start, end]) => start === y1 && end === y2);
            active.splice(index, 1);
        }
        
        prevX = x;
    }
    
    return area;
    
    function getActiveHeight(intervals) {
        if (intervals.length === 0) return 0;
        
        intervals.sort((a, b) => a[0] - b[0]);
        let height = 0;
        let currentEnd = -1;
        
        for (const [start, end] of intervals) {
            if (start > currentEnd) {
                height += end - start;
                currentEnd = end;
            } else if (end > currentEnd) {
                height += end - currentEnd;
                currentEnd = end;
            }
        }
        
        return height;
    }
}

// Test cases
console.log("=== Segment Tree Examples ===");

const numArray = new NumArray([1, 3, 5]);
console.log("1. Sum Range(0, 2):", numArray.sumRange(0, 2));
numArray.update(1, 2);
console.log("   Sum Range(0, 2) after update:", numArray.sumRange(0, 2));

const rmq = new RMQSegmentTree([1, 3, 2, 7, 9, 11]);
console.log("2. Range Min Query(1, 3):", rmq.query(1, 3));

console.log("3. Count Smaller([5,2,6,1]):", countSmaller([5,2,6,1]));

const numMatrix = new NumMatrix([[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]);
console.log("4. 2D Range Sum(2,1,4,3):", numMatrix.sumRegion(2, 1, 4, 3));

const calendar = new MyCalendarThree();
console.log("5. Calendar book(10, 20):", calendar.book(10, 20));
console.log("   Calendar book(50, 60):", calendar.book(50, 60));
console.log("   Calendar book(10, 40):", calendar.book(10, 40));

console.log("6. Falling Squares([[1,2],[2,3],[6,1]]):", fallingSquares([[1,2],[2,3],[6,1]]));

const buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]];
console.log("7. Skyline:", getSkyline(buildings));

console.log("8. Reverse Pairs([1,3,2,3,1]):", reversePairs([1,3,2,3,1]));

const lazyTree = new LazySegmentTree(5);
lazyTree.updateRange(1, 3, 5);
console.log("9. Lazy Segment Tree updated range [1,3] with +5");

console.log("10. Rectangle Area([[0,0,2,2],[1,0,2,3],[1,0,3,1]]):", rectangleArea([[0,0,2,2],[1,0,2,3],[1,0,3,1]]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        SegmentTree,
        NumArray,
        RMQSegmentTree,
        countSmaller,
        NumMatrix,
        MyCalendarThree,
        fallingSquares,
        getSkyline,
        reversePairs,
        LazySegmentTree,
        rectangleArea
    };
}