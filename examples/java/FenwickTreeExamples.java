import java.util.*;

public class FenwickTreeExamples {
    
    static class FenwickTree {
        int[] tree;
        int n;
        
        public FenwickTree(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }
        
        public void update(int i, int delta) {
            for (++i; i <= n; i += i & -i) tree[i] += delta;
        }
        
        public int query(int i) {
            int sum = 0;
            for (++i; i > 0; i -= i & -i) sum += tree[i];
            return sum;
        }
        
        public int rangeQuery(int l, int r) {
            return query(r) - (l > 0 ? query(l - 1) : 0);
        }
    }
    
    // 1. Range Sum Query - Mutable
    static class NumArray {
        FenwickTree ft;
        int[] nums;
        
        public NumArray(int[] nums) {
            this.nums = nums.clone();
            ft = new FenwickTree(nums.length);
            for (int i = 0; i < nums.length; i++) {
                ft.update(i, nums[i]);
            }
        }
        
        public void update(int index, int val) {
            ft.update(index, val - nums[index]);
            nums[index] = val;
        }
        
        public int sumRange(int left, int right) {
            return ft.rangeQuery(left, right);
        }
    }
    
    // 2. Count of Smaller Numbers After Self
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Integer[] result = new Integer[n];
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        
        Map<Integer, Integer> ranks = new HashMap<>();
        int rank = 0;
        for (int num : sorted) {
            if (!ranks.containsKey(num)) ranks.put(num, rank++);
        }
        
        FenwickTree ft = new FenwickTree(rank);
        for (int i = n - 1; i >= 0; i--) {
            int r = ranks.get(nums[i]);
            result[i] = r > 0 ? ft.query(r - 1) : 0;
            ft.update(r, 1);
        }
        
