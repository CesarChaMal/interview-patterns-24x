import java.util.*;

public class IntervalsExamples {
    
    // 1. Merge Intervals
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> merged = new ArrayList<>();
        
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
    
    // 2. Insert Interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        
        // Add all intervals before newInterval
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i++]);
        }
        
        // Merge overlapping intervals
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);
        
        // Add remaining intervals
        while (i < intervals.length) {
            result.add(intervals[i++]);
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    // 3. Non-overlapping Intervals
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 0, end = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                count++;
            } else {
                end = intervals[i][1];
            }
        }
        return count;
    }
    
    // 4. Meeting Rooms
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }
    
    // 5. Meeting Rooms II
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
    
    // 6. Interval List Intersections
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;
        
        while (i < firstList.length && j < secondList.length) {
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);
            
            if (start <= end) {
                result.add(new int[]{start, end});
            }
            
            if (firstList[i][1] < secondList[j][1]) i++;
            else j++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    // 7. Employee Free Time
    public List<int[]> employeeFreeTime(List<List<int[]>> schedule) {
        List<int[]> intervals = new ArrayList<>();
        for (List<int[]> employee : schedule) {
            intervals.addAll(employee);
        }
        
        intervals.sort((a, b) -> a[0] - b[0]);
        List<int[]> merged = new ArrayList<>();
        
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }
        
        List<int[]> result = new ArrayList<>();
        for (int i = 1; i < merged.size(); i++) {
            result.add(new int[]{merged.get(i - 1)[1], merged.get(i)[0]});
        }
        return result;
    }
    
    // 8. My Calendar I
    static class MyCalendar {
        private List<int[]> calendar = new ArrayList<>();
        
        public boolean book(int start, int end) {
            for (int[] event : calendar) {
                if (start < event[1] && end > event[0]) return false;
            }
            calendar.add(new int[]{start, end});
            return true;
        }
    }
    
    // 9. Minimum Number of Arrows to Burst Balloons
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1, end = points[0][1];
        
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > end) {
                arrows++;
                end = points[i][1];
            }
        }
        return arrows;
    }
    
    // 10. Car Pooling
    public boolean carPooling(int[][] trips, int capacity) {
        int[] timeline = new int[1001];
        
        for (int[] trip : trips) {
            timeline[trip[1]] += trip[0];
            timeline[trip[2]] -= trip[0];
        }
        
        int passengers = 0;
        for (int change : timeline) {
            passengers += change;
            if (passengers > capacity) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        IntervalsExamples examples = new IntervalsExamples();
        
        System.out.println("=== Intervals Examples ===");
        
        // Test 1: Merge Intervals
        int[][] intervals1 = {{1,3},{2,6},{8,10},{15,18}};
        System.out.println("Merge Intervals: " + Arrays.deepToString(examples.merge(intervals1)));
        
        // Test 2: Insert Interval
        int[][] intervals2 = {{1,3},{6,9}};
        int[] newInterval = {2,5};
        System.out.println("Insert Interval: " + Arrays.deepToString(examples.insert(intervals2, newInterval)));
        
        // Test 3: Non-overlapping Intervals
        int[][] intervals3 = {{1,2},{2,3},{3,4},{1,3}};
        System.out.println("Erase Overlap Intervals: " + examples.eraseOverlapIntervals(intervals3));
        
        // Test 4: Meeting Rooms
        int[][] meetings1 = {{0,30},{5,10},{15,20}};
        System.out.println("Can Attend Meetings: " + examples.canAttendMeetings(meetings1));
        
        // Test 5: Meeting Rooms II
        int[][] meetings2 = {{0,30},{5,10},{15,20}};
        System.out.println("Min Meeting Rooms: " + examples.minMeetingRooms(meetings2));
        
        // Test 6: Interval Intersections
        int[][] first = {{0,2},{5,10},{13,23},{24,25}};
        int[][] second = {{1,5},{8,12},{15,24},{25,26}};
        System.out.println("Interval Intersections: " + Arrays.deepToString(examples.intervalIntersection(first, second)));
        
        // Test 7: Employee Free Time
        List<List<int[]>> schedule = Arrays.asList(
            Arrays.asList(new int[]{1,3}, new int[]{6,7}),
            Arrays.asList(new int[]{2,4}),
            Arrays.asList(new int[]{2,5}, new int[]{9,12})
        );
        System.out.println("Employee Free Time: " + examples.employeeFreeTime(schedule).size() + " free intervals");
        
        // Test 8: My Calendar
        MyCalendar calendar = new MyCalendar();
        System.out.println("Calendar book(10,20): " + calendar.book(10, 20));
        System.out.println("Calendar book(15,25): " + calendar.book(15, 25));
        System.out.println("Calendar book(20,30): " + calendar.book(20, 30));
        
        // Test 9: Min Arrow Shots
        int[][] balloons = {{10,16},{2,8},{1,6},{7,12}};
        System.out.println("Min Arrow Shots: " + examples.findMinArrowShots(balloons));
        
        // Test 10: Car Pooling
        int[][] trips = {{2,1,5},{3,3,7}};
        System.out.println("Car Pooling(capacity=4): " + examples.carPooling(trips, 4));
    }
}