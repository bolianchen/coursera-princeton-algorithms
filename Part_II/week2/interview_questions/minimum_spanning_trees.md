# Interview Questions: Minimum Spanning Trees

## ****Question 1****

**Bottleneck minimum spanning tree.** Given a connected edge-weighted graph, design an efficient algorithm to find a *minimum bottleneck spanning tree*(MBST). The bottleneck capacity of a spanning tree is the weights of its largest edge. A minimum bottleneck spanning tree is a spanning tree of minimum bottleneck capacity.

<pre>
💡 Hint: prove that an MST is a minimum bottleneck spanning tree.
</pre>
<pre>
Proof: 
Assume that there existed an MST T of a graph G. Let T’ be an MBST of G.
Let e be the bottleneck edge of T. Consider the cut in the tree defined by e.
By the cut property every other edge crossing the cut necessarily
has weight at least that of e. Now look at the edges of T’
which cross the cut. By the above statement they must too all have
weight at least that of c(e). But then if b(T) is the bottleneck cost of a tree T, b(T) = c(e) ≤ cost
of any edge of T’ crossing the cut ≤ b(T’). Since T’ has minimum bottleneck
cost among trees, so b(T’) ≤ b(T). Therefore, b(T) = b(T’), 
i.e. the MST is an MBST.*
</pre>

## ****Answer****

### My original solution

- Add all the edges to a MinPQ
- Remove edges from the MinPQ to form a tree until all the vertices are connected.
- Use the edges in the MinPQ as a new graph and run Prim's or Kruskal’s algorithm.

### The solution after checking the hint

- Run Kruskal’s or Prim’s algorithm to find a MST. It is necessarily a MBST.

<pre>
💡 Extra challenge: Compute a minimum bottleneck spanning tree
in linear time in the worst case. Assume that you can compute
the median of n keys in linear time in the worst case.
</pre>

## ****Question 2****

**Is an edge in a MST.** Given an edge-weighted graph *G* and an edge *e*, design a linear-time algorithm to determine whether *e* appears in some MST of *G*.

*Note:* Since your algorithm must take linear time in the worst case, you cannot afford to compute the MST itself.

<pre>
💡 Hint: consider the subgraph G′ of G containing only those edges
whose weight is strictly less than that of e.
</pre>

## ****Answer****

Assume the two vertices connected by e are v and w. The key point of a MST is to connect v and w to the rest vertices with the lowest cost.

- If The graph only has two vertices, one edge is needed to make the connection
    - e should be the cheapest edge to be in a MST
- If the graph contains more than two vertices, two edges are needed to make the connection
    - e should be one of the 2 smallest edges among all the adjacent edges of v and w.



 

## ****Question 3****

**Minimum-weight feedback edge set.** A *feedback edge set* of a graph is a subset of edges that contains at least one edge from every cycle in the graph. If the edges of a feedback edge set are removed, the resulting graph is acyclic. Given an edge-weighted graph, design an efficient algorithm to find a feedback edge set of minimum weight. Assume the edge weights are positive.

<pre>
💡 Hint: complement of an MST.
</pre>

## ****Answer****

1. Find a MST on a new graph with the same edges but with weights being multiplied by -1
2. the set of the edges not in the MST is a minimum-weight feedback edge set.