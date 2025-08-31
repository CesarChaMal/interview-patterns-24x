import java.util.*;

public class BFSExamples {
    
    // 1. Binary Tree Level Order Traversal
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }
    
    // 2. Number of Islands
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    bfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }
    
    private void bfs(char[][] grid, int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        grid[i][j] = '0';
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0], y = curr[1] + dir[1];
                if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == '1') {
                    grid[x][y] = '0';
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }
    
    // 3. Rotting Oranges
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh++;
            }
        }
        
        if (fresh == 0) return 0;
        
        int minutes = 0;
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] dir : dirs) {
                    int x = curr[0] + dir[0], y = curr[1] + dir[1];
                    if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        queue.offer(new int[]{x, y});
                        fresh--;
                    }
                }
            }
            minutes++;
        }
        
        return fresh == 0 ? minutes - 1 : -1;
    }
    
    // 4. Word Ladder
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return level;
                
                char[] chars = word.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[j] = c;
                        String newWord = new String(chars);
                        if (wordSet.contains(newWord)) {
                            wordSet.remove(newWord);
                            queue.offer(newWord);
                        }
                    }
                    chars[j] = original;
                }
            }
            level++;
        }
        return 0;
    }
    
    // 5. Minimum Depth of Binary Tree
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null) return depth;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            depth++;
        }
        return depth;
    }
    
    // 6. Open the Lock
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        
        if (dead.contains("0000")) return -1;
        
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        visited.add("0000");
        int steps = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) return steps;
                
                for (int j = 0; j < 4; j++) {
                    char c = curr.charAt(j);
                    String up = curr.substring(0, j) + (c == '9' ? '0' : (char)(c + 1)) + curr.substring(j + 1);
                    String down = curr.substring(0, j) + (c == '0' ? '9' : (char)(c - 1)) + curr.substring(j + 1);
                    
                    if (!dead.contains(up) && !visited.contains(up)) {
                        visited.add(up);
                        queue.offer(up);
                    }
                    if (!dead.contains(down) && !visited.contains(down)) {
                        visited.add(down);
                        queue.offer(down);
                    }
                }
            }
            steps++;
        }
        return -1;
    }
    
    // 7. Binary Tree Right Side View
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == size - 1) result.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return result;
    }
    
    // 8. Shortest Path in Binary Matrix
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] == 1) return -1;
        int n = grid.length;
        if (n == 1) return 1;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 1});
        grid[0][0] = 1;
        
        int[][] dirs = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1], dist = curr[2];
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx == n - 1 && ny == n - 1) return dist + 1;
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                    grid[nx][ny] = 1;
                    queue.offer(new int[]{nx, ny, dist + 1});
                }
            }
        }
        return -1;
    }
    
    // 9. Perfect Squares
    public int numSquares(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(n);
        visited.add(n);
        int level = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                for (int j = 1; j * j <= curr; j++) {
                    int next = curr - j * j;
                    if (next == 0) return level;
                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }
        }
        return level;
    }
    
    // 10. Walls and Gates
    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                if (rooms[i][j] == 0) queue.offer(new int[]{i, j});
            }
        }
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0], y = curr[1] + dir[1];
                if (x >= 0 && x < rooms.length && y >= 0 && y < rooms[0].length && 
                    rooms[x][y] == Integer.MAX_VALUE) {
                    rooms[x][y] = rooms[curr[0]][curr[1]] + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }
    
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }
    
    public static void main(String[] args) {
        BFSExamples examples = new BFSExamples();
        
        System.out.println("=== BFS Examples ===");
        
        // Test 1: Level Order Traversal
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println("Level Order: " + examples.levelOrder(root));
        
        // Test 2: Number of Islands
        char[][] grid = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println("Number of Islands: " + examples.numIslands(grid));
        
        // Test 3: Rotting Oranges
        int[][] oranges = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("Rotting Oranges: " + examples.orangesRotting(oranges));
        
        // Test 4: Word Ladder
        System.out.println("Word Ladder: " + examples.ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
        
        // Test 5: Minimum Depth
        System.out.println("Min Depth: " + examples.minDepth(root));
        
        // Test 6: Open the Lock
        System.out.println("Open Lock: " + examples.openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202"));
        
        // Test 7: Right Side View
        System.out.println("Right Side View: " + examples.rightSideView(root));
        
        // Test 8: Shortest Path Binary Matrix
        int[][] matrix = {{0,0,0},{1,1,0},{1,1,0}};
        System.out.println("Shortest Path: " + examples.shortestPathBinaryMatrix(matrix));
        
        // Test 9: Perfect Squares
        System.out.println("Perfect Squares(12): " + examples.numSquares(12));
        
        // Test 10: Walls and Gates
        int[][] rooms = {{Integer.MAX_VALUE,-1,0,Integer.MAX_VALUE}};
        examples.wallsAndGates(rooms);
        System.out.println("Walls and Gates completed");
    }
}