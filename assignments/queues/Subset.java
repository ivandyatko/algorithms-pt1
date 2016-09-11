import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        String[] s = StdIn.readAllStrings();
        StdRandom.shuffle(s);

        int k = Integer.parseInt(args[0]);

        if (k < 0 || k > s.length) {
            throw new IllegalArgumentException();
        }

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        for (int i = 0; i < k; i++) {
            queue.enqueue(s[i]);
        }

        Iterator<String> iter = queue.iterator();

        for (int i = 0; i < k; i++) {
            StdOut.println(iter.next());
        }
    }
}