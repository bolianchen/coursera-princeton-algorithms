# Interview Questions: Maximum Flow

ContinuingDays: 1
CreatedTime: May 14, 2022 7:18 AM
Source: Course

## **Question 1**

**Fattest path.** Given an edge-weighted digraph and two vertices *s* and *t*, design an *E*log*E* algorithm to find a fattest path from *s* to *t*. The *bottleneck capacity* of a path is the minimum weight of an edge on the path. A *fattest path* is a path such that no other path has a higher bottleneck capacity.

<aside>
💡 *Hint:* design a linear-time subroutine that takes a real-number *T* and determines if there is a path from *s* to *t* of bottleneck capacity greater than or equal to *T*.

</aside>

## Answer

- Initialize a flow network (residual network) using each edge weight as the edge capacity
- Use Dijkstra’s shortest-paths algorithm to choose edges to give the maximum amount of flow that can be pushed through a forward edge or diverted from a backward edge.
- A path from s to t formed by the chosen edges is the fattest path.

## ****Question 2****

**Perfect matchings in k-regular bipartite graphs.** Suppose that there are *n* men and *n* women at a dance and that each man knows exactly *k* women and each woman knows exactly *k* men (and relationships are mutual). Show that it is always possible to arrange a dance so that each man and woman are matched with someone they know.

<aside>
💡 *Hint*: formulate the bipartite matching problem as a maxflow problem; find a (fractional) feasible flow of value *n*; conclude that there is a perfect matching.

</aside>

<aside>
💡 Reference: [Harold Parker’s answer](https://www.coursera.org/learn/algorithms-part2/discussions/forums/fNwXjJvFEeaRew5BAmrkbw/threads/XClK6P3ZEee89Aot9xXjvg) in Course’s Discussion Forums

</aside>

## Answer

Define a flow network (also a digraph) having

- edges from a source node s to all the n men
- edges from the n men to the n women according to their relationships
- edges from all the n women to a terminal node t
- all the edges have capacity 1

It is always possible to arrange a dance iff there exists n flow subgraph

Assumption: the flow network has maximum flow m < n

- According to maxflow-mincut theorem, there exists an st-cut (Cs/Ct) whose capacity equals m.
- Let Cs contain x men and y women, and then m = n - x + y. Since m < n, x > y.
- There would be at least (x - y) * k outflowing edges from men in Cs to women in Ct
- Then we can find augmenting paths to increase the flow, that contradicts the assumption

## ****Question 3****

****Maximum weight closure problem.**** A subset of vertices *S* in a digraph is *closed* if there are no edges pointing from *S* to a vertex outside *S*. Given a digraph with weights (positive or negative) on the *vertices*, find a closed subset of vertices of maximum total weight.

<aside>
💡 *Hint*: formulate as a mincut problem; assign edge (*v*, *w*) a weight of infinity if there is an edge from *v* to *w* in the original digraph.

</aside>

<aside>
💡 Reference: 
[https://en.wikipedia.org/wiki/Closure_problem](https://en.wikipedia.org/wiki/Closure_problem)
[https://www.romsoc.eu/the-closure-problem-explained-in-a-daily-life-application/](https://www.romsoc.eu/the-closure-problem-explained-in-a-daily-life-application/)

</aside>

## Answer

Let V = all the vertices in the whole digraph G. The task is to partition the set of vertices into two sets S and V-S, a closure with the greatest possible weight and its complement.

- Define a new Graph G’, formed by adding a source s and a sink t to G.
- For every vertex v with w(v) > 0, add edges (s, v) with capacity w(v); for every vertex u with w(u) < 0, add edges (u, t) with capacity -w(u)
- Define the capacities of all the edges E in the original digraph G as infinity

It can be demonstrated that there is a bijection between the minimum cuts in this graph and the closures in G. So the task is to solve find the minimum cut in G’. (use Ford-Fulkerson algorithm)