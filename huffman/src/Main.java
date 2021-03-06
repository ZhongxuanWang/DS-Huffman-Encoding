
public class Main {

    /**
     * I really think my Algorithm has the best performance (in regarding to compression ratio) ... lol
     *
     *  To optimize the compression, instead building a tree with bin and then give real characters, I used real
     *  characters in the building block of the tree, and used \000 which is   to act as a NumNode, which is
     *  insignificant in the compressed result. However, in your input you cannot use   or , which acts as the
     *  separator of the tree and the pathway.
     *
     *  However, it gives a much better compression result, which is my top priority.
     *
     *  In the updated algorithm, I optimized the compression, and makes the compressed string fewer bits less.
     *
     *
     */

    public static void main(String[] args) throws Exception {
        String encoding = Huffman.encode("qwtuyqweryewqtopqperioiytutyuytiuiyuqweoiroqppqowpeqruyqweouriuoqweituyutryrtyuywrterutuy");
        System.out.println(" - ".repeat(50));

        System.out.println(Huffman.decode(encoding));
    }

}
