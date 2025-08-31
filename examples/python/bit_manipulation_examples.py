# 1. Single Number
def single_number(nums):
    result = 0
    for num in nums:
        result ^= num
    return result

# 2. Single Number Ii
def single_number_ii(nums):
    ones = twos = 0
    for num in nums:
        ones = (ones ^ num) & ~twos
        twos = (twos ^ num) & ~ones
    return ones

# 3. Single Number Iii
def single_number_iii(nums):
    xor = 0
    for num in nums:
        xor ^= num
    
    # Find rightmost set bit
    diff = xor & (-xor)
    
    a = b = 0
    for num in nums:
        if num & diff:
            a ^= num
        else:
            b ^= num
    
    return [a, b]

# 4. Count Bits
def count_bits(n):
    result = [0] * (n + 1)
    for i in range(1, n + 1):
        result[i] = result[i >> 1] + (i & 1)
    return result

# 5. Number Of 1 Bits
def number_of_1_bits(n):
    count = 0
    while n:
        count += 1
        n &= n - 1  # Remove rightmost 1 bit
    return count

# 6. Reverse Bits
def reverse_bits(n):
    result = 0
    for _ in range(32):
        result = (result << 1) | (n & 1)
        n >>= 1
    return result

# 7. Is Power Of Two
def is_power_of_two(n):
    return n > 0 and (n & (n - 1)) == 0

# 8. Is Power Of Four
def is_power_of_four(n):
    return n > 0 and (n & (n - 1)) == 0 and (n & 0x55555555) != 0

# 9. Missing Number
def missing_number(nums):
    n = len(nums)
    expected_xor = 0
    actual_xor = 0
    
    for i in range(n + 1):
        expected_xor ^= i
    
    for num in nums:
        actual_xor ^= num
    
    return expected_xor ^ actual_xor

# 10. Find Duplicate
def find_duplicate(nums):
    # Using bit manipulation - each number appears twice except one
    xor = 0
    for i in range(1, len(nums)):
        xor ^= i
    
    for num in nums:
        xor ^= num
    
    return xor

# 11. Subsets
def subsets(nums):
    n = len(nums)
    result = []
    
    for mask in range(1 << n):
        subset = []
        for i in range(n):
            if mask & (1 << i):
                subset.append(nums[i])
        result.append(subset)
    
    return result

# 12. Gray Code
def gray_code(n):
    result = []
    for i in range(1 << n):
        result.append(i ^ (i >> 1))
    return result

# 13. Total Hamming Distance
def total_hamming_distance(nums):
    total = 0
    n = len(nums)
    
    for bit in range(32):
        ones = sum((num >> bit) & 1 for num in nums)
        zeros = n - ones
        total += ones * zeros
    
    return total

# 14. Maximum Xor
def maximum_xor(nums):
    max_xor = 0
    mask = 0
    
    for i in range(31, -1, -1):
        mask |= (1 << i)
        prefixes = {num & mask for num in nums}
        
        temp = max_xor | (1 << i)
        for prefix in prefixes:
            if temp ^ prefix in prefixes:
                max_xor = temp
                break
    
    return max_xor

# 15. Find Maximum Xor With Element
def find_maximum_xor_with_element(nums, queries):
    class TrieNode:
        def __init__(self):
            self.children = {}
            self.min_val = float('inf')
    
    root = TrieNode()
    
    def insert(num):
        node = root
        node.min_val = min(node.min_val, num)
        for i in range(31, -1, -1):
            bit = (num >> i) & 1
            if bit not in node.children:
                node.children[bit] = TrieNode()
            node = node.children[bit]
            node.min_val = min(node.min_val, num)
    
    def query_max_xor(x, m):
        node = root
        if node.min_val > m:
            return -1
        
        max_xor = 0
        for i in range(31, -1, -1):
            bit = (x >> i) & 1
            toggled_bit = 1 - bit
            
            if (toggled_bit in node.children and 
                node.children[toggled_bit].min_val <= m):
                max_xor |= (1 << i)
                node = node.children[toggled_bit]
            else:
                node = node.children[bit]
        
        return max_xor
    
    # Sort nums and queries by value for processing
    sorted_nums = sorted(nums)
    indexed_queries = sorted(enumerate(queries), key=lambda x: x[1][1])
    
    result = [0] * len(queries)
    num_idx = 0
    
    for orig_idx, (xi, mi) in indexed_queries:
        # Insert all numbers <= mi
        while num_idx < len(sorted_nums) and sorted_nums[num_idx] <= mi:
            insert(sorted_nums[num_idx])
            num_idx += 1
        
        result[orig_idx] = query_max_xor(xi, mi)
    
    return result

