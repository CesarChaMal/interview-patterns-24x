"use strict";
// 1. Course Schedule
function canFinish(numCourses, prerequisites) {
    const indegree = new Array(numCourses).fill(0);
    const graph = Array.from({ length: numCourses }, () => []);
    for (const [course, pre] of prerequisites) {
        graph[pre].push(course);
        indegree[course]++;
    }
    const queue = [];
    for (let i = 0; i < numCourses; i++) {
        if (indegree[i] === 0)
            queue.push(i);
    }
    let count = 0;
    while (queue.length) {
        const course = queue.shift();
        count++;
        for (const next of graph[course]) {
            if (--indegree[next] === 0)
                queue.push(next);
        }
    }
    return count === numCourses;
}
// 2. Course Schedule II
function findOrder(numCourses, prerequisites) {
    const indegree = new Array(numCourses).fill(0);
    const graph = Array.from({ length: numCourses }, () => []);
    for (const [course, pre] of prerequisites) {
        graph[pre].push(course);
        indegree[course]++;
    }
    const queue = [];
    for (let i = 0; i < numCourses; i++) {
        if (indegree[i] === 0)
            queue.push(i);
    }
    const result = [];
    while (queue.length) {
        const course = queue.shift();
        result.push(course);
        for (const next of graph[course]) {
            if (--indegree[next] === 0)
                queue.push(next);
        }
    }
    return result.length === numCourses ? result : [];
}
// 3. Alien Dictionary
function alienOrder(words) {
    const graph = new Map();
    const indegree = new Map();
    for (const word of words) {
        for (const char of word) {
            indegree.set(char, 0);
            graph.set(char, new Set());
        }
    }
    for (let i = 0; i < words.length - 1; i++) {
        const w1 = words[i], w2 = words[i + 1];
        if (w1.length > w2.length && w1.startsWith(w2))
            return "";
        for (let j = 0; j < Math.min(w1.length, w2.length); j++) {
            const c1 = w1[j], c2 = w2[j];
            if (c1 !== c2) {
                if (!graph.get(c1).has(c2)) {
                    graph.get(c1).add(c2);
                    indegree.set(c2, indegree.get(c2) + 1);
                }
                break;
            }
        }
    }
    const queue = [];
    for (const [char, deg] of indegree) {
        if (deg === 0)
            queue.push(char);
    }
    const result = [];
    while (queue.length) {
        const char = queue.shift();
        result.push(char);
        for (const next of graph.get(char)) {
            indegree.set(next, indegree.get(next) - 1);
            if (indegree.get(next) === 0)
                queue.push(next);
        }
    }
    return result.length === indegree.size ? result.join('') : "";
}
// 4. Minimum Height Trees
function findMinHeightTrees(n, edges) {
    if (n === 1)
        return [0];
    const graph = new Map();
    for (let i = 0; i < n; i++)
        graph.set(i, new Set());
    for (const [a, b] of edges) {
        graph.get(a).add(b);
        graph.get(b).add(a);
    }
    const leaves = [];
    for (let i = 0; i < n; i++) {
        if (graph.get(i).size === 1)
            leaves.push(i);
    }
    let remaining = n;
    while (remaining > 2) {
        const leafCount = leaves.length;
        remaining -= leafCount;
        for (let i = 0; i < leafCount; i++) {
            const leaf = leaves.shift();
            const neighbor = [...graph.get(leaf)][0];
            graph.get(neighbor).delete(leaf);
            if (graph.get(neighbor).size === 1)
                leaves.push(neighbor);
        }
    }
    return leaves;
}
// 5. Sequence Reconstruction
function sequenceReconstruction(nums, sequences) {
    const graph = new Map();
    const indegree = new Map();
    for (const num of nums) {
        graph.set(num, new Set());
        indegree.set(num, 0);
    }
    for (const seq of sequences) {
        for (let i = 0; i < seq.length - 1; i++) {
            const from = seq[i], to = seq[i + 1];
            if (!graph.has(from) || !graph.has(to))
                return false;
            if (!graph.get(from).has(to)) {
                graph.get(from).add(to);
                indegree.set(to, indegree.get(to) + 1);
            }
        }
    }
    const queue = [];
    for (const [num, deg] of indegree) {
        if (deg === 0)
            queue.push(num);
    }
    let idx = 0;
    while (queue.length) {
        if (queue.length > 1)
            return false;
        const num = queue.shift();
        if (nums[idx++] !== num)
            return false;
        for (const next of graph.get(num)) {
            indegree.set(next, indegree.get(next) - 1);
            if (indegree.get(next) === 0)
                queue.push(next);
        }
    }
    return idx === nums.length;
}
// 6. All Ancestors of a Node in DAG
function getAncestors(n, edges) {
    const graph = Array.from({ length: n }, () => []);
    const indegree = new Array(n).fill(0);
    for (const [a, b] of edges) {
        graph[a].push(b);
        indegree[b]++;
    }
    const ancestors = Array.from({ length: n }, () => new Set());
    const queue = [];
    for (let i = 0; i < n; i++) {
        if (indegree[i] === 0)
            queue.push(i);
    }
    while (queue.length) {
        const node = queue.shift();
        for (const child of graph[node]) {
            ancestors[child].add(node);
            for (const ancestor of ancestors[node]) {
                ancestors[child].add(ancestor);
            }
            if (--indegree[child] === 0)
                queue.push(child);
        }
    }
    return ancestors.map(set => [...set].sort((a, b) => a - b));
}
// 7. Parallel Courses
function minimumSemesters(n, relations) {
    const graph = Array.from({ length: n + 1 }, () => []);
    const indegree = new Array(n + 1).fill(0);
    for (const [pre, course] of relations) {
        graph[pre].push(course);
        indegree[course]++;
    }
    const queue = [];
    for (let i = 1; i <= n; i++) {
        if (indegree[i] === 0)
            queue.push(i);
    }
    let semesters = 0, studied = 0;
    while (queue.length) {
        const size = queue.length;
        semesters++;
        for (let i = 0; i < size; i++) {
            const course = queue.shift();
            studied++;
            for (const next of graph[course]) {
                if (--indegree[next] === 0)
                    queue.push(next);
            }
        }
    }
    return studied === n ? semesters : -1;
}
// 8. Sort Items by Groups Respecting Dependencies
function sortItems(n, m, group, beforeItems) {
    let groupId = m;
    for (let i = 0; i < n; i++) {
        if (group[i] === -1)
            group[i] = groupId++;
    }
    const itemGraph = Array.from({ length: n }, () => []);
    const groupGraph = Array.from({ length: groupId }, () => []);
    const itemIndegree = new Array(n).fill(0);
    const groupIndegree = new Array(groupId).fill(0);
    for (let i = 0; i < n; i++) {
        for (const prev of beforeItems[i]) {
            itemGraph[prev].push(i);
            itemIndegree[i]++;
            if (group[prev] !== group[i]) {
                groupGraph[group[prev]].push(group[i]);
                groupIndegree[group[i]]++;
            }
        }
    }
    function topologicalSort(graph, indegree) {
        const queue = [];
        for (let i = 0; i < indegree.length; i++) {
            if (indegree[i] === 0)
                queue.push(i);
        }
        const result = [];
        while (queue.length) {
            const node = queue.shift();
            result.push(node);
            for (const next of graph[node]) {
                if (--indegree[next] === 0)
                    queue.push(next);
            }
        }
        return result.length === graph.length ? result : [];
    }
    const itemOrder = topologicalSort(itemGraph, itemIndegree);
    const groupOrder = topologicalSort(groupGraph, groupIndegree);
    if (!itemOrder.length || !groupOrder.length)
        return [];
    const groupToItems = new Map();
    for (const item of itemOrder) {
        if (!groupToItems.has(group[item]))
            groupToItems.set(group[item], []);
        groupToItems.get(group[item]).push(item);
    }
    const result = [];
    for (const g of groupOrder) {
        result.push(...(groupToItems.get(g) || []));
    }
    return result;
}
// 9. Build a Matrix With Conditions
function buildMatrix(k, rowConditions, colConditions) {
    function topSort(conditions) {
        const graph = Array.from({ length: k + 1 }, () => []);
        const indegree = new Array(k + 1).fill(0);
        for (const [a, b] of conditions) {
            graph[a].push(b);
            indegree[b]++;
        }
        const queue = [];
        for (let i = 1; i <= k; i++) {
            if (indegree[i] === 0)
                queue.push(i);
        }
        const result = [];
        while (queue.length) {
            const node = queue.shift();
            result.push(node);
            for (const next of graph[node]) {
                if (--indegree[next] === 0)
                    queue.push(next);
            }
        }
        return result.length === k ? result : [];
    }
    const rowOrder = topSort(rowConditions);
    const colOrder = topSort(colConditions);
    if (!rowOrder.length || !colOrder.length)
        return [];
    const rowPos = new Map();
    const colPos = new Map();
    for (let i = 0; i < k; i++) {
        rowPos.set(rowOrder[i], i);
        colPos.set(colOrder[i], i);
    }
    const matrix = Array.from({ length: k }, () => new Array(k).fill(0));
    for (let i = 1; i <= k; i++) {
        matrix[rowPos.get(i)][colPos.get(i)] = i;
    }
    return matrix;
}
// 10. Find All Possible Recipes
function findAllRecipes(recipes, ingredients, supplies) {
    const supplySet = new Set(supplies);
    const graph = new Map();
    const indegree = new Map();
    for (let i = 0; i < recipes.length; i++) {
        const recipe = recipes[i];
        indegree.set(recipe, 0);
        for (const ingredient of ingredients[i]) {
            if (!supplySet.has(ingredient)) {
                if (!graph.has(ingredient))
                    graph.set(ingredient, []);
                graph.get(ingredient).push(recipe);
                indegree.set(recipe, indegree.get(recipe) + 1);
            }
        }
    }
    const queue = [];
    for (const [recipe, deg] of indegree) {
        if (deg === 0)
            queue.push(recipe);
    }
    const result = [];
    while (queue.length) {
        const recipe = queue.shift();
        result.push(recipe);
        for (const next of graph.get(recipe) || []) {
            indegree.set(next, indegree.get(next) - 1);
            if (indegree.get(next) === 0)
                queue.push(next);
        }
    }
    return result;
}
// Test cases
console.log("=== Topological Sort Examples ===");
// Test 1: Course Schedule
console.log(`Can Finish(2, [[1,0]]): ${canFinish(2, [[1, 0]])}`);
// Test 2: Course Schedule II
console.log(`Find Order(2, [[1,0]]): ${findOrder(2, [[1, 0]])}`);
// Test 3: Alien Dictionary
console.log(`Alien Order(['wrt','wrf','er','ett','rftt']): ${alienOrder(['wrt', 'wrf', 'er', 'ett', 'rftt'])}`);
// Test 4: Minimum Height Trees
console.log(`Min Height Trees(4, [[1,0],[1,2],[1,3]]): ${findMinHeightTrees(4, [[1, 0], [1, 2], [1, 3]])}`);
// Test 5: Sequence Reconstruction
console.log(`Sequence Reconstruction([1,2,3], [[1,2],[1,3]]): ${sequenceReconstruction([1, 2, 3], [[1, 2], [1, 3]])}`);
// Test 6: All Ancestors
console.log(`Get Ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]): ${JSON.stringify(getAncestors(8, [[0, 3], [0, 4], [1, 3], [2, 4], [2, 7], [3, 5], [3, 6], [3, 7], [4, 6]]))}`);
// Test 7: Parallel Courses
console.log(`Minimum Semesters(3, [[1,3],[2,3]]): ${minimumSemesters(3, [[1, 3], [2, 3]])}`);
// Test 8: Sort Items
console.log(`Sort Items: Complex test case`);
// Test 9: Build Matrix
console.log(`Build Matrix(3, [[1,2],[3,2]], [[2,1],[3,2]]): ${JSON.stringify(buildMatrix(3, [[1, 2], [3, 2]], [[2, 1], [3, 2]]))}`);
// Test 10: Find All Recipes
console.log(`Find All Recipes: ${findAllRecipes(['bread'], [['yeast', 'flour']], ['yeast', 'flour', 'corn'])}`);
//# sourceMappingURL=topologicalsortExamples.js.map