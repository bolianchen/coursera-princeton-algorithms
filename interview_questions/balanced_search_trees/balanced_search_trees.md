# Balanced Search Trees
## Question1 Red–black BST with no extra memory
Describe how to save the memory for storing the color information when implementing a red–black BST.

### *my answer*

### *hint*

## Question2 Document search
Design an algorithm that takes a sequence of n document words and a sequence of m query words
and find the shortest interval in which the m query words appear in the document in the order given.
The length of an interval is the number of words in that interval.

### *my answer*
* Brute-force:
    a) construct an array of Node<QueryWord x, Array indicesAtDocument> ==> O(mn)
    b) iterate sequentially the indices at the document of each query word.
       Valid combinations of m indices are strictly increasing. Return the one with
       the smallest difference between the first index and the last index 
       


### *hint*

## Question3 Generalized queue
Design a generalized queue data type that supports all of the following operations
in logarithmic time (or better) in the worst case.

* Create an empty data structure.
* Append an item to the end of the queue.
* Remove an item from the front of the queue.
* Return the ith item in the queue.

For the above four except for the last specification:
use a red-black BST to store data
use the order of the data added as the key and data itself as value
use an array-based list to maintain the indices of the data currently in the BST

* Remove the ith item from the queue.
==> this item is the most difficult one to meet in logarithmic time

### *my answer*


### *hint*
