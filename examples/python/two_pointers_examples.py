# 1. Two Sum
def two_sum(numbers, target):
    left, right = 0, len(numbers) - 1
    while left < right:
        current_sum = numbers[left] + numbers[right]
        if current_sum == target:
            return [left + 1, right + 1]
        elif current_sum < target:
            left += 1
        else:
            right -= 1
    return []

# 2. Three Sum
def three_sum(nums):
    nums.sort()
    result = []
    for i in range(len(nums) - 2):
        if i > 0 and nums[i] == nums[i - 1]:
            continue
        left, right = i + 1, len(nums) - 1
        while left < right:
            current_sum = nums[i] + nums[left] + nums[right]
            if current_sum == 0:
                result.append([nums[i], nums[left], nums[right]])
                while left < right and nums[left] == nums[left + 1]:
                    left += 1
                while left < right and nums[right] == nums[right - 1]:
                    right -= 1
                left += 1
                right -= 1
            elif current_sum < 0:
                left += 1
            else:
                right -= 1
    return result

# 3. Max Area
def max_area(height):
    left, right = 0, len(height) - 1
    max_area = 0
    while left < right:
        area = min(height[left], height[right]) * (right - left)
        max_area = max(max_area, area)
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1
    return max_area

# 4. Is Palindrome
def is_palindrome(s):
    left, right = 0, len(s) - 1
    while left < right:
        while left < right and not s[left].isalnum():
            left += 1
        while left < right and not s[right].isalnum():
            right -= 1
        if s[left].lower() != s[right].lower():
            return False
        left += 1
        right -= 1
    return True

# 5. Remove Duplicates
def remove_duplicates(nums):
    slow = 0
    for fast in range(1, len(nums)):
        if nums[fast] != nums[slow]:
            slow += 1
            nums[slow] = nums[fast]
    return slow + 1

# 6. Move Zeroes
def move_zeroes(nums):
    slow = 0
    for fast in range(len(nums)):
        if nums[fast] != 0:
            nums[slow] = nums[fast]
            slow += 1
    while slow < len(nums):
        nums[slow] = 0
        slow += 1

# 7. Trap
def trap(height):
    left, right = 0, len(height) - 1
    left_max = right_max = water = 0
    while left < right:
        if height[left] < height[right]:
            if height[left] >= left_max:
                left_max = height[left]
            else:
                water += left_max - height[left]
            left += 1
        else:
            if height[right] >= right_max:
                right_max = height[right]
            else:
                water += right_max - height[right]
            right -= 1
    return water

# 8. Sort Colors
def sort_colors(nums):
    left = i = 0
    right = len(nums) - 1
    while i <= right:
        if nums[i] == 0:
            nums[i], nums[left] = nums[left], nums[i]
            left += 1
            i += 1
        elif nums[i] == 2:
            nums[i], nums[right] = nums[right], nums[i]
            right -= 1
        else:
            i += 1

# 9. Four Sum
def four_sum(nums, target):
    nums.sort()
    result = []
    for i in range(len(nums) - 3):
        if i > 0 and nums[i] == nums[i - 1]:
            continue
        for j in range(i + 1, len(nums) - 2):
            if j > i + 1 and nums[j] == nums[j - 1]:
                continue
            left, right = j + 1, len(nums) - 1
            while left < right:
                current_sum = nums[i] + nums[j] + nums[left] + nums[right]
                if current_sum == target:
                    result.append([nums[i], nums[j], nums[left], nums[right]])
                    while left < right and nums[left] == nums[left + 1]:
                        left += 1
                    while left < right and nums[right] == nums[right - 1]:
                        right -= 1
                    left += 1
                    right -= 1
                elif current_sum < target:
                    left += 1
                else:
                    right -= 1
    return result

# 10. Reverse String
def reverse_string(s):
    left, right = 0, len(s) - 1
    while left < right:
        s[left], s[right] = s[right], s[left]
        left += 1
        right -= 1

def run_examples():
    print("=== Two Pointers Examples ===")
    
    # Test 1: Two Sum II
    print(f"Two Sum II([2,7,11,15], 9): {two_sum([2,7,11,15], 9)}")
    
    # Test 2: 3Sum
    print(f"3Sum([-1,0,1,2,-1,-4]): {three_sum([-1,0,1,2,-1,-4])}")
    
    # Test 3: Container With Most Water
    print(f"Max Area([1,8,6,2,5,4,8,3,7]): {max_area([1,8,6,2,5,4,8,3,7])}")
    
    # Test 4: Valid Palindrome
    print(f"Is Palindrome('A man, a plan, a canal: Panama'): {is_palindrome('A man, a plan, a canal: Panama')}")
    
    # Test 5: Remove Duplicates
    nums = [1,1,2]
    print(f"Remove Duplicates([1,1,2]): {remove_duplicates(nums)}")
    
    # Test 6: Move Zeroes
    nums2 = [0,1,0,3,12]
    move_zeroes(nums2)
    print(f"Move Zeroes([0,1,0,3,12]): {nums2}")
    
    # Test 7: Trapping Rain Water
    print(f"Trap([0,1,0,2,1,0,1,3,2,1,2,1]): {trap([0,1,0,2,1,0,1,3,2,1,2,1])}")
    
    # Test 8: Sort Colors
    colors = [2,0,2,1,1,0]
    sort_colors(colors)
    print(f"Sort Colors([2,0,2,1,1,0]): {colors}")
    
    # Test 9: 4Sum
    print(f"4Sum([1,0,-1,0,-2,2], 0): {four_sum([1,0,-1,0,-2,2], 0)}")
    
    # Test 10: Reverse String
    s = ['h','e','l','l','o']
    reverse_string(s)
    print(f"Reverse String(['h','e','l','l','o']): {s}")

if __name__ == "__main__":
    run_examples()