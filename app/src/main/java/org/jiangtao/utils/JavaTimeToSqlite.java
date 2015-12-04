package org.jiangtao.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by mr-jiang on 15-12-4.
 */
public class JavaTimeToSqlite {
    /**
     * 将String转化为TimeStamp
     * 有错误
     *
     * @param strDate
     * @return
     * @throws Exception
     */
    public static Timestamp parseTimestamp(String strDate) throws Exception {
        Date armFormateDate = null;
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        armFormateDate = (Date) format.parse(strDate);
        strDate = df1.format(armFormateDate);
        Timestamp ts1 = Timestamp.valueOf(strDate);
        return ts1;
    }

    /**
     * 将timestamp转化为string
     *
     * @param timestamp
     * @return
     */
    public static String parseString(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        String str = df.format(timestamp);
        return str;
    }
}
