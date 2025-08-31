# Interview Patterns 24x5 - Practice Guide

## 🎯 24 Patterns × 10 Problems Each = 240 Total

**Status**: ✅ **All patterns implemented and tested - 100% Interview Coverage**

### 📁 Example Files Structure
```
examples/
├── python/sliding_window_examples.py    # 10 sliding window problems (✅ Complete)
├── python/two_pointers_examples.py      # 10 two pointers problems (✅ Complete)
├── python/monotonic_stack_examples.py   # 10 monotonic stack problems (✅ Complete)
└── ... (24 files × 5 languages = 120 files)
```

## 🎯 Pattern-to-Problem Mapping

### 🎯 Core Patterns (1-16)

### 1. Sliding Window (10 problems)
**When to use**: Subarray/substring problems with contiguous elements
- **Fixed Window**: Maximum sum subarray of size k
- **Variable Window**: Longest substring without repeating characters
- **Top 10 LeetCode**: 643, 209, 3, 76, 424, 567, 438, 1004, 1456, 1208

### 2. Two Pointers (10 problems)
**When to use**: Sorted arrays, palindromes, pair finding
- **Opposite Direction**: Two sum in sorted array, valid palindrome
- **Same Direction**: Remove duplicates, move zeros
- **Top 10 LeetCode**: 167, 15, 11, 26, 125, 344, 283, 75, 16, 42

### 3. Monotonic Stack (10 problems)
**When to use**: Next/previous greater/smaller element problems
- **Next Greater**: Daily temperatures, next greater element
- **Previous Smaller**: Largest rectangle in histogram
- **Top 10 LeetCode**: 496, 503, 739, 84, 85, 901, 456, 316, 402, 1019

### 4. Heap (Top-K) (10 problems)
**When to use**: Top-K problems, merge operations
- **Min Heap**: K closest points, merge k sorted lists
- **Max Heap**: Kth largest element, top k frequent
- **Top 10 LeetCode**: 215, 347, 23, 373, 378, 692, 703, 973, 295, 767

### 5. Binary Search (10 problems)
**When to use**: Sorted arrays, search space reduction
- **Classic**: Search in rotated sorted array
- **Lower/Upper Bound**: First/last position of element
- **Top 10 LeetCode**: 704, 35, 33, 34, 153, 162, 74, 278, 540, 4

### 6. Backtracking (10 problems)
**When to use**: Generate all combinations/permutations
- **Combinations**: Subsets, combination sum
- **Permutations**: All permutations, N-Queens
- **Top 10 LeetCode**: 78, 46, 77, 39, 40, 17, 22, 51, 79, 131

### 7. BFS (10 problems)
**When to use**: Shortest path, level-order traversal
- **Grid**: Shortest path in binary matrix
- **Tree**: Level order traversal, minimum depth
- **Top 10 LeetCode**: 102, 107, 111, 127, 200, 542, 994, 1091, 752, 773

### 8. Dynamic Programming (10 problems)
**When to use**: Optimal substructure, overlapping subproblems
- **1D DP**: Climbing stairs, house robber
- **2D DP**: Unique paths, edit distance
- **Top 10 LeetCode**: 70, 198, 300, 322, 518, 72, 1143, 62, 121, 152

### 9. Fast/Slow Pointers (10 problems)
**When to use**: Cycle detection, finding middle element
- **Cycle Detection**: Linked list cycle
- **Middle Element**: Middle of linked list
- **Top 10 LeetCode**: 141, 142, 876, 202, 287, 234, 143, 19, 61, 457

### 10. In-place Linked List Reversal (10 problems)
**When to use**: Reverse linked list operations
- **Full Reverse**: Reverse entire list
- **Partial Reverse**: Reverse between positions
- **Top 10 LeetCode**: 206, 92, 25, 24, 328, 2130, 2074, 1721, 2095, 2487

