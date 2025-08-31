class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_end = False

class Trie:
    def __init__(self):
        self.root = TrieNode()
    
    def insert(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                node.children[char] = TrieNode()
            node = node.children[char]
        node.is_end = True
    
    def search(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                return False
            node = node.children[char]
        return node.is_end
    
    def starts_with(self, prefix):
        node = self.root
        for char in prefix:
            if char not in node.children:
                return False
            node = node.children[char]
        return True

# 1. Find Words
def find_words(board, words):
    trie = Trie()
    for word in words:
        trie.insert(word)
    
    result = set()
    def dfs(i, j, node, path):
        if node.is_end:
            result.add(path)
        if i < 0 or i >= len(board) or j < 0 or j >= len(board[0]):
            return
        char = board[i][j]
        if char not in node.children:
            return
        board[i][j] = '#'
        for di, dj in [(0,1), (1,0), (0,-1), (-1,0)]:
            dfs(i+di, j+dj, node.children[char], path+char)
        board[i][j] = char
    
    for i in range(len(board)):
        for j in range(len(board[0])):
            dfs(i, j, trie.root, "")
    return list(result)

# 2. Longest Word
def longest_word(words):
    trie = Trie()
    for word in words:
        trie.insert(word)
    
    def dfs(node, path):
        result = path
        for char, child in node.children.items():
            if child.is_end:
                candidate = dfs(child, path + char)
                if len(candidate) > len(result) or (len(candidate) == len(result) and candidate < result):
                    result = candidate
        return result
    
    return dfs(trie.root, "")

# 3. Replace Words
def replace_words(dictionary, sentence):
    trie = Trie()
    for root in dictionary:
        trie.insert(root)
    
    def find_root(word):
        node = trie.root
        for i, char in enumerate(word):
            if char not in node.children:
                return word
            node = node.children[char]
            if node.is_end:
                return word[:i+1]
        return word
    
    return " ".join(find_root(word) for word in sentence.split())

# 4. Word Squares
def word_squares(words):
    trie = Trie()
    for word in words:
        trie.insert(word)
    
    def get_words_with_prefix(prefix):
        node = trie.root
        for char in prefix:
            if char not in node.children:
                return []
            node = node.children[char]
        
        result = []
        def dfs(node, path):
            if node.is_end:
                result.append(prefix + path)
            for char, child in node.children.items():
                dfs(child, path + char)
        dfs(node, "")
        return result
    
    def backtrack(square):
        if len(square) == len(words[0]):
            return [square[:]]
        
        prefix = "".join(square[i][len(square)] for i in range(len(square)))
        candidates = get_words_with_prefix(prefix)
        
        result = []
        for word in candidates:
            square.append(word)
            result.extend(backtrack(square))
            square.pop()
        return result
    
    return backtrack([])

# 5. Map Sum
def map_sum(pairs):
    trie = Trie()
    values = {}
    
    def insert(key, val):
        values[key] = val
        node = trie.root
        for char in key:
            if char not in node.children:
                node.children[char] = TrieNode()
            node = node.children[char]
        node.is_end = True
    
    def sum_prefix(prefix):
        node = trie.root
        for char in prefix:
            if char not in node.children:
                return 0
            node = node.children[char]
        
        total = 0
        def dfs(node, path):
            nonlocal total
            if node.is_end and prefix + path in values:
                total += values[prefix + path]
            for char, child in node.children.items():
                dfs(child, path + char)
        dfs(node, "")
        return total
    
    for key, val in pairs:
        insert(key, val)
    return sum_prefix

# 6. Palindrome Pairs
def palindrome_pairs(words):
    def is_palindrome(s):
        return s == s[::-1]
    
    result = []
    for i in range(len(words)):
        for j in range(len(words)):
            if i != j:
                combined = words[i] + words[j]
                if is_palindrome(combined):
                    result.append([i, j])
    return result

# 7. Auto Complete System
def auto_complete_system(sentences, times):
    trie = Trie()
    counts = {}
    
    for sentence, time in zip(sentences, times):
        trie.insert(sentence)
        counts[sentence] = time
    
    def get_suggestions(prefix):
        node = trie.root
        for char in prefix:
            if char not in node.children:
                return []
            node = node.children[char]
        
        suggestions = []
        def dfs(node, path):
            if node.is_end:
                suggestions.append((counts.get(prefix + path, 0), prefix + path))
            for char, child in node.children.items():
                dfs(child, path + char)
        dfs(node, "")
        
        suggestions.sort(key=lambda x: (-x[0], x[1]))
        return [s[1] for s in suggestions[:3]]
    
    return get_suggestions

# 8. Stream Checker
def stream_checker(words):
    trie = Trie()
    for word in words:
        trie.insert(word[::-1])
    
    stream = []
    def query(letter):
        stream.append(letter)
        node = trie.root
        for i in range(len(stream) - 1, -1, -1):
            char = stream[i]
            if char not in node.children:
                return False
            node = node.children[char]
            if node.is_end:
                return True
        return False
    
    return query

# 9. Concatenated Words
def concatenated_words(words):
    trie = Trie()
    for word in words:
        trie.insert(word)
    
    def can_form(word, start, count):
        if start == len(word):
            return count > 1
        
        node = trie.root
        for i in range(start, len(word)):
            char = word[i]
            if char not in node.children:
                return False
            node = node.children[char]
            if node.is_end and can_form(word, i + 1, count + 1):
                return True
        return False
    
    return [word for word in words if can_form(word, 0, 0)]

# 10. Word Filter
def word_filter(words):
    """Find words that match both prefix and suffix"""
    trie = Trie()
    
    # Insert all words with special encoding
    for i, word in enumerate(words):
        for j in range(len(word) + 1):
            # Insert suffix + '#' + word
            encoded = word[j:] + '#' + word
            trie.insert(encoded)
    
    def search(prefix, suffix):
        # Search for suffix + '#' + prefix
        query = suffix + '#' + prefix
        node = trie.root
        for char in query:
            if char not in node.children:
                return -1
            node = node.children[char]
        
        # Find the largest index
        max_index = -1
        def dfs(node, path):
            nonlocal max_index
            if node.is_end:
                # Extract original word and find its index
                if '#' in query + path:
                    word = (query + path).split('#')[1]
                    if word in words:
                        max_index = max(max_index, words.index(word))
            for char, child in node.children.items():
                dfs(child, path + char)
        
        dfs(node, "")
        return max_index
    
    return search

# Run Examples
def run_examples():
    print("=== Trie Examples ===")
    
    # Test 1: Basic Trie
    trie = Trie()
    trie.insert("apple")
    print(f"1. Search 'apple': {trie.search('apple')}")
    print(f"   Search 'app': {trie.search('app')}")
    print(f"   Starts with 'app': {trie.starts_with('app')}")
    
    # Test 2: Word Search II
    board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]]
    words = ["oath","pea","eat","rain"]
    print(f"2. Find Words: {find_words(board, words)}")
    
    # Test 3: Longest Word
    words = ["w","wo","wor","worl","world"]
    print(f"3. Longest Word: {longest_word(words)}")
    
    # Test 4: Replace Words
    dictionary = ["cat","bat","rat"]
    sentence = "the cattle was rattled by the battery"
    print(f"4. Replace Words: {replace_words(dictionary, sentence)}")
    
    # Test 5: Word Squares
    words = ["area","lead","wall","lady","ball"]
    print(f"5. Word Squares: {word_squares(words)}")
    
    # Test 6: Map Sum
    pairs = [("apple", 3), ("app", 2)]
    sum_func = map_sum(pairs)
    print(f"6. Map Sum 'ap': {sum_func('ap')}")
    
    # Test 7: Palindrome Pairs
    words = ["code","edoc","da","d"]
    print(f"7. Palindrome Pairs: {palindrome_pairs(words)}")
    
    # Test 8: Auto Complete System
    sentences = ["i love you", "island", "iroman", "i love leetcode"]
    times = [5, 3, 2, 2]
    autocomplete = auto_complete_system(sentences, times)
    print(f"8. Auto Complete 'i': {autocomplete('i')}")
    
    # Test 9: Stream Checker
    words = ["cd","f","kl"]
    checker = stream_checker(words)
    print(f"9. Stream Checker 'c': {checker('c')}")
    print(f"   Stream Checker 'd': {checker('d')}")
    
    # Test 10: Concatenated Words
    words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
    print(f"10. Concatenated Words: {concatenated_words(words)}")

if __name__ == "__main__":
    run_examples()