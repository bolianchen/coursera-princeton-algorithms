import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {
       int k = Integer.parseInt(args[0]);
       RandomizedQueue<String> rq = new RandomizedQueue<>();
       while (!StdIn.isEmpty()) {
           String input = StdIn.readString();
           rq.enqueue(input);
       }
       for (int i = 0; i < k; i += 1) {
           System.out.println(rq.dequeue());
       }
   }
}

