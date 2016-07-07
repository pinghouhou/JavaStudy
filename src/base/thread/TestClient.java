package base.thread;


/**
 * 作者：余天然 on 16/6/29 下午7:18
 */
public class TestClient {

    public static void main(String[] args) {
        LooperThread looperThread = new LooperThread(new LooperThread.Callbak() {
            @Override
            public boolean handleMessage(LooperThread.Message msg) {
                System.out.println("handleMessage:" + msg.what + "\tthread:" + Thread.currentThread().getName());
                return false;
            }
        });
        looperThread.start();

        LooperThread.Message msg1 = new LooperThread.Message(1);
        System.out.println("sendMessage:" + msg1.what + "\tthread:" + Thread.currentThread().getName());
        looperThread.sendMessage(msg1);

        LooperThread.Message msg2 = new LooperThread.Message(2);
        System.out.println("sendMessage:" + msg2.what + "\tthread:" + Thread.currentThread().getName());
        looperThread.sendMessage(msg2);
    }
}
