package base.handler;

/**
 * 消息循环器
 * <p>
 * Created by yutianran on 16/7/4.
 */
public class MyLooper {

    private static ThreadLocal<MyLooper> threadLocal = new ThreadLocal<>();
    private static MyLooper myLooper;
    public MyMessageQueue queue;//一个线程对应一个阻塞队列

    private MyLooper() {
        queue = new MyMessageQueue();
    }

    //获取当前线程相对应的Looper对象
    public static MyLooper myLooper() {
        return threadLocal.get();//当未调用prepare()方法时。ThreadLocal.get()方法返回的为null;
    }

    //为本线程准备对应的MyLooper对象
    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException( "Only one MyLooper may be created per thread");
        }
        threadLocal.set(new MyLooper());
    }

    //这里启动消息循环
    public static void loop() {
        while (true) {
            myLooper = myLooper();
            MyMessageQueue mQueue = myLooper.queue;
            MyMessage msg = mQueue.next();// take()方法是个阻塞方法。线程运行到此会阻塞住。以准备接收发过来的消息
            msg.target.dispatchMessage(msg);
        }
    }
}
