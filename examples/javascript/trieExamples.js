// Trie Node class
class TrieNode {
    constructor() {
        this.children = {};
        this.isEndOfWord = false;
    }
}

// 1. Implement Trie (Prefix Tree)
class Trie {
    constructor() {
        this.root = new TrieNode();
    }
    
    insert(word) {
        let node = this.root;
        for (const char of word) {
            if (!node.children[char]) {
                node.children[char] = new TrieNode();
            }
            node = node.children[char];
        }
        node.isEndOfWord = true;
    }
    
    search(word) {
        let node = this.root;
        for (const char of word) {
            if (!node.children[char]) {
                return false;
            }
            node = node.children[char];
        }
        return node.isEndOfWord;
    }
    
    startsWith(prefix) {
        let node = this.root;
        for (const char of prefix) {
            if (!node.children[char]) {
                return false;
            }
            node = node.children[char];
        }
        return true;
    }
}

// 2. Word Search II
function findWords(board, words) {
    const trie = new Trie();
    for (const word of words) {
        trie.insert(word);
    }
    
    const result = new Set();
    const m = board.length, n = board[0].length;
    
    function dfs(i, j, node, path) {
        if (i < 0 || i >= m || j < 0 || j >= n || !node.children[board[i][j]]) {
            return;
        }
        
        const char = board[i][j];
        const nextNode = node.children[char];
        const newPath = path + char;
        
        if (nextNode.isEndOfWord) {
            result.add(newPath);
        }
        
        board[i][j] = '#';
        dfs(i + 1, j, nextNode, newPath);
        dfs(i - 1, j, nextNode, newPath);
        dfs(i, j + 1, nextNode, newPath);
        dfs(i, j - 1, nextNode, newPath);
        board[i][j] = char;
    }
    
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            dfs(i, j, trie.root, '');
        }
    }
    
    return Array.from(result);
}

// 3. Replace Words
function replaceWords(dictionary, sentence) {
    const trie = new Trie();
    for (const root of dictionary) {
        trie.insert(root);
    }
    
    function findRoot(word) {
        let node = trie.root;
        let prefix = '';
        
        for (const char of word) {
            if (!node.children[char]) {
                return word;
            }
            node = node.children[char];
            prefix += char;
            if (node.isEndOfWord) {
                return prefix;
            }
        }
        return word;
    }
    
    return sentence.split(' ').map(findRoot).join(' ');
}

// 4. Word Break
function wordBreak(s, wordDict) {
    const trie = new Trie();
    for (const word of wordDict) {
        trie.insert(word);
    }
    
    const dp = new Array(s.length + 1).fill(false);
    dp[0] = true;
    
    for (let i = 1; i <= s.length; i++) {
        let node = trie.root;
        for (let j = i - 1; j >= 0; j--) {
            const char = s[j];
            if (!node.children[char]) {
                break;
            }
            node = node.children[char];
            if (node.isEndOfWord && dp[j]) {
                dp[i] = true;
                break;
            }
        }
    }
    
    return dp[s.length];
}

// 5. Design Add and Search Words Data Structure
class WordDictionary {
    constructor() {
        this.root = new TrieNode();
    }
    
    addWord(word) {
        let node = this.root;
        for (const char of word) {
            if (!node.children[char]) {
                node.children[char] = new TrieNode();
            }
            node = node.children[char];
        }
        node.isEndOfWord = true;
    }
    
    search(word) {
        function dfs(node, i) {
            if (i === word.length) {
                return node.isEndOfWord;
            }
            
            const char = word[i];
            if (char === '.') {
                for (const child of Object.values(node.children)) {
                    if (dfs(child, i + 1)) {
                        return true;
                    }
                }
                return false;
            } else {
                if (!node.children[char]) {
                    return false;
                }
                return dfs(node.children[char], i + 1);
            }
        }
        
        return dfs(this.root, 0);
    }
}

// 6. Longest Word in Dictionary
function longestWord(words) {
    const trie = new Trie();
    for (const word of words) {
        trie.insert(word);
    }
    
    let longest = '';
    
    function dfs(node, path) {
        if (path.length > longest.length || 
            (path.length === longest.length && path < longest)) {
            longest = path;
        }
        
        for (const [char, child] of Object.entries(node.children)) {
            if (child.isEndOfWord) {
                dfs(child, path + char);
            }
        }
    }
    
    dfs(trie.root, '');
    return longest;
}

// 7. Maximum XOR of Two Numbers in an Array
function findMaximumXOR(nums) {
    class TrieBit {
        constructor() {
            this.children = {};
        }
    }
    
    const root = new TrieBit();
    
    // Insert all numbers into trie
    for (const num of nums) {
        let node = root;
        for (let i = 31; i >= 0; i--) {
            const bit = (num >> i) & 1;
            if (!node.children[bit]) {
                node.children[bit] = new TrieBit();
            }
            node = node.children[bit];
        }
    }
    
    let maxXor = 0;
    
    // Find maximum XOR for each number
    for (const num of nums) {
        let node = root;
        let currentXor = 0;
        
        for (let i = 31; i >= 0; i--) {
            const bit = (num >> i) & 1;
            const toggledBit = 1 - bit;
            
            if (node.children[toggledBit]) {
                currentXor |= (1 << i);
                node = node.children[toggledBit];
            } else {
                node = node.children[bit];
            }
        }
        
        maxXor = Math.max(maxXor, currentXor);
    }
    
    return maxXor;
}

