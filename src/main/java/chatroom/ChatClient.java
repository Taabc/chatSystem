package chatroom;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import View.*;
import pojo.*;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import static java.lang.Thread.sleep;


public class ChatClient {
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

    public ChatClient(){
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

    public ChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public ClientMessage login(int account , String password){
        ClientMessage clientMessage =new ClientMessage();
        try{


            System.out.println("开始login");
            
//            ObjectOutputStream obops = new ObjectOutputStream(ops);
//            ObjectInputStream obins = new ObjectInputStream(ins);
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            clientMessage.setUser(user);
            clientMessage.setOperate("Login");

            //System.out.println("client 发出 message"+clientMessage.toString());

            obops.writeObject(clientMessage);
            obops.reset();



            clientMessage = (ClientMessage)obins.readObject();
            //System.out.println("client获得server返回message"+clientMessage.toString());

//            try {
//                clientMessage = (ClientMessage)obins.readObject();
//                System.out.println("client获得server返回message"+clientMessage.toString());
//            }catch (ClassNotFoundException e){
//                e.printStackTrace();
//            }


            //clientMessage = (ClientMessage) obins.readObject();


        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            //e.printStackTrace();
        }

        return clientMessage;
    }

    public void getGroupFile(int groupAccount){
        System.out.println("启动getGroupFile");
        ClientMessage serverMessage = new ClientMessage();
        Group group = new Group();
        group.setGnum(groupAccount);
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        serverMessage.setGroupList(groupList);
        serverMessage.setOperate("getGroupFileList");
        try{
            obops.writeObject(serverMessage);
            obops.reset();
            //clientMessage = (ClientMessage)obins.readObject();
            //obins.readObject().toString();
            //System.out.println(clientMessage.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        //return clientMessage.getGroupList().get(0);


    }
    public void getGroupNumber(int groupAccount){
        System.out.println("开始获取群成员");
        ClientMessage serverMessage = new ClientMessage();
        Group group = new Group();
        group.setGnum(groupAccount);
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        serverMessage.setGroupList(groupList);
        serverMessage.setOperate("getGroupNumber");
        try{
            obops.writeObject(serverMessage);
            obops.reset();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public void uploadFile (ClientMessage clientMessage){
//        System.out.println("开始上传群文件");
//        clientMessage.setOperate("uploadFile");
//        try{
//            obops.writeObject(clientMessage);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }



    public ClientMessage loginOut(int account){
        ClientMessage clientMessage =new ClientMessage();
        User user = new User();
        user.setAccount(account);
        clientMessage.setUser(user);
        clientMessage.setOperate("LoginOut");
        try{
            //OutputStream ops;
            //ops = socket.getOutputStream();

            System.out.println("开始LoginOut");
            //ObjectOutputStream obops = new ObjectOutputStream(ops);

            clientMessage.setUser(user);
            clientMessage.setOperate("LoginOut");

            obops.writeObject(clientMessage);
            obops.reset();

        }catch (IOException e){
            e.printStackTrace();
        }

        return clientMessage;
    }

    public void chat(){




            //try {
                //in = new DataInputStream(socket.getInputStream());

                //out = new DataOutputStream(socket.getOutputStream());

                //Scanner scanner = new Scanner(System.in);
                System.out.println("client启动");

                //new shuaxinText(in).start();

//                while (true){
//
//                }n
        instantMessage = new instantMessage();
               instantMessage.start();



    }

    public void sendMessage(ClientMessage clientMessage){

        try{
            obops.writeObject(clientMessage);
            //不重置输出流相同
            obops.reset();
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
           // File f = new File(file_url);
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println(AllSize);
            out.writeLong(AllSize);
            while((len = fileInputStream.read(fileBuffer))!=-1){
                System.out.println("len:"+len);
                read_size += len;
                System.out.println("read_size:"+read_size);
                //System.out.println("fileBuffer"+fileBuffer);
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
    public void getFriendIpPortByAccount(int account){
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setOperate("getFriendIpPort");
        clientMessage.setFriendAccount(account);
        try{
            obops.writeObject(clientMessage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void destroySocket(){
        try {

            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void setUpServer(int port) {
        try {
            System.out.println(port);
           // sleep(3000);
            ServerSocket ss = new ServerSocket(port);
            System.out.println("服务器创建成功！端口号为:" + port);

//            while (true) {//一直监听等待
//                Socket client = ss.accept();//获取进入的客户机
//
//                System.out.println("进入了1个客户机连接：" + client.getRemoteSocketAddress().toString());
//                System.out.println(ChatTool.getSocketNum());
//                //传入 线程,并开启
//                ClientThread st = new ClientThread(client);
//                st.start();
//            }
            ServerThread st = new ServerThread(ss);
            st.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class ServerThread extends Thread{
        private ServerSocket ss ;
        public ServerThread(ServerSocket ss){
            this.ss = ss;
        }

        @Override
        public void run() {
            try{
                while (true){
                    System.out.println("ServerThread启动");
                    Socket client = ss.accept();//获取进入的客户机
                    System.out.println("进入了1个客户机连接：" + client.getRemoteSocketAddress().toString());
                    ClientThread clientThread = new ClientThread(client);
                    clientThread.start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class ClientThread extends Thread{
        private Socket client;
        public ClientThread(Socket client) {
            this.client = client;
        }
        @Override
        public void run() {
            try {

                System.out.println("ClientThread启动");
                InputStream ins;
                OutputStream ops;
                ins = client.getInputStream();
                ops = client.getOutputStream();
                ObjectInputStream obins = new ObjectInputStream(ins);
                ObjectOutputStream obops = new ObjectOutputStream(ops);
                DataOutputStream dataOutputStream = new DataOutputStream(ops);
                DataInputStream dataInputStream = new DataInputStream(ins);
                SocketStream socketStream = new SocketStream();

                boolean flag=true;
                while (flag){
                    System.out.println("循环开始");
                    Object object = obins.readObject();
                    ClientMessage clientMessage = (ClientMessage) object;
                    switch (clientMessage.getOperate()){
                        case "FriendFile":{
                            System.out.println("接收到好友文件");
                            //System.out.println(clientMessage);
                            //byte[] file_date = clientMessage.getUserFile().getFile_date();
                            String filePath = "src/main/resources/fileDownload/friendFile/"+clientMessage.getUser().getAccount()+"/"+clientMessage.getUserFile().getFilename();

                            //Utils.write_file_to_file(file_date,filePath);
                            //int gnum=clientMessage.getUserFile().getGnum();
                            //String filename = clientMessage.getUserFile().getFilename();
                           // String filePath = "src/main/resources/fileDownload/groupFile/"+gnum+"/"+filename;
                            //新建进度条
                            JProgressBar jProgressBar = new JProgressBar();
                            JButton jButton = new JButton("取消");
                            File file = new File(filePath);
                            File fileParent = file.getParentFile();
                            if(!fileParent.exists()){
                                fileParent.mkdirs();
                            }

                            //设置进度条属性
                            jProgressBar.setString(null);
                            jProgressBar.setStringPainted(true);
                            //将进度条加入加载的Jpanel并显示
                            LoadingBarForm loadingBarForm = new LoadingBarForm(jProgressBar);
                            loadingBarForm.initComponents();
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            long read_size = 0;
                            double percentage = 0;//百分比
                            int len = 0;//每次接收的大小
                            byte[] bytes = new byte[1024];
                            //byte[] bytes2 = new byte[1];
                            // ins.read(bytes2);
                            long filesize = dataInputStream.readLong();//获取文件大小
                            System.out.println(filesize);
                            while((len = ins.read(bytes)) != -1){
                                System.out.println("len:"+len);
                                read_size += len;
                                //System.out.println("read_size:"+read_size);

                                fileOutputStream.write(bytes,0,len);
                                fileOutputStream.flush();
                                percentage = (double) read_size / filesize;
                                //System.out.println(percentage);
                                //实时更新加载进度
                                jProgressBar.setValue((int)(percentage * 100));
                                if (read_size==filesize){
                                    System.out.println("下载完成");
                                    JOptionPane.showConfirmDialog(null, "下载成功", "消息提示", JOptionPane.YES_NO_OPTION);
                                    break;
                                }
                            }
                            fileOutputStream.close();
                            client.close();
                            flag =false;
                            break;

                        }
                        case "Register":{
                            System.out.println("连接对象注册");
                            socketStream.setObops(obops);
                            socketStream.setSocket(client);
                            socketStream.setLinkAccount(clientMessage.getUser().getAccount());
                            socketStream.setType(0);
                            FriendListForm.p2pClientStreamList.add(socketStream);
                            break;

                        }
                        case "SDLFile":{
                            System.out.println("开始向服务器上传文件");
                            String file_road = clientMessage.getUserFile().getFile_load();
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
                            File file = new File(file_road);
                            AllSize = file.length();
                            FileInputStream fileInputStream = new FileInputStream(file);
                            System.out.println(AllSize);
                            dataOutputStream.writeLong(AllSize);

                            while((len = fileInputStream.read(fileBuffer))!=-1){
                                System.out.println("len:"+len);
                                read_size += len;
                                System.out.println("read_size:"+read_size);
                                //System.out.println("fileBuffer"+fileBuffer);
                                ops.write(fileBuffer,0,len);
                                ops.flush();
                                percentage = (double) read_size / AllSize;
                                //System.out.println(percentage);
                                jProgressBar.setValue((int)(percentage * 100));
                            }
                            fileInputStream.close();
                            System.out.println(read_size);
                            System.out.println("上传完毕");
                            client.close();
                            flag =false;
                            break;
                        }
                        case "SUPFile":{
                            System.out.println("服务器开始向这里传文件");

                            int gnum=clientMessage.getUserFile().getGnum();
                            String filename = clientMessage.getUserFile().getFilename();
                            String filePath = "src/main/resources/fileDownload/groupFile/"+gnum+"/"+filename;
                            //新建进度条
                            JProgressBar jProgressBar = new JProgressBar();
                            JButton jButton = new JButton("取消");
                            File file = new File(filePath);
                            File fileParent = file.getParentFile();
                            if(!fileParent.exists()){
                                fileParent.mkdirs();
                            }

                            //设置进度条属性
                            jProgressBar.setString(null);
                            jProgressBar.setStringPainted(true);
                            //将进度条加入加载的Jpanel并显示
                            LoadingBarForm loadingBarForm = new LoadingBarForm(jProgressBar);
                            loadingBarForm.initComponents();
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            long read_size = 0;
                            double percentage = 0;//百分比
                            int len = 0;//每次接收的大小
                            byte[] bytes = new byte[1024];
                            //byte[] bytes2 = new byte[1];
                            // ins.read(bytes2);
                            long filesize = dataInputStream.readLong();//获取文件大小
                            System.out.println(filesize);
                            while((len = ins.read(bytes)) != -1){
                                System.out.println("len:"+len);
                                read_size += len;
                                //System.out.println("read_size:"+read_size);

                                fileOutputStream.write(bytes,0,len);
                                fileOutputStream.flush();
                                percentage = (double) read_size / filesize;
                                //System.out.println(percentage);
                                //实时更新加载进度
                                jProgressBar.setValue((int)(percentage * 100));
                                if (read_size==filesize){
                                    System.out.println("下载完成");
                                    JOptionPane.showConfirmDialog(null, "下载成功", "消息提示", JOptionPane.YES_NO_OPTION);
                                    break;
                                }
                            }
                            fileOutputStream.close();
                            client.close();
                            flag =false;
                            break;
                        }
                        case "LoginOut":{
                            System.out.println("开始退出");
                            FriendListForm.p2pClientStreamList.remove(socketStream);
                            client.close();
                            flag=false;
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
    class instantMessage extends Thread{
//        private DataInputStream in;
//        private List<JTextPane> jTextPanes;
//        private List<JLabel> jLabelUserStateList;
        //private ChatClient chatClient;
        public instantMessage(){
            super();
           // this.chatClient = chatClient;
        }

        @Override
        public void run() {
            while(!isInterrupted()){
                try{
                    //Thread.sleep(1000);
                    //System.out.println("jinruxianc 1");
                    System.out.println("run运行中");
                    Object object = obins.readObject();
                    ClientMessage serverMessage = (ClientMessage) object;
                    System.out.println("RUN中serverMessage:"+serverMessage);
                    if(serverMessage.getOperate().equals("SendMessage")){
                        System.out.println("run进入SendMessage");
                        for (int i = 0; i< FriendListForm.jFriendTextPaneList.size();i++){
                            if (Integer.parseInt(FriendListForm.jFriendTextPaneList.get(i).getName()) == serverMessage.getUser().getAccount()){
                                ///StyledDocument inputSDoc = inputJTpane.getStyledDocument(); //获取读取的StyledDocument
                                StyledDocument inputSDoc = serverMessage.getMessageSDoc();
                                StyledDocument outSDoc = FriendListForm.jFriendTextPaneList.get(i).getStyledDocument(); //获取欲输出的StyledDocument
                                List<Picture> pictureList = serverMessage.getPictureList();
                                for (int k = 0;k<pictureList.size();k++){
//                                String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
//                                String tempImgName = clientMessage.getUser().getAccount() + TimeNow;
                                    String filePath = "src/main/resources/imgDownload/"+pictureList.get(k).getP_name()+pictureList.get(k).getType();
                                    if(!Utils.write_picture_to_file(pictureList.get(k).getP_date(),filePath)){
                                        System.out.println("图片写入错误");
                                    }
                                }


                                for (int j = 0; j < inputSDoc.getLength(); j++) { //遍历读取的StyledDocument
                                    //System.out.println(i);
                                    if (inputSDoc.getCharacterElement(j).getName().equals("icon")) { //如果发现是icon元素，那么：
                                        Element ele = inputSDoc.getCharacterElement(j);
                                        ImageIcon icon = (ImageIcon) StyleConstants.getIcon(ele.getAttributes());
                                        System.out.println(ele.getAttributes());
                                        //Image newImage = icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
                                        String Load ;
                                        String filePath = null;
                                        for (int n=0;n<pictureList.size();n++){
                                            if (pictureList.get(n).getOldLoad().equals(icon.toString())){
                                                //icon = (ImageIcon) StyleConstants.getIcon(pictureList.get(n).getP_name());
                                                Load = "src/main/resources/imgDownload/"+pictureList.get(n).getP_name()+pictureList.get(n).getType();
                                                icon = new ImageIcon(Load);
                                                filePath = "src/main/resources/temp/img/"+pictureList.get(n).getP_name()+".jpg";
                                            }
                                        }
                                        Image img = icon.getImage() ;
                                        Image newImage = getScaledImage(img,200,200);

                                        BufferedImage blmage = (BufferedImage) newImage;
                                        BufferedImage bufferedImage = null;
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                                        try{
                                            // outputfile.createNewFile();
                                            //ImageIO.write(blmage,  "jpg",  outputfile);
                                            ImageIO.write(blmage,  "jpg",  baos);
                                            baos.flush();
                                            byte[] imageTByte = baos.toByteArray();
                                            baos.close();
                                            Utils.write_temp_picture_to_file(imageTByte,filePath);


                                            //bufferedImage = ImageIO.read(new File("d:\\"+"test1.jpg"));
                                        }catch (Exception c){
                                            c.printStackTrace();
                                        }
//                                    byte[] imageByte =  Utils.load_picture_from_file(icon.toString());
//                                    File outputfile = new File("d:\\"+"test1.jpg");
//
//                                    try{
//                                        ImageIO.write(blmage,  "jpg",  outputfile);
//                                        bufferedImage = ImageIO.read(new File("d:\\"+"test1.jpg"));
//                                    }catch (Exception c){
//                                        c.printStackTrace();
//                                    }
//                                    String filePath = "d:\\"+"test1.jpg";

                                        //Image newImage2 = bufferedImage;
                                        //newImage =(ImageIcon) StyleConstants.getIcon(bufferedImage.geta);

                                        //ImageIcon newicon = new ImageIcon(newImage2);
                                        //System.out.println(newicon.toString());
                                        //System.out.println(newicon2.toString());
                                        System.out.println(icon.toString());
                                        //System.out.println(icon.getIconHeight()+icon.getIconWidth());filePath
                                        //outputJTpane.insertIcon(new ImageIcon(newicon.toString()));//插入icon元素
                                        FriendListForm.jFriendTextPaneList.get(i).insertIcon(new ImageIcon(filePath));
                                        FriendListForm.jFriendTextPaneList.get(i).setCaretPosition(FriendListForm.jFriendTextPaneList.get(i).getDocument().getLength());
                                    } else {//如果不是icon（可以判断是文字，因为目前只有图片元素插入）
                                        try {
                                            String s = inputSDoc.getText(j, 1);
                                            outSDoc.insertString(FriendListForm.jFriendTextPaneList.get(i).getCaretPosition(), s, null);//从光标处插入文字
                                            FriendListForm.jFriendTextPaneList.get(i).setCaretPosition(FriendListForm.jFriendTextPaneList.get(i).getDocument().getLength());
                                        } catch (BadLocationException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                                try{
                                    outSDoc.insertString(FriendListForm.jFriendTextPaneList.get(i).getCaretPosition(),"\r\n",null);
                                    FriendListForm.jFriendTextPaneList.get(i).setCaretPosition(FriendListForm.jFriendTextPaneList.get(i).getDocument().getLength());
                                }catch (BadLocationException e2){
                                    e2.printStackTrace();
                                }
                            }
                        }

                    }else if(serverMessage.getOperate().equals("AddFriend")){
                        System.out.println("接收到添加好友消息");
                        JPanel jPanel=null ;
                        Component[] components = FriendListForm.jPanelCenter.getComponents();

                        for (int j=0;j<components.length;j++){
                            System.out.println(j);
                            System.out.println(components[j].getName());
                            if (components[j].getName().equals("ApplyMsg")){
                                JPanel temp = (JPanel) components[j];
                                JScrollPane tempJS = (JScrollPane)temp.getComponent(1);
                                jPanel=(JPanel) tempJS.getViewport().getView();
                            }
                        }
                        JLabel jLabel = new JLabel(serverMessage.getUser().getName()+serverMessage.getUser().getAccount()+"发来了好友请求");
                        jPanel.add(jLabel);
                        JPanel jPanelLS = jPanel;
                        jLabel.addMouseListener(new MouseListener(){
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int flag=JOptionPane.showConfirmDialog(null, "确定添加该好友吗？", "添加好友", JOptionPane.YES_NO_OPTION);
                                if(flag==JOptionPane.YES_OPTION){
                                    //发送同意申请的消息
                                    ClientMessage clientMessage = new ClientMessage();
                                    clientMessage.setUser(FriendListForm.clientMessage.getUser());
                                    clientMessage.setFriendAccount(serverMessage.getUser().getAccount());
                                    clientMessage.setOperate("AgreeAdd");
                                    jPanelLS.remove(jLabel);
                                    jPanelLS.repaint();
                                    jPanelLS.revalidate();
                                    try {
                                        obops.writeObject(clientMessage);
                                        obops.reset();
                                    }catch (IOException ioException){
                                        ioException.printStackTrace();
                                    }

                                }else if(flag==JOptionPane.NO_OPTION){
                                    jPanelLS.remove(jLabel);
                                    jPanelLS.repaint();
                                    jPanelLS.revalidate();
                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }
                        });

                        jPanel.revalidate();

                    }else if (serverMessage.getOperate().equals("AddFriendSuccess")){
                        System.out.println("添加好友成功");
                        JPanel jPCenterTop = null;
                        Component[] components = FriendListForm.jPanelCenter.getComponents();
                        for (int j=0;j<components.length;j++){
                            if (components[j].getName().equals("MyFriend")){
                                JPanel temp = (JPanel) components[j];
                                JScrollPane tempJS = (JScrollPane)temp.getComponent(1);
                                jPCenterTop=(JPanel) tempJS.getViewport().getView();
                            }
                        }
                        List<Friend> friendList = serverMessage.getFriendList();
                        for (int j=0;j<friendList.size();j++){
                            if (friendList.get(j).getFaccount()==serverMessage.getUser().getAccount()){
                                String frendAccount = Integer.toString(friendList.get(j).getFaccount());
                                String friendName = friendList.get(j).getName();
                                JPanel jPanel = new JPanel();
                                jPanel.setName(frendAccount);
                                JLabel jLabelUser = new JLabel(friendList.get(j).getName(),JLabel.LEFT);
                                JLabel jLabelUserState;
                                if (friendList.get(j).getLstatus()==1){
                                    jLabelUserState = new JLabel("在线");
                                }else {
                                    jLabelUserState = new JLabel("离线");
                                }
                                jLabelUserState.setName(Integer.toString(friendList.get(j).getFaccount()));
                                jPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                jPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                                final int fid = friendList.get(j).getFaccount();

                                JTextPane jTextPane = new JTextPane();
                                jTextPane.setName(Integer.toString(friendList.get(j).getFaccount()));
                                FriendListForm.jFriendTextPaneList.add(jTextPane);

                                jPanel.addMouseListener(new MouseListener(){

                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        if(e.getButton()==java.awt.event.MouseEvent.BUTTON3){
                                            JPopupMenu m_popupMenu = new JPopupMenu();

                                            JMenuItem delMenItem = new JMenuItem();
                                            delMenItem.setText("  删除  ");
                                            delMenItem.addActionListener(new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    //该操作需要做的事
                                                    int flag=JOptionPane.showConfirmDialog(null, "确定要删除"+friendName+"这名好友吗？", "删除好友", JOptionPane.YES_NO_OPTION);
                                                    if (flag == JOptionPane.YES_OPTION){
                                                        ClientMessage clientMessage = new ClientMessage();
                                                        clientMessage.setUser(FriendListForm.clientMessage.getUser());
                                                        clientMessage.setOperate("DeleteFriend");
                                                        clientMessage.setFriendAccount(fid);
                                                        FriendListForm.chatClient.sendMessage(clientMessage);
                                                    }
                                                }
                                            });
                                            m_popupMenu.add(delMenItem);
                                            m_popupMenu.show(jPanel,e.getX(),e.getY());
                                        }
                                        else {
                                            System.out.println(jTextPane.getName());
                                            ImageTextPaneForm imageTextPaneForm = new ImageTextPaneForm(jTextPane);
                                            System.out.println(fid);
                                        }
                                    }

                                    //region Description
                                    @Override
                                    public void mousePressed(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseReleased(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                    }
                                    //endregion


// 还有mouseEntered/mouseExited/mousePressed/mouseReleased
                                });

                                FriendListForm.jLabelUserStateList.add(jLabelUserState);
                                FriendListForm.clientMessage.getFriendList().add(friendList.get(j));
                                jPanel.add(jLabelUser);
                                jPanel.add(jLabelUserState);
                                jPCenterTop.add(jPanel);
                                jPCenterTop.revalidate();
                            }
                        }

                    }
                    else if (serverMessage.getOperate().equals("DeleteFriend")){
                        JPanel jPanel=null ;
                        Component[] components = FriendListForm.jPanelCenter.getComponents();

                        for (int j=0;j<components.length;j++){
                            System.out.println(j);
                            System.out.println(components[j].getName());
                            if (components[j].getName().equals("MyFriend")){
                                JPanel temp = (JPanel) components[j];
                                JScrollPane tempJS = (JScrollPane)temp.getComponent(1);
                                jPanel=(JPanel) tempJS.getViewport().getView();
                                break;
                            }
                        }
                        Component[] componentsFriend = jPanel.getComponents();
                        for (int j=0;j<componentsFriend.length;j++){
                            JPanel jpFriend = (JPanel) componentsFriend[j];
                            if (Integer.parseInt(jpFriend.getName())==serverMessage.getUser().getAccount()){
                                jPanel.remove(jpFriend);
                                jPanel.repaint();
                                jPanel.revalidate();
                                break;
                            }
                        }
                    }

                    else if(serverMessage.getOperate().equals("FriendLogin")){
                        //收到好友登录的消息时启动
                        //更新jLable的内容
                        for (int i=0;i<FriendListForm.jLabelUserStateList.size();i++){
                            if (FriendListForm.jLabelUserStateList.get(i).getName().equals(Integer.toString(serverMessage.getUser().getAccount()))){
                                FriendListForm.jLabelUserStateList.get(i).setText("在线");
                            }
                        }
                        //更新好友的IP和port
                        for (int i=0;i<FriendListForm.clientMessage.getFriendList().size();i++){
                            if (FriendListForm.clientMessage.getFriendList().get(i).getFaccount()==serverMessage.getUser().getAccount()){
                                FriendListForm.clientMessage.getFriendList().get(i).setLport(serverMessage.getUser().getLport());
                                FriendListForm.clientMessage.getFriendList().get(i).setLip(serverMessage.getUser().getLip());
                                System.out.println(serverMessage.getUser().getAccount()+"ip和port地址更新");
                            }
                        }

                    }else if(serverMessage.getOperate().equals("FriendOut")){
                        //收到好友离线时启动
                        //更新jLable的内容
                        for (int i=0;i<FriendListForm.jLabelUserStateList.size();i++){
                            if (FriendListForm.jLabelUserStateList.get(i).getName().equals(Integer.toString(serverMessage.getUser().getAccount()))){
                                FriendListForm.jLabelUserStateList.get(i).setText("离线");
                            }
                        }
                        //好友离线时如果有主动连接的socket就关闭
                        for (int i=0;i<FriendListForm.p2pClientStreamList.size();i++){
                           if( FriendListForm.p2pClientStreamList.get(i).getLinkAccount() == serverMessage.getUser().getAccount()){
                               if(FriendListForm.p2pClientStreamList.get(i).getType()==1){
                                   System.out.println("与"+FriendListForm.p2pClientStreamList.get(i).getLinkAccount()+"连接关闭" );
                                   FriendListForm.p2pClientStreamList.get(i).getSocket().close();
                                   FriendListForm.p2pClientStreamList.remove(i);
                               }
                           }
                        }
                    }
                    else if (serverMessage.getOperate().equals("ApplyIntoGroup")){
                        System.out.println("接收到希望加入群聊的消息");
                        JPanel jPanel=null ;
                        Component[] components = FriendListForm.jPanelCenter.getComponents();

                        for (int j=0;j<components.length;j++){
                            System.out.println(j);
                            System.out.println(components[j].getName());
                            if (components[j].getName().equals("ApplyMsg")){
                                JPanel temp = (JPanel) components[j];
                                JScrollPane tempJS = (JScrollPane)temp.getComponent(1);
                                jPanel=(JPanel) tempJS.getViewport().getView();
                            }
                        }
                        JLabel jLabel = new JLabel(serverMessage.getUser().getName()+serverMessage.getUser().getAccount()+"希望加入群:"+serverMessage.getGroup().getGname());
                        jPanel.add(jLabel);
                        JPanel jPanelLS = jPanel;
                        jLabel.addMouseListener(new MouseListener(){
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int flag=JOptionPane.showConfirmDialog(null, "确定让该人加入群聊吗？", "添加群成员", JOptionPane.YES_NO_OPTION);
                                if(flag==JOptionPane.YES_OPTION){
                                    //发送同意申请的消息
                                    ClientMessage clientMessage = new ClientMessage();
                                    clientMessage.setUser(FriendListForm.clientMessage.getUser());
                                    clientMessage.setFriendAccount(serverMessage.getUser().getAccount());
                                    clientMessage.setGroup(serverMessage.getGroup());
                                    clientMessage.setOperate("IntoGroupAgree");
                                    jPanelLS.remove(jLabel);
                                    jPanelLS.repaint();
                                    jPanelLS.revalidate();
                                    try {
                                        obops.writeObject(clientMessage);
                                        obops.reset();
                                    }catch (IOException ioException){
                                        ioException.printStackTrace();
                                    }

                                }else if(flag==JOptionPane.NO_OPTION){
                                    jPanelLS.remove(jLabel);
                                    jPanelLS.repaint();
                                    jPanelLS.revalidate();
                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }
                        });

                        jPanel.revalidate();

                    }
                    else if (serverMessage.getOperate().equals("UploadGroupView")){
                        JPanel jPCCenterChild=null ;
                        Component[] components = FriendListForm.jPanelCenter.getComponents();

                        for (int j=0;j<components.length;j++){
                            System.out.println(j);
                            System.out.println(components[j].getName());
                            if (components[j].getName().equals("MyGroup")){
                                JPanel temp = (JPanel) components[j];
                                JScrollPane tempJS = (JScrollPane)temp.getComponent(1);
                                jPCCenterChild=(JPanel) tempJS.getViewport().getView();
                            }
                        }
                        List<Group> groupList = serverMessage.getGroupList();
                        for (int i = 0;i<groupList.size();i++){
                            if (groupList.get(i).getGnum() ==serverMessage.getGroup().getGnum()){


                            JPanel jPanel = new JPanel();
                            jPanel.setSize(300,100);
                            jPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            JLabel jLabelGroup = new JLabel(groupList.get(i).getGname(),JLabel.LEFT);
                            int groupAccount = groupList.get(i).getGnum();
                            jLabelGroup.setName(Integer.toString(groupList.get(i).getGnum()));
                            jLabelGroup.setText(groupList.get(i).getGname());

                            JTextPane jTextPane = new JTextPane();
                            jTextPane.setName(Integer.toString(groupList.get(i).getGnum()));
                            FriendListForm.jGroupTextPanelList.add(jTextPane);

                            jPanel.addMouseListener(new MouseListener() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    System.out.println(jTextPane.getName());
                                    GroupTextPaneForm imageTextPaneForm = new GroupTextPaneForm(jTextPane,groupAccount);

                                    System.out.println(groupAccount);
                                }

                                @Override
                                public void mousePressed(MouseEvent e) {

                                }

                                @Override
                                public void mouseReleased(MouseEvent e) {

                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {

                                }

                                @Override
                                public void mouseExited(MouseEvent e) {

                                }
                            });
                            jPanel.add(jLabelGroup);

                            jPCCenterChild.add(jPanel);
                                jPCCenterChild.revalidate();
                            }
                        }

                    }
                    else if(serverMessage.getOperate().equals("getGroupFileList")){
                        System.out.println("获得群文件列表");
                        System.out.println(serverMessage);
                        if(serverMessage.getUserFileList().size()!=0){
                            for (int i=0;i<FriendListForm.jGroupFilePanelList.size();i++){
                                if (FriendListForm.jGroupFilePanelList.get(i).getName().equals(
                                        Integer.toString(serverMessage.getUserFileList().get(0).getGnum()))){
                                    FriendListForm.jGroupFilePanelList.get(i).removeAll();
                                    FriendListForm.jGroupFilePanelList.get(i).repaint();
                                    System.out.println(serverMessage.getUserFileList());
                                    for (int j=0;j<serverMessage.getUserFileList().size();j++){
                                        String filename = serverMessage.getUserFileList().get(j).getFilename();
                                        int gnum = serverMessage.getUserFileList().get(j).getGnum();
                                        JLabel jLabel = new JLabel(filename);
                                        jLabel.setBorder(BorderFactory.createRaisedBevelBorder());
                                        jLabel.setPreferredSize(new Dimension(100, 20));
                                        jLabel.addMouseListener(new MouseListener(){
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                System.out.println("jLable触发");
                                                System.out.println(jLabel.getText());
                                                int i=JOptionPane.showConfirmDialog(null, "确定要下载吗？", "消息提示", JOptionPane.YES_NO_OPTION);
                                                if(i==JOptionPane.YES_OPTION){
                                                    ClientMessage cmsg = new ClientMessage();
                                                    UserFile userFile = new UserFile();
                                                    userFile.setFilename(filename);
                                                    userFile.setGnum(gnum);
                                                    cmsg.setUserFile(userFile);
                                                    cmsg.setOperate("downLoadGroupFile");
                                                    try{
                                                        obops.writeObject(cmsg);
                                                        //不重置输出流相同
                                                        obops.reset();
                                                    }catch (IOException ejf){
                                                        ejf.printStackTrace();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void mousePressed(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseReleased(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseEntered(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseExited(MouseEvent e) {

                                            }
                                        });
                                        FriendListForm.jGroupFilePanelList.get(i).add(jLabel);
                                    }
                                    FriendListForm.jGroupFilePanelList.get(i).revalidate();
                                }
                            }
                        }


                    }else if(serverMessage.getOperate().equals("downLoadGroupFile")){
                        System.out.println("获得下载的文件");
                        byte[] file_data = serverMessage.getUserFile().getFile_date();
                        int gnum=serverMessage.getUserFile().getGnum();
                        String filename = serverMessage.getUserFile().getFilename();
                        if(file_data!=null){
                            String filePath = "src/main/resources/fileDownload/groupFile/"+gnum+"/"+filename;
                            Utils.write_file_to_file(file_data,filePath);
                            JOptionPane.showConfirmDialog(null, "下载成功", "消息提示", JOptionPane.YES_NO_OPTION);

                        }else {
                            JOptionPane.showConfirmDialog(null, serverMessage.getMessage(), "消息提示", JOptionPane.YES_NO_OPTION);
                            System.out.println(serverMessage.getMessage());
                        }

                    }
                    else if(serverMessage.getOperate().equals("getGroupNumber")){
                        System.out.println("获得群成员");
                        System.out.println(serverMessage);
                        for (int i=0;i<FriendListForm.jGroupNumberPanelList.size();i++){
                            System.out.println("进入群成员循环");
                            if(FriendListForm.jGroupNumberPanelList.get(i).getName().equals(
                                    Integer.toString(serverMessage.getGroupList().get(0).getGnum())
                            )){
                                System.out.println(serverMessage.getGroupList().get(0).getGroupNumber());
                                List<User> groupNumberList = serverMessage.getGroupList().get(0).getGroupNumber();
                                System.out.println(groupNumberList);
                                FriendListForm.jGroupNumberPanelList.get(i).removeAll();
                                FriendListForm.jGroupNumberPanelList.get(i).repaint();
                                for (int j=0;j<groupNumberList.size();j++){
                                    JLabel jLabel = new JLabel(groupNumberList.get(j).getName());
                                    jLabel.setBorder(BorderFactory.createRaisedBevelBorder());
                                    FriendListForm.jGroupNumberPanelList.get(i).add(jLabel);
                                }
                                FriendListForm.jGroupNumberPanelList.get(i).revalidate();
                            }
                        }
                    }else if(serverMessage.getOperate().equals("sendGroupMessage")){
                        System.out.println("接收到群消息");
                        System.out.println(serverMessage);
                        for (int i=0;i<FriendListForm.jGroupTextPanelList.size();i++){
                            System.out.println(FriendListForm.jGroupTextPanelList.get(i).getName());

                            if (FriendListForm.jGroupTextPanelList.get(i).getName().equals(
                                    Integer.toString(serverMessage.getGroup().getGnum())
                            )){
                                System.out.println("进入if");
                                System.out.println(FriendListForm.jGroupTextPanelList.get(i).getName());
                                StyledDocument inputSDoc = serverMessage.getMessageSDoc();
                                StyledDocument outSDoc = FriendListForm.jGroupTextPanelList.get(i).getStyledDocument(); //获取欲输出的StyledDocument
                                List<Picture> pictureList = serverMessage.getPictureList();
                                for (int k = 0;k<pictureList.size();k++){
//                                String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
//                                String tempImgName = clientMessage.getUser().getAccount() + TimeNow;
                                    String filePath = "src/main/resources/imgDownload/"+pictureList.get(k).getP_name()+pictureList.get(k).getType();
                                    if(!Utils.write_picture_to_file(pictureList.get(k).getP_date(),filePath)){
                                        System.out.println("图片写入错误");
                                    }
                                }


                                for (int j = 0; j < inputSDoc.getLength(); j++) { //遍历读取的StyledDocument
                                    //System.out.println(i);
                                    if (inputSDoc.getCharacterElement(j).getName().equals("icon")) { //如果发现是icon元素，那么：
                                        Element ele = inputSDoc.getCharacterElement(j);
                                        ImageIcon icon = (ImageIcon) StyleConstants.getIcon(ele.getAttributes());
                                        System.out.println(ele.getAttributes());
                                        //Image newImage = icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
                                        String Load ;
                                        String filePath = null;
                                        for (int n=0;n<pictureList.size();n++){
                                            if (pictureList.get(n).getOldLoad().equals(icon.toString())){
                                                //icon = (ImageIcon) StyleConstants.getIcon(pictureList.get(n).getP_name());
                                                Load = "src/main/resources/imgDownload/"+pictureList.get(n).getP_name()+pictureList.get(n).getType();
                                                icon = new ImageIcon(Load);
                                                filePath = "src/main/resources/temp/img/"+pictureList.get(n).getP_name()+".jpg";
                                            }
                                        }
                                        Image img = icon.getImage() ;
                                        Image newImage = getScaledImage(img,200,200);

                                        BufferedImage blmage = (BufferedImage) newImage;
                                        BufferedImage bufferedImage = null;
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                                        try{
                                            // outputfile.createNewFile();
                                            //ImageIO.write(blmage,  "jpg",  outputfile);
                                            ImageIO.write(blmage,  "jpg",  baos);
                                            baos.flush();
                                            byte[] imageTByte = baos.toByteArray();
                                            baos.close();
                                            Utils.write_temp_picture_to_file(imageTByte,filePath);


                                            //bufferedImage = ImageIO.read(new File("d:\\"+"test1.jpg"));
                                        }catch (Exception c){
                                            c.printStackTrace();
                                        }
//                                    byte[] imageByte =  Utils.load_picture_from_file(icon.toString());
//                                    File outputfile = new File("d:\\"+"test1.jpg");
//
//                                    try{
//                                        ImageIO.write(blmage,  "jpg",  outputfile);
//                                        bufferedImage = ImageIO.read(new File("d:\\"+"test1.jpg"));
//                                    }catch (Exception c){
//                                        c.printStackTrace();
//                                    }
//                                    String filePath = "d:\\"+"test1.jpg";

                                        //Image newImage2 = bufferedImage;
                                        //newImage =(ImageIcon) StyleConstants.getIcon(bufferedImage.geta);

                                        //ImageIcon newicon = new ImageIcon(newImage2);
                                        //System.out.println(newicon.toString());
                                        //System.out.println(newicon2.toString());
                                        System.out.println(icon.toString());
                                        //System.out.println(icon.getIconHeight()+icon.getIconWidth());filePath
                                        //outputJTpane.insertIcon(new ImageIcon(newicon.toString()));//插入icon元素
                                        FriendListForm.jGroupTextPanelList.get(i).insertIcon(new ImageIcon(filePath));
                                        FriendListForm.jGroupTextPanelList.get(i).setCaretPosition(FriendListForm.jGroupTextPanelList.get(i).getDocument().getLength());
                                    } else {//如果不是icon（可以判断是文字，因为目前只有图片元素插入）
                                        try {
                                            String s = inputSDoc.getText(j, 1);
                                            outSDoc.insertString(FriendListForm.jGroupTextPanelList.get(i).getCaretPosition(), s, null);//从光标处插入文字
                                            FriendListForm.jGroupTextPanelList.get(i).setCaretPosition(FriendListForm.jGroupTextPanelList.get(i).getDocument().getLength());
                                        } catch (BadLocationException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                                try{
                                    outSDoc.insertString(FriendListForm.jGroupTextPanelList.get(i).getCaretPosition(),"\r\n",null);
                                    FriendListForm.jGroupTextPanelList.get(i).setCaretPosition(FriendListForm.jGroupTextPanelList.get(i).getDocument().getLength());
                                }catch (BadLocationException e2){
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }else if (serverMessage.getOperate().equals("LoginOut")){
                        instantMessage.interrupt();
                        System.out.println("instantMessage线程结束");
                    }else if (serverMessage.getOperate().equals("ServerMsg")){
                        System.out.println(serverMessage.getMessage());
                        int flag=JOptionPane.showConfirmDialog(null, serverMessage.getMessage(), "消息",JOptionPane.PLAIN_MESSAGE);
//                        if(flag==JOptionPane.YES_OPTION){
//                            //发送同意申请的消息
//                            ClientMessage clientMessage = new ClientMessage();
//                            clientMessage.setUser(FriendListForm.clientMessage.getUser());
//                            clientMessage.setFriendAccount(serverMessage.getUser().getAccount());
//                            clientMessage.setOperate("AgreeAdd");
//                            try {
//                                obops.writeObject(clientMessage);
//                            }catch (IOException ioException){
//                                ioException.printStackTrace();
//                            }
//
//                        }
                    }
                    //System.out.println(clientMessage);

                    //clientMessage = null;

                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            try{
                System.out.println("socket关闭");
                socket.close();
            }catch (IOException ioException){
                ioException.printStackTrace();
            }

        }
    }

    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();

        System.out.println("kkkkkk");

        //String message = chatClient.login(18145111,"123456");

        //System.out.println("最后message"+message);
//        chatClient.chat(tex);
//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            String str = scanner.nextLine();
//            chatClient.sendMessage(str);
//        }
    }
}
