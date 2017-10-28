package com.csru.imagesearcher.indexer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;

import net.semanticmetadata.lire.imageanalysis.features.global.ColorLayout;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.BinaryPatternsPyramid;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
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
import net.semanticmetadata.lire.imageanalysis.features.global.SimpleColorHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.Tamura;

import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;

import com.csru.imagesearcher.searchdependstype.SearchDependsType;

public class Indexer {
	/**
	 * 构造函数
	 */
	public Indexer() {
		System.out.println("-------------创建索引------------");
	}
	/**
	 * 根据图片路径索引
	 * @param imagePath  图片路径
	 * @param searchType 依据类型
	 * @throws IOException
	 */
    public void DoIndex(String imagePath, SearchDependsType searchType) throws IOException {
        // CEDDIndex
        boolean passed = false;
        //imagePath="ImagesFolder";
        if (imagePath.length() > 0) {
            File f = new File(imagePath);
            System.out.println("图片来源路径：" + imagePath);
            if (f.exists() && f.isDirectory()) passed = true;
        }
        if (!passed) {
            System.out.println("创建索引的图片来源路径有误！");
            //System.out.println("Run \"Indexer <directory>\" to index files of a directory.");
            System.exit(1);
        }
        // Getting all images from a directory and its sub directories.
        ArrayList<String> images = FileUtils.readFileLines(new File(imagePath), true);

        
        GlobalDocumentBuilder globalDocumentBuilder = null;
        IndexWriter iw = null;
                    
        //索引类型
        switch (searchType) {
		case CEDD:
	        // Creating a CEDD document builder 颜色和边缘的方向性描述符(Color and Edge Directivity Descriptor,CEDD)
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) CEDD.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\CEDD", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
       
		case AutoColorCorrelogram:
	        //AutoColorCorrelogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) AutoColorCorrelogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\AutoColorCorrelogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case BinaryPatternsPyramid:
	        //BinaryPatternsPyramid 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) BinaryPatternsPyramid.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\BinaryPatternsPyramid", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case ColorLayout:
	        //ColorLayout 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) ColorLayout.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\ColorLayout", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case EdgeHistogram:
	        //EdgeHistogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) EdgeHistogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\EdgeHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case FCTH:
	        //FCTH 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) FCTH.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\FCTH", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case FuzzyColorHistogram:
	        //FuzzyColorHistogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) FuzzyColorHistogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\FuzzyColorHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case Gabor:
	        //Gabor 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) Gabor.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\Gabor", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case JCD:
	        //JCD 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) JCD.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\JCD", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case JpegCoefficientHistogram:
	        //JpegCoefficientHistogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) JpegCoefficientHistogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\JpegCoefficientHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case LocalBinaryPatterns:
	        //LocalBinaryPatterns 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) LocalBinaryPatterns.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\LocalBinaryPatterns", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case LuminanceLayout:
	        //LuminanceLayout 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) LuminanceLayout.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\LuminanceLayout", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case OpponentHistogram:
	        //OpponentHistogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) OpponentHistogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\OpponentHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case PHOG:
	        //PHOG 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) PHOG.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\PHOG", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case RotationInvariantLocalBinaryPatterns:
	        //RotationInvariantLocalBinaryPatterns 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) RotationInvariantLocalBinaryPatterns.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\RotationInvariantLocalBinaryPatterns", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case ScalableColor:
	        //ScalableColor 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) ScalableColor.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\ScalableColor", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case SimpleColorHistogram:
	        //SimpleColorHistogram 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) SimpleColorHistogram.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\SimpleColorHistogram", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
		case Tamura:
	        //Tamura 
	        globalDocumentBuilder = new GlobalDocumentBuilder((Class<? extends GlobalFeature>) Tamura.class);
	        // 创建一个 Lucene IndexWriter
	        iw = LuceneUtils.createIndexWriter("IndexPath\\Tamura", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
	        break;
	        

		default:
			break;
		}
        
        for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
            String imageFilePath = it.next();
            System.out.println("Indexing " + imageFilePath);
            try {
                BufferedImage img = ImageIO.read(new FileInputStream(imageFilePath));
                Document document = globalDocumentBuilder.createDocument(img, imageFilePath);
                iw.addDocument(document);
            } catch (Exception e) {
                System.err.println("索引失败！");
                e.printStackTrace();
            }
        }
        // closing the IndexWriter
        LuceneUtils.closeWriter(iw);
        System.out.println("---------索引完毕.-------------");
    }
    
    /**
     * 创建所有索引
     * @param imagePath	图片库路径
     * @throws IOException
     */
    public boolean IndexAll(String imagePath) throws IOException{
		Indexer myIndexer = new Indexer();
		myIndexer.DoIndex(imagePath, SearchDependsType.CEDD);
		myIndexer.DoIndex(imagePath, SearchDependsType.FuzzyColorHistogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.AutoColorCorrelogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.BinaryPatternsPyramid);
		myIndexer.DoIndex(imagePath, SearchDependsType.ColorLayout);
		myIndexer.DoIndex(imagePath, SearchDependsType.EdgeHistogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.FCTH);
		myIndexer.DoIndex(imagePath, SearchDependsType.Gabor);
		myIndexer.DoIndex(imagePath, SearchDependsType.JCD);
		myIndexer.DoIndex(imagePath, SearchDependsType.JpegCoefficientHistogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.LocalBinaryPatterns);
		myIndexer.DoIndex(imagePath, SearchDependsType.LuminanceLayout);
		myIndexer.DoIndex(imagePath, SearchDependsType.OpponentHistogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.PHOG);
		myIndexer.DoIndex(imagePath, SearchDependsType.RotationInvariantLocalBinaryPatterns);
		myIndexer.DoIndex(imagePath, SearchDependsType.ScalableColor);
		myIndexer.DoIndex(imagePath, SearchDependsType.SimpleColorHistogram);
		myIndexer.DoIndex(imagePath, SearchDependsType.Tamura);	
		
		return true;
    }
}
