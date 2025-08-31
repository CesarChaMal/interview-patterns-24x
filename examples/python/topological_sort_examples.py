from collections import defaultdict, deque

# 1. Can Finish
def can_finish(num_courses, prerequisites):
    indegree = [0] * num_courses
    graph = defaultdict(list)
    
    for course, pre in prerequisites:
        graph[pre].append(course)
        indegree[course] += 1
    
    queue = deque([i for i in range(num_courses) if indegree[i] == 0])
    count = 0
    
    while queue:
        course = queue.popleft()
        count += 1
        for next_course in graph[course]:
            indegree[next_course] -= 1
            if indegree[next_course] == 0:
                queue.append(next_course)
    
    return count == num_courses

# 2. Find Order
def find_order(num_courses, prerequisites):
    indegree = [0] * num_courses
    graph = defaultdict(list)
    
    for course, pre in prerequisites:
        graph[pre].append(course)
        indegree[course] += 1
    
    queue = deque([i for i in range(num_courses) if indegree[i] == 0])
    result = []
    
    while queue:
        course = queue.popleft()
        result.append(course)
        for next_course in graph[course]:
            indegree[next_course] -= 1
            if indegree[next_course] == 0:
                queue.append(next_course)
    
    return result if len(result) == num_courses else []

# 3. Alien Order
def alien_order(words):
    graph = defaultdict(set)
    indegree = {c: 0 for word in words for c in word}
    
    for i in range(len(words) - 1):
        w1, w2 = words[i], words[i + 1]
        if len(w1) > len(w2) and w1.startswith(w2):
            return ""
        
        for j in range(min(len(w1), len(w2))):
            c1, c2 = w1[j], w2[j]
            if c1 != c2:
                if c2 not in graph[c1]:
                    graph[c1].add(c2)
                    indegree[c2] += 1
                break
    
    queue = deque([c for c in indegree if indegree[c] == 0])
    result = []
    
    while queue:
        c = queue.popleft()
        result.append(c)
        for next_c in graph[c]:
            indegree[next_c] -= 1
            if indegree[next_c] == 0:
                queue.append(next_c)
    
    return ''.join(result) if len(result) == len(indegree) else ""

# 4. Find Min Height Trees
def find_min_height_trees(n, edges):
    if n == 1:
        return [0]
    
    graph = defaultdict(set)
    for a, b in edges:
        graph[a].add(b)
        graph[b].add(a)
    
    leaves = deque([i for i in range(n) if len(graph[i]) == 1])
    remaining = n
    
    while remaining > 2:
        leaf_count = len(leaves)
        remaining -= leaf_count
        for _ in range(leaf_count):
            leaf = leaves.popleft()
            neighbor = graph[leaf].pop()
            graph[neighbor].remove(leaf)
            if len(graph[neighbor]) == 1:
                leaves.append(neighbor)
    
    return list(leaves)

# 5. Sequence Reconstruction
def sequence_reconstruction(nums, sequences):
    graph = defaultdict(set)
    indegree = {num: 0 for num in nums}
    
    for seq in sequences:
        for i in range(len(seq) - 1):
            from_num, to_num = seq[i], seq[i + 1]
            if from_num not in indegree or to_num not in indegree:
                return False
            if to_num not in graph[from_num]:
                graph[from_num].add(to_num)
                indegree[to_num] += 1
    
    queue = deque([num for num in indegree if indegree[num] == 0])
    idx = 0
    
    while queue:
        if len(queue) > 1:
            return False
        num = queue.popleft()
        if nums[idx] != num:
            return False
        idx += 1
        for next_num in graph[num]:
            indegree[next_num] -= 1
            if indegree[next_num] == 0:
                queue.append(next_num)
    
    return idx == len(nums)

# 6. Get Ancestors
def get_ancestors(n, edges):
    graph = defaultdict(list)
    indegree = [0] * n
    
    for a, b in edges:
        graph[a].append(b)
        indegree[b] += 1
    
    ancestors = [set() for _ in range(n)]
    queue = deque([i for i in range(n) if indegree[i] == 0])
    
    while queue:
        node = queue.popleft()
        for child in graph[node]:
            ancestors[child].add(node)
            ancestors[child].update(ancestors[node])
            indegree[child] -= 1
            if indegree[child] == 0:
                queue.append(child)
    
    return [sorted(list(ancestor_set)) for ancestor_set in ancestors]

# 7. Minimum Semesters
def minimum_semesters(n, relations):
    graph = defaultdict(list)
    indegree = [0] * (n + 1)
    
    for pre, course in relations:
        graph[pre].append(course)
        indegree[course] += 1
    
    queue = deque([i for i in range(1, n + 1) if indegree[i] == 0])
    semesters = 0
    studied = 0
    
    while queue:
        size = len(queue)
        semesters += 1
        for _ in range(size):
            course = queue.popleft()
            studied += 1
            for next_course in graph[course]:
                indegree[next_course] -= 1
                if indegree[next_course] == 0:
                    queue.append(next_course)
    
    return semesters if studied == n else -1

