import heapq
from collections import defaultdict

class UnionFind:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [0] * n
        self.components = n
    
    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]
    
    def union(self, x, y):
        px, py = self.find(x), self.find(y)
        if px == py:
            return False
        if self.rank[px] < self.rank[py]:
            px, py = py, px
        self.parent[py] = px
        if self.rank[px] == self.rank[py]:
            self.rank[px] += 1
        self.components -= 1
        return True
    
    def connected(self, x, y):
        return self.find(x) == self.find(y)

# 1. Num Islands
def num_islands(grid):
    if not grid:
        return 0
    m, n = len(grid), len(grid[0])
    uf = UnionFind(m * n)
    islands = 0
    
    for i in range(m):
        for j in range(n):
            if grid[i][j] == '1':
                islands += 1
                curr = i * n + j
                if i > 0 and grid[i-1][j] == '1' and uf.union(curr, (i-1) * n + j):
                    islands -= 1
                if j > 0 and grid[i][j-1] == '1' and uf.union(curr, i * n + j - 1):
                    islands -= 1
    return islands

# 2. Find Circle Num
def find_circle_num(is_connected):
    n = len(is_connected)
    uf = UnionFind(n)
    
    for i in range(n):
        for j in range(i + 1, n):
            if is_connected[i][j] == 1:
                uf.union(i, j)
    
    return uf.components

# 3. Valid Tree
def valid_tree(n, edges):
    if len(edges) != n - 1:
        return False
    uf = UnionFind(n)
    
    for a, b in edges:
        if not uf.union(a, b):
            return False
    
    return uf.components == 1

# 4. Accounts Merge
def accounts_merge(accounts):
    email_to_id = {}
    email_to_name = {}
    id_counter = 0
    
    for account in accounts:
        name = account[0]
        for email in account[1:]:
            if email not in email_to_id:
                email_to_id[email] = id_counter
                id_counter += 1
            email_to_name[email] = name
    
    uf = UnionFind(id_counter)
    for account in accounts:
        first_id = email_to_id[account[1]]
        for email in account[2:]:
            uf.union(first_id, email_to_id[email])
    
    groups = defaultdict(list)
    for email, email_id in email_to_id.items():
        root = uf.find(email_id)
        groups[root].append(email)
    
    result = []
    for emails in groups.values():
        emails.sort()
        result.append([email_to_name[emails[0]]] + emails)
    
    return result

# 5. Find Redundant Connection
def find_redundant_connection(edges):
    uf = UnionFind(len(edges) + 1)
    for a, b in edges:
        if not uf.union(a, b):
            return [a, b]
    return []

# 6. Remove Stones
def remove_stones(stones):
    uf = UnionFind(20000)
    seen = set()
    
    for x, y in stones:
        uf.union(x, y + 10000)
        seen.add(x)
        seen.add(y + 10000)
    
    roots = {uf.find(coord) for coord in seen}
    return len(stones) - len(roots)

# 7. Equations Possible
def equations_possible(equations):
    uf = UnionFind(26)
    
    for eq in equations:
        if eq[1] == '=':
            uf.union(ord(eq[0]) - ord('a'), ord(eq[3]) - ord('a'))
    
    for eq in equations:
        if eq[1] == '!' and uf.connected(ord(eq[0]) - ord('a'), ord(eq[3]) - ord('a')):
            return False
    
    return True

# 8. Swim In Water
def swim_in_water(grid):
    n = len(grid)
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    heap = [(grid[0][0], 0, 0)]
    visited = set()
    
    while heap:
        time, x, y = heapq.heappop(heap)
        
        if (x, y) in visited:
            continue
        visited.add((x, y))
        
        if x == n - 1 and y == n - 1:
            return time
        
        for dx, dy in directions:
            nx, ny = x + dx, y + dy
            if 0 <= nx < n and 0 <= ny < n and (nx, ny) not in visited:
                heapq.heappush(heap, (max(time, grid[nx][ny]), nx, ny))
    
    return -1

# 9. Minimum Cost
def minimum_cost(n, connections):
    connections.sort(key=lambda x: x[2])
    uf = UnionFind(n + 1)
    cost = edges = 0
    
    for a, b, c in connections:
        if uf.union(a, b):
            cost += c
            edges += 1
            if edges == n - 1:
                break
    
    return cost if edges == n - 1 else -1

# 10. Make Connected
def make_connected(n, connections):
    if len(connections) < n - 1:
        return -1
    
    uf = UnionFind(n)
    for a, b in connections:
        uf.union(a, b)
    
    return uf.components - 1

# 11. Run Examples
def run_examples():
    print("=== Union-Find Examples ===")
    
    # Test 1: Number of Islands
    grid = [['1','1','1','1','0'],['1','1','0','1','0'],['1','1','0','0','0'],['0','0','0','0','0']]
    print(f"1. Num Islands: {num_islands(grid)}")
    
    # Test 2: Friend Circles
    is_connected = [[1,1,0],[1,1,0],[0,0,1]]
    print(f"2. Find Circle Num: {find_circle_num(is_connected)}")
    
    # Test 3: Graph Valid Tree
    print(f"3. Valid Tree: {valid_tree(5, [[0,1],[0,2],[0,3],[1,4]])}")
    
    # Test 4: Accounts Merge
    accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],
                ["John","johnsmith@mail.com","john00@mail.com"],
                ["Mary","mary@mail.com"],
                ["John","johnnybravo@mail.com"]]
    print(f"4. Accounts Merge: {accounts_merge(accounts)}")
    
    # Test 5: Redundant Connection
    print(f"5. Redundant Connection: {find_redundant_connection([[1,2],[1,3],[2,3]])}")
    
    # Test 6: Most Stones Removed
    print(f"6. Remove Stones: {remove_stones([[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]])}")
    
    # Test 7: Satisfiability of Equality Equations
    print(f"7. Equations Possible: {equations_possible(['a==b','b!=a'])}")
    
    # Test 8: Swim in Rising Water
    swim_grid = [[0,2],[1,3]]
    print(f"8. Swim in Water: {swim_in_water(swim_grid)}")
    
    # Test 9: Connecting Cities
    print(f"9. Minimum Cost: {minimum_cost(3, [[1,2,5],[1,3,6],[2,3,1]])}")
    
    # Test 10: Make Connected
    print(f"10. Make Connected: {make_connected(4, [[0,1],[0,2],[1,2]])}")

if __name__ == "__main__":
    run_examples()