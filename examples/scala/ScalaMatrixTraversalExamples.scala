import scala.collection.mutable

object ScalaMatrixTraversalExamples {
  
  // 1. Number of Islands
  def numIslands(grid: Array[Array[Char]]): Int = {
    if (grid.isEmpty) return 0
    
    val m = grid.length
    val n = grid(0).length
    var count = 0
    
    def dfs(i: Int, j: Int): Unit = {
      if (i < 0 || i >= m || j < 0 || j >= n || grid(i)(j) == '0') return
      grid(i)(j) = '0'
      dfs(i + 1, j)
      dfs(i - 1, j)
      dfs(i, j + 1)
      dfs(i, j - 1)
    }
    
    for (i <- grid.indices; j <- grid(0).indices) {
      if (grid(i)(j) == '1') {
        dfs(i, j)
        count += 1
      }
    }
    
    count
  }
  
  // 2. Max Area of Island
  def maxAreaOfIsland(grid: Array[Array[Int]]): Int = {
    val m = grid.length
    val n = grid(0).length
    var maxArea = 0
    
    def dfs(i: Int, j: Int): Int = {
      if (i < 0 || i >= m || j < 0 || j >= n || grid(i)(j) == 0) return 0
      grid(i)(j) = 0
      1 + dfs(i + 1, j) + dfs(i - 1, j) + dfs(i, j + 1) + dfs(i, j - 1)
    }
    
    for (i <- grid.indices; j <- grid(0).indices) {
      if (grid(i)(j) == 1) {
        maxArea = math.max(maxArea, dfs(i, j))
      }
    }
    
    maxArea
  }
  
  // 3. Surrounded Regions
  def solve(board: Array[Array[Char]]): Unit = {
    if (board.isEmpty) return
    
    val m = board.length
    val n = board(0).length
    
    def dfs(i: Int, j: Int): Unit = {
      if (i < 0 || i >= m || j < 0 || j >= n || board(i)(j) != 'O') return
      board(i)(j) = 'T'
      dfs(i + 1, j)
      dfs(i - 1, j)
      dfs(i, j + 1)
      dfs(i, j - 1)
    }
    
    // Mark boundary-connected 'O's
    for (i <- board.indices) {
      dfs(i, 0)
      dfs(i, n - 1)
    }
    for (j <- board(0).indices) {
      dfs(0, j)
      dfs(m - 1, j)
    }
    
    // Convert remaining 'O's to 'X' and 'T's back to 'O'
    for (i <- board.indices; j <- board(0).indices) {
      if (board(i)(j) == 'O') board(i)(j) = 'X'
      else if (board(i)(j) == 'T') board(i)(j) = 'O'
    }
  }
  
  // 4. Pacific Atlantic Water Flow
  def pacificAtlantic(heights: Array[Array[Int]]): List[List[Int]] = {
    val m = heights.length
    val n = heights(0).length
    val pacific = Array.fill(m, n)(false)
    val atlantic = Array.fill(m, n)(false)
    
    def dfs(i: Int, j: Int, ocean: Array[Array[Boolean]]): Unit = {
      ocean(i)(j) = true
      val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
      for ((di, dj) <- directions) {
        val ni = i + di
        val nj = j + dj
        if (ni >= 0 && ni < m && nj >= 0 && nj < n && 
            !ocean(ni)(nj) && heights(ni)(nj) >= heights(i)(j)) {
          dfs(ni, nj, ocean)
        }
      }
    }
    
    // Start from Pacific borders
    for (i <- 0 until m) {
      dfs(i, 0, pacific)
      dfs(i, n - 1, atlantic)
    }
    for (j <- 0 until n) {
      dfs(0, j, pacific)
      dfs(m - 1, j, atlantic)
    }
    
    val result = mutable.ListBuffer[List[Int]]()
    for (i <- 0 until m; j <- 0 until n) {
      if (pacific(i)(j) && atlantic(i)(j)) {
        result += List(i, j)
      }
    }
    
    result.toList
  }
  
