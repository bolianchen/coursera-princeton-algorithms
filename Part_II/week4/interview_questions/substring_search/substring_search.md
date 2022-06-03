# Interview Questions: Substring Search

## **Question 1**

**Cyclic rotation of a string.** A string *s* is a cyclic rotation of a string *t* if *s* and *t* have the same length and *s* consists of a suffix of *t* followed by a prefix of *t*. For example, "winterbreak" is a cyclic rotation of "breakwinter" (and vice versa). Design a linear-time algorithm to determine whether one string is a cyclic rotation of another.

## Answer

- make the first string *s* a ring
- use the second string *t* as the pattern to run either Knuth-Morris-Pratt or Boyer-Moore or Rabin-Karp (iterations up to s.length)

## ****Question 2****

**Tandem repeat.** A tandem repeat of a base string *b* within a string *s* is a substring of *s* consisting of at least one consecutive copy of the base string *b*. Given *b* and *s*, design an algorithm to find a tandem repeat of *b* within *s* of maximum length. Your algorithm should run in time proportional to *M* + *N*, where *M* is length of *b* and *N* is the length *s*.

For example, if *s* is "**abcab**cab**abcab**a" and *b* is "abcab", then "abcababcab" is the tandem substring of maximum length (2 copies).

## Answer

- Use either Knuth-Morris-Pratt or Boyer-Moore or Rabin-Karp to search the base string *b* within the string *s*.
- Search again the base string from s.substring(last index of previous match)

## ****Question 3****

**Longest palindromic substring.** Given a string *s*, find the longest substring that is a palindrome in expected linearithmic time.

*Signing bonus*: Do it in linear time in the worst case.


>```💡 Hint:use given a parameter L, find all palindromic substrings of length exactly L in linear time using a Karp-Rabin strategy.```     
>```💡Hint (signing bonus): To do it in linear time in the worst case, use *Manacher's algorithm* or suffix trees.```


## Answer
