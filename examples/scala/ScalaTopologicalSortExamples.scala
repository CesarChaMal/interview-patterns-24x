import scala.collection.mutable

object ScalaTopologicalSortExamples {
  
  // 1. Course Schedule
  def canFinish(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {
    val graph = Array.fill(numCourses)(mutable.ListBuffer[Int]())
    val indegree = Array.fill(numCourses)(0)
    
    for (prereq <- prerequisites) {
      graph(prereq(1)) += prereq(0)
      indegree(prereq(0)) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- indegree.indices if indegree(i) == 0) {
      queue.enqueue(i)
    }
    
    var count = 0
    while (queue.nonEmpty) {
      val course = queue.dequeue()
      count += 1
      
      for (next <- graph(course)) {
        indegree(next) -= 1
        if (indegree(next) == 0) {
          queue.enqueue(next)
        }
      }
    }
    
    count == numCourses
  }
  
  // 2. Course Schedule II
  def findOrder(numCourses: Int, prerequisites: Array[Array[Int]]): Array[Int] = {
    val graph = Array.fill(numCourses)(mutable.ListBuffer[Int]())
    val indegree = Array.fill(numCourses)(0)
    
    for (prereq <- prerequisites) {
      graph(prereq(1)) += prereq(0)
      indegree(prereq(0)) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- indegree.indices if indegree(i) == 0) {
      queue.enqueue(i)
    }
    
    val result = mutable.ListBuffer[Int]()
    while (queue.nonEmpty) {
      val course = queue.dequeue()
      result += course
      
      for (next <- graph(course)) {
        indegree(next) -= 1
        if (indegree(next) == 0) {
          queue.enqueue(next)
        }
      }
    }
    
    if (result.length == numCourses) result.toArray else Array()
  }
  
  // 3. Alien Dictionary
  def alienOrder(words: Array[String]): String = {
    val graph = mutable.Map[Char, mutable.Set[Char]]()
    val indegree = mutable.Map[Char, Int]()
    
    // Initialize
    for (word <- words; c <- word) {
      graph(c) = mutable.Set[Char]()
      indegree(c) = 0
    }
    
    // Build graph
    for (i <- 0 until words.length - 1) {
      val word1 = words(i)
      val word2 = words(i + 1)
      
      if (word1.length > word2.length && word1.startsWith(word2)) {
        return ""
      }
      
      var j = 0
      var found = false
      while (j < math.min(word1.length, word2.length) && !found) {
        if (word1(j) != word2(j)) {
          if (!graph(word1(j)).contains(word2(j))) {
            graph(word1(j)) += word2(j)
            indegree(word2(j)) += 1
          }
          found = true
        }
        j += 1
      }
    }
    
    val queue = mutable.Queue[Char]()
    for ((char, degree) <- indegree if degree == 0) {
      queue.enqueue(char)
    }
    
    val result = new mutable.StringBuilder()
    while (queue.nonEmpty) {
      val char = queue.dequeue()
      result += char
      
      for (next <- graph(char)) {
        indegree(next) -= 1
        if (indegree(next) == 0) {
          queue.enqueue(next)
        }
      }
    }
    
    if (result.length == indegree.size) result.toString else ""
  }
  
  // 4. Minimum Height Trees
  def findMinHeightTrees(n: Int, edges: Array[Array[Int]]): List[Int] = {
    if (n == 1) return List(0)
    
    val graph = Array.fill(n)(mutable.Set[Int]())
    for (edge <- edges) {
      graph(edge(0)) += edge(1)
      graph(edge(1)) += edge(0)
    }
    
    var leaves = mutable.Queue[Int]()
    for (i <- graph.indices if graph(i).size == 1) {
      leaves.enqueue(i)
    }
    
    var remaining = n
    while (remaining > 2) {
      val leafCount = leaves.size
      remaining -= leafCount
      
      for (_ <- 0 until leafCount) {
        val leaf = leaves.dequeue()
        val neighbor = graph(leaf).head
        graph(neighbor) -= leaf
        if (graph(neighbor).size == 1) {
          leaves.enqueue(neighbor)
        }
      }
    }
    
    leaves.toList
  }
  
  // 5. Sequence Reconstruction
  def sequenceReconstruction(nums: Array[Int], sequences: Array[Array[Int]]): Boolean = {
    val graph = mutable.Map[Int, mutable.Set[Int]]()
    val indegree = mutable.Map[Int, Int]()
    
    for (num <- nums) {
      graph(num) = mutable.Set[Int]()
      indegree(num) = 0
    }
    
    for (seq <- sequences) {
      for (i <- 0 until seq.length - 1) {
        if (!graph.contains(seq(i)) || !graph.contains(seq(i + 1))) {
          return false
        }
        if (!graph(seq(i)).contains(seq(i + 1))) {
          graph(seq(i)) += seq(i + 1)
          indegree(seq(i + 1)) += 1
        }
      }
    }
    
    val queue = mutable.Queue[Int]()
    for ((num, degree) <- indegree if degree == 0) {
      queue.enqueue(num)
    }
    
    val result = mutable.ListBuffer[Int]()
    while (queue.nonEmpty) {
      if (queue.size > 1) return false
      
      val num = queue.dequeue()
      result += num
      
      for (next <- graph(num)) {
        indegree(next) -= 1
        if (indegree(next) == 0) {
          queue.enqueue(next)
        }
      }
    }
    
    result.toArray.sameElements(nums)
  }
  
  // 6. Parallel Courses
  def minimumSemesters(n: Int, relations: Array[Array[Int]]): Int = {
    val graph = Array.fill(n + 1)(mutable.ListBuffer[Int]())
    val indegree = Array.fill(n + 1)(0)
    
    for (relation <- relations) {
      graph(relation(0)) += relation(1)
      indegree(relation(1)) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- 1 to n if indegree(i) == 0) {
      queue.enqueue(i)
    }
    
    var semesters = 0
    var studied = 0
    
    while (queue.nonEmpty) {
      semesters += 1
      val size = queue.size
      
      for (_ <- 0 until size) {
        val course = queue.dequeue()
        studied += 1
        
        for (next <- graph(course)) {
          indegree(next) -= 1
          if (indegree(next) == 0) {
            queue.enqueue(next)
          }
        }
      }
    }
    
    if (studied == n) semesters else -1
  }
  
  // 7. Sort Items by Groups Respecting Dependencies
  def sortItems(n: Int, m: Int, group: Array[Int], beforeItems: Array[Array[Int]]): Array[Int] = {
    var groupId = m
    for (i <- group.indices if group(i) == -1) {
      group(i) = groupId
      groupId += 1
    }
    
    val itemGraph = Array.fill(n)(mutable.ListBuffer[Int]())
    val itemIndegree = Array.fill(n)(0)
    val groupGraph = Array.fill(groupId)(mutable.ListBuffer[Int]())
    val groupIndegree = Array.fill(groupId)(0)
    
    for (i <- beforeItems.indices) {
      for (prev <- beforeItems(i)) {
        itemGraph(prev) += i
        itemIndegree(i) += 1
        
        if (group(prev) != group(i)) {
          groupGraph(group(prev)) += group(i)
          groupIndegree(group(i)) += 1
        }
      }
    }
    
    def topologicalSort(graph: Array[mutable.ListBuffer[Int]], indegree: Array[Int]): Array[Int] = {
      val queue = mutable.Queue[Int]()
      for (i <- indegree.indices if indegree(i) == 0) {
        queue.enqueue(i)
      }
      
      val result = mutable.ListBuffer[Int]()
      while (queue.nonEmpty) {
        val node = queue.dequeue()
        result += node
        
        for (next <- graph(node)) {
          indegree(next) -= 1
          if (indegree(next) == 0) {
            queue.enqueue(next)
          }
        }
      }
      
      result.toArray
    }
    
    val itemOrder = topologicalSort(itemGraph, itemIndegree.clone())
    val groupOrder = topologicalSort(groupGraph, groupIndegree.clone())
    
    if (itemOrder.length != n || groupOrder.length != groupId) {
      return Array()
    }
    
    val groupToItems = mutable.Map[Int, mutable.ListBuffer[Int]]()
    for (item <- itemOrder) {
      groupToItems.getOrElseUpdate(group(item), mutable.ListBuffer[Int]()) += item
    }
    
    val result = mutable.ListBuffer[Int]()
    for (g <- groupOrder) {
      result ++= groupToItems.getOrElse(g, mutable.ListBuffer[Int]())
    }
    
    result.toArray
  }
  
  // 8. Build a Matrix With Conditions
  def buildMatrix(k: Int, rowConditions: Array[Array[Int]], colConditions: Array[Array[Int]]): Array[Array[Int]] = {
    def topologicalSort(conditions: Array[Array[Int]]): Array[Int] = {
      val graph = Array.fill(k + 1)(mutable.ListBuffer[Int]())
      val indegree = Array.fill(k + 1)(0)
      
      for (condition <- conditions) {
        graph(condition(0)) += condition(1)
        indegree(condition(1)) += 1
      }
      
      val queue = mutable.Queue[Int]()
      for (i <- 1 to k if indegree(i) == 0) {
        queue.enqueue(i)
      }
      
      val result = mutable.ListBuffer[Int]()
      while (queue.nonEmpty) {
        val node = queue.dequeue()
        result += node
        
        for (next <- graph(node)) {
          indegree(next) -= 1
          if (indegree(next) == 0) {
            queue.enqueue(next)
          }
        }
      }
      
      if (result.length == k) result.toArray else Array()
    }
    
    val rowOrder = topologicalSort(rowConditions)
    val colOrder = topologicalSort(colConditions)
    
    if (rowOrder.isEmpty || colOrder.isEmpty) {
      return Array()
    }
    
    val rowPos = mutable.Map[Int, Int]()
    val colPos = mutable.Map[Int, Int]()
    
    for (i <- rowOrder.indices) rowPos(rowOrder(i)) = i
    for (i <- colOrder.indices) colPos(colOrder(i)) = i
    
    val matrix = Array.fill(k, k)(0)
    for (i <- 1 to k) {
      matrix(rowPos(i))(colPos(i)) = i
    }
    
    matrix
  }
  
  // 9. Find All Possible Recipes from Given Supplies
  def findAllRecipes(recipes: Array[String], ingredients: Array[Array[String]], supplies: Array[String]): List[String] = {
    val graph = mutable.Map[String, mutable.ListBuffer[String]]()
    val indegree = mutable.Map[String, Int]()
    val available = mutable.Set(supplies: _*)
    
    for (i <- recipes.indices) {
      indegree(recipes(i)) = 0
      for (ingredient <- ingredients(i)) {
        if (!available.contains(ingredient)) {
          graph.getOrElseUpdate(ingredient, mutable.ListBuffer[String]()) += recipes(i)
          indegree(recipes(i)) += 1
        }
      }
    }
    
    val queue = mutable.Queue[String]()
    for ((recipe, degree) <- indegree if degree == 0) {
      queue.enqueue(recipe)
    }
    
    val result = mutable.ListBuffer[String]()
    while (queue.nonEmpty) {
      val recipe = queue.dequeue()
      result += recipe
      available += recipe
      
      for (next <- graph.getOrElse(recipe, mutable.ListBuffer[String]())) {
        indegree(next) -= 1
        if (indegree(next) == 0) {
          queue.enqueue(next)
        }
      }
    }
    
    result.toList
  }
  
  // 10. Maximum Students Taking Exam
  def maxStudents(seats: Array[Array[Char]]): Int = {
    val m = seats.length
    val n = seats(0).length
    val validMasks = Array.ofDim[Boolean](1 << n)
    
    for (mask <- validMasks.indices) {
      var valid = true
      for (j <- 0 until n) {
        if ((mask & (1 << j)) != 0) {
          if (seats(0)(j) == '#') {
            valid = false
          }
          if (j > 0 && (mask & (1 << (j - 1))) != 0) {
            valid = false
          }
        }
      }
      validMasks(mask) = valid
    }
    
    var dp = Array.fill(1 << n)(0)
    
    for (mask <- dp.indices if validMasks(mask)) {
      dp(mask) = Integer.bitCount(mask)
    }
    
    for (i <- 1 until m) {
      val newDp = Array.fill(1 << n)(0)
      
      for (mask <- newDp.indices if validMasks(mask)) {
        var valid = true
        for (j <- 0 until n) {
          if ((mask & (1 << j)) != 0 && seats(i)(j) == '#') {
            valid = false
          }
        }
        
        if (valid) {
          for (prevMask <- dp.indices) {
            var canPlace = true
            for (j <- 0 until n) {
              if ((mask & (1 << j)) != 0) {
                if (j > 0 && (prevMask & (1 << (j - 1))) != 0) canPlace = false
                if (j < n - 1 && (prevMask & (1 << (j + 1))) != 0) canPlace = false
              }
            }
            if (canPlace) {
              newDp(mask) = math.max(newDp(mask), dp(prevMask) + Integer.bitCount(mask))
            }
          }
        }
      }
      
      dp = newDp
    }
    
    dp.max
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Topological Sort Examples ===")
    
    // Test 1: Course Schedule
    println("Test 1 - Can Finish(2, [[1,0]]): " + canFinish(2, Array(Array(1,0))))
    
    // Test 2: Course Schedule II
    println("Test 2 - Find Order(2, [[1,0]]): " + findOrder(2, Array(Array(1,0))).mkString("[", ",", "]"))
    
    // Test 3: Alien Dictionary
    println("Test 3 - Alien Order(['wrt','wrf','er','ett','rftt']): " + alienOrder(Array("wrt","wrf","er","ett","rftt")))
    
    // Test 4: Minimum Height Trees
    println("Test 4 - Find Min Height Trees(4, [[1,0],[1,2],[1,3]]): " + findMinHeightTrees(4, Array(Array(1,0),Array(1,2),Array(1,3))))
    
    // Test 5: Sequence Reconstruction
    println("Test 5 - Sequence Reconstruction([1,2,3], [[1,2],[1,3]]): " + sequenceReconstruction(Array(1,2,3), Array(Array(1,2),Array(1,3))))
    
    // Test 6: Parallel Courses
    println("Test 6 - Minimum Semesters(3, [[1,3],[2,3]]): " + minimumSemesters(3, Array(Array(1,3),Array(2,3))))
    
    // Test 7: Sort Items by Groups Respecting Dependencies
    println("Test 7 - Sort Items: " + sortItems(8, 2, Array(-1,-1,1,0,0,1,0,-1), Array(Array(),Array(6),Array(5),Array(6),Array(3,6),Array(),Array(),Array())).mkString("[", ",", "]"))
    
    // Test 8: Build a Matrix With Conditions
    println("Test 8 - Build Matrix: " + buildMatrix(3, Array(Array(1,2),Array(3,2)), Array(Array(2,1),Array(3,2))).length + " rows")
    
    // Test 9: Find All Possible Recipes from Given Supplies
    println("Test 9 - Find All Recipes: " + findAllRecipes(Array("bread"), Array(Array("yeast","flour")), Array("yeast","flour","corn")))
    
    // Test 10: Maximum Students Taking Exam
    val seats = Array(Array('#','.','#','#','.','#'),Array('.','#','#','#','#','.'),Array('#','.','#','#','.','#'))
    println("Test 10 - Max Students: " + maxStudents(seats))
  }
}