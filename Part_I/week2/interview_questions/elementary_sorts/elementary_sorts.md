# Elementary Sorts
## Question1: Intersection of two sets.
Given two arrays a[] and b[], each containing n distinct 2D points in the plane, design a
subquadratic algorithm to count the number of points that are contained both in array a[] and array b[].

### *My Answer*:
    - sort a[] and b[] in ascending order ==> 2 * O(nlgn)
    - iterate over a[] and b[] with two separate indices i, j, compare a[i] and b[j]
      make increment to both indices of the arrays when both entries are the same,
      make increment to the index of either array whose entry is smaller ==> 2 * O(n)

## Question2: Permutation.
Given two integer arrays of size n, design a subquadratic algorithm to determine
whether one is a permutation of the other. That is, do they contain exactly the same entries but,
possibly, in a different order.

### *My Answer*:
    - sort two arrays => 2 * O(nlgn)
    - iterate the two arrays with one single index, compare every entry => O(n) 

## Question3: Dutch national flag.
Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color.
The allowed operations are:
    - swap(i,j): swap the pebble in bucket i with the pebble in bucket j.
    - color(i): determine the color of the pebble in bucket i.

The performance requirements are as follows:
    - At most n calls to color().
    - At most n calls to swap().
    - Constant extra space.

### *My Answer*:
    - maintain an index r to swap next found red flag to, initialize r as 0
    - maintain an index b to swap next found blue flag to, initialize b as n-1
    - iterate the buckets with an index i from the start (i=0)
        while i < b, repeat the following steps
	    (1) call color(i)
	    (2) if color(i) is red, swap(i, r), advance both i and r with one increment
	    (3) if color(i) is blue, swap(i, b) and make one decrement to b
	    (4) if color(i) is white, advance i with one increment
    requirement check:
    - each swap would move one flag to its correct position, since no need for more than
      n such movements to arrange n flags, at most n calls to swap()
    - never call color() twice on the same flag, so at most n calls to color
    - only two additional indices r and b are used
    
