from functools import lru_cache

# 1. Fibonacci
def fibonacci(n):
    @lru_cache(maxsize=None)
    def fib(n):
        if n <= 1:
            return n
        return fib(n - 1) + fib(n - 2)
    return fib(n)

# 2. Climb Stairs
def climb_stairs(n):
    @lru_cache(maxsize=None)
    def climb(i):
        if i <= 1:
            return 1
        return climb(i - 1) + climb(i - 2)
    return climb(n)

# 3. Unique Paths
def unique_paths(m, n):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i == m - 1 and j == n - 1:
            return 1
        if i >= m or j >= n:
            return 0
        return dfs(i + 1, j) + dfs(i, j + 1)
    return dfs(0, 0)

# 4. Unique Paths With Obstacles
def unique_paths_with_obstacles(obstacle_grid):
    m, n = len(obstacle_grid), len(obstacle_grid[0])
    
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i >= m or j >= n or obstacle_grid[i][j] == 1:
            return 0
        if i == m - 1 and j == n - 1:
            return 1
        return dfs(i + 1, j) + dfs(i, j + 1)
    return dfs(0, 0)

# 5. Min Path Sum
def min_path_sum(grid):
    m, n = len(grid), len(grid[0])
    
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i >= m or j >= n:
            return float('inf')
        if i == m - 1 and j == n - 1:
            return grid[i][j]
        return grid[i][j] + min(dfs(i + 1, j), dfs(i, j + 1))
    return dfs(0, 0)

# 6. Longest Increasing Subsequence
def longest_increasing_subsequence(nums):
    @lru_cache(maxsize=None)
    def dfs(i):
        result = 1
        for j in range(i + 1, len(nums)):
            if nums[j] > nums[i]:
                result = max(result, 1 + dfs(j))
        return result
    
    return max(dfs(i) for i in range(len(nums))) if nums else 0

# 7. Word Break
def word_break(s, word_dict):
    word_set = set(word_dict)
    
    @lru_cache(maxsize=None)
    def dfs(start):
        if start == len(s):
            return True
        for end in range(start + 1, len(s) + 1):
            if s[start:end] in word_set and dfs(end):
                return True
        return False
    return dfs(0)

# 8. Coin Change
def coin_change(coins, amount):
    @lru_cache(maxsize=None)
    def dfs(remaining):
        if remaining == 0:
            return 0
        if remaining < 0:
            return float('inf')
        
        result = float('inf')
        for coin in coins:
            result = min(result, 1 + dfs(remaining - coin))
        return result
    
    result = dfs(amount)
    return result if result != float('inf') else -1

# 9. Rob Houses
def rob_houses(nums):
    @lru_cache(maxsize=None)
    def dfs(i):
        if i >= len(nums):
            return 0
        return max(nums[i] + dfs(i + 2), dfs(i + 1))
    return dfs(0)

# 10. Decode Ways
def decode_ways(s):
    @lru_cache(maxsize=None)
    def dfs(i):
        if i == len(s):
            return 1
        if s[i] == '0':
            return 0
        
        result = dfs(i + 1)
        if i + 1 < len(s) and int(s[i:i+2]) <= 26:
            result += dfs(i + 2)
        return result
    return dfs(0)

# 11. Longest Common Subsequence
def longest_common_subsequence(text1, text2):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i >= len(text1) or j >= len(text2):
            return 0
        if text1[i] == text2[j]:
            return 1 + dfs(i + 1, j + 1)
        return max(dfs(i + 1, j), dfs(i, j + 1))
    return dfs(0, 0)

# 12. Edit Distance
def edit_distance(word1, word2):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i == len(word1):
            return len(word2) - j
        if j == len(word2):
            return len(word1) - i
        
        if word1[i] == word2[j]:
            return dfs(i + 1, j + 1)
        
        return 1 + min(
            dfs(i + 1, j),      # delete
            dfs(i, j + 1),      # insert
            dfs(i + 1, j + 1)   # replace
        )
    return dfs(0, 0)

# 13. Palindrome Partitioning Min Cuts
def palindrome_partitioning_min_cuts(s):
    @lru_cache(maxsize=None)
    def is_palindrome(start, end):
        while start < end:
            if s[start] != s[end]:
                return False
            start += 1
            end -= 1
        return True
    
    @lru_cache(maxsize=None)
    def dfs(start):
        if start == len(s):
            return 0
        
        result = float('inf')
        for end in range(start, len(s)):
            if is_palindrome(start, end):
                result = min(result, 1 + dfs(end + 1))
        return result
    
    return dfs(0) - 1

# 14. Target Sum
def target_sum(nums, target):
    @lru_cache(maxsize=None)
    def dfs(i, current_sum):
        if i == len(nums):
            return 1 if current_sum == target else 0
        return dfs(i + 1, current_sum + nums[i]) + dfs(i + 1, current_sum - nums[i])
    return dfs(0, 0)

# 15. Longest Palindromic Subsequence
def longest_palindromic_subsequence(s):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if i > j:
            return 0
        if i == j:
            return 1
        if s[i] == s[j]:
            return 2 + dfs(i + 1, j - 1)
        return max(dfs(i + 1, j), dfs(i, j - 1))
    return dfs(0, len(s) - 1)

