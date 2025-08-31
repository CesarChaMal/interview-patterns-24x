// Simple MinHeap implementation
class MinHeap {
    constructor() {
        this.heap = [];
    }
    
    push(val) {
        this.heap.push(val);
        this.heapifyUp(this.heap.length - 1);
    }
    
    pop() {
        if (this.heap.length === 0) return undefined;
        if (this.heap.length === 1) return this.heap.pop();
        
        const root = this.heap[0];
        this.heap[0] = this.heap.pop();
        this.heapifyDown(0);
        return root;
    }
    
    peek() {
        return this.heap[0];
    }
    
    size() {
        return this.heap.length;
    }
    
    heapifyUp(index) {
        while (index > 0) {
            const parentIndex = Math.floor((index - 1) / 2);
            if (this.heap[parentIndex] <= this.heap[index]) break;
            [this.heap[parentIndex], this.heap[index]] = [this.heap[index], this.heap[parentIndex]];
            index = parentIndex;
        }
    }
    
    heapifyDown(index) {
        while (true) {
            let smallest = index;
            const leftChild = 2 * index + 1;
            const rightChild = 2 * index + 2;
            
            if (leftChild < this.heap.length && this.heap[leftChild] < this.heap[smallest]) {
                smallest = leftChild;
            }
            if (rightChild < this.heap.length && this.heap[rightChild] < this.heap[smallest]) {
                smallest = rightChild;
            }
            if (smallest === index) break;
            
            [this.heap[index], this.heap[smallest]] = [this.heap[smallest], this.heap[index]];
            index = smallest;
        }
    }
}

// 1. Kth Largest Element
function findKthLargest(nums, k) {
    const minHeap = new MinHeap();
    for (const num of nums) {
        minHeap.push(num);
        if (minHeap.size() > k) {
            minHeap.pop();
        }
    }
    return minHeap.peek();
}

// 2. Top K Frequent Elements
function topKFrequent(nums, k) {
    const count = new Map();
    for (const num of nums) {
        count.set(num, (count.get(num) || 0) + 1);
    }
    
    const minHeap = new MinHeap();
    for (const [num, freq] of count) {
        minHeap.push([freq, num]);
        if (minHeap.size() > k) {
            minHeap.pop();
        }
    }
    
    return minHeap.heap.map(([freq, num]) => num);
}

// 3. Merge k Sorted Lists
function mergeKLists(lists) {
    if (!lists || lists.length === 0) return null;
    
    const minHeap = new MinHeap();
    for (let i = 0; i < lists.length; i++) {
        if (lists[i]) {
            minHeap.push([lists[i].val, i, lists[i]]);
        }
    }
    
    const dummy = { next: null };
    let current = dummy;
    
    while (minHeap.size() > 0) {
        const [val, listIndex, node] = minHeap.pop();
        current.next = node;
        current = current.next;
        
        if (node.next) {
            minHeap.push([node.next.val, listIndex, node.next]);
        }
    }
    
    return dummy.next;
}

// 4. Find Median from Data Stream
class MedianFinder {
    constructor() {
        this.maxHeap = []; // For smaller half
        this.minHeap = new MinHeap(); // For larger half
    }
    
    addNum(num) {
        if (this.maxHeap.length === 0 || num <= -this.maxHeap[0]) {
            this.maxHeap.push(-num);
            this.maxHeapifyUp(this.maxHeap.length - 1);
        } else {
            this.minHeap.push(num);
        }
        
        // Balance heaps
        if (this.maxHeap.length > this.minHeap.size() + 1) {
            this.minHeap.push(-this.maxHeap[0]);
            this.maxHeap[0] = this.maxHeap.pop();
            if (this.maxHeap.length > 0) this.maxHeapifyDown(0);
        } else if (this.minHeap.size() > this.maxHeap.length + 1) {
            this.maxHeap.push(-this.minHeap.pop());
            this.maxHeapifyUp(this.maxHeap.length - 1);
        }
    }
    
    findMedian() {
        if (this.maxHeap.length === this.minHeap.size()) {
            return (-this.maxHeap[0] + this.minHeap.peek()) / 2;
        }
        return this.maxHeap.length > this.minHeap.size() ? -this.maxHeap[0] : this.minHeap.peek();
    }
    
    maxHeapifyUp(index) {
        while (index > 0) {
            const parentIndex = Math.floor((index - 1) / 2);
            if (this.maxHeap[parentIndex] >= this.maxHeap[index]) break;
            [this.maxHeap[parentIndex], this.maxHeap[index]] = [this.maxHeap[index], this.maxHeap[parentIndex]];
            index = parentIndex;
        }
    }
    
    maxHeapifyDown(index) {
        while (true) {
            let largest = index;
            const leftChild = 2 * index + 1;
            const rightChild = 2 * index + 2;
            
            if (leftChild < this.maxHeap.length && this.maxHeap[leftChild] > this.maxHeap[largest]) {
                largest = leftChild;
            }
            if (rightChild < this.maxHeap.length && this.maxHeap[rightChild] > this.maxHeap[largest]) {
                largest = rightChild;
            }
            if (largest === index) break;
            
            [this.maxHeap[index], this.maxHeap[largest]] = [this.maxHeap[largest], this.maxHeap[index]];
            index = largest;
        }
    }
}

