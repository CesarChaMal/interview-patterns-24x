// 1. Koko Eating Bananas
function minEatingSpeed(piles: number[], h: number): number {
    let left = 1, right = Math.max(...piles);
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        const hours = piles.reduce((sum, pile) => sum + Math.ceil(pile / mid), 0);
        if (hours <= h) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 2. Capacity To Ship Packages Within D Days
function shipWithinDays(weights: number[], days: number): number {
    let left = Math.max(...weights), right = weights.reduce((a, b) => a + b);
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        let daysNeeded = 1, currentWeight = 0;
        for (const weight of weights) {
            if (currentWeight + weight > mid) {
                daysNeeded++;
                currentWeight = weight;
            } else {
                currentWeight += weight;
            }
        }
        if (daysNeeded <= days) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 3. Split Array Largest Sum
function splitArray(nums: number[], m: number): number {
    let left = Math.max(...nums), right = nums.reduce((a, b) => a + b);
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        let subarrays = 1, currentSum = 0;
        for (const num of nums) {
            if (currentSum + num > mid) {
                subarrays++;
                currentSum = num;
            } else {
                currentSum += num;
            }
        }
        if (subarrays <= m) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 4. Minimum Number of Days to Make m Bouquets
function minDays(bloomDay: number[], m: number, k: number): number {
    if (m * k > bloomDay.length) return -1;
    let left = Math.min(...bloomDay), right = Math.max(...bloomDay);
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        let bouquets = 0, consecutive = 0;
        for (const bloom of bloomDay) {
            if (bloom <= mid) {
                consecutive++;
                if (consecutive === k) {
                    bouquets++;
                    consecutive = 0;
                }
            } else {
                consecutive = 0;
            }
        }
        if (bouquets >= m) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 5. Smallest Divisor Given Threshold
function smallestDivisor(nums: number[], threshold: number): number {
    let left = 1, right = Math.max(...nums);
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        const sum = nums.reduce((acc, num) => acc + Math.ceil(num / mid), 0);
        if (sum <= threshold) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 6. Magnetic Force Between Two Balls
function maxDistance(position: number[], m: number): number {
    position.sort((a, b) => a - b);
    let left = 1, right = position[position.length - 1] - position[0];
    while (left < right) {
        const mid = Math.floor((left + right + 1) / 2);
        let count = 1, lastPos = position[0];
        for (let i = 1; i < position.length; i++) {
            if (position[i] - lastPos >= mid) {
                count++;
                lastPos = position[i];
                if (count === m) break;
            }
        }
        if (count >= m) left = mid;
        else right = mid - 1;
    }
    return left;
}

// 7. Minimize Max Distance to Gas Station
function minmaxGasDist(stations: number[], k: number): number {
    let left = 0, right = 1e8;
    while (right - left > 1e-6) {
        const mid = (left + right) / 2;
        let needed = 0;
        for (let i = 1; i < stations.length; i++) {
            needed += Math.floor((stations[i] - stations[i - 1]) / mid);
        }
        if (needed <= k) right = mid;
        else left = mid;
    }
    return left;
}

// 8. Find K-th Smallest Pair Distance
function smallestDistancePair(nums: number[], k: number): number {
    nums.sort((a, b) => a - b);
    let left = 0, right = nums[nums.length - 1] - nums[0];
    while (left < right) {
        const mid = Math.floor((left + right) / 2);
        let count = 0, leftPtr = 0;
        for (let rightPtr = 1; rightPtr < nums.length; rightPtr++) {
            while (nums[rightPtr] - nums[leftPtr] > mid) leftPtr++;
            count += rightPtr - leftPtr;
        }
        if (count >= k) right = mid;
        else left = mid + 1;
    }
    return left;
}

// 9. Median of Two Sorted Arrays
function findMedianSortedArrays(nums1: number[], nums2: number[]): number {
    if (nums1.length > nums2.length) [nums1, nums2] = [nums2, nums1];
    
    const m = nums1.length, n = nums2.length;
    let left = 0, right = m;
    
    while (left <= right) {
        const partitionX = Math.floor((left + right) / 2);
        const partitionY = Math.floor((m + n + 1) / 2) - partitionX;
        
        const maxLeftX = partitionX === 0 ? -Infinity : nums1[partitionX - 1];
        const minRightX = partitionX === m ? Infinity : nums1[partitionX];
        
        const maxLeftY = partitionY === 0 ? -Infinity : nums2[partitionY - 1];
        const minRightY = partitionY === n ? Infinity : nums2[partitionY];
        
        if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
            if ((m + n) % 2 === 0) {
                return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
            } else {
                return Math.max(maxLeftX, maxLeftY);
            }
        } else if (maxLeftX > minRightY) {
            right = partitionX - 1;
        } else {
            left = partitionX + 1;
        }
    }
    return -1;
}

// 10. Aggressive Cows
function aggressiveCows(stalls: number[], cows: number): number {
    stalls.sort((a, b) => a - b);
    let left = 1, right = stalls[stalls.length - 1] - stalls[0];
    while (left < right) {
        const mid = Math.floor((left + right + 1) / 2);
        let count = 1, lastPos = stalls[0];
        for (let i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= mid) {
                count++;
                lastPos = stalls[i];
                if (count === cows) break;
            }
        }
        if (count >= cows) left = mid;
        else right = mid - 1;
    }
    return left;
}

// Test cases
console.log("=== Binary Search on Answer Examples ===");

// Test 1: Koko Eating Bananas
console.log(`Min Eating Speed([3,6,7,11], 8): ${minEatingSpeed([3,6,7,11], 8)}`);

// Test 2: Ship Within Days
console.log(`Ship Within Days([1,2,3,4,5,6,7,8,9,10], 5): ${shipWithinDays([1,2,3,4,5,6,7,8,9,10], 5)}`);

// Test 3: Split Array Largest Sum
console.log(`Split Array([7,2,5,10,8], 2): ${splitArray([7,2,5,10,8], 2)}`);

// Test 4: Min Days for Bouquets
console.log(`Min Days([1,10,3,10,2], 3, 1): ${minDays([1,10,3,10,2], 3, 1)}`);

// Test 5: Smallest Divisor
console.log(`Smallest Divisor([1,2,5,9], 6): ${smallestDivisor([1,2,5,9], 6)}`);

// Test 6: Magnetic Force
console.log(`Max Distance([1,2,3,4,7], 3): ${maxDistance([1,2,3,4,7], 3)}`);

// Test 7: Min Max Gas Distance
console.log(`Min Max Gas Dist([1,2,3,4,5,6,7,8,9,10], 9): ${minmaxGasDist([1,2,3,4,5,6,7,8,9,10], 9)}`);

// Test 8: Smallest Distance Pair
console.log(`Smallest Distance Pair([1,3,1], 1): ${smallestDistancePair([1,3,1], 1)}`);

// Test 9: Median of Two Sorted Arrays
console.log(`Find Median([1,3], [2]): ${findMedianSortedArrays([1,3], [2])}`);

// Test 10: Aggressive Cows
console.log(`Aggressive Cows([1,2,4,8,9], 3): ${aggressiveCows([1,2,4,8,9], 3)}`);