declare class MinHeap<T> {
    private compare;
    private heap;
    constructor(compare: (a: T, b: T) => number);
    push(val: T): void;
    pop(): T | undefined;
    peek(): T | undefined;
    size(): number;
    private bubbleUp;
    private bubbleDown;
}
declare function findKthLargest(nums: number[], k: number): number;
declare function topKFrequent(nums: number[], k: number): number[];
declare class ListNode {
    val: number;
    next: ListNode | null;
    constructor(val?: number, next?: ListNode | null);
}
declare function mergeKLists(lists: Array<ListNode | null>): ListNode | null;
declare class MedianFinder {
    private maxHeap;
    private minHeap;
    addNum(num: number): void;
    findMedian(): number;
}
declare function lastStoneWeight(stones: number[]): number;
declare function leastInterval(tasks: string[], n: number): number;
declare function nthUglyNumber(n: number): number;
declare function kthSmallest(matrix: number[][], k: number): number;
declare const mf: MedianFinder;
declare const points: number[][];
declare const meetings: number[][];
declare const matrix: number[][];
//# sourceMappingURL=heapExamples.d.ts.map