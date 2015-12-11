package org.jiangtao.utils;

/**
 * Created by mr-jiang on 15-11-25.
 */
public class Code {
    // LoginActivity请求吗
    public final static int FORGOT_PASSWORD_REQUESTCODE = 0x555;
    //用户登陆成功后，没有邮箱的登陆
    public final static int RESULLTCODE_LOGINSUCCESS_NOPICTURE = 0x556;
    //跳转到注册界面所用的请求码
    public final static int REQUESTCODE_INDEXACTIVITY_TO_LOGINACTIVITY = 0x124;
    //跳转到注册界面
    public final static int REQUESTCODE_LOGINACTIVITY_TO_REGISTERACTIVITY = 0x124;
    //更新用户信息请求吗
    public final static int REQUESTCODE_UPDATEUSER_INFORMATION = 0x124;
    /**
     * WriteDynamicActivity
     */
    //打开相册请求码
    public final static int REQUESTCODE_OPEN_GALLERY = 0x600;
    //写完文章后返回到本地的返回码
    public final static int RESULTCODE_RETRUN_INDEX = 0x777;
    //打开写动态界面的请求码
    public final static int REQUEST_OPEN_WRITEDYNAMIC = 0x778;
}
