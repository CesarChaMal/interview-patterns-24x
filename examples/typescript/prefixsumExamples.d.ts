declare function subarraySum(nums: number[], k: number): number;
declare function checkSubarraySum(nums: number[], k: number): boolean;
declare function maxSubArrayLen(nums: number[], k: number): number;
declare function numSubarraysWithSum(nums: number[], goal: number): number;
declare function findMaxLength(nums: number[]): number;
declare class NumArray {
    private prefixSum;
    constructor(nums: number[]);
    sumRange(left: number, right: number): number;
}
declare function productExceptSelf(nums: number[]): number[];
declare function subarraysDivByK(nums: number[], k: number): number;
declare class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null);
}
declare function minOperations(nums: number[], x: number): number;
declare const numArray: NumArray;
//# sourceMappingURL=prefixsumExamples.d.ts.map