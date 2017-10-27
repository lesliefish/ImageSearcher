package com.csru.imagesearcher.tcpserver;

import java.io.*;
import java.net.*;

import com.csru.imagesearcher.indexer.Indexer;
import com.csru.imagesearcher.searchdependstype.SearchDependsType;
import com.csru.imagesearcher.seracher.Searcher;


public class TcpServer{
	private static final int PORT=12345;
	
	public TcpServer() {
		// TODO Auto-generated constructor stub
	}
	public void ServiceStart() {
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("服务器端口号："+ server.getLocalPort());

			while(true){
				Socket client = server.accept();
				System.out.println("已连接客户端地址："+client.getInetAddress()+"端口号:"+client.getPort());
				
				//启动线程
				new WorkThread(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WorkThread extends Thread{
	private Socket tclient;
	public WorkThread(Socket sock){
		tclient = sock;
	}
	
	public void run(){
		try {
			BufferedWriter outbuf = new BufferedWriter(new OutputStreamWriter(tclient.getOutputStream()));
			BufferedReader inbuf = new BufferedReader(new InputStreamReader(tclient.getInputStream()));
			String recvMsg = inbuf.readLine();//读取接受客户端的数据
			String[] recvDataStr = recvMsg.split("CSU");

			String action = recvDataStr[0];
													
			if(action.equals("SEARCH")){	 							   //搜索请求
				String imgPath = recvDataStr[1];
				String imgDepends = recvDataStr[2];
				SearchDependsType searchDependsType;
				switch (imgDepends) {
					case "AutoColorCorrelogram":
						searchDependsType = SearchDependsType.AutoColorCorrelogram;break;
					case "BinaryPatternsPyramid":
						searchDependsType = SearchDependsType.BinaryPatternsPyramid;break;
					case "CEDD":
						searchDependsType = SearchDependsType.CEDD;break;
					case "ColorLayout":
						searchDependsType = SearchDependsType.ColorLayout;break;
					case "EdgeHistogram":
						searchDependsType = SearchDependsType.EdgeHistogram;break;
					case "FCTH":
						searchDependsType = SearchDependsType.FCTH;break;
					case "FuzzyColorHistogram":
						searchDependsType = SearchDependsType.FuzzyColorHistogram;break;
					case "Gabor":
						searchDependsType = SearchDependsType.Gabor;break;
					case "JCD":
						searchDependsType = SearchDependsType.JCD;break;
					case "JpegCoefficientHistogram":
						searchDependsType = SearchDependsType.JpegCoefficientHistogram;break;
					case "LocalBinaryPatterns":
						searchDependsType = SearchDependsType.LocalBinaryPatterns;break;
					case "LuminanceLayout":
						searchDependsType = SearchDependsType.LuminanceLayout;break;
					case "OpponentHistogram":
						searchDependsType = SearchDependsType.OpponentHistogram;break;
					case "PHOG":
						searchDependsType = SearchDependsType.PHOG;break;
					case "RotationInvariantLocalBinaryPatterns":
						searchDependsType = SearchDependsType.RotationInvariantLocalBinaryPatterns;break;
					case "ScalableColor":
						searchDependsType = SearchDependsType.ScalableColor;break;
					case "SimpleColorHistogram":
						searchDependsType = SearchDependsType.SimpleColorHistogram;break;
					case "Tamura":
						searchDependsType = SearchDependsType.Tamura;break;
				default:
					searchDependsType = SearchDependsType.CEDD;
					break;
				}
				Searcher mySearcher = new Searcher();
				//开始搜索，默认搜索60幅图像
				mySearcher.DoSearch(imgPath, searchDependsType, 60);
				
				String str = "";
				for(int i=0; i< Searcher.imgList.size();i++){
					str+=Searcher.imgList.get(i)+"CSU";
				}
				System.out.println("Send String is: " + str);
				outbuf.write(str);
				outbuf.close();
			}else if(action.equals("INDEX")) {								//索引请求
				//建立索引文件
				String imagePath="ImagesFolder";
				Indexer myIndexer = new Indexer();
				boolean isIndexover =  myIndexer.IndexAll(imagePath);
				if(isIndexover){
					String str = "Index over!";
					System.out.println(str);
					outbuf.write(str);
					outbuf.close();
				}
			}
			tclient.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getStackTrace());
		}
	}
	
}
