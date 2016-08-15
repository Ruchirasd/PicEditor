
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
public class DownSample {

    public void downSamplePic(String path, int n) throws IOException {

        File input = new File(path);
        BufferedImage inputImage = ImageIO.read(input);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int widthN = (width) / 4;
        int heightN = (height) / 4;
        BufferedImage outImage = new BufferedImage(widthN, heightN, BufferedImage.TYPE_BYTE_GRAY);
        int ratioX = 4; // ratioX = width/widthN
        int ratioY = 4; // ratioY = height/heightN
        for (int i = 0; i < heightN; i++) {
            for (int j = 0; j < widthN; j++) {
                int gray = 0;
                for (int k = 0; k < ratioX; k++) {
                    for (int l = 0; l < ratioY; l++) {
                        if (j * ratioX + k < width && i * ratioY + l < height) {
                            int val = inputImage.getRGB(j * ratioX + k, i * ratioY + l);
                            val = val & 255;
                            gray += val;
                        }
                    }
                }
                gray /= ratioX * ratioY;
                int val = (255 << 24) | (gray << 16) | (gray << 8) | gray;
                outImage.setRGB(j, i, val);
            }
        }
        System.out.println("" + Integer.toBinaryString(inputImage.getRGB(0, 0)));
        System.out.println("" + Integer.toBinaryString(outImage.getRGB(0, 0)));

        File output = null;
        if (n == 0) {
            output = new File("3-DownSampled/DownSampled.png");
        } else {
            output = new File("3-DownSampled/Down Sampled - " + n + ".png");
        }

        ImageIO.write(outImage, "png", output);

    }

    static public void main(String args[]) throws Exception {
        DownSample ds = new DownSample();
        for (int i = 1; i < 6; i++) {
            ds.downSamplePic("1-Original/" + i + ".jpg", i);
        }
    }
}
