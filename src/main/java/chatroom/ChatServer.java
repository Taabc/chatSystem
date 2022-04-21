package chatroom;

import chatroom.ServerThread;
import pojo.ClientMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    static List<ClientMessage> clientMessageList = new ArrayList<ClientMessage>();
    public static void main(String[] args) throws IOException {
        ChatServer cs = new ChatServer();
        cs.setUpServer(7777);
    }

    public void setUpServer(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("服务器创建成功！端口号为:" + port);

            while (true) {//一直监听等待
                Socket client = ss.accept();//获取进入的客户机

                System.out.println("进入了1个客户机连接：" + client.getRemoteSocketAddress().toString());
                System.out.println(ChatTool.getSocketNum());
                //传入 线程,并开启
                ServerThread st = new ServerThread(client);
                st.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