# 16. Regular Expression Matching
def regular_expression_matching(s, p):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if j == len(p):
            return i == len(s)
        
        first_match = i < len(s) and (p[j] == s[i] or p[j] == '.')
        
        if j + 1 < len(p) and p[j + 1] == '*':
            return (dfs(i, j + 2) or 
                   (first_match and dfs(i + 1, j)))
        else:
            return first_match and dfs(i + 1, j + 1)
    
    return dfs(0, 0)

# 17. Wildcard Matching
def wildcard_matching(s, p):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if j == len(p):
            return i == len(s)
        if i == len(s):
            return all(c == '*' for c in p[j:])
        
        if p[j] == '*':
            return dfs(i + 1, j) or dfs(i, j + 1)
        elif p[j] == '?' or s[i] == p[j]:
            return dfs(i + 1, j + 1)
        else:
            return False
    
    return dfs(0, 0)

# 18. Interleaving String
def interleaving_string(s1, s2, s3):
    if len(s1) + len(s2) != len(s3):
        return False
    
    @lru_cache(maxsize=None)
    def dfs(i, j, k):
        if k == len(s3):
            return i == len(s1) and j == len(s2)
        
        result = False
        if i < len(s1) and s1[i] == s3[k]:
            result |= dfs(i + 1, j, k + 1)
        if j < len(s2) and s2[j] == s3[k]:
            result |= dfs(i, j + 1, k + 1)
        
        return result
    
    return dfs(0, 0, 0)

# 19. Distinct Subsequences
def distinct_subsequences(s, t):
    @lru_cache(maxsize=None)
    def dfs(i, j):
        if j == len(t):
            return 1
        if i == len(s):
            return 0
        
        result = dfs(i + 1, j)
        if s[i] == t[j]:
            result += dfs(i + 1, j + 1)
        return result
    
    return dfs(0, 0)

# 20. Run Examples
def run_examples():
    print("=== DFS + Memoization Examples ===")
    
    # Test 1: Fibonacci
    print(f"Fibonacci(10): {fibonacci(10)}")
    
    # Test 2: Climbing Stairs
    print(f"Climb Stairs(5): {climb_stairs(5)}")
    
    # Test 3: Unique Paths
    print(f"Unique Paths(3,7): {unique_paths(3, 7)}")
    
    # Test 4: Min Path Sum
    grid = [[1,3,1],[1,5,1],[4,2,1]]
    print(f"Min Path Sum: {min_path_sum(grid)}")
    
    # Test 5: Longest Increasing Subsequence
    nums = [10,9,2,5,3,7,101,18]
    print(f"LIS: {longest_increasing_subsequence(nums)}")
    
    # Test 6: Word Break
    s = "leetcode"
    word_dict = ["leet", "code"]
    print(f"Word Break: {word_break(s, word_dict)}")
    
    # Test 7: Coin Change
    coins = [1, 3, 4]
    amount = 6
    print(f"Coin Change: {coin_change(coins, amount)}")
    
    # Test 8: House Robber
    nums = [2, 7, 9, 3, 1]
    print(f"Rob Houses: {rob_houses(nums)}")
    
    # Test 9: Decode Ways
    s = "226"
    print(f"Decode Ways: {decode_ways(s)}")
    
    # Test 10: Edit Distance
    word1 = "horse"
    word2 = "ros"
    print(f"Edit Distance: {edit_distance(word1, word2)}")

def run_examples():
    print("=== DFS + Memoization Examples ===")
    
    # Test 1: Fibonacci
    print(f"Fibonacci(10): {fibonacci(10)}")
    
    # Test 2: Climbing Stairs
    print(f"Climb Stairs(5): {climb_stairs(5)}")
    
    # Test 3: Unique Paths
    print(f"Unique Paths(3,7): {unique_paths(3, 7)}")
    
    # Test 4: Min Path Sum
    grid = [[1,3,1],[1,5,1],[4,2,1]]
    print(f"Min Path Sum: {min_path_sum(grid)}")
    
    # Test 5: Longest Increasing Subsequence
    nums = [10,9,2,5,3,7,101,18]
    print(f"LIS: {longest_increasing_subsequence(nums)}")
    
    # Test 6: Word Break
    s = "leetcode"
    word_dict = ["leet", "code"]
    print(f"Word Break: {word_break(s, word_dict)}")
    
    # Test 7: Coin Change
    coins = [1, 3, 4]
    amount = 6
    print(f"Coin Change: {coin_change(coins, amount)}")
    
    # Test 8: House Robber
    nums = [2, 7, 9, 3, 1]
    print(f"Rob Houses: {rob_houses(nums)}")
    
    # Test 9: Decode Ways
    s = "226"
    print(f"Decode Ways: {decode_ways(s)}")
    
    # Test 10: Edit Distance
    word1 = "horse"
    word2 = "ros"
    print(f"Edit Distance: {edit_distance(word1, word2)}")

if __name__ == "__main__":
    run_examples()