from collections import deque

class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

# 1. Level Order
def level_order(root):
    if not root:
        return []
    
    result = []
    queue = deque([root])
    
    while queue:
        level_size = len(queue)
        level = []
        for _ in range(level_size):
            node = queue.popleft()
            level.append(node.val)
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        result.append(level)
    
    return result

# 2. Num Islands
def num_islands(grid):
    if not grid:
        return 0
    
    def bfs_islands(grid, i, j):
        queue = deque([(i, j)])
        grid[i][j] = '0'
        
        directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
        while queue:
            x, y = queue.popleft()
            for dx, dy in directions:
                nx, ny = x + dx, y + dy
                if 0 <= nx < len(grid) and 0 <= ny < len(grid[0]) and grid[nx][ny] == '1':
                    grid[nx][ny] = '0'
                    queue.append((nx, ny))
    
    count = 0
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            if grid[i][j] == '1':
                bfs_islands(grid, i, j)
                count += 1
    return count

# 3. Oranges Rotting
def oranges_rotting(grid):
    queue = deque()
    fresh = 0
    
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            if grid[i][j] == 2:
                queue.append((i, j))
            elif grid[i][j] == 1:
                fresh += 1
    
    if fresh == 0:
        return 0
    
    minutes = 0
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    while queue:
        minutes += 1
        for _ in range(len(queue)):
            x, y = queue.popleft()
            for dx, dy in directions:
                nx, ny = x + dx, y + dy
                if 0 <= nx < len(grid) and 0 <= ny < len(grid[0]) and grid[nx][ny] == 1:
                    grid[nx][ny] = 2
                    queue.append((nx, ny))
                    fresh -= 1
    
    return minutes - 1 if fresh == 0 else -1

# 4. Ladder Length
def ladder_length(begin_word, end_word, word_list):
    word_set = set(word_list)
    if end_word not in word_set:
        return 0
    
    queue = deque([begin_word])
    level = 1
    
    while queue:
        for _ in range(len(queue)):
            word = queue.popleft()
            if word == end_word:
                return level
            
            for i in range(len(word)):
                for c in 'abcdefghijklmnopqrstuvwxyz':
                    new_word = word[:i] + c + word[i+1:]
                    if new_word in word_set:
                        word_set.remove(new_word)
                        queue.append(new_word)
        level += 1
    
    return 0

# 5. Min Depth
def min_depth(root):
    if not root:
        return 0
    
    queue = deque([root])
    depth = 1
    
    while queue:
        for _ in range(len(queue)):
            node = queue.popleft()
            if not node.left and not node.right:
                return depth
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        depth += 1
    
    return depth

# 6. Open Lock
def open_lock(deadends, target):
    dead = set(deadends)
    if "0000" in dead:
        return -1
    
    queue = deque(["0000"])
    visited = {"0000"}
    steps = 0
    
    while queue:
        for _ in range(len(queue)):
            curr = queue.popleft()
            if curr == target:
                return steps
            
            for i in range(4):
                digit = int(curr[i])
                for d in [-1, 1]:
                    new_digit = (digit + d) % 10
                    new_combo = curr[:i] + str(new_digit) + curr[i+1:]
                    if new_combo not in dead and new_combo not in visited:
                        visited.add(new_combo)
                        queue.append(new_combo)
        steps += 1
    
    return -1

# 7. Right Side View
def right_side_view(root):
    if not root:
        return []
    
    result = []
    queue = deque([root])
    
    while queue:
        level_size = len(queue)
        for i in range(level_size):
            node = queue.popleft()
            if i == level_size - 1:
                result.append(node.val)
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
    
    return result

# 8. Shortest Path Binary Matrix
def shortest_path_binary_matrix(grid):
    if grid[0][0] == 1:
        return -1
    n = len(grid)
    if n == 1:
        return 1
    
    queue = deque([(0, 0, 1)])
    grid[0][0] = 1
    
    directions = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1)]
    
    while queue:
        x, y, dist = queue.popleft()
        for dx, dy in directions:
            nx, ny = x + dx, y + dy
            if nx == n - 1 and ny == n - 1:
                return dist + 1
            if 0 <= nx < n and 0 <= ny < n and grid[nx][ny] == 0:
                grid[nx][ny] = 1
                queue.append((nx, ny, dist + 1))
    
    return -1

# 9. Num Squares
def num_squares(n):
    queue = deque([n])
    visited = {n}
    level = 0
    
    while queue:
        level += 1
        for _ in range(len(queue)):
            curr = queue.popleft()
            i = 1
            while i * i <= curr:
                next_val = curr - i * i
                if next_val == 0:
                    return level
                if next_val not in visited:
                    visited.add(next_val)
                    queue.append(next_val)
                i += 1
    
    return level

# 10. Walls And Gates
def walls_and_gates(rooms):
    if not rooms:
        return
    
    queue = deque()
    for i in range(len(rooms)):
        for j in range(len(rooms[0])):
            if rooms[i][j] == 0:
                queue.append((i, j))
    
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    while queue:
        x, y = queue.popleft()
        for dx, dy in directions:
            nx, ny = x + dx, y + dy
            if (0 <= nx < len(rooms) and 0 <= ny < len(rooms[0]) and 
                rooms[nx][ny] == float('inf')):
                rooms[nx][ny] = rooms[x][y] + 1
                queue.append((nx, ny))

def run_examples():
    print("=== BFS Examples ===")
    
    # Test 1: Level Order Traversal
    root = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
    print(f"Level Order: {level_order(root)}")
    
    # Test 2: Number of Islands
    grid = [['1','1','0','0','0'],['1','1','0','0','0'],['0','0','1','0','0'],['0','0','0','1','1']]
    print(f"Number of Islands: {num_islands(grid)}")
    
    # Test 3: Rotting Oranges
    oranges = [[2,1,1],[1,1,0],[0,1,1]]
    print(f"Rotting Oranges: {oranges_rotting(oranges)}")
    
    # Test 4: Word Ladder
    print(f"Word Ladder: {ladder_length('hit', 'cog', ['hot','dot','dog','lot','log','cog'])}")
    
    # Test 5: Minimum Depth
    print(f"Min Depth: {min_depth(root)}")
    
    # Test 6: Open the Lock
    print(f"Open Lock: {open_lock(['0201','0101','0102','1212','2002'], '0202')}")
    
    # Test 7: Right Side View
    print(f"Right Side View: {right_side_view(root)}")
    
    # Test 8: Shortest Path Binary Matrix
    matrix = [[0,0,0],[1,1,0],[1,1,0]]
    print(f"Shortest Path: {shortest_path_binary_matrix(matrix)}")
    
    # Test 9: Perfect Squares
    print(f"Perfect Squares(12): {num_squares(12)}")
    
    # Test 10: Walls and Gates
    rooms = [[float('inf'), -1, 0, float('inf')]]
    walls_and_gates(rooms)
    print("Walls and Gates completed")

if __name__ == "__main__":
    run_examples()