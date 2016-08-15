
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
public class Distortion {

    public void calculateDistortion(String originalPath,String sampledPath,int n) throws IOException {
        
        File input = new File(originalPath);
        BufferedImage imageR = ImageIO.read(input);
    
        input = new File(sampledPath);
        BufferedImage imageO = ImageIO.read(input);
        
        int w = imageO.getWidth();
        int h = imageO.getHeight();
      
        int grayO = 0;
        int grayR = 0;
        float sd = 0f;
        float avg = 0;
        
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                grayO = imageO.getRGB(j, i) & 255;
                grayR = imageR.getRGB(j, i) & 255;
                avg += Math.abs(grayO - grayR);
                sd += Math.pow(grayO - grayR, 2);
            }
        }
        avg /= h * w;
        sd /= h * w;
        sd = (float) Math.sqrt(sd);
        System.out.println(n +"\t"+avg+"\t" + sd);
    }
    
    static public void main(String args[]) throws Exception {
        System.out.println("Image   Average     Standard deviation");
        Distortion d = new Distortion();
        for (int i = 1; i < 6; i++) {
            d.calculateDistortion("2-GreyScaled/"+"Grey - " + i + ".png","3-UpSampled/UP Sampled - " + i + ".png", i);
        }
    }
}
