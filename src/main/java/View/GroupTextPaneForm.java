package View;

import chatroom.ChatClient;
import chatroom.ChatTool;
import pojo.ClientMessage;
import pojo.Group;
import pojo.User;
import pojo.UserFile;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupTextPaneForm extends JFrame {
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


    public GroupTextPaneForm(JTextPane jTextPane1,int groupId ) {

        System.out.println("");
        this.jTextPane1 = jTextPane1;
        initComponents(jTextPane1,groupId);
        setSize(700,500);
        setVisible(true);
//        try{
//            start(new Stage());
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        System.out.println("初始化");
        GroupTextPaneController.add2(groupId,jTextPane2, jTextPane1, jButton2, jButton1, jFrame);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(JTextPane jTextPane1,int groupId) {
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



        javax.swing.GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        GroupLayout.SequentialGroup hSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jButton1).addComponent(jButton2).addComponent(jButton3);

        GroupLayout.ParallelGroup hParalGroup = jPanel1Layout.createParallelGroup().addComponent(jScrollPane2).addGroup(hSeqGroup);

        jPanel1Layout.setHorizontalGroup(hParalGroup);

        GroupLayout.ParallelGroup vParaGroup = jPanel1Layout.createParallelGroup().addComponent(jButton1).addComponent(jButton2).addComponent(jButton3);

        GroupLayout.SequentialGroup vSeqGroup = jPanel1Layout.createSequentialGroup().addComponent(jScrollPane2).addGroup(vParaGroup);

        jPanel1Layout.setVerticalGroup(vSeqGroup);




        jButton2.setText("插入图片");

        jButton1.setText("发送");

        jButton3.setText("上传文件");

//        jButton3.addActionListener((ActionEvent e) -> {
//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            int result = chooser.showOpenDialog(null);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                System.out.println(chooser.getSelectedFile().toString());
//                String fileRoad = chooser.getSelectedFile().toString();
//                byte[] file_date =Utils.load_picture_from_file(fileRoad);
//                ClientMessage cmsg = new ClientMessage();
//                cmsg.setUser(FriendListForm.clientMessage.getUser());
//                Group group = new Group();
//                group.setGnum(groupId);
//                cmsg.setGroup(group);
//                cmsg.setOperate("UploadFile");
//                UserFile userFile = new UserFile();
//                userFile.setFile_date(file_date);
//                userFile.setGnum(groupId);
//                String type;
//                type=fileRoad.substring(fileRoad.length()-4,fileRoad.length());
//                String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
//                userFile.setFilename(groupId+TimeNow+type);
//                List<UserFile> userFileList = new ArrayList<UserFile>();
//                userFileList.add(userFile);
//                cmsg.setUserFileList(userFileList);
//                FriendListForm.chatClient.sendMessage(cmsg);
//            }
//
//        });
        jButton3.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println(chooser.getSelectedFile().toString());
                String fileRoad = chooser.getSelectedFile().toString();
                File file = chooser.getSelectedFile();
                //byte[] file_date =Utils.load_picture_from_file(fileRoad);
                ClientMessage cmsg = new ClientMessage();
                cmsg.setUser(FriendListForm.clientMessage.getUser());
                Group group = new Group();
                group.setGnum(groupId);
                cmsg.setGroup(group);

                cmsg.setOperate("UploadFile");
                UserFile userFile = new UserFile();
                //userFile.setFile_date(file_date);
                userFile.setGnum(groupId);
                userFile.setFile_load(fileRoad);
                String type;
                type=fileRoad.substring(fileRoad.length()-4,fileRoad.length());
                String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
                userFile.setFilename(groupId+TimeNow+type);
                List<UserFile> userFileList = new ArrayList<UserFile>();
                userFileList.add(userFile);
                //cmsg.setUserFileList(userFileList);
                cmsg.setUserFile(userFile);
                FriendListForm.chatClient.sendMessage(cmsg);
              //  FriendListForm.chatClient.sendFile_date(file);

            }

        });

        jSplitPaneRight = new JSplitPane();
        jPanelRight = new JPanel();
//        jTextGroupFile = new JPanel();
//        jTextNumberList = new JPanel();
        jPanelGroupFile = new JPanel(new GridLayout(0, 1, 4, 4));
        jPanelGroupFile.setName(Integer.toString(groupId));
        FriendListForm.jGroupFilePanelList.add(jPanelGroupFile);
        //让主线程添加文件列表
        FriendListForm.chatClient.getGroupFile(groupId);

        jPanelNumberList = new JPanel(new GridLayout(0, 1, 4, 4));

        jPanelNumberList.setName(Integer.toString(groupId));
        FriendListForm.jGroupNumberPanelList.add(jPanelNumberList);
        //主线程添加群成员
        FriendListForm.chatClient.getGroupNumber(groupId);

        jPanelGroupFile.setSize(300,300);
        jPanelNumberList.setSize(300,300);
        jScrollPaneR1 = new JScrollPane(jPanelGroupFile);
        jScrollPaneR2 = new JScrollPane(jPanelNumberList);


        jScrollPaneR1.setViewportView(jPanelGroupFile);
        jScrollPaneR2.setViewportView(jPanelNumberList);

        jSplitPaneRight.setLeftComponent(jScrollPaneR1);

        jSplitPaneRight.setRightComponent(jScrollPaneR2);

        jSplitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneRight.setResizeWeight(0.8);


        jSplitPane = new JSplitPane();
        jSplitPane.setLeftComponent(jSplitPane1);
        jSplitPane.setRightComponent(jSplitPaneRight);
        jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        jSplitPane.setResizeWeight(0.6);

//        List<>
//        for (int i=0;i<SMain.jGroupFilePanelList.size();i++){
//            if (SMain.jGroupFilePanelList.get(i).get)
//        }
//
//        List<User> groupNumber =




        setContentPane(jSplitPane);
        //setSize(500,500);
        //GridPane gridPane = new GridPane();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);





        pack();
    }// </editor-fold>




    // Variables declaration - do not modify
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JPanel jPanel1;
    private JPanel jPanelRight;

    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPaneR1;
    private JScrollPane jScrollPaneR2;

    private JSplitPane jSplitPane;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPaneRight;

    //private javax.swing.JTextPane jTextPane1;
    private JTextPane jTextPane2;
    private JPanel jPanelGroupFile;
    private JPanel jPanelNumberList;

    private JPanel jPanel2;
    // End of variables declaration

    public static void main(String[] args) {
//        javax.swing.JTextPane jTextPane1 = new JTextPane();
//        ClientMessage clientMessage; ChatClient chatClient;
//        clientMessage= new ClientMessage();
//        chatClient = new ChatClient();
//        GroupTextPaneForm GroupTextPaneForm = new GroupTextPaneForm(jTextPane1,clientMessage,chatClient);
//        //imageTextPaneForm.;
//        GroupTextPaneForm.setSize(700,500);
//        GroupTextPaneForm.setVisible(true);
//        //GroupTextPaneForm.jSplitPane.setDividerLocation
//       //JFrame jf = new JFrame("测试窗口");

    }
}