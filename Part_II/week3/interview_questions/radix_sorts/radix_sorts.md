# Interview Questions: Radix Sorts

## ****Question 1****

**2-sum.** Given an array *a* of *n* 64-bit integers and a target value *T*, determine whether there are two distinct integers *i* and *j* such that *ai*+*aj* = *T*. Your algorithm should run in linear time in the worst case.


>```💡 Hint: sort the array in linear time.```


## Answer

It is reasonable to assume T/2 not contained in a. Moreover, it might be difficult to find hash function to ensure no collisions for the n 64-bit integers.

- create a new array b
- add all the entries in a and the differences between T and the entries in a to b
- do radix sort on b, and check if any adjacent entries are equal.

## ****Question 2****

**American flag sort.** Given an array of *n* objects with integer keys between 0 and *R*−1, design a linear-time algorithm to rearrange them in ascending order. Use extra space at most proportional to R.

>```💡 Hint: first compute the frequency counts for each integer, which tells you where the keys need to go. Then cyclically permute the keys into their proper places.```

## Answer

- Create an array count[R+1] and follow the steps in the radix sort to update the entries of the array to represent where the keys should go
- iterate the original array of n objects
    - put each visited object to where it should go according to the count array; swap the object at the position to the visited entry; update the count array accordingly
    - if the swapped object is at where it should go, and then visit the next entry; otherwise, repeat the step above.

## ****Question 3****

**Cyclic rotations.** Two strings *s* and *t* are *cyclic rotations* of one another if they have the same length and *s* consists of a suffix of *t* followed by a prefix of *t*. For example, "suffixsort" and "sortsuffix" are cyclic rotations.

Given *n* distinct strings, each of length *L*, design an algorithm to determine whether there exists a pair of distinct strings that are cyclic rotations of one another. For example, the following list of *n*=12 strings of length *L*=10 contains exactly one pair of strings ("suffixsort" and "sortsuffix") that are cyclic rotations of one another.

```
algorithms polynomial sortsuffix boyermoore
structures minimumcut suffixsort stackstack
binaryheap digraphdfs stringsort digraphbfs
```

The order of growth of the running time should be *nL^*2 (or better) in the worst case. Assume that the alphabet size *R* is a small constant.

*Signing bonus*. Do it in *NnL* time in the worst case.

>```💡 Hint: define a fingerprint of a string in such a way that two strings are cyclic rotations of one another if and only if they have the same fingerprint. Signing bonus: design an algorithm to find the fingerprint of a string of length L in time proportional to L in the worst case.```


## Answer

- for each string, consider all the combinations by rotating its letters (n strings ⇒ nL strings)
    - ex: abcd ⇒ abcd, bcda, cdab, dabc
- radix sort the nL strings ( runtime ~ nL * L = nL^2)
- check the sorted list; if any adjacent strings are the same, there exists a pair of distinct strings that are cyclic rotations of one another.
