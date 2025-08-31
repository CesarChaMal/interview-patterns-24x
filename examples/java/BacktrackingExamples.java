import java.util.*;

public class BacktrackingExamples {
    
    // 1. Permutations
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, boolean[] used) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    path.add(nums[i]);
                    backtrack(result, path, nums, used);
                    path.remove(path.size() - 1);
                    used[i] = false;
                }
            }
        }
    }
    
    // 2. Combinations
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), 1, n, k);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> path, int start, int n, int k) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
        } else {
            for (int i = start; i <= n; i++) {
                path.add(i);
                backtrack(result, path, i + 1, n, k);
                path.remove(path.size() - 1);
            }
        }
    }
    
    // 3. Subsets
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, int start) {
        result.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(result, path, nums, i + 1);
            path.remove(path.size() - 1);
        }
    }
    
    // 4. N-Queens
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        backtrack(result, board, 0, n);
        return result;
    }
    
    private void backtrack(List<List<String>> result, int[] board, int row, int n) {
        if (row == n) {
            List<String> solution = new ArrayList<>();
            for (int col : board) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    sb.append(i == col ? 'Q' : '.');
                }
                solution.add(sb.toString());
            }
            result.add(solution);
        } else {
            for (int col = 0; col < n; col++) {
                if (isValid(board, row, col)) {
                    board[row] = col;
                    backtrack(result, board, row + 1, n);
                }
            }
        }
    }
    
    private boolean isValid(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    // 5. Generate Parentheses
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String path, int open, int close, int n) {
        if (path.length() == 2 * n) {
            result.add(path);
        } else {
            if (open < n) backtrack(result, path + "(", open + 1, close, n);
            if (close < open) backtrack(result, path + ")", open, close + 1, n);
        }
    }
    
    // 6. Word Search
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backtrack(board, word, i, j, 0)) return true;
            }
        }
        return false;
    }
    
    private boolean backtrack(char[][] board, String word, int i, int j, int k) {
        if (k == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(k)) return false;
        char temp = board[i][j];
        board[i][j] = '#';
        boolean found = backtrack(board, word, i+1, j, k+1) || backtrack(board, word, i-1, j, k+1) ||
                       backtrack(board, word, i, j+1, k+1) || backtrack(board, word, i, j-1, k+1);
        board[i][j] = temp;
        return found;
    }
    
    // 7. Palindrome Partitioning
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), s, 0);
        return result;
    }
    
    private void backtrack(List<List<String>> result, List<String> path, String s, int start) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
        } else {
            for (int end = start + 1; end <= s.length(); end++) {
                String substr = s.substring(start, end);
                if (isPalindrome(substr)) {
                    path.add(substr);
                    backtrack(result, path, s, end);
                    path.remove(path.size() - 1);
                }
            }
        }
    }
    
    private boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
    
    // 8. Letter Combinations of Phone Number
    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> result = new ArrayList<>();
        backtrack(result, "", digits, 0, mapping);
        return result;
    }
    
    private void backtrack(List<String> result, String path, String digits, int index, String[] mapping) {
        if (index == digits.length()) {
            result.add(path);
        } else {
            String letters = mapping[digits.charAt(index) - '0'];
            for (char c : letters.toCharArray()) {
                backtrack(result, path + c, digits, index + 1, mapping);
            }
        }
    }
    
    // 9. Sudoku Solver
    public void solveSudoku(char[][] board) {
        backtrack(board);
    }
    
    private boolean backtrack(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;
                            if (backtrack(board)) return true;
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == c || board[row][i] == c || board[3*(row/3)+i/3][3*(col/3)+i%3] == c) return false;
        }
        return true;
    }
    
    // 10. Combination Sum
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> path, int[] candidates, int target, int start) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
        } else if (target > 0) {
            for (int i = start; i < candidates.length; i++) {
                path.add(candidates[i]);
                backtrack(result, path, candidates, target - candidates[i], i);
                path.remove(path.size() - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        BacktrackingExamples examples = new BacktrackingExamples();
        
        System.out.println("=== Backtracking Examples ===");
        
        // Test 1: Permutations
        System.out.println("Permutations([1,2,3]): " + examples.permute(new int[]{1,2,3}));
        
        // Test 2: Combinations
        System.out.println("Combinations(4,2): " + examples.combine(4,2));
        
        // Test 3: Subsets
        System.out.println("Subsets([1,2,3]): " + examples.subsets(new int[]{1,2,3}));
        
        // Test 4: N-Queens
        System.out.println("N-Queens(4): " + examples.solveNQueens(4).size() + " solutions");
        
        // Test 5: Generate Parentheses
        System.out.println("Generate Parentheses(3): " + examples.generateParenthesis(3));
        
        // Test 6: Word Search
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("Word Search 'ABCCED': " + examples.exist(board, "ABCCED"));
        
        // Test 7: Palindrome Partitioning
        System.out.println("Palindrome Partition 'aab': " + examples.partition("aab"));
        
        // Test 8: Letter Combinations
        System.out.println("Letter Combinations '23': " + examples.letterCombinations("23"));
        
        // Test 10: Combination Sum
        System.out.println("Combination Sum([2,3,6,7], 7): " + examples.combinationSum(new int[]{2,3,6,7}, 7));
    }
}