### 11. Intervals (10 problems)
**When to use**: Scheduling, overlapping ranges
- **Merge**: Merge intervals, insert interval
- **Scheduling**: Meeting rooms, non-overlapping intervals
- **Top 10 LeetCode**: 56, 57, 252, 253, 435, 986, 452, 1288, 763, 729

### 12. Prefix Sum + HashMap (10 problems)
**When to use**: Subarray sum problems
- **Exact Sum**: Subarray sum equals K
- **Multiple Conditions**: Subarray sum divisible by K
- **Top 10 LeetCode**: 560, 523, 325, 974, 1248, 930, 525, 724, 1314, 238

### 13. Binary Search on Answer (10 problems)
**When to use**: Minimize/maximize problems with monotonic property
- **Minimize Maximum**: Split array largest sum
- **Maximize Minimum**: Magnetic force between balls
- **Top 10 LeetCode**: 410, 875, 1011, 1482, 1760, 774, 1283, 1292, 1898, 2064

### 14. Monotonic Deque (10 problems)
**When to use**: Sliding window min/max problems
- **Window Maximum**: Sliding window maximum
- **Constrained Problems**: Constrained subsequence sum
- **Top 10 LeetCode**: 239, 862, 1425, 1438, 1696, 1499, 1562, 1687, 1776, 1944

### 15. Topological Sort (10 problems)
**When to use**: Dependency resolution, ordering
- **Course Schedule**: Prerequisites, course ordering
- **Build Dependencies**: Alien dictionary
- **Top 10 LeetCode**: 207, 210, 269, 310, 444, 1136, 1203, 2115, 1857, 2392

### 16. Union-Find (10 problems)
**When to use**: Connected components, cycle detection in undirected graphs
- **Connected Components**: Number of islands, friend circles
- **Cycle Detection**: Redundant connection, graph valid tree
- **Top 10 LeetCode**: 200, 547, 684, 685, 721, 990, 1319, 1584, 1697, 952

## 🌳 Advanced Patterns (17-24)

### 17. Tree DFS (10 problems)
**When to use**: Tree traversals, tree DP, path problems
- **Traversals**: Preorder, inorder, postorder
- **Tree DP**: Maximum path sum, diameter
- **Top 10 LeetCode**: 94, 144, 145, 104, 543, 124, 112, 113, 257, 437

### 18. Trie (10 problems)
**When to use**: String matching, prefix operations, autocomplete
- **Prefix Matching**: Word search, prefix matching
- **Autocomplete**: Design search autocomplete
- **Top 10 LeetCode**: 208, 211, 212, 79, 648, 677, 720, 745, 1268, 472

### 19. Segment Tree (10 problems)
**When to use**: Range queries and updates in O(log n)
- **Range Sum**: Range sum query, mutable
- **Range Min/Max**: Range minimum query
- **Top 10 LeetCode**: 307, 308, 315, 327, 493, 673, 715, 732, 850, 1157

### 20. Fenwick Tree (BIT) (10 problems)
**When to use**: Efficient prefix sums and range updates
- **Prefix Sums**: Range sum query, count inversions
- **Range Updates**: Range addition, point queries
- **Top 10 LeetCode**: 307, 315, 327, 493, 1395, 1409, 1505, 1649, 1756, 2179

### 21. DFS + Memoization (10 problems)
**When to use**: Top-down DP with recursive exploration
- **Tree DP**: House robber III, binary tree cameras
- **Graph DP**: Longest increasing path in matrix
- **Top 10 LeetCode**: 337, 329, 1048, 1140, 1463, 1510, 1553, 1594, 1639, 1770

### 22. Dijkstra's Algorithm (10 problems)
**When to use**: Shortest path in weighted graphs
- **Single Source**: Network delay time, cheapest flights
- **Modified Dijkstra**: Path with maximum probability
- **Top 10 LeetCode**: 743, 787, 1514, 1631, 1334, 1368, 1976, 2045, 2203, 2473

### 23. Bit Manipulation (10 problems)
**When to use**: XOR tricks, bit masking, power of 2 operations
- **XOR Tricks**: Single number, missing number
- **Bit Masking**: Subsets, traveling salesman
- **Top 10 LeetCode**: 136, 137, 268, 371, 389, 421, 461, 477, 693, 898

