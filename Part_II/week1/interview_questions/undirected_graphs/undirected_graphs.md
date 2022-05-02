# Interview Questions: Undirected Graphs

## Question 1

Nonrecursive depth-first search. Implement depth-first search in an undirected graph without using recursion.

## Answer
Use a stack to maintain the LILO order for the exploration of adjacent vertexes
<pre>
function nonrecurDFS(Graph g, int source)
    int numV = g.V()
    boolean[] marked = new boolean[num_V]
    stack = Stack()
    marked[source] = true
    stack.push(source)
    while stack.size() ≠ 0
        vertex = stack.pop()
        adjVertex = G.adj(vertex)
        for i=0 to adjVertex.size()-1
            if (!marked[adjVertex[i]])
                marked[adjVertex[i]] = true
                stack.push(adjVertex[i])
            endif
        next i
    endwhile
endfunction
</pre>

## ****Question 2****

**Diameter and center of a tree.** Given a connected graph with no cycles

- *Diameter*: design a linear-time algorithm to find the longest simple path in the graph.
- *Center*: design a linear-time algorithm to find a vertex such that its maximum distance from any other vertex is minimized.

💡 Hint (diameter): to compute the diameter, pick a vertex *ss*; run BFS from *ss*; then run BFS again from the vertex that is furthest from *ss*.<br/>
💡 Hint (center): consider vertices on the longest path.

## Answer

### Brute Force Solutions - V(V+E) runtime

- *Diameter*
<pre>
function findDiameter(G)
    maxPathLength=0
    longestPath=null
    for i=0 to G.V()-1
        pathLength, path = bfsVariant(G, i)
        if pathLength > maxPathLength
            longestPath = path
            maxPathLength = pathLength
        endif
    next i
    return longestPath
endfunction
</pre>

- *Center*
<pre>
function findCenter(G)
    maxDistanceAll=inf
    vertex=null
    for i=0 to V-1
        maxDistance = bfsVariant(G, i)
        if maxDistance < maxDistanceAll
            vertex = i
            maxDistance = distance
        endif
    return vertex
endfunction
</pre>

### Linear Time Solutions - (V+E) runtime

- *Diameter*
<pre>
function findDiameter(G)
    bfs = BFS(G, 0)
    v = bfs.furthestVertex()
    bfs2 = BFS(G, v)
    return bfs2.longestPath()
endfunction
</pre>

- *Center*  
find the center of the longest path

## Question 3

Euler cycle. An Euler cycle in a graph is a cycle (not necessarily simple) that uses every edge in the graph exactly one.

- Show that a connected graph has an Euler cycle if and only if every vertex has even degree.
- Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.

💡 Hint: use depth-first search and piece together the cycles you discover.

## Answer

- Show that a connected graph has an Euler cycle if and only if every vertex has even degree.  
(<=) *every vertex has even degree ⇒ an Euler cycle exists* (check reference#1)</br>
(=>) *an Euler cycle exists ⇒ every vertex has even degree*</br>
Given any vertex has odd degree, if we start from it, after visiting all the edges connecting to it once, we would end up not at it.  Any further move would induce a repeated edge, then no Euler cycles can exist.

- Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.
1. check whether or not the degrees of all the vertices are even. if they are, then there exists an Euler cycle. ( runtime V + E)
2. if an Euler path exists, apply Fleury’s Algorithm to find one. (check reference#3)

## Reference

1. [https://www.whitman.edu/mathematics/cgt_online/book/section05.02.html](https://www.whitman.edu/mathematics/cgt_online/book/section05.02.html)
2. [https://math.libretexts.org/Bookshelves/Applied_Mathematics/Book%3A_College_Mathematics_for_Everyday_Life_(Inigo_et_al)/06%3A_Graph_Theory/6.03%3A_Euler_Circuits#:~:text=One example of an Euler,Euler circuits for this graph](https://math.libretexts.org/Bookshelves/Applied_Mathematics/Book%3A_College_Mathematics_for_Everyday_Life_(Inigo_et_al)/06%3A_Graph_Theory/6.03%3A_Euler_Circuits#:~:text=One%20example%20of%20an%20Euler,Euler%20circuits%20for%20this%20graph).
3. [https://www.youtube.com/watch?v=F4BM6fnLl04](https://www.youtube.com/watch?v=F4BM6fnLl04)
