import edu.princeton.cs.algs4.ResizingArrayStack;

//TODO: raise Exceptions

public class QueueWithTwoStacks<T> {
    ResizingArrayStack<T> deStack;
    ResizingArrayStack<T> enStack;

    public QueueWithTwoStacks() {
        deStack = new ResizingArrayStack<>();
        enStack = new ResizingArrayStack<>();
    }

    public void enqueue(T item) {
        enStack.push(item);
    }
    public T dequeue() {
        if (deStack.isEmpty()) {
            // pop all elements from enStack and push into deStack
            // elements added earlier would be arranged to be popped first in deStack
            while (!enStack.isEmpty()) {
                deStack.push(enStack.pop());
            }
        }
        return deStack.pop();
    }

    public boolean isEmpty() {
        return (deStack.isEmpty() || enStack.isEmpty());
    }

    public static void main(String[] args) {
        QueueWithTwoStacks<Integer> q2s = new QueueWithTwoStacks<>();
        q2s.enqueue(3);
        q2s.enqueue(5);
        q2s.enqueue(7);
        q2s.enqueue(9);
        System.out.println(q2s.dequeue());
    }
}
