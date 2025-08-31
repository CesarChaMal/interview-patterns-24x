import java.util.*;

public class UnionFindExamples {
    
    class UnionFind {
        int[] parent, rank;
        int components;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            if (rank[px] < rank[py]) { int temp = px; px = py; py = temp; }
            parent[py] = px;
            if (rank[px] == rank[py]) rank[px]++;
            components--;
            return true;
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    // 1. Number of Islands
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        UnionFind uf = new UnionFind(m * n);
        int islands = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    islands++;
                    int curr = i * n + j;
                    if (i > 0 && grid[i-1][j] == '1' && uf.union(curr, (i-1) * n + j)) islands--;
                    if (j > 0 && grid[i][j-1] == '1' && uf.union(curr, i * n + j - 1)) islands--;
                }
            }
        }
        return islands;
    }
    
    // 2. Friend Circles
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) uf.union(i, j);
            }
        }
        return uf.components;
    }
    
    // 3. Graph Valid Tree
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false;
        UnionFind uf = new UnionFind(n);
        
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) return false;
        }
        return uf.components == 1;
    }
    
    // 4. Accounts Merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToId = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        int id = 0;
        
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (!emailToId.containsKey(email)) emailToId.put(email, id++);
                emailToName.put(email, name);
            }
        }
        
        UnionFind uf = new UnionFind(id);
        for (List<String> account : accounts) {
            int firstId = emailToId.get(account.get(1));
            for (int i = 2; i < account.size(); i++) {
                uf.union(firstId, emailToId.get(account.get(i)));
            }
        }
        
        Map<Integer, List<String>> groups = new HashMap<>();
        for (String email : emailToId.keySet()) {
            int root = uf.find(emailToId.get(email));
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }
        
        List<List<String>> result = new ArrayList<>();
        for (List<String> emails : groups.values()) {
            Collections.sort(emails);
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(emails.get(0)));
            account.addAll(emails);
            result.add(account);
        }
        return result;
    }
    
    // 5. Redundant Connection
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length + 1);
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) return edge;
        }
        return new int[0];
    }
    
    // 6. Most Stones Removed
    public int removeStones(int[][] stones) {
        UnionFind uf = new UnionFind(20000);
        Set<Integer> seen = new HashSet<>();
        
        for (int[] stone : stones) {
            int x = stone[0], y = stone[1] + 10000;
            uf.union(x, y);
            seen.add(x);
            seen.add(y);
        }
        
        Set<Integer> roots = new HashSet<>();
        for (int coord : seen) {
            roots.add(uf.find(coord));
        }
        return stones.length - roots.size();
    }
    
    // 7. Satisfiability of Equality Equations
    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind(26);
        
        for (String eq : equations) {
            if (eq.charAt(1) == '=') {
                uf.union(eq.charAt(0) - 'a', eq.charAt(3) - 'a');
            }
        }
        
        for (String eq : equations) {
            if (eq.charAt(1) == '!' && uf.connected(eq.charAt(0) - 'a', eq.charAt(3) - 'a')) {
                return false;
            }
        }
        return true;
    }
    
    // 8. Swim in Rising Water
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] visited = new boolean[n][n];
        
        pq.offer(new int[]{0, 0, grid[0][0]});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], time = curr[2];
            
            if (visited[x][y]) continue;
            visited[x][y] = true;
            
            if (x == n - 1 && y == n - 1) return time;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited[nx][ny]) {
                    pq.offer(new int[]{nx, ny, Math.max(time, grid[nx][ny])});
                }
            }
        }
        return -1;
    }
    
    // 9. Connecting Cities With Minimum Cost
    public int minimumCost(int n, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);
        UnionFind uf = new UnionFind(n + 1);
        int cost = 0, edges = 0;
        
        for (int[] conn : connections) {
            if (uf.union(conn[0], conn[1])) {
                cost += conn[2];
                if (++edges == n - 1) break;
            }
        }
        return edges == n - 1 ? cost : -1;
    }
    
    // 10. Number of Operations to Make Network Connected
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;
        
        UnionFind uf = new UnionFind(n);
        for (int[] conn : connections) {
            uf.union(conn[0], conn[1]);
        }
        return uf.components - 1;
    }
    
    public static void main(String[] args) {
        UnionFindExamples examples = new UnionFindExamples();
        
        System.out.println("=== Union-Find Examples ===");
        
        // Test 1: Number of Islands
        char[][] grid = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
        System.out.println("Num Islands: " + examples.numIslands(grid));
        
        // Test 2: Friend Circles
        int[][] isConnected = {{1,1,0},{1,1,0},{0,0,1}};
        System.out.println("Find Circle Num: " + examples.findCircleNum(isConnected));
        
        // Test 3: Graph Valid Tree
        System.out.println("Valid Tree(5, [[0,1],[0,2],[0,3],[1,4]]): " + 
            examples.validTree(5, new int[][]{{0,1},{0,2},{0,3},{1,4}}));
        
        // Test 4: Accounts Merge
        List<List<String>> accounts = Arrays.asList(
            Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
            Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
            Arrays.asList("Mary","mary@mail.com"),
            Arrays.asList("John","johnnybravo@mail.com")
        );
        System.out.println("Accounts Merge: " + examples.accountsMerge(accounts));
        
        // Test 5: Redundant Connection
        System.out.println("Find Redundant Connection([[1,2],[1,3],[2,3]]): " + 
            Arrays.toString(examples.findRedundantConnection(new int[][]{{1,2},{1,3},{2,3}})));
        
        // Test 6: Most Stones Removed
        System.out.println("Remove Stones([[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]): " + 
            examples.removeStones(new int[][]{{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}}));
        
        // Test 7: Satisfiability of Equality Equations
        System.out.println("Equations Possible(['a==b','b!=a']): " + 
            examples.equationsPossible(new String[]{"a==b","b!=a"}));
        
        // Test 8: Swim in Rising Water
        int[][] swimGrid = {{0,2},{1,3}};
        System.out.println("Swim in Water: " + examples.swimInWater(swimGrid));
        
        // Test 9: Connecting Cities
        System.out.println("Minimum Cost(3, [[1,2,5],[1,3,6],[2,3,1]]): " + 
            examples.minimumCost(3, new int[][]{{1,2,5},{1,3,6},{2,3,1}}));
        
        // Test 10: Make Connected
        System.out.println("Make Connected(4, [[0,1],[0,2],[1,2]]): " + 
            examples.makeConnected(4, new int[][]{{0,1},{0,2},{1,2}}));
    }
}