// 8. Palindrome Pairs
function palindromePairs(words) {
    const trie = new Trie();
    const result = [];
    
    // Helper function to check if string is palindrome
    function isPalindrome(s, start, end) {
        while (start < end) {
            if (s[start] !== s[end]) return false;
            start++;
            end--;
        }
        return true;
    }
    
    // Insert all words (reversed) into trie with their indices
    for (let i = 0; i < words.length; i++) {
        const word = words[i].split('').reverse().join('');
        let node = trie.root;
        for (const char of word) {
            if (!node.children[char]) {
                node.children[char] = new TrieNode();
            }
            node = node.children[char];
        }
        node.isEndOfWord = true;
        node.wordIndex = i;
    }
    
    // For each word, search in trie
    for (let i = 0; i < words.length; i++) {
        const word = words[i];
        let node = trie.root;
        
        for (let j = 0; j <= word.length; j++) {
            if (node.isEndOfWord && node.wordIndex !== i) {
                if (j === word.length || isPalindrome(word, j, word.length - 1)) {
                    result.push([i, node.wordIndex]);
                }
            }
            
            if (j < word.length) {
                if (!node.children[word[j]]) break;
                node = node.children[word[j]];
            }
        }
    }
    
    return result;
}

// 9. Stream of Characters
class StreamChecker {
    constructor(words) {
        this.trie = new Trie();
        this.stream = [];
        
        // Insert all words reversed into trie
        for (const word of words) {
            this.trie.insert(word.split('').reverse().join(''));
        }
    }
    
    query(letter) {
        this.stream.push(letter);
        let node = this.trie.root;
        
        // Check from the end of stream backwards
        for (let i = this.stream.length - 1; i >= 0; i--) {
            const char = this.stream[i];
            if (!node.children[char]) {
                return false;
            }
            node = node.children[char];
            if (node.isEndOfWord) {
                return true;
            }
        }
        
        return false;
    }
}

// 10. Auto-complete System
class AutocompleteSystem {
    constructor(sentences, times) {
        this.trie = new Trie();
        this.currentInput = '';
        
        // Build trie with sentences and their frequencies
        for (let i = 0; i < sentences.length; i++) {
            this.insertSentence(sentences[i], times[i]);
        }
    }
    
    insertSentence(sentence, count) {
        let node = this.trie.root;
        for (const char of sentence) {
            if (!node.children[char]) {
                node.children[char] = new TrieNode();
            }
            node = node.children[char];
        }
        node.isEndOfWord = true;
        node.count = (node.count || 0) + count;
    }
    
    input(c) {
        if (c === '#') {
            this.insertSentence(this.currentInput, 1);
            this.currentInput = '';
            return [];
        }
        
        this.currentInput += c;
        return this.getSuggestions();
    }
    
    getSuggestions() {
        let node = this.trie.root;
        
        // Navigate to current prefix
        for (const char of this.currentInput) {
            if (!node.children[char]) {
                return [];
            }
            node = node.children[char];
        }
        
        // Collect all sentences with this prefix
        const suggestions = [];
        
        function dfs(node, path) {
            if (node.isEndOfWord) {
                suggestions.push([path, node.count]);
            }
            
            for (const [char, child] of Object.entries(node.children)) {
                dfs(child, path + char);
            }
        }
        
        dfs(node, this.currentInput);
        
        // Sort by count (desc) then lexicographically
        suggestions.sort((a, b) => {
            if (a[1] !== b[1]) return b[1] - a[1];
            return a[0].localeCompare(b[0]);
        });
        
        return suggestions.slice(0, 3).map(item => item[0]);
    }
}

// Test cases
console.log("=== Trie Examples ===");

const trie = new Trie();
trie.insert("apple");
console.log("Search 'apple':", trie.search("apple"));
console.log("Search 'app':", trie.search("app"));
console.log("Starts with 'app':", trie.startsWith("app"));

const board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]];
const words = ["oath","pea","eat","rain"];
console.log("Find Words:", findWords(board, words));

console.log("Replace Words:", replaceWords(["cat","bat","rat"], "the cattle was rattled by the battery"));

console.log("Word Break('leetcode', ['leet','code']):", wordBreak("leetcode", ["leet","code"]));

const wordDict = new WordDictionary();
wordDict.addWord("bad");
wordDict.addWord("dad");
wordDict.addWord("mad");
console.log("Search 'pad':", wordDict.search("pad"));
console.log("Search '.ad':", wordDict.search(".ad"));

console.log("Longest Word:", longestWord(["w","wo","wor","worl","world"]));

console.log("Maximum XOR:", findMaximumXOR([3,10,5,25,2,8]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        Trie,
        TrieNode,
        findWords,
        replaceWords,
        wordBreak,
        WordDictionary,
        longestWord,
        findMaximumXOR,
        palindromePairs,
        StreamChecker,
        AutocompleteSystem
    };
}