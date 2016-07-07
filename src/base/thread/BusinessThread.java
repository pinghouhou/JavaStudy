package base.thread;

/**
 * Created by yutianran on 16/7/4.
 */
public class BusinessThread extends Thread {

    Thread businessThread;
    boolean businessThreadStarted=false;
    BusinessThreadHandler businessThreadHandler;
}
