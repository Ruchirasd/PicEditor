/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piceditor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ruchira
 */
public class PicEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File input = new File("1.jpg");
        BufferedImage inputImage;
        try {
            inputImage = ImageIO.read(input);
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            String binaryCode=Integer.toBinaryString(inputImage.getRGB(0,0));
            System.out.println(Integer.toBinaryString(inputImage.getRGB(0, 0)));
            System.out.println(binaryCode.charAt(29));
        } catch (IOException ex) {
            Logger.getLogger(PicEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
