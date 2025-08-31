import scala.collection.mutable

object ScalaDijkstraExamples {
  
  // 1. Network Delay Time
  def networkDelayTime(times: Array[Array[Int]], n: Int, k: Int): Int = {
    val graph = mutable.Map[Int, mutable.ListBuffer[(Int, Int)]]()
    for (time <- times) {
      graph.getOrElseUpdate(time(0), mutable.ListBuffer[(Int, Int)]()) += ((time(1), time(2)))
    }
    
    val dist = Array.fill(n + 1)(Int.MaxValue)
    dist(k) = 0
    val pq = mutable.PriorityQueue[(Int, Int)]()(Ordering.by[(Int, Int), Int](_._1).reverse)
    pq.enqueue((0, k))
    
    while (pq.nonEmpty) {
      val (d, u) = pq.dequeue()
      if (d <= dist(u)) {
      
      for ((v, w) <- graph.getOrElse(u, mutable.ListBuffer())) {
        if (dist(u) + w < dist(v)) {
          dist(v) = dist(u) + w
          pq.enqueue((dist(v), v))
        }
      }
      }
    }
    
    val maxDist = (1 to n).map(dist).max
    if (maxDist == Int.MaxValue) -1 else maxDist
  }
  
  // 2. Cheapest Flights Within K Stops
  def findCheapestPrice(n: Int, flights: Array[Array[Int]], src: Int, dst: Int, k: Int): Int = {
    val graph = mutable.Map[Int, mutable.ListBuffer[(Int, Int)]]()
    for (flight <- flights) {
      graph.getOrElseUpdate(flight(0), mutable.ListBuffer[(Int, Int)]()) += ((flight(1), flight(2)))
    }
    
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    pq.enqueue((0, src, 0))
    val visited = mutable.Map[(Int, Int), Int]()
    
    while (pq.nonEmpty) {
      val (cost, node, stops) = pq.dequeue()
      
      if (node == dst) return cost
      if (stops <= k && (!visited.contains((node, stops)) || visited((node, stops)) > cost)) {
      
      visited((node, stops)) = cost
      
      for ((neighbor, price) <- graph.getOrElse(node, mutable.ListBuffer())) {
        pq.enqueue((cost + price, neighbor, stops + 1))
      }
      }
    }
    
    -1
  }
  
