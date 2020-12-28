
public class Main {
    public static void main(String[] args) throws Exception {

        /*
         It's up to you whether to approach in a static / instance way! The class has all of them correctly implemented!
         */
        // Huffman.encode("hfpqewruuqpiwoeruqeasfhqqwepwoprqwpoeqwpqweporipqwoeirppoiohfpqewruuqpiwoeruqeasfhqqwepwoprqwpoeqwpqweporipqwoeirppoio");
        String encoding = Huffman.encode("qwtuyqweryewqtopqperioiytutyuytiuiyuqweoiroqppqowpeqruyqweouriuoqweituyutryrtyuywrterutuy");
        // System.out.println();

        System.out.println(Huffman.decode(encoding));
    }

}
