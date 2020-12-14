import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class Huffman {
    public String text;

    public Huffman(String text) {
        this.text = text;
    }

    /**
     * Encode the string object
     * @return encoded @text
     */
    public String encode() {
        return encode(text);
    }


    public static String encode(String text) {
        HashMap<Character, Integer> map = new HashMap<>();

        PriorityQueue queue = new PriorityQueue();
        for (char c : text.toCharArray()) {
            Integer r;
            if( (r = map.get(c)) != null) {
                map.put(c, ++r);
            } else {
                map.put(c, 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            queue.add(new WordPair(entry.getKey(), entry.getValue()));
        }

        System.out.println(queue);
        return text;
    }

    public static String decode(String compressedResult) {
        return compressedResult;
    }


    public void breadthFirstSearch() {
        System.out.println(toString());
    }

    /**
     * Breadth First Search result
     */
    @Override
    public String toString() {
        return "";
    }

    static class PriorityQueue extends java.util.PriorityQueue<WordPair> {
        @Override
        public boolean add(WordPair pair) {
            // // Java lambda + final array to exchange result
            // final boolean[] updated = new boolean[1];
            // forEach((nodePair -> {
            //     if (nodePair.cEquals(pair)) {
            //         nodePair.r = Math.max(pair.r, nodePair.r) + 1;
            //         updated[0] = true;
            //     }
            // }));
            //
            // if (!updated[0])
                super.add(pair);
            return true;
        }
    }
}
