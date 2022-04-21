package TestDemo;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class clientDemo {
    private String host = "192.168.1.147";
    private int port = 7777;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    OutputStream ops;
    InputStream ins ;
    ObjectOutputStream obops;
    ObjectInputStream obins;
    Thread instantMessage=null;
    public clientDemo(){
        try{
            //连接到服务器
            socket = new Socket(host, port);//创建socket类对象
            ins = socket.getInputStream();
            ops = socket.getOutputStream();

            obops = new ObjectOutputStream(ops);
            obins = new ObjectInputStream(ins);
            out = new DataOutputStream(ops);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendFile_date(File file){
        try{
            long AllSize = file.length();
            long read_size=0;
            byte[] fileBuffer = new byte[1024];
            int len =0;
            double percentage = 0;
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println(AllSize);
            out.writeLong(AllSize);
            while((len = fileInputStream.read(fileBuffer))!=-1){
                System.out.println("len:"+len);
                read_size += len;
                System.out.println("read_size:"+read_size);
                ops.write(fileBuffer,0,len);
                ops.flush();
                //percentage = (double) read_size / AllSize;
                //System.out.println(percentage);
            }
            System.out.println(read_size);
            System.out.println("上传完毕");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        clientDemo clientDemo =new clientDemo ();
        System.out.println(11111);
//        JFileChooser chooser = new JFileChooser();
//        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        int result = chooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            System.out.println(chooser.getSelectedFile().toString());
//            String fileRoad = chooser.getSelectedFile().toString();
//            File file = chooser.getSelectedFile();
//            clientDemo.sendFile_date(file);
  //      }
    }
}