// 5. K Closest Points to Origin
function kClosest(points, k) {
    const maxHeap = [];
    
    for (const [x, y] of points) {
        const dist = x * x + y * y;
        maxHeap.push([dist, [x, y]]);
        maxHeap.sort((a, b) => b[0] - a[0]);
        
        if (maxHeap.length > k) {
            maxHeap.shift();
        }
    }
    
    return maxHeap.map(([dist, point]) => point);
}

// 6. Kth Smallest Element in a Sorted Matrix
function kthSmallest(matrix, k) {
    const n = matrix.length;
    const minHeap = new MinHeap();
    
    // Add first row to heap
    for (let j = 0; j < n; j++) {
        minHeap.push([matrix[0][j], 0, j]);
    }
    
    for (let i = 0; i < k - 1; i++) {
        const [val, row, col] = minHeap.pop();
        
        if (row + 1 < n) {
            minHeap.push([matrix[row + 1][col], row + 1, col]);
        }
    }
    
    return minHeap.peek()[0];
}

// 7. Last Stone Weight
function lastStoneWeight(stones) {
    const maxHeap = [...stones];
    maxHeap.sort((a, b) => b - a);
    
    while (maxHeap.length > 1) {
        const first = maxHeap.shift();
        const second = maxHeap.shift();
        
        if (first !== second) {
            const diff = first - second;
            let inserted = false;
            
            for (let i = 0; i < maxHeap.length; i++) {
                if (diff > maxHeap[i]) {
                    maxHeap.splice(i, 0, diff);
                    inserted = true;
                    break;
                }
            }
            
            if (!inserted) {
                maxHeap.push(diff);
            }
        }
    }
    
    return maxHeap.length === 0 ? 0 : maxHeap[0];
}

// 8. Task Scheduler
function leastInterval(tasks, n) {
    const count = new Map();
    for (const task of tasks) {
        count.set(task, (count.get(task) || 0) + 1);
    }
    
    const maxHeap = [...count.values()].sort((a, b) => b - a);
    let time = 0;
    
    while (maxHeap.length > 0) {
        const temp = [];
        
        for (let i = 0; i <= n; i++) {
            if (maxHeap.length > 0) {
                temp.push(maxHeap.shift());
            }
        }
        
        for (const freq of temp) {
            if (freq - 1 > 0) {
                maxHeap.push(freq - 1);
            }
        }
        
        maxHeap.sort((a, b) => b - a);
        time += maxHeap.length > 0 ? n + 1 : temp.length;
    }
    
    return time;
}

// 9. Ugly Number II
function nthUglyNumber(n) {
    const minHeap = new MinHeap();
    const seen = new Set();
    const factors = [2, 3, 5];
    
    minHeap.push(1);
    seen.add(1);
    
    let ugly = 1;
    for (let i = 0; i < n; i++) {
        ugly = minHeap.pop();
        
        for (const factor of factors) {
            const next = ugly * factor;
            if (!seen.has(next)) {
                seen.add(next);
                minHeap.push(next);
            }
        }
    }
    
    return ugly;
}

// 10. Meeting Rooms II
function minMeetingRooms(intervals) {
    if (intervals.length === 0) return 0;
    
    intervals.sort((a, b) => a[0] - b[0]);
    const minHeap = new MinHeap();
    
    for (const [start, end] of intervals) {
        if (minHeap.size() > 0 && minHeap.peek() <= start) {
            minHeap.pop();
        }
        minHeap.push(end);
    }
    
    return minHeap.size();
}

// Test cases
console.log("=== Heap Examples ===");

console.log("1. Kth Largest([3,2,1,5,6,4], 2):", findKthLargest([3,2,1,5,6,4], 2));
console.log("2. Top K Frequent([1,1,1,2,2,3], 2):", topKFrequent([1,1,1,2,2,3], 2));

const medianFinder = new MedianFinder();
medianFinder.addNum(1);
medianFinder.addNum(2);
console.log("3. Median after adding 1,2:", medianFinder.findMedian());
medianFinder.addNum(3);
console.log("   Median after adding 3:", medianFinder.findMedian());

console.log("4. K Closest Points(k=1):", kClosest([[-2,2]], 1));
console.log("5. Kth Smallest in Matrix(k=8):", kthSmallest([[1,5,9],[10,11,13],[12,13,15]], 8));
console.log("6. Last Stone Weight([2,7,4,1,8,1]):", lastStoneWeight([2,7,4,1,8,1]));
console.log("7. Least Interval(['A','A','A','B','B','B'], n=2):", leastInterval(['A','A','A','B','B','B'], 2));
console.log("8. 10th Ugly Number:", nthUglyNumber(10));
console.log("9. Min Meeting Rooms([[0,30],[5,10],[15,20]]):", minMeetingRooms([[0,30],[5,10],[15,20]]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        MinHeap,
        findKthLargest,
        topKFrequent,
        mergeKLists,
        MedianFinder,
        kClosest,
        kthSmallest,
        lastStoneWeight,
        leastInterval,
        nthUglyNumber,
        minMeetingRooms
    };
}