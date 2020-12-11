import java.util.Objects;

public class WordPair implements Comparable<WordPair> {

    public char c;
    public int r;

    public WordPair(char c, int r) {
        this.c = c;
        this.r = r;
    }

    public boolean cEquals(WordPair o) {
        return o.c == c;
    }

    @Override
    public int compareTo(WordPair o) {
        return -Integer.compare(r, o.r);
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
}
