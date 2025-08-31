// 1. Single Number
function singleNumber(nums) {
    return nums.reduce((result, num) => result ^ num, 0);
}

// 2. Single Number II
function singleNumberII(nums) {
    let ones = 0, twos = 0;
    for (const num of nums) {
        ones = (ones ^ num) & ~twos;
        twos = (twos ^ num) & ~ones;
    }
    return ones;
}

// 3. Missing Number
function missingNumber(nums) {
    const n = nums.length;
    const expected = n * (n + 1) / 2;
    const actual = nums.reduce((sum, num) => sum + num, 0);
    return expected - actual;
}

function missingNumberXOR(nums) {
    let result = nums.length;
    for (let i = 0; i < nums.length; i++) {
        result ^= i ^ nums[i];
    }
    return result;
}

// 4. Sum of Two Integers
function getSum(a, b) {
    while (b !== 0) {
        const carry = a & b;
        a = a ^ b;
        b = carry << 1;
    }
    return a;
}

// 5. Find the Difference
function findTheDifference(s, t) {
    let result = 0;
    for (const c of s) result ^= c.charCodeAt(0);
    for (const c of t) result ^= c.charCodeAt(0);
    return String.fromCharCode(result);
}

// 6. Maximum XOR of Two Numbers in an Array
function findMaximumXOR(nums) {
    let maxXor = 0;
    let mask = 0;
    
    for (let i = 30; i >= 0; i--) {
        mask |= (1 << i);
        const prefixes = new Set(nums.map(num => num & mask));
        
        const candidate = maxXor | (1 << i);
        for (const prefix of prefixes) {
            if (prefixes.has(candidate ^ prefix)) {
                maxXor = candidate;
                break;
            }
        }
    }
    
    return maxXor;
}

// 7. Hamming Distance
function hammingDistance(x, y) {
    let xor = x ^ y;
    let count = 0;
    while (xor !== 0) {
        count += xor & 1;
        xor >>>= 1;
    }
    return count;
}

function hammingDistanceBuiltIn(x, y) {
    return (x ^ y).toString(2).split('1').length - 1;
}

// 8. Total Hamming Distance
function totalHammingDistance(nums) {
    let total = 0;
    const n = nums.length;
    
    for (let i = 0; i < 32; i++) {
        const ones = nums.filter(num => (num >> i) & 1).length;
        total += ones * (n - ones);
    }
    
    return total;
}

// 9. Binary Number with Alternating Bits
function hasAlternatingBits(n) {
    let prev = n & 1;
    n >>>= 1;
    
    while (n > 0) {
        const curr = n & 1;
        if (curr === prev) return false;
        prev = curr;
        n >>>= 1;
    }
    
    return true;
}

function hasAlternatingBitsXOR(n) {
    const xor = n ^ (n >> 1);
    return (xor & (xor + 1)) === 0;
}

// 10. Bitwise ORs of Subarrays
function subarrayBitwiseORs(arr) {
    const result = new Set();
    let current = new Set();
    
    for (const num of arr) {
        const next = new Set();
        next.add(num);
        for (const prev of current) {
            next.add(prev | num);
        }
        current = next;
        for (const val of current) {
            result.add(val);
        }
    }
    
    return result.size;
}

// Bonus of Two
function isPowerOfTwo(n) {
    return n > 0 && (n & (n - 1)) === 0;
}

// Bonus of 1 Bits
function hammingWeight(n) {
    let count = 0;
    while (n !== 0) {
        count++;
        n &= (n - 1); // Remove the rightmost 1 bit
    }
    return count;
}

// Bonus Bits
function reverseBits(n) {
    let result = 0;
    for (let i = 0; i < 32; i++) {
        result = (result << 1) | (n & 1);
        n >>>= 1;
    }
    return result >>> 0; // Convert to unsigned 32-bit integer
}

// Bonus Bits
function countBits(n) {
    const result = new Array(n + 1);
    result[0] = 0;
    for (let i = 1; i <= n; i++) {
        result[i] = result[i >> 1] + (i & 1);
    }
    return result;
}

