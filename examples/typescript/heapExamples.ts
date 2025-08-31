class MinHeap<T> {
    private heap: T[] = [];
    constructor(private compare: (a: T, b: T) => number) {}
    
    push(val: T): void {
        this.heap.push(val);
        this.bubbleUp(this.heap.length - 1);
    }
    
    pop(): T | undefined {
        if (this.heap.length === 0) return undefined;
        if (this.heap.length === 1) return this.heap.pop();
        
        const top = this.heap[0];
        this.heap[0] = this.heap.pop()!;
        this.bubbleDown(0);
        return top;
    }
    
    peek(): T | undefined {
        return this.heap[0];
    }
    
    size(): number {
        return this.heap.length;
    }
    
    private bubbleUp(index: number): void {
        while (index > 0) {
            const parentIndex = Math.floor((index - 1) / 2);
            if (this.compare(this.heap[index], this.heap[parentIndex]) >= 0) break;
            [this.heap[index], this.heap[parentIndex]] = [this.heap[parentIndex], this.heap[index]];
            index = parentIndex;
        }
    }
    
    private bubbleDown(index: number): void {
        while (true) {
            let minIndex = index;
            const leftChild = 2 * index + 1;
            const rightChild = 2 * index + 2;
            
            if (leftChild < this.heap.length && this.compare(this.heap[leftChild], this.heap[minIndex]) < 0) {
                minIndex = leftChild;
            }
            if (rightChild < this.heap.length && this.compare(this.heap[rightChild], this.heap[minIndex]) < 0) {
                minIndex = rightChild;
            }
            if (minIndex === index) break;
            
            [this.heap[index], this.heap[minIndex]] = [this.heap[minIndex], this.heap[index]];
            index = minIndex;
        }
    }
}

// 1. Kth Largest Element in an Array
function findKthLargest(nums: number[], k: number): number {
    const minHeap = new MinHeap<number>((a, b) => a - b);
    for (const num of nums) {
        minHeap.push(num);
        if (minHeap.size() > k) minHeap.pop();
    }
    return minHeap.peek()!;
}

// 2. Top K Frequent Elements
function topKFrequent(nums: number[], k: number): number[] {
    const count = new Map<number, number>();
    for (const num of nums) {
        count.set(num, (count.get(num) || 0) + 1);
    }
    
    const minHeap = new MinHeap<[number, number]>((a, b) => a[1] - b[1]);
    for (const [num, freq] of count) {
        minHeap.push([num, freq]);
        if (minHeap.size() > k) minHeap.pop();
    }
    
    const result: number[] = [];
    while (minHeap.size() > 0) {
        result.push(minHeap.pop()![0]);
    }
    return result;
}

// 3. Merge k Sorted Lists
class ListNode {
    val: number;
    next: ListNode | null;
    constructor(val?: number, next?: ListNode | null) {
        this.val = val === undefined ? 0 : val;
        this.next = next === undefined ? null : next;
    }
}

function mergeKLists(lists: Array<ListNode | null>): ListNode | null {
    const minHeap = new MinHeap<[number, number, ListNode]>((a, b) => a[0] - b[0]);
    
    for (let i = 0; i < lists.length; i++) {
        if (lists[i]) {
            minHeap.push([lists[i]!.val, i, lists[i]!]);
        }
    }
    
    const dummy = new ListNode(0);
    let curr = dummy;
    
    while (minHeap.size() > 0) {
        const [val, i, node] = minHeap.pop()!;
        curr.next = node;
        curr = curr.next;
        
        if (node.next) {
            minHeap.push([node.next.val, i, node.next]);
        }
    }
    
    return dummy.next;
}

// 4. Find Median from Data Stream
class MedianFinder {
    private maxHeap = new MinHeap<number>((a, b) => b - a); // for smaller half
    private minHeap = new MinHeap<number>((a, b) => a - b); // for larger half
    
    addNum(num: number): void {
        this.maxHeap.push(num);
        this.minHeap.push(this.maxHeap.pop()!);
        
        if (this.minHeap.size() > this.maxHeap.size()) {
            this.maxHeap.push(this.minHeap.pop()!);
        }
    }
    
    findMedian(): number {
        return this.maxHeap.size() > this.minHeap.size() 
            ? this.maxHeap.peek()! 
            : (this.maxHeap.peek()! + this.minHeap.peek()!) / 2;
    }
}

// 5. K Closest Points to Origin
function kClosest(points: number[][], k: number): number[][] {
    const maxHeap = new MinHeap<[number, number[]]>((a, b) => b[0] - a[0]);
    
    for (const point of points) {
        const dist = point[0] * point[0] + point[1] * point[1];
        maxHeap.push([dist, point]);
        if (maxHeap.size() > k) maxHeap.pop();
    }
    
    const result: number[][] = [];
    while (maxHeap.size() > 0) {
        result.push(maxHeap.pop()![1]);
    }
    return result;
}

