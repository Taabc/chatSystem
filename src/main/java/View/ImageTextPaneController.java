package View;

import chatroom.ChatClient;
import pojo.ClientMessage;
import pojo.Picture;
import utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ImageTextPaneController {

    static JFileChooser jfc = new JFileChooser();
    static FileNameExtensionFilter filter=new FileNameExtensionFilter("d:\\"+"test1.jpg","jpg","gif");

    public static void add2(JTextPane inputJTpane,
                            JTextPane outputJTpane,
                            JButton insertButton,
                            JButton senon,
                            JFrame jf){

        insertButton.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showOpenDialog(jf);
            if (result == JFileChooser.APPROVE_OPTION) {
                inputJTpane.insertIcon(new ImageIcon(chooser.getSelectedFile().toString()));
            }
        });



        senon.addActionListener((ActionEvent e) -> {
            StyledDocument inputSDoc = inputJTpane.getStyledDocument(); //获取读取的StyledDocument
            StyledDocument outSDoc = outputJTpane.getStyledDocument(); //获取欲输出的StyledDocument
            ClientMessage clientMessage1 = new ClientMessage();
            List<Picture> pictureList = new ArrayList<Picture>();
//            try{
//
//                System.out.println("inputSDoc:"+inputSDoc.getText(0,inputSDoc.getLength()));
//
//                System.out.println("outSDoc:"+outSDoc.getText(0,outSDoc.getLength()));
//            }catch (Exception exception) {
//                exception.printStackTrace();
//            }

            for (int i = 0; i < inputSDoc.getLength(); i++) { //遍历读取的StyledDocument
                //System.out.println(i);
                if (inputSDoc.getCharacterElement(i).getName().equals("icon")) { //如果发现是icon元素，那么：
                    Element ele = inputSDoc.getCharacterElement(i);
                    ImageIcon icon = (ImageIcon) StyleConstants.getIcon(ele.getAttributes());
                    //Image newImage = icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
                    Image img = icon.getImage() ;
                    Image newImage = getScaledImage(img,200,200);
//                    File f = new File(newImage.toString());
//                    try{
//                        InputStream is = new FileInputStream(f);
//                        byte[] b = new byte[(int)f.length()];
//                    }catch (Exception c){
//                        c.printStackTrace();
//                    }
//                    byte[] b =Utils.load_picture_from_file(newImage.toString());
//                    Utils.write_picture_to_file(b,"testimg",1);
                    //BufferedImage blmage = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);
                    BufferedImage blmage = (BufferedImage) newImage;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //BufferedImage bufferedImage = null;
                    String TimeNow = new SimpleDateFormat("yyyyMMddHHmmssSS").format(Calendar.getInstance().getTime());
                    String tempImgName = FriendListForm.clientMessage.getUser().getAccount() + TimeNow;
//                    File outputfile2 = new File("\\temp\\img\\test1.jpg");
//                    File outputfile3 = new File("img/full.png");
                    //File outputfile = new File("temp/img/"+tempImgName+".jpg");
                            //new File("d:\\"+"test1.jpg");
                    String filePath = "src/main/resources/temp/img/"+tempImgName+".jpg";
                    String type = icon.toString();
                    System.out.println(icon.toString());

                    type=type.substring(type.length()-4,type.length());
                    System.out.println(type);
                    byte[] imageByte =  Utils.load_picture_from_file(icon.toString());
                    Picture picture = new Picture();
                    picture.setP_name(tempImgName);
                    picture.setP_date(imageByte);
                    picture.setType(type);
                    picture.setOldLoad(icon.toString());
                    pictureList.add(picture);

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

                            //"temp/img/"+tempImgName+".jpg";

                     //filter = new FileNameExtensionFilter("d:\\"+"test1.jpg","jpg");
//                    jfc.setFileFilter(filter);
//                    System.out.println(jfc.getSelectedFile().getAbsolutePath());
//                    String filePath = jfc.getSelectedFile().getAbsolutePath();


//                    BufferedImage bimg = (BufferedImage)newImage;
//
//                    ImageInputStream is = new ImageInputStream(newImage);
//                    byte[] b = new byte[(int)newImage.()];
                    //Image newImage2 = bufferedImage;
                    //newImage =(ImageIcon) StyleConstants.getIcon(bufferedImage.geta);

                    //ImageIcon newicon = new ImageIcon(newImage2);
                    //System.out.println(newicon.toString());
                    //System.out.println(newicon2.toString());
                    //System.out.println(icon.toString());
                    //System.out.println(icon.getIconHeight()+icon.getIconWidth());filePath
                    //outputJTpane.insertIcon(new ImageIcon(newicon.toString()));//插入icon元素
                    outputJTpane.insertIcon(new ImageIcon(filePath));
                } else {//如果不是icon（可以判断是文字，因为目前只有图片元素插入）
                    try {
                        String s = inputSDoc.getText(i, 1);
                        outSDoc.insertString(outputJTpane.getCaretPosition(), s, null);//从光标处插入文字
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try{
                outSDoc.insertString(outputJTpane.getCaretPosition(),"\r\n",null);
            }catch (BadLocationException e2){
                e2.printStackTrace();
            }

            clientMessage1.setPictureList(pictureList);
            clientMessage1.setUser(FriendListForm.clientMessage.getUser());
            clientMessage1.setOperate("SendMessage");
            clientMessage1.setFriendAccount(Integer.parseInt(outputJTpane.getName()));
            clientMessage1.setMessageSDoc(inputSDoc);
//            for (int i = 0 ;i<inputSDoc.getLength();i++){
//                System.out.println("inputSDoc:");
//                try{
//                    System.out.println(inputSDoc.getText(i,1));
//                }catch (Exception e3){
//                    e3.printStackTrace();
//                }
//
//            }
            FriendListForm.chatClient.sendMessage(clientMessage1);
//            for (int i = 0 ; i < clientMessage1.getMessageSDoc().getLength();i++){
//                System.out.println("MessageSDoc:");
//                try{
//                    System.out.println(clientMessage1.getMessageSDoc().getText(i,1));
//                }catch (Exception e4){
//                    e4.printStackTrace();
//                }
//            }
            clientMessage1 = null;


        });
        //===========End of senon

    }
    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}

