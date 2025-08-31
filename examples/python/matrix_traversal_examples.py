# 1. Spiral Order
def spiral_order(matrix):
    if not matrix or not matrix[0]:
        return []
    
    result = []
    top, bottom = 0, len(matrix) - 1
    left, right = 0, len(matrix[0]) - 1
    
    while top <= bottom and left <= right:
        # Traverse right
        for col in range(left, right + 1):
            result.append(matrix[top][col])
        top += 1
        
        # Traverse down
        for row in range(top, bottom + 1):
            result.append(matrix[row][right])
        right -= 1
        
        # Traverse left
        if top <= bottom:
            for col in range(right, left - 1, -1):
                result.append(matrix[bottom][col])
            bottom -= 1
        
        # Traverse up
        if left <= right:
            for row in range(bottom, top - 1, -1):
                result.append(matrix[row][left])
            left += 1
    
    return result

# 2. Rotate Matrix
def rotate_matrix(matrix):
    n = len(matrix)
    
    # Transpose
    for i in range(n):
        for j in range(i, n):
            matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
    
    # Reverse each row
    for i in range(n):
        matrix[i].reverse()

# 3. Set Zeroes
def set_zeroes(matrix):
    m, n = len(matrix), len(matrix[0])
    first_row_zero = any(matrix[0][j] == 0 for j in range(n))
    first_col_zero = any(matrix[i][0] == 0 for i in range(m))
    
    # Use first row and column as markers
    for i in range(1, m):
        for j in range(1, n):
            if matrix[i][j] == 0:
                matrix[i][0] = 0
                matrix[0][j] = 0
    
    # Set zeros based on markers
    for i in range(1, m):
        for j in range(1, n):
            if matrix[i][0] == 0 or matrix[0][j] == 0:
                matrix[i][j] = 0
    
    # Handle first row and column
    if first_row_zero:
        for j in range(n):
            matrix[0][j] = 0
    
    if first_col_zero:
        for i in range(m):
            matrix[i][0] = 0

# 4. Search Matrix
def search_matrix(matrix, target):
    if not matrix or not matrix[0]:
        return False
    
    m, n = len(matrix), len(matrix[0])
    row, col = 0, n - 1
    
    while row < m and col >= 0:
        if matrix[row][col] == target:
            return True
        elif matrix[row][col] > target:
            col -= 1
        else:
            row += 1
    
    return False

# 5. Num Islands
def num_islands(grid):
    if not grid or not grid[0]:
        return 0
    
    m, n = len(grid), len(grid[0])
    count = 0
    
    def dfs(i, j):
        if i < 0 or i >= m or j < 0 or j >= n or grid[i][j] != '1':
            return
        grid[i][j] = '0'
        dfs(i + 1, j)
        dfs(i - 1, j)
        dfs(i, j + 1)
        dfs(i, j - 1)
    
    for i in range(m):
        for j in range(n):
            if grid[i][j] == '1':
                count += 1
                dfs(i, j)
    
    return count

# 6. Max Area Island
def max_area_island(grid):
    if not grid or not grid[0]:
        return 0
    
    m, n = len(grid), len(grid[0])
    max_area = 0
    
    def dfs(i, j):
        if i < 0 or i >= m or j < 0 or j >= n or grid[i][j] != 1:
            return 0
        grid[i][j] = 0
        return 1 + dfs(i + 1, j) + dfs(i - 1, j) + dfs(i, j + 1) + dfs(i, j - 1)
    
    for i in range(m):
        for j in range(n):
            if grid[i][j] == 1:
                max_area = max(max_area, dfs(i, j))
    
    return max_area

# 7. Surrounded Regions
def surrounded_regions(board):
    if not board or not board[0]:
        return
    
    m, n = len(board), len(board[0])
    
    def dfs(i, j):
        if i < 0 or i >= m or j < 0 or j >= n or board[i][j] != 'O':
            return
        board[i][j] = 'T'  # Temporary mark
        dfs(i + 1, j)
        dfs(i - 1, j)
        dfs(i, j + 1)
        dfs(i, j - 1)
    
    # Mark border-connected 'O's
    for i in range(m):
        dfs(i, 0)
        dfs(i, n - 1)
    for j in range(n):
        dfs(0, j)
        dfs(m - 1, j)
    
    # Convert remaining 'O's to 'X' and restore 'T's to 'O'
    for i in range(m):
        for j in range(n):
            if board[i][j] == 'O':
                board[i][j] = 'X'
            elif board[i][j] == 'T':
                board[i][j] = 'O'

# 8. Pacific Atlantic
def pacific_atlantic(heights):
    if not heights or not heights[0]:
        return []
    
    m, n = len(heights), len(heights[0])
    pacific = [[False] * n for _ in range(m)]
    atlantic = [[False] * n for _ in range(m)]
    
    def dfs(i, j, ocean, prev_height):
        if (i < 0 or i >= m or j < 0 or j >= n or 
            ocean[i][j] or heights[i][j] < prev_height):
            return
        
        ocean[i][j] = True
        dfs(i + 1, j, ocean, heights[i][j])
        dfs(i - 1, j, ocean, heights[i][j])
        dfs(i, j + 1, ocean, heights[i][j])
        dfs(i, j - 1, ocean, heights[i][j])
    
    # Start from Pacific borders
    for i in range(m):
        dfs(i, 0, pacific, heights[i][0])
    for j in range(n):
        dfs(0, j, pacific, heights[0][j])
    
    # Start from Atlantic borders
    for i in range(m):
        dfs(i, n - 1, atlantic, heights[i][n - 1])
    for j in range(n):
        dfs(m - 1, j, atlantic, heights[m - 1][j])
    
    result = []
    for i in range(m):
        for j in range(n):
            if pacific[i][j] and atlantic[i][j]:
                result.append([i, j])
    
    return result

