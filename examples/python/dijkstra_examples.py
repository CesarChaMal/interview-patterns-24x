import heapq
from collections import defaultdict

# 1. Dijkstra
def dijkstra(graph, start):
    distances = {node: float('inf') for node in graph}
    distances[start] = 0
    pq = [(0, start)]
    
    while pq:
        current_dist, current = heapq.heappop(pq)
        
        if current_dist > distances[current]:
            continue
        
        for neighbor, weight in graph[current]:
            distance = current_dist + weight
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                heapq.heappush(pq, (distance, neighbor))
    
    return distances

# 2. Network Delay Time
def network_delay_time(times, n, k):
    graph = defaultdict(list)
    for u, v, w in times:
        graph[u].append((v, w))
    
    # Ensure all nodes exist in graph
    for i in range(1, n + 1):
        if i not in graph:
            graph[i] = []
    
    distances = dijkstra(graph, k)
    max_time = max(distances[i] for i in range(1, n + 1))
    return max_time if max_time != float('inf') else -1

# 3. Cheapest Flights Within K Stops
def cheapest_flights_within_k_stops(n, flights, src, dst, k):
    graph = defaultdict(list)
    for u, v, w in flights:
        graph[u].append((v, w))
    
    # (cost, node, stops)
    pq = [(0, src, 0)]
    visited = {}
    
    while pq:
        cost, node, stops = heapq.heappop(pq)
        
        if node == dst:
            return cost
        
        if stops > k or (node in visited and visited[node] <= stops):
            continue
        
        visited[node] = stops
        
        for neighbor, price in graph[node]:
            heapq.heappush(pq, (cost + price, neighbor, stops + 1))
    
    return -1

# 4. Minimum Effort Path
def minimum_effort_path(heights):
    m, n = len(heights), len(heights[0])
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    # (effort, row, col)
    pq = [(0, 0, 0)]
    efforts = [[float('inf')] * n for _ in range(m)]
    efforts[0][0] = 0
    
    while pq:
        effort, row, col = heapq.heappop(pq)
        
        if row == m - 1 and col == n - 1:
            return effort
        
        if effort > efforts[row][col]:
            continue
        
        for dr, dc in directions:
            nr, nc = row + dr, col + dc
            if 0 <= nr < m and 0 <= nc < n:
                new_effort = max(effort, abs(heights[nr][nc] - heights[row][col]))
                if new_effort < efforts[nr][nc]:
                    efforts[nr][nc] = new_effort
                    heapq.heappush(pq, (new_effort, nr, nc))
    
    return 0

# 5. Swim In Water
def swim_in_water(grid):
    n = len(grid)
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    # (time, row, col)
    pq = [(grid[0][0], 0, 0)]
    visited = set()
    
    while pq:
        time, row, col = heapq.heappop(pq)
        
        if (row, col) in visited:
            continue
        
        visited.add((row, col))
        
        if row == n - 1 and col == n - 1:
            return time
        
        for dr, dc in directions:
            nr, nc = row + dr, col + dc
            if 0 <= nr < n and 0 <= nc < n and (nr, nc) not in visited:
                heapq.heappush(pq, (max(time, grid[nr][nc]), nr, nc))
    
    return -1

# 6. Shortest Path Binary Matrix
def shortest_path_binary_matrix(grid):
    n = len(grid)
    if grid[0][0] == 1 or grid[n-1][n-1] == 1:
        return -1
    
    directions = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1)]
    
    # (distance, row, col)
    pq = [(1, 0, 0)]
    visited = set()
    
    while pq:
        dist, row, col = heapq.heappop(pq)
        
        if (row, col) in visited:
            continue
        
        visited.add((row, col))
        
        if row == n - 1 and col == n - 1:
            return dist
        
        for dr, dc in directions:
            nr, nc = row + dr, col + dc
            if (0 <= nr < n and 0 <= nc < n and 
                grid[nr][nc] == 0 and (nr, nc) not in visited):
                heapq.heappush(pq, (dist + 1, nr, nc))
    
    return -1

# 7. Find Cheapest Price
def find_cheapest_price(n, flights, src, dst, k):
    graph = defaultdict(list)
    for u, v, w in flights:
        graph[u].append((v, w))
    
    # (cost, node, stops)
    pq = [(0, src, 0)]
    costs = {}
    
    while pq:
        cost, node, stops = heapq.heappop(pq)
        
        if node == dst:
            return cost
        
        if stops > k:
            continue
        
        if (node, stops) in costs and costs[(node, stops)] <= cost:
            continue
        
        costs[(node, stops)] = cost
        
        for neighbor, price in graph[node]:
            heapq.heappush(pq, (cost + price, neighbor, stops + 1))
    
    return -1

