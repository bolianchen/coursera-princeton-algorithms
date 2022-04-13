# QuickSort

## Question#1 Nuts and Bolts
A disorganized carpenter has a mixed pile of n nuts and n bolts. The goal is to find the 
corresponding pairs of nuts and bolts. Each nut fits exactly one bolt and each bolt fits
exactly one nut. By fitting a nut and a bolt together, the carpenter can see which one is bigger
(but the carpenter cannot compare two nuts or two bolts directly). Design an algorithm for
the problem that uses at most proportional to nlogn compares (probabilistically).

### *My Answer*:
* randomly choose 1 nut (denoted nut0) out of the pile as the pivot to do partioning for the bolts
* use the bolt fits exactly with nut0 to do partioning for the nuts
* do the above two steps recursively for each partition until each partion has size of 1.

### *Hint*:
Modify the quicksort partitioning part of quicksort
Remark: This [research paper](http://web.cs.ucla.edu/~rafail/PUBLIC/17.pdf) gives an algorithm that runs in nlog^4n time in the worst case

## Question#2 Selection in two sorted arrays.
Given two sorted arrays a[] and b[], of lengths n1 and n2 and an integer 0 ≤ k < n1 + n2,
design an algorithm to find a key of rank k. The order of growth of the worst case running time
of your algorithm should be logn, where n = n1 + n2
* Version 1: n1=n2 (equal length arrays) and k=n/2 (median).
* Version 2: k=n/2 (median).
* Version 3: no restrictions.

### *My Answer*:
Version 1:
* compare a[n/2] and b[n/2]
* if a[n/2] >= b[n/2], throw away the entries of a from n/2 to n-1;
  throw away the entries of b from n/2+1 to n-1
* if a[n/2] < b[n/2], throw away the entries of a from n/2+1 to n-1;
  throw away the entries of b from n/2 to n-1
* return the largest element in the remaining entries of the two arrays

Version 2:  
Similar with Version 1, we only replace n/2's to n1/2 and n2/2 respectively to a[] and b[].
However, according to the parity of n1 and n2, adjustment to n1/2 and n2/2 with increment or decrement
may be needed to ensure half of entries thrown away after one comparison

Version 3:  
Similar with the previous versions, (n1\*k)/(n1+n2) and (n2\*k)/(n1+n2) are used.
Adjustment to them may be needed to ensure (n1+n2)-k are thrown away after several comparisons.
  
### *Hint*:
There are two basic approaches:
* Approach A: Compute the median in a[] and the median in b[]. Recur in a subproblem of roughly half the size.
* Approach B: Design a constant-time algorithm to determine whether a[i] is a key of rank k. Use this subroutine and binary search.  

Dealing with corner cases can be tricky.
  
## Question#3 Decimal dominants.
Given an array with n keys, design an algorithm to find all values that occur more than n/10 times.
The expected running time of your algorithm should be linear.

### *Answer*:

### Hint:
Determine the (n/10)<sup>th</sup> largest key using quickselect and
check if it occurs more than n/10 times.

Alternate solution: use 9 counters.