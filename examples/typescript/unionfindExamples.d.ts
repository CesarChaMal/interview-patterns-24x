declare class UnionFind {
    parent: number[];
    rank: number[];
    components: number;
    constructor(n: number);
    find(x: number): number;
    union(x: number, y: number): boolean;
    connected(x: number, y: number): boolean;
}
declare function findCircleNum(isConnected: number[][]): number;
declare function validTree(n: number, edges: number[][]): boolean;
declare function accountsMerge(accounts: string[][]): string[][];
declare function findRedundantConnection(edges: number[][]): number[];
declare function removeStones(stones: number[][]): number;
declare function equationsPossible(equations: string[]): boolean;
declare function minimumCost(n: number, connections: number[][]): number;
declare function makeConnected(n: number, connections: number[][]): number;
declare const grid: string[][];
declare const isConnected: number[][];
declare const accounts: string[][];
declare const swimGrid: number[][];
//# sourceMappingURL=unionfindExamples.d.ts.map