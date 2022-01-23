import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String champion = null;
        // Press Ctrl+D to end the input stream while typing manually
        while (!StdIn.isEmpty()) {
            String inputWord = StdIn.readString();
            if (StdRandom.bernoulli(1.0/i)) {
                champion = inputWord;
            }
            i += 1;
        }
        if (champion != null) {
            System.out.println(champion);
        }
    }
}
