# 1. Climb Stairs
def climb_stairs(n):
    if n <= 2:
        return n
    prev2, prev1 = 1, 2
    for i in range(3, n + 1):
        curr = prev1 + prev2
        prev2, prev1 = prev1, curr
    return prev1

# 2. Rob
def rob(nums):
    prev2 = prev1 = 0
    for num in nums:
        curr = max(prev1, prev2 + num)
        prev2, prev1 = prev1, curr
    return prev1

# 3. Coin Change
def coin_change(coins, amount):
    dp = [amount + 1] * (amount + 1)
    dp[0] = 0
    
    for i in range(1, amount + 1):
        for coin in coins:
            if coin <= i:
                dp[i] = min(dp[i], dp[i - coin] + 1)
    
    return dp[amount] if dp[amount] <= amount else -1

# 4. Length Of Lis
def length_of_lis(nums):
    from bisect import bisect_left
    dp = []
    for num in nums:
        pos = bisect_left(dp, num)
        if pos == len(dp):
            dp.append(num)
        else:
            dp[pos] = num
    return len(dp)

# 5. Max Subarray
def max_subarray(nums):
    max_sum = curr_sum = nums[0]
    for i in range(1, len(nums)):
        curr_sum = max(nums[i], curr_sum + nums[i])
        max_sum = max(max_sum, curr_sum)
    return max_sum

# 6. Unique Paths
def unique_paths(m, n):
    dp = [1] * n
    for i in range(1, m):
        for j in range(1, n):
            dp[j] += dp[j - 1]
    return dp[n - 1]

# 7. Knapsack
def knapsack(weights, values, capacity):
    dp = [0] * (capacity + 1)
    for i in range(len(weights)):
        for w in range(capacity, weights[i] - 1, -1):
            dp[w] = max(dp[w], dp[w - weights[i]] + values[i])
    return dp[capacity]

# 8. Min Distance
def min_distance(word1, word2):
    m, n = len(word1), len(word2)
    dp = list(range(n + 1))
    
    for i in range(1, m + 1):
        prev = dp[0]
        dp[0] = i
        for j in range(1, n + 1):
            temp = dp[j]
            if word1[i - 1] == word2[j - 1]:
                dp[j] = prev
            else:
                dp[j] = 1 + min(prev, dp[j], dp[j - 1])
            prev = temp
    return dp[n]

# 9. Longest Common Subsequence
def longest_common_subsequence(text1, text2):
    m, n = len(text1), len(text2)
    dp = [0] * (n + 1)
    
    for i in range(1, m + 1):
        prev = 0
        for j in range(1, n + 1):
            temp = dp[j]
            if text1[i - 1] == text2[j - 1]:
                dp[j] = prev + 1
            else:
                dp[j] = max(dp[j], dp[j - 1])
            prev = temp
    return dp[n]

# 10. Word Break
def word_break(s, word_dict):
    word_set = set(word_dict)
    dp = [False] * (len(s) + 1)
    dp[0] = True
    
    for i in range(1, len(s) + 1):
        for j in range(i):
            if dp[j] and s[j:i] in word_set:
                dp[i] = True
                break
    return dp[len(s)]

def run_examples():
    print("=== Dynamic Programming Examples ===")
    
    # Test 1: Climbing Stairs
    print(f"Climb Stairs(5): {climb_stairs(5)}")
    
    # Test 2: House Robber
    print(f"House Robber([2,7,9,3,1]): {rob([2,7,9,3,1])}")
    
    # Test 3: Coin Change
    print(f"Coin Change([1,3,4], 6): {coin_change([1,3,4], 6)}")
    
    # Test 4: Longest Increasing Subsequence
    print(f"LIS([10,9,2,5,3,7,101,18]): {length_of_lis([10,9,2,5,3,7,101,18])}")
    
    # Test 5: Maximum Subarray
    print(f"Max Subarray([-2,1,-3,4,-1,2,1,-5,4]): {max_subarray([-2,1,-3,4,-1,2,1,-5,4])}")
    
    # Test 6: Unique Paths
    print(f"Unique Paths(3,7): {unique_paths(3, 7)}")
    
    # Test 7: 0/1 Knapsack
    print(f"Knapsack([1,3,4,5], [1,4,5,7], 7): {knapsack([1,3,4,5], [1,4,5,7], 7)}")
    
    # Test 8: Edit Distance
    print(f"Edit Distance('horse', 'ros'): {min_distance('horse', 'ros')}")
    
    # Test 9: Longest Common Subsequence
    print(f"LCS('abcde', 'ace'): {longest_common_subsequence('abcde', 'ace')}")
    
    # Test 10: Word Break
    print(f"Word Break('leetcode', ['leet','code']): {word_break('leetcode', ['leet', 'code'])}")

if __name__ == "__main__":
    run_examples()