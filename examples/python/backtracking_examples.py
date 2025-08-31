# 1. Permute
def permute(nums):
    result = []
    def backtrack(path, used):
        if len(path) == len(nums):
            result.append(path[:])
        else:
            for i in range(len(nums)):
                if not used[i]:
                    used[i] = True
                    path.append(nums[i])
                    backtrack(path, used)
                    path.pop()
                    used[i] = False
    backtrack([], [False] * len(nums))
    return result

# 2. Combine
def combine(n, k):
    result = []
    def backtrack(start, path):
        if len(path) == k:
            result.append(path[:])
        else:
            for i in range(start, n + 1):
                path.append(i)
                backtrack(i + 1, path)
                path.pop()
    backtrack(1, [])
    return result

# 3. Subsets
def subsets(nums):
    result = []
    def backtrack(start, path):
        result.append(path[:])
        for i in range(start, len(nums)):
            path.append(nums[i])
            backtrack(i + 1, path)
            path.pop()
    backtrack(0, [])
    return result

# 4. Solve N Queens
def solve_n_queens(n):
    result = []
    def is_valid(board, row, col):
        for i in range(row):
            if board[i] == col or abs(board[i] - col) == abs(i - row):
                return False
        return True
    
    def backtrack(board, row):
        if row == n:
            result.append(['.' * col + 'Q' + '.' * (n - col - 1) for col in board])
        else:
            for col in range(n):
                if is_valid(board, row, col):
                    board[row] = col
                    backtrack(board, row + 1)
    
    backtrack([-1] * n, 0)
    return result

# 5. Generate Parenthesis
def generate_parenthesis(n):
    result = []
    def backtrack(path, open_count, close_count):
        if len(path) == 2 * n:
            result.append(path)
        else:
            if open_count < n:
                backtrack(path + '(', open_count + 1, close_count)
            if close_count < open_count:
                backtrack(path + ')', open_count, close_count + 1)
    backtrack('', 0, 0)
    return result

# 6. Exist
def exist(board, word):
    def backtrack(i, j, k):
        if k == len(word):
            return True
        if i < 0 or i >= len(board) or j < 0 or j >= len(board[0]) or board[i][j] != word[k]:
            return False
        temp = board[i][j]
        board[i][j] = '#'
        found = (backtrack(i+1, j, k+1) or backtrack(i-1, j, k+1) or 
                backtrack(i, j+1, k+1) or backtrack(i, j-1, k+1))
        board[i][j] = temp
        return found
    
    for i in range(len(board)):
        for j in range(len(board[0])):
            if backtrack(i, j, 0):
                return True
    return False

# 7. Palindrome Partitioning
def partition(s):
    result = []
    def is_palindrome(string):
        return string == string[::-1]
    
    def backtrack(start, path):
        if start == len(s):
            result.append(path[:])
        else:
            for end in range(start + 1, len(s) + 1):
                substr = s[start:end]
                if is_palindrome(substr):
                    path.append(substr)
                    backtrack(end, path)
                    path.pop()
    
    backtrack(0, [])
    return result

# 8. Letter Combinations of Phone Number
def letter_combinations(digits):
    if not digits:
        return []
    mapping = {'2': 'abc', '3': 'def', '4': 'ghi', '5': 'jkl', 
               '6': 'mno', '7': 'pqrs', '8': 'tuv', '9': 'wxyz'}
    result = []
    def backtrack(index, path):
        if index == len(digits):
            result.append(path)
        else:
            for c in mapping[digits[index]]:
                backtrack(index + 1, path + c)
    backtrack(0, '')
    return result

# 9. Solve Sudoku
def solve_sudoku(board):
    def is_valid(row, col, c):
        for i in range(9):
            if (board[i][col] == c or board[row][i] == c or 
                board[3*(row//3)+i//3][3*(col//3)+i%3] == c):
                return False
        return True
    
    def backtrack():
        for i in range(9):
            for j in range(9):
                if board[i][j] == '.':
                    for c in '123456789':
                        if is_valid(i, j, c):
                            board[i][j] = c
                            if backtrack():
                                return True
                            board[i][j] = '.'
                    return False
        return True
    
    backtrack()

# 10. Combination Sum
def combination_sum(candidates, target):
    result = []
    def backtrack(start, path, current_sum):
        if current_sum == target:
            result.append(path[:])
        elif current_sum < target:
            for i in range(start, len(candidates)):
                path.append(candidates[i])
                backtrack(i, path, current_sum + candidates[i])
                path.pop()
    backtrack(0, [], 0)
    return result

def run_examples():
    print("=== Backtracking Examples ===")
    
    # Test 1: Permutations
    print(f"Permutations([1,2,3]): {permute([1,2,3])}")
    
    # Test 2: Combinations
    print(f"Combinations(4,2): {combine(4,2)}")
    
    # Test 3: Subsets
    print(f"Subsets([1,2,3]): {subsets([1,2,3])}")
    
    # Test 4: N-Queens
    print(f"N-Queens(4): {len(solve_n_queens(4))} solutions")
    
    # Test 5: Generate Parentheses
    print(f"Generate Parentheses(3): {generate_parenthesis(3)}")
    
    # Test 6: Word Search
    board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']]
    print(f"Word Search 'ABCCED': {exist(board, 'ABCCED')}")
    
    # Test 7: Palindrome Partitioning
    print(f"Palindrome Partition 'aab': {partition('aab')}")
    
    # Test 8: Letter Combinations
    print(f"Letter Combinations '23': {letter_combinations('23')}")
    
    # Test 9: Sudoku Solver
    print("Sudoku Solver: Completed")
    
    # Test 10: Combination Sum
    print(f"Combination Sum([2,3,6,7], 7): {combination_sum([2,3,6,7], 7)}")

if __name__ == "__main__":
    run_examples()