# Union Find
## Question1: Social network connectivity.
Given a social network containing n members and a log file containing m timestamps
at which times pairs of members formed friendships, design an algorithm
to determine the earliest time at which all members are connected
(i.e., every member is a friend of a friend of a friend ... of a friend).
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.

The running time of your algorithm should be mlogn or better and use extra space proportional to n.

### *My Answer*: 
    - initialize a weighted union-find data with the n members (indexed as 0, 1, 2, ..., n-1)
    - initialize a int num_networks as n
    - run union once for each timestamp, and reduce num_networks by 1 if union is actually conducted
    - if num_networks equal to 1, return; otherwise, continue the previous steps

## Question2: Union-find with specific canonical element.
Add a method find() to the union-find data type so that find(i) returns the largest element
in the connected component containing i. The operations, union(), connected(), and find()
should all take logarithmic time or better.

For example, if one of the connected components is {1, 2, 6, 9}, then the find() method
should return 9 for each of the four elements in the connected components.

### *My Answer*:
    - initialize an extra array to the weighted quick-union data structure that stores for each root i the largest element in the connected component containing i.
    - after each union, update the largest element in the new root when needed
    - find(i) is to get the largest element of the root of i

## Question3: Successor with delete.
Given a set of nn integers  S={0,1,...,n−1} and a sequence of requests of the following form:
    Remove x from S
    Find the successor of x: the smallest y in S such that y ≥ x.

design a data type so that all operations (except construction)  take logarithmic time or better in the worst case.

### *My Answer*:
    - delete(i) is to conduct union(i, i+1)
    - successor(i) is to find the largest element in the connected component containing i+1 
