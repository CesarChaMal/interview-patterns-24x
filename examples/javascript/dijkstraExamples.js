// Priority Queue implementation for JavaScript
class PriorityQueue {
    constructor(compare) {
        this.heap = [];
        this.compare = compare || ((a, b) => a - b);
    }
    
    enqueue(item) {
        this.heap.push(item);
        this.heapifyUp(this.heap.length - 1);
    }
    
    dequeue() {
        if (this.heap.length === 0) return undefined;
        if (this.heap.length === 1) return this.heap.pop();
        
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
            if (this.compare(this.heap[index], this.heap[parentIndex]) >= 0) break;
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
            
            if (minIndex === index) break;
            [this.heap[index], this.heap[minIndex]] = [this.heap[minIndex], this.heap[index]];
            index = minIndex;
        }
    }
}

// 1. Network Delay Time
function networkDelayTime(times, n, k) {
    const graph = new Map();
    for (const [u, v, w] of times) {
        if (!graph.has(u)) graph.set(u, []);
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
        if (!graph.has(u)) graph.set(u, []);
        graph.get(u).push([v, w]);
    }
    
    const pq = new PriorityQueue((a, b) => a[1] - b[1]);
    pq.enqueue([src, 0, 0]);
    
    const visited = new Map();
    
    while (pq.size > 0) {
        const [node, cost, stops] = pq.dequeue();
        
        if (node === dst) return cost;
        if (stops > k) continue;
        if (visited.has(node) && visited.get(node) <= stops) continue;
        
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
        if (!graph.has(u)) graph.set(u, []);
        if (!graph.has(v)) graph.set(v, []);
        graph.get(u).push([v, prob]);
        graph.get(v).push([u, prob]);
    }
    
    const pq = new PriorityQueue((a, b) => b[1] - a[1]);
    pq.enqueue([start, 1.0]);
    
    const visited = new Set();
    
    while (pq.size > 0) {
        const [node, prob] = pq.dequeue();
        
        if (node === end) return prob;
        if (visited.has(node)) continue;
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
    const dirs = [[0,1], [1,0], [0,-1], [-1,0]];
    
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, 0]);
    
    const visited = Array.from({length: m}, () => new Array(n).fill(false));
    
    while (pq.size > 0) {
        const [x, y, effort] = pq.dequeue();
        
        if (x === m - 1 && y === n - 1) return effort;
        if (visited[x][y]) continue;
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
    const dist = Array.from({length: n}, () => new Array(n).fill(Infinity));
    for (let i = 0; i < n; i++) dist[i][i] = 0;
    
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
    const dirs = [[0,1], [0,-1], [1,0], [-1,0]];
    
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, 0]);
    
    const visited = Array.from({length: m}, () => new Array(n).fill(false));
    
    while (pq.size > 0) {
        const [x, y, cost] = pq.dequeue();
        
        if (x === m - 1 && y === n - 1) return cost;
        if (visited[x][y]) continue;
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
    if (grid[0][0] !== 0 || grid[n-1][n-1] !== 0) return -1;
    
    const queue = [[0, 0, 1]];
    grid[0][0] = 1;
    
    const dirs = [[-1,-1], [-1,0], [-1,1], [0,-1], [0,1], [1,-1], [1,0], [1,1]];
    
    while (queue.length > 0) {
        const [x, y, path] = queue.shift();
        
        if (x === n - 1 && y === n - 1) return path;
        
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
    const dirs = [[0,1], [1,0], [0,-1], [-1,0]];
    
    const pq = new PriorityQueue((a, b) => a[2] - b[2]);
    pq.enqueue([0, 0, grid[0][0]]);
    
    const visited = Array.from({length: n}, () => new Array(n).fill(false));
    
    while (pq.size > 0) {
        const [x, y, time] = pq.dequeue();
        
        if (x === n - 1 && y === n - 1) return time;
        if (visited[x][y]) continue;
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
    const pq = new PriorityQueue((a, b) => b[1] - a[1]); // Max heap
    
    for (const [x, y] of points) {
        const dist = x * x + y * y;
        pq.enqueue([[x, y], dist]);
        
        if (pq.size > k) {
            pq.dequeue();
        }
    }
    
    return pq.heap.map(([point, dist]) => point);
}

// 10. Minimum Spanning Tree (Prim's Algorithm)
function minimumSpanningTree(n, edges) {
    const graph = new Map();
    for (const [u, v, w] of edges) {
        if (!graph.has(u)) graph.set(u, []);
        if (!graph.has(v)) graph.set(v, []);
        graph.get(u).push([v, w]);
        graph.get(v).push([u, w]);
    }
    
    const pq = new PriorityQueue((a, b) => a[1] - b[1]);
    const visited = new Set();
    let totalCost = 0;
    
    // Start from node 0
    pq.enqueue([0, 0]);
    
    while (pq.size > 0 && visited.size < n) {
        const [node, cost] = pq.dequeue();
        
        if (visited.has(node)) continue;
        visited.add(node);
        totalCost += cost;
        
        if (graph.has(node)) {
            for (const [neighbor, weight] of graph.get(node)) {
                if (!visited.has(neighbor)) {
                    pq.enqueue([neighbor, weight]);
                }
            }
        }
    }
    
    return visited.size === n ? totalCost : -1;
}

// Test cases
console.log("=== Dijkstra's Algorithm Examples ===");

console.log("1. Network Delay Time:", networkDelayTime([[2,1,1],[2,3,1],[3,4,1]], 4, 2));
console.log("2. Cheapest Flights:", findCheapestPrice(3, [[0,1,100],[1,2,100],[0,2,500]], 0, 2, 1));
console.log("3. Max Probability:", maxProbability(3, [[0,1],[1,2],[0,2]], [0.5,0.5,0.2], 0, 2));
console.log("4. Minimum Effort:", minimumEffortPath([[1,2,2],[3,8,2],[5,3,5]]));
console.log("5. Find The City:", findTheCity(4, [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], 4));
console.log("6. Min Cost Valid Path:", minCost([[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]));
console.log("7. Shortest Path Binary:", shortestPathBinaryMatrix([[0,1],[1,0]]));
console.log("8. Swim in Water:", swimInWater([[0,2],[1,3]]));
console.log("9. K Closest Points:", kClosest([[1,1],[2,2],[3,3]], 2));
console.log("10. Minimum Spanning Tree:", minimumSpanningTree(4, [[0,1,10],[0,2,6],[0,3,5],[1,3,15],[2,3,4]]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        PriorityQueue,
        networkDelayTime,
        findCheapestPrice,
        maxProbability,
        minimumEffortPath,
        findTheCity,
        minCost,
        shortestPathBinaryMatrix,
        swimInWater,
        kClosest,
        minimumSpanningTree
    };
}