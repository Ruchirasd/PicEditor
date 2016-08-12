
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
    
    public void downSamplePic(String path,int n) throws IOException {
        //try {
            File input = new File(path);
            BufferedImage inImage = ImageIO.read(input);
            int w = inImage.getWidth();
            int h = inImage.getHeight();
            int w2 = (w)/4;
            int h2 = (h)/4;
            BufferedImage outImage = new BufferedImage(w2, h2, BufferedImage.TYPE_BYTE_GRAY);
            int x_ratio = 4;
            int y_ratio = 4;
            for (int i = 0; i < h2; i++) {
                for (int j = 0; j < w2; j++) {
                    int gray=0;
                    for (int k = 0; k < x_ratio; k++) {
                        for (int l = 0; l < y_ratio; l++) {
                            if(j*x_ratio+k<w && i*y_ratio+l<h){
                                int val = inImage.getRGB(j*x_ratio+k, i*y_ratio+l);
                                //System.out.println(""+Integer.toBinaryString(val));
                                val= val & 255;
                                gray+=val;
                            }
                        }
                    }
                    gray/=x_ratio*y_ratio;
                    int val= (255<<24) | (gray<<16) | (gray<<8) | gray;
                    outImage.setRGB(j, i, val);
                }
            }
            System.out.println(""+Integer.toBinaryString(inImage.getRGB(0, 0)));
            System.out.println(""+Integer.toBinaryString(outImage.getRGB(0, 0)));
            
            File output = null;
            if (n == 0) {
                output = new File("3-DownSampled/DownSampled.png");
            } else {
                output = new File("3-DownSampled/Down Sampled - " + n + ".png");
            }

            ImageIO.write(outImage, "gif", output);
            
            
//            File ouptut = new File(path.replaceFirst("in", "out"));
//            ImageIO.write(outImage, "gif", ouptut);

        //} catch (Exception e) {
            //System.out.println(""+e.getMessage());
        //}
    }
static public void main(String args[]) throws Exception {
    DownSample ds = new DownSample();
        for (int i = 1; i < 6; i++) {
            ds.downSamplePic("1-Original/"+i + ".jpg",i);
        }
    }
}

    
