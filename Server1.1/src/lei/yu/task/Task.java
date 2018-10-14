package lei.yu.task;

import lei.yu.imagesearch.DependsType;
import lei.yu.imagesearch.IndexCreater;
import lei.yu.imagesearch.Searcher;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Task implements Callable<Boolean> {

    // 将和客户端通信的socket传递进来
    private Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    // 根据字符串获取检索/索引类型
    DependsType getTypeByString(String str) {
        DependsType type = null;
        switch (str) {
            case "AutoColorCorrelogram":
                type = DependsType.AutoColorCorrelogram;
                break;
            case "BinaryPatternsPyramid":
                type = DependsType.BinaryPatternsPyramid;
                break;
            case "CEDD":
                type = DependsType.CEDD;
                break;
            case "ColorLayout":
                type = DependsType.ColorLayout;
                break;
            case "EdgeHistogram":
                type = DependsType.EdgeHistogram;
                break;
            case "FCTH":
                type = DependsType.FCTH;
                break;
            case "FuzzyColorHistogram":
                type = DependsType.FuzzyColorHistogram;
                break;
            case "Gabor":
                type = DependsType.Gabor;
                break;
            case "JCD":
                type = DependsType.JCD;
                break;
            case "JpegCoefficientHistogram":
                type = DependsType.JpegCoefficientHistogram;
                break;
            case "LocalBinaryPatterns":
                type = DependsType.LocalBinaryPatterns;
                break;
            case "LuminanceLayout":
                type = DependsType.LuminanceLayout;
                break;
            case "OpponentHistogram":
                type = DependsType.OpponentHistogram;
                break;
            case "PHOG":
                type = DependsType.PHOG;
                break;
            case "RotationInvariantLocalBinaryPatterns":
                type = DependsType.RotationInvariantLocalBinaryPatterns;
                break;
            case "ScalableColor":
                type = DependsType.ScalableColor;
                break;
            case "SimpleColorHistogram":
                type = DependsType.SimpleColorHistogram;
                break;
            case "Tamura":
                type = DependsType.Tamura;
                break;
            default:
                type = DependsType.ALL;
                break;
        }

        return type;
    }

    // 检索事件
    public ArrayList<String> doSearch(String path, String typeStr) {
        Searcher searcher = new Searcher();
        return searcher.doSearch(path, getTypeByString(typeStr), 30);
    }

    // 索引操作
    public void doIndex(String imagesPath, String typeString) {
        IndexCreater indexCreater = new IndexCreater();
        DependsType type = null;

        indexCreater.doCreateIndex(imagesPath, getTypeByString(typeString));
    }

    @Override
    public Boolean call() {
        try {
            BufferedReader inBuffer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            // 接收到的数据
            String recvMessage = inBuffer.readLine();
            String[] datas = recvMessage.split("CSU");

            if (datas.length != 3) {
                System.out.println("客户端发送数据有误. 传入参数必须为4个. 但是实际上是" + datas.length);
                this.socket.close();
                return false;
            }

            if (datas[0].equals("SEARCH")) {
                // 执行检索
                System.out.println("接收到搜索请求：");
                String path = datas[1];
                String dependType = datas[2];
                ArrayList<String> list = doSearch(path, dependType);

                String sendStr = "";
                for (int i = 0; i < list.size(); i++) {
                    sendStr += list.get(i) + "CSU";
                }
                System.out.println("发送给客户端的字符串是: " + sendStr);
                BufferedWriter outBuffer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                if (sendStr.isEmpty()) {
                    System.out.println("search result is empty.");
                }
                outBuffer.write(sendStr);

            } else if (datas[0].equals("INDEX")) {
                // 执行索引
                System.out.println("It is a create index request.");
                String sendStr = "Index over!";
                BufferedWriter outBuffer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                outBuffer.write(sendStr);
            }

            this.socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }
}
