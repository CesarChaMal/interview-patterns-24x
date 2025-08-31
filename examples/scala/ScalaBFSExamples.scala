import scala.collection.mutable

object ScalaBFSExamples {
  
  // final class TreeNode to avoid JDK 24 emitter issues
  final class TreeNode(var value: Int, var left: TreeNode, var right: TreeNode) {
    def this(value: Int) = this(value, null, null)
  }
  object TreeNode {
    def apply(value: Int): TreeNode = new TreeNode(value)
    def apply(value: Int, left: TreeNode): TreeNode = new TreeNode(value, left, null)
    def apply(value: Int, left: TreeNode, right: TreeNode): TreeNode = new TreeNode(value, left, right)
  }

  // 1. Binary Tree Level Order Traversal
  def levelOrder(root: TreeNode): List[List[Int]] = {
    if (root == null) return List()
    
    val result = mutable.ListBuffer[List[Int]]()
    val queue = mutable.Queue[TreeNode](root)
    
    while (queue.nonEmpty) {
      val levelSize = queue.size
      val level = mutable.ListBuffer[Int]()
      for (_ <- 0 until levelSize) {
        val node = queue.dequeue()
        level += node.value
        if (node.left != null) queue.enqueue(node.left)
        if (node.right != null) queue.enqueue(node.right)
      }
      result += level.toList
    }
    result.toList
  }
  
  // 2. Number of Islands
  def numIslands(grid: Array[Array[Char]]): Int = {
    if (grid.isEmpty) return 0
    
    var count = 0
    for (i <- grid.indices; j <- grid(0).indices) {
      if (grid(i)(j) == '1') {
        bfsIslands(grid, i, j)
        count += 1
      }
    }
    count
  }
  
