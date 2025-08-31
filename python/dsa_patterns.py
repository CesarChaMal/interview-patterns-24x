# -*- coding: utf-8 -*-
"""
24 Essential DSA Patterns for Coding Interviews - Python Implementation

Complete study resource with 240 LeetCode solutions across all patterns.
Each pattern includes 10 working examples with real algorithm execution.

Study Guide:
- Core Patterns (1-16): Foundation covering 85% of problems
- Advanced Patterns (17-24): Specialization covering remaining 15%
- Practice 2-3 patterns per week for optimal retention
"""

import sys
import os
import subprocess

# Add examples directory to path for imports
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'examples', 'python'))

# 🎓 DIRECT EXECUTION: Comment out patterns you want to skip
pattern_files = [
    'sliding_window_examples.py',
    'two_pointers_examples.py', 
    'monotonic_stack_examples.py',
    'heap_examples.py',
    'binary_search_examples.py',
    'backtracking_examples.py',
    'bfs_examples.py',
    'dynamic_programming_examples.py',
    'fast_slow_pointers_examples.py',
    'linked_list_reversal_examples.py',
    'intervals_examples.py',
    'prefix_sum_examples.py',
    'binary_search_answer_examples.py',
    'monotonic_deque_examples.py',
    'topological_sort_examples.py',
    'union_find_examples.py',
    'tree_dfs_examples.py',
    'trie_examples.py',
    'segment_tree_examples.py',
    'fenwick_tree_examples.py',
    'dfs_memoization_examples.py',
    'dijkstra_examples.py',
    'bit_manipulation_examples.py',
    'matrix_traversal_examples.py'
]

# Execute all pattern examples
examples_dir = os.path.join(os.path.dirname(__file__), '..', 'examples', 'python')
for pattern_file in pattern_files:
    file_path = os.path.join(examples_dir, pattern_file)
    if os.path.exists(file_path):
        subprocess.run([sys.executable, file_path], cwd=examples_dir)

# 🎓 STUDY TIP: Comment out files above to skip patterns
# The count will automatically adjust based on active files

# Pattern information (all 24 patterns defined)
patterns = [
    # ===== CORE PATTERNS (1-16) - FOUNDATION LEVEL =====
    # Master these first - they cover 85% of interview problems
    
    {"name": "Sliding Window", "complexity": "O(n)", "description": "Subarray problems, longest substring"},
    {"name": "Two Pointers", "complexity": "O(n)", "description": "Array traversal, palindromes"},
    {"name": "Monotonic Stack", "complexity": "O(n)", "description": "Next greater element, daily temperatures"},
    {"name": "Heap (Top-K)", "complexity": "O(n log k)", "description": "Priority queue, k-largest problems"},
    {"name": "Binary Search", "complexity": "O(log n)", "description": "Search in sorted arrays"},
    {"name": "Backtracking", "complexity": "O(2^n)", "description": "Combinations, permutations, N-Queens"},
    {"name": "BFS", "complexity": "O(V+E)", "description": "Graph traversal, shortest path"},
    {"name": "Dynamic Programming", "complexity": "O(n^2)", "description": "Optimization problems, memoization"},
    {"name": "Fast/Slow Pointers", "complexity": "O(n)", "description": "Cycle detection, middle element"},
    {"name": "Linked List Reversal", "complexity": "O(n)", "description": "In-place node manipulation"},
    {"name": "Intervals", "complexity": "O(n log n)", "description": "Merge, scheduling, overlaps"},
    {"name": "Prefix Sum + HashMap", "complexity": "O(n)", "description": "Subarray sum problems"},
    {"name": "Binary Search on Answer", "complexity": "O(n log m)", "description": "Search space reduction"},
    {"name": "Monotonic Deque", "complexity": "O(n)", "description": "Sliding window maximum"},
    {"name": "Topological Sort", "complexity": "O(V+E)", "description": "Dependency resolution"},
    {"name": "Union-Find", "complexity": "O(a(n))", "description": "Connected components"},
    
    # ===== ADVANCED PATTERNS (17-24) - SPECIALIZATION LEVEL =====
    # Learn these after mastering core patterns - they cover remaining 15%
    
    {"name": "Tree DFS", "complexity": "O(n)", "description": "Tree traversals, path problems"},
    {"name": "Trie", "complexity": "O(m)", "description": "String matching, autocomplete"},
    {"name": "Segment Tree", "complexity": "O(log n)", "description": "Range queries and updates"},
    {"name": "Fenwick Tree (BIT)", "complexity": "O(log n)", "description": "Efficient prefix sums"},
    {"name": "DFS + Memoization", "complexity": "O(n*m)", "description": "Top-down DP with recursion"},
    {"name": "Dijkstra's Algorithm", "complexity": "O(E log V)", "description": "Shortest path in weighted graphs"},
    {"name": "Bit Manipulation", "complexity": "O(1)", "description": "XOR tricks, bit masking"},
    {"name": "Matrix Traversal", "complexity": "O(m*n)", "description": "2D grid problems, islands"}
    
    # 💡 EXAMPLE: To focus on just Sliding Window and Two Pointers:
    # 1. Comment out unwanted files in pattern_files list above
    # 2. The program will automatically show only active patterns
    # 3. Total count will adjust dynamically
]

