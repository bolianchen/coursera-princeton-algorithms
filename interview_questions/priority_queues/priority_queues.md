# Priority Queues
## Question1 Dynamic median.
Design a data type that supports insert in logarithmic time, find-the-median in constant time,
and remove-the-median in logarithmic time. If the number of keys in the data type is even,
find/remove the lower median.

### *my answer*
* maintain one MaxPQ and one MinPQ respectively named as maxpq and minpq
* insert each new item to either maxpq or minpq in turn
* while maxpq.max() > minpq.min(): do minpq.insert(maxpq.delMax()) and maxpq.insert(minpq.delMin())
* finally, there should be only difference of 1 for the number of keys between maxpq and minpq
* choose between maxpq.max() and minpq.min()

### *hint*
Maintain two binary heaps, one that is max-oriented and one that is min-oriented.

## Question2 Randomized priority queue.
Describe how to add the methods sample() and delRandom() to our binary heap implementation.
The two methods return a key that is chosen uniformly at random among the remaining keys,
with the latter method also removing that key. The sample() method should take constant time;
the delRandom() method should take logarithmic time. Do not worry about resizing the underlying array.

### *my answer*
Sample an random index uniformly between 1 and n, named as s
* for sample(), return the element at index s from the binary heap
* for delSample(), exchange the elements of s and n, make one decrement to n, swim and sink the element at s

### *hint*
Use sink() and swim().

##Question3 Taxicab numbers.
A taxicab number is an integer that can be expressed as the sum of two cubes of positive integers
in two different ways: a^3 + b^3 = c^3 + d^3.
For example, 1729 is the smallest taxicab number: 9^3 + 10^3 = 1^3 + 12^3.
Design an algorithm to find all taxicab numbers with a, b, c, and d less than n.

Version 1: Use time proportional to n^2 log n and space proportional to n^2.
Version 2: Use time proportional to n^2 log n and space proportional to n.

### *my answer*

Brute force:
Check all the combinations of 4 out of n ==> runtime O(n^4) with space O(1) 

Version 1:
* calculate the sums of two cubes for all the combinations of any 2 distinct numbers less than n and save them into an array (time n^2, space n^2)
* sort the array (time n^2 log n)
* iterate over the sorted array, record only the element repeated exactly twice (time n^2)

Version 2:

### *hint*
* Version 1: Form the sums a^3 + b^3 and sort.
* Version 2: Use a min-oriented priority queue with n items.
