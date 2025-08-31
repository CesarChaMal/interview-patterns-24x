// Fenwick Tree (Binary Indexed Tree) implementation
class FenwickTree {
    constructor(n) {
        this.n = n;
        this.tree = new Array(n + 1).fill(0);
    }
    
    update(i, delta) {
        for (++i; i <= this.n; i += i & -i) {
            this.tree[i] += delta;
        }
    }
    
    query(i) {
        let sum = 0;
        for (++i; i > 0; i -= i & -i) {
            sum += this.tree[i];
        }
        return sum;
    }
    
    rangeQuery(left, right) {
        return this.query(right) - (left > 0 ? this.query(left - 1) : 0);
    }
}

// 1. Range Sum Query - Mutable
class NumArray {
    constructor(nums) {
        this.nums = [...nums];
        this.fenwick = new FenwickTree(nums.length);
        
        for (let i = 0; i < nums.length; i++) {
            this.fenwick.update(i, nums[i]);
        }
    }
    
    update(index, val) {
        const delta = val - this.nums[index];
        this.nums[index] = val;
        this.fenwick.update(index, delta);
    }
    
    sumRange(left, right) {
        return this.fenwick.rangeQuery(left, right);
    }
}

// 2. Count of Smaller Numbers After Self
function countSmaller(nums) {
    const sorted = [...new Set(nums)].sort((a, b) => a - b);
    const compress = new Map();
    sorted.forEach((num, i) => compress.set(num, i + 1));
    
    const fenwick = new FenwickTree(sorted.length);
    const result = [];
    
    for (let i = nums.length - 1; i >= 0; i--) {
        const rank = compress.get(nums[i]);
        result[i] = fenwick.query(rank - 2);
        fenwick.update(rank - 1, 1);
    }
    
    return result;
}

// 3. Range Sum Query 2D - Mutable
class NumMatrix {
    constructor(matrix) {
        if (!matrix || matrix.length === 0) return;
        
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.matrix = Array.from({ length: this.m }, () => new Array(this.n).fill(0));
        this.fenwick = Array.from({ length: this.m + 1 }, () => new Array(this.n + 1).fill(0));
        
        for (let i = 0; i < this.m; i++) {
            for (let j = 0; j < this.n; j++) {
                this.update(i, j, matrix[i][j]);
            }
        }
    }
    
    update(row, col, val) {
        const delta = val - this.matrix[row][col];
        this.matrix[row][col] = val;
        
        for (let i = row + 1; i <= this.m; i += i & -i) {
            for (let j = col + 1; j <= this.n; j += j & -j) {
                this.fenwick[i][j] += delta;
            }
        }
    }
    
    sumRegion(row1, col1, row2, col2) {
        return this.query(row2, col2) - this.query(row1 - 1, col2) - 
               this.query(row2, col1 - 1) + this.query(row1 - 1, col1 - 1);
    }
    
    query(row, col) {
        let sum = 0;
        for (let i = row + 1; i > 0; i -= i & -i) {
            for (let j = col + 1; j > 0; j -= j & -j) {
                sum += this.fenwick[i][j];
            }
        }
        return sum;
    }
}

// 4. Reverse Pairs
function reversePairs(nums) {
    const sorted = [...nums].sort((a, b) => a - b);
    const compress = new Map();
    sorted.forEach((num, i) => compress.set(num, i + 1));
    
    const fenwick = new FenwickTree(nums.length);
    let count = 0;
    
    for (let i = nums.length - 1; i >= 0; i--) {
        // Find position of nums[i] * 2 + 1 in sorted array
        let left = 0, right = sorted.length - 1;
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            if (sorted[mid] < nums[i] * 2) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        count += fenwick.query(right);
        fenwick.update(compress.get(nums[i]) - 1, 1);
    }
    
    return count;
}

