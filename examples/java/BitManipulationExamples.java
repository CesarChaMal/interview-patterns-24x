import java.util.*;

public class BitManipulationExamples {
    
    // 1. Single Number
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    // 2. Single Number II
    public int singleNumberII(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }
        return ones;
    }
    
    // 3. Missing Number
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expected = n * (n + 1) / 2;
        int actual = 0;
        for (int num : nums) {
            actual += num;
        }
        return expected - actual;
    }
    
    public int missingNumberXOR(int[] nums) {
        int result = nums.length;
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }
    
    // 4. Sum of Two Integers
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
    
    // 5. Find the Difference
    public char findTheDifference(String s, String t) {
        char result = 0;
        for (char c : s.toCharArray()) {
            result ^= c;
        }
        for (char c : t.toCharArray()) {
            result ^= c;
        }
        return result;
    }
    
    // 6. Maximum XOR of Two Numbers in an Array
    public int findMaximumXOR(int[] nums) {
        int maxXor = 0;
        int mask = 0;
        
        for (int i = 30; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> prefixes = new HashSet<>();
            for (int num : nums) {
                prefixes.add(num & mask);
            }
            
            int candidate = maxXor | (1 << i);
            for (int prefix : prefixes) {
                if (prefixes.contains(candidate ^ prefix)) {
                    maxXor = candidate;
                    break;
                }
            }
        }
        
        return maxXor;
    }
    
    // 7. Hamming Distance
    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int count = 0;
        while (xor != 0) {
            count += xor & 1;
            xor >>= 1;
        }
        return count;
    }
    
    public int hammingDistanceBuiltIn(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
    
    // 8. Total Hamming Distance
    public int totalHammingDistance(int[] nums) {
        int total = 0;
        int n = nums.length;
        
        for (int i = 0; i < 32; i++) {
            int ones = 0;
            for (int num : nums) {
                ones += (num >> i) & 1;
            }
            total += ones * (n - ones);
        }
        
        return total;
    }
    
    // 9. Binary Number with Alternating Bits
    public boolean hasAlternatingBits(int n) {
        int prev = n & 1;
        n >>= 1;
        
        while (n > 0) {
            int curr = n & 1;
            if (curr == prev) return false;
            prev = curr;
            n >>= 1;
        }
        
        return true;
    }
    
    public boolean hasAlternatingBitsXOR(int n) {
        long xor = n ^ (n >> 1);
        return (xor & (xor + 1)) == 0;
    }
    
    // 10. Bitwise ORs of Subarrays
    public int subarrayBitwiseORs(int[] arr) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> current = new HashSet<>();
        
        for (int num : arr) {
            Set<Integer> next = new HashSet<>();
            next.add(num);
            for (int prev : current) {
                next.add(prev | num);
            }
            current = next;
            result.addAll(current);
        }
        
        return result.size();
    }
    
    // Bonus: Power of Two
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    // Bonus: Number of 1 Bits
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n &= (n - 1); // Remove the rightmost 1 bit
        }
        return count;
    }
    
    // Bonus: Reverse Bits
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>= 1;
        }
        return result;
    }
    
    // Bonus: Counting Bits
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }
    
    // Bonus: Single Number III
    public int[] singleNumberIII(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        
        int rightmostBit = xor & (-xor);
        int num1 = 0, num2 = 0;
        
        for (int num : nums) {
            if ((num & rightmostBit) != 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }
        
        return new int[]{num1, num2};
    }
    
    // Bonus: Bitwise AND of Numbers Range
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        while (left != right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;
    }
    
    // Bonus: Maximum Product of Word Lengths
    public int maxProduct(String[] words) {
        int n = words.length;
        int[] masks = new int[n];
        
        for (int i = 0; i < n; i++) {
            for (char c : words[i].toCharArray()) {
                masks[i] |= 1 << (c - 'a');
            }
        }
        
        int maxProd = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((masks[i] & masks[j]) == 0) {
                    maxProd = Math.max(maxProd, words[i].length() * words[j].length());
                }
            }
        }
        
        return maxProd;
    }
    
    // Bonus: Gray Code
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            result.add(i ^ (i >> 1));
        }
        return result;
    }
    
    public static void main(String[] args) {
        BitManipulationExamples examples = new BitManipulationExamples();
        
        System.out.println("=== Bit Manipulation Examples ===");
        
        // Test 1: Single Number
        System.out.println("1. Single Number [2,2,1]: " + examples.singleNumber(new int[]{2,2,1}));
        
        // Test 2: Single Number II
        System.out.println("2. Single Number II [2,2,3,2]: " + examples.singleNumberII(new int[]{2,2,3,2}));
        
        // Test 3: Missing Number
        System.out.println("3. Missing Number [3,0,1]: " + examples.missingNumber(new int[]{3,0,1}));
        
        // Test 4: Sum of Two Integers
        System.out.println("4. Sum of 1 and 2: " + examples.getSum(1, 2));
        
        // Test 5: Find the Difference
        System.out.println("5. Find Difference 'abcd', 'abcde': " + examples.findTheDifference("abcd", "abcde"));
        
        // Test 6: Maximum XOR
        System.out.println("6. Max XOR [3,10,5,25,2,8]: " + examples.findMaximumXOR(new int[]{3,10,5,25,2,8}));
        
        // Test 7: Hamming Distance
        System.out.println("7. Hamming Distance 1,4: " + examples.hammingDistance(1, 4));
        
        // Test 8: Total Hamming Distance
        System.out.println("8. Total Hamming Distance [4,14,2]: " + examples.totalHammingDistance(new int[]{4,14,2}));
        
        // Test 9: Alternating Bits
        System.out.println("9. Has Alternating Bits 5: " + examples.hasAlternatingBits(5));
        
        // Test 10: Bitwise ORs
        System.out.println("10. Subarray Bitwise ORs [1,1,2]: " + examples.subarrayBitwiseORs(new int[]{1,1,2}));
        
        // Bonus Functions
        System.out.println("Bonus - Power of Two 16: " + examples.isPowerOfTwo(16));
        System.out.println("Bonus - Hamming Weight 11: " + examples.hammingWeight(11));
        System.out.println("Bonus - Reverse Bits: " + examples.reverseBits(43261596));
        System.out.println("Bonus - Count Bits 5: " + Arrays.toString(examples.countBits(5)));
        System.out.println("Bonus - Single Number III: " + Arrays.toString(examples.singleNumberIII(new int[]{1,2,1,3,2,5})));
        System.out.println("Bonus - Range Bitwise AND [5,7]: " + examples.rangeBitwiseAnd(5, 7));
        System.out.println("Bonus - Max Product: " + examples.maxProduct(new String[]{"abcw","baz","foo","bar","xtfn","abcdef"}));
        System.out.println("Bonus - Gray Code 2: " + examples.grayCode(2));
    }
}