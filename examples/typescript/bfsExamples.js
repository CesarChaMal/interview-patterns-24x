"use strict";
class TreeNode {
    constructor(val, left, right) {
        this.val = val === undefined ? 0 : val;
        this.left = left === undefined ? null : left;
        this.right = right === undefined ? null : right;
    }
}
// 1. Binary Tree Level Order Traversal
function levelOrder(root) {
    if (!root)
        return [];
    const result = [];
    const queue = [root];
    while (queue.length > 0) {
        const levelSize = queue.length;
        const level = [];
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift();
            level.push(node.val);
            if (node.left)
                queue.push(node.left);
            if (node.right)
                queue.push(node.right);
        }
        result.push(level);
    }
    return result;
}
// 2. Number of Islands
function numIslands(grid) {
    if (!grid.length)
        return 0;
    let count = 0;
    for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[0].length; j++) {
            if (grid[i][j] === '1') {
                bfsIslands(grid, i, j);
                count++;
            }
        }
    }
    return count;
}
function bfsIslands(grid, i, j) {
    const queue = [[i, j]];
    grid[i][j] = '0';
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    while (queue.length > 0) {
        const [x, y] = queue.shift();
        for (const [dx, dy] of directions) {
            const nx = x + dx, ny = y + dy;
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] === '1') {
                grid[nx][ny] = '0';
                queue.push([nx, ny]);
            }
        }
    }
}
// 3. Rotting Oranges
function orangesRotting(grid) {
    const queue = [];
    let fresh = 0;
    for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[0].length; j++) {
            if (grid[i][j] === 2)
                queue.push([i, j]);
            else if (grid[i][j] === 1)
                fresh++;
        }
    }
    if (fresh === 0)
        return 0;
    let minutes = 0;
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    while (queue.length > 0) {
        const size = queue.length;
        for (let i = 0; i < size; i++) {
            const [x, y] = queue.shift();
            for (const [dx, dy] of directions) {
                const nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] === 1) {
                    grid[nx][ny] = 2;
                    queue.push([nx, ny]);
                    fresh--;
                }
            }
        }
        minutes++;
    }
    return fresh === 0 ? minutes - 1 : -1;
}
// 4. Word Ladder
function ladderLength(beginWord, endWord, wordList) {
    const wordSet = new Set(wordList);
    if (!wordSet.has(endWord))
        return 0;
    const queue = [beginWord];
    let level = 1;
    while (queue.length > 0) {
        const size = queue.length;
        for (let i = 0; i < size; i++) {
            const word = queue.shift();
            if (word === endWord)
                return level;
            for (let j = 0; j < word.length; j++) {
                for (let c = 97; c <= 122; c++) { // 'a' to 'z'
                    const newWord = word.slice(0, j) + String.fromCharCode(c) + word.slice(j + 1);
                    if (wordSet.has(newWord)) {
                        wordSet.delete(newWord);
                        queue.push(newWord);
                    }
                }
            }
        }
        level++;
    }
    return 0;
}
// 5. Minimum Depth of Binary Tree
function minDepth(root) {
    if (!root)
        return 0;
    const queue = [root];
    let depth = 1;
    while (queue.length > 0) {
        const size = queue.length;
        for (let i = 0; i < size; i++) {
            const node = queue.shift();
            if (!node.left && !node.right)
                return depth;
            if (node.left)
                queue.push(node.left);
            if (node.right)
                queue.push(node.right);
        }
        depth++;
    }
    return depth;
}
// 6. Open the Lock
function openLock(deadends, target) {
    const dead = new Set(deadends);
    if (dead.has("0000"))
        return -1;
    const queue = ["0000"];
    const visited = new Set(["0000"]);
    let steps = 0;
    while (queue.length > 0) {
        const size = queue.length;
        for (let i = 0; i < size; i++) {
            const curr = queue.shift();
            if (curr === target)
                return steps;
            for (let j = 0; j < 4; j++) {
                const digit = parseInt(curr[j]);
                for (const d of [-1, 1]) {
                    const newDigit = (digit + d + 10) % 10;
                    const newCombo = curr.slice(0, j) + newDigit + curr.slice(j + 1);
                    if (!dead.has(newCombo) && !visited.has(newCombo)) {
                        visited.add(newCombo);
                        queue.push(newCombo);
                    }
                }
            }
        }
        steps++;
    }
    return -1;
}
// 7. Binary Tree Right Side View
function rightSideView(root) {
    if (!root)
        return [];
    const result = [];
    const queue = [root];
    while (queue.length > 0) {
        const levelSize = queue.length;
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift();
            if (i === levelSize - 1)
                result.push(node.val);
            if (node.left)
                queue.push(node.left);
            if (node.right)
                queue.push(node.right);
        }
    }
    return result;
}
// 8. Shortest Path in Binary Matrix
function shortestPathBinaryMatrix(grid) {
    if (grid[0][0] === 1)
        return -1;
    const n = grid.length;
    if (n === 1)
        return 1;
    const queue = [[0, 0, 1]];
    grid[0][0] = 1;
    const directions = [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]];
    while (queue.length > 0) {
        const [x, y, dist] = queue.shift();
        for (const [dx, dy] of directions) {
            const nx = x + dx, ny = y + dy;
            if (nx === n - 1 && ny === n - 1)
                return dist + 1;
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] === 0) {
                grid[nx][ny] = 1;
                queue.push([nx, ny, dist + 1]);
            }
        }
    }
    return -1;
}
// 9. Perfect Squares
function numSquares(n) {
    const queue = [n];
    const visited = new Set([n]);
    let level = 0;
    while (queue.length > 0) {
        level++;
        const size = queue.length;
        for (let i = 0; i < size; i++) {
            const curr = queue.shift();
            for (let j = 1; j * j <= curr; j++) {
                const next = curr - j * j;
                if (next === 0)
                    return level;
                if (!visited.has(next)) {
                    visited.add(next);
                    queue.push(next);
                }
            }
        }
    }
    return level;
}
// 10. Walls and Gates
function wallsAndGates(rooms) {
    if (!rooms.length)
        return;
    const queue = [];
    for (let i = 0; i < rooms.length; i++) {
        for (let j = 0; j < rooms[0].length; j++) {
            if (rooms[i][j] === 0)
                queue.push([i, j]);
        }
    }
    const directions = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    while (queue.length > 0) {
        const [x, y] = queue.shift();
        for (const [dx, dy] of directions) {
            const nx = x + dx, ny = y + dy;
            if (nx >= 0 && nx < rooms.length && ny >= 0 && ny < rooms[0].length &&
                rooms[nx][ny] === Infinity) {
                rooms[nx][ny] = rooms[x][y] + 1;
                queue.push([nx, ny]);
            }
        }
    }
}
// Test cases
console.log("=== BFS Examples ===");
// Test 1: Level Order Traversal
const root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
console.log("Level Order:", levelOrder(root));
// Test 2: Number of Islands
const grid = [['1', '1', '0', '0', '0'], ['1', '1', '0', '0', '0'], ['0', '0', '1', '0', '0'], ['0', '0', '0', '1', '1']];
console.log("Number of Islands:", numIslands(grid));
// Test 3: Rotting Oranges
const oranges = [[2, 1, 1], [1, 1, 0], [0, 1, 1]];
console.log("Rotting Oranges:", orangesRotting(oranges));
// Test 4: Word Ladder
console.log("Word Ladder:", ladderLength("hit", "cog", ["hot", "dot", "dog", "lot", "log", "cog"]));
// Test 5: Minimum Depth
console.log("Min Depth:", minDepth(root));
// Test 6: Open the Lock
console.log("Open Lock:", openLock(["0201", "0101", "0102", "1212", "2002"], "0202"));
// Test 7: Right Side View
console.log("Right Side View:", rightSideView(root));
// Test 8: Shortest Path Binary Matrix
const matrix = [[0, 0, 0], [1, 1, 0], [1, 1, 0]];
console.log("Shortest Path:", shortestPathBinaryMatrix(matrix));
// Test 9: Perfect Squares
console.log("Perfect Squares(12):", numSquares(12));
// Test 10: Walls and Gates
const rooms = [[Infinity, -1, 0, Infinity]];
wallsAndGates(rooms);
console.log("Walls and Gates completed");
//# sourceMappingURL=bfsExamples.js.map