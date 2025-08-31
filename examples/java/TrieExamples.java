import java.util.*;

public class TrieExamples {
    
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }
    
    // 1. Implement Trie (Prefix Tree)
    static class Trie {
        TrieNode root;
        
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            return node != null && node.isEnd;
        }
        
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }
        
        private TrieNode searchPrefix(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) return null;
                node = node.children[idx];
            }
            return node;
        }
    }
    
    // 2. Design Add and Search Words Data Structure
    static class WordDictionary {
        TrieNode root;
        
        public WordDictionary() {
            root = new TrieNode();
        }
        
        public void addWord(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        public boolean search(String word) {
            return dfs(word, 0, root);
        }
        
        private boolean dfs(String word, int idx, TrieNode node) {
            if (idx == word.length()) return node.isEnd;
            char c = word.charAt(idx);
            if (c == '.') {
                for (TrieNode child : node.children) {
                    if (child != null && dfs(word, idx + 1, child)) return true;
                }
                return false;
            } else {
                int i = c - 'a';
                return node.children[i] != null && dfs(word, idx + 1, node.children[i]);
            }
        }
    }
    
    // 3. Word Search II
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        Set<String> result = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, "", result);
            }
        }
        return new ArrayList<>(result);
    }
    
    private void dfs(char[][] board, int i, int j, TrieNode node, String word, Set<String> result) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '#') return;
        char c = board[i][j];
        int idx = c - 'a';
        if (node.children[idx] == null) return;
        
        node = node.children[idx];
        word += c;
        if (node.isEnd) result.add(word);
        
        board[i][j] = '#';
        dfs(board, i+1, j, node, word, result);
        dfs(board, i-1, j, node, word, result);
        dfs(board, i, j+1, node, word, result);
        dfs(board, i, j-1, node, word, result);
        board[i][j] = c;
    }
    
    // 4. Replace Words
    public String replaceWords(List<String> dictionary, String sentence) {
        TrieNode root = new TrieNode();
        for (String word : dictionary) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        StringBuilder result = new StringBuilder();
        for (String word : sentence.split(" ")) {
            if (result.length() > 0) result.append(" ");
            result.append(findRoot(root, word));
        }
        return result.toString();
    }
    
    private String findRoot(TrieNode root, String word) {
        TrieNode node = root;
        StringBuilder prefix = new StringBuilder();
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return word;
            node = node.children[idx];
            prefix.append(c);
            if (node.isEnd) return prefix.toString();
        }
        return word;
    }
    
    // 5. Map Sum Pairs
    static class MapSum {
        TrieNode root;
        
        static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            int val = 0;
        }
        
        public MapSum() {
            root = new TrieNode();
        }
        
        public void insert(String key, int val) {
            TrieNode node = root;
            for (char c : key.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.val = val;
        }
        
        public int sum(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) return 0;
                node = node.children[idx];
            }
            return dfs(node);
        }
        
        private int dfs(TrieNode node) {
            int sum = node.val;
            for (TrieNode child : node.children) {
                if (child != null) sum += dfs(child);
            }
            return sum;
        }
    }
    
    // 6. Longest Word in Dictionary
    public String longestWord(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        String result = "";
        for (String word : words) {
            if (canBuild(root, word) && (word.length() > result.length() || 
                (word.length() == result.length() && word.compareTo(result) < 0))) {
                result = word;
            }
        }
        return result;
    }
    
    private boolean canBuild(TrieNode root, String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null || !node.children[idx].isEnd) return false;
            node = node.children[idx];
        }
        return true;
    }
    
    // 7. Search Suggestions System
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        TrieNode root = new TrieNode();
        for (String product : products) {
            TrieNode node = root;
            for (char c : product.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        List<List<String>> result = new ArrayList<>();
        TrieNode node = root;
        for (char c : searchWord.toCharArray()) {
            int idx = c - 'a';
            if (node != null && node.children[idx] != null) {
                node = node.children[idx];
                List<String> suggestions = new ArrayList<>();
                dfsProducts(node, searchWord.substring(0, result.size() + 1), suggestions);
                result.add(suggestions.subList(0, Math.min(3, suggestions.size())));
            } else {
                node = null;
                result.add(new ArrayList<>());
            }
        }
        return result;
    }
    
    private void dfsProducts(TrieNode node, String prefix, List<String> suggestions) {
        if (suggestions.size() == 3) return;
        if (node.isEnd) suggestions.add(prefix);
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                dfsProducts(node.children[i], prefix + (char)('a' + i), suggestions);
            }
        }
    }
    
    // 8. Palindrome Pairs
    public List<List<Integer>> palindromePairs(String[] words) {
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            String word = new StringBuilder(words[i]).reverse().toString();
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            TrieNode node = root;
            for (int j = 0; j <= word.length(); j++) {
                if (node.isEnd && isPalindrome(word, j, word.length() - 1)) {
                    result.add(Arrays.asList(i, Arrays.asList(words).indexOf(new StringBuilder(word.substring(0, j)).reverse().toString())));
                }
                if (j < word.length()) {
                    int idx = word.charAt(j) - 'a';
                    if (node.children[idx] == null) break;
                    node = node.children[idx];
                }
            }
        }
        return result;
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
    
    // 9. Word Break II
    public List<String> wordBreak(String s, List<String> wordDict) {
        TrieNode root = new TrieNode();
        for (String word : wordDict) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        
        List<String> result = new ArrayList<>();
        dfsWordBreak(s, 0, root, root, "", result);
        return result;
    }
    
    private void dfsWordBreak(String s, int idx, TrieNode root, TrieNode node, String path, List<String> result) {
        if (idx == s.length()) {
            if (node == root) result.add(path.trim());
            return;
        }
        
        int charIdx = s.charAt(idx) - 'a';
        if (node.children[charIdx] != null) {
            TrieNode next = node.children[charIdx];
            if (next.isEnd) {
                dfsWordBreak(s, idx + 1, root, root, path + s.substring(idx - (path.split(" ").length > 0 ? path.split(" ")[path.split(" ").length - 1].length() : 0), idx + 1) + " ", result);
            }
            dfsWordBreak(s, idx + 1, root, next, path, result);
        }
    }
    
    // 10. Stream of Characters
    static class StreamChecker {
        TrieNode root;
        StringBuilder sb;
        
        public StreamChecker(String[] words) {
            root = new TrieNode();
            sb = new StringBuilder();
            for (String word : words) {
                TrieNode node = root;
                for (int i = word.length() - 1; i >= 0; i--) {
                    char c = word.charAt(i);
                    int idx = c - 'a';
                    if (node.children[idx] == null) node.children[idx] = new TrieNode();
                    node = node.children[idx];
                }
                node.isEnd = true;
            }
        }
        
        public boolean query(char letter) {
            sb.append(letter);
            TrieNode node = root;
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                int idx = c - 'a';
                if (node.children[idx] == null) return false;
                node = node.children[idx];
                if (node.isEnd) return true;
            }
            return false;
        }
    }
    
    public static void main(String[] args) {
        TrieExamples examples = new TrieExamples();
        
        System.out.println("=== Trie Examples ===");
        
        // Test 1: Implement Trie
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println("1. Trie search 'apple': " + trie.search("apple"));
        
        // Test 2: Add and Search Words
        WordDictionary wd = new WordDictionary();
        wd.addWord("bad");
        wd.addWord("dad");
        System.out.println("2. WordDict search 'b.d': " + wd.search("b.d"));
        
        // Test 3: Word Search II
        char[][] board = {{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String[] words = {"oath","pea","eat","rain"};
        System.out.println("3. Word Search II: " + examples.findWords(board, words));
        
        // Test 4: Replace Words
        List<String> dict = Arrays.asList("cat", "bat", "rat");
        System.out.println("4. Replace Words: " + examples.replaceWords(dict, "the cattle was rattled by the battery"));
        
        // Test 5: Map Sum Pairs
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        System.out.println("5. MapSum sum 'ap': " + mapSum.sum("ap"));
        
        // Test 6: Longest Word in Dictionary
        System.out.println("6. Longest Word: " + examples.longestWord(new String[]{"w","wo","wor","worl","world"}));
        
        // Test 7: Search Suggestions System
        String[] products = {"mobile","mouse","moneypot","monitor","mousepad"};
        System.out.println("7. Suggestions 'mouse': " + examples.suggestedProducts(products, "mouse"));
        
        // Test 8: Palindrome Pairs
        System.out.println("8. Palindrome Pairs: " + examples.palindromePairs(new String[]{"abcd","dcba","lls","s","sssll"}));
        
        // Test 9: Word Break II
        System.out.println("9. Word Break II: " + examples.wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
        
        // Test 10: Stream of Characters
        StreamChecker sc = new StreamChecker(new String[]{"cd","f","kl"});
        System.out.println("10. Stream Checker 'a': " + sc.query('a'));
    }
}