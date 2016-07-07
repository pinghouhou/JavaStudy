package base.handler;

import base.rx.helper.LogUtil;

/**
 * 模拟子线程请求网络
 * <p>
 * Created by yutianran on 16/7/4.
 */
public class TestClient {

    private MyHandler mainHandler;

    public static void main(String[] args) {
        new TestClient().test();
    }

    private void test() {
        //初始化主线程Looper
        MyLooper.prepare();
        mainHandler = new MyHandler(new MyHandler.CallBack() {
            @Override
            public void handleMessage(MyMessage msg) {
                // 刷新界面
                String obj = (String) msg.obj;
                LogUtil.print("刷新界面:" + obj);
            }
        });
        //发起网络请求
        LogUtil.print("在主线程发起一个网络请求");
        NetThread netThread = new NetThread("http://baidu.com");
        netThread.start();
        LogUtil.print("在主线程继续其它操作");

        //开始消息循环
        MyLooper.loop();
    }


    //网络线程类
    private class NetThread extends Thread {
        private String url;

        public NetThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String body = getWebData(url);
            MyMessage msg = new MyMessage();
            msg.obj = body;
            mainHandler.sendMessage(msg);
        }
    }

    //执行网络请求
    private String getWebData(String url) {
        LogUtil.print("执行请求网络:" + url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String body = "这是" + url + "的响应值";
        LogUtil.print("请求网络成功:" + body);
        return body;
    }
}
