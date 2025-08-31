import java.util.*;

public class SegmentTreeExamples {
    
    // 1. Range Sum Query - Mutable
    static class NumArray {
        int[] tree;
        int n;
        
        public NumArray(int[] nums) {
            n = nums.length;
            tree = new int[2 * n];
            for (int i = 0; i < n; i++) tree[n + i] = nums[i];
            for (int i = n - 1; i > 0; i--) tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
        
        public void update(int index, int val) {
            index += n;
            tree[index] = val;
            while (index > 0) {
                tree[index / 2] = tree[index] + tree[index ^ 1];
                index /= 2;
            }
        }
        
        public int sumRange(int left, int right) {
            left += n;
            right += n;
            int sum = 0;
            while (left <= right) {
                if (left % 2 == 1) sum += tree[left++];
                if (right % 2 == 0) sum += tree[right--];
                left /= 2;
                right /= 2;
            }
            return sum;
        }
    }
    
    // 2. Count of Smaller Numbers After Self
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Integer[] result = new Integer[n];
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        
        mergeSort(nums, indices, result, 0, n - 1);
        return Arrays.asList(result);
    }
    
    private void mergeSort(int[] nums, int[] indices, Integer[] result, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(nums, indices, result, left, mid);
        mergeSort(nums, indices, result, mid + 1, right);
        merge(nums, indices, result, left, mid, right);
    }
    
    private void merge(int[] nums, int[] indices, Integer[] result, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        while (i <= mid && j <= right) {
            if (nums[indices[i]] <= nums[indices[j]]) {
                if (result[indices[i]] == null) result[indices[i]] = 0;
                result[indices[i]] += j - mid - 1;
                temp[k++] = indices[i++];
            } else {
                temp[k++] = indices[j++];
            }
        }
        
        while (i <= mid) {
            if (result[indices[i]] == null) result[indices[i]] = 0;
            result[indices[i]] += j - mid - 1;
            temp[k++] = indices[i++];
        }
        
        while (j <= right) temp[k++] = indices[j++];
        
        for (i = left; i <= right; i++) indices[i] = temp[i - left];
    }
    
    // 3. Range Module
    static class RangeModule {
        TreeMap<Integer, Integer> ranges;
        
        public RangeModule() {
            ranges = new TreeMap<>();
        }
        
        public void addRange(int left, int right) {
            Integer start = ranges.floorKey(left);
            Integer end = ranges.floorKey(right);
            
            if (start != null && ranges.get(start) >= left) left = start;
            if (end != null && ranges.get(end) > right) right = ranges.get(end);
            
            ranges.put(left, right);
            ranges.subMap(left, false, right, true).clear();
        }
        
        public boolean queryRange(int left, int right) {
            Integer start = ranges.floorKey(left);
            return start != null && ranges.get(start) >= right;
        }
        
        public void removeRange(int left, int right) {
            Integer start = ranges.floorKey(left);
            Integer end = ranges.floorKey(right);
            
            if (end != null && ranges.get(end) > right) ranges.put(right, ranges.get(end));
            if (start != null && ranges.get(start) > left) ranges.put(start, left);
            
            ranges.subMap(left, true, right, false).clear();
        }
    }
    
    // 4. My Calendar III
    static class MyCalendarThree {
        TreeMap<Integer, Integer> delta;
        
        public MyCalendarThree() {
            delta = new TreeMap<>();
        }
        
        public int book(int start, int end) {
            delta.put(start, delta.getOrDefault(start, 0) + 1);
            delta.put(end, delta.getOrDefault(end, 0) - 1);
            
            int active = 0, ans = 0;
            for (int d : delta.values()) {
                active += d;
                ans = Math.max(ans, active);
            }
            return ans;
        }
    }
    
