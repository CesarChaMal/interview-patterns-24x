// 1. Spiral Matrix
function spiralOrder(matrix) {
    if (!matrix || matrix.length === 0) return [];
    
    const result = [];
    let top = 0, bottom = matrix.length - 1;
    let left = 0, right = matrix[0].length - 1;
    
    while (top <= bottom && left <= right) {
        // Traverse right
        for (let col = left; col <= right; col++) {
            result.push(matrix[top][col]);
        }
        top++;
        
        // Traverse down
        for (let row = top; row <= bottom; row++) {
            result.push(matrix[row][right]);
        }
        right--;
        
        // Traverse left
        if (top <= bottom) {
            for (let col = right; col >= left; col--) {
                result.push(matrix[bottom][col]);
            }
            bottom--;
        }
        
        // Traverse up
        if (left <= right) {
            for (let row = bottom; row >= top; row--) {
                result.push(matrix[row][left]);
            }
            left++;
        }
    }
    
    return result;
}

// 2. Rotate Image
function rotate(matrix) {
    const n = matrix.length;
    
    // Transpose
    for (let i = 0; i < n; i++) {
        for (let j = i; j < n; j++) {
            [matrix[i][j], matrix[j][i]] = [matrix[j][i], matrix[i][j]];
        }
    }
    
    // Reverse each row
    for (let i = 0; i < n; i++) {
        matrix[i].reverse();
    }
}

// 3. Set Matrix Zeroes
function setZeroes(matrix) {
    const m = matrix.length, n = matrix[0].length;
    let firstRowZero = false, firstColZero = false;
    
    // Check if first row has zero
    for (let j = 0; j < n; j++) {
        if (matrix[0][j] === 0) {
            firstRowZero = true;
            break;
        }
    }
    
    // Check if first column has zero
    for (let i = 0; i < m; i++) {
        if (matrix[i][0] === 0) {
            firstColZero = true;
            break;
        }
    }
    
    // Mark zeros in first row and column
    for (let i = 1; i < m; i++) {
        for (let j = 1; j < n; j++) {
            if (matrix[i][j] === 0) {
                matrix[i][0] = 0;
                matrix[0][j] = 0;
            }
        }
    }
    
    // Set zeros based on marks
    for (let i = 1; i < m; i++) {
        for (let j = 1; j < n; j++) {
            if (matrix[i][0] === 0 || matrix[0][j] === 0) {
                matrix[i][j] = 0;
            }
        }
    }
    
    // Set first row to zero if needed
    if (firstRowZero) {
        for (let j = 0; j < n; j++) {
            matrix[0][j] = 0;
        }
    }
    
    // Set first column to zero if needed
    if (firstColZero) {
        for (let i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
    }
}

// 4. Word Search
function exist(board, word) {
    const m = board.length, n = board[0].length;
    
    function backtrack(i, j, k) {
        if (k === word.length) return true;
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] !== word[k]) {
            return false;
        }
        
        const temp = board[i][j];
        board[i][j] = '#';
        
        const found = backtrack(i + 1, j, k + 1) ||
                     backtrack(i - 1, j, k + 1) ||
                     backtrack(i, j + 1, k + 1) ||
                     backtrack(i, j - 1, k + 1);
        
        board[i][j] = temp;
        return found;
    }
    
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (backtrack(i, j, 0)) return true;
        }
    }
    return false;
}

// 5. Number of Islands
function numIslands(grid) {
    if (!grid || grid.length === 0) return 0;
    
    const m = grid.length, n = grid[0].length;
    let count = 0;
    
    function dfs(i, j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] === '0') {
            return;
        }
        
        grid[i][j] = '0';
        dfs(i + 1, j);
        dfs(i - 1, j);
        dfs(i, j + 1);
        dfs(i, j - 1);
    }
    
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (grid[i][j] === '1') {
                count++;
                dfs(i, j);
            }
        }
    }
    
    return count;
}

// 6. Surrounded Regions
function solve(board) {
    if (!board || board.length === 0) return;
    
    const m = board.length, n = board[0].length;
    
    function dfs(i, j) {
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] !== 'O') {
            return;
        }
        
        board[i][j] = 'T';
        dfs(i + 1, j);
        dfs(i - 1, j);
        dfs(i, j + 1);
        dfs(i, j - 1);
    }
    
    // Mark boundary-connected 'O's
    for (let i = 0; i < m; i++) {
        dfs(i, 0);
        dfs(i, n - 1);
    }
    for (let j = 0; j < n; j++) {
        dfs(0, j);
        dfs(m - 1, j);
    }
    
    // Convert remaining 'O's to 'X' and 'T's back to 'O'
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (board[i][j] === 'O') {
                board[i][j] = 'X';
            } else if (board[i][j] === 'T') {
                board[i][j] = 'O';
            }
        }
    }
}

