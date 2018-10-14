package lei.yu.imagesearch;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.*;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class IndexCreater {
    ArrayList<DependsType> dependsArray;

    // 构造函数
    public IndexCreater() {
        dependsArray = new ArrayList<DependsType>();
        dependsArray.add(DependsType.AutoColorCorrelogram);
        dependsArray.add(DependsType.BinaryPatternsPyramid);
        dependsArray.add(DependsType.CEDD);
        dependsArray.add(DependsType.ColorLayout);
        dependsArray.add(DependsType.EdgeHistogram);
        dependsArray.add(DependsType.FCTH);
        dependsArray.add(DependsType.FuzzyColorHistogram);
        dependsArray.add(DependsType.Gabor);
        dependsArray.add(DependsType.JCD);
        dependsArray.add(DependsType.JpegCoefficientHistogram);
        dependsArray.add(DependsType.LocalBinaryPatterns);
        dependsArray.add(DependsType.LuminanceLayout);
        dependsArray.add(DependsType.OpponentHistogram);
        dependsArray.add(DependsType.PHOG);
        dependsArray.add(DependsType.RotationInvariantLocalBinaryPatterns);
        dependsArray.add(DependsType.ScalableColor);
        dependsArray.add(DependsType.SimpleColorHistogram);
        dependsArray.add(DependsType.Tamura);
    }

    // 根据类型创建索引
    public void creatIndex(String imagesPath, DependsType type) {
        try {
            // 读取指定的文件夹下的图像文件 对此图像文件建立索引
            ArrayList<String> images = FileUtils.readFileLines(new File(imagesPath), true);

            GlobalDocumentBuilder globalDocumentBuilder = null;
            IndexWriter indexWriter = null;

            switch (type) {

                case AutoColorCorrelogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) AutoColorCorrelogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\AutoColorCorrelogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }
                case BinaryPatternsPyramid: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) BinaryPatternsPyramid.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\BinaryPatternsPyramid", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case JCD: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) JCD.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\JCD", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case CEDD: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) CEDD.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\CEDD", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case FCTH: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) FCTH.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\FCTH", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case PHOG: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) PHOG.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\PHOG", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case Gabor: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) Gabor.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\Gabor", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case Tamura: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) Tamura.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\Tamura", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case ColorLayout: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) ColorLayout.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\ColorLayout", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case EdgeHistogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) EdgeHistogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\EdgeHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case ScalableColor: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) ScalableColor.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\ScalableColor", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case LuminanceLayout: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) LuminanceLayout.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\LuminanceLayout", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case OpponentHistogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) OpponentHistogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\OpponentHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case FuzzyColorHistogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) FuzzyColorHistogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\FuzzyColorHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case LocalBinaryPatterns: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) LocalBinaryPatterns.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\LocalBinaryPatterns", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case SimpleColorHistogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) SimpleColorHistogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\SimpleColorHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case JpegCoefficientHistogram: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) JpegCoefficientHistogram.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\JpegCoefficientHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                case RotationInvariantLocalBinaryPatterns: {
                    globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) RotationInvariantLocalBinaryPatterns.class);
                    indexWriter = LuceneUtils.createIndexWriter("IndexPath\\RotationInvariantLocalBinaryPatterns", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
                    break;
                }

                default: {
                    break;
                }
            }

            for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
                String imageFilePath = it.next();
                System.out.println("Indexing " + imageFilePath);
                try {
                    BufferedImage img = ImageIO.read(new FileInputStream(imageFilePath));
                    Document document = globalDocumentBuilder.createDocument(img, imageFilePath);
                    indexWriter.addDocument(document);
                } catch (Exception e) {
                    System.err.println("索引失败！");
                    e.printStackTrace();
                }
            }

            // closing the IndexWriter
            LuceneUtils.closeWriter(indexWriter);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void doCreateIndex(String imagesPath, DependsType type) {

        if (type == DependsType.ALL) {
            for (DependsType t : dependsArray) {
                creatIndex(imagesPath, t);
            }
        } else {
            creatIndex(imagesPath, type);
        }
    }
}
