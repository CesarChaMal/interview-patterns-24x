import scala.collection.mutable

object ScalaUnionFindExamples {
  
  class UnionFind(n: Int) {
    private val parent = Array.tabulate(n)(identity)
    private val rank = Array.fill(n)(0)
    var components = n
    
    def find(x: Int): Int = {
      if (parent(x) != x) {
        parent(x) = find(parent(x))
      }
      parent(x)
    }
    
    def union(x: Int, y: Int): Boolean = {
      val px = find(x)
      val py = find(y)
      if (px == py) return false
      
      if (rank(px) < rank(py)) {
        parent(px) = py
      } else if (rank(px) > rank(py)) {
        parent(py) = px
      } else {
        parent(py) = px
        rank(px) += 1
      }
      components -= 1
      true
    }
    
    def connected(x: Int, y: Int): Boolean = find(x) == find(y)
  }
  
  // 1. Number of Islands
  def numIslands(grid: Array[Array[Char]]): Int = {
    if (grid.isEmpty) return 0
    val m = grid.length
    val n = grid(0).length
    val uf = new UnionFind(m * n)
    var islands = 0
    
    for (i <- grid.indices; j <- grid(0).indices) {
      if (grid(i)(j) == '1') {
        islands += 1
        val curr = i * n + j
        if (i > 0 && grid(i-1)(j) == '1' && uf.union(curr, (i-1) * n + j)) {
          islands -= 1
        }
        if (j > 0 && grid(i)(j-1) == '1' && uf.union(curr, i * n + j - 1)) {
          islands -= 1
        }
      }
    }
    islands
  }
  
  // 2. Friend Circles
  def findCircleNum(isConnected: Array[Array[Int]]): Int = {
    val n = isConnected.length
    val uf = new UnionFind(n)
    
    for (i <- isConnected.indices; j <- i + 1 until n) {
      if (isConnected(i)(j) == 1) {
        uf.union(i, j)
      }
    }
    
    uf.components
  }
  
  // 3. Graph Valid Tree
  def validTree(n: Int, edges: Array[Array[Int]]): Boolean = {
    if (edges.length != n - 1) return false
    val uf = new UnionFind(n)
    
    for (edge <- edges) {
      if (!uf.union(edge(0), edge(1))) {
        return false
      }
    }
    
    uf.components == 1
  }
  
  // 4. Accounts Merge
  def accountsMerge(accounts: List[List[String]]): List[List[String]] = {
    val emailToId = mutable.Map[String, Int]()
    val emailToName = mutable.Map[String, String]()
    var idCounter = 0
    
    for (account <- accounts) {
      val name = account.head
      for (email <- account.tail) {
        if (!emailToId.contains(email)) {
          emailToId(email) = idCounter
          idCounter += 1
        }
        emailToName(email) = name
      }
    }
    
    val uf = new UnionFind(idCounter)
    for (account <- accounts) {
      val firstId = emailToId(account(1))
      for (email <- account.drop(2)) {
        uf.union(firstId, emailToId(email))
      }
    }
    
    val groups = mutable.Map[Int, mutable.ListBuffer[String]]()
    for ((email, emailId) <- emailToId) {
      val root = uf.find(emailId)
      groups.getOrElseUpdate(root, mutable.ListBuffer[String]()) += email
    }
    
    groups.values.map { emails =>
      val sortedEmails = emails.sorted.toList
      emailToName(sortedEmails.head) :: sortedEmails
    }.toList
  }
  
  // 5. Redundant Connection
  def findRedundantConnection(edges: Array[Array[Int]]): Array[Int] = {
    val uf = new UnionFind(edges.length + 1)
    for (edge <- edges) {
      if (!uf.union(edge(0), edge(1))) {
        return edge
      }
    }
    Array()
  }
  
  // 6. Most Stones Removed with Same Row or Column
  def removeStones(stones: Array[Array[Int]]): Int = {
    val uf = new UnionFind(20000)
    val seen = mutable.Set[Int]()
    
    for (stone <- stones) {
      val x = stone(0)
      val y = stone(1) + 10000
      uf.union(x, y)
      seen += x
      seen += y
    }
    
    val roots = seen.map(uf.find).toSet
    stones.length - roots.size
  }
  
