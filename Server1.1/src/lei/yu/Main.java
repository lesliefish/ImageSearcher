package lei.yu;

import lei.yu.tcpserver.Server;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Server server = new Server();
        server.startServer();
    }
}
