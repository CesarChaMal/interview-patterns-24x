import java.util.*;

public class TopologicalSortExamples {
    
    // 1. Course Schedule
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        
        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            for (int next : graph.get(course)) {
                if (--indegree[next] == 0) queue.offer(next);
            }
        }
        return count == numCourses;
    }
    
    // 2. Course Schedule II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        
        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        int[] result = new int[numCourses];
        int idx = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[idx++] = course;
            for (int next : graph.get(course)) {
                if (--indegree[next] == 0) queue.offer(next);
            }
        }
        return idx == numCourses ? result : new int[0];
    }
    
    // 3. Alien Dictionary
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        
        for (String word : words) {
            for (char c : word.toCharArray()) {
                indegree.put(c, 0);
                graph.put(c, new HashSet<>());
            }
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i], w2 = words[i + 1];
            if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
            
            for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
                char c1 = w1.charAt(j), c2 = w2.charAt(j);
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }
        
        Queue<Character> queue = new LinkedList<>();
        for (char c : indegree.keySet()) {
            if (indegree.get(c) == 0) queue.offer(c);
        }
        
        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            result.append(c);
            for (char next : graph.get(c)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        
        return result.length() == indegree.size() ? result.toString() : "";
    }
    
    // 4. Minimum Height Trees
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return Arrays.asList(0);
        
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new HashSet<>());
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) leaves.offer(i);
        }
        
        int remaining = n;
        while (remaining > 2) {
            int leafCount = leaves.size();
            remaining -= leafCount;
            for (int i = 0; i < leafCount; i++) {
                int leaf = leaves.poll();
                int neighbor = graph.get(leaf).iterator().next();
                graph.get(neighbor).remove(leaf);
                if (graph.get(neighbor).size() == 1) leaves.offer(neighbor);
            }
        }
        
        return new ArrayList<>(leaves);
    }
    
    // 5. Sequence Reconstruction
    public boolean sequenceReconstruction(int[] nums, List<List<Integer>> sequences) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        
        for (int num : nums) {
            graph.put(num, new HashSet<>());
            indegree.put(num, 0);
        }
        
        for (List<Integer> seq : sequences) {
            for (int i = 0; i < seq.size() - 1; i++) {
                int from = seq.get(i), to = seq.get(i + 1);
                if (!graph.containsKey(from) || !graph.containsKey(to)) return false;
                if (!graph.get(from).contains(to)) {
                    graph.get(from).add(to);
                    indegree.put(to, indegree.get(to) + 1);
                }
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int num : indegree.keySet()) {
            if (indegree.get(num) == 0) queue.offer(num);
        }
        
        int idx = 0;
        while (!queue.isEmpty()) {
            if (queue.size() > 1) return false;
            int num = queue.poll();
            if (nums[idx++] != num) return false;
            for (int next : graph.get(num)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        
        return idx == nums.length;
    }
    
    // 6. All Ancestors of a Node in DAG
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            indegree[edge[1]]++;
        }
        
        List<Set<Integer>> ancestors = new ArrayList<>();
        for (int i = 0; i < n; i++) ancestors.add(new HashSet<>());
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int child : graph.get(node)) {
                ancestors.get(child).add(node);
                ancestors.get(child).addAll(ancestors.get(node));
                if (--indegree[child] == 0) queue.offer(child);
            }
        }
        
        List<List<Integer>> result = new ArrayList<>();
        for (Set<Integer> set : ancestors) {
            List<Integer> list = new ArrayList<>(set);
            Collections.sort(list);
            result.add(list);
        }
        return result;
    }
    
    // 7. Parallel Courses
    public int minimumSemesters(int n, int[][] relations) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n + 1];
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        
        for (int[] relation : relations) {
            graph.get(relation[0]).add(relation[1]);
            indegree[relation[1]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        int semesters = 0, studied = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            semesters++;
            for (int i = 0; i < size; i++) {
                int course = queue.poll();
                studied++;
                for (int next : graph.get(course)) {
                    if (--indegree[next] == 0) queue.offer(next);
                }
            }
        }
        
        return studied == n ? semesters : -1;
    }
    
    // 8. Sort Items by Groups Respecting Dependencies
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        int groupId = m;
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) group[i] = groupId++;
        }
        
        List<List<Integer>> itemGraph = new ArrayList<>();
        List<List<Integer>> groupGraph = new ArrayList<>();
        int[] itemIndegree = new int[n];
        int[] groupIndegree = new int[groupId];
        
        for (int i = 0; i < n; i++) itemGraph.add(new ArrayList<>());
        for (int i = 0; i < groupId; i++) groupGraph.add(new ArrayList<>());
        
        for (int i = 0; i < n; i++) {
            for (int prev : beforeItems.get(i)) {
                itemGraph.get(prev).add(i);
                itemIndegree[i]++;
                
                if (group[prev] != group[i]) {
                    groupGraph.get(group[prev]).add(group[i]);
                    groupIndegree[group[i]]++;
                }
            }
        }
        
        List<Integer> itemOrder = topologicalSort(itemGraph, itemIndegree);
        List<Integer> groupOrder = topologicalSort(groupGraph, groupIndegree);
        
        if (itemOrder.isEmpty() || groupOrder.isEmpty()) return new int[0];
        
        Map<Integer, List<Integer>> groupToItems = new HashMap<>();
        for (int item : itemOrder) {
            groupToItems.computeIfAbsent(group[item], k -> new ArrayList<>()).add(item);
        }
        
        List<Integer> result = new ArrayList<>();
        for (int g : groupOrder) {
            result.addAll(groupToItems.getOrDefault(g, new ArrayList<>()));
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    private List<Integer> topologicalSort(List<List<Integer>> graph, int[] indegree) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            for (int next : graph.get(node)) {
                if (--indegree[next] == 0) queue.offer(next);
            }
        }
        
        return result.size() == graph.size() ? result : new ArrayList<>();
    }
    
    // 9. Build a Matrix With Conditions
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        List<Integer> rowOrder = topologicalSort(k, rowConditions);
        List<Integer> colOrder = topologicalSort(k, colConditions);
        
        if (rowOrder.isEmpty() || colOrder.isEmpty()) return new int[0][0];
        
        Map<Integer, Integer> rowPos = new HashMap<>();
        Map<Integer, Integer> colPos = new HashMap<>();
        
        for (int i = 0; i < k; i++) {
            rowPos.put(rowOrder.get(i), i);
            colPos.put(colOrder.get(i), i);
        }
        
        int[][] result = new int[k][k];
        for (int i = 1; i <= k; i++) {
            result[rowPos.get(i)][colPos.get(i)] = i;
        }
        
        return result;
    }
    
    private List<Integer> topologicalSort(int k, int[][] conditions) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[k + 1];
        for (int i = 0; i <= k; i++) graph.add(new ArrayList<>());
        
        for (int[] condition : conditions) {
            graph.get(condition[0]).add(condition[1]);
            indegree[condition[1]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= k; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            for (int next : graph.get(node)) {
                if (--indegree[next] == 0) queue.offer(next);
            }
        }
        
        return result.size() == k ? result : new ArrayList<>();
    }
    
    // 10. Find All Possible Recipes
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Set<String> supplySet = new HashSet<>(Arrays.asList(supplies));
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        
        for (int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            indegree.put(recipe, 0);
            for (String ingredient : ingredients.get(i)) {
                if (!supplySet.contains(ingredient)) {
                    graph.computeIfAbsent(ingredient, k -> new ArrayList<>()).add(recipe);
                    indegree.put(recipe, indegree.get(recipe) + 1);
                }
            }
        }
        
        Queue<String> queue = new LinkedList<>();
        for (String recipe : indegree.keySet()) {
            if (indegree.get(recipe) == 0) queue.offer(recipe);
        }
        
        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String recipe = queue.poll();
            result.add(recipe);
            for (String next : graph.getOrDefault(recipe, new ArrayList<>())) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        TopologicalSortExamples examples = new TopologicalSortExamples();
        
        System.out.println("=== Topological Sort Examples ===");
        
        // Test 1: Course Schedule
        System.out.println("Can Finish(2, [[1,0]]): " + examples.canFinish(2, new int[][]{{1,0}}));
        
        // Test 2: Course Schedule II
        System.out.println("Find Order(2, [[1,0]]): " + Arrays.toString(examples.findOrder(2, new int[][]{{1,0}})));
        
        // Test 3: Alien Dictionary
        System.out.println("Alien Order(['wrt','wrf','er','ett','rftt']): " + 
            examples.alienOrder(new String[]{"wrt","wrf","er","ett","rftt"}));
        
        // Test 4: Minimum Height Trees
        System.out.println("Min Height Trees(4, [[1,0],[1,2],[1,3]]): " + 
            examples.findMinHeightTrees(4, new int[][]{{1,0},{1,2},{1,3}}));
        
        // Test 5: Sequence Reconstruction
        List<List<Integer>> sequences = Arrays.asList(Arrays.asList(1,2), Arrays.asList(1,3));
        System.out.println("Sequence Reconstruction([1,2,3], [[1,2],[1,3]]): " + 
            examples.sequenceReconstruction(new int[]{1,2,3}, sequences));
        
        // Test 6: All Ancestors
        System.out.println("Get Ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]): " + 
            examples.getAncestors(8, new int[][]{{0,3},{0,4},{1,3},{2,4},{2,7},{3,5},{3,6},{3,7},{4,6}}));
        
        // Test 7: Parallel Courses
        System.out.println("Minimum Semesters(3, [[1,3],[2,3]]): " + 
            examples.minimumSemesters(3, new int[][]{{1,3},{2,3}}));
        
        // Test 8: Sort Items (simplified test)
        System.out.println("Sort Items: Complex test case");
        
        // Test 9: Build Matrix
        System.out.println("Build Matrix: Complex test case");
        
        // Test 10: Find All Recipes
        String[] recipes = {"bread"};
        List<List<String>> ingredients = Arrays.asList(Arrays.asList("yeast","flour"));
        String[] supplies = {"yeast","flour","corn"};
        System.out.println("Find All Recipes: " + examples.findAllRecipes(recipes, ingredients, supplies));
    }
}