package lei.yu.task;

import lei.yu.mainsearcher.DependsType;
import lei.yu.mainsearcher.Searcher;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Task implements Callable<Boolean> {

    private Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<String> doSearch(String path, String typeStr) {
        Searcher searcher = new Searcher();
        DependsType type = null;
        switch (typeStr) {
            case "":
            default: {
                type = DependsType.CEDD;
            }
        }


        return searcher.doSearch(path, type, 2);
    }

    @Override
    public Boolean call() {
        try {
            BufferedReader inBuffer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            // 接收到的数据
            String recvMessage = inBuffer.readLine();
            String[] datas = recvMessage.split("CSU");

            if (datas.length != 3) {
                System.out.println("data from client is invalid. params count must be 3. but recieve is " + datas.length);
                this.socket.close();
                return false;
            }

            if (datas[0].equals("SEARCH")) {
                // 执行检索
                System.out.println("It is a search request.");
                String path = datas[1];
                String dependType = datas[2];
                ArrayList<String> list = doSearch(path, dependType);

                String str = "";
                for (int i = 0; i < list.size(); i++) {
                    str += list.get(i) + "CSU";
                }
                System.out.println("Send String is: " + str);
                BufferedWriter outBuffer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                if (str.isEmpty()){
                    str = "999999";
                }
                outBuffer.write(str);
            } else if (datas[0].equals("INDEX")) {
                // 执行索引
                System.out.println("It is a create index request.");
            }

            this.socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }
}
