package net.semanticmetadata.lire.imageanalysis.filters;

import junit.framework.TestCase;
import net.semanticmetadata.lire.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by mlux on 10.11.2015.
 */
public class TestAutoCrop extends TestCase {
    String path = "testdata/logo-shave/";
    String[] logos = {
            "14080.orig.jpg",
            "1011630.1.orig.jpg",
            "85504391.orig.png",
            "78682393.orig.png",
            "78902572.orig.png",
            "85186052.orig.png",
            "86093484.orig.png",
            "86099964.orig.png",
            "baz.png"
    };

    public void testAnalyze() throws IOException {
        for (int i = 0; i < logos.length; i++) {
            System.out.println("Processing " + path + logos[i]);
            BufferedImage img = ImageIO.read(new FileInputStream(path + logos[i]));
            BufferedImage src = ImageUtils.removeScratches(img);
//            src = ImageUtils.differenceOfGaussians(src);
//            src = ImageUtils.denoiseImage(src);
            BufferedImage result = ImageUtils.trimWhiteSpace(src, img, 196, 2,2,2,2);
            ImageIO.write(result, "PNG", new File(path + logos[i] + ".out.png"));
        }
    }

}
