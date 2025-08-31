// 1. Course Schedule
function canFinish(numCourses, prerequisites) {
    const graph = Array.from({ length: numCourses }, () => []);
    const inDegree = new Array(numCourses).fill(0);
    
    for (const [course, prereq] of prerequisites) {
        graph[prereq].push(course);
        inDegree[course]++;
    }
    
    const queue = [];
    for (let i = 0; i < numCourses; i++) {
        if (inDegree[i] === 0) {
            queue.push(i);
        }
    }
    
    let completed = 0;
    while (queue.length > 0) {
        const course = queue.shift();
        completed++;
        
        for (const nextCourse of graph[course]) {
            inDegree[nextCourse]--;
            if (inDegree[nextCourse] === 0) {
                queue.push(nextCourse);
            }
        }
    }
    
    return completed === numCourses;
}

// 2. Course Schedule II
function findOrder(numCourses, prerequisites) {
    const graph = Array.from({ length: numCourses }, () => []);
    const inDegree = new Array(numCourses).fill(0);
    
    for (const [course, prereq] of prerequisites) {
        graph[prereq].push(course);
        inDegree[course]++;
    }
    
    const queue = [];
    for (let i = 0; i < numCourses; i++) {
        if (inDegree[i] === 0) {
            queue.push(i);
        }
    }
    
    const result = [];
    while (queue.length > 0) {
        const course = queue.shift();
        result.push(course);
        
        for (const nextCourse of graph[course]) {
            inDegree[nextCourse]--;
            if (inDegree[nextCourse] === 0) {
                queue.push(nextCourse);
            }
        }
    }
    
    return result.length === numCourses ? result : [];
}

// 3. Alien Dictionary
function alienOrder(words) {
    const graph = new Map();
    const inDegree = new Map();
    
    // Initialize graph
    for (const word of words) {
        for (const char of word) {
            graph.set(char, new Set());
            inDegree.set(char, 0);
        }
    }
    
    // Build graph
    for (let i = 0; i < words.length - 1; i++) {
        const word1 = words[i];
        const word2 = words[i + 1];
        
        if (word1.length > word2.length && word1.startsWith(word2)) {
            return "";
        }
        
        for (let j = 0; j < Math.min(word1.length, word2.length); j++) {
            if (word1[j] !== word2[j]) {
                if (!graph.get(word1[j]).has(word2[j])) {
                    graph.get(word1[j]).add(word2[j]);
                    inDegree.set(word2[j], inDegree.get(word2[j]) + 1);
                }
                break;
            }
        }
    }
    
    // Topological sort
    const queue = [];
    for (const [char, degree] of inDegree) {
        if (degree === 0) {
            queue.push(char);
        }
    }
    
    const result = [];
    while (queue.length > 0) {
        const char = queue.shift();
        result.push(char);
        
        for (const neighbor of graph.get(char)) {
            inDegree.set(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) === 0) {
                queue.push(neighbor);
            }
        }
    }
    
    return result.length === inDegree.size ? result.join('') : "";
}

// 4. Minimum Height Trees
function findMinHeightTrees(n, edges) {
    if (n === 1) return [0];
    
    const graph = Array.from({ length: n }, () => new Set());
    
    for (const [u, v] of edges) {
        graph[u].add(v);
        graph[v].add(u);
    }
    
    let leaves = [];
    for (let i = 0; i < n; i++) {
        if (graph[i].size === 1) {
            leaves.push(i);
        }
    }
    
    let remaining = n;
    while (remaining > 2) {
        remaining -= leaves.length;
        const newLeaves = [];
        
        for (const leaf of leaves) {
            const neighbor = [...graph[leaf]][0];
            graph[neighbor].delete(leaf);
            if (graph[neighbor].size === 1) {
                newLeaves.push(neighbor);
            }
        }
        
        leaves = newLeaves;
    }
    
    return leaves;
}

// 5. Sequence Reconstruction
function sequenceReconstruction(nums, sequences) {
    const graph = new Map();
    const inDegree = new Map();
    
    for (const num of nums) {
        graph.set(num, []);
        inDegree.set(num, 0);
    }
    
    for (const seq of sequences) {
        for (let i = 0; i < seq.length - 1; i++) {
            const from = seq[i];
            const to = seq[i + 1];
            
            if (!nums.includes(from) || !nums.includes(to)) {
                return false;
            }
            
            graph.get(from).push(to);
            inDegree.set(to, inDegree.get(to) + 1);
        }
    }
    
    const queue = [];
    for (const [num, degree] of inDegree) {
        if (degree === 0) {
            queue.push(num);
        }
    }
    
    const result = [];
    while (queue.length > 0) {
        if (queue.length > 1) return false;
        
        const num = queue.shift();
        result.push(num);
        
        for (const neighbor of graph.get(num)) {
            inDegree.set(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) === 0) {
                queue.push(neighbor);
            }
        }
    }
    
    return result.length === nums.length && result.every((val, i) => val === nums[i]);
}

