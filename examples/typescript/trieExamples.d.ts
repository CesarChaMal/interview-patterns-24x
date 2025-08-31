declare class TrieNode {
    children: (TrieNode | null)[];
    isEnd: boolean;
}
declare class Trie {
    root: TrieNode;
    constructor();
    insert(word: string): void;
    search(word: string): boolean;
    startsWith(prefix: string): boolean;
    private searchPrefix;
}
declare class WordDictionary {
    root: TrieNode;
    constructor();
    addWord(word: string): void;
    search(word: string): boolean;
    private dfs;
}
declare function findWords(board: string[][], words: string[]): string[];
declare function replaceWords(dictionary: string[], sentence: string): string;
declare class MapSum {
    private root;
    constructor();
    insert(key: string, val: number): void;
    sum(prefix: string): number;
    private dfs;
}
declare function longestWord(words: string[]): string;
declare function suggestedProducts(products: string[], searchWord: string): string[][];
declare function palindromePairs(words: string[]): number[][];
declare class StreamChecker {
    private root;
    private sb;
    constructor(words: string[]);
    query(letter: string): boolean;
}
declare const trie: Trie;
declare const wd: WordDictionary;
declare const board: string[][];
declare const words: string[];
declare const dict: string[];
declare const mapSum: MapSum;
declare const products: string[];
declare const sc: StreamChecker;
//# sourceMappingURL=trieExamples.d.ts.map