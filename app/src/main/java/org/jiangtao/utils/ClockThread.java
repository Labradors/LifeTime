package org.jiangtao.utils;

import android.util.Log;

/**
 * Created by erdaye on 2015/12/7.
 */
public class ClockThread extends Thread {
    @Override
    public void run() {
        super.run();
        while (true){
            Log.d("时间","============================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
