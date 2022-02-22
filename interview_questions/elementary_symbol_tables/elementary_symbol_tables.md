# Elementary Symbol Tables
## Question1 Java autoboxing and equals().
Consider two double values a and b and their corresponding Double values x and y.

* Find values such that (a == b) is true but x.equals(y) is false.
* Find values such that (a == b) is false but x.equals(y) is true.

### *my answer*
* Find values such that (a == b) is true but x.equals(y) is false.
a = +0.0, b = -0.0 can meet the condition.

* Find values such that (a == b) is false but x.equals(y) is true.
a = Double.NaN, b = Double.NaN can meet the condition. 
Since NaN cannot be compared with any floating type value, we will get false
for all comparison operations except for "!=".

### *hint*
IEEE floating point arithmetic has some peculiar rules for 0.0, −0.0, and NaN.
Java requires that equals() implements an equivalence relation.

### *reference*
https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html#equals-java.lang.Object-
[Understanding Java Autoboxing](https://stackoverflow.com/questions/36052241/understanding-double-autoboxing)
[IEEE Standard for Floating-Point Arithmetic](https://en.wikipedia.org/wiki/IEEE_754)

## Question2 Check if a binary tree is a BST.
Given a binary tree where each Node contains a key, determine whether it is a binary search tree.
Use extra space proportional to the height of the tree.

### *my answer*

The results of in-order traversal are ascendingly sorted entries <=> BST ?



Do in-order traversal recursively on the binary tree, return false once an element strictly larger than
its previous element found. The extra space is the function call stack for recursion, which is 
proportional to the tree height.

### *hint*
design a recursive function isBST(Node x, Key min, Key max) that determines
whether x is the root of a binary search tree with all keys between min and max.

## Question3 Inorder traversal with constant extra space.
Design an algorithm to perform an inorder traversal of a binary search tree using
only a constant amount of extra space.

### *my answer*
?

### *hint*
you may modify the BST during the traversal provided you restore it upon completion

## Question4 Web tracking.
Suppose that you are tracking n web sites and m users and you want to support the following API:
* User visits a website.
* How many times has a given user visited a given site?
What data structure or data structures would you use?

### *my answer*
Use symbol table with user as Key and another symbol table as Value.
The Key and Value of the inside symbol table are website and visits. ST<User, ST<Website, Visits>>
Use binary search tree as the data structure for both symbol tables.
* User visits a website => time O(lg n + lg m)
* How many times has a given user visited a given site => O(lg n + lg m)

### *hint*
maintain a symbol table of symbol tables