// 5. Count of Range Sum
function countRangeSum(nums, lower, upper) {
    const prefixSums = [0];
    for (const num of nums) {
        prefixSums.push(prefixSums[prefixSums.length - 1] + num);
    }
    
    const sorted = [...prefixSums].sort((a, b) => a - b);
    const compress = new Map();
    sorted.forEach((sum, i) => compress.set(sum, i + 1));
    
    const fenwick = new FenwickTree(sorted.length);
    let count = 0;
    
    for (const sum of prefixSums) {
        // Count prefix sums in range [sum - upper, sum - lower]
        const leftBound = sum - upper;
        const rightBound = sum - lower;
        
        let left = 0, right = sorted.length - 1;
        let leftIdx = -1, rightIdx = -1;
        
        // Find leftmost position >= leftBound
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            if (sorted[mid] >= leftBound) {
                leftIdx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        // Find rightmost position <= rightBound
        left = 0;
        right = sorted.length - 1;
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            if (sorted[mid] <= rightBound) {
                rightIdx = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        if (leftIdx !== -1 && rightIdx !== -1 && leftIdx <= rightIdx) {
            count += fenwick.rangeQuery(leftIdx, rightIdx);
        }
        
        fenwick.update(compress.get(sum) - 1, 1);
    }
    
    return count;
}

// 6. Create Sorted Array through Instructions
function createSortedArray(instructions) {
    const MOD = 1000000007;
    const sorted = [...new Set(instructions)].sort((a, b) => a - b);
    const compress = new Map();
    sorted.forEach((num, i) => compress.set(num, i + 1));
    
    const fenwick = new FenwickTree(sorted.length);
    let cost = 0;
    
    for (let i = 0; i < instructions.length; i++) {
        const rank = compress.get(instructions[i]);
        const smaller = fenwick.query(rank - 2);
        const larger = i - fenwick.query(rank - 1);
        
        cost = (cost + Math.min(smaller, larger)) % MOD;
        fenwick.update(rank - 1, 1);
    }
    
    return cost;
}

// 7. Kth Smallest Element in a Sorted Matrix
function kthSmallest(matrix, k) {
    const n = matrix.length;
    let left = matrix[0][0];
    let right = matrix[n - 1][n - 1];
    
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        let count = 0;
        let j = n - 1;
        
        for (let i = 0; i < n; i++) {
            while (j >= 0 && matrix[i][j] > mid) {
                j--;
            }
            count += j + 1;
        }
        
        if (count < k) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    
    return left;
}

// 8. Range Add Query
function getModifiedArray(length, updates) {
    const fenwick = new FenwickTree(length);
    
    for (const [start, end, inc] of updates) {
        fenwick.update(start, inc);
        if (end + 1 < length) {
            fenwick.update(end + 1, -inc);
        }
    }
    
    const result = [];
    for (let i = 0; i < length; i++) {
        result.push(fenwick.query(i));
    }
    
    return result;
}

// 9. 2D Fenwick Tree Query
function query2D(matrix, row, col) {
    const m = matrix.length;
    const n = matrix[0].length;
    const fenwick2D = new NumMatrix(matrix);
    
    return fenwick2D.sumRegion(0, 0, row, col);
}

// 10. Max Sum Rectangle No Larger Than K
function maxSumSubmatrix(matrix, k) {
    const m = matrix.length;
    const n = matrix[0].length;
    let maxSum = -Infinity;
    
    for (let left = 0; left < n; left++) {
        const temp = new Array(m).fill(0);
        
        for (let right = left; right < n; right++) {
            for (let i = 0; i < m; i++) {
                temp[i] += matrix[i][right];
            }
            
            // Find max subarray sum <= k
            const prefixSums = [0];
            let sum = 0;
            
            for (const num of temp) {
                sum += num;
                
                // Binary search for the largest prefix sum such that sum - prefix <= k
                let left = 0, right = prefixSums.length - 1;
                let target = sum - k;
                
                while (left <= right) {
                    const mid = Math.floor((left + right) / 2);
                    if (prefixSums[mid] >= target) {
                        maxSum = Math.max(maxSum, sum - prefixSums[mid]);
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                
                // Insert sum in sorted order
                let insertPos = 0;
                while (insertPos < prefixSums.length && prefixSums[insertPos] < sum) {
                    insertPos++;
                }
                prefixSums.splice(insertPos, 0, sum);
            }
        }
    }
    
    return maxSum;
}

// Test cases
console.log("=== Fenwick Tree Examples ===");

const numArray = new NumArray([1, 3, 5]);
console.log("1. Sum Range(0, 2):", numArray.sumRange(0, 2));
numArray.update(1, 2);
console.log("   Sum Range(0, 2) after update:", numArray.sumRange(0, 2));

console.log("2. Count Smaller([5,2,6,1]):", countSmaller([5,2,6,1]));

const matrix = [[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]];
const numMatrix = new NumMatrix(matrix);
console.log("3. Sum Region(2,1,4,3):", numMatrix.sumRegion(2, 1, 4, 3));
numMatrix.update(3, 2, 2);
console.log("   Sum Region(2,1,4,3) after update:", numMatrix.sumRegion(2, 1, 4, 3));

console.log("4. Reverse Pairs([1,3,2,3,1]):", reversePairs([1,3,2,3,1]));

console.log("5. Count Range Sum([-2,5,-1], -2, 2):", countRangeSum([-2,5,-1], -2, 2));

console.log("6. Create Sorted Array([1,5,6,2]):", createSortedArray([1,5,6,2]));

const sortedMatrix = [[1,5,9],[10,11,13],[12,13,15]];
console.log("7. Kth Smallest(k=8):", kthSmallest(sortedMatrix, 8));

console.log("8. Range Add Query([5, [[1,3,2],[2,4,3],[0,2,-2]]]):", getModifiedArray(5, [[1,3,2],[2,4,3],[0,2,-2]]));

console.log("9. 2D Fenwick Query(2,2):", query2D([[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5]], 2, 2));

console.log("10. Max Sum Rectangle <= 2:", maxSumSubmatrix([[1,0,1],[0,-2,3]], 2));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        FenwickTree,
        NumArray,
        countSmaller,
        NumMatrix,
        reversePairs,
        countRangeSum,
        createSortedArray,
        kthSmallest,
        getModifiedArray,
        query2D,
        maxSumSubmatrix
    };
}