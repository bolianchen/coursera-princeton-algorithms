# Interview Questions: Shortest Paths

## ****Question 1****

**Monotonic shortest path.** Given an edge-weighted digraph *G*, design an *E*log*E* algorithm to find a *monotonic* shortest path from *s* to every other vertex. A path is *monotonic* if the sequence of edge weights along the path are either strictly increasing or strictly decreasing.

>```💡 Hint : Relax edges in ascending order and find a best path; then relax edges in descending order and find a best path.```

## Answer

Let the target vertex as v, that we like to find a path from s to

- Relax edges in ascending order and stop when the distTo(v) is no longer infinite
- Relax edges in descending order. Only update the distTo entry for each vertex once when first encountering it. Stop when the distTo(v) is no longer infinite.

Since a priority queue would be used to store edges and to query them. The number of query is at most the number of edges. Therefore, the runtime is ElogE.

## ****Question 2****

**Second shortest path.** Given an edge-weighted digraph and let *P* be a shortest path from vertex *s* to vertex *t*. Design an *E*log*V* algorithm to find a path (not necessarily simple) other than *P* from *s* to *t* that is as short as possible. Assume all of the edge weights are strictly positive.

>```💡 Hint: compute the shortest path distances from *s* to every vertex and the shortest path distances from every vertex to *t*```

## Answer

Let the edge-weighted digraph as G.

- Initialize a vertex-indexed boolean array and mark the entries of all the vertices on P as true.
- Run Dijkstra on G and s to get the shortest path distances from s to every vertex v named d(s, v). Keep the edgeTo[] for retrieving the path.
- Run Dijkstra on reversed G and t to get the shortest path distances from t to every vertex v, named d(v, t). Keep the edgeTo[] to retrieving the path.
- Calculate d(s, v) + d(v, t) for every vertex v. Find the vertex w with the minimum distance but not in P. Retrieve and combine the shortest paths from s to w and from w to t.

## ****Question 3****

**Shortest path with one skippable edge.** Given an edge-weighted digraph, design an *E*log*V* algorithm to find a shortest path from *s* to *t* where you can change the weight of any one edge to zero. Assume the edge weights are nonnegative.

>```💡 Reference: [https://stackoverflow.com/questions/16291676/shortest-path-with-one-skippable-edge(https://stackoverflow.com/questions/16291676/shortest-path-with-one-skippable-edge)```

>```💡 Hint: compute the shortest path from *s* to every vertex; compute the shortest path from every vertex to *t*; combine.```

## Answer

Since the graph has positive edges, Dijkstra’s algorithm is likely to be applicable. (eager version runtime *E*log*V*) Let the edge-weighted digraph as G.

- Run Dijkstra on G and s to get the shortest path distances from s to every vertex v named d(s, v). Keep the edgeTo[] for retrieving the path.
- Run Dijkstra on reversed G and t to get the shortest path distances from t to every vertex v, named d(v, t). Keep the edgeTo[] to retrieving the path.
- For every edge (v, w), calculate d(s, v) + d(w, t). Find the edge (v’, w’) having the smallest value and combine the shortest paths from s to v’ and from w’ to t
