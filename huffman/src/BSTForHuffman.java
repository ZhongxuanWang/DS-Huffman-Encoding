import java.util.LinkedList;
import java.util.Queue;

public class BSTForHuffman {

    Node root;
    int size;

    public BSTForHuffman() {
        this.size = 0;
    }

    public void addBase(WordPair pair1, WordPair pair2) {

    }

    public void add(WordPair pair) {
        if (root == null) {
            System.out.println("WARNING! You should invoke addBase method first.");
            return;
        }
        add(root, pair);
        size ++;
    }

    private void add(Node node, WordPair itemToAdd) {
        if (node.getData() == itemToAdd)
            return;
        if (itemToAdd < node.getData()) {
            if (node.getLeft() == null) {
                node.setLeft(new Node(itemToAdd));
                return;
            }
            add(node.getLeft(), itemToAdd);
        }
        if (itemToAdd > node.getData()) {
            if (node.getRight() == null) {
                node.setRight(new Node(itemToAdd));
                return;
            }
            add(node.getRight(), itemToAdd);
        }
    }

    public void breadthFirstSearch() {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            System.out.println(n.toString());

            if (n.left != null) {
                queue.add(n.left);
            }

            if (n.right != null) {
                queue.add(n.right);
            }
        }
    }

    public void mergeTwoTrees(Node r1, Node r2) {

    }

    static class Node {
        int sum = 0;
        Node left, right;
        WordPair data;

        Node (int sum) {
            this.sum = sum;
            left = null;
            right = null;
        }
        Node (Node left, Node right) {
            this.sum = left.sum + right.sum;
            this.left = left;
            this.right = right;
            this.data = null;
        }

        Node(WordPair data) {
            this.data = data;
            this.sum = data.r;
            this.left = null;
            this.right = null;
        }

        boolean isWordNode() {
            return data != null && left == null && right == null;
        }

        @Override
        public String toString() {
            String nodeType;
            if (isWordNode()) {
                nodeType = "WordNode{";
            } else {
                nodeType = "SumNode{";
            }
            return nodeType +
                    "data=" + data +
                    ",hasLeft=" + (left!=null) +
                    ",hasRight=" + (right!=null) +
                    '}';
        }
    }
}