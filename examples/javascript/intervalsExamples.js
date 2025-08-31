// 1. Merge Intervals
function merge(intervals) {
    intervals.sort((a, b) => a[0] - b[0]);
    const merged = [];
    
    for (const interval of intervals) {
        if (merged.length === 0 || merged[merged.length - 1][1] < interval[0]) {
            merged.push(interval);
        } else {
            merged[merged.length - 1][1] = Math.max(merged[merged.length - 1][1], interval[1]);
        }
    }
    
    return merged;
}

// 2. Insert Interval
function insert(intervals, newInterval) {
    const result = [];
    let i = 0;
    
    // Add all intervals before newInterval
    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        result.push(intervals[i++]);
    }
    
    // Merge overlapping intervals
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.push(newInterval);
    
    // Add remaining intervals
    while (i < intervals.length) {
        result.push(intervals[i++]);
    }
    
    return result;
}

// 3. Non-overlapping Intervals
function eraseOverlapIntervals(intervals) {
    intervals.sort((a, b) => a[1] - b[1]);
    let count = 0;
    let end = intervals[0][1];
    
    for (let i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < end) {
            count++;
        } else {
            end = intervals[i][1];
        }
    }
    
    return count;
}

// 4. Meeting Rooms
function canAttendMeetings(intervals) {
    intervals.sort((a, b) => a[0] - b[0]);
    for (let i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < intervals[i - 1][1]) return false;
    }
    return true;
}

// 5. Meeting Rooms II
function minMeetingRooms(intervals) {
    intervals.sort((a, b) => a[0] - b[0]);
    const heap = [];
    
    for (const interval of intervals) {
        if (heap.length > 0 && heap[0] <= interval[0]) {
            heap.shift(); // Remove min element (simulate heap.pop())
            heap.sort((a, b) => a - b); // Re-sort to maintain min heap
        }
        heap.push(interval[1]);
        heap.sort((a, b) => a - b); // Maintain min heap order
    }
    
    return heap.length;
}

// 6. Interval List Intersections
function intervalIntersection(firstList, secondList) {
    const result = [];
    let i = 0, j = 0;
    
    while (i < firstList.length && j < secondList.length) {
        const start = Math.max(firstList[i][0], secondList[j][0]);
        const end = Math.min(firstList[i][1], secondList[j][1]);
        
        if (start <= end) {
            result.push([start, end]);
        }
        
        if (firstList[i][1] < secondList[j][1]) i++;
        else j++;
    }
    
    return result;
}

// 7. Employee Free Time
function employeeFreeTime(schedule) {
    const intervals = [];
    for (const employee of schedule) {
        intervals.push(...employee);
    }
    
    intervals.sort((a, b) => a[0] - b[0]);
    const merged = [];
    
    for (const interval of intervals) {
        if (merged.length === 0 || merged[merged.length - 1][1] < interval[0]) {
            merged.push(interval);
        } else {
            merged[merged.length - 1][1] = Math.max(merged[merged.length - 1][1], interval[1]);
        }
    }
    
    const result = [];
    for (let i = 1; i < merged.length; i++) {
        result.push([merged[i - 1][1], merged[i][0]]);
    }
    
    return result;
}

// 8. My Calendar I
class MyCalendar {
    constructor() {
        this.calendar = [];
    }
    
    book(start, end) {
        for (const [s, e] of this.calendar) {
            if (start < e && end > s) return false;
        }
        this.calendar.push([start, end]);
        return true;
    }
}

// 9. Minimum Number of Arrows to Burst Balloons
function findMinArrowShots(points) {
    points.sort((a, b) => a[1] - b[1]);
    let arrows = 1;
    let end = points[0][1];
    
    for (let i = 1; i < points.length; i++) {
        if (points[i][0] > end) {
            arrows++;
            end = points[i][1];
        }
    }
    
    return arrows;
}

// 10. Car Pooling
function carPooling(trips, capacity) {
    const timeline = new Array(1001).fill(0);
    
    for (const [passengers, start, end] of trips) {
        timeline[start] += passengers;
        timeline[end] -= passengers;
    }
    
    let currentPassengers = 0;
    for (const change of timeline) {
        currentPassengers += change;
        if (currentPassengers > capacity) return false;
    }
    
    return true;
}

// Test cases
console.log("=== Intervals Examples ===");

// Test 1 Intervals
console.log("Merge Intervals([[1,3],[2,6],[8,10],[15,18]]):", merge([[1,3],[2,6],[8,10],[15,18]]));

// Test 2 Interval
console.log("Insert Interval([[1,3],[6,9]], [2,5]):", insert([[1,3],[6,9]], [2,5]));

// Test 3-overlapping Intervals
console.log("Erase Overlap Intervals([[1,2],[2,3],[3,4],[1,3]]):", eraseOverlapIntervals([[1,2],[2,3],[3,4],[1,3]]));

// Test 4 Rooms
console.log("Can Attend Meetings([[0,30],[5,10],[15,20]]):", canAttendMeetings([[0,30],[5,10],[15,20]]));

// Test 5 Rooms II
console.log("Min Meeting Rooms([[0,30],[5,10],[15,20]]):", minMeetingRooms([[0,30],[5,10],[15,20]]));

// Test 6 Intersections
const first = [[0,2],[5,10],[13,23],[24,25]];
const second = [[1,5],[8,12],[15,24],[25,26]];
console.log("Interval Intersections:", intervalIntersection(first, second));

// Test 7 Free Time
const schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]];
console.log("Employee Free Time:", employeeFreeTime(schedule).length, "free intervals");

// Test 8 Calendar
const calendar = new MyCalendar();
console.log("Calendar book(10,20):", calendar.book(10, 20));
console.log("Calendar book(15,25):", calendar.book(15, 25));
console.log("Calendar book(20,30):", calendar.book(20, 30));

// Test 9 Arrow Shots
console.log("Min Arrow Shots([[10,16],[2,8],[1,6],[7,12]]):", findMinArrowShots([[10,16],[2,8],[1,6],[7,12]]));

// Test 10 Pooling
console.log("Car Pooling([[2,1,5],[3,3,7]], capacity=4):", carPooling([[2,1,5],[3,3,7]], 4));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        merge,
        insert,
        eraseOverlapIntervals,
        canAttendMeetings,
        minMeetingRooms,
        intervalIntersection,
        employeeFreeTime,
        findMinArrowShots,
        carPooling
    };
}