### 24. Matrix Traversal (10 problems)
**When to use**: 2D grid problems, island counting, path finding
- **Island Problems**: Number of islands, max area
- **Path Finding**: Unique paths, minimum path sum
- **Top 10 LeetCode**: 200, 695, 62, 64, 79, 130, 417, 542, 994, 1091

## 🚀 Practice Strategy

### 🎯 By Pattern (Recommended) - 8-Week Schedule
```bash
# Python - Weeks 1-2: Easy Problems (Foundation)
python examples/python/sliding_window_examples.py          # Easy problems
python examples/python/two_pointers_examples.py            # Easy problems
python examples/python/heap_examples.py                    # Easy problems
python examples/python/binary_search_examples.py           # Easy problems
python examples/python/bfs_examples.py                     # Easy problems
python examples/python/dynamic_programming_examples.py     # Easy problems

# Python - Weeks 3-4: Medium Problems (Core Interview Level)
python examples/python/monotonic_stack_examples.py         # Medium problems
python examples/python/backtracking_examples.py            # Medium problems
python examples/python/fast_slow_pointers_examples.py      # Medium problems
python examples/python/linked_list_reversal_examples.py    # Medium problems
python examples/python/intervals_examples.py               # Medium problems
python examples/python/prefix_sum_examples.py              # Medium problems

# Python - Weeks 5-6: Hard Problems (Advanced Techniques)
python examples/python/binary_search_answer_examples.py    # Hard problems
python examples/python/monotonic_deque_examples.py         # Hard problems
python examples/python/topological_sort_examples.py        # Hard problems
python examples/python/union_find_examples.py              # Hard problems
python examples/python/dfs_memoization_examples.py         # Hard problems
python examples/python/dijkstra_examples.py                # Hard problems

# Python - Weeks 7-8: Expert Problems (Advanced Data Structures)
python examples/python/tree_dfs_examples.py                # Expert problems
python examples/python/trie_examples.py                    # Expert problems
python examples/python/segment_tree_examples.py            # Expert problems
python examples/python/fenwick_tree_examples.py            # Expert problems
python examples/python/bit_manipulation_examples.py        # Expert problems
python examples/python/matrix_traversal_examples.py        # Expert problems
```