# 16. Range Bitwise And
def range_bitwise_and(left, right):
    shift = 0
    while left != right:
        left >>= 1
        right >>= 1
        shift += 1
    return left << shift

# 17. Utf8 Validation
def utf8_validation(data):
    def get_bytes_count(byte):
        if byte >> 7 == 0:
            return 1
        elif byte >> 5 == 0b110:
            return 2
        elif byte >> 4 == 0b1110:
            return 3
        elif byte >> 3 == 0b11110:
            return 4
        else:
            return -1
    
    i = 0
    while i < len(data):
        bytes_count = get_bytes_count(data[i])
        if bytes_count == -1:
            return False
        
        for j in range(1, bytes_count):
            if i + j >= len(data) or data[i + j] >> 6 != 0b10:
                return False
        
        i += bytes_count
    
    return True

# 18. Decode Xored Array
def decode_xored_array(encoded, first):
    result = [first]
    for num in encoded:
        result.append(result[-1] ^ num)
    return result

# 19. Xor Queries
def xor_queries(arr, queries):
    prefix_xor = [0]
    for num in arr:
        prefix_xor.append(prefix_xor[-1] ^ num)
    
    result = []
    for left, right in queries:
        result.append(prefix_xor[right + 1] ^ prefix_xor[left])
    return result

# 20. Minimum Flips To Make Or Equal
def minimum_flips_to_make_or_equal(a, b, c):
    flips = 0
    for i in range(32):
        bit_a = (a >> i) & 1
        bit_b = (b >> i) & 1
        bit_c = (c >> i) & 1
        
        if bit_c == 0:
            flips += bit_a + bit_b
        else:
            if bit_a == 0 and bit_b == 0:
                flips += 1
    
    return flips

# 21. Run Examples
def run_examples():
    print("=== Bit Manipulation Examples ===")
    
    # Test 1: Single Number
    nums = [2, 2, 1]
    print(f"Single Number: {single_number(nums)}")
    
    # Test 2: Single Number II
    nums = [2, 2, 3, 2]
    print(f"Single Number II: {single_number_ii(nums)}")
    
    # Test 3: Single Number III
    nums = [1, 2, 1, 3, 2, 5]
    print(f"Single Number III: {single_number_iii(nums)}")
    
    # Test 4: Counting Bits
    print(f"Count Bits(5): {count_bits(5)}")
    
    # Test 5: Number of 1 Bits
    print(f"Number of 1 Bits(11): {number_of_1_bits(11)}")
    
    # Test 6: Reverse Bits
    print(f"Reverse Bits(43261596): {reverse_bits(43261596)}")
    
    # Test 7: Power of Two
    print(f"Is Power of Two(16): {is_power_of_two(16)}")
    
    # Test 8: Missing Number
    nums = [3, 0, 1]
    print(f"Missing Number: {missing_number(nums)}")
    
    # Test 9: Subsets using bit manipulation
    nums = [1, 2, 3]
    print(f"Subsets: {subsets(nums)}")
    
    # Test 10: Gray Code
    print(f"Gray Code(2): {gray_code(2)}")

if __name__ == "__main__":
    run_examples()