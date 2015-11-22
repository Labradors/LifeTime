package org.jiangtao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mr-jiang on 15-11-21.
 * 对验证码和邮箱的验证
 */
public class ValidateEmailAndNumber {

    /**
     * 匹配是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        boolean isNumber = pattern.matcher(str).matches();
        boolean isSix = false;
        if (str.length() == 6) {
            isSix = true;
        }
        return isNumber && isSix;
    }

    /**
     * 验证是否为邮箱格式
     *
     * @param mEmail
     * @return
     */
    public static boolean isEmail(String mEmail) {
        String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mEmail);
        return matcher.matches();
    }

    /**
     * 验证两次密码的输入是否相同
     *
     * @param passWord
     * @param repeatPassWord
     * @return
     */
    public static boolean isCommonValue(String passWord, String repeatPassWord) {
        if (passWord.equals(repeatPassWord)) {
            return true;
        }
        return false;
    }
}
