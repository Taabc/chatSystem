package View;

import chatroom.ChatClient;
import pojo.*;
import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ImageTextPaneForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private int id;

    private JTextPane jTextPane1;
    private JFrame jFrame;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }


    public ImageTextPaneForm(JTextPane jTextPane1) {
        System.out.println("");
        this.jTextPane1 = jTextPane1;
        initComponents(jTextPane1);
        setSize(500,500);
       setVisible(true);
//        try{
//            start(new Stage());
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        System.out.println("初始化");
        ImageTextPaneController.add2(jTextPane2, jTextPane1, jButton2, jButton1, jFrame);
    }


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        jFrame = new JFrame();
//        jSplitPane1 = new JSplitPane();
//        jScrollPane1 = new JScrollPane();
//        //jTextPane1 = new JTextPane();
//        jPanel1 = new JPanel();
//        jScrollPane2 = new JScrollPane();
//        jTextPane2 = new JTextPane();
//        jButton2 = new JButton();
//        jButton1 = new JButton();
//
//        jPanel2 = new JPanel();
//
//        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
//        jSplitPane1.setResizeWeight(0.8);
//
//        jScrollPane1.setViewportView(jTextPane1);
//
//        //jSplitPane1.setTopComponent(jScrollPane1);
//        jSplitPane1.setLeftComponent(jScrollPane1);
//
//        jScrollPane2.setViewportView(jTextPane2);
//
//        jSplitPane1.setRightComponent(jPanel1);
//        jTextPane1.setSize(300,300);
//        jTextPane2.setSize(400,400);
//        jScrollPane2.setSize(400,400);
//        jPanel1.setSize(500,500);
//        jSplitPane1.setSize(500,500);
//        jFrame.setContentPane(jSplitPane1);
//        //setSize(500,500);
//        GridPane gridPane = new GridPane();
//
//
//        javax.swing.GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//
//        GroupLayout.SequentialGroup hSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jButton1).addComponent(jButton2);
//
//        GroupLayout.ParallelGroup hParalGroup = jPanel1Layout.createParallelGroup().addComponent(jScrollPane2).addGroup(hSeqGroup);
//
//        jPanel1Layout.setHorizontalGroup(hParalGroup);
//
//        GroupLayout.ParallelGroup vParaGroup = jPanel1Layout.createParallelGroup().addComponent(jButton1).addComponent(jButton2);
//
//        GroupLayout.SequentialGroup vSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jScrollPane2).addGroup(vParaGroup);
//
//        jPanel1Layout.setVerticalGroup(vSeqGroup);
//
////        jPanel1Layout.setHorizontalGroup(
////                jPanel1Layout.createParallelGroup()
////                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
////                .addComponent(jButton1)
////                .addComponent(jButton2)
////        );
////        jPanel1Layout.setVerticalGroup(
////                jPanel1Layout.createParallelGroup()
////                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
////                .addComponent(jButton1)
////                .addComponent(jButton2)
////        );
//        //jPanel1.setBorder();
////        jPanel1.add(jScrollPane2);
////        jPanel1.add(jButton2);
////        jPanel1.add(jButton1);
//
//        //jPanel1Layout.addLayoutComponent(jSplitPane1);
//
//        //jSplitPane1.setRightComponent(jPanel1);
//
//
//        jButton2.setText("插入图片");
//
//        jButton1.setText("发送");
//
////        javax.swing.GroupLayout layout = new GroupLayout(jPanel1);
////        getContentPane().setLayout(layout);
////        layout.setHorizontalGroup(
////                layout.createParallelGroup()
////                        .addGroup(layout.createSequentialGroup()
////                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
////                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97,108)
////                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
////                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105,113)
////                                //.addComponent(jSplitPane1)
////                        )
////        );
////        layout.setVerticalGroup(
////                layout.createParallelGroup()
////                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
////                                //.addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
////                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
////                                .addGroup(layout.createParallelGroup()
////                                        .addComponent(jButton1)
////                                        .addComponent(jButton2)))
////        );
//
//        jFrame.pack();
//        //GridPane gridPane1 = new GridPane();
//        //gridPane1.add(jFrame,0,0);
//        jFrame.setSize(500,500);
//        jFrame.setVisible(true);
////        Parent parent = (Parent) jFrame;
////        Scene scene = new Scene(jFrame, 660, 460);
////        primaryStage.setTitle("聊天界面");
////        primaryStage.getIcons().add(new Image("img/qq.png"));
////        JFrame.setStyle("-fx-background-color:White;");
////        scene.getStylesheets().add(
////                getClass().getResource("css/ChatPaneStyle.css")
////                        .toExternalForm());
////        primaryStage.setResizable(true);
////        primaryStage.setScene(scene);
////        primaryStage.set
////        primaryStage.show();
//
//    }
////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(JTextPane jTextPane1) {
        //JFrame jFrame = new JFrame();

        jSplitPane1 = new JSplitPane();
        jScrollPane1 = new JScrollPane();
        //jTextPane1 = new JTextPane();
        jPanel1 = new JPanel();
        jScrollPane2 = new JScrollPane();
        jTextPane2 = new JTextPane();
        jButton2 = new JButton();
        jButton1 = new JButton();
        jButton3 = new JButton();

        jPanel2 = new JPanel();

        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.8);

        jScrollPane1.setViewportView(jTextPane1);

        //jSplitPane1.setTopComponent(jScrollPane1);
        jSplitPane1.setLeftComponent(jScrollPane1);

        jScrollPane2.setViewportView(jTextPane2);

        jSplitPane1.setRightComponent(jPanel1);
        jTextPane1.setSize(300,300);
        jTextPane2.setSize(400,400);
        jScrollPane2.setSize(400,400);
        jPanel1.setSize(500,500);
        jSplitPane1.setSize(500,500);
        setContentPane(jSplitPane1);
        //setSize(500,500);
        //GridPane gridPane = new GridPane();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        javax.swing.GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        GroupLayout.SequentialGroup hSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jButton1).addComponent(jButton2).addComponent(jButton3);

        GroupLayout.ParallelGroup hParalGroup = jPanel1Layout.createParallelGroup().addComponent(jScrollPane2).addGroup(hSeqGroup);

        jPanel1Layout.setHorizontalGroup(hParalGroup);

        GroupLayout.ParallelGroup vParaGroup = jPanel1Layout.createParallelGroup().addComponent(jButton1).addComponent(jButton2).addComponent(jButton3);

        GroupLayout.SequentialGroup vSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jScrollPane2).addGroup(vParaGroup);

        jPanel1Layout.setVerticalGroup(vSeqGroup);