  // 3. Path With Minimum Effort
  def minimumEffortPath(heights: Array[Array[Int]]): Int = {
    val m = heights.length
    val n = heights(0).length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    val dist = Array.fill(m, n)(Int.MaxValue)
    dist(0)(0) = 0
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    pq.enqueue((0, 0, 0))
    
    while (pq.nonEmpty) {
      val (effort, x, y) = pq.dequeue()
      
      if (x == m - 1 && y == n - 1) return effort
      if (effort <= dist(x)(y)) {
      
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
          val newEffort = math.max(effort, math.abs(heights(nx)(ny) - heights(x)(y)))
          if (newEffort < dist(nx)(ny)) {
            dist(nx)(ny) = newEffort
            pq.enqueue((newEffort, nx, ny))
          }
        }
      }
      }
    }
    
    -1
  }
  
  // 4. Swim in Rising Water
  def swimInWater(grid: Array[Array[Int]]): Int = {
    val n = grid.length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    val dist = Array.fill(n, n)(Int.MaxValue)
    dist(0)(0) = grid(0)(0)
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    pq.enqueue((grid(0)(0), 0, 0))
    
    while (pq.nonEmpty) {
      val (time, x, y) = pq.dequeue()
      
      if (x == n - 1 && y == n - 1) return time
      if (time <= dist(x)(y)) {
      
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
          val newTime = math.max(time, grid(nx)(ny))
          if (newTime < dist(nx)(ny)) {
            dist(nx)(ny) = newTime
            pq.enqueue((newTime, nx, ny))
          }
        }
      }
      }
    }
    
    -1
  }
  
  // 5. The Maze II
  def shortestDistance(maze: Array[Array[Int]], start: Array[Int], destination: Array[Int]): Int = {
    val m = maze.length
    val n = maze(0).length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    val dist = Array.fill(m, n)(Int.MaxValue)
    dist(start(0))(start(1)) = 0
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    pq.enqueue((0, start(0), start(1)))
    
    while (pq.nonEmpty) {
      val (d, x, y) = pq.dequeue()
      
      if (d <= dist(x)(y)) {
      
      for ((dx, dy) <- directions) {
        var nx = x
        var ny = y
        var steps = 0
        
        while (nx + dx >= 0 && nx + dx < m && ny + dy >= 0 && ny + dy < n && maze(nx + dx)(ny + dy) == 0) {
          nx += dx
          ny += dy
          steps += 1
        }
        
        if (dist(x)(y) + steps < dist(nx)(ny)) {
          dist(nx)(ny) = dist(x)(y) + steps
          pq.enqueue((dist(nx)(ny), nx, ny))
        }
      }
      }
    }
    
    val result = dist(destination(0))(destination(1))
    if (result == Int.MaxValue) -1 else result
  }
  
  // 6. Path with Maximum Probability
  def maxProbability(n: Int, edges: Array[Array[Int]], succProb: Array[Double], start: Int, end: Int): Double = {
    val graph = mutable.Map[Int, mutable.ListBuffer[(Int, Double)]]()
    for (i <- edges.indices) {
      val Array(a, b) = edges(i)
      val prob = succProb(i)
      graph.getOrElseUpdate(a, mutable.ListBuffer[(Int, Double)]()) += ((b, prob))
      graph.getOrElseUpdate(b, mutable.ListBuffer[(Int, Double)]()) += ((a, prob))
    }
    
    val dist = Array.fill(n)(0.0)
    dist(start) = 1.0
    val pq = mutable.PriorityQueue[(Double, Int)]()(Ordering.by[(Double, Int), Double](_._1))
    pq.enqueue((1.0, start))
    
    while (pq.nonEmpty) {
      val (prob, node) = pq.dequeue()
      
      if (node == end) return prob
      if (prob >= dist(node)) {
      
      for ((neighbor, edgeProb) <- graph.getOrElse(node, mutable.ListBuffer())) {
        val newProb = prob * edgeProb
        if (newProb > dist(neighbor)) {
          dist(neighbor) = newProb
          pq.enqueue((newProb, neighbor))
        }
      }
      }
    }
    
    0.0
  }
  
  // 7. Minimum Cost to Make at Least One Valid Path in a Grid
  def minCost(grid: Array[Array[Int]]): Int = {
    val m = grid.length
    val n = grid(0).length
    val directions = Array((0, 1), (0, -1), (1, 0), (-1, 0))
    
    val dist = Array.fill(m, n)(Int.MaxValue)
    dist(0)(0) = 0
    val pq = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by[(Int, Int, Int), Int](_._1).reverse)
    pq.enqueue((0, 0, 0))
    
    while (pq.nonEmpty) {
      val (cost, x, y) = pq.dequeue()
      
      if (x == m - 1 && y == n - 1) return cost
      if (cost <= dist(x)(y)) {
      
      for (dir <- directions.indices) {
        val (dx, dy) = directions(dir)
        val nx = x + dx
        val ny = y + dy
        
        if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
          val newCost = cost + (if (grid(x)(y) == dir + 1) 0 else 1)
          if (newCost < dist(nx)(ny)) {
            dist(nx)(ny) = newCost
            pq.enqueue((newCost, nx, ny))
          }
        }
      }
      }
    }
    
    -1
  }
  
  // 8. Shortest Path in a Grid with Obstacles Elimination
  def shortestPath(grid: Array[Array[Int]], k: Int): Int = {
    val m = grid.length
    val n = grid(0).length
    val directions = Array((0, 1), (1, 0), (0, -1), (-1, 0))
    
    if (k >= m + n - 2) return m + n - 2
    
    val visited = mutable.Set[(Int, Int, Int)]()
    val queue = mutable.Queue[(Int, Int, Int, Int)]()
    queue.enqueue((0, 0, 0, 0))
    
    while (queue.nonEmpty) {
      val (x, y, obstacles, steps) = queue.dequeue()
      
      if (x == m - 1 && y == n - 1) return steps
      if (!visited.contains((x, y, obstacles))) {
      
      visited += ((x, y, obstacles))
      
      for ((dx, dy) <- directions) {
        val nx = x + dx
        val ny = y + dy
        
        if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
          val newObstacles = obstacles + grid(nx)(ny)
          if (newObstacles <= k && !visited.contains((nx, ny, newObstacles))) {
            queue.enqueue((nx, ny, newObstacles, steps + 1))
          }
        }
      }
      }
    }
    
    -1
  }
  
  // 9. Minimum Weighted Subgraph With the Required Paths
  def minimumWeight(n: Int, edges: Array[Array[Int]], src1: Int, src2: Int, dest: Int): Long = {
    def dijkstra(start: Int, graph: mutable.Map[Int, mutable.ListBuffer[(Int, Long)]]): Array[Long] = {
      val dist = Array.fill(n)(Long.MaxValue)
      dist(start) = 0
      val pq = mutable.PriorityQueue[(Long, Int)]()(Ordering.by[(Long, Int), Long](_._1).reverse)
      pq.enqueue((0L, start))
      
      while (pq.nonEmpty) {
        val (d, u) = pq.dequeue()
        if (d <= dist(u)) {
        
        for ((v, w) <- graph.getOrElse(u, mutable.ListBuffer())) {
          if (dist(u) + w < dist(v)) {
            dist(v) = dist(u) + w
            pq.enqueue((dist(v), v))
          }
        }
        }
      }
      
      dist
    }
    
    val graph = mutable.Map[Int, mutable.ListBuffer[(Int, Long)]]()
    val reverseGraph = mutable.Map[Int, mutable.ListBuffer[(Int, Long)]]()
    
    for (edge <- edges) {
      val Array(from, to, weight) = edge
      graph.getOrElseUpdate(from, mutable.ListBuffer[(Int, Long)]()) += ((to, weight.toLong))
      reverseGraph.getOrElseUpdate(to, mutable.ListBuffer[(Int, Long)]()) += ((from, weight.toLong))
    }
    
    val distFromSrc1 = dijkstra(src1, graph)
    val distFromSrc2 = dijkstra(src2, graph)
    val distToDest = dijkstra(dest, reverseGraph)
    
    var minWeight = Long.MaxValue
    for (i <- 0 until n) {
      if (distFromSrc1(i) != Long.MaxValue && distFromSrc2(i) != Long.MaxValue && distToDest(i) != Long.MaxValue) {
        minWeight = math.min(minWeight, distFromSrc1(i) + distFromSrc2(i) + distToDest(i))
      }
    }
    
    if (minWeight == Long.MaxValue) -1L else minWeight
  }
  
  // 10. Find the City With the Smallest Number of Neighbors at a Threshold Distance
  def findTheCity(n: Int, edges: Array[Array[Int]], distanceThreshold: Int): Int = {
    val dist = Array.fill(n, n)(Int.MaxValue)
    
    for (i <- 0 until n) {
      dist(i)(i) = 0
    }
    
    for (edge <- edges) {
      val Array(from, to, weight) = edge
      dist(from)(to) = weight
      dist(to)(from) = weight
    }
    
    // Floyd-Warshall
    for (k <- 0 until n; i <- 0 until n; j <- 0 until n) {
      if (dist(i)(k) != Int.MaxValue && dist(k)(j) != Int.MaxValue) {
        dist(i)(j) = math.min(dist(i)(j), dist(i)(k) + dist(k)(j))
      }
    }
    
    var minCount = Int.MaxValue
    var result = -1
    
    for (i <- 0 until n) {
      var count = 0
      for (j <- 0 until n) {
        if (i != j && dist(i)(j) <= distanceThreshold) {
          count += 1
        }
      }
      if (count <= minCount) {
        minCount = count
        result = i
      }
    }
    
    result
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Dijkstra Examples ===")
    
    // Test 1: Network Delay Time
    println("Test 1 - Network Delay Time: " + networkDelayTime(Array(Array(2,1,1),Array(2,3,1),Array(3,4,1)), 4, 2))
    
    // Test 2: Cheapest Flights Within K Stops
    println("Test 2 - Cheapest Price: " + findCheapestPrice(3, Array(Array(0,1,100),Array(1,2,100),Array(0,2,500)), 0, 2, 1))
    
    // Test 3: Path With Minimum Effort
    val heights = Array(Array(1,2,2),Array(3,8,2),Array(5,3,5))
    println("Test 3 - Minimum Effort Path: " + minimumEffortPath(heights))
    
    // Test 4: Swim in Rising Water
    val grid = Array(Array(0,2),Array(1,3))
    println("Test 4 - Swim in Water: " + swimInWater(grid))
    
    // Test 5: The Maze II
    val maze = Array(Array(0,0,1,0,0),Array(0,0,0,0,0),Array(0,0,0,1,0),Array(1,1,0,1,1),Array(0,0,0,0,0))
    println("Test 5 - Shortest Distance: " + shortestDistance(maze, Array(0,4), Array(4,4)))
    
    // Test 6: Path with Maximum Probability
    println("Test 6 - Max Probability: " + maxProbability(3, Array(Array(0,1),Array(1,2),Array(0,2)), Array(0.5,0.5,0.2), 0, 2))
    
    // Test 7: Minimum Cost to Make at Least One Valid Path
    val pathGrid = Array(Array(1,1,1,1),Array(2,2,2,2),Array(1,1,1,1),Array(2,2,2,2))
    println("Test 7 - Min Cost: " + minCost(pathGrid))
    
    // Test 8: Shortest Path with Obstacles Elimination
    val obstacleGrid = Array(Array(0,0,0),Array(1,1,0),Array(0,0,0),Array(0,1,1),Array(0,0,0))
    println("Test 8 - Shortest Path: " + shortestPath(obstacleGrid, 1))
    
    // Test 9: Minimum Weighted Subgraph
    println("Test 9 - Minimum Weight: " + minimumWeight(6, Array(Array(0,2,2),Array(0,5,6),Array(1,0,3),Array(1,4,5),Array(2,1,1),Array(2,3,3),Array(2,3,4),Array(3,4,2),Array(4,5,1)), 0, 1, 5))
    
    // Test 10: Find the City
    println("Test 10 - Find The City: " + findTheCity(4, Array(Array(0,1,3),Array(1,2,1),Array(1,3,4),Array(2,3,1)), 4))
  }
}