// 6. Parallel Courses
function minimumSemesters(n, relations) {
    const graph = Array.from({ length: n + 1 }, () => []);
    const inDegree = new Array(n + 1).fill(0);
    
    for (const [prev, next] of relations) {
        graph[prev].push(next);
        inDegree[next]++;
    }
    
    const queue = [];
    for (let i = 1; i <= n; i++) {
        if (inDegree[i] === 0) {
            queue.push(i);
        }
    }
    
    let semesters = 0;
    let completed = 0;
    
    while (queue.length > 0) {
        const size = queue.length;
        semesters++;
        
        for (let i = 0; i < size; i++) {
            const course = queue.shift();
            completed++;
            
            for (const nextCourse of graph[course]) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] === 0) {
                    queue.push(nextCourse);
                }
            }
        }
    }
    
    return completed === n ? semesters : -1;
}

// 7. Sort Items by Groups Respecting Dependencies
function sortItems(n, m, group, beforeItems) {
    // Assign unique group IDs to items without groups
    let groupId = m;
    for (let i = 0; i < n; i++) {
        if (group[i] === -1) {
            group[i] = groupId++;
        }
    }
    
    // Build graphs
    const itemGraph = Array.from({ length: n }, () => []);
    const itemInDegree = new Array(n).fill(0);
    const groupGraph = Array.from({ length: groupId }, () => []);
    const groupInDegree = new Array(groupId).fill(0);
    
    for (let i = 0; i < n; i++) {
        for (const prev of beforeItems[i]) {
            itemGraph[prev].push(i);
            itemInDegree[i]++;
            
            if (group[prev] !== group[i]) {
                groupGraph[group[prev]].push(group[i]);
                groupInDegree[group[i]]++;
            }
        }
    }
    
    // Topological sort function
    const topSort = (graph, inDegree) => {
        const queue = [];
        for (let i = 0; i < inDegree.length; i++) {
            if (inDegree[i] === 0) {
                queue.push(i);
            }
        }
        
        const result = [];
        while (queue.length > 0) {
            const node = queue.shift();
            result.push(node);
            
            for (const neighbor of graph[node]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] === 0) {
                    queue.push(neighbor);
                }
            }
        }
        
        return result.length === graph.length ? result : [];
    };
    
    const itemOrder = topSort(itemGraph, [...itemInDegree]);
    const groupOrder = topSort(groupGraph, [...groupInDegree]);
    
    if (itemOrder.length === 0 || groupOrder.length === 0) {
        return [];
    }
    
    // Group items by their groups
    const groupToItems = new Map();
    for (const item of itemOrder) {
        const g = group[item];
        if (!groupToItems.has(g)) {
            groupToItems.set(g, []);
        }
        groupToItems.get(g).push(item);
    }
    
    // Build final result
    const result = [];
    for (const g of groupOrder) {
        if (groupToItems.has(g)) {
            result.push(...groupToItems.get(g));
        }
    }
    
    return result;
}

// 8. All Ancestors of a Node in a Directed Acyclic Graph
function getAncestors(n, edges) {
    const graph = Array.from({ length: n }, () => []);
    const inDegree = new Array(n).fill(0);
    
    for (const [from, to] of edges) {
        graph[from].push(to);
        inDegree[to]++;
    }
    
    const result = Array.from({ length: n }, () => []);
    const queue = [];
    
    for (let i = 0; i < n; i++) {
        if (inDegree[i] === 0) {
            queue.push(i);
        }
    }
    
    while (queue.length > 0) {
        const node = queue.shift();
        
        for (const neighbor of graph[node]) {
            // Add current node and all its ancestors to neighbor's ancestors
            result[neighbor].push(node);
            for (const ancestor of result[node]) {
                if (!result[neighbor].includes(ancestor)) {
                    result[neighbor].push(ancestor);
                }
            }
            
            inDegree[neighbor]--;
            if (inDegree[neighbor] === 0) {
                queue.push(neighbor);
            }
        }
    }
    
    // Sort each result array
    for (let i = 0; i < n; i++) {
        result[i].sort((a, b) => a - b);
    }
    
    return result;
}

