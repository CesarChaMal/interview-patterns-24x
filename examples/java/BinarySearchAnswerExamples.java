import java.util.*;

public class BinarySearchAnswerExamples {
    
    // 1. Koko Eating Bananas
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = Arrays.stream(piles).max().getAsInt();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, h)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private boolean canFinish(int[] piles, int speed, int h) {
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;
        }
        return hours <= h;
    }
    
    // 2. Capacity To Ship Packages Within D Days
    public int shipWithinDays(int[] weights, int days) {
        int left = Arrays.stream(weights).max().getAsInt();
        int right = Arrays.stream(weights).sum();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canShip(weights, mid, days)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private boolean canShip(int[] weights, int capacity, int days) {
        int daysNeeded = 1, currentWeight = 0;
        for (int weight : weights) {
            if (currentWeight + weight > capacity) {
                daysNeeded++;
                currentWeight = weight;
            } else {
                currentWeight += weight;
            }
        }
        return daysNeeded <= days;
    }
    
    // 3. Split Array Largest Sum
    public int splitArray(int[] nums, int m) {
        int left = Arrays.stream(nums).max().getAsInt();
        int right = Arrays.stream(nums).sum();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSplit(nums, mid, m)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private boolean canSplit(int[] nums, int maxSum, int m) {
        int subarrays = 1, currentSum = 0;
        for (int num : nums) {
            if (currentSum + num > maxSum) {
                subarrays++;
                currentSum = num;
            } else {
                currentSum += num;
            }
        }
        return subarrays <= m;
    }
    
    // 4. Minimum Number of Days to Make m Bouquets
    public int minDays(int[] bloomDay, int m, int k) {
        if (m * k > bloomDay.length) return -1;
        int left = Arrays.stream(bloomDay).min().getAsInt();
        int right = Arrays.stream(bloomDay).max().getAsInt();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canMakeBouquets(bloomDay, mid, m, k)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private boolean canMakeBouquets(int[] bloomDay, int day, int m, int k) {
        int bouquets = 0, consecutive = 0;
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                consecutive++;
                if (consecutive == k) {
                    bouquets++;
                    consecutive = 0;
                }
            } else {
                consecutive = 0;
            }
        }
        return bouquets >= m;
    }
    
    // 5. Smallest Divisor Given Threshold
    public int smallestDivisor(int[] nums, int threshold) {
        int left = 1, right = Arrays.stream(nums).max().getAsInt();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (getDivisionSum(nums, mid) <= threshold) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private int getDivisionSum(int[] nums, int divisor) {
        int sum = 0;
        for (int num : nums) {
            sum += (num + divisor - 1) / divisor;
        }
        return sum;
    }
    
    // 6. Magnetic Force Between Two Balls
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int left = 1, right = position[position.length - 1] - position[0];
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (canPlaceBalls(position, mid, m)) left = mid;
            else right = mid - 1;
        }
        return left;
    }
    
    private boolean canPlaceBalls(int[] position, int minDist, int m) {
        int count = 1, lastPos = position[0];
        for (int i = 1; i < position.length; i++) {
            if (position[i] - lastPos >= minDist) {
                count++;
                lastPos = position[i];
                if (count == m) return true;
            }
        }
        return false;
    }
    
    // 7. Minimize Max Distance to Gas Station
    public double minmaxGasDist(int[] stations, int k) {
        double left = 0, right = 1e8;
        while (right - left > 1e-6) {
            double mid = (left + right) / 2;
            if (canAddStations(stations, mid, k)) right = mid;
            else left = mid;
        }
        return left;
    }
    
    private boolean canAddStations(int[] stations, double maxDist, int k) {
        int needed = 0;
        for (int i = 1; i < stations.length; i++) {
            needed += (int) ((stations[i] - stations[i - 1]) / maxDist);
        }
        return needed <= k;
    }
    
    // 8. Find K-th Smallest Pair Distance
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = nums[nums.length - 1] - nums[0];
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (countPairs(nums, mid) >= k) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    
    private int countPairs(int[] nums, int maxDist) {
        int count = 0, left = 0;
        for (int right = 1; right < nums.length; right++) {
            while (nums[right] - nums[left] > maxDist) left++;
            count += right - left;
        }
        return count;
    }
    
    // 9. Median of Two Sorted Arrays
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
        
        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        
        while (left <= right) {
            int partitionX = (left + right) / 2;
            int partitionY = (m + n + 1) / 2 - partitionX;
            
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];
            
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];
            
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
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
    public int aggressiveCows(int[] stalls, int cows) {
        Arrays.sort(stalls);
        int left = 1, right = stalls[stalls.length - 1] - stalls[0];
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (canPlaceCows(stalls, mid, cows)) left = mid;
            else right = mid - 1;
        }
        return left;
    }
    
    private boolean canPlaceCows(int[] stalls, int minDist, int cows) {
        int count = 1, lastPos = stalls[0];
        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= minDist) {
                count++;
                lastPos = stalls[i];
                if (count == cows) return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        BinarySearchAnswerExamples examples = new BinarySearchAnswerExamples();
        
        System.out.println("=== Binary Search on Answer Examples ===");
        
        // Test 1: Koko Eating Bananas
        System.out.println("Min Eating Speed([3,6,7,11], 8): " + examples.minEatingSpeed(new int[]{3,6,7,11}, 8));
        
        // Test 2: Ship Within Days
        System.out.println("Ship Within Days([1,2,3,4,5,6,7,8,9,10], 5): " + examples.shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5));
        
        // Test 3: Split Array Largest Sum
        System.out.println("Split Array([7,2,5,10,8], 2): " + examples.splitArray(new int[]{7,2,5,10,8}, 2));
        
        // Test 4: Min Days for Bouquets
        System.out.println("Min Days([1,10,3,10,2], 3, 1): " + examples.minDays(new int[]{1,10,3,10,2}, 3, 1));
        
        // Test 5: Smallest Divisor
        System.out.println("Smallest Divisor([1,2,5,9], 6): " + examples.smallestDivisor(new int[]{1,2,5,9}, 6));
        
        // Test 6: Magnetic Force
        System.out.println("Max Distance([1,2,3,4,7], 3): " + examples.maxDistance(new int[]{1,2,3,4,7}, 3));
        
        // Test 7: Min Max Gas Distance
        System.out.println("Min Max Gas Dist([1,2,3,4,5,6,7,8,9,10], 9): " + examples.minmaxGasDist(new int[]{1,2,3,4,5,6,7,8,9,10}, 9));
        
        // Test 8: Smallest Distance Pair
        System.out.println("Smallest Distance Pair([1,3,1], 1): " + examples.smallestDistancePair(new int[]{1,3,1}, 1));
        
        // Test 9: Median of Two Sorted Arrays
        System.out.println("Find Median([1,3], [2]): " + examples.findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
        
        // Test 10: Aggressive Cows
        System.out.println("Aggressive Cows([1,2,4,8,9], 3): " + examples.aggressiveCows(new int[]{1,2,4,8,9}, 3));
    }
}