// 7. Pacific Atlantic Water Flow
function pacificAtlantic(heights) {
    if (!heights || heights.length === 0) return [];
    
    const m = heights.length, n = heights[0].length;
    const pacific = Array.from({ length: m }, () => new Array(n).fill(false));
    const atlantic = Array.from({ length: m }, () => new Array(n).fill(false));
    
    function dfs(i, j, ocean, prevHeight) {
        if (i < 0 || i >= m || j < 0 || j >= n || 
            ocean[i][j] || heights[i][j] < prevHeight) {
            return;
        }
        
        ocean[i][j] = true;
        dfs(i + 1, j, ocean, heights[i][j]);
        dfs(i - 1, j, ocean, heights[i][j]);
        dfs(i, j + 1, ocean, heights[i][j]);
        dfs(i, j - 1, ocean, heights[i][j]);
    }
    
    // Start from Pacific borders
    for (let i = 0; i < m; i++) {
        dfs(i, 0, pacific, 0);
        dfs(i, n - 1, atlantic, 0);
    }
    for (let j = 0; j < n; j++) {
        dfs(0, j, pacific, 0);
        dfs(m - 1, j, atlantic, 0);
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

// 8. Game of Life
function gameOfLife(board) {
    const m = board.length, n = board[0].length;
    const directions = [[-1,-1],[-1,0],[-1,1],[0,-1],[0,1],[1,-1],[1,0],[1,1]];
    
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            let liveNeighbors = 0;
            
            for (const [di, dj] of directions) {
                const ni = i + di, nj = j + dj;
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && 
                    (board[ni][nj] === 1 || board[ni][nj] === 2)) {
                    liveNeighbors++;
                }
            }
            
            if (board[i][j] === 1 && (liveNeighbors < 2 || liveNeighbors > 3)) {
                board[i][j] = 2; // Live -> Dead
            }
            if (board[i][j] === 0 && liveNeighbors === 3) {
                board[i][j] = 3; // Dead -> Live
            }
        }
    }
    
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (board[i][j] === 2) board[i][j] = 0;
            if (board[i][j] === 3) board[i][j] = 1;
        }
    }
}

// 9. Valid Sudoku
function isValidSudoku(board) {
    const rows = Array.from({ length: 9 }, () => new Set());
    const cols = Array.from({ length: 9 }, () => new Set());
    const boxes = Array.from({ length: 9 }, () => new Set());
    
    for (let i = 0; i < 9; i++) {
        for (let j = 0; j < 9; j++) {
            const val = board[i][j];
            if (val === '.') continue;
            
            const boxIndex = Math.floor(i / 3) * 3 + Math.floor(j / 3);
            
            if (rows[i].has(val) || cols[j].has(val) || boxes[boxIndex].has(val)) {
                return false;
            }
            
            rows[i].add(val);
            cols[j].add(val);
            boxes[boxIndex].add(val);
        }
    }
    
    return true;
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
console.log("=== Matrix Traversal Examples ===");

const matrix = [[1,2,3],[4,5,6],[7,8,9]];
console.log("Spiral Order:", spiralOrder(matrix));

const rotateMatrix = [[1,2,3],[4,5,6],[7,8,9]];
rotate(rotateMatrix);
console.log("Rotated Matrix:", rotateMatrix);

const board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']];
console.log("Word Search 'ABCCED':", exist(board, "ABCCED"));

const grid = [['1','1','0','0','0'],['1','1','0','0','0'],['0','0','1','0','0'],['0','0','0','1','1']];
console.log("Number of Islands:", numIslands(grid));

const heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]];
console.log("Pacific Atlantic cells:", pacificAtlantic(heights).length);

const binaryGrid = [[0,0,0],[1,1,0],[1,1,0]];
console.log("Shortest Path Binary Matrix:", shortestPathBinaryMatrix(binaryGrid));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        spiralOrder,
        rotate,
        setZeroes,
        exist,
        numIslands,
        solve,
        pacificAtlantic,
        gameOfLife,
        isValidSudoku,
        shortestPathBinaryMatrix
    };
}