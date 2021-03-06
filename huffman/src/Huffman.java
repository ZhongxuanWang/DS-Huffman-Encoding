import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * HUFFMAN
 * VERSION 1.1
 * - >1.0 : 8 fewer bits!
 */

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
        boolean first = true;
        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n instanceof NumNode) {
                //
                // I believe you don't have \000 in your input!    
                //
                //  I could've done it differently, which is using plain binary to construct the tree and then put down
                //  the characters accordingly. However, that would increase the size. Thus, I did this instead
                //  considering the algorithm here only takes the keyboard input
                //
                //  However, if you ask me to compress a video footage or anything like that, I would go with that more
                //  reliable approach!
                //
                // Also, please be sure to copy the whole string. the "null" character could be decisive in the string
                if (!first) tree.append('\000'); else first = false;
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

        // \002 STX. START OF TEXT. A transmission control character which precedes a text and which is used to terminate a heading
        String result = tree.toString() + '\002' + bin2text(bin.toString());

        System.out.printf("Original Text: %s\nCompressed Text: %s\nOld Length: %d\nNew Length: %d\nCompression Ratio: "+
                        "%f \n", text, result, text.length(), result.length(), 1.0*result.length() / text.length());
        // printQueue(queue);

        return result;
    }
    /**
     * Put the binary path in the map. 0 is left, 1 is right.
     * @param root root node
     * @param map map to put the binary path
     */
    public static void getPath(Node root, HashMap<Character, String> map, String pathway) {
        if (root.left != null)
            getPath(root.left, map, pathway + '0');

        if (root.right != null)
            getPath(root.right, map, pathway + '1');

        if (root instanceof WordPair)
            map.put(((WordPair) root).c, pathway);

    }

    public static String decode(String compressedResult) throws NotExpectedException {
        // assert compressedResult.charAt(0) == '&' : "The string doesn't adhere to Daniel Huffman rule";

        String[] splitTemp = compressedResult.split(((Character)'\002').toString());
        String tree = splitTemp[0];
        StringBuilder builder = new StringBuilder();
        for (String s : Arrays.copyOfRange(splitTemp, 1, splitTemp.length)) {
            builder.append(s);
        }
        String bin = text2bin(builder.toString());

        Queue<Character> allChars = new LinkedList<>();
        for (char c : tree.toCharArray()) {
            allChars.add(c);
        }

        // Here is to construct a tree by Breadth first search
        Queue<Node> queue = new LinkedList<>();
        Node root = new NumNode(-1);
        queue.add(root);

        while (!allChars.isEmpty()){
            char c1 = allChars.poll();
            Character c2 = null;
            if (!allChars.isEmpty())
                c2 = allChars.poll();


            Node polled = queue.poll();
            if (polled == null) throw new NotExpectedException();
            if (polled.left == null) {
                if (c1 == '\000') {
                    polled.left = new NumNode(-1);
                    queue.add(polled.left);
                } else {
                    polled.left = new WordPair(c1, -1);
                }
            }

            if (polled.right == null && c2 != null) {
                if (c2 == '\000') {
                    polled.right = new NumNode(-1);
                    queue.add(polled.right);
                } else {
                    polled.right = new WordPair(c2, -1);
                }
            }
        }
        Node node = root;
        StringBuilder original = new StringBuilder();
        char[] chars = bin.toCharArray();
        for (int i = 0; i < chars.length; i ++ ) {
            if (node instanceof WordPair) {
                original.append(((WordPair) node).c);
                node = root;
                // if (chars.length - i < 8)
                //     break; // Place to stop
            }

            if (chars[i] == '1')
                node = node.right;

            if (chars[i] == '0')
                node = node.left;
        }
        return original.toString();
    }

    /**
     * View the queue via backtracking (mainly for debug usage)
     * @param queue queue to be printed
     */
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

    public static String bin2text(String in) {
        StringBuilder b = new StringBuilder();
        if (in.length() % 8 != 0) {
            in = in + "0".repeat(8 - in.length() % 8);
        }

        for (int i = 0; i < in.length(); i+=8) {
            b.append(((char)Integer.parseInt(in.substring(i, i+8), 2)));
        }

        return b.toString();
    }

    public static String text2bin(String in) {
        StringBuilder binary = new StringBuilder();

        for (char c : in.toCharArray()) {
            String strTemp = Integer.toBinaryString(c);
            int len = strTemp.length();
            if (len % 8 != 0) strTemp = "0".repeat(8 - len % 8) + strTemp;
            binary.append(strTemp);
        }

        return binary.toString();
    }

    // // TODO - If I have time.
    // // TODO - This approach is definitely more secure, with almost no probability of failing, but the compressed string would be longer
    // public static String b2h(String input) {
    //     // Convert Binary to Hexadecimal to further compress
    //     for (int i = 0; i < input.length(); i++);
    //     return input;
    // }
    //
    // public static String h2b(String input) {
    //     // Convert Hexadecimal to Binary to decompress
    //     return input;
    // }
}

class NotExpectedException extends Exception {
    NotExpectedException() {
        super("This is not expected to happen! I have tried many extraneous cases but lost this. I would look back on this :)");
    }
}