# Stacks and Queues
## Question1: Queue with two stacks.
Implement a queue with two stacks so that each queue operations
takes a constant amortized number of stack operations.

### *My Answer*:
    - initialize two stacks (named as enqueueStack and dequeueStack) in QueueWithTwoStacks
    - when enqueue is called, push the element into enqueueStack
    - when dequeue is called
        - if dequeueStack is not empty, call pop on dequeueStack
        - otherwise, pop all elements from enqueueStack and push them to dequeueStack, and call pop on it
    
## Question2: Stack with max.
Create a data structure that efficiently supports the stack operations (push and pop)
and also a return-the-maximum operation.  Assume the elements are real numbers so that you can compare them.

### *My Answer*:

Answer#1
    - implement stack with an array which support push and pop with constant runtime
    - maintain the index to the max in the array
        - when push is called, check if index should be updated to the newly pushed element
        - when pop is called, if the popped index is that of the max, check all the elements
          in the array to find the new max (linear runtime)
Answer#2:
    - according to the hint provided on Coursera: use two stacks,
      one to store all of the items and a second stack to store the maximums.
      (all the operations take constant time)

## Question3: Java generics.
Explain why Java prohibits generic array creation.  

### *My Answer*:
    Each object in Java has a "class" attribute which can be retrieved at runtime.
    The information for an object to know its class was provided when the object
    was created. Codes use generic types denoted like T cannot know what type T is.
    Array (covariant) creation need to know that component type, so array creation of generic types 
    is not allowed.
Reference: 
    - https://www.quora.com/Why-does-Java-prohibit-generic-array-creation 
    - https://stackoverflow.com/questions/18666710/why-are-arrays-covariant-but-generics-are-invariant 

      