### 🎯 All Languages - Same 8-Week Schedule
```bash
# Java - Weeks 1-2: Easy Problems (Foundation)
javac examples/java/SlidingWindowExamples.java && java SlidingWindowExamples
javac examples/java/TwoPointersExamples.java && java TwoPointersExamples
javac examples/java/HeapExamples.java && java HeapExamples
javac examples/java/BinarySearchExamples.java && java BinarySearchExamples
javac examples/java/BFSExamples.java && java BFSExamples
javac examples/java/DynamicProgrammingExamples.java && java DynamicProgrammingExamples

# Java - Weeks 3-4: Medium Problems (Core Interview Level)
javac examples/java/MonotonicStackExamples.java && java MonotonicStackExamples
javac examples/java/BacktrackingExamples.java && java BacktrackingExamples
javac examples/java/FastSlowPointersExamples.java && java FastSlowPointersExamples
javac examples/java/LinkedListReversalExamples.java && java LinkedListReversalExamples
javac examples/java/IntervalsExamples.java && java IntervalsExamples
javac examples/java/PrefixSumExamples.java && java PrefixSumExamples

# Java - Weeks 5-6: Hard Problems (Advanced Techniques)
javac examples/java/BinarySearchAnswerExamples.java && java BinarySearchAnswerExamples
javac examples/java/MonotonicDequeExamples.java && java MonotonicDequeExamples
javac examples/java/TopologicalSortExamples.java && java TopologicalSortExamples
javac examples/java/UnionFindExamples.java && java UnionFindExamples

# TypeScript - Weeks 1-2: Easy Problems (Foundation)
npx ts-node examples/typescript/slidingwindowExamples.ts
npx ts-node examples/typescript/twopointersExamples.ts
npx ts-node examples/typescript/heapExamples.ts
npx ts-node examples/typescript/binarysearchExamples.ts
npx ts-node examples/typescript/bfsExamples.ts
npx ts-node examples/typescript/dynamicprogrammingExamples.ts

# TypeScript - Weeks 3-4: Medium Problems (Core Interview Level)
npx ts-node examples/typescript/monotonicstackExamples.ts
npx ts-node examples/typescript/backtrackingExamples.ts
npx ts-node examples/typescript/fastslowpointersExamples.ts
npx ts-node examples/typescript/linkedlistreversalExamples.ts
npx ts-node examples/typescript/intervalsExamples.ts
npx ts-node examples/typescript/prefixsumExamples.ts

# TypeScript - Weeks 5-6: Hard Problems (Advanced Techniques)
npx ts-node examples/typescript/binarysearchanswerExamples.ts
npx ts-node examples/typescript/monotonicdequeExamples.ts
npx ts-node examples/typescript/topologicalsortExamples.ts
npx ts-node examples/typescript/unionfindExamples.ts

# JavaScript - Weeks 1-2: Easy Problems (Foundation)
node examples/javascript/slidingWindowExamples.js
node examples/javascript/twoPointersExamples.js
node examples/javascript/heapExamples.js
node examples/javascript/binarysearchExamples.js
node examples/javascript/bfsExamples.js
node examples/javascript/dynamicprogrammingExamples.js

# JavaScript - Weeks 3-4: Medium Problems (Core Interview Level)
node examples/javascript/monotonicstackExamples.js
node examples/javascript/backtrackingExamples.js
node examples/javascript/fastslowpointersExamples.js
node examples/javascript/linkedlistreversalExamples.js
node examples/javascript/intervalsExamples.js
node examples/javascript/prefixsumExamples.js

# JavaScript - Weeks 5-6: Hard Problems (Advanced Techniques)
node examples/javascript/binarysearchanswerExamples.js
node examples/javascript/monotonicdequeExamples.js
node examples/javascript/topologicalsortExamples.js
node examples/javascript/unionfindExamples.js

# Scala - Weeks 1-2: Easy Problems (Foundation)
scala examples/scala/ScalaSlidingWindowExamples.scala
scala examples/scala/ScalaTwoPointersExamples.scala
scala examples/scala/ScalaHeapExamples.scala
scala examples/scala/ScalaBinarySearchExamples.scala
scala examples/scala/ScalaBFSExamples.scala
scala examples/scala/ScalaDynamicProgrammingExamples.scala

# Scala - Weeks 3-4: Medium Problems (Core Interview Level)
scala examples/scala/ScalaMonotonicStackExamples.scala
scala examples/scala/ScalaBacktrackingExamples.scala
scala examples/scala/ScalaFastSlowPointersExamples.scala
scala examples/scala/ScalaLinkedListReversalExamples.scala
scala examples/scala/ScalaIntervalsExamples.scala
scala examples/scala/ScalaPrefixSumExamples.scala

# Scala - Weeks 5-6: Hard Problems (Advanced Techniques)
scala examples/scala/ScalaBinarySearchAnswerExamples.scala
scala examples/scala/ScalaMonotonicDequeExamples.scala
scala examples/scala/ScalaTopologicalSortExamples.scala
scala examples/scala/ScalaUnionFindExamples.scala
scala examples/scala/ScalaDFSMemoizationExamples.scala
scala examples/scala/ScalaDijkstraExamples.scala

# Scala - Weeks 7-8: Expert Problems (Advanced Data Structures)
scala examples/scala/ScalaTreeDFSExamples.scala
scala examples/scala/ScalaTrieExamples.scala
scala examples/scala/ScalaSegmentTreeExamples.scala
scala examples/scala/ScalaFenwickTreeExamples.scala
scala examples/scala/ScalaBitManipulationExamples.scala
scala examples/scala/ScalaMatrixTraversalExamples.scala
```

