package TestDemo;

import chatroom.ServerThread;
import pojo.ClientMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class serverTest {
    static List<ClientMessage> clientMessageList = new ArrayList<ClientMessage>();
    public static void main(String[] args) throws IOException {
        serverTest cs = new serverTest();
        cs.setUpServer(7777);
    }

    public void setUpServer(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("服务器创建成功！端口号为:" + port);

            //while (true) {//一直监听等待
                Socket client = ss.accept();//获取进入的客户机

                System.out.println("进入了1个客户机连接：" + client.getRemoteSocketAddress().toString());
                //System.out.println(ChatTool.getSocketNum());
                //传入 线程,并开启
//                ServerThread st = new ServerThread(client);
//                st.start();
           InputStream inputStream = client.getInputStream();
           OutputStream outputStream =  client.getOutputStream();
           DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
           DataInputStream dataInputStream = new DataInputStream(inputStream);
            String filePath = "src/main/resources/userFileSave/groupFile/1/test.png";//+userFile.getFilename();
            File file = new File(filePath);
            FileOutputStream fileOutputStream  = new FileOutputStream(file);
            long read_size = 0;
            double percentage = 0;//百分比
            long filesize = dataInputStream.readLong();//获取文件大小
            System.out.println(filesize);
            int len = 0;//每次接收的大小
            byte[] bytes = new byte[1024];
            //下边这部分，基本跟客户端一样
            while((len = inputStream.read(bytes)) != -1){
                System.out.println("len"+len);
                read_size += len;
                System.out.println("read_size"+read_size);
                fileOutputStream.write(bytes,0,len);
                fileOutputStream.flush();
//                percentage = (double) read_size / filesize;
//                down_pro.setValue((int)(percentage * 100));
                // System.out.println((int)(percentage * 100));
                if(read_size == filesize){
                    System.out.println("接收完成");
                }
            }
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
