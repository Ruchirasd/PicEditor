
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
public class UpSample {

    public void upSamplePic(String path, int n) throws IOException {
        
        File input = new File(path);
        BufferedImage inputImage = ImageIO.read(input);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int widthN = (width) * 4;
        int heightN = (height) * 4;
        BufferedImage outputImage = new BufferedImage(widthN, heightN, BufferedImage.TYPE_BYTE_GRAY);
        float ratioX = 0.25f;
        float ratioY = 0.25f;
        for (int i = 0; i < heightN; i++) {
            for (int j = 0; j < widthN; j++) {
                int gray = 0;
                if (j * ratioX < width && i * ratioY < height) {
                    gray = inputImage.getRGB((int) (j * ratioX), (int) (i * ratioY));
                }
                int val = (255 << 24) | (gray << 16) | (gray << 8) | gray;
                outputImage.setRGB(j, i, val);
            }

        }

        File output = null;
        if (n == 0) {
            output = new File("3-UpSampled/UpSampled.png");
        } else {
            output = new File("3-UpSampled/UP Sampled - " + n + ".png");
        }

        ImageIO.write(outputImage, "png", output);
    }

    static public void main(String args[]) throws Exception {
        UpSample us = new UpSample();
        for (int i = 1; i < 6; i++) {
            us.upSamplePic("3-DownSampled/Down Sampled - " + i + ".png", i);
        }
    }

}