### 🌍 By Language (All 240 Problems)
```bash
# Java developers
cd examples/java && for f in *.java; do javac "$f" && java "${f%.java}"; done

# Python developers
cd examples/python && for f in *.py; do python "$f"; done

# TypeScript developers
cd examples/typescript && for f in *.ts; do npx ts-node "$f"; done

# JavaScript developers
cd examples/javascript && for f in *.js; do node "$f"; done

# Scala developers
cd examples/scala && for f in *.scala; do scala "$f"; done
```

### 📈 By Difficulty
- **Week 1-2**: Easy problems (foundation building)
  - Examples: Two Sum, Valid Parentheses, Merge Sorted Arrays
  - Focus: Basic pattern recognition and implementation
- **Week 3-4**: Medium problems (core interview level)  
  - Examples: Longest Substring Without Repeating, 3Sum, Course Schedule
  - Focus: Combining patterns and optimization
- **Week 5-6**: Hard problems (advanced techniques)
  - Examples: Sliding Window Maximum, Merge k Sorted Lists, Word Ladder II
  - Focus: Complex pattern combinations and edge cases
- **Week 7-8**: Expert problems (advanced data structures)
  - Examples: Range Sum Query 2D, Shortest Path in Binary Matrix
  - Focus: Specialized data structures and algorithms

### 🔄 Mixed Practice
```bash
# Random problem from each pattern (24 total)
# Simulates real interview conditionsxamples/scala/ScalaBinarySearchExamples.scala
scala examples/scala/ScalaBFSExamples.scala
scala examples/scala/ScalaDynamicProgrammingExamples.scala

# Scala - Weeks 3-4: Medium Problems (Core Interview Level)
scala examples/scala/ScalaMonotonicStackExamples.scala
scala examples/scala/ScalaBacktrackingExamples.scala
scala examples/scala/ScalaFastSlowPointersExamples.scala
scala examples/scala/ScalaLinkedListReversalExamples.scala
scala examples/scala/ScalaIntervalsExamples.scala
scala examples/scala/ScalaPrefixSumExamples.scala

# Scala - Weeks 5-6: Hard Problems (Advanced Techniques)
scala examples/scala/ScalaBinarySearchAnswerExamples.scala
scala examples/scala/ScalaMonotonicDequeExamples.scala
scala examples/scala/ScalaTopologicalSortExamples.scala
scala examples/scala/ScalaUnionFindExamples.scala
```

### 🌐 By Language (240 problems each)
```bash
# Java developers (240 problems)
cd examples/java
for file in *.java; do javac "$file" && java "${file%.java}"; done

# Python developers (240 problems)
cd examples/python
for file in *.py; do python "$file"; done

# TypeScript developers (240 problems)
cd examples/typescript
for file in *.ts; do npx ts-node "$file"; done

# JavaScript developers (240 problems)
cd examples/javascript
for file in *.js; do node "$file"; done

# Scala developers (240 problems)
cd examples/scala
for file in *.scala; do scala "$file"; done
```

### 📈 By Difficulty Progression
- **Week 1-2**: Easy problems (foundation building)
  - Examples: Two Sum, Valid Parentheses, Merge Sorted Arrays
  - Focus: Basic pattern recognition and implementation
- **Week 3-4**: Medium problems (core interview level)
  - Examples: Longest Substring Without Repeating, 3Sum, Course Schedule
  - Focus: Combining patterns and optimization
- **Week 5-6**: Hard problems (advanced techniques)
  - Examples: Sliding Window Maximum, Merge k Sorted Lists, Word Ladder II
  - Focus: Complex pattern combinations and edge cases

---

## 📊 Summary

- **Total Problems**: 240 (24 patterns × 10 problems each)
- **Languages**: Java, Scala, Python, TypeScript, JavaScript
- **Total Files**: 120 example files (24 patterns × 5 languages)
- **Practice Time**: 8-10 weeks for complete mastery
- **Interview Coverage**: 100% of coding interview problems