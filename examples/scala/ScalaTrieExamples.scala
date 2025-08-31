import scala.collection.mutable

object ScalaTrieExamples {
  
  // 1. Implement Trie (Prefix Tree)
  class Trie {
    val root = new TrieNode()
    
    class TrieNode {
      val children = Array.fill[TrieNode](26)(null)
      var isEnd = false
    }
    
    def insert(word: String): Unit = {
      var curr = root
      for (c <- word) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          curr.children(index) = new TrieNode()
        }
        curr = curr.children(index)
      }
      curr.isEnd = true
    }
    
    def search(word: String): Boolean = {
      var curr = root
      for (c <- word) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          return false
        }
        curr = curr.children(index)
      }
      curr.isEnd
    }
    
    def startsWith(prefix: String): Boolean = {
      var curr = root
      for (c <- prefix) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          return false
        }
        curr = curr.children(index)
      }
      true
    }
  }
  
  // 2. Add and Search Word - Data structure design
  class WordDictionary {
    private val root = new TrieNode()
    
    class TrieNode {
      val children = Array.fill[TrieNode](26)(null)
      var isEnd = false
    }
    
    def addWord(word: String): Unit = {
      var curr = root
      for (c <- word) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          curr.children(index) = new TrieNode()
        }
        curr = curr.children(index)
      }
      curr.isEnd = true
    }
    
    def search(word: String): Boolean = {
      def dfs(node: TrieNode, i: Int): Boolean = {
        if (i == word.length) return node.isEnd
        
        val c = word(i)
        if (c == '.') {
          for (child <- node.children if child != null) {
            if (dfs(child, i + 1)) return true
          }
          false
        } else {
          val index = c - 'a'
          if (node.children(index) == null) false
          else dfs(node.children(index), i + 1)
        }
      }
      
      dfs(root, 0)
    }
  }
  
  // 3. Word Search II
  def findWords(board: Array[Array[Char]], words: Array[String]): List[String] = {
    val trie = new Trie()
    for (word <- words) {
      trie.insert(word)
    }
    
    val result = mutable.Set[String]()
    val m = board.length
    val n = board(0).length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    def dfs(i: Int, j: Int, node: trie.TrieNode, path: String): Unit = {
      if (i < 0 || i >= m || j < 0 || j >= n || board(i)(j) == '#') return
      
      val c = board(i)(j)
      val index = c - 'a'
      if (node.children(index) == null) return
      
      val nextNode = node.children(index)
      val newPath = path + c
      
      if (nextNode.isEnd) {
        result += newPath
      }
      
      board(i)(j) = '#'
      for ((di, dj) <- directions) {
        dfs(i + di, j + dj, nextNode, newPath)
      }
      board(i)(j) = c
    }
    
    for (i <- board.indices; j <- board(0).indices) {
      dfs(i, j, trie.root, "")
    }
    
    result.toList
  }
  
  // 4. Replace Words
  def replaceWords(dictionary: List[String], sentence: String): String = {
    val trie = new Trie()
    for (root <- dictionary) {
      trie.insert(root)
    }
    
    def findRoot(word: String): String = {
      var curr = trie.root
      for (i <- word.indices) {
        val c = word(i)
        val index = c - 'a'
        if (curr.children(index) == null) {
          return word
        }
        curr = curr.children(index)
        if (curr.isEnd) {
          return word.substring(0, i + 1)
        }
      }
      word
    }
    
    sentence.split(" ").map(findRoot).mkString(" ")
  }
  
  // 5. Longest Word in Dictionary
  def longestWord(words: Array[String]): String = {
    val trie = new Trie()
    for (word <- words) {
      trie.insert(word)
    }
    
    def canBuild(word: String): Boolean = {
      var curr = trie.root
      for (c <- word) {
        val index = c - 'a'
        curr = curr.children(index)
        if (!curr.isEnd) return false
      }
      true
    }
    
    var result = ""
    for (word <- words) {
      if (canBuild(word)) {
        if (word.length > result.length || (word.length == result.length && word < result)) {
          result = word
        }
      }
    }
    result
  }
  
  // 6. Map Sum Pairs
  class MapSum {
    private val root = new TrieNode()
    
    class TrieNode {
      val children = Array.fill[TrieNode](26)(null)
      var value = 0
    }
    
    def insert(key: String, value: Int): Unit = {
      var curr = root
      for (c <- key) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          curr.children(index) = new TrieNode()
        }
        curr = curr.children(index)
      }
      curr.value = value
    }
    
    def sum(prefix: String): Int = {
      var curr = root
      for (c <- prefix) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          return 0
        }
        curr = curr.children(index)
      }
      
      def dfs(node: TrieNode): Int = {
        var sum = node.value
        for (child <- node.children if child != null) {
          sum += dfs(child)
        }
        sum
      }
      
      dfs(curr)
    }
  }
  
  // 7. Maximum XOR of Two Numbers in an Array
  def findMaximumXOR(nums: Array[Int]): Int = {
    class TrieNode {
      val children = Array.fill[TrieNode](2)(null)
    }
    
    val root = new TrieNode()
    
    def insert(num: Int): Unit = {
      var curr = root
      for (i <- 31 to 0 by -1) {
        val bit = (num >> i) & 1
        if (curr.children(bit) == null) {
          curr.children(bit) = new TrieNode()
        }
        curr = curr.children(bit)
      }
    }
    
    def findMax(num: Int): Int = {
      var curr = root
      var maxXor = 0
      for (i <- 31 to 0 by -1) {
        val bit = (num >> i) & 1
        val toggledBit = 1 - bit
        if (curr.children(toggledBit) != null) {
          maxXor |= (1 << i)
          curr = curr.children(toggledBit)
        } else {
          curr = curr.children(bit)
        }
      }
      maxXor
    }
    
    for (num <- nums) {
      insert(num)
    }
    
    var result = 0
    for (num <- nums) {
      result = math.max(result, findMax(num))
    }
    result
  }
  
  // 8. Word Squares
  def wordSquares(words: Array[String]): List[List[String]] = {
    val trie = new Trie()
    for (word <- words) {
      trie.insert(word)
    }
    
    val n = words(0).length
    val result = mutable.ListBuffer[List[String]]()
    
    def getWordsWithPrefix(prefix: String): List[String] = {
      var curr = trie.root
      for (c <- prefix) {
        val index = c - 'a'
        if (curr.children(index) == null) {
          return List()
        }
        curr = curr.children(index)
      }
      
      val words = mutable.ListBuffer[String]()
      def dfs(node: trie.TrieNode, path: String): Unit = {
        if (node.isEnd) {
          words += path
        }
        for (i <- node.children.indices if node.children(i) != null) {
          dfs(node.children(i), path + ('a' + i).toChar)
        }
      }
      
      dfs(curr, prefix)
      words.toList
    }
    
    def backtrack(square: mutable.ListBuffer[String]): Unit = {
      if (square.length == n) {
        result += square.toList
        return
      }
      
      val row = square.length
      val prefix = (0 until row).map(col => square(col)(row)).mkString
      
      for (word <- getWordsWithPrefix(prefix)) {
        square += word
        backtrack(square)
        square.remove(square.length - 1)
      }
    }
    
    for (word <- words) {
      val square = mutable.ListBuffer[String](word)
      backtrack(square)
    }
    
    result.toList
  }
  
  // 9. Palindrome Pairs
  def palindromePairs(words: Array[String]): List[List[Int]] = {
    val trie = new Trie()
    val wordToIndex = mutable.Map[String, Int]()
    
    for (i <- words.indices) {
      trie.insert(words(i).reverse)
      wordToIndex(words(i)) = i
    }
    
    def isPalindrome(s: String, start: Int, end: Int): Boolean = {
      var left = start
      var right = end
      while (left < right) {
        if (s(left) != s(right)) return false
        left += 1
        right -= 1
      }
      true
    }
    
    val result = mutable.ListBuffer[List[Int]]()
    
    for (i <- words.indices) {
      val word = words(i)
      
      // Case 1: word + reverse(word) forms palindrome
      if (wordToIndex.contains(word.reverse) && wordToIndex(word.reverse) != i) {
        result += List(i, wordToIndex(word.reverse))
      }
      
      // Case 2: word + suffix forms palindrome
      for (j <- 1 until word.length) {
        if (isPalindrome(word, 0, j - 1)) {
          val suffix = word.substring(j).reverse
          if (wordToIndex.contains(suffix)) {
            result += List(wordToIndex(suffix), i)
          }
        }
        
        if (isPalindrome(word, j, word.length - 1)) {
          val prefix = word.substring(0, j).reverse
          if (wordToIndex.contains(prefix)) {
            result += List(i, wordToIndex(prefix))
          }
        }
      }
    }
    
    result.toList
  }
  
  // 10. Stream of Characters
  class StreamChecker(words: Array[String]) {
    private val trie = new Trie()
    private val stream = mutable.ListBuffer[Char]()
    
    for (word <- words) {
      trie.insert(word.reverse)
    }
    
    def query(letter: Char): Boolean = {
      stream += letter
      
      var curr = trie.root
      for (i <- stream.length - 1 to 0 by -1) {
        val c = stream(i)
        val index = c - 'a'
        if (curr.children(index) == null) {
          return false
        }
        curr = curr.children(index)
        if (curr.isEnd) {
          return true
        }
      }
      false
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Trie Examples ===")
    
    // Test 1: Implement Trie
    val trie = new Trie()
    trie.insert("apple")
    println("Test 1 - Trie search('apple'): " + trie.search("apple"))
    
    // Test 2: Add and Search Word
    val wordDict = new WordDictionary()
    wordDict.addWord("bad")
    wordDict.addWord("dad")
    wordDict.addWord("mad")
    println("Test 2 - WordDict search('.ad'): " + wordDict.search(".ad"))
    
    // Test 3: Word Search II
    val board = Array(Array('o','a','a','n'),Array('e','t','a','e'),Array('i','h','k','r'),Array('i','f','l','v'))
    val words = Array("oath","pea","eat","rain")
    println("Test 3 - Find Words: " + findWords(board, words))
    
    // Test 4: Replace Words
    println("Test 4 - Replace Words: " + replaceWords(List("cat","bat","rat"), "the cattle was rattled by the battery"))
    
    // Test 5: Longest Word in Dictionary
    println("Test 5 - Longest Word: " + longestWord(Array("w","wo","wor","worl","world")))
    
    // Test 6: Map Sum Pairs
    val mapSum = new MapSum()
    mapSum.insert("apple", 3)
    println("Test 6 - Map Sum('ap'): " + mapSum.sum("ap"))
    
    // Test 7: Maximum XOR of Two Numbers
    println("Test 7 - Find Maximum XOR: " + findMaximumXOR(Array(3, 10, 5, 25, 2, 8)))
    
    // Test 8: Word Squares
    println("Test 8 - Word Squares: " + wordSquares(Array("area","lead","wall","lady","ball")).length + " squares")
    
    // Test 9: Palindrome Pairs
    println("Test 9 - Palindrome Pairs: " + palindromePairs(Array("abcd","dcba","lls","s","sssll")).length + " pairs")
    
    // Test 10: Stream of Characters
    val streamChecker = new StreamChecker(Array("cd","f","kl"))
    println("Test 10 - Stream Checker('c'): " + streamChecker.query('c'))
  }
}