# 8. Minimum Cost To Make Valid Path
def minimum_cost_to_make_valid_path(grid):
    m, n = len(grid), len(grid[0])
    directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]
    
    # (cost, row, col)
    pq = [(0, 0, 0)]
    costs = [[float('inf')] * n for _ in range(m)]
    costs[0][0] = 0
    
    while pq:
        cost, row, col = heapq.heappop(pq)
        
        if row == m - 1 and col == n - 1:
            return cost
        
        if cost > costs[row][col]:
            continue
        
        for i, (dr, dc) in enumerate(directions):
            nr, nc = row + dr, col + dc
            if 0 <= nr < m and 0 <= nc < n:
                new_cost = cost + (0 if grid[row][col] == i + 1 else 1)
                if new_cost < costs[nr][nc]:
                    costs[nr][nc] = new_cost
                    heapq.heappush(pq, (new_cost, nr, nc))
    
    return -1

# 9. Shortest Distance From All Buildings
def shortest_distance_from_all_buildings(grid):
    m, n = len(grid), len(grid[0])
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    buildings = []
    for i in range(m):
        for j in range(n):
            if grid[i][j] == 1:
                buildings.append((i, j))
    
    total_distances = [[0] * n for _ in range(m)]
    reachable_count = [[0] * n for _ in range(m)]
    
    for building_idx, (start_r, start_c) in enumerate(buildings):
        # BFS from each building
        queue = [(start_r, start_c, 0)]
        visited = set()
        
        while queue:
            row, col, dist = queue.pop(0)
            
            if (row, col) in visited:
                continue
            
            visited.add((row, col))
            
            if grid[row][col] == 0:
                total_distances[row][col] += dist
                reachable_count[row][col] += 1
            
            for dr, dc in directions:
                nr, nc = row + dr, col + dc
                if (0 <= nr < m and 0 <= nc < n and 
                    (nr, nc) not in visited and grid[nr][nc] != 2):
                    queue.append((nr, nc, dist + 1))
    
    min_distance = float('inf')
    for i in range(m):
        for j in range(n):
            if (grid[i][j] == 0 and 
                reachable_count[i][j] == len(buildings)):
                min_distance = min(min_distance, total_distances[i][j])
    
    return min_distance if min_distance != float('inf') else -1

# 10. Minimum Spanning Tree Kruskal
def minimum_spanning_tree_kruskal(n, edges):
    # Union-Find for Kruskal's algorithm
    parent = list(range(n))
    
    def find(x):
        if parent[x] != x:
            parent[x] = find(parent[x])
        return parent[x]
    
    def union(x, y):
        px, py = find(x), find(y)
        if px != py:
            parent[px] = py
            return True
        return False
    
    edges.sort(key=lambda x: x[2])
    mst_cost = 0
    edges_used = 0
    
    for u, v, weight in edges:
        if union(u, v):
            mst_cost += weight
            edges_used += 1
            if edges_used == n - 1:
                break
    
    return mst_cost if edges_used == n - 1 else -1

# 11. Run Examples
def run_examples():
    print("=== Dijkstra's Algorithm Examples ===")
    
    # Test 1: Basic Dijkstra
    graph = {
        'A': [('B', 4), ('C', 2)],
        'B': [('C', 1), ('D', 5)],
        'C': [('D', 8), ('E', 10)],
        'D': [('E', 2)],
        'E': []
    }
    print(f"Dijkstra from A: {dijkstra(graph, 'A')}")
    
    # Test 2: Network Delay Time
    times = [[2,1,1],[2,3,1],[3,4,1]]
    n, k = 4, 2
    print(f"Network Delay Time: {network_delay_time(times, n, k)}")
    
    # Test 3: Cheapest Flights
    n = 3
    flights = [[0,1,100],[1,2,100],[0,2,500]]
    src, dst, k = 0, 2, 1
    print(f"Cheapest Flights: {cheapest_flights_within_k_stops(n, flights, src, dst, k)}")
    
    # Test 4: Path With Minimum Effort
    heights = [[1,2,2],[3,8,2],[5,3,5]]
    print(f"Minimum Effort Path: {minimum_effort_path(heights)}")
    
    # Test 5: Swim in Rising Water
    grid = [[0,2],[1,3]]
    print(f"Swim in Water: {swim_in_water(grid)}")
    
    # Test 6: Shortest Path in Binary Matrix
    grid = [[0,0,0],[1,1,0],[1,1,0]]
    print(f"Shortest Path Binary Matrix: {shortest_path_binary_matrix(grid)}")
    
    # Test 7: Find Cheapest Price (duplicate function)
    n = 4
    flights = [[0,1,1],[0,2,5],[1,2,1],[2,3,1]]
    src, dst, k = 0, 3, 1
    print(f"Find Cheapest Price: {find_cheapest_price(n, flights, src, dst, k)}")
    
    # Test 8: Minimum Cost Valid Path
    grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
    print(f"Min Cost Valid Path: {minimum_cost_to_make_valid_path(grid)}")
    
    # Test 9: Shortest Distance Buildings
    grid = [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]
    print(f"Shortest Distance Buildings: {shortest_distance_from_all_buildings(grid)}")
    
    # Test 10: MST Kruskal
    n = 4
    edges = [[0,1,10],[0,2,6],[0,3,5],[1,3,15],[2,3,4]]
    print(f"Minimum Spanning Tree: {minimum_spanning_tree_kruskal(n, edges)}")

if __name__ == "__main__":
    run_examples()