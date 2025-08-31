# 1. Binary Search
def binary_search(nums, target):
    left, right = 0, len(nums) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            return mid
        elif nums[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

# 2. First Bad Version
def first_bad_version(n):
    def is_bad_version(version):
        return version >= 4  # Mock implementation
    
    left, right = 1, n
    while left < right:
        mid = left + (right - left) // 2
        if is_bad_version(mid):
            right = mid
        else:
            left = mid + 1
    return left

# 3. Search Insert
def search_insert(nums, target):
    left, right = 0, len(nums) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            return mid
        elif nums[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return left

# 4. Find Peak Element
def find_peak_element(nums):
    left, right = 0, len(nums) - 1
    while left < right:
        mid = left + (right - left) // 2
        if nums[mid] > nums[mid + 1]:
            right = mid
        else:
            left = mid + 1
    return left

# 5. Search Rotated
def search_rotated(nums, target):
    left, right = 0, len(nums) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            return mid
        
        if nums[left] <= nums[mid]:
            if nums[left] <= target < nums[mid]:
                right = mid - 1
            else:
                left = mid + 1
        else:
            if nums[mid] < target <= nums[right]:
                left = mid + 1
            else:
                right = mid - 1
    return -1

# 6. Find Min
def find_min(nums):
    left, right = 0, len(nums) - 1
    while left < right:
        mid = left + (right - left) // 2
        if nums[mid] > nums[right]:
            left = mid + 1
        else:
            right = mid
    return nums[left]

# 7. Search Matrix
def search_matrix(matrix, target):
    m, n = len(matrix), len(matrix[0])
    left, right = 0, m * n - 1
    while left <= right:
        mid = left + (right - left) // 2
        val = matrix[mid // n][mid % n]
        if val == target:
            return True
        elif val < target:
            left = mid + 1
        else:
            right = mid - 1
    return False

# 8. Search Range
def search_range(nums, target):
    def find_first(nums, target):
        left, right = 0, len(nums) - 1
        result = -1
        while left <= right:
            mid = left + (right - left) // 2
            if nums[mid] == target:
                result = mid
                right = mid - 1
            elif nums[mid] < target:
                left = mid + 1
            else:
                right = mid - 1
        return result
    
    def find_last(nums, target):
        left, right = 0, len(nums) - 1
        result = -1
        while left <= right:
            mid = left + (right - left) // 2
            if nums[mid] == target:
                result = mid
                left = mid + 1
            elif nums[mid] < target:
                left = mid + 1
            else:
                right = mid - 1
        return result
    
    return [find_first(nums, target), find_last(nums, target)]

# 9. My Sqrt
def my_sqrt(x):
    if x == 0:
        return 0
    left, right = 1, x
    while left <= right:
        mid = left + (right - left) // 2
        if mid == x // mid:
            return mid
        elif mid < x // mid:
            left = mid + 1
        else:
            right = mid - 1
    return right

# 10. Is Perfect Square
def is_perfect_square(num):
    left, right = 1, num
    while left <= right:
        mid = left + (right - left) // 2
        square = mid * mid
        if square == num:
            return True
        elif square < num:
            left = mid + 1
        else:
            right = mid - 1
    return False

def run_examples():
    print("=== Binary Search Examples ===")
    
    # Test 1: Binary Search
    print(f"Binary Search([-1,0,3,5,9,12], 9): {binary_search([-1,0,3,5,9,12], 9)}")
    
    # Test 2: First Bad Version
    print(f"First Bad Version(5): {first_bad_version(5)}")
    
    # Test 3: Search Insert Position
    print(f"Search Insert([1,3,5,6], 5): {search_insert([1,3,5,6], 5)}")
    
    # Test 4: Find Peak Element
    print(f"Find Peak([1,2,3,1]): {find_peak_element([1,2,3,1])}")
    
    # Test 5: Search in Rotated Sorted Array
    print(f"Search Rotated([4,5,6,7,0,1,2], 0): {search_rotated([4,5,6,7,0,1,2], 0)}")
    
    # Test 6: Find Minimum in Rotated Sorted Array
    print(f"Find Min([3,4,5,1,2]): {find_min([3,4,5,1,2])}")
    
    # Test 7: Search a 2D Matrix
    matrix = [[1,4,7,11],[2,5,8,12],[3,6,9,16]]
    print(f"Search Matrix(matrix, 5): {search_matrix(matrix, 5)}")
    
    # Test 8: Find First and Last Position
    print(f"Search Range([5,7,7,8,8,10], 8): {search_range([5,7,7,8,8,10], 8)}")
    
    # Test 9: Sqrt(x)
    print(f"Sqrt(4): {my_sqrt(4)}")
    
    # Test 10: Valid Perfect Square
    print(f"Is Perfect Square(16): {is_perfect_square(16)}")

if __name__ == "__main__":
    run_examples()