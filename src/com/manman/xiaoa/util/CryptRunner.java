package com.manman.xiaoa.util;

/**
 * 功能描述: 入口程序
 * @author XiaoA
 * @version 2018年5月4日
 * @see com.manman.xiaoa.util
 * @since JDK 1.7
 */
public class CryptRunner{

    public static void main(String[] args) {
        CryptFrame frame= new CryptFrame();
        frame.initComponent();
        frame.initActionEvent();
        frame.setVisible(true);
    }
}
