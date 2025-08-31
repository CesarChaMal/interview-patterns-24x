"use strict";
class PriorityQueue {
    constructor(compare) {
        this.compare = compare;
        this.heap = [];
    }
    enqueue(item) {
        this.heap.push(item);
        this.heapifyUp(this.heap.length - 1);
    }
    dequeue() {
        if (this.heap.length === 0)
            return undefined;
        if (this.heap.length === 1)
            return this.heap.pop();
        const root = this.heap[0];
        this.heap[0] = this.heap.pop();
        this.heapifyDown(0);
        return root;
    }
    get size() {
        return this.heap.length;
    }
    heapifyUp(index) {
        while (index > 0) {
            const parentIndex = Math.floor((index - 1) / 2);
            if (this.compare(this.heap[index], this.heap[parentIndex]) >= 0)
                break;
            [this.heap[index], this.heap[parentIndex]] = [this.heap[parentIndex], this.heap[index]];
            index = parentIndex;
        }
    }
    heapifyDown(index) {
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
            if (minIndex === index)
                break;
            [this.heap[index], this.heap[minIndex]] = [this.heap[minIndex], this.heap[index]];
            index = minIndex;
        }
    }
}
// 1. Network Delay Time
function networkDelayTime(times, n, k) {
    const graph = new Map();
    for (const [u, v, w] of times) {
        if (!graph.has(u))
            graph.set(u, []);
        graph.get(u).push([v, w]);
    }
    const pq = new PriorityQueue((a, b) => a[1] - b[1]);
    pq.enqueue([k, 0]);
    const visited = new Set();
    let maxTime = 0;
    while (pq.size > 0) {
        const [node, time] = pq.dequeue();
        if (!visited.has(node)) {
            visited.add(node);
            maxTime = Math.max(maxTime, time);
            if (graph.has(node)) {
                for (const [next, weight] of graph.get(node)) {
                    if (!visited.has(next)) {
                        pq.enqueue([next, time + weight]);
                    }
                }
            }
        }
    }
    return visited.size === n ? maxTime : -1;
}
// 2. Cheapest Flights Within K Stops
function findCheapestPrice(n, flights, src, dst, k) {
    const graph = new Map();
    for (const [u, v, w] of flights) {
        if (!graph.has(u))
            graph.set(u, []);
        graph.get(u).push([v, w]);
    }
    const pq = new PriorityQueue((a, b) => a[1] - b[1]);
    pq.enqueue([src, 0, 0]);
    const visited = new Map();
    while (pq.size > 0) {
        const [node, cost, stops] = pq.dequeue();
        if (node === dst)
            return cost;
        if (stops > k)
            continue;
        if (visited.has(node) && visited.get(node) <= stops)
            continue;
        visited.set(node, stops);
        if (graph.has(node)) {
            for (const [next, price] of graph.get(node)) {
                pq.enqueue([next, cost + price, stops + 1]);
            }
        }
    }
    return -1;
}
// 3. Path with Maximum Probability
function maxProbability(n, edges, succProb, start, end) {
    const graph = new Map();
    for (let i = 0; i < edges.length; i++) {
        const [u, v] = edges[i];
        const prob = succProb[i];
        if (!graph.has(u))
            graph.set(u, []);
        if (!graph.has(v))
            graph.set(v, []);
        graph.get(u).push([v, prob]);
        graph.get(v).push([u, prob]);
    }
    const pq = new PriorityQueue((a, b) => b[1] - a[1]);
    pq.enqueue([start, 1.0]);
    const visited = new Set();
    while (pq.size > 0) {
        const [node, prob] = pq.dequeue();
        if (node === end)
            return prob;
        if (visited.has(node))
            continue;
        visited.add(node);
        if (graph.has(node)) {
            for (const [next, nextProb] of graph.get(node)) {
                if (!visited.has(next)) {
                    pq.enqueue([next, prob * nextProb]);
                }
            }
        }
    }
    return 0.0;
}
// 4. Path With Minimum Effort
function minimumEffortPath(heights) {
    const m = heights.length;
    const n = heights[0].length;
    const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, 0]);
    const visited = Array.from({ length: m }, () => new Array(n).fill(false));
    while (pq.size > 0) {
        const [x, y, effort] = pq.dequeue();
        if (x === m - 1 && y === n - 1)
            return effort;
        if (visited[x][y])
            continue;
        visited[x][y] = true;
        for (const [dx, dy] of dirs) {
            const nx = x + dx;
            const ny = y + dy;
            if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                const newEffort = Math.max(effort, Math.abs(heights[nx][ny] - heights[x][y]));
                pq.enqueue([nx, ny, newEffort]);
            }
        }
    }
    return -1;
}
// 5. Find the City With the Smallest Number of Neighbors
function findTheCity(n, edges, distanceThreshold) {
    const dist = Array.from({ length: n }, () => new Array(n).fill(Infinity));
    for (let i = 0; i < n; i++)
        dist[i][i] = 0;
    for (const [u, v, w] of edges) {
        dist[u][v] = w;
        dist[v][u] = w;
    }
    // Floyd-Warshall
    for (let k = 0; k < n; k++) {
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < n; j++) {
                if (dist[i][k] !== Infinity && dist[k][j] !== Infinity) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
    let minCount = n;
    let result = 0;
    for (let i = 0; i < n; i++) {
        const count = dist[i].filter((d, j) => i !== j && d <= distanceThreshold).length;
        if (count <= minCount) {
            minCount = count;
            result = i;
        }
    }
    return result;
}
// 6. Minimum Cost to Make at Least One Valid Path
function minCost(grid) {
    const m = grid.length;
    const n = grid[0].length;
    const dirs = [[0, 1], [0, -1], [1, 0], [-1, 0]];
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, 0]);
    const visited = Array.from({ length: m }, () => new Array(n).fill(false));
    while (pq.size > 0) {
        const [x, y, cost] = pq.dequeue();
        if (x === m - 1 && y === n - 1)
            return cost;
        if (visited[x][y])
            continue;
        visited[x][y] = true;
        for (let dir = 0; dir < 4; dir++) {
            const [dx, dy] = dirs[dir];
            const nx = x + dx;
            const ny = y + dy;
            if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                const newCost = cost + (grid[x][y] === dir + 1 ? 0 : 1);
                pq.enqueue([nx, ny, newCost]);
            }
        }
    }
    return -1;
}
// 7. Shortest Path in Binary Matrix
function shortestPathBinaryMatrix(grid) {
    const n = grid.length;
    if (grid[0][0] !== 0 || grid[n - 1][n - 1] !== 0)
        return -1;
    const queue = [[0, 0, 1]];
    grid[0][0] = 1;
    const dirs = [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]];
    while (queue.length > 0) {
        const [x, y, path] = queue.shift();
        if (x === n - 1 && y === n - 1)
            return path;
        for (const [dx, dy] of dirs) {
            const nx = x + dx;
            const ny = y + dy;
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] === 0) {
                grid[nx][ny] = 1;
                queue.push([nx, ny, path + 1]);
            }
        }
    }
    return -1;
}
// 8. Swim in Rising Water
function swimInWater(grid) {
    const n = grid.length;
    const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, grid[0][0]]);
    const visited = Array.from({ length: n }, () => new Array(n).fill(false));
    while (pq.size > 0) {
        const [x, y, time] = pq.dequeue();
        if (x === n - 1 && y === n - 1)
            return time;
        if (visited[x][y])
            continue;
        visited[x][y] = true;
        for (const [dx, dy] of dirs) {
            const nx = x + dx;
            const ny = y + dy;
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited[nx][ny]) {
                const newTime = Math.max(time, grid[nx][ny]);
                pq.enqueue([nx, ny, newTime]);
            }
        }
    }
    return -1;
}
// 9. K Closest Points to Origin
function kClosest(points, k) {
    const pq = new PriorityQueue((a, b) => b[1] - a[1]);
    for (const point of points) {
        const dist = point[0] * point[0] + point[1] * point[1];
        pq.enqueue([point, dist]);
        if (pq.size > k)
            pq.dequeue();
    }
    const result = [];
    while (pq.size > 0) {
        result.push(pq.dequeue()[0]);
    }
    return result;
}
// 10. The Maze II
function shortestDistance(maze, start, destination) {
    const m = maze.length;
    const n = maze[0].length;
    const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([start[0], start[1], 0]);
    const visited = Array.from({ length: m }, () => new Array(n).fill(false));
    while (pq.size > 0) {
        const [x, y, dist] = pq.dequeue();
        if (x === destination[0] && y === destination[1])
            return dist;
        if (visited[x][y])
            continue;
        visited[x][y] = true;
        for (const [dx, dy] of dirs) {
            let nx = x;
            let ny = y;
            let steps = 0;
            while (nx + dx >= 0 && nx + dx < m && ny + dy >= 0 && ny + dy < n && maze[nx + dx][ny + dy] === 0) {
                nx += dx;
                ny += dy;
                steps++;
            }
            if (!visited[nx][ny]) {
                pq.enqueue([nx, ny, dist + steps]);
            }
        }
    }
    return -1;
}
// Test cases
console.log("=== Dijkstra Examples ===");
// Test 1: Network Delay Time
const times = [[2, 1, 1], [2, 3, 1], [3, 4, 1]];
console.log("1. Network Delay Time:", networkDelayTime(times, 4, 2));
// Test 2: Cheapest Flights Within K Stops
const flights = [[0, 1, 100], [1, 2, 100], [0, 2, 500]];
console.log("2. Cheapest Flights:", findCheapestPrice(3, flights, 0, 2, 1));
// Test 3: Path with Maximum Probability
const edges = [[0, 1], [1, 2], [0, 2]];
const succProb = [0.5, 0.5, 0.2];
console.log("3. Max Probability:", maxProbability(3, edges, succProb, 0, 2));
// Test 4: Path With Minimum Effort
const heights = [[1, 2, 2], [3, 8, 2], [5, 3, 5]];
console.log("4. Minimum Effort:", minimumEffortPath(heights));
// Test 5: Find the City
const cityEdges = [[0, 1, 3], [1, 2, 1], [1, 3, 4], [2, 3, 1]];
console.log("5. Find City:", findTheCity(4, cityEdges, 4));
// Test 6: Minimum Cost Valid Path
const grid = [[1, 1, 1, 1], [2, 2, 2, 2], [1, 1, 1, 1], [2, 2, 2, 2]];
console.log("6. Min Cost Path:", minCost(grid));
// Test 7: Shortest Path Binary Matrix
const binaryGrid = [[0, 0, 0], [1, 1, 0], [1, 1, 0]];
console.log("7. Shortest Path Binary:", shortestPathBinaryMatrix(binaryGrid));
// Test 8: Swim in Rising Water
const waterGrid = [[0, 2], [1, 3]];
console.log("8. Swim in Water:", swimInWater(waterGrid));
// Test 9: K Closest Points
const points = [[1, 1], [-1, -1], [3, -1]];
console.log("9. K Closest Points:", kClosest(points, 2));
// Test 10: The Maze II
const maze = [[0, 0, 1, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 1, 0], [1, 1, 0, 1, 1], [0, 0, 0, 0, 0]];
console.log("10. Maze II:", shortestDistance(maze, [0, 4], [4, 4]));
//# sourceMappingURL=dijkstraExamples.js.map