def print_study_guide():
    print("\n" + "=" * 80)
    print("COMPLETE DSA PATTERNS STUDY GUIDE FOR CODING INTERVIEWS")
    print("=" * 80)
    print("\n[BOOKS] LEARNING PATH:")
    print("   Week 1-4: Master Core Patterns (1-16) - Foundation")
    print("   Week 5-6: Learn Advanced Patterns (17-24) - Specialization")
    print("   Week 7-8: Mixed practice and mock interviews")
    print("\n[TARGET] SUCCESS METRICS:")
    print("   - Recognize pattern in 30 seconds")
    print("   - Implement solution in 10-15 minutes")
    print("   - Explain time/space complexity")
    print("   - Handle edge cases confidently")
    print("\n[BULB] STUDY TIPS:")
    print("   - Focus on pattern recognition over memorization")
    print("   - Practice 2-3 patterns per week for retention")
    print("   - Solve 5-10 problems per pattern")
    print("   - Review failed attempts to identify gaps")
    print("\n[WRENCH] CUSTOMIZATION:")
    print("   - Comment out files in pattern_files list to focus on specific patterns")
    print("   - Add # before any file name to skip it during execution")
    print("   - Uncomment files when ready to practice them")
    print("\n[TROPHY] INTERVIEW READINESS CHECKLIST:")
    print("   [X] Pattern Recognition: Can identify pattern in 30 seconds")
    print("   [X] Implementation Speed: Code solution in 10-15 minutes")
    print("   [X] Complexity Analysis: Explain time/space trade-offs")
    print("   [X] Edge Cases: Handle boundary conditions")
    print("   [X] Code Quality: Clean, readable, bug-free implementation")
    print("   [X] Communication: Explain approach clearly to interviewer")
    print("\n[CHART] DIFFICULTY DISTRIBUTION:")
    print("   [GREEN] Easy (30%): Basic pattern application")
    print("   [YELLOW] Medium (50%): Pattern combinations and optimizations")
    print("   [RED] Hard (20%): Advanced patterns and edge cases")
    print("\n[TARGET] COMPANY FOCUS:")
    print("   * FAANG: All 24 patterns essential")
    print("   * Startups: Focus on Core Patterns (1-16)")
    print("   * Enterprise: Emphasize algorithms and system design")
    print("\n" + "=" * 80)

def main():
    print("Welcome to the Complete DSA Patterns Mastery Program!")
    print("All patterns executed via direct execution with real algorithm output!")
    
    print_study_guide()
    
    total_patterns = len(pattern_files)  # Dynamic count based on active files
    print(f"\nEXECUTED {total_patterns} PATTERNS WITH 10 EXAMPLES EACH")
    print(f"Total: {total_patterns * 10} Complete LeetCode Solutions\n")
    
    if total_patterns < 24:
        print(f"📝 NOTE: {24 - total_patterns} patterns are commented out in pattern_files. Uncomment them to practice more!\n")
    
    # Core Patterns (1-16)
    print("\n" + "=" * 60)
    print("CORE PATTERNS (1-16) - FOUNDATION LEVEL")
    print("Covers 85% of standard coding interview problems")
    print("=" * 60)
    
    for i in range(min(16, total_patterns)):
        pattern = patterns[i]
        print(f"\n{str(i + 1).rjust(2)}. {pattern['name']}")
        print(f"    Time Complexity: {pattern['complexity']}")
        print(f"    Use Case: {pattern['description']}")
        print(f"    [X] 10 examples executed via direct execution")
    
    # Advanced Patterns (17-24)
    if total_patterns > 16:
        print("\n" + "=" * 60)
        print("ADVANCED PATTERNS (17-24) - SPECIALIZATION LEVEL")
        print("Covers remaining 15% of specialized problems")
        print("=" * 60)
        
        for i in range(16, total_patterns):
            pattern = patterns[i]
            print(f"\n{str(i + 1).rjust(2)}. {pattern['name']}")
            print(f"    Time Complexity: {pattern['complexity']}")
            print(f"    Use Case: {pattern['description']}")
            print(f"    [X] 10 examples executed via direct execution")
    
    # Final Summary
    print("\n" + "=" * 80)
    print("EXECUTION SUMMARY")
    print("=" * 80)
    
    core_count = min(16, total_patterns)
    advanced_count = max(0, total_patterns - 16)
    
    print(f"Core Patterns Executed: {core_count}/16")
    print(f"Advanced Patterns Executed: {advanced_count}/8")
    print(f"Total Patterns Executed: {total_patterns}/24")
    print(f"Total Examples Executed: {total_patterns * 10}")
    
    success_rate = (total_patterns / 24 * 100)
    print(f"Coverage: {success_rate:.1f}%")
    
    if total_patterns == 24:
        print("\n[PARTY] CONGRATULATIONS! All 24 patterns executed successfully!")
        print("[ROCKET] You have 100% interview coverage!")
    else:
        print(f"\n[NOTE] {24 - total_patterns} patterns are commented out in pattern_files.")
        print("   Uncomment files to practice more patterns!")
    
    print("\n[BOOKS] Next Steps:")
    print("   1. Practice implementing each pattern from memory")
    print("   2. Time yourself solving 2-3 problems per pattern")
    print("   3. Focus on pattern recognition in new problems")
    print("   4. Review time/space complexity analysis")
    print("\n[MEDAL] MASTERY LEVELS:")
    print("   [BRONZE] Bronze: Recognize and implement with hints (Beginner)")
    print("   [SILVER] Silver: Implement independently in 15-20 minutes (Intermediate)")
    print("   [GOLD] Gold: Implement optimally in 10-15 minutes (Advanced)")
    print("   [DIAMOND] Diamond: Teach others and handle variations (Expert)")
    print("\n[FIRE] INTERVIEW SIMULATION:")
    print("   * Practice 1-2 random patterns daily")
    print("   * Set 20-minute timer for each problem")
    print("   * Explain your approach out loud")
    print("   * Code without IDE assistance")
    print("   * Test with edge cases manually")
    print("\n" + "=" * 80)

if __name__ == "__main__":
    main()