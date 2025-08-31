declare class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null);
}
declare function inorderTraversal(root: TreeNode | null): number[];
declare function preorderTraversal(root: TreeNode | null): number[];
declare function postorderTraversal(root: TreeNode | null): number[];
declare function maxDepth(root: TreeNode | null): number;
declare function diameterOfBinaryTree(root: TreeNode | null): number;
declare function maxPathSum(root: TreeNode | null): number;
declare function hasPathSum(root: TreeNode | null, targetSum: number): boolean;
declare function binaryTreePaths(root: TreeNode | null): string[];
declare function pathSumIII(root: TreeNode | null, targetSum: number): number;
declare const root: TreeNode;
//# sourceMappingURL=treedfsExamples.d.ts.map