package View;

import chatroom.ChatClient;
import com.sun.prism.image.ViewPort;
import pojo.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FriendListForm extends JFrame{
    /*定义组件*/
    //定义第一张卡片

    JPanel  Form;
    public static JPanel jPanelTop,jPanelCenter,jPanelBottom;
    JPanel jPCenterTop,jPCenterCenter,jPCenterBottom;
    JPanel jPCTOPChild,jPCCenterChild,jPCBottomChild;
    JLabel jLabelTop;
    JButton jBCTop,jBCCenter,jBCBottom;
    JScrollPane fjsp1,fJspTOPChild,fJspCenterChild,fJspBottomChild;

    public static ChatClient chatClient=null;
    public static ClientMessage clientMessage=null;

    public static List<JTextPane> jFriendTextPaneList = new ArrayList<JTextPane>();
    public static List<JTextPane> jGroupTextPanelList = new ArrayList<JTextPane>();
    public static List<JPanel> jGroupFilePanelList = new ArrayList<JPanel>();
    public static List<JPanel> jGroupNumberPanelList = new ArrayList<JPanel>();
    public static List<JLabel> jLabelUserStateList = new ArrayList<JLabel>();

    public static ArrayList<SocketStream> p2pClientStreamList=new ArrayList<SocketStream>();
//    public List<JTextPane> jTextPanes;
//    public List<JTextPane> jGroupTextPanes;
    public FriendListForm(ChatClient chatClient,ClientMessage clientMessage){
        super();
        FriendListForm.chatClient = chatClient;
        FriendListForm.clientMessage = clientMessage;
    }

    public void initComponents()
    {
        /*处理组件 */
        Font font = new Font("方正喵呜体",Font.BOLD,20);

        //处理第一张卡片
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int flag=JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
                if(flag==JOptionPane.YES_OPTION){
                    if (clientMessage != null){
                        chatClient.loginOut(clientMessage.getUser().getAccount());
                        for (int i=0;i<FriendListForm.p2pClientStreamList.size();i++){
                            ClientMessage endMessage = new ClientMessage();
                            User user =new User();
                            user.setAccount(clientMessage.getUser().getAccount());
                            endMessage.setOperate("LoginOut");
                            endMessage.setUser(user);

                            try {
                                if (FriendListForm.p2pClientStreamList.get(i).getType()==1){
                                    FriendListForm.p2pClientStreamList.get(i).getObops().writeObject(endMessage);
                                    FriendListForm.p2pClientStreamList.get(i).getSocket().close();
                                }
                            }catch (IOException ioException){
                                ioException.printStackTrace();
                            }
                        }
                        // chatClient.destroySocket();

                    }
                    System.out.println("开始停止");
                    System.exit(0);
                }
            }
        });
        //fjl1 = new JLabel(new ImageIcon("img/4545d8e1db93fbea7811011f2f9753b0.jpg"));
        Form = new JPanel();

        jPanelTop = new JPanel(new BorderLayout());
        jLabelTop = new JLabel("这是顶部");

        jPanelCenter = new JPanel(new GridLayout(3, 1, 4, 4));
        jPanelBottom = new JPanel(new GridLayout(3, 1));

        jPCenterTop = new JPanel(new GridLayout(2, 1, 4, 4));
        jPCenterTop.setName("MyFriend");
        jPCenterCenter = new JPanel(new GridLayout(2, 1, 4, 4));
        jPCenterCenter.setName("MyGroup");
        jPCenterBottom = new JPanel(new GridLayout(2, 1, 4, 4));
        jPCenterBottom.setName("ApplyMsg");

        jBCTop = new JButton("我的好友");
        jBCTop.setFont(font);
        jBCTop.setForeground(Color.BLUE);
        jPCTOPChild = new JPanel(new GridLayout(0, 1, 4, 4));
        jPCTOPChild.setName("MyFriendChild");
        jPCenterTop.add(jBCTop);
        fJspTOPChild = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 把fjp1放到可以滚动的JScrollPane里
        fJspTOPChild.setViewportView(jPCTOPChild);
        jPCenterTop.add(fJspTOPChild);

        jBCCenter = new JButton("群");
        jPCCenterChild = new JPanel(new GridLayout(0, 1, 4, 4));
        jPCCenterChild.setName("MyGroupChild");
        jPCenterCenter.add(jBCCenter);
        fJspCenterChild = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 把fjp1放到可以滚动的JScrollPane里
        fJspCenterChild.setViewportView(jPCCenterChild);
        jPCenterCenter.add(fJspCenterChild);

        jBCBottom = new JButton("好友申请");
        jPCBottomChild = new JPanel(new GridLayout(0, 1, 4, 4));
        jPCBottomChild.setName("ApplyMsgChild");
        jPCenterBottom.add(jBCBottom);
        fJspBottomChild = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 把fjp1放到可以滚动的JScrollPane里
        fJspBottomChild.setViewportView(jPCBottomChild);
        jPCenterBottom.add(fJspBottomChild);



        System.out.println(FriendListForm.clientMessage);
        List<Friend> friendList = FriendListForm.clientMessage.getFriendList();
        List<Group> groupList = FriendListForm.clientMessage.getGroupList();

        for (int k = 0;k < friendList.size();k++) {
            String friendAccount = Integer.toString(friendList.get(k).getFaccount());;
            String friendName = friendList.get(k).getName();
            JPanel jPanel = new JPanel();
            jPanel.setName(friendAccount);
            jPanel.setSize(300,200);

            JLabel jLabelUser = new JLabel(friendList.get(k).getName(),JLabel.LEFT);
            JLabel jLabelUserState;
            if (friendList.get(k).getLstatus()==1){
                jLabelUserState = new JLabel("在线");
            }else {
                jLabelUserState = new JLabel("离线");
            }
            jLabelUserState.setName(Integer.toString(friendList.get(k).getFaccount()));
            jPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            final int fid = friendList.get(k).getFaccount();

            JTextPane jTextPane = new JTextPane();
            jTextPane.setName(Integer.toString(friendList.get(k).getFaccount()));
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
            jPanel.add(jLabelUser);
            jPanel.add(jLabelUserState);
            jPCTOPChild.add(jPanel);
        }
        for (int i = 0;i<groupList.size();i++){
            String gName = groupList.get(i).getGname();
            JPanel jPanel = new JPanel();
            jPanel.setName(gName);
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
        }

        JButton jButton = new JButton("添加好友");
        jButton.addActionListener((ActionEvent e) -> {
            String inputValue = JOptionPane.showInputDialog("Please input a value");
            if (!inputValue.equals("")){
                ClientMessage clientMessage = new ClientMessage();
                clientMessage.setOperate("AddFriend");
                clientMessage.setUser(FriendListForm.clientMessage.getUser());
                clientMessage.setFriendAccount(Integer.parseInt(inputValue));
                System.out.println("添加的好友账号为"+inputValue);
                FriendListForm.chatClient.sendMessage(clientMessage);
            }

        });

        JButton jButton1 = new JButton("加入群聊");
        jButton1.addActionListener((ActionEvent e) -> {
            String inputValue = JOptionPane.showInputDialog("Please input a value");
            if (!inputValue.equals("")){
                ClientMessage clientMessage = new ClientMessage();
                clientMessage.setOperate("ApplyIntoGroup");
                clientMessage.setUser(FriendListForm.clientMessage.getUser());
                //clientMessage.setFriendAccount(Integer.parseInt(inputValue));
                Group group = new Group();
                group.setGnum(Integer.parseInt(inputValue));
                clientMessage.setGroup(group);
                System.out.println("添加的群聊ID为"+inputValue);
                FriendListForm.chatClient.sendMessage(clientMessage);
            }

        });
        JButton jButton2 = new JButton("创建群聊");
        jButton2.addActionListener((ActionEvent e) -> {
            String inputValue = JOptionPane.showInputDialog("Please input a value");
            if (!inputValue.equals("")){
                ClientMessage clientMessage = new ClientMessage();
                clientMessage.setOperate("AddGroup");
                clientMessage.setUser(FriendListForm.clientMessage.getUser());
                //clientMessage.setFriendAccount(Integer.parseInt(inputValue));
                Group group = new Group();
                group.setGname(inputValue);
                clientMessage.setGroup(group);
                System.out.println("想创建的群名为"+inputValue);
                FriendListForm.chatClient.sendMessage(clientMessage);
            }

        });


        //为第一张卡片添加组件
        fjsp1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);// 把fjp1放到可以滚动的JScrollPane里
        fjsp1.setViewportView(jPanelCenter);


        //System.out.println(fjsp2.getViewport()

        jPanelCenter.setPreferredSize(new Dimension(300,590));

        Form.add(jButton);
        Form.add(jButton1);
        Form.add(jButton2);

        Form.add(jPanelTop,"North");
        Form.add(fjsp1,"Center");
        Form.add(jPanelBottom,"South");

        jPanelCenter.add(jPCenterTop,"North");
        jPanelCenter.add(jPCenterCenter,"Center");
        jPanelCenter.add(jPCenterBottom,"South");

        //jPanelBottom.add(jButton);
        FriendListForm.chatClient.chat();

        //setUpServer(clientMessage.getUser().getLport());
        FriendListForm.chatClient.setUpServer(FriendListForm.clientMessage.getUser().getLport()+100);

        this.add(Form);
        this.setTitle("QQ2011");
        this.setIconImage(new ImageIcon("image/qq.jpg").getImage());
        this.setSize(300, 700);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);




    }

    private void createPopupMenu() {
        JPopupMenu m_popupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("  删除  ");
        delMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //该操作需要做的事
            }
        });
        m_popupMenu.add(delMenItem);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FriendListForm friendlist = new FriendListForm(null,null);
        friendlist.initComponents();
    }

}