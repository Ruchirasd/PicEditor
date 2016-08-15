
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ruchira
 */
public class HufmanCode {
     public static void main(String[] args) {
        try {
            File input = new File("Grey - 1.png");
            BufferedImage inImage = ImageIO.read(input);
            // we will assume that all our characters will have
            // code less than 256, for simplicity
            int[] charFreqs = new int[256];
            // read each character and record the frequencies
            for (int i = 0; i < inImage.getHeight(); i++) {
                for (int j = 0; j < inImage.getWidth(); j++) {
                    charFreqs[(inImage.getRGB(j, i) & 255)]++;
                }
            }

            // build tree
            HuffmanTree tree = HuffmanCode.buildTree(charFreqs);

            // print out results
            System.out.println("Fixed Length\tFreq\tHuffman Code");
            HashMap<Character, String> map = new HashMap<>();
            HuffmanCode.printCodes(tree, new StringBuffer(), map);
            
            String img = "<h>";
            
            for(Character k:map.keySet()){
                img+=k+":"+map.get(k)+";";
            }
            
            img += "</h>";
            String tempImg="";
            for (int i = 0; i < inImage.getHeight(); i++) {
                for (int j = 0; j < inImage.getWidth(); j++) {
                    tempImg += map.get((char) (inImage.getRGB(j, i) & 255));
                }
            }
            
            for (int i = 8; i <= tempImg.length(); i+=8) {
                img+=(char)Integer.parseInt(tempImg.substring(i-8, i),2);
            }
            
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("entropyCompressed.raw"));
                writer.write(img);
            } catch (IOException e) {
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(HuffmanCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

abstract class HuffmanTree implements Comparable<HuffmanTree> {

    public final int frequency; // the frequency of this tree

    public HuffmanTree(int freq) {
        frequency = freq;
    }

    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree {

    public final char value; // the character this leaf represents

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}

class HuffmanNode extends HuffmanTree {

    public final HuffmanTree left, right; // subtrees

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

class HuffmanCode {

    // input is an array of frequencies, indexed by character code

    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0) {
                trees.offer(new HuffmanLeaf(charFreqs[i], (char) i));
            }
        }

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix, HashMap<Character, String> map) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(String.format("%08d", Integer.parseInt(Integer.toBinaryString(leaf.value))) + "\t" + leaf.frequency + "\t" + prefix);
            map.put(leaf.value, prefix.toString());

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix, map);
            prefix.deleteCharAt(prefix.length() - 1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix, map);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

}
