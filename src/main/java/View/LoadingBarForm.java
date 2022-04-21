package View;

import javax.swing.*;
import java.awt.*;


public class LoadingBarForm extends JFrame {
    public JProgressBar jProgressBar;
    public JPanel Form;
    public LoadingBarForm(JProgressBar jProgressBar){
        this.jProgressBar = jProgressBar;
    }
    public void initComponents(){
        /*处理组件 */
        Font font = new Font("方正喵呜体",Font.BOLD,20);

        //处理第一张卡片
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Form = new JPanel();
        Form.add(jProgressBar);
        this.add(Form);
        this.setTitle("QQ2011");
        this.setSize(300, 300);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