  // 7. Satisfiability of Equality Equations
  def equationsPossible(equations: Array[String]): Boolean = {
    val uf = new UnionFind(26)
    
    for (eq <- equations) {
      if (eq(1) == '=') {
        uf.union(eq(0) - 'a', eq(3) - 'a')
      }
    }
    
    for (eq <- equations) {
      if (eq(1) == '!' && uf.connected(eq(0) - 'a', eq(3) - 'a')) {
        return false
      }
    }
    
    true
  }
  
  // 8. Swim in Rising Water
  def swimInWater(grid: Array[Array[Int]]): Int = {
    val n = grid.length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    val heap = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    val visited = mutable.Set[(Int, Int)]()
    
    heap.enqueue((grid(0)(0), 0, 0))
    
    while (heap.nonEmpty) {
      val (time, x, y) = heap.dequeue()
      
      if (visited.contains((x, y))) {
        // continue
      } else {
        visited += ((x, y))
        
        if (x == n - 1 && y == n - 1) {
          return time
        }
        
        for ((dx, dy) <- directions) {
          val nx = x + dx
          val ny = y + dy
          if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited.contains((nx, ny))) {
            heap.enqueue((math.max(time, grid(nx)(ny)), nx, ny))
          }
        }
      }
    }
    
    -1
  }
  
  // 9. Connecting Cities With Minimum Cost
  def minimumCost(n: Int, connections: Array[Array[Int]]): Int = {
    val sortedConnections = connections.sortBy(_(2))
    val uf = new UnionFind(n + 1)
    var cost = 0
    var edges = 0
    
    for (connection <- sortedConnections) {
      val Array(a, b, c) = connection
      if (uf.union(a, b)) {
        cost += c
        edges += 1
        if (edges == n - 1) {
          return cost
        }
      }
    }
    
    if (edges == n - 1) cost else -1
  }
  
  // 10. Number of Operations to Make Network Connected
  def makeConnected(n: Int, connections: Array[Array[Int]]): Int = {
    if (connections.length < n - 1) return -1
    
    val uf = new UnionFind(n)
    for (connection <- connections) {
      uf.union(connection(0), connection(1))
    }
    
    uf.components - 1
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Union-Find Examples ===")
    
    // Test 1: Number of Islands
    val grid = Array(Array('1','1','1','1','0'),Array('1','1','0','1','0'),Array('1','1','0','0','0'),Array('0','0','0','0','0'))
    println("Test 1 - Num Islands: " + numIslands(grid))
    
    // Test 2: Friend Circles
    val isConnected = Array(Array(1,1,0),Array(1,1,0),Array(0,0,1))
    println("Test 2 - Find Circle Num: " + findCircleNum(isConnected))
    
    // Test 3: Graph Valid Tree
    println("Test 3 - Valid Tree: " + validTree(5, Array(Array(0,1),Array(0,2),Array(0,3),Array(1,4))))
    
    // Test 4: Accounts Merge
    val accounts = List(List("John","johnsmith@mail.com","john_newyork@mail.com"),
                       List("John","johnsmith@mail.com","john00@mail.com"),
                       List("Mary","mary@mail.com"),
                       List("John","johnnybravo@mail.com"))
    println("Test 4 - Accounts Merge: " + accountsMerge(accounts).length + " accounts")
    
    // Test 5: Redundant Connection
    println("Test 5 - Redundant Connection: " + findRedundantConnection(Array(Array(1,2),Array(1,3),Array(2,3))).mkString("[", ",", "]"))
    
    // Test 6: Most Stones Removed
    println("Test 6 - Remove Stones: " + removeStones(Array(Array(0,0),Array(0,1),Array(1,0),Array(1,2),Array(2,1),Array(2,2))))
    
    // Test 7: Satisfiability of Equality Equations
    println("Test 7 - Equations Possible: " + equationsPossible(Array("a==b","b!=a")))
    
    // Test 8: Swim in Rising Water
    val swimGrid = Array(Array(0,2),Array(1,3))
    println("Test 8 - Swim in Water: " + swimInWater(swimGrid))
    
    // Test 9: Connecting Cities
    println("Test 9 - Minimum Cost: " + minimumCost(3, Array(Array(1,2,5),Array(1,3,6),Array(2,3,1))))
    
    // Test 10: Make Connected
    println("Test 10 - Make Connected: " + makeConnected(4, Array(Array(0,1),Array(0,2),Array(1,2))))
  }
}