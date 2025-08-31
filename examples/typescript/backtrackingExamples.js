"use strict";
// 1. Permutations
function permute(nums) {
    const result = [];
    const backtrack = (path, used) => {
        if (path.length === nums.length) {
            result.push([...path]);
        }
        else {
            for (let i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    path.push(nums[i]);
                    backtrack(path, used);
                    path.pop();
                    used[i] = false;
                }
            }
        }
    };
    backtrack([], new Array(nums.length).fill(false));
    return result;
}
// 2. Combinations
function combine(n, k) {
    const result = [];
    const backtrack = (start, path) => {
        if (path.length === k) {
            result.push([...path]);
        }
        else {
            for (let i = start; i <= n; i++) {
                path.push(i);
                backtrack(i + 1, path);
                path.pop();
            }
        }
    };
    backtrack(1, []);
    return result;
}
// 3. Subsets
function subsets(nums) {
    const result = [];
    const backtrack = (start, path) => {
        result.push([...path]);
        for (let i = start; i < nums.length; i++) {
            path.push(nums[i]);
            backtrack(i + 1, path);
            path.pop();
        }
    };
    backtrack(0, []);
    return result;
}
// 4. N-Queens
function solveNQueens(n) {
    const result = [];
    const isValid = (board, row, col) => {
        for (let i = 0; i < row; i++) {
            if (board[i] === col || Math.abs(board[i] - col) === Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    };
    const backtrack = (board, row) => {
        if (row === n) {
            result.push(board.map(col => '.'.repeat(col) + 'Q' + '.'.repeat(n - col - 1)));
        }
        else {
            for (let col = 0; col < n; col++) {
                if (isValid(board, row, col)) {
                    board[row] = col;
                    backtrack(board, row + 1);
                }
            }
        }
    };
    backtrack(new Array(n).fill(-1), 0);
    return result;
}
// 5. Generate Parentheses
function generateParenthesis(n) {
    const result = [];
    const backtrack = (path, open, close) => {
        if (path.length === 2 * n) {
            result.push(path);
        }
        else {
            if (open < n)
                backtrack(path + '(', open + 1, close);
            if (close < open)
                backtrack(path + ')', open, close + 1);
        }
    };
    backtrack('', 0, 0);
    return result;
}
// 6. Word Search
function exist(board, word) {
    const backtrack = (i, j, k) => {
        if (k === word.length)
            return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] !== word[k])
            return false;
        const temp = board[i][j];
        board[i][j] = '#';
        const found = backtrack(i + 1, j, k + 1) || backtrack(i - 1, j, k + 1) ||
            backtrack(i, j + 1, k + 1) || backtrack(i, j - 1, k + 1);
        board[i][j] = temp;
        return found;
    };
    for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[0].length; j++) {
            if (backtrack(i, j, 0))
                return true;
        }
    }
    return false;
}
// 7. Palindrome Partitioning
function partition(s) {
    const result = [];
    const isPalindrome = (str) => str === str.split('').reverse().join('');
    const backtrack = (start, path) => {
        if (start === s.length) {
            result.push([...path]);
        }
        else {
            for (let end = start + 1; end <= s.length; end++) {
                const substr = s.substring(start, end);
                if (isPalindrome(substr)) {
                    path.push(substr);
                    backtrack(end, path);
                    path.pop();
                }
            }
        }
    };
    backtrack(0, []);
    return result;
}
// 8. Letter Combinations of Phone Number
function letterCombinations(digits) {
    if (!digits)
        return [];
    const mapping = {
        '2': 'abc', '3': 'def', '4': 'ghi', '5': 'jkl',
        '6': 'mno', '7': 'pqrs', '8': 'tuv', '9': 'wxyz'
    };
    const result = [];
    const backtrack = (index, path) => {
        if (index === digits.length) {
            result.push(path);
        }
        else {
            for (const c of mapping[digits[index]]) {
                backtrack(index + 1, path + c);
            }
        }
    };
    backtrack(0, '');
    return result;
}
// 9. Sudoku Solver
function solveSudoku(board) {
    const isValid = (row, col, c) => {
        for (let i = 0; i < 9; i++) {
            if (board[i][col] === c || board[row][i] === c ||
                board[3 * Math.floor(row / 3) + Math.floor(i / 3)][3 * Math.floor(col / 3) + i % 3] === c) {
                return false;
            }
        }
        return true;
    };
    const backtrack = () => {
        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                if (board[i][j] === '.') {
                    for (let c = '1'; c <= '9'; c = String.fromCharCode(c.charCodeAt(0) + 1)) {
                        if (isValid(i, j, c)) {
                            board[i][j] = c;
                            if (backtrack())
                                return true;
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    };
    backtrack();
}
// 10. Combination Sum
function combinationSum(candidates, target) {
    const result = [];
    const backtrack = (start, path, sum) => {
        if (sum === target) {
            result.push([...path]);
        }
        else if (sum < target) {
            for (let i = start; i < candidates.length; i++) {
                path.push(candidates[i]);
                backtrack(i, path, sum + candidates[i]);
                path.pop();
            }
        }
    };
    backtrack(0, [], 0);
    return result;
}
// Test cases
console.log("=== Backtracking Examples ===");
// Test 1: Permutations
console.log("1. Permutations([1,2,3]):", permute([1, 2, 3]));
// Test 2: Combinations
console.log("2. Combinations(4,2):", combine(4, 2));
// Test 3: Subsets
console.log("3. Subsets([1,2,3]):", subsets([1, 2, 3]));
// Test 4: N-Queens
console.log("4. N-Queens(4):", solveNQueens(4).length, "solutions");
// Test 5: Generate Parentheses
console.log("5. Generate Parentheses(3):", generateParenthesis(3));
// Test 6: Word Search
const board = [['A', 'B', 'C', 'E'], ['S', 'F', 'C', 'S'], ['A', 'D', 'E', 'E']];
console.log("6. Word Search 'ABCCED':", exist(board, "ABCCED"));
// Test 7: Palindrome Partitioning
console.log("7. Palindrome Partition 'aab':", partition("aab"));
// Test 8: Letter Combinations
console.log("8. Letter Combinations '23':", letterCombinations("23"));
// Test 9: Sudoku Solver
const sudokuBoard = [['5', '3', '.', '.', '7', '.', '.', '.', '.'], ['6', '.', '.', '1', '9', '5', '.', '.', '.'], ['.', '9', '8', '.', '.', '.', '.', '6', '.'], ['8', '.', '.', '.', '6', '.', '.', '.', '3'], ['4', '.', '.', '8', '.', '3', '.', '.', '1'], ['7', '.', '.', '.', '2', '.', '.', '.', '6'], ['.', '6', '.', '.', '.', '.', '2', '8', '.'], ['.', '.', '.', '4', '1', '9', '.', '.', '5'], ['.', '.', '.', '.', '8', '.', '.', '7', '9']];
solveSudoku(sudokuBoard);
console.log("9. Sudoku Solver: solved");
// Test 10: Combination Sum
console.log("10. Combination Sum([2,3,6,7], 7):", combinationSum([2, 3, 6, 7], 7));
//# sourceMappingURL=backtrackingExamples.js.map