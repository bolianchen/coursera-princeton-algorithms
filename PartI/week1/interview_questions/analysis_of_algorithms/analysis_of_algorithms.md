# Analysis of Algorithms
## Question1: 3-SUM in quadratic time.
Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case.
You may assume that you can sort the n integers in time proportional to n^2 or better.

### *my Answer*:
    - sort the input array ==>  O(nlogn)
    - create a map<Integer, Integer> and iterate over the value in the array and put <value, index> in the map ==> O(n)
    - for a sum of a pair of two distinct values in the input array, check if -sum in the map
    - if -sum in the map and the combination of the pair and -sum have not been added to the output, add it.

### *hint*

## Question2: Search in a bitonic array.
An array is bitonic if it is comprised of an increasing sequence of integers followed immediately
by a decreasing sequence of integers. Write a program that, given a bitonic array of n distinct integer values,
determines whether a given integer is in the array.

Standard version: Use ∼3lgn compares in the worst case.
Signing bonus: Use ∼2lgn compares in the worst case
(and prove that no algorithm can guarantee to perform fewer than ∼2lgn compares in the worst case).

### *my Answer*:
    Standard version:
	    - find the index of the peak for the bitonic array => O(lgn)
	    - if the peak equals to the target to search, return true;
	    - otherwise, do binary search on (0, peak-1) and (peak+1, N) => O(2lgn)
    Signing Bonus: 
	refer to the answer proposed by user3017842:
	https://stackoverflow.com/questions/19372930/given-a-bitonic-array-and-element-x-in-the-array-find-the-index-of-x-in-2logn

### *hint*

## Question3 Egg drop.
Suppose that you have an n-story building (with floors 1 through n) and plenty of eggs.
An egg breaks if it is dropped from floor T or higher and does not break otherwise.
Your goal is to devise a strategy to determine the value of T given the following limitations
on the number of eggs and tosses:

Version 0: 1 egg, ≤T tosses.
Version 1:  ∼1lgn eggs and ∼1lgn tosses.
Version 2: ∼lgT eggs and  ∼2lgT tosses.
Version 3: 2 eggs and  ~2*sqrt(n) tosses.
Version 4: 2 eggs and ≤ c*sqrt(T) tosses for some fixed constant c.

### *my Answer*:

Version 0:
    - drop the egg starting from floor1
    - if the egg is not broken, climb up one floor and do step1
    - if the egg is broken, the current floor is T

Version 1:
    - start from the floor (1+n)/2
    - if the egg is broken, go to the middle floor of the lower part
    - if the egg is not broken, go to the middle floor of the upper part

Version 2:
    while the thrown egg not broken:
        - start from the floor i=1
        - if the egg is not broken, multiply 2*i

    it takes around logT tosses to end the previous step

    the final i is slightly larger than T (~T)
    do binary search between floor (i/2, i) with the remaining lgT eggs and lgT tosses
    (T/2 < i/2 < T) ==> the interval length (i-i/2) < T

Version 3:
    while the thrown egg not broken:
        - start from the floor sqrt(n)
        - make increment sqrt(n) once

    1 egg and more than sqrt(n) tosses remain

    restart from the last multiple of sqrt(n) plus 1
    make one increment until the egg broken

Version 4:

    assume c larger than 2 ==> c^2 * T
    while the thrown egg not broken:

Version 4:  1 + 2 + 3 + ... + t ∼ (1/2)*t^2, Aim for c = 2*sqrt(2)

### *hint*
