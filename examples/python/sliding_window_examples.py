from collections import defaultdict, deque

# 1. Max Subarray Sum
def max_subarray_sum(nums, k):
    window_sum = max_sum = sum(nums[:k])
    for i in range(k, len(nums)):
        window_sum += nums[i] - nums[i - k]
        max_sum = max(max_sum, window_sum)
    return max_sum

# 2. Length Of Longest Substring
def length_of_longest_substring(s):
    char_set = set()
    left = max_len = 0
    for right in range(len(s)):
        while s[right] in char_set:
            char_set.remove(s[left])
            left += 1
        char_set.add(s[right])
        max_len = max(max_len, right - left + 1)
    return max_len

# 3. Min Window
def min_window(s, t):
    need = defaultdict(int)
    for c in t:
        need[c] += 1
    
    left = right = valid = 0
    start = 0
    min_len = float('inf')
    window = defaultdict(int)
    
    while right < len(s):
        c = s[right]
        right += 1
        if c in need:
            window[c] += 1
            if window[c] == need[c]:
                valid += 1
        
        while valid == len(need):
            if right - left < min_len:
                start = left
                min_len = right - left
            d = s[left]
            left += 1
            if d in need:
                if window[d] == need[d]:
                    valid -= 1
                window[d] -= 1
    
    return "" if min_len == float('inf') else s[start:start + min_len]

# 4. Max Sliding Window
def max_sliding_window(nums, k):
    dq = deque()
    result = []
    
    for i in range(len(nums)):
        while dq and dq[0] < i - k + 1:
            dq.popleft()
        while dq and nums[dq[-1]] < nums[i]:
            dq.pop()
        dq.append(i)
        if i >= k - 1:
            result.append(nums[dq[0]])
    
    return result

# 5. Character Replacement
def character_replacement(s, k):
    count = [0] * 26
    left = max_count = max_len = 0
    
    for right in range(len(s)):
        count[ord(s[right]) - ord('A')] += 1
        max_count = max(max_count, count[ord(s[right]) - ord('A')])
        
        while right - left + 1 - max_count > k:
            count[ord(s[left]) - ord('A')] -= 1
            left += 1
        
        max_len = max(max_len, right - left + 1)
    
    return max_len

# 6. Check Inclusion
def check_inclusion(s1, s2):
    need = defaultdict(int)
    for c in s1:
        need[c] += 1
    
    left = right = valid = 0
    window = defaultdict(int)
    
    while right < len(s2):
        c = s2[right]
        right += 1
        if c in need:
            window[c] += 1
            if window[c] == need[c]:
                valid += 1
        
        while right - left >= len(s1):
            if valid == len(need):
                return True
            d = s2[left]
            left += 1
            if d in need:
                if window[d] == need[d]:
                    valid -= 1
                window[d] -= 1
    
    return False

# 7. Find Anagrams
def find_anagrams(s, p):
    result = []
    need = defaultdict(int)
    for c in p:
        need[c] += 1
    
    left = right = valid = 0
    window = defaultdict(int)
    
    while right < len(s):
        c = s[right]
        right += 1
        if c in need:
            window[c] += 1
            if window[c] == need[c]:
                valid += 1
        
        while right - left >= len(p):
            if valid == len(need):
                result.append(left)
            d = s[left]
            left += 1
            if d in need:
                if window[d] == need[d]:
                    valid -= 1
                window[d] -= 1
    
    return result

# 8. Longest Ones
def longest_ones(nums, k):
    left = zeros = max_len = 0
    for right in range(len(nums)):
        if nums[right] == 0:
            zeros += 1
        while zeros > k:
            if nums[left] == 0:
                zeros -= 1
            left += 1
        max_len = max(max_len, right - left + 1)
    return max_len

# 9. Num Subarray Product Less Than K
def num_subarray_product_less_than_k(nums, k):
    if k <= 1:
        return 0
    left = product = count = 0
    for right in range(len(nums)):
        product *= nums[right]
        while product >= k:
            product //= nums[left]
            left += 1
        count += right - left + 1
    return count

# 10. Min Sub Array Len
def min_sub_array_len(target, nums):
    left = total = min_len = 0
    min_len = float('inf')
    for right in range(len(nums)):
        total += nums[right]
        while total >= target:
            min_len = min(min_len, right - left + 1)
            total -= nums[left]
            left += 1
    return 0 if min_len == float('inf') else min_len

def run_examples():
    print("=== Sliding Window Examples ===")
    
    # Test 1: Maximum Subarray Sum
    print(f"Max Subarray Sum([2,1,5,1,3,2], k=3): {max_subarray_sum([2,1,5,1,3,2], 3)}")
    
    # Test 2: Longest Substring Without Repeating
    print(f"Longest Substring 'abcabcbb': {length_of_longest_substring('abcabcbb')}")
    
    # Test 3: Minimum Window Substring
    print(f"Min Window('ADOBECODEBANC', 'ABC'): {min_window('ADOBECODEBANC', 'ABC')}")
    
    # Test 4: Sliding Window Maximum
    print(f"Sliding Window Max([1,3,-1,-3,5,3,6,7], k=3): {max_sliding_window([1,3,-1,-3,5,3,6,7], 3)}")
    
    # Test 5: Character Replacement
    print(f"Character Replacement('ABAB', k=2): {character_replacement('ABAB', 2)}")
    
    # Test 6: Permutation in String
    print(f"Check Inclusion('ab', 'eidbaooo'): {check_inclusion('ab', 'eidbaooo')}")
    
    # Test 7: Find Anagrams
    print(f"Find Anagrams('cbaebabacd', 'abc'): {find_anagrams('cbaebabacd', 'abc')}")
    
    # Test 8: Longest Ones
    print(f"Longest Ones([1,1,1,0,0,0,1,1,1,1,0], k=2): {longest_ones([1,1,1,0,0,0,1,1,1,1,0], 2)}")
    
    # Test 9: Subarray Product Less Than K
    print(f"Subarray Product([10,5,2,6], k=100): {num_subarray_product_less_than_k([10,5,2,6], 100)}")
    
    # Test 10: Minimum Size Subarray Sum
    print(f"Min Subarray Len(target=7, [2,3,1,2,4,3]): {min_sub_array_len(7, [2,3,1,2,4,3])}")

if __name__ == "__main__":
    run_examples()