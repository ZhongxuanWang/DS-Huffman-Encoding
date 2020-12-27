import java.util.Objects;

public class WordPair extends Node implements Comparable<Node>{

    public char c;
    public int r;

    public WordPair(char c, int r, Node left, Node right) {
        super(left, right);
        this.c = c;
        this.r = r;
    }

    public WordPair(char c, int r) {
        super(null, null);
        this.c = c;
        this.r = r;
    }

    public boolean cEquals(WordPair o) {
        return o.c == c;
    }

    @Override
    public int compareTo(Node o) {
        if (o instanceof WordPair) {
            return Integer.compare(r, ((WordPair) o).r);
        } else { // NumNode
            return Integer.compare(r, ((NumNode) o).num);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordPair)) return false;
        WordPair pair = (WordPair) o;
        return c == pair.c && r == pair.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, r);
    }

    @Override
    public String toString() {
        return c + ":" + r;
    }
}
