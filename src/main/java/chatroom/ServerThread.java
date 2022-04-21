package chatroom;

import User.UserInfo;
import View.FriendListForm;
import com.sun.org.apache.xpath.internal.objects.XObject;
import pojo.*;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.Utils;
import utils.UtilsServer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket client;
    private OutputStream ops;
    private UserService userService;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        processSocket();
    }

    public void sendMsg2Me(String msg) {
        try {
            msg += "\r\n";
            DataOutputStream ops = new DataOutputStream(client.getOutputStream());
            ops.writeUTF(msg);
            ops.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processSocket() {
        //sendMsg2Me("welcome to the chat room,please enter your name");
        try {

            System.out.println("processSocket启动");

            InputStream ins;
            ins = client.getInputStream();
            ops = client.getOutputStream();
            DataInputStream dains = new DataInputStream(ins);
            ObjectInputStream obins = new ObjectInputStream(ins);
            ObjectOutputStream obops = new ObjectOutputStream(ops);
            SocketStream socketStream = new SocketStream();

            boolean flag = true;


            //String msg = ins.readUTF();
            while (flag) {
//                    ChatTool.sendMsg2All(name,msg);
//                    msg=ins.readUTF();

                System.out.println("循环开始");
                Object object = obins.readObject();
                ClientMessage clientMessage = (ClientMessage) object;
                //System.out.println(clientMessage.toString());
                //ClientMessage clientMessage1 = new ClientMessage();
                //clientMessage1.setMessage("123456");

                //obops.writeObject(clientMessage);

                switch (clientMessage.getOperate()) {


                    /**
                     * 根据获得的操作符判断进行什么操作
                     * login通过账号和密码获取登录状态和好友列表，群，自定义分组等信息
                     */
                    case "Login": {
                        System.out.println("开始login");
                        User clientUser = clientMessage.getUser();

                        System.out.println(clientUser.toString());

                        userService = new UserServiceImpl();
                        User user = userService.login(clientUser.getAccount(), clientUser.getPassword());
                        ClientMessage serverMessage = new ClientMessage();
                        if (user != null) {

                            System.out.println("user!=null");

//                            if (user.getPassword().equals(clientUser.getPassword())) {
//
//                                System.out.println("密码正确");

                            if (user.getLstatus() == 0) {

                                System.out.println("账号未登录");

//                                        InetAddress inetAddress = client.getInetAddress();
//
//                                        System.out.println("inetAddress:"+inetAddress.toString());
//
//                                        System.out.println("getRemoteSocketAddress:"+client.getRemoteSocketAddress().toString());
//                                        System.out.println("getport:"+client.getPort());
//                                        System.out.println("getHostAddd:"+client.getInetAddress().getHostAddress());

                                int userPort = client.getPort();
                                String userIp = client.getInetAddress().getHostAddress();

                                userService.updateIpPort(user.getAccount(), userIp, userPort);

                                user.setLip(userIp);
                                user.setLport(userPort);

                                serverMessage.setUser(user);
                                List<Friend> friendList = userService.getAllFriendByAccount(user.getAccount());
                                serverMessage.setFriendList(friendList);

                                List<SubGroup> subGroupList = userService.getSubGroupByAccount(user.getAccount());
                                serverMessage.setSubGroupList(subGroupList);

                                List<Group> groupList = userService.getGroupByAccount(user.getAccount());
                                serverMessage.setGroupList(groupList);

                                socketStream.setSocket(client);
                                socketStream.setObops(obops);
                                ChatTool.addSocket(socketStream);

                                serverMessage.setMessage("登录成功");
                                //将上线消息发送给好友
                                ChatTool.sendMsgAllFriend(friendList, user,"FriendLogin",null);

                                obops.writeObject(serverMessage);

                                try{
                                    sleep(1000);
                                   // wait(1000);
                                }catch (Exception ewait){
                                    ewait.printStackTrace();
                                }

                                for (int i=0;i<ChatServer.clientMessageList.size();i++){
                                    if (ChatServer.clientMessageList.get(i).getFriendAccount() == serverMessage.getUser().getAccount()){
                                        obops.writeObject(ChatServer.clientMessageList.get(i));
                                        ChatServer.clientMessageList.remove(i);
                                    }
                                }



                            } else {
                                serverMessage.setMessage("账号已登录");
                                obops.writeObject(serverMessage);
                                flag = false;
                            }
//                            }
//                        else {
//                                serverMessage.setMessage("密码错误");
//                                obops.writeObject(serverMessage);
//                                flag = false;
//                            }
                        } else {
                            serverMessage.setMessage("账号或密码错误");
                            obops.writeObject(serverMessage);
                            flag = false;
                        }
                        break;
                    }
                    case "LoginOut": {
                        System.out.println("开始退出");
                        User user = clientMessage.getUser();
                        List<Friend> friendList = userService.getAllFriendByAccount(user.getAccount());
                        ChatTool.sendMsgAllFriend(friendList, user,"FriendOut",null);
                        userService.loginOut(user.getAccount());
                        ChatTool.delSocket(socketStream);
                        ClientMessage endMessage = new ClientMessage();
                        endMessage.setOperate("LoginOut");
                        obops.writeObject(endMessage);
                        client.close();
                        flag=false;
                        break;
                    }

                    case "SendMessage":{
                        System.out.println("开始发送消息");
                        int friendAccount = clientMessage.getFriendAccount();
                        User fuser = userService.getIpPortByAccount(friendAccount);
                        System.out.println("线程中:"+fuser);
                        clientMessage.getUser().setLip(fuser.getLip());
                        clientMessage.getUser().setLport(fuser.getLport());
                        System.out.println("clientmessage:"+clientMessage);
                        for (int i = 0 ; i < clientMessage.getMessageSDoc().getLength();i++){
                            System.out.println("MessageSDoc:");
                            try{
                                System.out.println(clientMessage.getMessageSDoc().getText(i,1));
                            }catch (Exception e4){
                                e4.printStackTrace();
                            }
                        }

                        //此时user的name为发送者，IP和port为接收者
                        ChatTool.sendMsgFriend(clientMessage);
                        break;
                    }
                    case "getFriendIpPort":{
                        System.out.println("开始获取查询好友的Ip和Port");
                        int friendAccount = clientMessage.getFriendAccount();
                        User user = userService.getIpPortByAccount(friendAccount);
                        user.setAccount(friendAccount);
                        ClientMessage serverMessage = new ClientMessage();
                        serverMessage.setUser(user);
                        serverMessage.setOperate("getFriendIpPort");
                        obops.writeObject(serverMessage);
                        break;
                    }
                    case "getGroupFileList":{
                        System.out.println("开始查询群文件列表");
                        int groupAccount = clientMessage.getGroupList().get(0).getGnum();
                        List<UserFile> fileList =  userService.getGroupFileList(groupAccount);
                        ClientMessage serverMessage = new ClientMessage();
                        serverMessage.setOperate("getGroupFileList");
                        serverMessage.setUserFileList(fileList);
                        obops.writeObject(serverMessage);
                        break;

                    }
                    case "AddFriend":{
                        System.out.println("发送好友申请");
                        Boolean sign = false;
                        int friendAccount = clientMessage.getFriendAccount();
                       // UserService userService = new  UserServiceImpl();
                        User fuser = userService.getIpPortByAccount(friendAccount);
                        if (fuser==null){
                            System.out.println("该账号不存在");
                            ClientMessage serverMessage = new ClientMessage();
                            serverMessage.setOperate("ServerMsg");
                            serverMessage.setMessage("该账号不存在");
                            obops.writeObject(serverMessage);
                            break;
                        }
                        List<Integer> friendList = userService.getFriendAccountByAccount(clientMessage.getUser().getAccount());

                        for (int i =0;i<friendList.size();i++){
                            if (friendAccount == friendList.get(i)){
                                System.out.println("已是好友");
                                ClientMessage serverMessage = new ClientMessage();
                                serverMessage.setOperate("ServerMsg");
                                serverMessage.setMessage("已是好友");
                                obops.writeObject(serverMessage);
                                sign = true;
                                break;
                            }
                        }
                        if (sign){
                            break;
                        }
                        System.out.println("asd");
                        System.out.println("线程中:"+fuser);
                        clientMessage.getUser().setLip(fuser.getLip());
                        clientMessage.getUser().setLport(fuser.getLport());
                        System.out.println("clientmessage:"+clientMessage);
                        if (ChatTool.sendMsgFriend(clientMessage)){
                            System.out.println("好友申请发送成功");

                        }else {
                            System.out.println("申请加入离线列表");
                            ChatServer.clientMessageList.add(clientMessage);
                        }
                        ClientMessage serverMessage = new ClientMessage();
                        serverMessage.setOperate("ServerMsg");
                        serverMessage.setMessage("好友申请发送成功");
                        obops.writeObject(serverMessage);

                        break;
                    }
                    case "AgreeAdd":{
                        Boolean sign = false;
                        int friendAccount = clientMessage.getFriendAccount();
                        List<Integer> friendList = userService.getFriendAccountByAccount(clientMessage.getUser().getAccount());
                        for (int i =0;i<friendList.size();i++){
                            if (friendAccount == friendList.get(i)){
                                System.out.println("已是好友");
                                ClientMessage serverMessage = new ClientMessage();
                                serverMessage.setOperate("ServerMsg");
                                serverMessage.setMessage("已是好友");
                                obops.writeObject(serverMessage);
                                sign = true;
                                break;
                            }
                        }
                        if (sign){
                            break;
                        }
                        System.out.println("好友申请通过");
                        if (userService.insertFriendRelation(clientMessage.getUser().getAccount(),clientMessage.getFriendAccount(),1)){
                            System.out.println("添加成功");
                        }
                        if (userService.insertFriendRelation(clientMessage.getFriendAccount(),clientMessage.getUser().getAccount(),1)){
                            System.out.println("添加成功");
                        }
                        User fuser = userService.getIpPortByAccount(friendAccount);
                        System.out.println("线程中:"+fuser);
                        clientMessage.getUser().setLip(fuser.getLip());
                        clientMessage.getUser().setLport(fuser.getLport());
                        ClientMessage serverMessage = new ClientMessage();
                        serverMessage.setUser(clientMessage.getUser());
                        serverMessage.setOperate("AddFriendSuccess");
                        serverMessage.setFriendAccount(friendAccount);
                        serverMessage.setFriendList(userService.getAllFriendByAccount(friendAccount));
                        ChatTool.sendMsgFriend(serverMessage);

                        User userfd = new User();
                        userfd.setAccount(friendAccount);
                        ClientMessage serverMsg = new ClientMessage();
                        serverMsg.setUser(userfd);
                        serverMsg.setFriendAccount(clientMessage.getUser().getAccount());
                        serverMsg.setOperate("AddFriendSuccess");
                        serverMsg.setFriendList(userService.getAllFriendByAccount(clientMessage.getUser().getAccount()));
                        obops.writeObject(serverMsg);
                        break;
                    }
                    case "DeleteFriend":{
                        ClientMessage serverMessage = new ClientMessage();
                        ClientMessage serverMsg = new ClientMessage();
                        int uaccount = clientMessage.getUser().getAccount();
                        int faccount = clientMessage.getFriendAccount();
                        try{
                            if (userService.deleteFriendByAccount(uaccount,faccount)){
                                if (userService.deleteFriendByAccount(faccount,uaccount)){
                                    serverMessage.setOperate("DeleteFriend");
                                    User fuser = userService.getIpPortByAccount(faccount);
                                    serverMessage.setUser(clientMessage.getUser());
                                    serverMessage.getUser().setLip(fuser.getLip());
                                    serverMessage.getUser().setLport(fuser.getLport());
                                    serverMessage.setFriendAccount(clientMessage.getFriendAccount());
                                    ChatTool.sendMsgFriend(serverMessage);

                                    serverMsg.setOperate("DeleteFriend");
                                    User uuser = new User();
                                    uuser.setAccount(faccount);
                                    serverMsg.setUser(uuser);
                                    serverMsg.setFriendAccount(faccount);
                                    obops.writeObject(serverMsg);
                                    obops.reset();
                                    break;
                                }
                            }

                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                        serverMsg.setOperate("ServerMsg");
                        serverMsg.setMessage("好友删除失败");
                        break;

                    }
                    case "AddGroup":{
                        int gid=0;
                        try{
                            gid = userService.addGroup(clientMessage.getGroup().getGname());
                            userService.addGroupNumber(clientMessage.getUser().getAccount(),0,gid);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }

                        if (gid!=0){
                            Group group = userService.getGroupById(gid);
                            ClientMessage serverMessage = new ClientMessage();
                            serverMessage.setOperate("UploadGroupView");
                            serverMessage.setGroup(group);
                            List<Group> groupList = new ArrayList<>();
                            groupList.add(group);
                            serverMessage.setGroupList(groupList);
                            obops.writeObject(serverMessage);
                        }else {
                            ClientMessage serverMessage = new ClientMessage();
                            serverMessage.setOperate("ServerMsg");
                            serverMessage.setMessage("创建失败");
                            obops.writeObject(serverMessage);
                        }
                        break;

                    }
                    case "ApplyIntoGroup":{
                        List<User> groupUserList = userService.getGroupNumber(clientMessage.getGroup().getGnum());
                        Group group = userService.getGroupById(clientMessage.getGroup().getGnum());
                        System.out.println(group.toString());
                        if (group!=null){
                            for (int i=0;i<groupUserList.size();i++){
                                if (groupUserList.get(i).getUgnum()==0){
                                    User fuser = userService.getIpPortByAccount(groupUserList.get(i).getAccount());
                                    ClientMessage serverMessage = new ClientMessage();
                                    serverMessage.setOperate("ApplyIntoGroup");
                                    serverMessage.setUser(clientMessage.getUser());
                                    serverMessage.setFriendAccount(groupUserList.get(i).getAccount());
                                    serverMessage.getUser().setLport(fuser.getLport());
                                    serverMessage.getUser().setLip(fuser.getLip());
                                    serverMessage.setGroup(group);
                                    ChatTool.sendMsgFriend(serverMessage);
                                    break;
                                }
                            }
                            break;
                        }else {
                            ClientMessage serverMessage = new ClientMessage();
                            serverMessage.setOperate("ServerMsg");
                            serverMessage.setMessage("该群不存在");
                            obops.writeObject(serverMessage);
                        }

                        break;
                    }
                    case "IntoGroupAgree":{
                        ClientMessage serverMessage = new ClientMessage();
                        serverMessage.setOperate("getGroupNumber");
                        List<User> groupUserList;
                        if (userService.addGroupNumber(clientMessage.getFriendAccount(),2,clientMessage.getGroup().getGnum())){
                            groupUserList= userService.getGroupNumber(clientMessage.getGroup().getGnum());
                            for (int i=0;i<groupUserList.size();i++){

                                User user = userService.getIpPortByAccount(groupUserList.get(i).getAccount());
                                groupUserList.get(i).setLip(user.getLip());
                                groupUserList.get(i).setLport(user.getLport());
                                if (groupUserList.get(i).getAccount() == clientMessage.getFriendAccount()){
                                    ClientMessage sMsg = new ClientMessage();
                                    sMsg.setOperate("UploadGroupView");
                                    sMsg.setGroupList(userService.getGroupByAccount(clientMessage.getFriendAccount()));
                                    sMsg.setFriendAccount(clientMessage.getFriendAccount());
                                    sMsg.setUser(clientMessage.getUser());
                                    sMsg.getUser().setLport(user.getLport());
                                    sMsg.getUser().setLip(user.getLip());
                                    sMsg.setGroup(clientMessage.getGroup());
                                    ChatTool.sendMsgFriend(sMsg);
                                }

                            }

                            Group group = new Group();
                            group.setGnum(clientMessage.getGroup().getGnum());
                            group.setGroupNumber(groupUserList);
                            List<Group> groupList = new ArrayList<Group>();
                            groupList.add(group);
                            serverMessage.setGroupList(groupList);
                            serverMessage.setGroup(group);
                            ChatTool.sendMsgGroup(serverMessage);

                        }else {
                            serverMessage.setOperate("ServerMsg");
                            serverMessage.setMessage("添加群成员失败");
                            obops.writeObject(serverMessage);
                        }
                        break;
                    }
//                    case "UploadFile":{
//                        System.out.println("开始上传文件的操作");
//                        int groupNumber = clientMessage.getGroup().getGnum();
//                        List<UserFile> userFileList = clientMessage.getUserFileList();
//                        for (int i = 0 ;i<userFileList.size();i++){
//                             UserFile userFile =  userFileList.get(i);
//                             String filePath = "src/main/resources/userFileSave/groupFile/"+userFile.getGnum()+"/"+userFile.getFilename();
//                             if(UtilsServer.write_file_to_file(userFile.getFile_date(),filePath)){
//                                 System.out.println("群文件写入成功");
//                                 userService.insertGroupFileInfo(userFile.getGnum(),filePath,userFile.getFilename());
//                             }
//                        }
//                        ClientMessage serverMessage = new ClientMessage();
//                        serverMessage.setUserFileList(userService.getGroupFileList(groupNumber));
//                        serverMessage.setOperate("getGroupFileList");
//                        obops.writeObject(serverMessage);
//                        break;
//                    }
                    case "UploadFile":{
                        System.out.println("开始上传文件的操作");
                        //交给UploadFile线程去做不影响其他使用
                        UploadFileThread uploadFileThread=  new UploadFileThread(clientMessage,obops);
                        uploadFileThread.start();

                        break;
                    }
                    case "downLoadGroupFile":{
                        System.out.println("开始下载群文件");
//                        ClientMessage serverMessage = new ClientMessage();
//                        serverMessage.setOperate("downLoadGroupFile");
//                        int gnum = clientMessage.getUserFile().getGnum();
//                        String filename = clientMessage.getUserFile().getFilename();
//                        String filePath = "src/main/resources/userFileSave/groupFile/"+gnum+"/"+filename;
//                        byte[] file_date = UtilsServer.load_file_from_file(filePath);
//                        if (file_date!=null){
//                            System.out.println("群文件读取成功");
//                            UserFile userFile = new UserFile();
//                            userFile.setGnum(gnum);
//                            userFile.setFile_date(file_date);
//                            userFile.setFilename(filename);
//                            serverMessage.setMessage("下载成功");
//                            serverMessage.setUserFile(userFile);
//                        }else {
//                            System.out.println("群文件读取失败");
//                            serverMessage.setMessage("文件读取失败");
//                        }
//                        obops.writeObject(serverMessage);
//                        break;
                        DownLoadGroupFileThread downLoadGroupFileThread = new DownLoadGroupFileThread(clientMessage,obops);
                        downLoadGroupFileThread.start();
                        break;

                    }
                    case "getGroupNumber":{
                        System.out.println("开始获取群成员的操作");
                        int groupNumber = clientMessage.getGroupList().get(0).getGnum();
                        List<User> groupNumberList = userService.getGroupNumber(groupNumber);
                        ClientMessage serverMessage = new ClientMessage();
                        Group group = new Group();
                        group.setGnum(groupNumber);
                        group.setGroupNumber(groupNumberList);
                        List<Group> groupList = new ArrayList<Group>();
                        groupList.add(group);
                        serverMessage.setGroupList(groupList);
                        serverMessage.setOperate("getGroupNumber");
                        obops.writeObject(serverMessage);
                        break;
                    }

                    case "sendGroupMessage":{
                        System.out.println("开始发送群消息");
                        int groupNumber = clientMessage.getGroup().getGnum();
                        List<User> groupNumberList = userService.getGroupNumber(groupNumber);
                        //System.out.println(groupNumberList);
                        for (int i=0;i<groupNumberList.size();i++){
                            int account = groupNumberList.get(i).getAccount();
                            if(account!=clientMessage.getUser().getAccount()){
                                User user = userService.getIpPortByAccount(account);
                                groupNumberList.get(i).setLip(user.getLip());
                                groupNumberList.get(i).setLport(user.getLport());
                            }

                        }
                        clientMessage.getGroup().setGroupNumber(groupNumberList);
                        ChatTool.sendMsgGroup(clientMessage);
                        break;

                    }

//                    case "Test2":{
//                        System.out.println("开始测试2");
//                        for (int i = 0; i<clientMessage.getPictureList().size();i++){
//                            Picture picture = clientMessage.getPictureList().get(i);
//                            System.out.println(i);
//                            boolean b = Utils.write_picture_to_file(picture.getP_date(),picture.getP_name(),i);
//                            System.out.println(b);
//                        }
//
//                    }
                }
            }

            System.out.println("脱离循环");


            //System.out.println();


        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
//        finally {
//            ChatTool.delSocket(client);
//            try {
//                client.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            return;
//        }
    }
    class UploadFileThread extends Thread{
        private String ip;
        private int port;
        private Socket socket;
        private OutputStream ops;
        private InputStream ips;
        private ObjectOutputStream obOps,mainObOps;
        private ObjectInputStream obIps;
        private DataInputStream daIps;
        private DataOutputStream daOps;
        private String file_road;
        private int gnum;
        private String file_name;
        public UploadFileThread(ClientMessage clientMessage,ObjectOutputStream mainObOps){
            try{
                this.mainObOps = mainObOps;
                this.file_road = clientMessage.getUserFile().getFile_load();
                this.gnum = clientMessage.getUserFile().getGnum();
                this.file_name = clientMessage.getUserFile().getFilename();
                this.ip = clientMessage.getUser().getLip();
                this.port = clientMessage.getUser().getLport()+100;

                socket = new Socket(ip,port);
                ops = socket.getOutputStream();
                ips = socket.getInputStream();
                obOps = new ObjectOutputStream(ops);
                obIps = new ObjectInputStream(ips);
                daIps = new DataInputStream(ips);
                daOps = new DataOutputStream(ops);

            }catch (Exception exception){
                exception.printStackTrace();
            }

        }

        @Override
        public void run() {
            try{
                System.out.println("下载线程开启");
                ClientMessage serverMessage = new ClientMessage();
                serverMessage.setOperate("SDLFile");
                UserFile userFile = new UserFile();
                userFile.setFile_load(file_road);
                serverMessage.setUserFile(userFile);
              //  Long all_size = daIps.readLong();
                obOps.writeObject(serverMessage);

                String filePath = "src/main/resources/userFileSave/groupFile/"+gnum+"/"+file_name;
                File file = new File(filePath);
                File fileParent = file.getParentFile();
                if(!fileParent.exists()){
                    fileParent.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                long read_size = 0;
                double percentage = 0;//百分比
                int len = 0;//每次接收的大小
                byte[] bytes = new byte[1024];
                byte[] bytes2 = new byte[1];
               // ins.read(bytes2);
                long filesize = daIps.readLong();//获取文件大小
                System.out.println(filesize);
                while((len = ips.read(bytes)) != -1){
                    System.out.println("len:"+len);
                    read_size += len;
                    System.out.println("read_size:"+read_size);

                    fileOutputStream.write(bytes,0,len);
                    fileOutputStream.flush();
                    if (read_size==filesize){
                        System.out.println("下载完成");
                        userService.insertGroupFileInfo(gnum,filePath,file_name);
                        break;
                    }

                }
                fileOutputStream.close();

                ClientMessage serverMsg = new ClientMessage();
                serverMsg.setUserFileList(userService.getGroupFileList(gnum));
                serverMsg.setOperate("getGroupFileList");
                mainObOps.writeObject(serverMsg);
                socket.close();

            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }
    class DownLoadGroupFileThread extends Thread{
        private String ip;
        private int port;
        private Socket socket;
        private OutputStream ops;
        private InputStream ips;
        private ObjectOutputStream obOps,mainObOps;
        private ObjectInputStream obIps;
        private DataInputStream daIps;
        private DataOutputStream daOps;
        private String file_road;
        private int gnum;
        private String file_name;
        public DownLoadGroupFileThread(ClientMessage clientMessage,ObjectOutputStream mainObOps){
            try{
                this.mainObOps = mainObOps;

                this.gnum = clientMessage.getUserFile().getGnum();
                this.file_name = clientMessage.getUserFile().getFilename();
                this.file_road = "src/main/resources/userFileSave/groupFile/"+gnum+"/"+file_name;
                this.ip = clientMessage.getUser().getLip();
                this.port = clientMessage.getUser().getLport()+100;

                socket = new Socket(ip,port);
                ops = socket.getOutputStream();
                ips = socket.getInputStream();
                obOps = new ObjectOutputStream(ops);
                obIps = new ObjectInputStream(ips);
                daIps = new DataInputStream(ips);
                daOps = new DataOutputStream(ops);

            }catch (Exception exception){
                exception.printStackTrace();
            }

        }

        @Override
        public void run() {
            try{
                System.out.println("发送线程开启");
                ClientMessage serverMessage = new ClientMessage();
                serverMessage.setOperate("SUPFile");
                UserFile userFile = new UserFile();
                userFile.setFile_load(file_road);
                serverMessage.setUserFile(userFile);
                //  Long all_size = daIps.readLong();
                obOps.writeObject(serverMessage);

                //String filePath = "src/main/resources/userFileSave/groupFile/"+gnum+"/"+file_name;
                File file = new File(file_road);
                long filesize =  file.length();//获取文件大小
                FileInputStream fileInputStream = new FileInputStream(file);
                System.out.println(filesize);
                daOps.writeLong(filesize);

                long read_size = 0;
                double percentage = 0;//百分比
                int len = 0;//每次接收的大小
                byte[] bytes = new byte[1024];
                byte[] bytes2 = new byte[1];
                // ins.read(bytes2);

                //System.out.println(filesize);
                while((len = fileInputStream.read(bytes)) != -1){
                    //System.out.println("len:"+len);
                    read_size += len;
                    //System.out.println("read_size:"+read_size);

                    ops.write(bytes,0,len);
                    ops.flush();
                    if (read_size==filesize){
                        System.out.println("发送完成");
                        break;
                    }

                }
                fileInputStream.close();

//                ClientMessage serverMsg = new ClientMessage();
//                serverMsg.setUserFileList(userService.getGroupFileList(gnum));
//                serverMsg.setOperate("getGroupFileList");
//                mainObOps.writeObject(serverMsg);
//                socket.close();

            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

}
