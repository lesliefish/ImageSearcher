package com.csru.imagesearcher.seracher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.omg.CORBA.PUBLIC_MEMBER;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.BinaryPatternsPyramid;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.ColorLayout;
import net.semanticmetadata.lire.imageanalysis.features.global.EdgeHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.imageanalysis.features.global.FuzzyColorHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.Gabor;
import net.semanticmetadata.lire.imageanalysis.features.global.JCD;
import net.semanticmetadata.lire.imageanalysis.features.global.JpegCoefficientHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.LocalBinaryPatterns;
import net.semanticmetadata.lire.imageanalysis.features.global.LuminanceLayout;
import net.semanticmetadata.lire.imageanalysis.features.global.OpponentHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.PHOG;
import net.semanticmetadata.lire.imageanalysis.features.global.RotationInvariantLocalBinaryPatterns;
import net.semanticmetadata.lire.imageanalysis.features.global.ScalableColor;
import net.semanticmetadata.lire.imageanalysis.features.global.Tamura;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import com.csru.imagesearcher.searchdependstype.*;
/**
 * 图像检索
 * @author yu
 *
 */
public class Searcher {
	public static ArrayList<String> imgList;
	
	public Searcher(){
		System.out.println("-------------开始检索------------");
		imgList = new ArrayList<>();
	}
	/**
	 * 
	 * @param inputImagePath	输入图片路径
	 * @param searchType		检索方式
	 * @param picturenumber		检索图片个数  
	 * @throws IOException
	 */
    public void DoSearch(String inputImagePath, SearchDependsType searchType, int picturenumber) throws IOException {
        //清空List
    	Searcher.imgList.clear();
    	
    	// 判断输入图片路径
        BufferedImage img = null;
        boolean passed = false;
        if (inputImagePath.length() > 0) {
            File f = new File(inputImagePath);
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
            System.exit(1);
        }
        
        IndexReader ir = null;
        ImageSearcher searcher = null;
        
        //搜索
        switch (searchType) {
		case CEDD:
	        //ColorLayout 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\CEDD")));
	        searcher = new GenericFastImageSearcher(picturenumber, CEDD.class);
	        break;
		case AutoColorCorrelogram:
	        //AutoColorCorrelogram
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\AutoColorCorrelogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, AutoColorCorrelogram.class);
	        break;
		case BinaryPatternsPyramid:
	        //BinaryPatternsPyramid 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\BinaryPatternsPyramid")));
	        searcher = new GenericFastImageSearcher(picturenumber, BinaryPatternsPyramid.class);
	        break;
		case ColorLayout:
	        //ColorLayout 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\ColorLayout")));
	        searcher = new GenericFastImageSearcher(picturenumber, ColorLayout.class);
	        break;
		case EdgeHistogram:
	        //EdgeHistogram 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\EdgeHistogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, EdgeHistogram.class);
	        break;
		case FCTH:
	        //FCTH 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\FCTH")));
	        searcher = new GenericFastImageSearcher(picturenumber, FCTH.class);
	        break;
		case FuzzyColorHistogram:
	        //FuzzyColorHistogram 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\FuzzyColorHistogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, FuzzyColorHistogram.class);
	        break;
		case Gabor:
	        //Gabor 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\Gabor")));
	        searcher = new GenericFastImageSearcher(picturenumber, Gabor.class);
	        break;
		case JCD:
	        //JCD 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\JCD")));
	        searcher = new GenericFastImageSearcher(picturenumber, JCD.class);
	        break;
		case JpegCoefficientHistogram:
	        //JpegCoefficientHistogram 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\JpegCoefficientHistogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, JpegCoefficientHistogram.class);
	        break;
		case LocalBinaryPatterns:
	        //LocalBinaryPatterns 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\LocalBinaryPatterns")));
	        searcher = new GenericFastImageSearcher(picturenumber, LocalBinaryPatterns.class);
	        break;
		case LuminanceLayout:
	        //LuminanceLayout 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\LuminanceLayout")));
	        searcher = new GenericFastImageSearcher(picturenumber, LuminanceLayout.class);
	        break;
		case OpponentHistogram:
	        //OpponentHistogram 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\OpponentHistogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, OpponentHistogram.class);
	        break;
		case PHOG:
	        //PHOG 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\PHOG")));
	        searcher = new GenericFastImageSearcher(picturenumber, PHOG.class);
	        break;
		case RotationInvariantLocalBinaryPatterns:
	        //RotationInvariantLocalBinaryPatterns 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\RotationInvariantLocalBinaryPatterns")));
	        searcher = new GenericFastImageSearcher(picturenumber, RotationInvariantLocalBinaryPatterns.class);
	        break;
		case ScalableColor:
	        //ScalableColor 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\ScalableColor")));
	        searcher = new GenericFastImageSearcher(picturenumber, ColorLayout.class);
	        break;
		case SimpleColorHistogram:
	        //SimpleColorHistogram 
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\SimpleColorHistogram")));
	        searcher = new GenericFastImageSearcher(picturenumber, ScalableColor.class);
	        break;
		case Tamura:
	        //Tamura
			ir= DirectoryReader.open(FSDirectory.open(Paths.get("IndexPath\\Tamura")));
	        searcher = new GenericFastImageSearcher(picturenumber, Tamura.class);
	        break;
		default:
			break;
		}
        
        
        // searching with a image file ...
        ImageSearchHits hits = searcher.search(img, ir);
        // searching with a Lucene document instance ...
        for (int i = 0; i < hits.length(); i++) {
            String fileName = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            System.out.println(hits.score(i) + ": \t" + fileName);
            imgList.add(fileName);//加入结果列表
        }
        
        System.out.println("-------------检索完毕------------");

    }
}