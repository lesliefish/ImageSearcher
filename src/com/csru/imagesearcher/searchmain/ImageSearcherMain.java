package com.csru.imagesearcher.searchmain;

import com.csru.imagesearcher.tcpserver.*;
public class ImageSearcherMain {
	
	public static void main(String[] args) throws Exception {
		TcpServer tcpServer = new TcpServer();
		tcpServer.ServiceStart();
	} 
}
