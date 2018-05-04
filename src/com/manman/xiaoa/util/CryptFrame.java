package com.manman.xiaoa.util;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * 功能描述: 窗口定义
 * @author XiaoA
 * @version 2018年5月4日
 * @see com.manman.xiaoa.util
 * @since JDK 1.7
 */
@SuppressWarnings("serial")
public class CryptFrame extends JFrame{

    /** 源串文本框 */
    private JTextField sourceTextField;

    /** 目标串文本框 */
    private JTextField targetTextField;

    /** 加密按钮 */
    private JButton encodeButton;

    /** 解密按钮 */
    private JButton decodeButton;

    /** 退出按钮 */
    private JButton exitButton;

    /** 当前窗口对象本身 */
    private CryptFrame frame;

    /** 移动位置坐标 */
    private int pointX, pointY;

    public CryptFrame() {
        // 缓存当前对象, 事件内会引用到
        frame= this;
        // 取消标题区域
        this.setUndecorated(true);
        // 定义大小
        this.setBounds(100, 100, 500, 250);
        // 不可调整大小
        this.setResizable(false);
        // 居中
        this.setLocationRelativeTo(null);
        // 退出
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 窗口拖动事件
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                pointX= e.getX();
                pointY= e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e) {
                int left= frame.getLocation().x;
                int top= frame.getLocation().y;
                frame.setLocation(left + e.getX() - pointX, top + e.getY() - pointY);
            }
        });
    }

    // 初始组件
    public void initComponent() {
        Container cp= frame.getContentPane();
        cp.setLayout(null);

        // 标题
        JLabel titleLabel= new JLabel(Const.LABEL_TITLE, JLabel.CENTER);
        titleLabel.setBounds(0, 0, 500, 50);
        titleLabel.setFont(new Font(Const.FONT_FAMILY_WEIRUANYAHEI, Font.BOLD, 22));
        cp.add(titleLabel);

        // 分割条
        JSeparator headerSeparator= new JSeparator();
        headerSeparator.setBounds(0, 50, 500, 1);
        cp.add(headerSeparator);

        Font font= new Font(Const.FONT_FAMILY_WEIRUANYAHEI, Font.PLAIN, 14);

        // 源串标签
        JLabel sourceLabel= new JLabel(Const.LABEL_SOURCE);
        sourceLabel.setBounds(20, 60, 160, 30);
        sourceLabel.setFont(font);
        cp.add(sourceLabel);

        // 源串文本框
        sourceTextField= new JTextField();
        sourceTextField.setBounds(100, 60, 340, 30);
        sourceTextField.setFont(font);
        cp.add(sourceTextField);

        // 目标串标签
        JLabel targetLabel= new JLabel(Const.LABEL_TARGET);
        targetLabel.setBounds(20, 140, 160, 30);
        targetLabel.setFont(font);
        cp.add(targetLabel);

        // 目标串文本框
        targetTextField= new JTextField();
        targetTextField.setBounds(100, 140, 340, 30);
        targetTextField.setFont(font);
        cp.add(targetTextField);

        // 分割条
        JSeparator footerSeparator= new JSeparator();
        footerSeparator.setBounds(0, 180, 500, 1);
        cp.add(footerSeparator);

        // 加密按钮
        encodeButton= new JButton(Const.LABEL_ENCODE);
        encodeButton.setBounds(120, 190, 80, 30);
        cp.add(encodeButton);

        // 解密按钮
        decodeButton= new JButton(Const.LABEL_DECODE);
        decodeButton.setBounds(220, 190, 80, 30);
        cp.add(decodeButton);

        // 退出按钮
        exitButton= new JButton(Const.LABEL_EXIT);
        exitButton.setBounds(320, 190, 80, 30);
        cp.add(exitButton);

    }

    // 初始事件
    public void initActionEvent() {
        // 加密按钮点击事件
        encodeButton.addActionListener(new ClickActionListener(Const.ENCRYPT_MODE, sourceTextField, targetTextField));

        // 解密按钮点击事件
        decodeButton.addActionListener(new ClickActionListener(Const.DECRYPT_MODE, sourceTextField, targetTextField));

        // 退出按钮点击事件
        exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // 目标串文本框获取焦点事件(实现自动复制内容)
        targetTextField.addFocusListener(new FocusListener(){

            @Override
            public void focusLost(FocusEvent e) {

            }

            @Override
            public void focusGained(FocusEvent e) {
                // 选择所有内容
                targetTextField.setSelectionStart(0);
                targetTextField.setSelectionEnd(targetTextField.getText().length());

                // 设置剪贴板, 实现自动复制
                Clipboard clipboard= Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection contents= new StringSelection(targetTextField.getText());
                clipboard.setContents(contents, new ClipboardOwner(){
                    @Override
                    public void lostOwnership(Clipboard clipboard, Transferable contents) {
                    }
                });
            }
        });
    }
}

// 自定义点击事件
class ClickActionListener implements ActionListener{

    private String mode;
    private JTextField sourceTextField;
    private JTextField targetTextField;

    public ClickActionListener(String mode, JTextField sourceTextField, JTextField targetTextField) {
        this.mode= mode;
        this.sourceTextField= sourceTextField;
        this.targetTextField= targetTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sourceText= sourceTextField.getText();
        if(sourceText == null || sourceText.trim().length() == 0) {
            JOptionPane.showMessageDialog(null, Const.VALIDATION_SOURCE_TEXT_IS_NULL, Const.DIALOG_TITLE_INFO,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 解密
        if(Const.DECRYPT_MODE.equalsIgnoreCase(mode)) {
            try {
                String targetText= CryptHelper.decode(sourceText);
                targetTextField.setText(targetText);
            }catch(Exception e1) {
                JOptionPane.showMessageDialog(null, Const.DIALOG_MESSAGE_DECODE_ERROR, Const.DIALOG_TITLE_INFO,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }else if(Const.ENCRYPT_MODE.equalsIgnoreCase(mode)) {
            // 加密
            try {
                String targetText= CryptHelper.encode(sourceText);
                targetTextField.setText(targetText);
            }catch(Exception e1) {
                JOptionPane.showMessageDialog(null, Const.DIALOG_MESSAGE_ENCODE_ERROR, Const.DIALOG_TITLE_INFO,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
