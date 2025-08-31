declare class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null);
}
declare function levelOrder(root: TreeNode | null): number[][];
declare function bfsIslands(grid: string[][], i: number, j: number): void;
declare function ladderLength(beginWord: string, endWord: string, wordList: string[]): number;
declare function minDepth(root: TreeNode | null): number;
declare function openLock(deadends: string[], target: string): number;
declare function rightSideView(root: TreeNode | null): number[];
declare function numSquares(n: number): number;
declare function wallsAndGates(rooms: number[][]): void;
declare const root: TreeNode;
declare const grid: string[][];
declare const oranges: number[][];
declare const matrix: number[][];
declare const rooms: number[][];
//# sourceMappingURL=bfsExamples.d.ts.map