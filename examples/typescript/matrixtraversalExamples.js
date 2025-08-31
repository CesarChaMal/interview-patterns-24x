"use strict";
// 1. Number of Islands
function numIslands(grid) {
    if (grid.length === 0)
        return 0;
    let count = 0;
    const dfs = (i, j) => {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] !== '1')
            return;
        grid[i][j] = '0';
        dfs(i + 1, j);
        dfs(i - 1, j);
        dfs(i, j + 1);
        dfs(i, j - 1);
    };
    for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[0].length; j++) {
            if (grid[i][j] === '1') {
                dfs(i, j);
                count++;
            }
        }
    }
    return count;
}
// 2. Max Area of Island
function maxAreaOfIsland(grid) {
    const dfsArea = (i, j) => {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] !== 1)
            return 0;
        grid[i][j] = 0;
        return 1 + dfsArea(i + 1, j) + dfsArea(i - 1, j) + dfsArea(i, j + 1) + dfsArea(i, j - 1);
    };
    let maxArea = 0;
    for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[0].length; j++) {
            if (grid[i][j] === 1) {
                maxArea = Math.max(maxArea, dfsArea(i, j));
            }
        }
    }
    return maxArea;
}
// 3. Unique Paths
function uniquePaths(m, n) {
    const dp = Array.from({ length: m }, () => new Array(n));
    for (let i = 0; i < m; i++)
        dp[i][0] = 1;
    for (let j = 0; j < n; j++)
        dp[0][j] = 1;
    for (let i = 1; i < m; i++) {
        for (let j = 1; j < n; j++) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }
    return dp[m - 1][n - 1];
}
// 4. Minimum Path Sum
function minPathSum(grid) {
    const m = grid.length;
    const n = grid[0].length;
    const dp = Array.from({ length: m }, () => new Array(n));
    dp[0][0] = grid[0][0];
    for (let i = 1; i < m; i++)
        dp[i][0] = dp[i - 1][0] + grid[i][0];
    for (let j = 1; j < n; j++)
        dp[0][j] = dp[0][j - 1] + grid[0][j];
    for (let i = 1; i < m; i++) {
        for (let j = 1; j < n; j++) {
            dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
        }
    }
    return dp[m - 1][n - 1];
}
// 5. Word Search
function exist(board, word) {
    const dfsSearch = (i, j, k) => {
        if (k === word.length)
            return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] !== word[k])
            return false;
        const temp = board[i][j];
        board[i][j] = '#';
        const found = dfsSearch(i + 1, j, k + 1) || dfsSearch(i - 1, j, k + 1) ||
            dfsSearch(i, j + 1, k + 1) || dfsSearch(i, j - 1, k + 1);
        board[i][j] = temp;
        return found;
    };
    for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[0].length; j++) {
            if (dfsSearch(i, j, 0))
                return true;
        }
    }
    return false;
}
// 6. Surrounded Regions
function solve(board) {
    if (board.length === 0)
        return;
    const m = board.length;
    const n = board[0].length;
    const dfsMark = (i, j) => {
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] !== 'O')
            return;
        board[i][j] = '#';
        dfsMark(i + 1, j);
        dfsMark(i - 1, j);
        dfsMark(i, j + 1);
        dfsMark(i, j - 1);
    };
    // Mark boundary 'O's and connected 'O's
    for (let i = 0; i < m; i++) {
        if (board[i][0] === 'O')
            dfsMark(i, 0);
        if (board[i][n - 1] === 'O')
            dfsMark(i, n - 1);
    }
    for (let j = 0; j < n; j++) {
        if (board[0][j] === 'O')
            dfsMark(0, j);
        if (board[m - 1][j] === 'O')
            dfsMark(m - 1, j);
    }
    // Convert remaining 'O's to 'X's and restore marked 'O's
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (board[i][j] === 'O')
                board[i][j] = 'X';
            else if (board[i][j] === '#')
                board[i][j] = 'O';
        }
    }
}
// 7. Pacific Atlantic Water Flow
function pacificAtlantic(heights) {
    const m = heights.length;
    const n = heights[0].length;
    const pacific = Array.from({ length: m }, () => new Array(n).fill(false));
    const atlantic = Array.from({ length: m }, () => new Array(n).fill(false));
    const dfsFlow = (visited, i, j, prevHeight) => {
        if (i < 0 || i >= m || j < 0 || j >= n || visited[i][j] || heights[i][j] < prevHeight)
            return;
        visited[i][j] = true;
        dfsFlow(visited, i + 1, j, heights[i][j]);
        dfsFlow(visited, i - 1, j, heights[i][j]);
        dfsFlow(visited, i, j + 1, heights[i][j]);
        dfsFlow(visited, i, j - 1, heights[i][j]);
    };
    for (let i = 0; i < m; i++) {
        dfsFlow(pacific, i, 0, heights[i][0]);
        dfsFlow(atlantic, i, n - 1, heights[i][n - 1]);
    }
    for (let j = 0; j < n; j++) {
        dfsFlow(pacific, 0, j, heights[0][j]);
        dfsFlow(atlantic, m - 1, j, heights[m - 1][j]);
    }
    const result = [];
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (pacific[i][j] && atlantic[i][j]) {
                result.push([i, j]);
            }
        }
    }
    return result;
}
// 8. 01 Matrix
function updateMatrix(mat) {
    const m = mat.length;
    const n = mat[0].length;
    const result = Array.from({ length: m }, () => new Array(n));
    const queue = [];
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (mat[i][j] === 0) {
                result[i][j] = 0;
                queue.push([i, j]);
            }
            else {
                result[i][j] = Infinity;
            }
        }
    }
    const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    while (queue.length > 0) {
        const [i, j] = queue.shift();
        for (const [di, dj] of dirs) {
            const x = i + di;
            const y = j + dj;
            if (x >= 0 && x < m && y >= 0 && y < n && result[x][y] > result[i][j] + 1) {
                result[x][y] = result[i][j] + 1;
                queue.push([x, y]);
            }
        }
    }
    return result;
}
// 9. Rotting Oranges
function orangesRotting(grid) {
    const m = grid.length;
    const n = grid[0].length;
    const queue = [];
    let fresh = 0;
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (grid[i][j] === 2)
                queue.push([i, j]);
            else if (grid[i][j] === 1)
                fresh++;
        }
    }
    if (fresh === 0)
        return 0;
    const dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]];
    let minutes = 0;
    while (queue.length > 0 && fresh > 0) {
        const size = queue.length;
        for (let k = 0; k < size; k++) {
            const [i, j] = queue.shift();
            for (const [di, dj] of dirs) {
                const x = i + di;
                const y = j + dj;
                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] === 1) {
                    grid[x][y] = 2;
                    fresh--;
                    queue.push([x, y]);
                }
            }
        }
        minutes++;
    }
    return fresh === 0 ? minutes : -1;
}
// 10. Shortest Path in Binary Matrix
function shortestPathBinaryMatrix(grid) {
    const n = grid.length;
    if (grid[0][0] !== 0 || grid[n - 1][n - 1] !== 0)
        return -1;
    const queue = [[0, 0, 1]];
    grid[0][0] = 1;
    const dirs = [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]];
    while (queue.length > 0) {
        const [x, y, dist] = queue.shift();
        if (x === n - 1 && y === n - 1)
            return dist;
        for (const [dx, dy] of dirs) {
            const nx = x + dx;
            const ny = y + dy;
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] === 0) {
                grid[nx][ny] = 1;
                queue.push([nx, ny, dist + 1]);
            }
        }
    }
    return -1;
}
// Test cases
console.log("=== Matrix Traversal Examples ===");
// Test 1: Number of Islands
const grid1 = [
    ['1', '1', '1', '1', '0'],
    ['1', '1', '0', '1', '0'],
    ['1', '1', '0', '0', '0'],
    ['0', '0', '0', '0', '0']
];
console.log("1. Number of Islands:", numIslands(grid1));
// Test 2: Max Area of Island
const grid2 = [
    [1, 1, 0, 0, 0],
    [1, 1, 0, 0, 0],
    [0, 0, 0, 1, 1],
    [0, 0, 0, 1, 1]
];
console.log("2. Max Area of Island:", maxAreaOfIsland(grid2));
// Test 3: Unique Paths
console.log("3. Unique Paths (3x7):", uniquePaths(3, 7));
// Test 4: Minimum Path Sum
const grid4 = [[1, 3, 1], [1, 5, 1], [4, 2, 1]];
console.log("4. Min Path Sum:", minPathSum(grid4));
// Test 5: Word Search
const board = [['A', 'B', 'C', 'E'], ['S', 'F', 'C', 'S'], ['A', 'D', 'E', 'E']];
console.log("5. Word Search 'ABCCED':", exist(board, "ABCCED"));
// Test 6: Surrounded Regions
const grid6 = [['X', 'X', 'X', 'X'], ['X', 'O', 'O', 'X'], ['X', 'X', 'O', 'X'], ['X', 'O', 'X', 'X']];
solve(grid6);
console.log("6. Surrounded Regions: solved");
// Test 7: Pacific Atlantic Water Flow
const heights = [[1, 2, 2, 3, 5], [3, 2, 3, 4, 4], [2, 4, 5, 3, 1], [6, 7, 1, 4, 5], [5, 1, 1, 2, 4]];
console.log("7. Pacific Atlantic:", pacificAtlantic(heights).length, "cells");
// Test 8: 01 Matrix
const mat = [[0, 0, 0], [0, 1, 0], [0, 0, 0]];
const distances = updateMatrix(mat);
console.log("8. 01 Matrix:", distances.map(row => `[${row.join(',')}]`).join(','));
// Test 9: Rotting Oranges
const oranges = [[2, 1, 1], [1, 1, 0], [0, 1, 1]];
console.log("9. Rotting Oranges:", orangesRotting(oranges), "minutes");
// Test 10: Shortest Path Binary Matrix
const binaryGrid = [[0, 0, 0], [1, 1, 0], [1, 1, 0]];
console.log("10. Shortest Path Binary:", shortestPathBinaryMatrix(binaryGrid));
//# sourceMappingURL=matrixtraversalExamples.js.map