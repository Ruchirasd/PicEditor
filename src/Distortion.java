
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
        //try {
        File input = new File(originalPath);
        BufferedImage inImageR = ImageIO.read(input);
    
        input = new File(sampledPath);
        BufferedImage inImageO = ImageIO.read(input);
        
        int w = inImageO.getWidth();
        int h = inImageO.getHeight();
        float x_ratio = 0.25f;
        float y_ratio = 0.25f;
        int grayO = 0;
        int grayR = 0;
        float sd = 0f;
        float avg = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                grayO = inImageO.getRGB(j, i) & 255;
                grayR = inImageR.getRGB(j, i) & 255;
                avg += Math.abs(grayO - grayR);
                sd += Math.pow(grayO - grayR, 2);
            }
        }
        avg /= h * w;
        sd /= h * w;
        sd = (float) Math.sqrt(sd);
        System.out.println("Avg:" + avg + " S.D: " + sd);
    }
    
    static public void main(String args[]) throws Exception {
        Distortion d = new Distortion();
        for (int i = 1; i < 6; i++) {
            d.calculateDistortion("2-GreyScaled/"+"Grey - " + i + ".png","3-UpSampled/UP Sampled - " + i + ".png", i);
        }
    }
}
