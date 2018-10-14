package lei.yu.tcpserver;

import lei.yu.task.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class Server {
    private static final int PORT = 12345; // 设置默认端口
    private Map clientSockets;

    public Server() {
        clientSockets = new HashMap();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("服务器端口号：" + serverSocket.getLocalPort());

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("已连接客户端地址：" + client.getInetAddress() + "端口号:" + client.getPort());

                BufferedWriter outbuf = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                Task task = new Task(client);
                FutureTask<Boolean> fut = new FutureTask<>(task);
                new Thread(fut).start();

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
