import java.util.LinkedList;
import java.util.Queue;

public class BST {

    Node root;
    int size;

    public BST() {
        this.size = 0;
    }

    public void add(Object itemToAdd) {
        int intToAdd = calcInteger(itemToAdd);
        if (root == null) {
            root = new Node(itemToAdd);
            size ++;
            return;
        }
        add(root, intToAdd);
        size ++;
    }

    public int calcInteger(Object obj) {
        return obj == null ? -1 : obj.hashCode();
    }

    /** Lesson learnt:
     * When adding, when returns a "null" value, the object doesn't have an address. Just do, new something() would
     * instantiate another object in an another address, causing the original object to lose the track! ! ! ! ! ! !
     *
     * Instead, setting it in-place would eliminate this kind of mistakes.
     */
    private void add(Node node, int itemToAdd) {
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

    public Node remove(Object itemToRemove) {
        if (root == null) return null;
        if (root.isBald() && root.equals(itemToRemove)) {
            root = null;
            return ((Node)itemToRemove);
        }

        int intToFind = calcInteger(itemToRemove);
        if (remove(root, intToFind) == null) return null;
        return new Node(itemToRemove);
    }

    private Node remove(Node node, int intToFind) {
        /*
          I learnt from here: it doesn't use recursion! It uses iteration.

           I think in Java I have to do like that, other than that I would need to write redundant code. Because , each
           variable in java stores the memory address of the object, but when it re-assigned with another value, the
           original value lost track, but it has its reference in another object, meaning it won't be recycled.

          https://www.techiedelight.com/deletion-from-bst/
         */

        Node parent = node;

        while (node != null && node.data != intToFind) {
            parent = node;

            if (intToFind < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        // When the key is not found
        if (node == null) return null;

        // Case 1: it has no children
        if (node.isBald()) {
            remove(parent, node);
        } else if (node.left == null && node.right != null || node.left != null && node.right == null) {
            // single children
            if (node.left == null) {
                parent = node.right;
                // Hope it's not collected yet!
                return node;
            } else {
                parent = node.left;
                return node;
            }
        } else {
            // full children

            Node successor = min(node.right);
            int data = successor.data;
            remove(successor);
            int oldData = node.data;
            node.data = data;
            return new Node(oldData);
        }
        return node;
    }

    /**
     * Precondition: node must exist in parent!
     * @param parent parent containing the node to be deleted
     * @param node the node to be deleted
     */
    public void remove(Node parent, Node node) {
        if (parent.left != null && parent.left.equals(node)) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    public Node min(Node node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void preOrderTraversal() {
        if (root == null) return;
        preOrderTraversal(root);
    }

    private void preOrderTraversal(Node node) {
        printNode(node);
        if (node.getLeft() != null)
            preOrderTraversal(node.getLeft());
        if (node.getRight() != null)
            preOrderTraversal(node.getRight());
    }

    public Object find(Object objectToFind) {
        return root == null ? null : find(root, calcInteger(objectToFind));
    }

    private Object find(Node node, int intToFind) {
        if (node == null) {
            return null;
        }

        int data = node.getData();
        if (data == intToFind) {
            return node;
        }
        return find(intToFind < data ? node.getLeft() : node.getRight(), intToFind);
    }

    public void inOrderTraversal() {
        if (root == null) return;
        inOrderTraversal(root);
    }

    public void inOrderTraversal(Node node) {
        if (node.getLeft() != null)
            inOrderTraversal(node.getLeft());

        printNode(node);

        if (node.getRight() != null)
            inOrderTraversal(node.getRight());
    }

    public void postOrderTraversal() {
        if (root == null) return;
        postOrderTraversal(root);
    }

    private void postOrderTraversal(Node node) {
        if (node.getLeft() != null)
            postOrderTraversal(node.getLeft());

        if (node.getRight() != null)
            postOrderTraversal(node.getRight());

        printNode(node);
    }

    public void breadthFirstSearch() {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            System.out.print(n.getData() + "\t");

            if (n.getLeft() != null) {
                queue.add(n.getLeft());
            }

            if (n.getRight() != null) {
                queue.add(n.getRight());
            }
        }
    }

    public void clear() {
        this.root = null;
        size = 0;
    }

    public int size() {
        return this.size;
    }

    private void printNode(Node node) {
        if (node != null) {
            System.out.print(node.getData() + "\t");
        }
    }

    public void display() {
        preOrderTraversal();
    }

    public class Node {
        Node left;
        Node right;
        Integer data;

        public Node() {
            this.left = null;
            this.right = null;
            this.data = 0;
        }

        public Node(Object data) {
            this.data = data.hashCode();
            this.left = null;
            this.right = null;
        }

        public Node(Object data, Node left, Node right) {
            this.data = data.hashCode();
            this.left = left;
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data.hashCode();
        }

        public boolean isBald() {
            return left == null && right == null;
        }

        @Override
        public String toString() {
            return "Node: " + data;
        }
    }
}