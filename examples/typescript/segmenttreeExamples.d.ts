declare class NumArray {
    private tree;
    private n;
    constructor(nums: number[]);
    update(index: number, val: number): void;
    sumRange(left: number, right: number): number;
}
declare class RangeModule {
    private ranges;
    addRange(left: number, right: number): void;
    queryRange(left: number, right: number): boolean;
    removeRange(left: number, right: number): void;
}
declare class MyCalendarThree {
    private delta;
    book(start: number, end: number): number;
}
declare function rectangleArea(rectangles: number[][]): number;
declare function findNumberOfLIS(nums: number[]): number;
declare class NumMatrix {
    private tree;
    private nums;
    private m;
    private n;
    constructor(matrix: number[][]);
    update(row: number, col: number, val: number): void;
    sumRegion(row1: number, col1: number, row2: number, col2: number): number;
    private sum;
}
declare function fallingSquares(positions: number[][]): number[];
declare const numArray: NumArray;
declare const rm: RangeModule;
declare const cal: MyCalendarThree;
declare const numMatrix: NumMatrix;
//# sourceMappingURL=segmenttreeExamples.d.ts.map