//        jPanel1Layout.setHorizontalGroup(
//                jPanel1Layout.createParallelGroup()
//                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
//                .addComponent(jButton1)
//                .addComponent(jButton2)
//        );
//        jPanel1Layout.setVerticalGroup(
//                jPanel1Layout.createParallelGroup()
//                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
//                .addComponent(jButton1)
//                .addComponent(jButton2)
//        );
        //jPanel1.setBorder();
//        jPanel1.add(jScrollPane2);
//        jPanel1.add(jButton2);
//        jPanel1.add(jButton1);

        //jPanel1Layout.addLayoutComponent(jSplitPane1);

        //jSplitPane1.setRightComponent(jPanel1);


        jButton2.setText("插入图片");

        jButton1.setText("发送");

        jButton3.setText("发送文件");

        jButton3.addActionListener((ActionEvent e) -> {
            //SMain.chatClient.getFriendIpPortByAccount(Integer.parseInt( jTextPane1.getName()));
            try{
                int friendPort;
                String friendIp;
                for (int i=0;i<FriendListForm.clientMessage.getFriendList().size();i++){
                    if (FriendListForm.clientMessage.getFriendList().get(i).getFaccount()==Integer.parseInt(jTextPane1.getName())){
                        System.out.println("获得对象IP和port");
                        friendIp = FriendListForm.clientMessage.getFriendList().get(i).getLip();
                        friendPort = FriendListForm.clientMessage.getFriendList().get(i).getLport()+100;
                        System.out.println("IP:"+friendIp);
                        System.out.println("Port:"+friendPort);

                        Socket socket=null;
                        ObjectOutputStream obops=null;
                        int flag =0;
                        for (int j=0;j<FriendListForm.p2pClientStreamList.size();j++){
                            String clientIp = FriendListForm.p2pClientStreamList.get(j).getSocket().getInetAddress().getHostAddress();
                            int clientPort = FriendListForm.p2pClientStreamList.get(j).getSocket().getPort();
                            System.out.println("clientIp:"+clientIp);
                            System.out.println("clientPort:"+clientPort);
                            if (clientIp.equals(friendIp)&&friendPort == clientPort){
                                socket = FriendListForm.p2pClientStreamList.get(j).getSocket();
                                obops = FriendListForm.p2pClientStreamList.get(j).getObops();
                                flag=1;
                                break;
                            }
                        }


                        if (flag!=1){
                            System.out.println("创建新会话");
                            socket =new Socket(friendIp,friendPort);
                            OutputStream ops = socket.getOutputStream();
                            obops = new ObjectOutputStream(ops);
                            User user =new User();
                            user.setAccount(FriendListForm.clientMessage.getUser().getAccount());
                            ClientMessage firstMessage = new ClientMessage();
                            firstMessage.setUser(user);
                            firstMessage.setOperate("Register");
                            obops.writeObject(firstMessage);
                            SocketStream socketStream = new SocketStream();
                            socketStream.setSocket(socket);
                            socketStream.setObops(obops);
                            socketStream.setType(1);
                            socketStream.setLinkAccount(Integer.parseInt(jTextPane1.getName()));
                            FriendListForm.p2pClientStreamList.add(socketStream);
                        }



                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int result = chooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            System.out.println(chooser.getSelectedFile().toString());
                            String fileRoad = chooser.getSelectedFile().toString();
                            //byte[] file_date = Utils.load_picture_from_file(fileRoad);

                            ClientMessage cmsg = new ClientMessage();
                            cmsg.setUser(FriendListForm.clientMessage.getUser());
                            cmsg.setOperate("FriendFile");
                            UserFile userFile = new UserFile();
                            //userFile.setFile_date(file_date);
                            userFile.setFile_load(fileRoad);
                            String type;
                            type=fileRoad.substring(fileRoad.length()-4,fileRoad.length());
                            String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
                            userFile.setFilename(FriendListForm.clientMessage.getUser().getAccount()+TimeNow+type);
                            cmsg.setUserFile(userFile);
                            obops.writeObject(cmsg);
                            File file = new File(fileRoad);
                            JProgressBar jProgressBar = new JProgressBar();
                            JButton jButton = new JButton("取消");
                            long AllSize ;
                            long read_size=0;
                            byte[] fileBuffer = new byte[1024];
                            int len =0;
                            double percentage = 0;
                            jProgressBar.setString(null);
                            jProgressBar.setStringPainted(true);
                            LoadingBarForm loadingBarForm = new LoadingBarForm(jProgressBar);
                            loadingBarForm.initComponents();

                            AllSize = file.length();
                            FileInputStream fileInputStream = new FileInputStream(file);
                            System.out.println(AllSize);
                            OutputStream outputStream = socket.getOutputStream();
                            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                            dataOutputStream.writeLong(AllSize);

                            while((len = fileInputStream.read(fileBuffer))!=-1){
                                System.out.println("len:"+len);
                                read_size += len;
                                System.out.println("read_size:"+read_size);
                                //System.out.println("fileBuffer"+fileBuffer);
                                outputStream.write(fileBuffer,0,len);
                                outputStream.flush();
                                percentage = (double) read_size / AllSize;
                                //System.out.println(percentage);
                                jProgressBar.setValue((int)(percentage * 100));
                            }
                            fileInputStream.close();
                            System.out.println(read_size);
                            System.out.println("上传完毕");

                        }

                        break;

                    }else {
                        System.out.println("获得对象地址失败");
                    }
                }




            }catch (Exception es){
                es.printStackTrace();
            }

        });

//        javax.swing.GroupLayout layout = new GroupLayout(jPanel1);
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup()
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97,108)
//                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105,113)
//                                //.addComponent(jSplitPane1)
//                        )
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup()
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                                //.addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
//                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                .addGroup(layout.createParallelGroup()
//                                        .addComponent(jButton1)
//                                        .addComponent(jButton2)))
//        );

        pack();
    }// </editor-fold>




    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    //private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;

    private javax.swing.JPanel jPanel2;
    // End of variables declaration

    public static void main(String[] args) {
//        javax.swing.JTextPane jTextPane1 = new JTextPane();
//        ClientMessage clientMessage; ChatClient chatClient;
//        clientMessage= new ClientMessage();
//        chatClient = new ChatClient();
//        ImageTextPaneForm imageTextPaneForm = new ImageTextPaneForm(jTextPane1,clientMessage,chatClient);
//        //imageTextPaneForm.;
//        imageTextPaneForm.setSize(500,500);
//        imageTextPaneForm.setVisible(true);
//       //JFrame jf = new JFrame("测试窗口");

    }
}