package com.weibo.ui;

import com.weibo.web.FindWeiBo;
import com.weibo.web.SpiderWeb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-22:01
 */
public class MyFrame extends JFrame {
    public Label label_ts = new Label();
    public MyFrame(String title){
        this.setTitle(title);
        this.setLayout(null);
        this.setResizable(false);
        initButton();
        this.setBounds(400,200,250,150);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initButton() {
        label_ts.setText("用户id:");
        label_ts.setBounds(20,20,30,50);
        label_ts.setFont(new Font("宋体", Font.PLAIN, 13));
        this.add(label_ts);

        JLabel label_beg = new JLabel();
        label_beg.setText("no");
        label_beg.setBounds(170,60,30,50);
        label_beg.setForeground(Color.BLACK);
        label_beg.setFont(new Font("宋体", Font.PLAIN, 13));
        this.add(label_beg);

        final JTextField jTextField = new JTextField();
        jTextField.setFont(new Font("宋体", Font.PLAIN, 13));
        jTextField.setBounds(60,30,150,30);
        this.add(jTextField);
        Button choose1 = new Button("开始");
        choose1.setBounds(20,70,130,30);
        choose1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label_beg.setText("监控中");
                label_beg.setForeground(Color.red);
                //        5893163236 我的
                //        5302726703 hhw
                String userId = jTextField.getText();
                SpiderWeb.init(userId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                FindWeiBo findWeiBo = new FindWeiBo(userId);
                Thread t1 = new Thread(findWeiBo);
                t1.start();
            }
        });
        this.add(choose1);
    }
}