# 8. Sort Items
def sort_items(n, m, group, before_items):
    group_id = m
    for i in range(n):
        if group[i] == -1:
            group[i] = group_id
            group_id += 1
    
    item_graph = defaultdict(list)
    group_graph = defaultdict(list)
    item_indegree = [0] * n
    group_indegree = [0] * group_id
    
    for i in range(n):
        for prev in before_items[i]:
            item_graph[prev].append(i)
            item_indegree[i] += 1
            
            if group[prev] != group[i]:
                group_graph[group[prev]].append(group[i])
                group_indegree[group[i]] += 1
    
    def topological_sort(graph, indegree):
        queue = deque([i for i in range(len(indegree)) if indegree[i] == 0])
        result = []
        while queue:
            node = queue.popleft()
            result.append(node)
            for next_node in graph[node]:
                indegree[next_node] -= 1
                if indegree[next_node] == 0:
                    queue.append(next_node)
        return result if len(result) == len(indegree) else []
    
    item_order = topological_sort(item_graph, item_indegree)
    group_order = topological_sort(group_graph, group_indegree)
    
    if not item_order or not group_order:
        return []
    
    group_to_items = defaultdict(list)
    for item in item_order:
        group_to_items[group[item]].append(item)
    
    result = []
    for g in group_order:
        result.extend(group_to_items[g])
    
    return result

# 9. Build Matrix
def build_matrix(k, row_conditions, col_conditions):
    def topological_sort(conditions):
        graph = defaultdict(list)
        indegree = [0] * (k + 1)
        
        for a, b in conditions:
            graph[a].append(b)
            indegree[b] += 1
        
        queue = deque([i for i in range(1, k + 1) if indegree[i] == 0])
        result = []
        
        while queue:
            node = queue.popleft()
            result.append(node)
            for next_node in graph[node]:
                indegree[next_node] -= 1
                if indegree[next_node] == 0:
                    queue.append(next_node)
        
        return result if len(result) == k else []
    
    row_order = topological_sort(row_conditions)
    col_order = topological_sort(col_conditions)
    
    if not row_order or not col_order:
        return []
    
    row_pos = {num: i for i, num in enumerate(row_order)}
    col_pos = {num: i for i, num in enumerate(col_order)}
    
    result = [[0] * k for _ in range(k)]
    for i in range(1, k + 1):
        result[row_pos[i]][col_pos[i]] = i
    
    return result

# 10. Find All Recipes
def find_all_recipes(recipes, ingredients, supplies):
    supply_set = set(supplies)
    graph = defaultdict(list)
    indegree = defaultdict(int)
    
    for i, recipe in enumerate(recipes):
        for ingredient in ingredients[i]:
            if ingredient not in supply_set:
                graph[ingredient].append(recipe)
                indegree[recipe] += 1
    
    queue = deque([recipe for recipe in recipes if indegree[recipe] == 0])
    result = []
    
    while queue:
        recipe = queue.popleft()
        result.append(recipe)
        for next_recipe in graph[recipe]:
            indegree[next_recipe] -= 1
            if indegree[next_recipe] == 0:
                queue.append(next_recipe)
    
    return result

def run_examples():
    print("=== Topological Sort Examples ===")
    
    # Test 1: Course Schedule
    print(f"Can Finish(2, [[1,0]]): {can_finish(2, [[1,0]])}")
    
    # Test 2: Course Schedule II
    print(f"Find Order(2, [[1,0]]): {find_order(2, [[1,0]])}")
    
    # Test 3: Alien Dictionary
    print(f"Alien Order(['wrt','wrf','er','ett','rftt']): {alien_order(['wrt','wrf','er','ett','rftt'])}")
    
    # Test 4: Minimum Height Trees
    print(f"Min Height Trees(4, [[1,0],[1,2],[1,3]]): {find_min_height_trees(4, [[1,0],[1,2],[1,3]])}")
    
    # Test 5: Sequence Reconstruction
    print(f"Sequence Reconstruction([1,2,3], [[1,2],[1,3]]): {sequence_reconstruction([1,2,3], [[1,2],[1,3]])}")
    
    # Test 6: All Ancestors
    print(f"Get Ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]): {get_ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]])}")
    
    # Test 7: Parallel Courses
    print(f"Minimum Semesters(3, [[1,3],[2,3]]): {minimum_semesters(3, [[1,3],[2,3]])}")
    
    # Test 8: Sort Items
    print(f"Sort Items: Complex test case")
    
    # Test 9: Build Matrix
    print(f"Build Matrix: Complex test case")
    
    # Test 10: Find All Recipes
    print(f"Find All Recipes: {find_all_recipes(['bread'], [['yeast','flour']], ['yeast','flour','corn'])}")

if __name__ == "__main__":
    run_examples()