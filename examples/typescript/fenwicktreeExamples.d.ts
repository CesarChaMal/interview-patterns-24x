declare class FenwickTree {
    private tree;
    constructor(n: number);
    update(i: number, delta: number): void;
    query(i: number): number;
    rangeQuery(l: number, r: number): number;
}
declare class NumArray {
    private ft;
    private arr;
    constructor(nums: number[]);
    update(index: number, val: number): void;
    sumRange(left: number, right: number): number;
}
declare function numTeamsInArray(rating: number[]): number;
declare function processQueries(queries: number[], m: number): number[];
declare function createSortedArray(instructions: number[]): number;
declare class MRUQueue {
    private ft;
    private values;
    private nextPos;
    constructor(n: number);
    fetch(k: number): number;
    private findKth;
}
declare function goodTriplets(nums1: number[], nums2: number[]): number;
declare function minInteger(num: string, k: number): string;
declare const numArray: NumArray;
declare const mru: MRUQueue;
declare const nums1: number[];
declare const nums2: number[];
//# sourceMappingURL=fenwicktreeExamples.d.ts.map