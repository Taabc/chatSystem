package chatroom;
import pojo.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天工具类
 */
public class ChatTool {
    //保存每1个登录成功的客户机  用于 服务器给all转发消息
    private static ArrayList<SocketStream> clientStreamList=new ArrayList<SocketStream>();
    //此处集合中为  ServerThread  和 Socket 的区别？
    //虽然仅用到 Socket感觉就能达到群发信息的目的，放前者更利于扩展吧  且是面向对象的编程
    //此处能实现功能 就懒得改了

    public static int getSocketNum(){
        return clientStreamList.size();
    }


    /**
     * 便于 验证成功后  加入客户机
     * @param clientStream
     */
    public static void addSocket(SocketStream clientStream) throws IOException {
        clientStreamList.add(clientStream);
        System.out.println("新客户机已加入集合");
       // sendMsg2All("account","I am online!The current number of people online is "+clientList.size());

    }

//    /**
//     * 群发=遍历list中的all元素， 对每个元素 写出
//     * @param msg
//     * @throws IOException
//     */
//    public static void sendMsg2All(String username,String msg) throws IOException {
//        for (int i = 0; i <clientList.size() ; i++) {
//            DataOutputStream ops = new DataOutputStream(clientList.get(i).getOutputStream());
//            ops.writeUTF(username+" said:"+msg+"\r\n");
//            ops.flush();
//        }
//    }
    /**
     * 账号上线时通过好友列表给该账号好友发送通知
     */
    public static void sendMsgAllFriend(List<Friend> friendList, User user,String operate,String message) throws IOException{
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setUser(user);
        clientMessage.setOperate(operate);
        clientMessage.setMessage(message);

        for (int i = 0; i < friendList.size(); i++){
            String ip = friendList.get(i).getLip();
            int port = friendList.get(i).getLport();

            for (int k = 0; k < clientStreamList.size(); k++){
                if(clientStreamList.get(k).getSocket().getInetAddress().getHostAddress().equals(ip) && clientStreamList.get(k).getSocket().getPort() == port){

                    clientStreamList.get(k).getObops().writeObject(clientMessage);
                    //ops.writeUTF(username+" said:"+msg+"\r\n");

                }
            }
        }

    }

    /**
     *好友单独私聊
     */
    public static boolean sendMsgFriend(ClientMessage clientMessage) throws IOException{
        String ip = clientMessage.getUser().getLip();
        int port = clientMessage.getUser().getLport();
        System.out.println("ip:"+ip);
        System.out.println("port"+port);
        //clientMessage.setOperate("FriendMessage");
        for (int i = 0; i < clientStreamList.size(); i++){
            String cip = clientStreamList.get(i).getSocket().getInetAddress().getHostAddress();
            int cport = clientStreamList.get(i).getSocket().getPort();
            System.out.println("cip:"+cip+"\n"+"cport:"+cport);
            if(cip.equals(ip) && cport == port){
                System.out.println("配队成功");
                clientStreamList.get(i).getObops().writeObject(clientMessage);
                clientStreamList.get(i).getObops().reset();
                return true;
                //break;
                //ops.writeUTF(username+" said:"+msg+"\r\n");
            }
        }
        return false;
    }
    public static void sendMsgGroup(ClientMessage clientMessage) throws IOException{
        List<User> userList = clientMessage.getGroup().getGroupNumber();
        for (int i=0;i<userList.size();i++){
            String ip = userList.get(i).getLip();
            int port = userList.get(i).getLport();
            for (int j=0;j<clientStreamList.size();j++){
                String cip = clientStreamList.get(j).getSocket().getInetAddress().getHostAddress();
                int cport = clientStreamList.get(j).getSocket().getPort();
                System.out.println("cip:"+cip+"\n"+"cport:"+cport);
                if (cip.equals(ip)&&cport == port){
                    System.out.println("配队成功");
                    clientStreamList.get(j).getObops().writeObject(clientMessage);
                }

            }
        }
    }

    /**
     * 通过QQ账号获取对应的Client
     */
//    public static Socket getSocketByAc(int account){
//        Socket socket;
//        for (int i = 0; i< clientList.size(); i++){
//            if (clientList.get(i).getAccount() == account){
//                socket = clientList.get(i).getSocket();
//                return socket;
//            }
//        }
//        return null;
//    }
    /**
     * 根据在同一群内的account列表 得到群员的Client的列表
     */
//    public static List<Socket> getGroupNumByAc(List<Integer> groupMemList) throws IOException{
//
//        List<Socket> sockets = new ArrayList<Socket>();
//        for (int i = 0; i < groupMemList.size(); i++){
//            for (int k = 0; k < clientList.size(); k++){
//                if (clientList.get(k).getAccount() == groupMemList.get(i)){
//                    sockets.add(clientList.get(i).getSocket());
//                    break;
//                }
//            }
//        }
//        return sockets;
//    }




    /**
     * 根据Client列表群发消息
     */
    public static void sendMsgGroup(int account,String msg, List<Socket> sockets) throws IOException{
        for (int i = 0 ;i < sockets.size();i++){
            DataOutputStream ops = new DataOutputStream(sockets.get(i).getOutputStream());
            ops.writeUTF(account + ":" +msg);
            ops.flush();
        }
    }

    /**
     * 下线时删除
     * @param socketStream
     */
    public static void delSocket(SocketStream socketStream){
        clientStreamList.remove(socketStream);
    }

}


