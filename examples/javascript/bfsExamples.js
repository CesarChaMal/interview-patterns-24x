// TreeNode class
class TreeNode {
    constructor(val, left, right) {
        this.val = val === undefined ? 0 : val;
        this.left = left === undefined ? null : left;
        this.right = right === undefined ? null : right;
    }
}

// 1. Binary Tree Level Order Traversal
function levelOrder(root) {
    if (!root) return [];
    const result = [];
    const queue = [root];
    
    while (queue.length > 0) {
        const levelSize = queue.length;
        const currentLevel = [];
        
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift();
            currentLevel.push(node.val);
            
            if (node.left) queue.push(node.left);
            if (node.right) queue.push(node.right);
        }
        result.push(currentLevel);
    }
    return result;
}

// 2. Binary Tree Right Side View
function rightSideView(root) {
    if (!root) return [];
    const result = [];
    const queue = [root];
    
    while (queue.length > 0) {
        const levelSize = queue.length;
        
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift();
            if (i === levelSize - 1) result.push(node.val);
            
            if (node.left) queue.push(node.left);
            if (node.right) queue.push(node.right);
        }
    }
    return result;
}

// 3. Binary Tree Zigzag Level Order Traversal
function zigzagLevelOrder(root) {
    if (!root) return [];
    const result = [];
    const queue = [root];
    let leftToRight = true;
    
    while (queue.length > 0) {
        const levelSize = queue.length;
        const currentLevel = [];
        
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift();
            
            if (leftToRight) {
                currentLevel.push(node.val);
            } else {
                currentLevel.unshift(node.val);
            }
            
            if (node.left) queue.push(node.left);
            if (node.right) queue.push(node.right);
        }
        
        result.push(currentLevel);
        leftToRight = !leftToRight;
    }
    return result;
}

// 4. Minimum Depth of Binary Tree
function minDepth(root) {
    if (!root) return 0;
    const queue = [[root, 1]];
    
    while (queue.length > 0) {
        const [node, depth] = queue.shift();
        
        if (!node.left && !node.right) return depth;
        
        if (node.left) queue.push([node.left, depth + 1]);
        if (node.right) queue.push([node.right, depth + 1]);
    }
    return 0;
}

// 5. Word Ladder
function ladderLength(beginWord, endWord, wordList) {
    const wordSet = new Set(wordList);
    if (!wordSet.has(endWord)) return 0;
    
    const queue = [[beginWord, 1]];
    const visited = new Set([beginWord]);
    
    while (queue.length > 0) {
        const [word, length] = queue.shift();
        
        if (word === endWord) return length;
        
        for (let i = 0; i < word.length; i++) {
            for (let c = 97; c <= 122; c++) {
                const newChar = String.fromCharCode(c);
                const newWord = word.slice(0, i) + newChar + word.slice(i + 1);
                
                if (wordSet.has(newWord) && !visited.has(newWord)) {
                    visited.add(newWord);
                    queue.push([newWord, length + 1]);
                }
            }
        }
    }
    return 0;
}

// 6. Number of Islands
function numIslands(grid) {
    if (!grid || grid.length === 0) return 0;
    
    const rows = grid.length;
    const cols = grid[0].length;
    let count = 0;
    
    const bfs = (startRow, startCol) => {
        const queue = [[startRow, startCol]];
        grid[startRow][startCol] = '0';
        
        while (queue.length > 0) {
            const [row, col] = queue.shift();
            const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
            
            for (const [dr, dc] of directions) {
                const newRow = row + dr;
                const newCol = col + dc;
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] === '1') {
                    grid[newRow][newCol] = '0';
                    queue.push([newRow, newCol]);
                }
            }
        }
    };
    
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            if (grid[i][j] === '1') {
                count++;
                bfs(i, j);
            }
        }
    }
    
    return count;
}