# 9. Shortest Path Binary Matrix
def shortest_path_binary_matrix(grid):
    n = len(grid)
    if grid[0][0] == 1 or grid[n-1][n-1] == 1:
        return -1
    
    if n == 1:
        return 1
    
    from collections import deque
    queue = deque([(0, 0, 1)])
    visited = {(0, 0)}
    directions = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1)]
    
    while queue:
        row, col, path_length = queue.popleft()
        
        for dr, dc in directions:
            nr, nc = row + dr, col + dc
            
            if (0 <= nr < n and 0 <= nc < n and 
                grid[nr][nc] == 0 and (nr, nc) not in visited):
                
                if nr == n - 1 and nc == n - 1:
                    return path_length + 1
                
                visited.add((nr, nc))
                queue.append((nr, nc, path_length + 1))
    
    return -1

# 10. Walls And Gates
def walls_and_gates(rooms):
    if not rooms or not rooms[0]:
        return
    
    m, n = len(rooms), len(rooms[0])
    from collections import deque
    queue = deque()
    
    # Find all gates
    for i in range(m):
        for j in range(n):
            if rooms[i][j] == 0:
                queue.append((i, j))
    
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    while queue:
        row, col = queue.popleft()
        
        for dr, dc in directions:
            nr, nc = row + dr, col + dc
            
            if (0 <= nr < m and 0 <= nc < n and 
                rooms[nr][nc] == 2147483647):  # INF
                rooms[nr][nc] = rooms[row][col] + 1
                queue.append((nr, nc))

# 11. Unique Paths With Obstacles
def unique_paths_with_obstacles(obstacle_grid):
    if not obstacle_grid or obstacle_grid[0][0] == 1:
        return 0
    
    m, n = len(obstacle_grid), len(obstacle_grid[0])
    dp = [[0] * n for _ in range(m)]
    dp[0][0] = 1
    
    for i in range(m):
        for j in range(n):
            if obstacle_grid[i][j] == 1:
                dp[i][j] = 0
            else:
                if i > 0:
                    dp[i][j] += dp[i-1][j]
                if j > 0:
                    dp[i][j] += dp[i][j-1]
    
    return dp[m-1][n-1]

# 12. Min Path Sum
def min_path_sum(grid):
    m, n = len(grid), len(grid[0])
    
    for i in range(1, m):
        grid[i][0] += grid[i-1][0]
    
    for j in range(1, n):
        grid[0][j] += grid[0][j-1]
    
    for i in range(1, m):
        for j in range(1, n):
            grid[i][j] += min(grid[i-1][j], grid[i][j-1])
    
    return grid[m-1][n-1]

# 13. Word Search
def word_search(board, word):
    if not board or not board[0]:
        return False
    
    m, n = len(board), len(board[0])
    
    def dfs(i, j, k):
        if k == len(word):
            return True
        if i < 0 or i >= m or j < 0 or j >= n or board[i][j] != word[k]:
            return False
        
        temp = board[i][j]
        board[i][j] = '#'  # Mark as visited
        
        found = (dfs(i+1, j, k+1) or dfs(i-1, j, k+1) or 
                dfs(i, j+1, k+1) or dfs(i, j-1, k+1))
        
        board[i][j] = temp  # Restore
        return found
    
    for i in range(m):
        for j in range(n):
            if dfs(i, j, 0):
                return True
    
    return False

# 14. Run Examples
def run_examples():
    print("=== Matrix Traversal Examples ===")
    
    # Test 1: Spiral Order
    matrix = [[1,2,3],[4,5,6],[7,8,9]]
    print(f"Spiral Order: {spiral_order(matrix)}")
    
    # Test 2: Rotate Matrix
    matrix = [[1,2,3],[4,5,6],[7,8,9]]
    rotate_matrix(matrix)
    print(f"Rotated Matrix: {matrix}")
    
    # Test 3: Search Matrix
    matrix = [[1,4,7,11],[2,5,8,12],[3,6,9,16],[10,13,14,17]]
    print(f"Search Matrix (5): {search_matrix(matrix, 5)}")
    
    # Test 4: Number of Islands
    grid = [["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]
    print(f"Number of Islands: {num_islands(grid)}")
    
    # Test 5: Max Area of Island
    grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0]]
    print(f"Max Area Island: {max_area_island(grid)}")
    
    # Test 6: Shortest Path Binary Matrix
    grid = [[0,0,0],[1,1,0],[1,1,0]]
    print(f"Shortest Path Binary Matrix: {shortest_path_binary_matrix(grid)}")
    
    # Test 7: Unique Paths with Obstacles
    obstacle_grid = [[0,0,0],[0,1,0],[0,0,0]]
    print(f"Unique Paths with Obstacles: {unique_paths_with_obstacles(obstacle_grid)}")
    
    # Test 8: Min Path Sum
    grid = [[1,3,1],[1,5,1],[4,2,1]]
    print(f"Min Path Sum: {min_path_sum(grid)}")
    
    # Test 9: Word Search
    board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]
    word = "ABCCED"
    print(f"Word Search '{word}': {word_search(board, word)}")
    
    # Test 10: Pacific Atlantic Water Flow
    heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
    print(f"Pacific Atlantic: {pacific_atlantic(heights)}")

if __name__ == "__main__":
    run_examples()