  // 5. Word Search
  def exist(board: Array[Array[Char]], word: String): Boolean = {
    val m = board.length
    val n = board(0).length
    
    def dfs(i: Int, j: Int, k: Int): Boolean = {
      if (k == word.length) return true
      if (i < 0 || i >= m || j < 0 || j >= n || board(i)(j) != word(k)) return false
      
      val temp = board(i)(j)
      board(i)(j) = '#'
      val found = dfs(i + 1, j, k + 1) || dfs(i - 1, j, k + 1) || 
                  dfs(i, j + 1, k + 1) || dfs(i, j - 1, k + 1)
      board(i)(j) = temp
      found
    }
    
    for (i <- board.indices; j <- board(0).indices) {
      if (dfs(i, j, 0)) return true
    }
    false
  }
  
  // 6. Spiral Matrix
  def spiralOrder(matrix: Array[Array[Int]]): List[Int] = {
    if (matrix.isEmpty) return List()
    
    val result = mutable.ListBuffer[Int]()
    var top = 0
    var bottom = matrix.length - 1
    var left = 0
    var right = matrix(0).length - 1
    
    while (top <= bottom && left <= right) {
      // Right
      for (j <- left to right) result += matrix(top)(j)
      top += 1
      
      // Down
      for (i <- top to bottom) result += matrix(i)(right)
      right -= 1
      
      // Left
      if (top <= bottom) {
        for (j <- right to left by -1) result += matrix(bottom)(j)
        bottom -= 1
      }
      
      // Up
      if (left <= right) {
        for (i <- bottom to top by -1) result += matrix(i)(left)
        left += 1
      }
    }
    
    result.toList
  }
  
  // 7. Rotate Image
  def rotate(matrix: Array[Array[Int]]): Unit = {
    val n = matrix.length
    
    // Transpose
    for (i <- 0 until n; j <- i until n) {
      val temp = matrix(i)(j)
      matrix(i)(j) = matrix(j)(i)
      matrix(j)(i) = temp
    }
    
    // Reverse each row
    for (i <- 0 until n) {
      var left = 0
      var right = n - 1
      while (left < right) {
        val temp = matrix(i)(left)
        matrix(i)(left) = matrix(i)(right)
        matrix(i)(right) = temp
        left += 1
        right -= 1
      }
    }
  }
  
  // 8. Set Matrix Zeroes
  def setZeroes(matrix: Array[Array[Int]]): Unit = {
    val m = matrix.length
    val n = matrix(0).length
    var firstRowZero = false
    var firstColZero = false
    
    // Check if first row has zero
    for (j <- 0 until n) {
      if (matrix(0)(j) == 0) firstRowZero = true
    }
    
    // Check if first column has zero
    for (i <- 0 until m) {
      if (matrix(i)(0) == 0) firstColZero = true
    }
    
    // Use first row and column as markers
    for (i <- 1 until m; j <- 1 until n) {
      if (matrix(i)(j) == 0) {
        matrix(i)(0) = 0
        matrix(0)(j) = 0
      }
    }
    
    // Set zeros based on markers
    for (i <- 1 until m; j <- 1 until n) {
      if (matrix(i)(0) == 0 || matrix(0)(j) == 0) {
        matrix(i)(j) = 0
      }
    }
    
    // Handle first row
    if (firstRowZero) {
      for (j <- 0 until n) matrix(0)(j) = 0
    }
    
    // Handle first column
    if (firstColZero) {
      for (i <- 0 until m) matrix(i)(0) = 0
    }
  }
  
  // 9. Valid Sudoku
  def isValidSudoku(board: Array[Array[Char]]): Boolean = {
    val rows = Array.fill(9)(mutable.Set[Char]())
    val cols = Array.fill(9)(mutable.Set[Char]())
    val boxes = Array.fill(9)(mutable.Set[Char]())
    
    for (i <- 0 until 9; j <- 0 until 9) {
      val char = board(i)(j)
      if (char != '.') {
        val boxIndex = (i / 3) * 3 + j / 3
        if (rows(i).contains(char) || cols(j).contains(char) || boxes(boxIndex).contains(char)) {
          return false
        }
        rows(i) += char
        cols(j) += char
        boxes(boxIndex) += char
      }
    }
    
    true
  }
  
