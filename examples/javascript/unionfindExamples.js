// Union-Find (Disjoint Set Union) class
class UnionFind {
    constructor(n) {
        this.parent = Array.from({length: n}, (_, i) => i);
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
        const rootX = this.find(x);
        const rootY = this.find(y);
        
        if (rootX !== rootY) {
            if (this.rank[rootX] < this.rank[rootY]) {
                this.parent[rootX] = rootY;
            } else if (this.rank[rootX] > this.rank[rootY]) {
                this.parent[rootY] = rootX;
            } else {
                this.parent[rootY] = rootX;
                this.rank[rootX]++;
            }
            this.components--;
            return true;
        }
        return false;
    }
    
    connected(x, y) {
        return this.find(x) === this.find(y);
    }
}

// 1. Number of Islands
function numIslands(grid) {
    if (!grid || grid.length === 0) return 0;
    
    const rows = grid.length;
    const cols = grid[0].length;
    const uf = new UnionFind(rows * cols);
    let islands = 0;
    
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            if (grid[i][j] === '1') {
                islands++;
            }
        }
    }
    
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            if (grid[i][j] === '1') {
                for (const [di, dj] of directions) {
                    const ni = i + di;
                    const nj = j + dj;
                    if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] === '1') {
                        if (uf.union(i * cols + j, ni * cols + nj)) {
                            islands--;
                        }
                    }
                }
            }
        }
    }
    
    return islands;
}

// 2. Find Circle Num (Number of Provinces)
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
    if (edges.length !== n - 1) return false;
    
    const uf = new UnionFind(n);
    
    for (const [u, v] of edges) {
        if (!uf.union(u, v)) {
            return false;
        }
    }
    
    return uf.components === 1;
}

// 4. Accounts Merge
function accountsMerge(accounts) {
    const emailToName = new Map();
    const emailToId = new Map();
    let id = 0;
    
    for (const account of accounts) {
        const name = account[0];
        for (let i = 1; i < account.length; i++) {
            const email = account[i];
            emailToName.set(email, name);
            if (!emailToId.has(email)) {
                emailToId.set(email, id++);
            }
        }
    }
    
    const uf = new UnionFind(id);
    
    for (const account of accounts) {
        const firstEmailId = emailToId.get(account[1]);
        for (let i = 2; i < account.length; i++) {
            uf.union(firstEmailId, emailToId.get(account[i]));
        }
    }
    
    const groups = new Map();
    for (const [email, emailId] of emailToId) {
        const root = uf.find(emailId);
        if (!groups.has(root)) {
            groups.set(root, []);
        }
        groups.get(root).push(email);
    }
    
    const result = [];
    for (const emails of groups.values()) {
        emails.sort();
        const name = emailToName.get(emails[0]);
        result.push([name, ...emails]);
    }
    
    return result;
}

// 5. Redundant Connection
function findRedundantConnection(edges) {
    const n = edges.length;
    const uf = new UnionFind(n + 1);
    
    for (const [u, v] of edges) {
        if (!uf.union(u, v)) {
            return [u, v];
        }
    }
    
    return [];
}

// 6. Most Stones Removed with Same Row or Column
function removeStones(stones) {
    const n = stones.length;
    const uf = new UnionFind(n);
    
    for (let i = 0; i < n; i++) {
        for (let j = i + 1; j < n; j++) {
            if (stones[i][0] === stones[j][0] || stones[i][1] === stones[j][1]) {
                uf.union(i, j);
            }
        }
    }
    
    return n - uf.components;
}

// 7. Satisfiability of Equality Equations
function equationsPossible(equations) {
    const uf = new UnionFind(26);
    
    // Process equality equations first
    for (const eq of equations) {
        if (eq[1] === '=') {
            const x = eq.charCodeAt(0) - 97;
            const y = eq.charCodeAt(3) - 97;
            uf.union(x, y);
        }
    }
    
    // Check inequality equations
    for (const eq of equations) {
        if (eq[1] === '!') {
            const x = eq.charCodeAt(0) - 97;
            const y = eq.charCodeAt(3) - 97;
            if (uf.connected(x, y)) {
                return false;
            }
        }
    }
    
    return true;
}

// 8. Number of Connected Components in Undirected Graph
function countComponents(n, edges) {
    const uf = new UnionFind(n);
    
    for (const [u, v] of edges) {
        uf.union(u, v);
    }
    
    return uf.components;
}

// 9. Smallest String With Swaps
function smallestStringWithSwaps(s, pairs) {
    const n = s.length;
    const uf = new UnionFind(n);
    
    for (const [i, j] of pairs) {
        uf.union(i, j);
    }
    
    const groups = new Map();
    for (let i = 0; i < n; i++) {
        const root = uf.find(i);
        if (!groups.has(root)) {
            groups.set(root, []);
        }
        groups.get(root).push(i);
    }
    
    const result = s.split('');
    
    for (const indices of groups.values()) {
        const chars = indices.map(i => s[i]).sort();
        indices.sort((a, b) => a - b);
        
        for (let i = 0; i < indices.length; i++) {
            result[indices[i]] = chars[i];
        }
    }
    
    return result.join('');
}

// 10. Evaluate Division
function calcEquation(equations, values, queries) {
    const graph = new Map();
    
    // Build graph
    for (let i = 0; i < equations.length; i++) {
        const [a, b] = equations[i];
        const value = values[i];
        
        if (!graph.has(a)) graph.set(a, new Map());
        if (!graph.has(b)) graph.set(b, new Map());
        
        graph.get(a).set(b, value);
        graph.get(b).set(a, 1 / value);
    }
    
    const dfs = (start, end, visited) => {
        if (!graph.has(start) || !graph.has(end)) return -1;
        if (start === end) return 1;
        
        visited.add(start);
        
        for (const [neighbor, value] of graph.get(start)) {
            if (!visited.has(neighbor)) {
                const result = dfs(neighbor, end, visited);
                if (result !== -1) {
                    return value * result;
                }
            }
        }
        
        return -1;
    };
    
    return queries.map(([a, b]) => dfs(a, b, new Set()));
}

// Test cases
console.log("=== Union-Find Examples ===");

const grid = [['1','1','0','0','0'],['1','1','0','0','0'],['0','0','1','0','0'],['0','0','0','1','1']];
console.log("Number of Islands:", numIslands(grid));

const isConnected = [[1,1,0],[1,1,0],[0,0,1]];
console.log("Find Circle Num:", findCircleNum(isConnected));

console.log("Valid Tree(5, [[0,1],[0,2],[0,3],[1,4]]):", validTree(5, [[0,1],[0,2],[0,3],[1,4]]));

console.log("Redundant Connection([[1,2],[1,3],[2,3]]):", findRedundantConnection([[1,2],[1,3],[2,3]]));

const stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]];
console.log("Remove Stones:", removeStones(stones));

console.log("Equations Possible(['a==b','b!=a']):", equationsPossible(['a==b','b!=a']));

console.log("Count Components(5, [[0,1],[1,2],[3,4]]):", countComponents(5, [[0,1],[1,2],[3,4]]));

console.log("Smallest String With Swaps('dcab', [[0,3],[1,2]]):", smallestStringWithSwaps('dcab', [[0,3],[1,2]]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        UnionFind,
        numIslands,
        findCircleNum,
        validTree,
        accountsMerge,
        findRedundantConnection,
        removeStones,
        equationsPossible,
        countComponents,
        smallestStringWithSwaps,
        calcEquation
    };
}