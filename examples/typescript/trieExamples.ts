class TrieNode {
    children: (TrieNode | null)[] = new Array(26).fill(null);
    isEnd: boolean = false;
}

// 1. Implement Trie (Prefix Tree)
class Trie {
    root: TrieNode;
    
    constructor() {
        this.root = new TrieNode();
    }
    
    insert(word: string): void {
        let node = this.root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    search(word: string): boolean {
        const node = this.searchPrefix(word);
        return node !== null && node.isEnd;
    }
    
    startsWith(prefix: string): boolean {
        return this.searchPrefix(prefix) !== null;
    }
    
    private searchPrefix(prefix: string): TrieNode | null {
        let node = this.root;
        for (const c of prefix) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) return null;
            node = node.children[idx]!;
        }
        return node;
    }
}

// 2. Design Add and Search Words Data Structure
class WordDictionary {
    root: TrieNode;
    
    constructor() {
        this.root = new TrieNode();
    }
    
    addWord(word: string): void {
        let node = this.root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    search(word: string): boolean {
        return this.dfs(word, 0, this.root);
    }
    
    private dfs(word: string, idx: number, node: TrieNode): boolean {
        if (idx === word.length) return node.isEnd;
        const c = word[idx];
        if (c === '.') {
            return node.children.some(child => child && this.dfs(word, idx + 1, child));
        } else {
            const i = c.charCodeAt(0) - 97;
            return node.children[i] && this.dfs(word, idx + 1, node.children[i]!);
        }
    }
}

// 3. Word Search II
function findWords(board: string[][], words: string[]): string[] {
    const root = new TrieNode();
    for (const word of words) {
        let node = root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    const result = new Set<string>();
    const dfs = (i: number, j: number, node: TrieNode, word: string): void => {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] === '#') return;
        const c = board[i][j];
        const idx = c.charCodeAt(0) - 97;
        if (!node.children[idx]) return;
        
        const nextNode = node.children[idx]!;
        const newWord = word + c;
        if (nextNode.isEnd) result.add(newWord);
        
        board[i][j] = '#';
        dfs(i+1, j, nextNode, newWord);
        dfs(i-1, j, nextNode, newWord);
        dfs(i, j+1, nextNode, newWord);
        dfs(i, j-1, nextNode, newWord);
        board[i][j] = c;
    };
    
    for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[0].length; j++) {
            dfs(i, j, root, "");
        }
    }
    return Array.from(result);
}

// 4. Replace Words
function replaceWords(dictionary: string[], sentence: string): string {
    const root = new TrieNode();
    for (const word of dictionary) {
        let node = root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    const findRoot = (word: string): string => {
        let node = root;
        let prefix = "";
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) return word;
            node = node.children[idx]!;
            prefix += c;
            if (node.isEnd) return prefix;
        }
        return word;
    };
    
    return sentence.split(" ").map(findRoot).join(" ");
}

// 5. Map Sum Pairs
class MapSum {
    private root: TrieNode & { val: number };
    
    constructor() {
        this.root = Object.assign(new TrieNode(), { val: 0 });
    }
    
    insert(key: string, val: number): void {
        let node = this.root;
        for (const c of key) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) {
                node.children[idx] = Object.assign(new TrieNode(), { val: 0 });
            }
            node = node.children[idx] as any;
        }
        (node as any).val = val;
    }
    
    sum(prefix: string): number {
        let node = this.root;
        for (const c of prefix) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) return 0;
            node = node.children[idx] as any;
        }
        return this.dfs(node as any);
    }
    
    private dfs(node: TrieNode & { val: number }): number {
        return node.val + node.children.filter(child => child).reduce((sum, child) => sum + this.dfs(child as any), 0);
    }
}

// 6. Longest Word in Dictionary
function longestWord(words: string[]): string {
    const root = new TrieNode();
    for (const word of words) {
        let node = root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    const canBuild = (word: string): boolean => {
        let node = root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx] || !node.children[idx]!.isEnd) return false;
            node = node.children[idx]!;
        }
        return true;
    };
    
    return words.filter(canBuild).reduce((longest, word) => 
        word.length > longest.length || (word.length === longest.length && word < longest) ? word : longest, "");
}

