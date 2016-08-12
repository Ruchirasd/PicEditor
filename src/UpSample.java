
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
        //try {
        File input = new File(path);
        BufferedImage inImage = ImageIO.read(input);
        int w = inImage.getWidth();
        int h = inImage.getHeight();
        int w2 = (w) * 4;
        int h2 = (h) * 4;
        BufferedImage outImage = new BufferedImage(w2, h2, BufferedImage.TYPE_BYTE_GRAY);
        float x_ratio = 0.25f;
        float y_ratio = 0.25f;
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                int gray = 0;
                if (j * x_ratio < w && i * y_ratio < h) {
                    gray = inImage.getRGB((int) (j * x_ratio), (int) (i * y_ratio));
                }
                int val = (255 << 24) | (gray << 16) | (gray << 8) | gray;
                outImage.setRGB(j, i, val);
            }

        }

        File output = null;
        if (n == 0) {
            output = new File("3-UpSampled/UpSampled.png");
        } else {
            output = new File("3-UpSampled/UP Sampled - " + n + ".png");
        }

        ImageIO.write(outImage, "gif", output);

//        File ouptut = new File(path.replaceFirst("in", "out"));
//        ImageIO.write(outImage, "gif", ouptut);
    }

    static public void main(String args[]) throws Exception {
        UpSample us = new UpSample();
        for (int i = 1; i < 6; i++) {
            us.upSamplePic("3-DownSampled/Down Sampled - " + i + ".png", i);
        }
    }

}
