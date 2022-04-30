# Interview Questions: Hash Tables

## ****Question 1****

****4-SUM.**** Given an array  *a*[ ] of *n* integers, the 4-SUM problem is to determine if there exist distinct indices *i*, *j*, *k*, and *l* such that *a*[*i*]+*a*[*j*]=*a*[*k*]+*a*[*l*]. Design an algorithm for the 4-SUM problem that takes time proportional to $n^2$ (under suitable technical assumptions).

### **Brute Force Solution**

function bruteForce(a[])

for i=0 to n-1

    for j=i+1 to n-1

        for k=j+1 to n-1

            for l=k+1 to n-1

                if a[i]+a[j]=a[k]+a[l] then return true

                endif

            next l

        next k

    next j

next i

return false

endfunction

Without loss of generality, we can assume there are no negative integers. We can always add a large value to ensure non-negativity.

### n^2 Solution

function effectiveFourSum(a[])

asendSort(a[])  // sort the array in ascending order

s=HashSet

for i=0 to n-1

for j=i to n-1

if s.contains(a[i] + a[j])

return true;

else

s.add(a[i] + a[j])

endif

next j

next i

## Question 2

**Hashing with wrong hashCode() or equals()**. Suppose that you implement a data type OlympicAthlete for use in a java.util.HashMap.

- Describe what happens if you override hashCode() but not equals().
- Describe what happens if you override equals() but not hashCode().
- Describe what happens if you override hashCode() but implement public boolean equals(OlympicAthlete that) instead of public boolean equals(Object that).

A basic requirement for clients to be able to use hashCode() for symbol tables. Given two data type references a and b, if a.equals(b) is true, then a.hashCode() must have the same numerical value as b.hashCode().

- Overriding hashCode() but not equals()
    
    a.equals(b) is true only when a and b refer to the same object. Instances having the same instance variables but referring to different objects would be treated as distinct keys by the hash based implementation of symbol table.
    
- Overriding equals() but not hashCode()
    
    a.hashCode() may not be the same as b.hashCode() when a.equals(b) returns true
    
- Overriding hashCode() but implementing public boolean equals(OlympicAthlete that)
    
    Hash based implementation of symbol table only recognize public boolean equals(Object that). Therefore, the equality remains whether two references referring the same object.
