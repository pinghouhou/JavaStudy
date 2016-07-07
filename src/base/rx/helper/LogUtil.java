package base.rx.helper;

import java.sql.Time;

/**
 * Created by yutianran on 16/7/4.
 */
public class LogUtil {

    public static void print(String msg) {
        System.out.println("Thread:" + Thread.currentThread().getName() + "\t" + System.currentTimeMillis() + "\t" + msg);
    }

}
