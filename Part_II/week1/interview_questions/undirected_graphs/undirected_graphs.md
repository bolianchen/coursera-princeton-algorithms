# Interview Questions: Undirected Graphs

References: https://github.com/bolianchen/coursera-princeton-algorithms/blob/main/Part_II/week1/slides/41UndirectedGraphs.pdf, https://github.com/bolianchen/coursera-princeton-algorithms/blob/main/Part_II/week1/slides/42DirectedGraphs.pdf
Status: In Progress
Tags: Interview, Programming
Type: Course

## Question 1

Nonrecursive depth-first search. Implement depth-first search in an undirected graph without using recursion.

## Answer

Use a stack to maintain the LILO order for the exploration of adjacent vertexes

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

## ****Question 2****

**Diameter and center of a tree.** Given a connected graph with no cycles

- *Diameter*: design a linear-time algorithm to find the longest simple path in the graph.
- *Center*: design a linear-time algorithm to find a vertex such that its maximum distance from any other vertex is minimized.

<aside>
💡 A path is a sequence of vertices connected by edges. 
A simple path is a path with no repeated vertices.

</aside>

<aside>
💡 *Hint (diameter)*: to compute the diameter, pick a vertex s*s*; run BFS from s*s*; then run BFS again from the vertex that is furthest from s*s*.

*Hint (center)*: consider vertices on the longest path.

</aside>

## Answer

### Brute Force Solutions - V(V+E) runtime

- ***Diameter***

function findDiameter(G)

maxPathLength=0

longestPath=null

for i=0 to G.V()-1

pathLength, path = bfsVariant(G, i)

if pathLength > maxPathLength

longestPath = path

maxPathLength = pathLength

endif

return longestPath

endfunction

- ***Center***

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

### Linear Time Solutions - (V+E) runtime

- ***Diameter***

function findDiameter(G)

bfs = BFS(G, 0)

v = bfs.furthestVertex()

bfs2 = BFS(G, v)

return bfs2.longestPath()

endfunction

- ***Center***

find the center of the longest path

## Question 3

Euler cycle. An Euler cycle in a graph is a cycle (not necessarily simple) that uses every edge in the graph exactly one.

- Show that a connected graph has an Euler cycle if and only if every vertex has even degree.
- Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.

“Is it possible to draw a given graph without lifting pencil from the paper and without tracing any of the edges more than once”

<aside>
💡 Euler Path
A path that travels through every edge of a connected graph once and only once and starts and ends at different vertices

</aside>

<aside>
💡 Euler Circuit(Cycle)
An Euler path that starts and ends at the same vertex

</aside>

![Untitled](Interview%20Questions%20Undirected%20Graphs%20282f42e29a764442b4d04a7340f84cab/Untitled.png)

![Untitled](Interview%20Questions%20Undirected%20Graphs%20282f42e29a764442b4d04a7340f84cab/Untitled%201.png)

<aside>
💡 *Hint:*  use depth-first search and piece together the cycles you discover.

</aside>

## Answer

- Show that a connected graph has an Euler cycle if and only if every vertex has even degree.
1. ***every vertex has even degree ⇒ an Euler cycle exists***

check reference#1

1. ***an Euler cycle exists⇒ every vertex has even degree***

Given any vertex has odd degree, if we start from it, after visiting all the edges connecting to it once, we would end up not at it.  Any further move would induce a repeated edge, then no Euler cycles can exist.

- Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.
    - check whether or not the degrees of all the vertices are even. if they are, then there exists an Euler cycle. ( runtime V + E)
    - if an Euler path exists, apply Fleury’s Algorithm to find one. (check reference#3)

## Reference

1. [https://www.whitman.edu/mathematics/cgt_online/book/section05.02.html](https://www.whitman.edu/mathematics/cgt_online/book/section05.02.html)
2. [https://math.libretexts.org/Bookshelves/Applied_Mathematics/Book%3A_College_Mathematics_for_Everyday_Life_(Inigo_et_al)/06%3A_Graph_Theory/6.03%3A_Euler_Circuits#:~:text=One example of an Euler,Euler circuits for this graph](https://math.libretexts.org/Bookshelves/Applied_Mathematics/Book%3A_College_Mathematics_for_Everyday_Life_(Inigo_et_al)/06%3A_Graph_Theory/6.03%3A_Euler_Circuits#:~:text=One%20example%20of%20an%20Euler,Euler%20circuits%20for%20this%20graph).
3. [https://www.youtube.com/watch?v=F4BM6fnLl04](https://www.youtube.com/watch?v=F4BM6fnLl04)