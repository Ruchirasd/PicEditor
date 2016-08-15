
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ruchiras
 */
public class GreyScale {

 

    public void convertToGreyScale(String path,int n) {
        try {
            File input = new File(path);
            BufferedImage inputImage = ImageIO.read(input);
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color c = new Color(inputImage.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(red + green + blue,
                            red + green + blue, red + green + blue);
                    outputImage.setRGB(j, i, newColor.getRGB());
                }
            }

            File output = null;
            if (n == 0) {
                output = new File("2-GreyScaled/Grey.png");
            } else {
                output = new File("2-GreyScaled/"+"Grey - " + n + ".png");
            }

            ImageIO.write(outputImage, "png", output);

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        GreyScale gc = new GreyScale();
        for (int i = 1; i < 6; i++) {
            gc.convertToGreyScale("1-Original/"+i + ".jpg",i);
        }

    }
}
