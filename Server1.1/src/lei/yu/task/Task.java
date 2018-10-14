package lei.yu.task;

import lei.yu.imagesearcher.DependsType;
import lei.yu.imagesearcher.Searcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Task implements Callable<Boolean> {

    private Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<String> doSearch(String path, DependsType type) {
        Searcher searcher = new Searcher();
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
                System.out.println("It is a search request.");
                String path = datas[1];
                String dependType = datas[2];
            } else if (datas[0].equals("INDEX")) {
                System.out.println("It is a create index request.");
            }

            this.socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }
}
