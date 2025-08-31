import java.util.*;

public class HeapExamples {
    
    // 1. Kth Largest Element in an Array
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) minHeap.poll();
        }
        return minHeap.peek();
    }
    
    // 2. Top K Frequent Elements
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) count.put(num, count.getOrDefault(num, 0) + 1);
        
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            heap.offer(new int[]{entry.getKey(), entry.getValue()});
            if (heap.size() > k) heap.poll();
        }
        
        int[] result = new int[k];
        for (int i = 0; i < k; i++) result[i] = heap.poll()[0];
        return result;
    }
    
    // 3. Merge k Sorted Lists
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode list : lists) {
            if (list != null) heap.offer(list);
        }
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            curr.next = node;
            curr = curr.next;
            if (node.next != null) heap.offer(node.next);
        }
        return dummy.next;
    }
    
    // 4. Find Median from Data Stream
    static class MedianFinder {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }
        
        public double findMedian() {
            return maxHeap.size() > minHeap.size() ? maxHeap.peek() : (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
    
    // 5. K Closest Points to Origin
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> 
            (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1]));
        
        for (int[] point : points) {
            heap.offer(point);
            if (heap.size() > k) heap.poll();
        }
        
        int[][] result = new int[k][];
        for (int i = 0; i < k; i++) result[i] = heap.poll();
        return result;
    }
    
    // 6. Kth Smallest Element in a Sorted Matrix
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < matrix.length; i++) {
            heap.offer(new int[]{matrix[i][0], i, 0});
        }
        
        for (int i = 0; i < k - 1; i++) {
            int[] curr = heap.poll();
            if (curr[2] + 1 < matrix[0].length) {
                heap.offer(new int[]{matrix[curr[1]][curr[2] + 1], curr[1], curr[2] + 1});
            }
        }
        return heap.peek()[0];
    }
    
    // 7. Last Stone Weight
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        for (int stone : stones) heap.offer(stone);
        
        while (heap.size() > 1) {
            int first = heap.poll();
            int second = heap.poll();
            if (first != second) heap.offer(first - second);
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }
    
    // 8. Task Scheduler
    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (char task : tasks) count[task - 'A']++;
        
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        for (int c : count) if (c > 0) heap.offer(c);
        
        int time = 0;
        while (!heap.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                if (!heap.isEmpty()) {
                    int freq = heap.poll();
                    if (freq > 1) temp.add(freq - 1);
                }
                time++;
                if (heap.isEmpty() && temp.isEmpty()) break;
            }
            heap.addAll(temp);
        }
        return time;
    }
    
    // 9. Ugly Number II
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> heap = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();
        heap.offer(1L);
        seen.add(1L);
        
        long ugly = 1;
        for (int i = 0; i < n; i++) {
            ugly = heap.poll();
            for (int factor : new int[]{2, 3, 5}) {
                long next = ugly * factor;
                if (!seen.contains(next)) {
                    heap.offer(next);
                    seen.add(next);
                }
            }
        }
        return (int) ugly;
    }
    
    // 10. Meeting Rooms II
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        for (int[] interval : intervals) {
            if (!heap.isEmpty() && heap.peek() <= interval[0]) {
                heap.poll();
            }
            heap.offer(interval[1]);
        }
        return heap.size();
    }
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public static void main(String[] args) {
        HeapExamples examples = new HeapExamples();
        
        System.out.println("=== Heap Examples ===");
        
        // Test 1: Kth Largest Element
        System.out.println("Kth Largest([3,2,1,5,6,4], k=2): " + examples.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        
        // Test 2: Top K Frequent Elements
        System.out.println("Top K Frequent([1,1,1,2,2,3], k=2): " + Arrays.toString(examples.topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        
        // Test 4: Median Finder
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        System.out.println("Median after adding 1,2: " + mf.findMedian());
        
        // Test 5: K Closest Points
        int[][] points = {{1,3},{-2,2}};
        System.out.println("K Closest Points(k=1): " + Arrays.deepToString(examples.kClosest(points, 1)));
        
        // Test 6: Kth Smallest in Matrix
        int[][] matrix = {{1,5,9},{10,11,13},{12,13,15}};
        System.out.println("Kth Smallest in Matrix(k=8): " + examples.kthSmallest(matrix, 8));
        
        // Test 7: Last Stone Weight
        System.out.println("Last Stone Weight([2,7,4,1,8,1]): " + examples.lastStoneWeight(new int[]{2,7,4,1,8,1}));
        
        // Test 8: Task Scheduler
        System.out.println("Least Interval(['A','A','A','B','B','B'], n=2): " + examples.leastInterval(new char[]{'A','A','A','B','B','B'}, 2));
        
        // Test 9: Ugly Number II
        System.out.println("10th Ugly Number: " + examples.nthUglyNumber(10));
        
        // Test 10: Meeting Rooms II
        int[][] meetings = {{0,30},{5,10},{15,20}};
        System.out.println("Min Meeting Rooms: " + examples.minMeetingRooms(meetings));
    }
}