import java.util.*;

public class DijkstraExamples {
    
    // 1. Network Delay Time
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(new int[]{time[1], time[2]});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});
        
        Set<Integer> visited = new HashSet<>();
        int maxTime = 0;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], time = curr[1];
            
            if (visited.contains(node)) continue;
            visited.add(node);
            maxTime = Math.max(maxTime, time);
            
            if (graph.containsKey(node)) {
                for (int[] edge : graph.get(node)) {
                    int next = edge[0], weight = edge[1];
                    if (!visited.contains(next)) {
                        pq.offer(new int[]{next, time + weight});
                    }
                }
            }
        }
        
        return visited.size() == n ? maxTime : -1;
    }
    
    // 2. Cheapest Flights Within K Stops
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.computeIfAbsent(flight[0], x -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0});
        
        Map<Integer, Integer> visited = new HashMap<>();
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], cost = curr[1], stops = curr[2];
            
            if (node == dst) return cost;
            if (stops > k) continue;
            if (visited.containsKey(node) && visited.get(node) <= stops) continue;
            
            visited.put(node, stops);
            
            if (graph.containsKey(node)) {
                for (int[] edge : graph.get(node)) {
                    int next = edge[0], price = edge[1];
                    pq.offer(new int[]{next, cost + price, stops + 1});
                }
            }
        }
        
        return -1;
    }
    
    // 3. Path with Maximum Probability
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, List<double[]>> graph = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0], v = edges[i][1];
            double prob = succProb[i];
            graph.computeIfAbsent(u, x -> new ArrayList<>()).add(new double[]{v, prob});
            graph.computeIfAbsent(v, x -> new ArrayList<>()).add(new double[]{u, prob});
        }
        
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[1], a[1]));
        pq.offer(new double[]{start, 1.0});
        
        Set<Integer> visited = new HashSet<>();
        
        while (!pq.isEmpty()) {
            double[] curr = pq.poll();
            int node = (int)curr[0];
            double prob = curr[1];
            
            if (node == end) return prob;
            if (visited.contains(node)) continue;
            visited.add(node);
            
            if (graph.containsKey(node)) {
                for (double[] edge : graph.get(node)) {
                    int next = (int)edge[0];
                    double nextProb = edge[1];
                    if (!visited.contains(next)) {
                        pq.offer(new double[]{next, prob * nextProb});
                    }
                }
            }
        }
        
        return 0.0;
    }
    
    // 4. Path With Minimum Effort
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 0});
        
        boolean[][] visited = new boolean[m][n];
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], effort = curr[2];
            
            if (x == m - 1 && y == n - 1) return effort;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    int newEffort = Math.max(effort, Math.abs(heights[nx][ny] - heights[x][y]));
                    pq.offer(new int[]{nx, ny, newEffort});
                }
            }
        }
        
        return -1;
    }
    
    // 5. Find the City With the Smallest Number of Neighbors at a Threshold Distance
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }
        
        for (int[] edge : edges) {
            dist[edge[0]][edge[1]] = edge[2];
            dist[edge[1]][edge[0]] = edge[2];
        }
        
        // Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        
        int minCount = n, result = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) count++;
            }
            if (count <= minCount) {
                minCount = count;
                result = i;
            }
        }
        
        return result;
    }
    
    // 6. Minimum Cost to Make at Least One Valid Path in a Grid
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 0});
        
        boolean[][] visited = new boolean[m][n];
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], cost = curr[2];
            
            if (x == m - 1 && y == n - 1) return cost;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dirs[dir][0], ny = y + dirs[dir][1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    int newCost = cost + (grid[x][y] == dir + 1 ? 0 : 1);
                    pq.offer(new int[]{nx, ny, newCost});
                }
            }
        }
        
        return -1;
    }
    
    // 7. Number of Ways to Arrive at Destination
    public int countPaths(int n, int[][] roads) {
        int MOD = 1000000007;
        Map<Integer, List<long[]>> graph = new HashMap<>();
        
        for (int[] road : roads) {
            graph.computeIfAbsent(road[0], x -> new ArrayList<>()).add(new long[]{road[1], road[2]});
            graph.computeIfAbsent(road[1], x -> new ArrayList<>()).add(new long[]{road[0], road[2]});
        }
        
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{0, 0});
        
        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1;
        
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int node = (int)curr[0];
            long time = curr[1];
            
            if (time > dist[node]) continue;
            
            if (graph.containsKey(node)) {
                for (long[] edge : graph.get(node)) {
                    int next = (int)edge[0];
                    long weight = edge[1];
                    long newTime = time + weight;
                    
                    if (newTime < dist[next]) {
                        dist[next] = newTime;
                        ways[next] = ways[node];
                        pq.offer(new long[]{next, newTime});
                    } else if (newTime == dist[next]) {
                        ways[next] = (ways[next] + ways[node]) % MOD;
                    }
                }
            }
        }
        
        return (int)ways[n - 1];
    }
    
    // 8. Swim in Rising Water
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, grid[0][0]});
        
        boolean[][] visited = new boolean[n][n];
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], time = curr[2];
            
            if (x == n - 1 && y == n - 1) return time;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited[nx][ny]) {
                    pq.offer(new int[]{nx, ny, Math.max(time, grid[nx][ny])});
                }
            }
        }
        
        return -1;
    }
    
    // 9. Shortest Path in Binary Matrix
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
        
        int[][] dirs = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 1});
        
        boolean[][] visited = new boolean[n][n];
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], dist = curr[2];
            
            if (x == n - 1 && y == n - 1) return dist;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && 
                    grid[nx][ny] == 0 && !visited[nx][ny]) {
                    pq.offer(new int[]{nx, ny, dist + 1});
                }
            }
        }
        
        return -1;
    }
    
    // 10. Minimum Spanning Tree (Kruskal's Algorithm)
    public int minimumSpanningTree(int n, int[][] edges) {
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        UnionFind uf = new UnionFind(n);
        int cost = 0, edgesUsed = 0;
        
        for (int[] edge : edges) {
            if (uf.union(edge[0], edge[1])) {
                cost += edge[2];
                edgesUsed++;
                if (edgesUsed == n - 1) break;
            }
        }
        
        return edgesUsed == n - 1 ? cost : -1;
    }
    
    // Union-Find for MST
    class UnionFind {
        int[] parent, rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }
    
    public static void main(String[] args) {
        DijkstraExamples examples = new DijkstraExamples();
        
        System.out.println("=== Dijkstra's Algorithm Examples ===");
        
        // Test 1: Network Delay Time
        int[][] times = {{2,1,1},{2,3,1},{3,4,1}};
        System.out.println("1. Network Delay Time: " + examples.networkDelayTime(times, 4, 2));
        
        // Test 2: Cheapest Flights
        int[][] flights = {{0,1,100},{1,2,100},{0,2,500}};
        System.out.println("2. Cheapest Flights: " + examples.findCheapestPrice(3, flights, 0, 2, 1));
        
        // Test 3: Max Probability
        int[][] edges = {{0,1},{1,2},{0,2}};
        double[] succProb = {0.5,0.5,0.2};
        System.out.println("3. Max Probability: " + examples.maxProbability(3, edges, succProb, 0, 2));
        
        // Test 4: Minimum Effort Path
        int[][] heights = {{1,2,2},{3,8,2},{5,3,5}};
        System.out.println("4. Minimum Effort Path: " + examples.minimumEffortPath(heights));
        
        // Test 5: Find the City
        int[][] cityEdges = {{0,1,3},{1,2,1},{1,3,4},{2,3,1}};
        System.out.println("5. Find the City: " + examples.findTheCity(4, cityEdges, 4));
        
        // Test 6: Min Cost Valid Path
        int[][] grid = {{1,1,1,1},{2,2,2,2},{1,1,1,1},{2,2,2,2}};
        System.out.println("6. Min Cost Valid Path: " + examples.minCost(grid));
        
        // Test 7: Count Paths
        int[][] roads = {{0,6,7},{0,1,2},{1,2,3},{1,3,3},{6,3,3},{3,5,1},{6,5,1},{2,5,1},{0,4,5},{4,6,2}};
        System.out.println("7. Count Paths: " + examples.countPaths(7, roads));
        
        // Test 8: Swim in Water
        int[][] swimGrid = {{0,2},{1,3}};
        System.out.println("8. Swim in Water: " + examples.swimInWater(swimGrid));
        
        // Test 9: Shortest Path Binary Matrix
        int[][] binaryGrid = {{0,0,0},{1,1,0},{1,1,0}};
        System.out.println("9. Shortest Path Binary Matrix: " + examples.shortestPathBinaryMatrix(binaryGrid));
        
        // Test 10: Minimum Spanning Tree
        int[][] mstEdges = {{0,1,10},{0,2,6},{0,3,5},{1,3,15},{2,3,4}};
        System.out.println("10. Minimum Spanning Tree: " + examples.minimumSpanningTree(4, mstEdges));
    }
}