    // 5. Count of Range Sum
    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] prefixSums = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSums[i + 1] = prefixSums[i] + nums[i];
        }
        return mergeSortRangeSum(prefixSums, 0, prefixSums.length - 1, lower, upper);
    }
    
    private int mergeSortRangeSum(long[] sums, int left, int right, int lower, int upper) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        int count = mergeSortRangeSum(sums, left, mid, lower, upper) + mergeSortRangeSum(sums, mid + 1, right, lower, upper);
        
        int j = mid + 1, k = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && sums[j] - sums[i] < lower) j++;
            while (k <= right && sums[k] - sums[i] <= upper) k++;
            count += k - j;
        }
        
        Arrays.sort(sums, left, right + 1);
        return count;
    }
    
    // 6. Reverse Pairs
    public int reversePairs(int[] nums) {
        return mergeSortReversePairs(nums, 0, nums.length - 1);
    }
    
    private int mergeSortReversePairs(int[] nums, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        int count = mergeSortReversePairs(nums, left, mid) + mergeSortReversePairs(nums, mid + 1, right);
        
        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && nums[i] > 2L * nums[j]) j++;
            count += j - (mid + 1);
        }
        
        Arrays.sort(nums, left, right + 1);
        return count;
    }
    
    // 7. Rectangle Area II
    public int rectangleArea(int[][] rectangles) {
        int MOD = 1_000_000_007;
        Set<Integer> xSet = new HashSet<>(), ySet = new HashSet<>();
        
        for (int[] rect : rectangles) {
            xSet.add(rect[0]);
            xSet.add(rect[2]);
            ySet.add(rect[1]);
            ySet.add(rect[3]);
        }
        
        Integer[] xs = xSet.toArray(new Integer[0]);
        Integer[] ys = ySet.toArray(new Integer[0]);
        Arrays.sort(xs);
        Arrays.sort(ys);
        
        Map<Integer, Integer> xMap = new HashMap<>(), yMap = new HashMap<>();
        for (int i = 0; i < xs.length; i++) xMap.put(xs[i], i);
        for (int i = 0; i < ys.length; i++) yMap.put(ys[i], i);
        
        boolean[][] grid = new boolean[xs.length][ys.length];
        for (int[] rect : rectangles) {
            for (int x = xMap.get(rect[0]); x < xMap.get(rect[2]); x++) {
                for (int y = yMap.get(rect[1]); y < yMap.get(rect[3]); y++) {
                    grid[x][y] = true;
                }
            }
        }
        
        long area = 0;
        for (int x = 0; x < xs.length - 1; x++) {
            for (int y = 0; y < ys.length - 1; y++) {
                if (grid[x][y]) {
                    area += (long)(xs[x + 1] - xs[x]) * (ys[y + 1] - ys[y]);
                    area %= MOD;
                }
            }
        }
        return (int)area;
    }
    
    // 8. Number of Longest Increasing Subsequence
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int[] lengths = new int[n];
        int[] counts = new int[n];
        Arrays.fill(lengths, 1);
        Arrays.fill(counts, 1);
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
        }
        
        int maxLen = Arrays.stream(lengths).max().getAsInt();
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLen) result += counts[i];
        }
        return result;
    }
    
    // 9. Range Sum Query 2D - Mutable
    static class NumMatrix {
        int[][] tree;
        int[][] nums;
        int m, n;
        
        public NumMatrix(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) return;
            m = matrix.length;
            n = matrix[0].length;
            tree = new int[m + 1][n + 1];
            nums = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }
        
        public void update(int row, int col, int val) {
            if (m == 0 || n == 0) return;
            int delta = val - nums[row][col];
            nums[row][col] = val;
            for (int i = row + 1; i <= m; i += i & (-i)) {
                for (int j = col + 1; j <= n; j += j & (-j)) {
                    tree[i][j] += delta;
                }
            }
        }
        
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sum(row2 + 1, col2 + 1) + sum(row1, col1) - sum(row1, col2 + 1) - sum(row2 + 1, col1);
        }
        
        private int sum(int row, int col) {
            int sum = 0;
            for (int i = row; i > 0; i -= i & (-i)) {
                for (int j = col; j > 0; j -= j & (-j)) {
                    sum += tree[i][j];
                }
            }
            return sum;
        }
    }
    
    // 10. Falling Squares
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> result = new ArrayList<>();
        List<int[]> intervals = new ArrayList<>();
        
        for (int[] pos : positions) {
            int left = pos[0], size = pos[1], right = left + size;
            int height = size;
            
            for (int[] interval : intervals) {
                if (interval[1] > left && interval[0] < right) {
                    height = Math.max(height, interval[2] + size);
                }
            }
            
            intervals.add(new int[]{left, right, height});
            result.add(intervals.stream().mapToInt(i -> i[2]).max().orElse(0));
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        SegmentTreeExamples examples = new SegmentTreeExamples();
        
        System.out.println("=== Segment Tree Examples ===");
        
        // Test 1: Range Sum Query - Mutable
        NumArray numArray = new NumArray(new int[]{1, 3, 5});
        System.out.println("1. Range Sum Query [1,3,5] sum(0,2): " + numArray.sumRange(0, 2));
        
        // Test 2: Count of Smaller Numbers After Self
        System.out.println("2. Count Smaller [5,2,6,1]: " + examples.countSmaller(new int[]{5, 2, 6, 1}));
        
        // Test 3: Range Module
        RangeModule rangeModule = new RangeModule();
        rangeModule.addRange(10, 20);
        rangeModule.removeRange(14, 16);
        System.out.println("3. Range Module query(10,14): " + rangeModule.queryRange(10, 14));
        
        // Test 4: My Calendar III
        MyCalendarThree myCalendarThree = new MyCalendarThree();
        System.out.println("4. Calendar III book(10,20): " + myCalendarThree.book(10, 20));
        
        // Test 5: Count of Range Sum
        System.out.println("5. Count Range Sum [-2,5,-1]: " + examples.countRangeSum(new int[]{-2, 5, -1}, -2, 2));
        
        // Test 6: Reverse Pairs
        System.out.println("6. Reverse Pairs [1,3,2,3,1]: " + examples.reversePairs(new int[]{1, 3, 2, 3, 1}));
        
        // Test 7: Rectangle Area II
        System.out.println("7. Rectangle Area II: " + examples.rectangleArea(new int[][]{{0,0,2,2},{1,0,2,3},{1,0,3,1}}));
        
        // Test 8: Number of Longest Increasing Subsequence
        System.out.println("8. Number of LIS [1,3,5,4,7]: " + examples.findNumberOfLIS(new int[]{1, 3, 5, 4, 7}));
        
        // Test 9: Range Sum Query 2D - Mutable
        NumMatrix numMatrix = new NumMatrix(new int[][]{{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}});
        System.out.println("9. 2D Range Sum (0,1,0,2): " + numMatrix.sumRegion(0, 1, 0, 2));
        
        // Test 10: Falling Squares
        System.out.println("10. Falling Squares [[1,2],[2,3],[6,1]]: " + examples.fallingSquares(new int[][]{{1, 2}, {2, 3}, {6, 1}}));
    }
}