  private def bfsIslands(grid: Array[Array[Char]], i: Int, j: Int): Unit = {
    val queue = mutable.Queue[(Int, Int)]((i, j))
    grid(i)(j) = '0'
    
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    while (queue.nonEmpty) {
      val (x, y) = queue.dequeue()
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid(0).length && grid(nx)(ny) == '1') {
          grid(nx)(ny) = '0'
          queue.enqueue((nx, ny))
        }
      }
    }
  }
  
  // 3. Rotting Oranges
  def orangesRotting(grid: Array[Array[Int]]): Int = {
    val queue = mutable.Queue[(Int, Int)]()
    var fresh = 0
    
    for (i <- grid.indices; j <- grid(0).indices) {
      if (grid(i)(j) == 2) queue.enqueue((i, j))
      else if (grid(i)(j) == 1) fresh += 1
    }
    
    if (fresh == 0) return 0
    
    var minutes = 0
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    while (queue.nonEmpty) {
      val size = queue.size
      for (_ <- 0 until size) {
        val (x, y) = queue.dequeue()
        for ((dx, dy) <- directions) {
          val nx = x + dx
          val ny = y + dy
          if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid(0).length && grid(nx)(ny) == 1) {
            grid(nx)(ny) = 2
            queue.enqueue((nx, ny))
            fresh -= 1
          }
        }
      }
      minutes += 1
    }
    
    if (fresh == 0) minutes - 1 else -1
  }
  
  // 4. Word Ladder
  def ladderLength(beginWord: String, endWord: String, wordList: List[String]): Int = {
    val wordSet = mutable.Set(wordList: _*)
    if (!wordSet.contains(endWord)) return 0
    
    val queue = mutable.Queue[String](beginWord)
    var level = 1
    
    while (queue.nonEmpty) {
      val size = queue.size
      for (_ <- 0 until size) {
        val word = queue.dequeue()
        if (word == endWord) return level
        
        for (i <- word.indices; c <- 'a' to 'z') {
          val newWord = word.updated(i, c)
          if (wordSet.contains(newWord)) {
            wordSet.remove(newWord)
            queue.enqueue(newWord)
          }
        }
      }
      level += 1
    }
    0
  }
  
  // 5. Minimum Depth of Binary Tree
  def minDepth(root: TreeNode): Int = {
    if (root == null) return 0
    
    val queue = mutable.Queue[TreeNode](root)
    var depth = 1
    
    while (queue.nonEmpty) {
      val size = queue.size
      for (_ <- 0 until size) {
        val node = queue.dequeue()
        if (node.left == null && node.right == null) return depth
        if (node.left != null) queue.enqueue(node.left)
        if (node.right != null) queue.enqueue(node.right)
      }
      depth += 1
    }
    depth
  }
  
  // 6. Open the Lock
  def openLock(deadends: Array[String], target: String): Int = {
    val dead = deadends.toSet
    if (dead.contains("0000")) return -1
    
    val queue = mutable.Queue[String]("0000")
    val visited = mutable.Set("0000")
    var steps = 0
    
    while (queue.nonEmpty) {
      val size = queue.size
      for (_ <- 0 until size) {
        val curr = queue.dequeue()
        if (curr == target) return steps
        
        for (i <- 0 until 4) {
          val digit = curr(i).asDigit
          for (d <- Array(-1, 1)) {
            val newDigit = (digit + d + 10) % 10
            val newCombo = curr.updated(i, (newDigit + '0').toChar)
            if (!dead.contains(newCombo) && !visited.contains(newCombo)) {
              visited.add(newCombo)
              queue.enqueue(newCombo)
            }
          }
        }
      }
      steps += 1
    }
    -1
  }
  
  // 7. Binary Tree Right Side View
  def rightSideView(root: TreeNode): List[Int] = {
    if (root == null) return List()
    
    val result = mutable.ListBuffer[Int]()
    val queue = mutable.Queue[TreeNode](root)
    
    while (queue.nonEmpty) {
      val levelSize = queue.size
      for (i <- 0 until levelSize) {
        val node = queue.dequeue()
        if (i == levelSize - 1) result += node.value
        if (node.left != null) queue.enqueue(node.left)
        if (node.right != null) queue.enqueue(node.right)
      }
    }
    result.toList
  }
  
  // 8. Shortest Path in Binary Matrix
  def shortestPathBinaryMatrix(grid: Array[Array[Int]]): Int = {
    if (grid(0)(0) == 1) return -1
    val n = grid.length
    if (n == 1) return 1
    
    val queue = mutable.Queue[(Int, Int, Int)]((0, 0, 1))
    grid(0)(0) = 1
    
    val directions = Array((-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1))
    
    while (queue.nonEmpty) {
      val (x, y, dist) = queue.dequeue()
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        if (nx == n - 1 && ny == n - 1) return dist + 1
        if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid(nx)(ny) == 0) {
          grid(nx)(ny) = 1
          queue.enqueue((nx, ny, dist + 1))
        }
      }
    }
    -1
  }
  
  // 9. Perfect Squares
  def numSquares(n: Int): Int = {
    val queue = mutable.Queue[Int](n)
    val visited = mutable.Set(n)
    var level = 0
    
    while (queue.nonEmpty) {
      level += 1
      val size = queue.size
      for (_ <- 0 until size) {
        val curr = queue.dequeue()
        var j = 1
        while (j * j <= curr) {
          val next = curr - j * j
          if (next == 0) return level
          if (!visited.contains(next)) {
            visited.add(next)
            queue.enqueue(next)
          }
          j += 1
        }
      }
    }
    level
  }
  
  // 10. Walls and Gates
  def wallsAndGates(rooms: Array[Array[Int]]): Unit = {
    if (rooms.isEmpty) return
    
    val queue = mutable.Queue[(Int, Int)]()
    for (i <- rooms.indices; j <- rooms(0).indices) {
      if (rooms(i)(j) == 0) queue.enqueue((i, j))
    }
    
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    while (queue.nonEmpty) {
      val (x, y) = queue.dequeue()
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && nx < rooms.length && ny >= 0 && ny < rooms(0).length && 
            rooms(nx)(ny) == Int.MaxValue) {
          rooms(nx)(ny) = rooms(x)(y) + 1
          queue.enqueue((nx, ny))
        }
      }
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== BFS Examples ===")
    
    // Test 1: Level Order Traversal
    val root = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
    println("Test 1 - Level Order: " + levelOrder(root).toString)
    
    // Test 2: Number of Islands
    val grid = Array(Array('1','1','0','0','0'),Array('1','1','0','0','0'),Array('0','0','1','0','0'),Array('0','0','0','1','1'))
    println("Test 2 - Number of Islands: " + numIslands(grid).toString)
    
    // Test 3: Rotting Oranges
    val oranges = Array(Array(2,1,1),Array(1,1,0),Array(0,1,1))
    println("Test 3 - Rotting Oranges: " + orangesRotting(oranges).toString)
    
    // Test 4: Word Ladder
    println("Test 4 - Word Ladder: " + ladderLength("hit", "cog", List("hot","dot","dog","lot","log","cog")).toString)
    
    // Test 5: Minimum Depth
    println("Test 5 - Min Depth: " + minDepth(root).toString)
    
    // Test 6: Open the Lock
    println("Test 6 - Open Lock: " + openLock(Array("0201","0101","0102","1212","2002"), "0202").toString)
    
    // Test 7: Right Side View
    println("Test 7 - Right Side View: " + rightSideView(root).toString)
    
    // Test 8: Shortest Path Binary Matrix
    val matrix = Array(Array(0,0,0),Array(1,1,0),Array(1,1,0))
    println("Test 8 - Shortest Path: " + shortestPathBinaryMatrix(matrix).toString)
    
    // Test 9: Perfect Squares
    println("Test 9 - Perfect Squares(12): " + numSquares(12).toString)
    
    // Test 10: Walls and Gates
    val rooms = Array(Array(Int.MaxValue, -1, 0, Int.MaxValue))
    wallsAndGates(rooms)
    println("Test 10 - Walls and Gates completed")
  }
}