package com.dingyabin.work.gui.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author 丁亚宾
 * Date: 2021/8/22.
 * Time:13:13
 */
public class ImageDisplayPanel extends JPanel {


    private Image drawImage = null;

    private Dimension imageDimension = null;


    public ImageDisplayPanel withImage(Image drawImage) {
        this.drawImage = drawImage;
        this.imageDimension = new Dimension(drawImage.getWidth(null),drawImage.getHeight(null));
        return this;
    }


    public ImageDisplayPanel withLogo() {
        try {
            BufferedImage logoImage = ImageIO.read(ImageDisplayPanel.class.getResource("/logo/Logo.png"));
            setPreferredSize(new Dimension(logoImage.getWidth(),logoImage.getHeight()));
            return withImage(logoImage);
        } catch (Exception e) {
            //ignore
        }
        return this;
    }



    public Dimension getSize(){
        return imageDimension;
    }


    @Override
    public void paint(Graphics g) {
        if (drawImage != null) {
            g.drawImage(drawImage, 0,0,null);
        }
    }


}
