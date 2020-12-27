
public class NumNode extends Node implements Comparable<Node>{
    int num;

    NumNode(int num, Node left, Node right) {
        super(left, right);
        this.num = num;
    }

    NumNode(int num) {
        super(null, null);
        this.num = num;
    }


    @Override
    public int compareTo(Node o) {
        if (o instanceof NumNode) {
            return Integer.compare(num, ((NumNode) o).num);
        } else {
            return Integer.compare(num, ((WordPair) o).r);
        }
    }

    @Override
    public String toString() {
        return num + " - left=" + left.getClass().getName() +
                ",right=" + right.getClass().getName();
    }
}

