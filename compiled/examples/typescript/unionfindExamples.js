class UnionFind {
    constructor(n) {
        this.parent = Array.from({ length: n }, (_, i) => i);
        this.rank = new Array(n).fill(0);
        this.components = n;
    }
    find(x) {
        if (this.parent[x] !== x) {
            this.parent[x] = this.find(this.parent[x]);
        }
        return this.parent[x];
    }
    union(x, y) {
        const px = this.find(x);
        const py = this.find(y);
        if (px === py)
            return false;
        if (this.rank[px] < this.rank[py]) {
            this.parent[px] = py;
        }
        else if (this.rank[px] > this.rank[py]) {
            this.parent[py] = px;
        }
        else {
            this.parent[py] = px;
            this.rank[px]++;
        }
        this.components--;
        return true;
    }
    connected(x, y) {
        return this.find(x) === this.find(y);
    }
}
// 1. Number of Islands
function numIslands(grid) {
    if (!grid.length)
        return 0;
    const m = grid.length, n = grid[0].length;
    const uf = new UnionFind(m * n);
    let islands = 0;
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (grid[i][j] === '1') {
                islands++;
                const curr = i * n + j;
                if (i > 0 && grid[i - 1][j] === '1' && uf.union(curr, (i - 1) * n + j))
                    islands--;
                if (j > 0 && grid[i][j - 1] === '1' && uf.union(curr, i * n + j - 1))
                    islands--;
            }
        }
    }
    return islands;
}
// 2. Friend Circles
function findCircleNum(isConnected) {
    const n = isConnected.length;
    const uf = new UnionFind(n);
    for (let i = 0; i < n; i++) {
        for (let j = i + 1; j < n; j++) {
            if (isConnected[i][j] === 1) {
                uf.union(i, j);
            }
        }
    }
    return uf.components;
}
// 3. Graph Valid Tree
function validTree(n, edges) {
    if (edges.length !== n - 1)
        return false;
    const uf = new UnionFind(n);
    for (const [a, b] of edges) {
        if (!uf.union(a, b))
            return false;
    }
    return uf.components === 1;
}
// 4. Accounts Merge
function accountsMerge(accounts) {
    const emailToId = new Map();
    const emailToName = new Map();
    let id = 0;
    for (const account of accounts) {
        const name = account[0];
        for (let i = 1; i < account.length; i++) {
            const email = account[i];
            if (!emailToId.has(email)) {
                emailToId.set(email, id++);
            }
            emailToName.set(email, name);
        }
    }
    const uf = new UnionFind(id);
    for (const account of accounts) {
        const firstId = emailToId.get(account[1]);
        for (let i = 2; i < account.length; i++) {
            uf.union(firstId, emailToId.get(account[i]));
        }
    }
    const groups = new Map();
    for (const [email, emailId] of emailToId) {
        const root = uf.find(emailId);
        if (!groups.has(root))
            groups.set(root, []);
        groups.get(root).push(email);
    }
    const result = [];
    for (const emails of groups.values()) {
        emails.sort();
        result.push([emailToName.get(emails[0]), ...emails]);
    }
    return result;
}
// 5. Redundant Connection
function findRedundantConnection(edges) {
    const uf = new UnionFind(edges.length + 1);
    for (const [a, b] of edges) {
        if (!uf.union(a, b))
            return [a, b];
    }
    return [];
}
// 6. Most Stones Removed
function removeStones(stones) {
    const uf = new UnionFind(20000);
    const seen = new Set();
    for (const [x, y] of stones) {
        uf.union(x, y + 10000);
        seen.add(x);
        seen.add(y + 10000);
    }
    const roots = new Set();
    for (const coord of seen) {
        roots.add(uf.find(coord));
    }
    return stones.length - roots.size;
}
// 7. Satisfiability of Equality Equations
function equationsPossible(equations) {
    const uf = new UnionFind(26);
    for (const eq of equations) {
        if (eq[1] === '=') {
            uf.union(eq.charCodeAt(0) - 97, eq.charCodeAt(3) - 97);
        }
    }
    for (const eq of equations) {
        if (eq[1] === '!' && uf.connected(eq.charCodeAt(0) - 97, eq.charCodeAt(3) - 97)) {
            return false;
        }
    }
    return true;
}
// 8. Swim in Rising Water
function swimInWater(grid) {
    const n = grid.length;
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    const heap = [[grid[0][0], 0, 0]];
    const visited = new Set();
    while (heap.length) {
        heap.sort((a, b) => a[0] - b[0]);
        const [time, x, y] = heap.shift();
        const key = `${x},${y}`;
        if (visited.has(key))
            continue;
        visited.add(key);
        if (x === n - 1 && y === n - 1)
            return time;
        for (const [dx, dy] of directions) {
            const nx = x + dx, ny = y + dy;
            const newKey = `${nx},${ny}`;
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited.has(newKey)) {
                heap.push([Math.max(time, grid[nx][ny]), nx, ny]);
            }
        }
    }
    return -1;
}
// 9. Connecting Cities With Minimum Cost
function minimumCost(n, connections) {
    connections.sort((a, b) => a[2] - b[2]);
    const uf = new UnionFind(n + 1);
    let cost = 0, edges = 0;
    for (const [a, b, c] of connections) {
        if (uf.union(a, b)) {
            cost += c;
            if (++edges === n - 1)
                break;
        }
    }
    return edges === n - 1 ? cost : -1;
}
// 10. Number of Operations to Make Network Connected
function makeConnected(n, connections) {
    if (connections.length < n - 1)
        return -1;
    const uf = new UnionFind(n);
    for (const [a, b] of connections) {
        uf.union(a, b);
    }
    return uf.components - 1;
}
// Test cases
console.log("=== Union-Find Examples ===");
// Test 1: Number of Islands
const grid = [['1', '1', '1', '1', '0'], ['1', '1', '0', '1', '0'], ['1', '1', '0', '0', '0'], ['0', '0', '0', '0', '0']];
console.log(`Num Islands: ${numIslands(grid)}`);
// Test 2: Friend Circles
const isConnected = [[1, 1, 0], [1, 1, 0], [0, 0, 1]];
console.log(`Find Circle Num: ${findCircleNum(isConnected)}`);
// Test 3: Graph Valid Tree
console.log(`Valid Tree(5, [[0,1],[0,2],[0,3],[1,4]]): ${validTree(5, [[0, 1], [0, 2], [0, 3], [1, 4]])}`);
// Test 4: Accounts Merge
const accounts = [["John", "johnsmith@mail.com", "john_newyork@mail.com"],
    ["John", "johnsmith@mail.com", "john00@mail.com"],
    ["Mary", "mary@mail.com"],
    ["John", "johnnybravo@mail.com"]];
console.log(`Accounts Merge: ${JSON.stringify(accountsMerge(accounts))}`);
// Test 5: Redundant Connection
console.log(`Find Redundant Connection([[1,2],[1,3],[2,3]]): ${findRedundantConnection([[1, 2], [1, 3], [2, 3]])}`);
// Test 6: Most Stones Removed
console.log(`Remove Stones([[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]): ${removeStones([[0, 0], [0, 1], [1, 0], [1, 2], [2, 1], [2, 2]])}`);
// Test 7: Satisfiability of Equality Equations
console.log(`Equations Possible(['a==b','b!=a']): ${equationsPossible(['a==b', 'b!=a'])}`);
// Test 8: Swim in Rising Water
const swimGrid = [[0, 2], [1, 3]];
console.log(`Swim in Water: ${swimInWater(swimGrid)}`);
// Test 9: Connecting Cities
console.log(`Minimum Cost(3, [[1,2,5],[1,3,6],[2,3,1]]): ${minimumCost(3, [[1, 2, 5], [1, 3, 6], [2, 3, 1]])}`);
// Test 10: Make Connected
console.log(`Make Connected(4, [[0,1],[0,2],[1,2]]): ${makeConnected(4, [[0, 1], [0, 2], [1, 2]])}`);