// 9. Build a Matrix With Conditions
function buildMatrix(k, rowConditions, colConditions) {
    const topSort = (conditions) => {
        const graph = Array.from({ length: k + 1 }, () => []);
        const inDegree = new Array(k + 1).fill(0);
        
        for (const [above, below] of conditions) {
            graph[above].push(below);
            inDegree[below]++;
        }
        
        const queue = [];
        for (let i = 1; i <= k; i++) {
            if (inDegree[i] === 0) {
                queue.push(i);
            }
        }
        
        const result = [];
        while (queue.length > 0) {
            const node = queue.shift();
            result.push(node);
            
            for (const neighbor of graph[node]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] === 0) {
                    queue.push(neighbor);
                }
            }
        }
        
        return result.length === k ? result : [];
    };
    
    const rowOrder = topSort(rowConditions);
    const colOrder = topSort(colConditions);
    
    if (rowOrder.length === 0 || colOrder.length === 0) {
        return [];
    }
    
    const rowPos = new Map();
    const colPos = new Map();
    
    for (let i = 0; i < k; i++) {
        rowPos.set(rowOrder[i], i);
        colPos.set(colOrder[i], i);
    }
    
    const matrix = Array.from({ length: k }, () => new Array(k).fill(0));
    
    for (let num = 1; num <= k; num++) {
        matrix[rowPos.get(num)][colPos.get(num)] = num;
    }
    
    return matrix;
}

// 10. Find All Possible Recipes from Given Supplies
function findAllRecipes(recipes, ingredients, supplies) {
    const graph = new Map();
    const inDegree = new Map();
    const supplySet = new Set(supplies);
    
    // Initialize
    for (const recipe of recipes) {
        graph.set(recipe, []);
        inDegree.set(recipe, 0);
    }
    
    // Build graph
    for (let i = 0; i < recipes.length; i++) {
        const recipe = recipes[i];
        for (const ingredient of ingredients[i]) {
            if (!supplySet.has(ingredient)) {
                if (!graph.has(ingredient)) {
                    graph.set(ingredient, []);
                    inDegree.set(ingredient, 0);
                }
                graph.get(ingredient).push(recipe);
                inDegree.set(recipe, inDegree.get(recipe) + 1);
            }
        }
    }
    
    // Topological sort
    const queue = [];
    for (const [item, degree] of inDegree) {
        if (degree === 0) {
            queue.push(item);
        }
    }
    
    const result = [];
    while (queue.length > 0) {
        const item = queue.shift();
        
        if (recipes.includes(item)) {
            result.push(item);
        }
        
        if (graph.has(item)) {
            for (const neighbor of graph.get(item)) {
                inDegree.set(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) === 0) {
                    queue.push(neighbor);
                }
            }
        }
    }
    
    return result;
}

// Test cases
console.log("=== Topological Sort Examples ===");

console.log("1. Can Finish(2, [[1,0]]):", canFinish(2, [[1,0]]));
console.log("2. Find Order(4, [[1,0],[2,0],[3,1],[3,2]]):", findOrder(4, [[1,0],[2,0],[3,1],[3,2]]));
console.log("3. Alien Order(['wrt','wrf','er','ett','rftt']):", alienOrder(['wrt','wrf','er','ett','rftt']));
console.log("4. Min Height Trees(4, [[1,0],[1,2],[1,3]]):", findMinHeightTrees(4, [[1,0],[1,2],[1,3]]));
console.log("5. Sequence Reconstruction([1,2,3], [[1,2],[1,3]]):", sequenceReconstruction([1,2,3], [[1,2],[1,3]]));
console.log("6. Minimum Semesters(3, [[1,3],[2,3]]):", minimumSemesters(3, [[1,3],[2,3]]));
console.log("7. Sort Items: Complex test case");
console.log("8. Get Ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]):", getAncestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]));
console.log("9. Build Matrix: Complex test case");
console.log("10. Find All Recipes(['bread'], [['yeast','flour']], ['yeast','flour','corn']):", findAllRecipes(['bread'], [['yeast','flour']], ['yeast','flour','corn']));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        canFinish,
        findOrder,
        alienOrder,
        findMinHeightTrees,
        sequenceReconstruction,
        minimumSemesters,
        sortItems,
        getAncestors,
        buildMatrix,
        findAllRecipes
    };
}