// 6. Last Stone Weight
function lastStoneWeight(stones: number[]): number {
    const maxHeap = new MinHeap<number>((a, b) => b - a);
    for (const stone of stones) maxHeap.push(stone);
    
    while (maxHeap.size() > 1) {
        const first = maxHeap.pop()!;
        const second = maxHeap.pop()!;
        if (first !== second) {
            maxHeap.push(first - second);
        }
    }
    
    return maxHeap.size() === 0 ? 0 : maxHeap.peek()!;
}

// 7. Task Scheduler
function leastInterval(tasks: string[], n: number): number {
    const count = new Map<string, number>();
    for (const task of tasks) {
        count.set(task, (count.get(task) || 0) + 1);
    }
    
    const maxHeap = new MinHeap<number>((a, b) => b - a);
    for (const freq of count.values()) {
        if (freq > 0) maxHeap.push(freq);
    }
    
    let time = 0;
    while (maxHeap.size() > 0) {
        const temp: number[] = [];
        for (let i = 0; i <= n; i++) {
            if (maxHeap.size() > 0) {
                const freq = maxHeap.pop()!;
                if (freq > 1) temp.push(freq - 1);
            }
            time++;
            if (maxHeap.size() === 0 && temp.length === 0) break;
        }
        for (const freq of temp) {
            maxHeap.push(freq);
        }
    }
    return time;
}

// 8. Ugly Number II
function nthUglyNumber(n: number): number {
    const minHeap = new MinHeap<number>((a, b) => a - b);
    const seen = new Set<number>();
    
    minHeap.push(1);
    seen.add(1);
    
    let ugly = 1;
    for (let i = 0; i < n; i++) {
        ugly = minHeap.pop()!;
        for (const factor of [2, 3, 5]) {
            const next = ugly * factor;
            if (!seen.has(next)) {
                minHeap.push(next);
                seen.add(next);
            }
        }
    }
    return ugly;
}

// 9. Meeting Rooms II
function minMeetingRooms(intervals: number[][]): number {
    intervals.sort((a, b) => a[0] - b[0]);
    const minHeap = new MinHeap<number>((a, b) => a - b);
    
    for (const [start, end] of intervals) {
        if (minHeap.size() > 0 && minHeap.peek()! <= start) {
            minHeap.pop();
        }
        minHeap.push(end);
    }
    return minHeap.size();
}

// 10. Kth Smallest Element in a Sorted Matrix
function kthSmallest(matrix: number[][], k: number): number {
    const minHeap = new MinHeap<[number, number, number]>((a, b) => a[0] - b[0]);
    
    for (let i = 0; i < matrix.length; i++) {
        minHeap.push([matrix[i][0], i, 0]);
    }
    
    for (let i = 0; i < k - 1; i++) {
        const [val, row, col] = minHeap.pop()!;
        if (col + 1 < matrix[0].length) {
            minHeap.push([matrix[row][col + 1], row, col + 1]);
        }
    }
    return minHeap.peek()![0];
}

// Test cases
console.log("=== Heap Examples ===");

// Test 1: Kth Largest Element
console.log("Kth Largest([3,2,1,5,6,4], k=2):", findKthLargest([3,2,1,5,6,4], 2));

// Test 2: Top K Frequent Elements
console.log("Top K Frequent([1,1,1,2,2,3], k=2):", topKFrequent([1,1,1,2,2,3], 2));

// Test 4: Median Finder
const mf = new MedianFinder();
mf.addNum(1);
mf.addNum(2);
console.log("Median after adding 1,2:", mf.findMedian());

// Test 5: K Closest Points
const points = [[1,3],[-2,2]];
console.log("K Closest Points(k=1):", kClosest(points, 1));

// Test 6: Last Stone Weight
console.log("Last Stone Weight([2,7,4,1,8,1]):", lastStoneWeight([2,7,4,1,8,1]));

// Test 7: Task Scheduler
console.log("Least Interval(['A','A','A','B','B','B'], n=2):", leastInterval(['A','A','A','B','B','B'], 2));

// Test 8: Ugly Number II
console.log("10th Ugly Number:", nthUglyNumber(10));

// Test 9: Meeting Rooms II
const meetings = [[0,30],[5,10],[15,20]];
console.log("Min Meeting Rooms:", minMeetingRooms(meetings));

// Test 10: Kth Smallest in Matrix
const matrix = [[1,5,9],[10,11,13],[12,13,15]];
console.log("Kth Smallest in Matrix(k=8):", kthSmallest(matrix, 8));