        return Arrays.asList(result);
    }
    
    // 3. Count of Range Sum
    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] prefixSums = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSums[i + 1] = prefixSums[i] + nums[i];
        }
        
        Set<Long> allSums = new HashSet<>();
        for (long sum : prefixSums) {
            allSums.add(sum);
            allSums.add(sum - lower);
            allSums.add(sum - upper);
        }
        
        Long[] sorted = allSums.toArray(new Long[0]);
        Arrays.sort(sorted);
        Map<Long, Integer> ranks = new HashMap<>();
        for (int i = 0; i < sorted.length; i++) {
            ranks.put(sorted[i], i);
        }
        
        FenwickTree ft = new FenwickTree(sorted.length);
        int count = 0;
        
        for (long sum : prefixSums) {
            int left = ranks.get(sum - upper);
            int right = ranks.get(sum - lower);
            count += ft.rangeQuery(left, right);
            ft.update(ranks.get(sum), 1);
        }
        
        return count;
    }
    
    // 4. Reverse Pairs
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        int count = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        
        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && nums[i] > 2L * nums[j]) j++;
            count += j - (mid + 1);
        }
        
        Arrays.sort(nums, left, right + 1);
        return count;
    }
    
    // 5. Count Number of Teams
    public int numTeamsInArray(int[] rating) {
        int n = rating.length;
        int count = 0;
        
        for (int j = 1; j < n - 1; j++) {
            int leftSmaller = 0, leftLarger = 0;
            int rightSmaller = 0, rightLarger = 0;
            
            for (int i = 0; i < j; i++) {
                if (rating[i] < rating[j]) leftSmaller++;
                else if (rating[i] > rating[j]) leftLarger++;
            }
            
            for (int k = j + 1; k < n; k++) {
                if (rating[k] < rating[j]) rightSmaller++;
                else if (rating[k] > rating[j]) rightLarger++;
            }
            
            count += leftSmaller * rightLarger + leftLarger * rightSmaller;
        }
        
        return count;
    }
    
    // 6. Queries on a Permutation With Key
    public int[] processQueries(int[] queries, int m) {
        FenwickTree ft = new FenwickTree(2 * m);
        int[] pos = new int[m + 1];
        
        for (int i = 1; i <= m; i++) {
            pos[i] = m + i;
            ft.update(m + i, 1);
        }
        
        int[] result = new int[queries.length];
        int nextPos = m;
        
        for (int i = 0; i < queries.length; i++) {
            int val = queries[i];
            result[i] = ft.query(pos[val] - 1);
            ft.update(pos[val], -1);
            pos[val] = nextPos--;
            ft.update(pos[val], 1);
        }
        
        return result;
    }
    
    // 7. Create Sorted Array through Instructions
    public int createSortedArray(int[] instructions) {
        int MOD = 1_000_000_007;
        int maxVal = Arrays.stream(instructions).max().getAsInt();
        FenwickTree ft = new FenwickTree(maxVal);
        
        long cost = 0;
        for (int i = 0; i < instructions.length; i++) {
            int val = instructions[i];
            int smaller = val > 1 ? ft.query(val - 2) : 0;
            int larger = ft.query(maxVal - 1) - ft.query(val - 1);
            cost = (cost + Math.min(smaller, larger)) % MOD;
            ft.update(val - 1, 1);
        }
        
        return (int)cost;
    }
    
    // 8. Range Add Query
    public int[] rangeAddQuery(int length, int[][] updates) {
        FenwickTree ft = new FenwickTree(length);
        
        for (int[] update : updates) {
            int left = update[0], right = update[1], val = update[2];
            ft.update(left, val);
            if (right + 1 < length) ft.update(right + 1, -val);
        }
        
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = ft.query(i);
        }
        return result;
    }
    
    // 9. Count Inversions
    public int countInversions(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        
        Map<Integer, Integer> ranks = new HashMap<>();
        int rank = 0;
        for (int num : sorted) {
            if (!ranks.containsKey(num)) ranks.put(num, rank++);
        }
        
        FenwickTree ft = new FenwickTree(rank);
        int count = 0;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            int r = ranks.get(nums[i]);
            count += r > 0 ? ft.query(r - 1) : 0;
            ft.update(r, 1);
        }
        
        return count;
    }
    
    // 10. 2D Range Sum Query
    static class NumMatrix {
        int[][] matrix;
        int m, n;
        
        public NumMatrix(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) return;
            this.matrix = matrix;
            this.m = matrix.length;
            this.n = matrix[0].length;
        }
        
        public void update(int row, int col, int val) {
            matrix[row][col] = val;
        }
        
        public int sumRegion(int row1, int col1, int row2, int col2) {
            int sum = 0;
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    sum += matrix[i][j];
                }
            }
            return sum;
        }
    }
    
    public static void main(String[] args) {
        FenwickTreeExamples examples = new FenwickTreeExamples();
        
        System.out.println("=== Fenwick Tree Examples ===");
        
        // Test 1: Range Sum Query
        int[] nums = {1, 3, 5, 7, 9, 11};
        NumArray numArray = new NumArray(nums);
        System.out.println("1. Sum Range [1,3]: " + numArray.sumRange(1, 3));
        numArray.update(1, 10);
        System.out.println("   Sum Range [1,3] after update: " + numArray.sumRange(1, 3));
        
        // Test 2: Count Smaller Numbers
        int[] nums2 = {5, 2, 6, 1};
        System.out.println("2. Count Smaller: " + examples.countSmaller(nums2));
        
        // Test 3: Count Range Sum
        int[] nums3 = {-2, 5, -1};
        System.out.println("3. Count Range Sum: " + examples.countRangeSum(nums3, -2, 2));
        
        // Test 4: Reverse Pairs
        int[] nums4 = {1, 3, 2, 3, 1};
        System.out.println("4. Reverse Pairs: " + examples.reversePairs(nums4));
        
        // Test 5: Count Teams
        int[] rating = {2, 5, 3, 4, 1};
        System.out.println("5. Count Teams: " + examples.numTeamsInArray(rating));
        
        // Test 6: Process Queries
        int[] queries = {3, 1, 2, 1};
        System.out.println("6. Process Queries: " + Arrays.toString(examples.processQueries(queries, 5)));
        
        // Test 7: Create Sorted Array
        int[] instructions = {1, 5, 6, 2};
        System.out.println("7. Create Sorted Array Cost: " + examples.createSortedArray(instructions));
        
        // Test 8: Range Add Query
        int[][] updates = {{1, 3, 2}, {2, 4, 3}, {0, 2, -2}};
        System.out.println("8. Range Add Query: " + Arrays.toString(examples.rangeAddQuery(5, updates)));
        
        // Test 9: Count Inversions
        int[] nums9 = {8, 4, 2, 1};
        System.out.println("9. Count Inversions: " + examples.countInversions(nums9));
        
        // Test 10: 2D Range Sum
        int[][] matrix = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}};
        NumMatrix numMatrix = new NumMatrix(matrix);
        System.out.println("10. 2D Sum Region [1,1,2,3]: " + numMatrix.sumRegion(1, 1, 2, 3));
    }
}