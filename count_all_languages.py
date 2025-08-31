#!/usr/bin/env python3

import os
import re
from collections import defaultdict

def count_main_functions(filepath, language):
    """Count main solution functions (not helper methods)"""
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
    except:
        return 0
    
    if language in ['python']:
        # Count top-level def functions (not nested ones)
        lines = content.split('\n')
        count = 0
        for line in lines:
            # Only count functions that start at column 0 (top-level)
            if re.match(r'^def\s+\w+.*:', line) and not line.startswith('def __'):
                count += 1
        return count
    
    elif language in ['java', 'typescript', 'javascript', 'scala']:
        # Count numbered comment sections (// 1. // 2. etc.)
        pattern = r'//\s*\d+\.\s+'
        matches = re.findall(pattern, content)
        return len(matches)
    
    return 0

def get_pattern_name(filename):
    """Extract pattern name from filename"""
    # Normalize to standard pattern names
    name_mappings = {
        'sliding_window_examples.py': 'sliding_window',
        'slidingwindowexamples.ts': 'sliding_window',
        'slidingwindowexamples.js': 'sliding_window', 
        'slidingwindowexamples.java': 'sliding_window',
        'scalaslidingwindowexamples.scala': 'sliding_window',
        
        'two_pointers_examples.py': 'two_pointers',
        'twopointersexamples.ts': 'two_pointers',
        'twopointersexamples.js': 'two_pointers',
        'twopointersexamples.java': 'two_pointers', 
        'scalatwopointers examples.scala': 'two_pointers',
        
        'backtracking_examples.py': 'backtracking',
        'backtrackingexamples.ts': 'backtracking',
        'backtrackingexamples.js': 'backtracking',
        'backtrackingexamples.java': 'backtracking',
        'scalabacktrackingexamples.scala': 'backtracking',
        
        'heap_examples.py': 'heap',
        'heapexamples.ts': 'heap', 
        'heapexamples.js': 'heap',
        'heapexamples.java': 'heap',
        'scalaheapexamples.scala': 'heap',
        
        'intervals_examples.py': 'intervals',
        'intervalsexamples.ts': 'intervals',
        'intervalsexamples.js': 'intervals', 
        'intervalsexamples.java': 'intervals',
        'scalaintervalsexamples.scala': 'intervals',
        
        'trie_examples.py': 'trie',
        'trieexamples.ts': 'trie',
        'trieexamples.js': 'trie',
        'trieexamples.java': 'trie',
        'scalatrieexamples.scala': 'trie'
    }
    
    normalized = filename.lower()
    if normalized in name_mappings:
        return name_mappings[normalized]
    
    # Fallback: extract pattern name
    name = filename.lower()
    name = re.sub(r'^scala', '', name)
    name = re.sub(r'examples?\.(py|java|ts|js|scala)$', '', name)
    name = re.sub(r'_examples$', '', name)
    
    return name

def main():
    base_dirs = {
        'python': 'examples/python',
        'java': 'examples/java', 
        'typescript': 'examples/typescript',
        'javascript': 'examples/javascript',
        'scala': 'examples/scala'
    }
    
    results = defaultdict(dict)
    
    for lang, lang_dir in base_dirs.items():
        if not os.path.exists(lang_dir):
            print(f"Directory {lang_dir} not found, skipping {lang}")
            continue
            
        extensions = {
            'python': '.py',
            'java': '.java',
            'typescript': '.ts', 
            'javascript': '.js',
            'scala': '.scala'
        }
        
        files = [f for f in os.listdir(lang_dir) if f.endswith(extensions[lang])]
        
        for filename in files:
            pattern_name = get_pattern_name(filename)
            filepath = os.path.join(lang_dir, filename)
            function_count = count_main_functions(filepath, lang)
            results[pattern_name][lang] = function_count
    
    # Print results
    print("=== Complete DSA Patterns Function Count (5 Languages) ===")
    print(f"{'Pattern':<25} {'Python':<8} {'Java':<8} {'TypeScript':<12} {'JavaScript':<12} {'Scala':<8} {'Status':<12}")
    print("-" * 100)
    
    total_patterns = 0
    perfect_patterns = 0  # All 5 languages have exactly 10
    
    # Filter to only show patterns that exist in multiple languages
    filtered_results = {}
    for pattern, counts in results.items():
        non_zero_count = sum(1 for c in counts.values() if c > 0)
        if non_zero_count >= 3:  # Pattern exists in at least 3 languages
            filtered_results[pattern] = counts
    
    for pattern in sorted(filtered_results.keys()):
        counts = filtered_results[pattern]
        python_count = counts.get('python', 0)
        java_count = counts.get('java', 0)
        ts_count = counts.get('typescript', 0)
        js_count = counts.get('javascript', 0)
        scala_count = counts.get('scala', 0)
        
        all_counts = [python_count, java_count, ts_count, js_count, scala_count]
        
        if all(c == 10 for c in all_counts):
            perfect_patterns += 1
            status = "PERFECT"
        elif all(c > 0 for c in all_counts) and len(set(all_counts)) == 1:
            status = f"CONSISTENT({all_counts[0]})"
        else:
            status = "INCONSISTENT"
        
        total_patterns += 1
        
        print(f"{pattern:<25} {python_count:<8} {java_count:<8} {ts_count:<12} {js_count:<12} {scala_count:<8} {status:<12}")
    
    print("-" * 100)
    print(f"Total Patterns: {total_patterns}")
    print(f"Perfect Patterns (10 each): {perfect_patterns}")
    print(f"Perfect Rate: {perfect_patterns/total_patterns*100:.1f}%")
    
    total_functions = sum(sum(pattern.values()) for pattern in filtered_results.values())
    expected_total = 24 * 5 * 10  # 24 patterns × 5 languages × 10 functions
    print(f"Total functions: {total_functions} (expected: {expected_total})")

if __name__ == "__main__":
    main()