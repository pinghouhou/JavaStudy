/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2014, JUN LU <lujun.hust@gmail.com>
 *  All rights reserved.  
 *   
 *  @file    LooperThread.java 
 *  @brief   带Looper的线程封装
 *  
 *  演示带Looper的线程原理
 *  
 *  @version 1.0     
 *  @author  卢俊
 *  @date    2014/10/15  
 * 
 */

package base.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义带消息循环（Looper）的工作线程
 */
public class LooperThread {

    private volatile boolean mIsLooperQuit = false;
    private Thread mThread;
    private Callbak mCallbak;
    private Lock mLock = new ReentrantLock();//互斥锁
    private Condition mCondition = mLock.newCondition();//条件变量
    private Queue<Message> mMessageQueue = new LinkedList<>();//消息队列

    public LooperThread(Callbak callback) {
        mCallbak = callback;
    }

    public void start() {
        if (mThread != null) {
            return;
        }
        mIsLooperQuit = false;
        mThread = new Thread(mLooperRunnable);
        mThread.start();
    }

    public void stop() {
        if (mThread == null) {
            return;
        }
        mIsLooperQuit = true;

        mLock.lock();
        mCondition.signal();
        mLock.unlock();

        mThread.interrupt();
        try {
            mThread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mMessageQueue.clear();
        mThread = null;
    }

    //消息结构体
    public static class Message {
        int what;

        public Message(int what) {
            this.what = what;
        }
    }

    //发送消息
    public void sendMessage(Message message) {
        if (mThread == null) {
            return;
        }
        mLock.lock();
        mMessageQueue.add(message);
        mCondition.signal();
        mLock.unlock();
    }

    //处理消息，由线程内部调用
    public static interface Callbak {
        public boolean handleMessage(Message msg);
    }

    //在mLooperRunnable.run()循环中解析消息
    protected Runnable mLooperRunnable = new Runnable() {

        @Override
        public void run() {

            while (!mIsLooperQuit) {

                mLock.lock();

                Message message = null;
                try {
                    while (!mIsLooperQuit && mMessageQueue.isEmpty()) {
                        mCondition.await();
                    }
                    message = mMessageQueue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mLock.unlock();
                }

                if (mCallbak != null && message != null) {
                    mCallbak.handleMessage(message);
                }
            }
        }
    };
}