// Bonus Number III
function singleNumberIII(nums) {
    const xor = nums.reduce((acc, num) => acc ^ num, 0);
    const rightmostBit = xor & (-xor);
    
    let num1 = 0, num2 = 0;
    for (const num of nums) {
        if ((num & rightmostBit) !== 0) {
            num1 ^= num;
        } else {
            num2 ^= num;
        }
    }
    
    return [num1, num2];
}

// Bonus AND of Numbers Range
function rangeBitwiseAnd(left, right) {
    let shift = 0;
    while (left !== right) {
        left >>>= 1;
        right >>>= 1;
        shift++;
    }
    return left << shift;
}

// Bonus Product of Word Lengths
function maxProduct(words) {
    const n = words.length;
    const masks = new Array(n);
    
    for (let i = 0; i < n; i++) {
        masks[i] = 0;
        for (const c of words[i]) {
            masks[i] |= 1 << (c.charCodeAt(0) - 97);
        }
    }
    
    let maxProd = 0;
    for (let i = 0; i < n; i++) {
        for (let j = i + 1; j < n; j++) {
            if ((masks[i] & masks[j]) === 0) {
                maxProd = Math.max(maxProd, words[i].length * words[j].length);
            }
        }
    }
    
    return maxProd;
}

// Bonus Code
function grayCode(n) {
    const result = [];
    for (let i = 0; i < (1 << n); i++) {
        result.push(i ^ (i >> 1));
    }
    return result;
}

// Test cases
console.log("=== Bit Manipulation Examples ===");

// Test 1 Number
console.log("1. Single Number [2,2,1]:", singleNumber([2,2,1]));

// Test 2 Number II
console.log("2. Single Number II [2,2,3,2]:", singleNumberII([2,2,3,2]));

// Test 3 Number
console.log("3. Missing Number [3,0,1]:", missingNumber([3,0,1]));

// Test 4 of Two Integers
console.log("4. Sum of 1 and 2:", getSum(1, 2));

// Test 5 the Difference
console.log("5. Find Difference 'abcd', 'abcde':", findTheDifference("abcd", "abcde"));

// Test 6 XOR
console.log("6. Max XOR [3,10,5,25,2,8]:", findMaximumXOR([3,10,5,25,2,8]));

// Test 7 Distance
console.log("7. Hamming Distance 1,4:", hammingDistance(1, 4));

// Test 8 Hamming Distance
console.log("8. Total Hamming Distance [4,14,2]:", totalHammingDistance([4,14,2]));

// Test 9 Bits
console.log("9. Has Alternating Bits 5:", hasAlternatingBits(5));

// Test 10 ORs
console.log("10. Subarray Bitwise ORs [1,1,2]:", subarrayBitwiseORs([1,1,2]));

// Bonus Functions
console.log("Bonus - Power of Two 16:", isPowerOfTwo(16));
console.log("Bonus - Hamming Weight 11:", hammingWeight(11));
console.log("Bonus - Reverse Bits:", reverseBits(43261596));
console.log("Bonus - Count Bits 5:", countBits(5));
console.log("Bonus - Single Number III:", singleNumberIII([1,2,1,3,2,5]));
console.log("Bonus - Range Bitwise AND [5,7]:", rangeBitwiseAnd(5, 7));
console.log("Bonus - Max Product:", maxProduct(["abcw","baz","foo","bar","xtfn","abcdef"]));
console.log("Bonus - Gray Code 2:", grayCode(2));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        singleNumber,
        singleNumberII,
        missingNumber,
        missingNumberXOR,
        getSum,
        findTheDifference,
        findMaximumXOR,
        hammingDistance,
        hammingDistanceBuiltIn,
        totalHammingDistance,
        hasAlternatingBits,
        hasAlternatingBitsXOR,
        subarrayBitwiseORs,
        isPowerOfTwo,
        hammingWeight,
        reverseBits,
        countBits,
        singleNumberIII,
        rangeBitwiseAnd,
        maxProduct,
        grayCode
    };
}