// 7. Rotting Oranges
function orangesRotting(grid) {
    const rows = grid.length;
    const cols = grid[0].length;
    const queue = [];
    let freshCount = 0;
    
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            if (grid[i][j] === 2) {
                queue.push([i, j, 0]);
            } else if (grid[i][j] === 1) {
                freshCount++;
            }
        }
    }
    
    if (freshCount === 0) return 0;
    
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    let minutes = 0;
    
    while (queue.length > 0) {
        const [row, col, time] = queue.shift();
        minutes = Math.max(minutes, time);
        
        for (const [dr, dc] of directions) {
            const newRow = row + dr;
            const newCol = col + dc;
            
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] === 1) {
                grid[newRow][newCol] = 2;
                freshCount--;
                queue.push([newRow, newCol, time + 1]);
            }
        }
    }
    
    return freshCount === 0 ? minutes : -1;
}

// 8. Course Schedule
function canFinish(numCourses, prerequisites) {
    const graph = Array.from({ length: numCourses }, () => []);
    const inDegree = new Array(numCourses).fill(0);
    
    for (const [course, prereq] of prerequisites) {
        graph[prereq].push(course);
        inDegree[course]++;
    }
    
    const queue = [];
    for (let i = 0; i < numCourses; i++) {
        if (inDegree[i] === 0) {
            queue.push(i);
        }
    }
    
    let completed = 0;
    while (queue.length > 0) {
        const course = queue.shift();
        completed++;
        
        for (const nextCourse of graph[course]) {
            inDegree[nextCourse]--;
            if (inDegree[nextCourse] === 0) {
                queue.push(nextCourse);
            }
        }
    }
    
    return completed === numCourses;
}

// 9. Clone Graph
function cloneGraph(node) {
    if (!node) return null;
    
    const visited = new Map();
    const queue = [node];
    visited.set(node, new Node(node.val));
    
    while (queue.length > 0) {
        const current = queue.shift();
        
        for (const neighbor of current.neighbors) {
            if (!visited.has(neighbor)) {
                visited.set(neighbor, new Node(neighbor.val));
                queue.push(neighbor);
            }
            visited.get(current).neighbors.push(visited.get(neighbor));
        }
    }
    
    return visited.get(node);
}

// 10. Shortest Path in Binary Matrix
function shortestPathBinaryMatrix(grid) {
    const n = grid.length;
    if (grid[0][0] === 1 || grid[n-1][n-1] === 1) return -1;
    if (n === 1) return 1;
    
    const queue = [[0, 0, 1]];
    const visited = new Set(['0,0']);
    const directions = [[-1,-1],[-1,0],[-1,1],[0,-1],[0,1],[1,-1],[1,0],[1,1]];
    
    while (queue.length > 0) {
        const [row, col, path] = queue.shift();
        
        for (const [dr, dc] of directions) {
            const newRow = row + dr;
            const newCol = col + dc;
            const key = `${newRow},${newCol}`;
            
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && 
                grid[newRow][newCol] === 0 && !visited.has(key)) {
                
                if (newRow === n-1 && newCol === n-1) return path + 1;
                
                visited.add(key);
                queue.push([newRow, newCol, path + 1]);
            }
        }
    }
    
    return -1;
}

// Test cases
console.log("=== BFS Examples ===");

// Create test tree: [3,9,20,null,null,15,7]
const root = new TreeNode(3);
root.left = new TreeNode(9);
root.right = new TreeNode(20);
root.right.left = new TreeNode(15);
root.right.right = new TreeNode(7);

console.log("Level Order:", levelOrder(root));
console.log("Right Side View:", rightSideView(root));
console.log("Zigzag Level Order:", zigzagLevelOrder(root));
console.log("Min Depth:", minDepth(root));

console.log("Word Ladder('hit', 'cog', ['hot','dot','dog','lot','log','cog']):", 
    ladderLength('hit', 'cog', ['hot','dot','dog','lot','log','cog']));

const grid = [['1','1','0','0','0'],['1','1','0','0','0'],['0','0','1','0','0'],['0','0','0','1','1']];
console.log("Number of Islands:", numIslands(grid));

console.log("Can Finish Courses(2, [[1,0]]):", canFinish(2, [[1,0]]));

const binaryGrid = [[0,0,0],[1,1,0],[1,1,0]];
console.log("Shortest Path Binary Matrix:", shortestPathBinaryMatrix(binaryGrid));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        TreeNode,
        levelOrder,
        rightSideView,
        zigzagLevelOrder,
        minDepth,
        ladderLength,
        numIslands,
        orangesRotting,
        canFinish,
        cloneGraph,
        shortestPathBinaryMatrix
    };
}