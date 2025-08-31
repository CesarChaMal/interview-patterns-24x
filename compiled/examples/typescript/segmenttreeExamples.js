// 1. Range Sum Query - Mutable
class NumArray {
    constructor(nums) {
        this.n = nums.length;
        this.tree = new Array(2 * this.n);
        for (let i = 0; i < this.n; i++)
            this.tree[this.n + i] = nums[i];
        for (let i = this.n - 1; i > 0; i--)
            this.tree[i] = this.tree[2 * i] + this.tree[2 * i + 1];
    }
    update(index, val) {
        index += this.n;
        this.tree[index] = val;
        while (index > 0) {
            this.tree[Math.floor(index / 2)] = this.tree[index] + this.tree[index ^ 1];
            index = Math.floor(index / 2);
        }
    }
    sumRange(left, right) {
        left += this.n;
        right += this.n;
        let sum = 0;
        while (left <= right) {
            if (left % 2 === 1)
                sum += this.tree[left++];
            if (right % 2 === 0)
                sum += this.tree[right--];
            left = Math.floor(left / 2);
            right = Math.floor(right / 2);
        }
        return sum;
    }
}
// 2. Count of Smaller Numbers After Self
function countSmaller(nums) {
    const n = nums.length;
    const result = new Array(n).fill(0);
    const indices = Array.from({ length: n }, (_, i) => i);
    const mergeSort = (left, right) => {
        if (left >= right)
            return;
        const mid = left + Math.floor((right - left) / 2);
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        merge(left, mid, right);
    };
    const merge = (left, mid, right) => {
        const temp = new Array(right - left + 1);
        let i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (nums[indices[i]] <= nums[indices[j]]) {
                result[indices[i]] += j - mid - 1;
                temp[k++] = indices[i++];
            }
            else {
                temp[k++] = indices[j++];
            }
        }
        while (i <= mid) {
            result[indices[i]] += j - mid - 1;
            temp[k++] = indices[i++];
        }
        while (j <= right)
            temp[k++] = indices[j++];
        for (i = left; i <= right; i++)
            indices[i] = temp[i - left];
    };
    mergeSort(0, n - 1);
    return result;
}
// 3. Range Module
class RangeModule {
    constructor() {
        this.ranges = new Map();
    }
    addRange(left, right) {
        let start = left;
        let end = right;
        for (const [l, r] of this.ranges) {
            if (r >= left && l <= right) {
                start = Math.min(start, l);
                end = Math.max(end, r);
                this.ranges.delete(l);
            }
        }
        this.ranges.set(start, end);
    }
    queryRange(left, right) {
        for (const [l, r] of this.ranges) {
            if (l <= left && r >= right)
                return true;
        }
        return false;
    }
    removeRange(left, right) {
        const toAdd = [];
        for (const [l, r] of this.ranges) {
            if (r > left && l < right) {
                this.ranges.delete(l);
                if (l < left)
                    toAdd.push([l, left]);
                if (r > right)
                    toAdd.push([right, r]);
            }
        }
        for (const [l, r] of toAdd) {
            this.ranges.set(l, r);
        }
    }
}
// 4. My Calendar III
class MyCalendarThree {
    constructor() {
        this.delta = new Map();
    }
    book(start, end) {
        this.delta.set(start, (this.delta.get(start) || 0) + 1);
        this.delta.set(end, (this.delta.get(end) || 0) - 1);
        let active = 0;
        let ans = 0;
        const sortedKeys = Array.from(this.delta.keys()).sort((a, b) => a - b);
        for (const key of sortedKeys) {
            active += this.delta.get(key);
            ans = Math.max(ans, active);
        }
        return ans;
    }
}
// 5. Count of Range Sum
function countRangeSum(nums, lower, upper) {
    const prefixSums = new Array(nums.length + 1).fill(0);
    for (let i = 0; i < nums.length; i++) {
        prefixSums[i + 1] = prefixSums[i] + nums[i];
    }
    const mergeSort = (left, right) => {
        if (left >= right)
            return 0;
        const mid = left + Math.floor((right - left) / 2);
        let count = mergeSort(left, mid) + mergeSort(mid + 1, right);
        let j = mid + 1, k = mid + 1;
        for (let i = left; i <= mid; i++) {
            while (j <= right && prefixSums[j] - prefixSums[i] < lower)
                j++;
            while (k <= right && prefixSums[k] - prefixSums[i] <= upper)
                k++;
            count += k - j;
        }
        prefixSums.slice(left, right + 1).sort((a, b) => a - b).forEach((val, idx) => {
            prefixSums[left + idx] = val;
        });
        return count;
    };
    return mergeSort(0, prefixSums.length - 1);
}
// 6. Reverse Pairs
function reversePairs(nums) {
    const mergeSort = (left, right) => {
        if (left >= right)
            return 0;
        const mid = left + Math.floor((right - left) / 2);
        let count = mergeSort(left, mid) + mergeSort(mid + 1, right);
        let j = mid + 1;
        for (let i = left; i <= mid; i++) {
            while (j <= right && nums[i] > 2 * nums[j])
                j++;
            count += j - (mid + 1);
        }
        nums.slice(left, right + 1).sort((a, b) => a - b).forEach((val, idx) => {
            nums[left + idx] = val;
        });
        return count;
    };
    return mergeSort(0, nums.length - 1);
}
// 7. Rectangle Area II
function rectangleArea(rectangles) {
    const MOD = 1000000007;
    const xSet = new Set();
    const ySet = new Set();
    for (const rect of rectangles) {
        xSet.add(rect[0]);
        xSet.add(rect[2]);
        ySet.add(rect[1]);
        ySet.add(rect[3]);
    }
    const xs = Array.from(xSet).sort((a, b) => a - b);
    const ys = Array.from(ySet).sort((a, b) => a - b);
    const xMap = new Map(xs.map((x, i) => [x, i]));
    const yMap = new Map(ys.map((y, i) => [y, i]));
    const grid = Array.from({ length: xs.length }, () => new Array(ys.length).fill(false));
    for (const rect of rectangles) {
        for (let x = xMap.get(rect[0]); x < xMap.get(rect[2]); x++) {
            for (let y = yMap.get(rect[1]); y < yMap.get(rect[3]); y++) {
                grid[x][y] = true;
            }
        }
    }
    let area = 0;
    for (let x = 0; x < xs.length - 1; x++) {
        for (let y = 0; y < ys.length - 1; y++) {
            if (grid[x][y]) {
                area += (xs[x + 1] - xs[x]) * (ys[y + 1] - ys[y]);
                area %= MOD;
            }
        }
    }
    return area;
}
// 8. Number of Longest Increasing Subsequence
function findNumberOfLIS(nums) {
    const n = nums.length;
    if (n === 0)
        return 0;
    const lengths = new Array(n).fill(1);
    const counts = new Array(n).fill(1);
    for (let i = 1; i < n; i++) {
        for (let j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                if (lengths[j] + 1 > lengths[i]) {
                    lengths[i] = lengths[j] + 1;
                    counts[i] = counts[j];
                }
                else if (lengths[j] + 1 === lengths[i]) {
                    counts[i] += counts[j];
                }
            }
        }
    }
    const maxLen = Math.max(...lengths);
    return lengths.reduce((sum, len, i) => len === maxLen ? sum + counts[i] : sum, 0);
}
// 9. Range Sum Query 2D - Mutable
class NumMatrix {
    constructor(matrix) {
        this.m = matrix.length;
        this.n = matrix.length > 0 ? matrix[0].length : 0;
        this.tree = Array.from({ length: this.m + 1 }, () => new Array(this.n + 1).fill(0));
        this.nums = Array.from({ length: this.m }, () => new Array(this.n).fill(0));
        for (let i = 0; i < this.m; i++) {
            for (let j = 0; j < this.n; j++) {
                this.update(i, j, matrix[i][j]);
            }
        }
    }
    update(row, col, val) {
        const delta = val - this.nums[row][col];
        this.nums[row][col] = val;
        for (let i = row + 1; i <= this.m; i += i & (-i)) {
            for (let j = col + 1; j <= this.n; j += j & (-j)) {
                this.tree[i][j] += delta;
            }
        }
    }
    sumRegion(row1, col1, row2, col2) {
        return this.sum(row2 + 1, col2 + 1) + this.sum(row1, col1) -
            this.sum(row1, col2 + 1) - this.sum(row2 + 1, col1);
    }
    sum(row, col) {
        let sum = 0;
        for (let i = row; i > 0; i -= i & (-i)) {
            for (let j = col; j > 0; j -= j & (-j)) {
                sum += this.tree[i][j];
            }
        }
        return sum;
    }
}
// 10. Falling Squares
function fallingSquares(positions) {
    const result = [];
    const intervals = [];
    for (const pos of positions) {
        const left = pos[0];
        const size = pos[1];
        const right = left + size;
        let height = size;
        for (const interval of intervals) {
            if (interval[1] > left && interval[0] < right) {
                height = Math.max(height, interval[2] + size);
            }
        }
        intervals.push([left, right, height]);
        result.push(Math.max(...intervals.map(i => i[2])));
    }
    return result;
}
// Test cases
console.log("=== Segment Tree Examples ===");
// Test 1: Range Sum Query - Mutable
const numArray = new NumArray([1, 3, 5]);
console.log("1. Range Sum Query [1,3,5] sum(0,2):", numArray.sumRange(0, 2));
// Test 2: Count of Smaller Numbers After Self
console.log("2. Count Smaller [5,2,6,1]:", countSmaller([5, 2, 6, 1]));
// Test 3: Range Module
const rm = new RangeModule();
rm.addRange(10, 20);
rm.removeRange(14, 16);
console.log("3. Range Module query(10,14):", rm.queryRange(10, 14));
// Test 4: My Calendar III
const cal = new MyCalendarThree();
console.log("4. Calendar III book(10,20):", cal.book(10, 20));
// Test 5: Count of Range Sum
console.log("5. Count Range Sum [-2,5,-1]:", countRangeSum([-2, 5, -1], -2, 2));
// Test 6: Reverse Pairs
console.log("6. Reverse Pairs [1,3,2,3,1]:", reversePairs([1, 3, 2, 3, 1]));
// Test 7: Rectangle Area II
console.log("7. Rectangle Area II:", rectangleArea([[0, 0, 2, 2], [1, 0, 2, 3], [1, 0, 3, 1]]));
// Test 8: Number of Longest Increasing Subsequence
console.log("8. Number of LIS [1,3,5,4,7]:", findNumberOfLIS([1, 3, 5, 4, 7]));
// Test 9: Range Sum Query 2D - Mutable
const numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1]]);
console.log("9. 2D Range Sum (0,1,0,2):", numMatrix.sumRegion(0, 1, 0, 2));
// Test 10: Falling Squares
console.log("10. Falling Squares [[1,2],[2,3],[6,1]]:", fallingSquares([[1, 2], [2, 3], [6, 1]]));
