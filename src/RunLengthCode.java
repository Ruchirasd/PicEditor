
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import piceditor.PicEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ruchira
 */
public class RunLengthCode {

    public static void main(String[] args) {
        File input = new File("Grey - 1.png");
        BufferedImage inputImage;

        try {
            inputImage = ImageIO.read(input);
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            System.out.println(width);
            System.out.println(height);
            System.out.println(Integer.toBinaryString(inputImage.getRGB(0, 0)));

            String[] planes = new String[8];
            int[] counts = new int[8];
            char[] prevSymbols = new char[8];
            char[] currentSymbols = new char[8];

            String binaryCode;
            String runLength = "";
            int count = 0;
            char prevSymbol = 0;
            char currentSymbol;
            System.out.println(Integer.toBinaryString(inputImage.getRGB(639, 359)));
            /*         for (int k = 0; k < 8; k++) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        binaryCode = Integer.toBinaryString(inputImage.getRGB(j,i));
                        
                        currentSymbol = binaryCode.charAt(24 + k);
                        if (j == 0) {
                            runLength += String.valueOf(currentSymbol);
                            prevSymbol = currentSymbol;
                            count = 1;
                        } else if (currentSymbol == prevSymbol) {
                            count += 1;
                        } else {
                            runLength += "," + Integer.toString(count);
                            count = 1;
                            prevSymbol = currentSymbol;
                        }

                    }

                    runLength += ":";
                }
                runLength += ";";
           }
             */

            //------------------------------------------------------------------------------------------
            for (int i = 0; i < height; i++) {
              
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < 8; k++) {
                        binaryCode = Integer.toBinaryString(inputImage.getRGB(j, i));

                        currentSymbols[k] = binaryCode.charAt(24 + k);
                        if (j == 0) {
                            planes[k] += String.valueOf(currentSymbols[k]);
                            prevSymbols[k] = currentSymbols[k];
                            counts[k] = 1;
                        } else if (currentSymbols[k] == prevSymbols[k]) {
                            counts[k] += 1;
                        } else {
                            planes[k] += "," + Integer.toString(count);
                            counts[k] = 1;
                            prevSymbols[k] = currentSymbols[k];
                        }
                        if(j==width){}

                        
                    }
                   
                }
              //  planes[k] += ":";
            }
//--------------------------------------------------------------------------------- 
            System.out.println(runLength);

            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("compressed.txt"));
            writer.write(runLength);

        } catch (IOException ex) {
            Logger.getLogger(RunLengthCode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
