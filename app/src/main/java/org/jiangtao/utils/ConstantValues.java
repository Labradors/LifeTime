package org.jiangtao.utils;

/**
 * Created by mr-jiang on 15-11-13.
 */
public class ConstantValues {
    public static final String url = "http://192.168.1.117:8080/LifeTime/";
    //欢迎界面的url
    public static final String welcomeUrl = url + "welcome.action";
    //请求获得验证码
    public static final String verificationCodeUrl = url + "sendvalidate.action";
    //提交用户基本信息，包括用户名，密码等信息
    public static final String registerInformationUrl = url + "registerinformation.action";
    //提交用户头像信息。
    public static final String registerImageUrl = url + "registerImage.action";
}
