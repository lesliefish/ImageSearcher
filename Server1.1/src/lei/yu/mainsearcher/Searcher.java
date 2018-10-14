package lei.yu.mainsearcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.*;
import net.semanticmetadata.lire.imageanalysis.features.global.OpponentHistogram;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;

public class Searcher {
    public Searcher() {
        System.out.println("------------------------开始检索------------------------");
    }

    public ArrayList<String> doSearch(String path, DependsType dependsType, int maxNumber) {

        ArrayList<String> searchList = new ArrayList<String>();
        IndexReader indexReader = null;
        ImageSearcher imageSearcher = null;

        BufferedImage img = null;
        boolean passed = false;
        if (path.length() > 0) {
            File f = new File(path);
            if (f.exists()) {
                try {
                    img = ImageIO.read(f);
                    passed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!passed) {
            System.out.println("输入图片路径有误！");
            return new ArrayList<String>();
        }


        try{
            //搜索
            switch (dependsType) {
                case CEDD:
                    //ColorLayout
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\CEDD")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, CEDD.class);
                    break;
                case AutoColorCorrelogram:
                    //AutoColorCorrelogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\AutoColorCorrelogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, AutoColorCorrelogram.class);
                    break;
                case BinaryPatternsPyramid:
                    //BinaryPatternsPyramid
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\BinaryPatternsPyramid")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, BinaryPatternsPyramid.class);
                    break;
                case ColorLayout:
                    //ColorLayout
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\ColorLayout")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, ColorLayout.class);
                    break;
                case EdgeHistogram:
                    //EdgeHistogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\EdgeHistogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, EdgeHistogram.class);
                    break;
                case FCTH:
                    //FCTH
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\FCTH")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, FCTH.class);
                    break;
                case FuzzyColorHistogram:
                    //FuzzyColorHistogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\FuzzyColorHistogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, FuzzyColorHistogram.class);
                    break;
                case Gabor:
                    //Gabor
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\Gabor")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, Gabor.class);
                    break;
                case JCD:
                    //JCD
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\JCD")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, JCD.class);
                    break;
                case JpegCoefficientHistogram:
                    //JpegCoefficientHistogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\JpegCoefficientHistogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, JpegCoefficientHistogram.class);
                    break;
                case LocalBinaryPatterns:
                    //LocalBinaryPatterns
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\LocalBinaryPatterns")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, LocalBinaryPatterns.class);
                    break;
                case LuminanceLayout:
                    //LuminanceLayout
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\LuminanceLayout")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, LuminanceLayout.class);
                    break;
                case OpponentHistogram:
                    //OpponentHistogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\OpponentHistogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, OpponentHistogram.class);
                    break;
                case PHOG:
                    //PHOG
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\PHOG")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, PHOG.class);
                    break;
                case RotationInvariantLocalBinaryPatterns:
                    //RotationInvariantLocalBinaryPatterns
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\RotationInvariantLocalBinaryPatterns")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, RotationInvariantLocalBinaryPatterns.class);
                    break;
                case ScalableColor:
                    //ScalableColor
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\ScalableColor")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, ColorLayout.class);
                    break;
                case SimpleColorHistogram:
                    //SimpleColorHistogram
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\SimpleColorHistogram")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, ScalableColor.class);
                    break;
                case Tamura:
                    //Tamura
                    indexReader= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\Tamura")));
                    imageSearcher = new GenericFastImageSearcher(maxNumber, Tamura.class);
                    break;
                default:
                    break;
            }

            // searching with a image file ...
            ImageSearchHits hits = imageSearcher.search(img, indexReader);
            // searching with a Lucene document instance ...
            for (int i = 0; i < hits.length(); i++) {
                String fileName = indexReader.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
                System.out.println(hits.score(i) + ": \t" + fileName);
                searchList.add(fileName);//加入结果列表
            }
            System.out.println("-------------检索完毕------------");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }


        return searchList;
    };
}
