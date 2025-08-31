declare class PriorityQueue<T> {
    private compare;
    private heap;
    constructor(compare: (a: T, b: T) => number);
    enqueue(item: T): void;
    dequeue(): T | undefined;
    get size(): number;
    private heapifyUp;
    private heapifyDown;
}
declare function networkDelayTime(times: number[][], n: number, k: number): number;
declare function findCheapestPrice(n: number, flights: number[][], src: number, dst: number, k: number): number;
declare function maxProbability(n: number, edges: number[][], succProb: number[], start: number, end: number): number;
declare function minimumEffortPath(heights: number[][]): number;
declare function findTheCity(n: number, edges: number[][], distanceThreshold: number): number;
declare function minCost(grid: number[][]): number;
declare function shortestDistance(maze: number[][], start: number[], destination: number[]): number;
declare const times: number[][];
declare const flights: number[][];
declare const edges: number[][];
declare const succProb: number[];
declare const heights: number[][];
declare const cityEdges: number[][];
declare const grid: number[][];
declare const binaryGrid: number[][];
declare const waterGrid: number[][];
declare const points: number[][];
declare const maze: number[][];
//# sourceMappingURL=dijkstraExamples.d.ts.map