# Interview Questions: Directed Graphs

References: https://github.com/bolianchen/coursera-princeton-algorithms/blob/main/Part_II/week1/slides/41UndirectedGraphs.pdf, https://github.com/bolianchen/coursera-princeton-algorithms/blob/main/Part_II/week1/slides/42DirectedGraphs.pdf
Status: In Progress
Tags: Interview, Programming
Type: Course

## ****Question 1****

**Shortest directed cycle.** Given a digraph *G*, design an efficient algorithm to find a directed cycle with the minimum number of edges (or report that the graph is acyclic). The running time of your algorithm should be at most proportional to *V*(*E*+*V*) and use space proportional to *E*+*V*, where *V* is the number of vertices and *E* is the number of edges.

<aside>
💡 *Hint:* run BFS from each vertex.

</aside>

## Answer

function findShortestDirectedCycle(G)

numEdges = inf

shortestCycle = null

for i=1 to V-1

bfs = BFS(G, i)

cycle = bfs.shortestCycle()

if cycle.size() < numEdges

shortestCycle = cycle

numEdges = cycle.size()

endif

next i

endfunction

## ****Question 2****

**Hamiltonian path in a DAG.** Given a directed acyclic graph, design a linear-time algorithm to determine whether it has a *Hamiltonian path* (a simple path that visits every vertex), and if so, find one.

<aside>
💡 Finding a Hamiltonian path for a general graph is a NP-complete problem

</aside>

## Answer

Compute a topological sort and check if there is an edge between each consecutive pair of vertices in the topological order. (refer to page 598 of the textbook)

## ****Question 3****

**Reachable vertex.**

- *DAG*: Design a linear-time algorithm to determine whether a DAG has a vertex that is reachable from every other vertex, and if so, find one.
- *Digraph*: Design a linear-time algorithm to determine whether a digraph has a vertex that is reachable from every other vertex, and if so, find one.

<aside>
💡 *Hint* (DAG): compute the outdegree of each vertex. 
*Hint* (digraph): compute the strong components and look at the kernel DAG (the digraph that results when you contract each strong component to a single vertex).

</aside>

## Answer

- *DAG*:

Find the last vertex in the topological order, check if all the other vertices are connected to it. (not following the hints)

- *Digraph*:

**Brute-force**

Run DFS or BFS on the reversed digraph for each vertex. Check if the other vertices are marked. (runtime V(V+E))

**Linear-time**

Compute the strong components of the Digraph and contract it to a kernel DAG.