// 7. Search Suggestions System
function suggestedProducts(products: string[], searchWord: string): string[][] {
    products.sort();
    const root = new TrieNode();
    for (const product of products) {
        let node = root;
        for (const c of product) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    const result: string[][] = [];
    let node: TrieNode | null = root;
    for (let i = 0; i < searchWord.length; i++) {
        const idx = searchWord[i].charCodeAt(0) - 97;
        if (node && node.children[idx]) {
            node = node.children[idx]!;
            const suggestions: string[] = [];
            const dfs = (n: TrieNode, prefix: string): void => {
                if (suggestions.length === 3) return;
                if (n.isEnd) suggestions.push(prefix);
                for (let j = 0; j < 26; j++) {
                    if (n.children[j]) {
                        dfs(n.children[j]!, prefix + String.fromCharCode(97 + j));
                    }
                }
            };
            dfs(node, searchWord.substring(0, i + 1));
            result.push(suggestions);
        } else {
            node = null;
            result.push([]);
        }
    }
    return result;
}

// 8. Palindrome Pairs
function palindromePairs(words: string[]): number[][] {
    const isPalindrome = (s: string, left: number, right: number): boolean => {
        while (left < right) {
            if (s[left++] !== s[right--]) return false;
        }
        return true;
    };
    
    const result: number[][] = [];
    for (let i = 0; i < words.length; i++) {
        for (let j = 0; j < words.length; j++) {
            if (i !== j) {
                const combined = words[i] + words[j];
                if (isPalindrome(combined, 0, combined.length - 1)) {
                    result.push([i, j]);
                }
            }
        }
    }
    return result;
}

// 9. Word Break II
function wordBreak(s: string, wordDict: string[]): string[] {
    const root = new TrieNode();
    for (const word of wordDict) {
        let node = root;
        for (const c of word) {
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) node.children[idx] = new TrieNode();
            node = node.children[idx]!;
        }
        node.isEnd = true;
    }
    
    const result: string[] = [];
    const dfs = (idx: number, path: string[]): void => {
        if (idx === s.length) {
            result.push(path.join(" "));
            return;
        }
        
        let node = root;
        for (let i = idx; i < s.length; i++) {
            const charIdx = s[i].charCodeAt(0) - 97;
            if (!node.children[charIdx]) break;
            node = node.children[charIdx]!;
            if (node.isEnd) {
                path.push(s.substring(idx, i + 1));
                dfs(i + 1, path);
                path.pop();
            }
        }
    };
    
    dfs(0, []);
    return result;
}

// 10. Stream of Characters
class StreamChecker {
    private root: TrieNode;
    private sb: string = "";
    
    constructor(words: string[]) {
        this.root = new TrieNode();
        for (const word of words) {
            let node = this.root;
            for (let i = word.length - 1; i >= 0; i--) {
                const c = word[i];
                const idx = c.charCodeAt(0) - 97;
                if (!node.children[idx]) node.children[idx] = new TrieNode();
                node = node.children[idx]!;
            }
            node.isEnd = true;
        }
    }
    
    query(letter: string): boolean {
        this.sb += letter;
        let node = this.root;
        for (let i = this.sb.length - 1; i >= 0; i--) {
            const c = this.sb[i];
            const idx = c.charCodeAt(0) - 97;
            if (!node.children[idx]) return false;
            node = node.children[idx]!;
            if (node.isEnd) return true;
        }
        return false;
    }
}

// Test cases
console.log("=== Trie Examples ===");

// Test 1: Implement Trie
const trie = new Trie();
trie.insert("apple");
console.log("1. Trie search 'apple':", trie.search("apple"));

// Test 2: Add and Search Words
const wd = new WordDictionary();
wd.addWord("bad");
wd.addWord("dad");
console.log("2. WordDict search 'b.d':", wd.search("b.d"));

// Test 3: Word Search II
const board = [['o','a','a','n'],['e','t','a','e'],['i','h','k','r'],['i','f','l','v']];
const words = ["oath","pea","eat","rain"];
console.log("3. Word Search II:", findWords(board, words));

// Test 4: Replace Words
const dict = ["cat", "bat", "rat"];
console.log("4. Replace Words:", replaceWords(dict, "the cattle was rattled by the battery"));

// Test 5: Map Sum Pairs
const mapSum = new MapSum();
mapSum.insert("apple", 3);
console.log("5. MapSum sum 'ap':", mapSum.sum("ap"));

// Test 6: Longest Word in Dictionary
console.log("6. Longest Word:", longestWord(["w","wo","wor","worl","world"]));

// Test 7: Search Suggestions System
const products = ["mobile","mouse","moneypot","monitor","mousepad"];
console.log("7. Suggestions 'mouse':", suggestedProducts(products, "mouse"));

// Test 8: Palindrome Pairs
console.log("8. Palindrome Pairs:", palindromePairs(["abcd","dcba","lls","s","sssll"]));

// Test 9: Word Break II
console.log("9. Word Break II:", wordBreak("catsanddog", ["cat","cats","and","sand","dog"]));

// Test 10: Stream of Characters
const sc = new StreamChecker(["cd","f","kl"]);
console.log("10. Stream Checker 'a':", sc.query('a'));