  // 10. Game of Life
  def gameOfLife(board: Array[Array[Int]]): Unit = {
    val m = board.length
    val n = board(0).length
    val directions = Array((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
    
    for (i <- 0 until m; j <- 0 until n) {
      var liveNeighbors = 0
      for ((di, dj) <- directions) {
        val ni = i + di
        val nj = j + dj
        if (ni >= 0 && ni < m && nj >= 0 && nj < n && (board(ni)(nj) == 1 || board(ni)(nj) == 2)) {
          liveNeighbors += 1
        }
      }
      
      if (board(i)(j) == 1 && (liveNeighbors < 2 || liveNeighbors > 3)) {
        board(i)(j) = 2 // Live -> Dead
      } else if (board(i)(j) == 0 && liveNeighbors == 3) {
        board(i)(j) = 3 // Dead -> Live
      }
    }
    
    for (i <- 0 until m; j <- 0 until n) {
      if (board(i)(j) == 2) board(i)(j) = 0
      else if (board(i)(j) == 3) board(i)(j) = 1
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Matrix Traversal Examples ===")
    
    // Test 1: Number of Islands
    val grid1 = Array(Array('1','1','1','1','0'),Array('1','1','0','1','0'),Array('1','1','0','0','0'),Array('0','0','0','0','0'))
    println("Test 1 - Num Islands: " + numIslands(grid1))
    
    // Test 2: Max Area of Island
    val grid2 = Array(Array(1,1,0,0,0),Array(1,1,0,0,0),Array(0,0,0,1,1),Array(0,0,0,1,1))
    println("Test 2 - Max Area of Island: " + maxAreaOfIsland(grid2))
    
    // Test 3: Surrounded Regions
    val board = Array(Array('X','X','X','X'),Array('X','O','O','X'),Array('X','X','O','X'),Array('X','O','X','X'))
    solve(board)
    println("Test 3 - Surrounded Regions: Completed")
    
    // Test 4: Pacific Atlantic Water Flow
    val heights = Array(Array(1,2,2,3,5),Array(3,2,3,4,4),Array(2,4,5,3,1),Array(6,7,1,4,5),Array(5,1,1,2,4))
    println("Test 4 - Pacific Atlantic: " + pacificAtlantic(heights).length + " cells")
    
    // Test 5: Word Search
    val wordBoard = Array(Array('A','B','C','E'),Array('S','F','C','S'),Array('A','D','E','E'))
    println("Test 5 - Word Search 'ABCCED': " + exist(wordBoard, "ABCCED"))
    
    // Test 6: Spiral Matrix
    val matrix = Array(Array(1,2,3),Array(4,5,6),Array(7,8,9))
    println("Test 6 - Spiral Order: " + spiralOrder(matrix))
    
    // Test 7: Rotate Image
    val rotateMatrix = Array(Array(1,2,3),Array(4,5,6),Array(7,8,9))
    rotate(rotateMatrix)
    println("Test 7 - Rotate Image: Completed")
    
    // Test 8: Set Matrix Zeroes
    val zeroMatrix = Array(Array(1,1,1),Array(1,0,1),Array(1,1,1))
    setZeroes(zeroMatrix)
    println("Test 8 - Set Matrix Zeroes: Completed")
    
    // Test 9: Valid Sudoku
    val sudoku = Array(Array('5','3','.','.','7','.','.','.','.'),Array('6','.','.','1','9','5','.','.','.'),Array('.','9','8','.','.','.','.','6','.'),Array('8','.','.','.','6','.','.','.','3'),Array('4','.','.','8','.','3','.','.','1'),Array('7','.','.','.','2','.','.','.','6'),Array('.','6','.','.','.','.','2','8','.'),Array('.','.','.','4','1','9','.','.','5'),Array('.','.','.','.','8','.','.','7','9'))
    println("Test 9 - Valid Sudoku: " + isValidSudoku(sudoku))
    
    // Test 10: Game of Life
    val lifeBoard = Array(Array(0,1,0),Array(0,0,1),Array(1,1,1),Array(0,0,0))
    gameOfLife(lifeBoard)
    println("Test 10 - Game of Life: Completed")
  }
}