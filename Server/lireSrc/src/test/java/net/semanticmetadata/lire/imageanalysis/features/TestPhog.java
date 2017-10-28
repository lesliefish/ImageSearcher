package net.semanticmetadata.lire.imageanalysis.features;

import junit.framework.TestCase;
import net.semanticmetadata.lire.imageanalysis.features.global.OpponentHistogram;
import net.semanticmetadata.lire.utils.FileUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mlux on 20.08.2015.
 */
public class TestPhog extends TestCase {
    public void testEquality() throws IOException {
        OpponentHistogram p1 = new OpponentHistogram();
        OpponentHistogram p2 = new OpponentHistogram();
        List<File> allImageFiles = FileUtils.getAllImageFiles(new File("testdata/ferrari"), true);
        for (Iterator<File> iterator = allImageFiles.iterator(); iterator.hasNext(); ) {
            File nextFile = iterator.next();
            p1.extract(ImageIO.read(nextFile));
            p2.extract(ImageIO.read(nextFile));
            System.out.println(p1);
            System.out.println(p2);
            System.out.println(p1.getDistance(p2) == 0);
        }

    }

    public void testExtraction() throws IOException {
        OpponentHistogram p1 = new OpponentHistogram();
        OpponentHistogram p2 = new OpponentHistogram();
        List<File> allImageFiles = FileUtils.getAllImageFiles(new File("testdata/ferrari"), true);
        long ms = System.currentTimeMillis();
        for (Iterator<File> iterator = allImageFiles.iterator(); iterator.hasNext(); ) {
            File nextFile = iterator.next();
            p1.extract(ImageIO.read(nextFile));
        }
        ms = System.currentTimeMillis() - ms;
        System.out.println("ms = " + ms);
        ms = System.currentTimeMillis();
        for (Iterator<File> iterator = allImageFiles.iterator(); iterator.hasNext(); ) {
            File nextFile = iterator.next();
            p2.extract(ImageIO.read(nextFile));
        }
        ms = System.currentTimeMillis() - ms;
        System.out.println("ms = " + ms);
    }
}
