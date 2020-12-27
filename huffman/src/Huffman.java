import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.*;

public class Huffman {
    public String text;
    @Deprecated
    private Huffman(String text) {
        this.text = text;
    }
    public static void bfsPrint(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node n = q.poll();
            System.out.println(n);
            if (n.left != null) {
                q.add(n.left);
            }
            if (n.right != null) {
                q.add(n.right);
            }
        }
    }

    /**
     * Encode the string object
     * @return encoded @text
     */

    public static String encode(String text) throws Exception {
        HashMap<Character, Integer> map = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>();
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

        while (queue.size() >= 2) {
            Node n1 = queue.poll();
            Node n2 = queue.poll();
            queue.add(merge(n1, n2));
        }
        Node polled = queue.poll();
        bfsPrint(polled);

        // Export using BFS
        Queue<Node> q = new LinkedList<>();
        q.add(polled);

        StringBuilder tree = new StringBuilder();
        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n instanceof NumNode) {
                //
                // I believe you don't have \032 in your input! 
                //
                //  I could've done it differently, which is using plain binary to construct the tree and then put down
                //  the characters accordingly. However, that would increase the size. Thus, I did this instead
                //  considering the algorithm here only takes the keyboard input
                //
                //  However, if you ask me to compress a video footage or anything like that, I would go with that more
                //  reliable approach!
                //
                tree.append('\032');
            } else {
                tree.append(((WordPair) n).c);
            }
            if (n.left != null)
                q.add(n.left);
            if (n.right != null)
                q.add(n.right);
        }

        HashMap<Character, String> hashMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            hashMap.put(c, "");
        }
        if (polled != null) {
            getPath(polled, hashMap, "");
        } else {
            throw new Exception("Fatal Exception");
        }
        // System.out.println();
        StringBuilder bin = new StringBuilder();
        for (char c : text.toCharArray()) {
            bin.append(hashMap.get(c));
        }
        System.out.println();

        String result = tree.toString() + b2text(bin.toString());

        System.out.printf("Original Text: %s\nCompressed Text: %s\nNew Length: %d\nCompression Ratio: %f \n", text, result, result.length(),
                1.0*result.length() / text.length());
        // printQueue(queue);

        return text;
    }
    /**
     * Put the binary path in the map. 0 is left, 1 is right.
     * @param root root node
     * @param map map to put the binary path
     */
    public static void getPath(Node root, HashMap<Character, String> map, String pathway) {
        if (root.left != null) {
            // if (root instanceof WordPair) {
            //     map.put(((WordPair) root).c, map.get(((WordPair) root).c) + "0");
            //     return;
            // }
            getPath(root.left, map, pathway + '0');
        }
        if (root.right != null) {
            // if (root instanceof WordPair) {
            //     map.put(((WordPair) root).c, map.get(((WordPair) root).c) + "0");
            // }
            getPath(root.right, map, pathway + '1');
        }
        if (root instanceof WordPair) {
            map.put(((WordPair) root).c, pathway);
        }
    }

    public static String decode(String compressedResult) {
        // assert compressedResult.charAt(0) == '&' : "The string doesn't adhere to Daniel Huffman rule";
        for (char c : compressedResult.toCharArray()) {
            if (c == '\032') {

            } else {

            }
        }
        return compressedResult;
    }

    public static void printQueue(PriorityQueue<WordPair> queue) {
        if (queue.isEmpty()) return;
        WordPair polled = queue.poll();
        System.out.println(polled);
        printQueue(queue);
        queue.add(polled);
    }

    public static Node merge(Node n1, Node n2) {
        if (n1.getClass() == n2.getClass() && n1.getClass() == WordPair.class) {
            // Last depth
            return new NumNode(((WordPair) n1).r + ((WordPair) n2).r, n1, n2);
        } else if (n1.getClass() == n2.getClass() && n1.getClass() == NumNode.class) {
            // For Root
            return new NumNode(((NumNode) n1).num + ((NumNode) n2).num, n1, n2);
        } else {
            // n1 and n2 are different classes
            if (n1.getClass() == NumNode.class) {
                return new NumNode(((NumNode) n1).num + ((WordPair) n2).r, n1, n2);
            } else {
                return new NumNode(((NumNode) n2).num + ((WordPair) n1).r, n1, n2);
            }
        }
    }

    public static String b2text(String in) {
        StringBuilder b = new StringBuilder();
        if (in.length() % 8 != 0) {
            for (int i = 0; i < in.length() % 8; i ++)
                in += "0";
        }

        for (int i = 0; i < in.length(); i+=8) {
            b.append(((char)Integer.parseInt(in.substring(i, i+8), 2)));
        }

        return b.toString();
    }

    // TODO - If I have time
    public static String b2h(String input) {
        // Convert Binary to Hexadecimal to further compress
        for (int i = 0; i < input.length(); i++);
        return input;
    }

    public static String h2b(String input) {
        // Convert Hexadecimal to Binary to decompress
        return input;
    }
}
