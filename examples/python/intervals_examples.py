import heapq

# 1. Merge
def merge(intervals):
    intervals.sort()
    merged = []
    
    for interval in intervals:
        if not merged or merged[-1][1] < interval[0]:
            merged.append(interval)
        else:
            merged[-1][1] = max(merged[-1][1], interval[1])
    
    return merged

# 2. Insert
def insert(intervals, new_interval):
    result = []
    i = 0
    
    # Add all intervals before new_interval
    while i < len(intervals) and intervals[i][1] < new_interval[0]:
        result.append(intervals[i])
        i += 1
    
    # Merge overlapping intervals
    while i < len(intervals) and intervals[i][0] <= new_interval[1]:
        new_interval[0] = min(new_interval[0], intervals[i][0])
        new_interval[1] = max(new_interval[1], intervals[i][1])
        i += 1
    result.append(new_interval)
    
    # Add remaining intervals
    while i < len(intervals):
        result.append(intervals[i])
        i += 1
    
    return result

# 3. Erase Overlap Intervals
def erase_overlap_intervals(intervals):
    intervals.sort(key=lambda x: x[1])
    count = 0
    end = intervals[0][1]
    
    for i in range(1, len(intervals)):
        if intervals[i][0] < end:
            count += 1
        else:
            end = intervals[i][1]
    
    return count

# 4. Can Attend Meetings
def can_attend_meetings(intervals):
    intervals.sort()
    for i in range(1, len(intervals)):
        if intervals[i][0] < intervals[i-1][1]:
            return False
    return True

# 5. Min Meeting Rooms
def min_meeting_rooms(intervals):
    intervals.sort()
    heap = []
    
    for interval in intervals:
        if heap and heap[0] <= interval[0]:
            heapq.heappop(heap)
        heapq.heappush(heap, interval[1])
    
    return len(heap)

# 6. Interval Intersection
def interval_intersection(first_list, second_list):
    result = []
    i = j = 0
    
    while i < len(first_list) and j < len(second_list):
        start = max(first_list[i][0], second_list[j][0])
        end = min(first_list[i][1], second_list[j][1])
        
        if start <= end:
            result.append([start, end])
        
        if first_list[i][1] < second_list[j][1]:
            i += 1
        else:
            j += 1
    
    return result

# 7. Employee Free Time
def employee_free_time(schedule):
    intervals = []
    for employee in schedule:
        intervals.extend(employee)
    
    intervals.sort()
    merged = []
    
    for interval in intervals:
        if not merged or merged[-1][1] < interval[0]:
            merged.append(interval)
        else:
            merged[-1][1] = max(merged[-1][1], interval[1])
    
    result = []
    for i in range(1, len(merged)):
        result.append([merged[i-1][1], merged[i][0]])
    
    return result

class MyCalendar:
    def __init__(self):
        self.calendar = []
    
    def book(self, start, end):
        for s, e in self.calendar:
            if start < e and end > s:
                return False
        self.calendar.append([start, end])
        return True

# 8. Find Min Arrow Shots
def find_min_arrow_shots(points):
    points.sort(key=lambda x: x[1])
    arrows = 1
    end = points[0][1]
    
    for i in range(1, len(points)):
        if points[i][0] > end:
            arrows += 1
            end = points[i][1]
    
    return arrows

# 9. Car Pooling
def car_pooling(trips, capacity):
    timeline = [0] * 1001
    
    for passengers, start, end in trips:
        timeline[start] += passengers
        timeline[end] -= passengers
    
    current_passengers = 0
    for change in timeline:
        current_passengers += change
        if current_passengers > capacity:
            return False
    
    return True

# 10. My Calendar Book
def my_calendar_book(bookings):
    calendar = MyCalendar()
    results = []
    for start, end in bookings:
        results.append(calendar.book(start, end))
    return results

def run_examples():
    print("=== Intervals Examples ===")
    
    # Test 1: Merge Intervals
    print(f"Merge Intervals([[1,3],[2,6],[8,10],[15,18]]): {merge([[1,3],[2,6],[8,10],[15,18]])}")
    
    # Test 2: Insert Interval
    print(f"Insert Interval([[1,3],[6,9]], [2,5]): {insert([[1,3],[6,9]], [2,5])}")
    
    # Test 3: Non-overlapping Intervals
    print(f"Erase Overlap Intervals([[1,2],[2,3],[3,4],[1,3]]): {erase_overlap_intervals([[1,2],[2,3],[3,4],[1,3]])}")
    
    # Test 4: Meeting Rooms
    print(f"Can Attend Meetings([[0,30],[5,10],[15,20]]): {can_attend_meetings([[0,30],[5,10],[15,20]])}")
    
    # Test 5: Meeting Rooms II
    print(f"Min Meeting Rooms([[0,30],[5,10],[15,20]]): {min_meeting_rooms([[0,30],[5,10],[15,20]])}")
    
    # Test 6: Interval Intersections
    first = [[0,2],[5,10],[13,23],[24,25]]
    second = [[1,5],[8,12],[15,24],[25,26]]
    print(f"Interval Intersections: {interval_intersection(first, second)}")
    
    # Test 7: Employee Free Time
    schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
    print(f"Employee Free Time: {len(employee_free_time(schedule))} free intervals")
    
    # Test 8: My Calendar
    calendar = MyCalendar()
    print(f"Calendar book(10,20): {calendar.book(10, 20)}")
    print(f"Calendar book(15,25): {calendar.book(15, 25)}")
    print(f"Calendar book(20,30): {calendar.book(20, 30)}")
    
    # Test 9: Min Arrow Shots
    print(f"Min Arrow Shots([[10,16],[2,8],[1,6],[7,12]]): {find_min_arrow_shots([[10,16],[2,8],[1,6],[7,12]])}")
    
    # Test 10: Car Pooling
    print(f"Car Pooling([[2,1,5],[3,3,7]], capacity=4): {car_pooling([[2,1,5],[3,3,7]], 4)}")

if __name__ == "__main__":
    run_examples()