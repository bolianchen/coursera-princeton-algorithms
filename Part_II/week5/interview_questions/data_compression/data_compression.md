# Interview Questions: Data Compression

## ****Question 1****

**Ternary Huffman codes.** Generalize the Huffman algorithm to codewords over the ternary alphabet (0, 1, and 2) instead of the binary alphabet. That is, given a bytestream, find a prefix-free ternary code that uses as few trits (0s, 1s, and 2s) as possible. Prove that it yields optimal prefix-free ternary code.

>```💡 *Hint:* Combine smallest 3 probabilities at each step (instead of smallest 2). Don't forget to handle the case when the number of symbols is not of the form 3+2*k* for some integer *k*.```


>```💡 Reference: [https://algs4.cs.princeton.edu/55compression/](https://algs4.cs.princeton.edu/55compression/)```


## Answer

- when there are less or equal to 3 symbol, it is trivial to encode them respectively with distinct codewords
- when there are 3 + 2k (k ≥1) symbols for some integer k, combine smallest 3 probabilities at each step
- when there are 2m (m≥2) symbols for some integer m, add one dummy symbol with 0 probability to 2m as 2m+1 = 3 + 2(m-1) to reduce to the case above.

My intuitive understanding is the cavities must be only at the deepest leaves; otherwise, you can get a code having smaller weighted external path length by replacing cavities with any lower symbols. The proof is similar with Proposition U in the textbook. 

## ****Question 2****

1. Identify an optimal uniquely-decodable code that is neither prefix free nor suffix tree.
2. Identify two optimal prefix-free codes for the same input that have a different distribution of codeword lengths.

## Answer

1. {0011, 011, 11, 1110} or {01, 10, 011, 110}
2. input: abccdd; two optimal prefix-free codes: {a: 000, b: 001, c: 01, d: 1} and {a: 00, b: 01, c: 10, d: 11}

## ****Question 3****

**Move-to-front coding.** Design an algorithm to implement move-to-front encoding so that each operation takes logarithmic time in the worst case. That is, maintain alphabet of symbols in a list. A symbol is encoded as the number of symbols that precede it in the list. After encoding a symbol, move it to the front of the list.

## Answer

- build a symbol table with letters in the alphabet as keys and their orders in a virtual list as values, the orders are initialized by the lexicographical order
- maintain a variable to be tracking the first letter, whose order is 0
- while encoding an input letter
    - append the value of the symbol table corresponding to the input letter to the encoded results
    - swap the values corresponding to the first letter and the input letter
    - update the first letter as the input letter
