"use strict";
class FenwickTree {
    constructor(n) {
        this.tree = new Array(n + 1).fill(0);
    }
    update(i, delta) {
        let idx = i + 1;
        while (idx < this.tree.length) {
            this.tree[idx] += delta;
            idx += idx & (-idx);
        }
    }
    query(i) {
        let sum = 0;
        let idx = i + 1;
        while (idx > 0) {
            sum += this.tree[idx];
            idx -= idx & (-idx);
        }
        return sum;
    }
    rangeQuery(l, r) {
        return this.query(r) - (l > 0 ? this.query(l - 1) : 0);
    }
}
// 1. Range Sum Query - Mutable
class NumArray {
    constructor(nums) {
        this.ft = new FenwickTree(nums.length);
        this.arr = [...nums];
        for (let i = 0; i < nums.length; i++) {
            this.ft.update(i, nums[i]);
        }
    }
    update(index, val) {
        this.ft.update(index, val - this.arr[index]);
        this.arr[index] = val;
    }
    sumRange(left, right) {
        return this.ft.rangeQuery(left, right);
    }
}
// 2. Count of Smaller Numbers After Self
function countSmaller(nums) {
    const sorted = [...new Set(nums)].sort((a, b) => a - b);
    const ranks = new Map(sorted.map((val, idx) => [val, idx]));
    const ft = new FenwickTree(sorted.length);
    const result = new Array(nums.length);
    for (let i = nums.length - 1; i >= 0; i--) {
        const rank = ranks.get(nums[i]);
        result[i] = rank > 0 ? ft.query(rank - 1) : 0;
        ft.update(rank, 1);
    }
    return result;
}
// 3. Count of Range Sum
function countRangeSum(nums, lower, upper) {
    const prefixSums = new Array(nums.length + 1).fill(0);
    for (let i = 0; i < nums.length; i++) {
        prefixSums[i + 1] = prefixSums[i] + nums[i];
    }
    const allSums = new Set();
    for (const sum of prefixSums) {
        allSums.add(sum);
        allSums.add(sum - lower);
        allSums.add(sum - upper);
    }
    const sorted = Array.from(allSums).sort((a, b) => a - b);
    const ranks = new Map(sorted.map((val, idx) => [val, idx]));
    const ft = new FenwickTree(sorted.length);
    let count = 0;
    for (const sum of prefixSums) {
        const left = ranks.get(sum - upper);
        const right = ranks.get(sum - lower);
        count += ft.rangeQuery(left, right);
        ft.update(ranks.get(sum), 1);
    }
    return count;
}
// 4. Reverse Pairs
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
// 5. Count Number of Teams
function numTeamsInArray(rating) {
    const n = rating.length;
    let count = 0;
    for (let j = 1; j < n - 1; j++) {
        let leftSmaller = 0, leftLarger = 0;
        let rightSmaller = 0, rightLarger = 0;
        for (let i = 0; i < j; i++) {
            if (rating[i] < rating[j])
                leftSmaller++;
            else if (rating[i] > rating[j])
                leftLarger++;
        }
        for (let k = j + 1; k < n; k++) {
            if (rating[k] < rating[j])
                rightSmaller++;
            else if (rating[k] > rating[j])
                rightLarger++;
        }
        count += leftSmaller * rightLarger + leftLarger * rightSmaller;
    }
    return count;
}
// 6. Queries on a Permutation With Key
function processQueries(queries, m) {
    const ft = new FenwickTree(2 * m);
    const pos = new Array(m + 1);
    for (let i = 1; i <= m; i++) {
        pos[i] = m + i;
        ft.update(m + i, 1);
    }
    const result = new Array(queries.length);
    let nextPos = m;
    for (let i = 0; i < queries.length; i++) {
        const value = queries[i];
        result[i] = ft.query(pos[value] - 1);
        ft.update(pos[value], -1);
        pos[value] = nextPos;
        nextPos--;
        ft.update(pos[value], 1);
    }
    return result;
}
// 7. Create Sorted Array through Instructions
function createSortedArray(instructions) {
    const MOD = 1000000007;
    const maxVal = Math.max(...instructions);
    const ft = new FenwickTree(maxVal);
    let cost = 0;
    for (let i = 0; i < instructions.length; i++) {
        const value = instructions[i];
        const smaller = value > 1 ? ft.query(value - 2) : 0;
        const larger = ft.query(maxVal - 1) - ft.query(value - 1);
        cost = (cost + Math.min(smaller, larger)) % MOD;
        ft.update(value - 1, 1);
    }
    return cost;
}
// 8. Design Most Recently Used Queue
class MRUQueue {
    constructor(n) {
        this.values = new Map();
        this.ft = new FenwickTree(2 * n);
        this.nextPos = n + 1;
        for (let i = 1; i <= n; i++) {
            this.values.set(i, i);
            this.ft.update(i, 1);
        }
    }
    fetch(k) {
        const pos = this.findKth(k);
        const value = this.values.get(pos);
        this.ft.update(pos, -1);
        this.values.set(this.nextPos, value);
        this.ft.update(this.nextPos, 1);
        this.nextPos++;
        return value;
    }
    findKth(k) {
        let left = 1;
        let right = this.nextPos - 1;
        while (left < right) {
            const mid = left + Math.floor((right - left) / 2);
            if (this.ft.query(mid) < k)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }
}
// 9. Count Good Triplets in an Array
function goodTriplets(nums1, nums2) {
    const n = nums1.length;
    const pos = new Map(nums2.map((val, idx) => [val, idx]));
    const arr = nums1.map(val => pos.get(val));
    const leftFt = new FenwickTree(n);
    const rightFt = new FenwickTree(n);
    for (const value of arr)
        rightFt.update(value, 1);
    let count = 0;
    for (let i = 0; i < n; i++) {
        rightFt.update(arr[i], -1);
        const leftSmaller = arr[i] > 0 ? leftFt.query(arr[i] - 1) : 0;
        const rightLarger = rightFt.query(n - 1) - (arr[i] < n - 1 ? rightFt.query(arr[i]) : 0);
        count += leftSmaller * rightLarger;
        leftFt.update(arr[i], 1);
    }
    return count;
}
// 10. Minimum Possible Integer After at Most K Adjacent Swaps on Digits
function minInteger(num, k) {
    if (k === 0)
        return num;
    const chars = num.split('');
    const n = chars.length;
    const used = new Array(n).fill(false);
    const ft = new FenwickTree(n);
    for (let i = 0; i < n; i++)
        ft.update(i, 1);
    const result = [];
    let remainingK = k;
    for (let pos = 0; pos < n; pos++) {
        let found = false;
        for (let digit = 0; digit <= 9 && !found; digit++) {
            for (let i = 0; i < n && !found; i++) {
                if (!used[i] && parseInt(chars[i]) === digit) {
                    const cost = ft.rangeQuery(0, i - 1);
                    if (cost <= remainingK) {
                        result.push(chars[i]);
                        used[i] = true;
                        ft.update(i, -1);
                        remainingK -= cost;
                        found = true;
                    }
                }
            }
        }
    }
    return result.join('');
}
// Test cases
console.log("=== Fenwick Tree Examples ===");
// Test 1: Range Sum Query - Mutable
const numArray = new NumArray([1, 3, 5]);
console.log("1. Range Sum Query [1,3,5] sum(0,2):", numArray.sumRange(0, 2));
// Test 2: Count of Smaller Numbers After Self
console.log("2. Count Smaller [5,2,6,1]:", countSmaller([5, 2, 6, 1]));
// Test 3: Count of Range Sum
console.log("3. Count Range Sum [-2,5,-1]:", countRangeSum([-2, 5, -1], -2, 2));
// Test 4: Reverse Pairs
console.log("4. Reverse Pairs [1,3,2,3,1]:", reversePairs([1, 3, 2, 3, 1]));
// Test 5: Count Number of Teams
console.log("5. Count Teams [2,5,3,4,1]:", numTeamsInArray([2, 5, 3, 4, 1]));
// Test 6: Queries on a Permutation With Key
console.log("6. Process Queries [3,1,2,1]:", processQueries([3, 1, 2, 1], 5));
// Test 7: Create Sorted Array through Instructions
console.log("7. Create Sorted Array [1,5,6,2]:", createSortedArray([1, 5, 6, 2]));
// Test 8: Design Most Recently Used Queue
const mru = new MRUQueue(8);
console.log("8. MRU Queue fetch(3):", mru.fetch(3));
// Test 9: Count Good Triplets in an Array
const nums1 = [4, 0, 1, 3, 2];
const nums2 = [4, 1, 0, 2, 3];
console.log("9. Good Triplets:", goodTriplets(nums1, nums2));
// Test 10: Minimum Possible Integer After K Swaps
console.log("10. Min Integer '4321', k=4:", minInteger("4321", 4));
//# sourceMappingURL=fenwicktreeExamples.js.map