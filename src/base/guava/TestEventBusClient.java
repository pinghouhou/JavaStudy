package base.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by yutianran on 16/6/29.
 */
public class TestEventBusClient {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus("test");
        //测试简单消息
//        EventListener listener = new EventListener();
//
//        eventBus.register(listener);
//
//        eventBus.post(new TestEvent(200));
//        eventBus.post(new TestEvent(300));
//        eventBus.post(new TestEvent(400));
//
//        System.out.println("LastMessage:" + listener.getLastMessage());

        //测试复杂消息
        MultipleListener multiListener = new MultipleListener();
        eventBus.register(multiListener);

        eventBus.post(new Integer(100));
        eventBus.post(new Integer(200));
        eventBus.post(new Integer(300));
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventBus.post(new Long(800));
                eventBus.post(new Long(800990));
                eventBus.post(new Long(800882934));
            }
        }).start();


        System.out.println("LastInteger:" + multiListener.getLastInteger());
        System.out.println("LastLong:" + multiListener.getLastLong());

    }

    //消息封装类：
    private static class TestEvent {
        private final int message;

        public TestEvent(int message) {
            this.message = message;
            System.out.println("event message:" + message);
        }

        public int getMessage() {
            return message;
        }
    }

    //消息接受类：
    private static class EventListener {
        public int lastMessage = 0;

        @Subscribe
        public void listen(TestEvent event) {
            lastMessage = event.getMessage();
            System.out.println("Message:" + lastMessage);
        }

        public int getLastMessage() {
            return lastMessage;
        }
    }

    //复杂消息接受类
    private static class MultipleListener {
        public Integer lastInteger;
        public Long lastLong;

        @Subscribe
        public void listenInteger(Integer event) {
            lastInteger = event;
            System.out.println("event Integer:" + lastInteger);
        }

        @Subscribe
        public void listenLong(Long event) {
            lastLong = event;
            System.out.println("event Long:" + lastLong);
        }

        public Integer getLastInteger() {
            return lastInteger;
        }

        public Long getLastLong() {
            return lastLong;
        }
    }


}