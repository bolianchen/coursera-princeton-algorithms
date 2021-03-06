# Mergesort
## Question1 Merging with smaller auxiliary array.
Suppose that the subarray a[0] to a[n-1] is sorted and the subarray a[n] to a[2*n-1] is sorted.
How can you merge the two subarrays so that a[0] to a[2*n-1] is sorted
using an auxiliary array of length n (instead of 2n)?

### *My Answer*:
    - let the auxiliary array of length n as b[0:n-1]
    - compare a[0:n-1] and a[n:2n-1] backward and fill from the back of b[0:n-1]
    - after b[0:n-1] is used up, start filling from the back of a[0:n-1]
    - finally, copy the contents in b[0:n-1] to a[n:2n-1]

### *Hint*:
Copy only the left half into the auxiliary array

## Question2 Counting inversions.
An inversion in an array a[] is a pair of entries a[i] and a[j] such that i < j but a[i] > a[j].
Given an array, design a linearithmic algorithm to count the number of inversions.

### *My Answer*:
    Since there can be n*(n-1)/2 inversions in the worst case(reversely sorted); therefore,
    to achieve O(nlogn) runtime it is critical to count multiple inversions at each operation.
    - In the procedure of merge sort, inversions are reverseed only during the merge step.
      Inversions are reversed when moving an element of the right sorted array to the
      auxiliary array when it's smaller than that of the left sorted array.
    - in each such movement, increase the inversion count by the number of the remaining
      elements in the left sorted array

Reference: https://www.geeksforgeeks.org/counting-inversions/ 

### *Hint*:

## Question3 Shuffling a linked list.
Given a singly-linked list containing n items, rearrange the items uniformly at random.
Your algorithm should consume a logarithmic (or constant) amount of extra memory
and run in time proportional to nlogn in the worst case. 

### *My Answer*:
    - 

Reference:
    https://stackoverflow.com/questions/12167630/algorithm-for-shuffling-a-linked-list-in-n-log-n-time/27624106#27624106

### *Hint*:
Design a linear-time subroutine that can take two uniformly shuffled linked lists
of sizes n1 and n2 and combined them into a uniformly shuffled linked lists of size n1 + n2.
