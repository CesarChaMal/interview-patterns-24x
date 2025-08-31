import java.util.*;

public class MatrixTraversalExamples {
    
    // 1. Number of Islands
    public int numIslands(char[][] grid) {
        if (grid.length == 0) return 0;
        int count = 0;
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }
    
    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1') return;
        grid[i][j] = '0';
        dfs(grid, i+1, j);
        dfs(grid, i-1, j);
        dfs(grid, i, j+1);
        dfs(grid, i, j-1);
    }
    
    // 2. Max Area of Island
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfsArea(grid, i, j));
                }
            }
        }
        return maxArea;
    }
    
    private int dfsArea(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) return 0;
        grid[i][j] = 0;
        return 1 + dfsArea(grid, i+1, j) + dfsArea(grid, i-1, j) + dfsArea(grid, i, j+1) + dfsArea(grid, i, j-1);
    }
    
    // 3. Unique Paths
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
    
    // 4. Minimum Path Sum
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        
        for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
    
    // 5. Word Search
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfsSearch(board, word, i, j, 0)) return true;
            }
        }
        return false;
    }
    
    private boolean dfsSearch(char[][] board, String word, int i, int j, int k) {
        if (k == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            board[i][j] != word.charAt(k)) return false;
        
        char temp = board[i][j];
        board[i][j] = '#';
        boolean found = dfsSearch(board, word, i+1, j, k+1) || dfsSearch(board, word, i-1, j, k+1) ||
                       dfsSearch(board, word, i, j+1, k+1) || dfsSearch(board, word, i, j-1, k+1);
        board[i][j] = temp;
        return found;
    }
    
    // 6. Surrounded Regions
    public void solve(char[][] board) {
        if (board.length == 0) return;
        int m = board.length, n = board[0].length;
        
        // Mark boundary 'O's and connected 'O's
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfsMark(board, i, 0);
            if (board[i][n-1] == 'O') dfsMark(board, i, n-1);
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfsMark(board, 0, j);
            if (board[m-1][j] == 'O') dfsMark(board, m-1, j);
        }
        
        // Convert remaining 'O's to 'X's and restore marked 'O's
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }
    
    private void dfsMark(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') return;
        board[i][j] = '#';
        dfsMark(board, i+1, j);
        dfsMark(board, i-1, j);
        dfsMark(board, i, j+1);
        dfsMark(board, i, j-1);
    }
    
    // 7. Pacific Atlantic Water Flow
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            dfsFlow(heights, pacific, i, 0, heights[i][0]);
            dfsFlow(heights, atlantic, i, n-1, heights[i][n-1]);
        }
        for (int j = 0; j < n; j++) {
            dfsFlow(heights, pacific, 0, j, heights[0][j]);
            dfsFlow(heights, atlantic, m-1, j, heights[m-1][j]);
        }
        
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }
    
    private void dfsFlow(int[][] heights, boolean[][] visited, int i, int j, int prevHeight) {
        if (i < 0 || i >= heights.length || j < 0 || j >= heights[0].length || 
            visited[i][j] || heights[i][j] < prevHeight) return;
        
        visited[i][j] = true;
        dfsFlow(heights, visited, i+1, j, heights[i][j]);
        dfsFlow(heights, visited, i-1, j, heights[i][j]);
        dfsFlow(heights, visited, i, j+1, heights[i][j]);
        dfsFlow(heights, visited, i, j-1, heights[i][j]);
    }
    
    // 8. 01 Matrix
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] result = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    result[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else {
                    result[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0], y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && result[x][y] > result[curr[0]][curr[1]] + 1) {
                    result[x][y] = result[curr[0]][curr[1]] + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
        return result;
    }
    
    // 9. Rotting Oranges
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh++;
            }
        }
        
        if (fresh == 0) return 0;
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        int minutes = 0;
        
        while (!queue.isEmpty() && fresh > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] dir : dirs) {
                    int x = curr[0] + dir[0], y = curr[1] + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        fresh--;
                        queue.offer(new int[]{x, y});
                    }
                }
            }
            minutes++;
        }
        
        return fresh == 0 ? minutes : -1;
    }
    
    // 10. Shortest Path in Binary Matrix
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n-1][n-1] != 0) return -1;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 1});
        grid[0][0] = 1;
        
        int[][] dirs = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1], dist = curr[2];
            
            if (x == n-1 && y == n-1) return dist;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                    grid[nx][ny] = 1;
                    queue.offer(new int[]{nx, ny, dist + 1});
                }
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        MatrixTraversalExamples examples = new MatrixTraversalExamples();
        
        System.out.println("=== Matrix Traversal Examples ===");
        
        // Test 1: Number of Islands
        char[][] grid1 = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
        System.out.println("1. Number of Islands: " + examples.numIslands(grid1));
        
        // Test 2: Max Area of Island
        int[][] grid2 = {{0,0,1,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,1,1,0,1,0,0,0,0,0,0,0,0}};
        System.out.println("2. Max Area of Island: " + examples.maxAreaOfIsland(grid2));
        
        // Test 3: Unique Paths
        System.out.println("3. Unique Paths (3,7): " + examples.uniquePaths(3, 7));
        
        // Test 4: Minimum Path Sum
        int[][] grid4 = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println("4. Min Path Sum: " + examples.minPathSum(grid4));
        
        // Test 5: Word Search
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("5. Word Search 'ABCCED': " + examples.exist(board, "ABCCED"));
        
        // Test 6: Surrounded Regions
        char[][] board6 = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        examples.solve(board6);
        System.out.println("6. Surrounded Regions completed");
        
        // Test 7: Pacific Atlantic Water Flow
        int[][] heights = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        System.out.println("7. Pacific Atlantic: " + examples.pacificAtlantic(heights));
        
        // Test 8: 01 Matrix
        int[][] mat = {{0,0,0},{0,1,0},{1,1,1}};
        int[][] result8 = examples.updateMatrix(mat);
        System.out.println("8. 01 Matrix: " + Arrays.deepToString(result8));
        
        // Test 9: Rotting Oranges
        int[][] oranges = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("9. Rotting Oranges: " + examples.orangesRotting(oranges));
        
        // Test 10: Shortest Path Binary Matrix
        int[][] binaryGrid = {{0,0,0},{1,1,0},{1,1,0}};
        System.out.println("10. Shortest Path Binary Matrix: " + examples.shortestPathBinaryMatrix(binaryGrid));
    }
}