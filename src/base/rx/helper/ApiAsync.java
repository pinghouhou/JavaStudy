package base.rx.helper;

import base.rx.common.Callback;
import base.rx.bean.News;
import base.rx.bean.Uri;
import base.rx.jar.Api;
import base.rx.jar.ApiImpl;

import java.util.List;
import java.util.concurrent.*;

/**
 * 接口的异步封装
 */
public class ApiAsync {
    private Api api = new ApiImpl();

    //线程池:核心有两个线程，最大线程数量可无限，存活时间60s
    private ExecutorService executor =
            new ThreadPoolExecutor(2, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    //将同步方式变换成异步回调方式
    public void getNewsList(String tag, Callback<List<News>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(api.getNewsList(tag));
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void save(News news, Callback<Uri> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(api.save(news));
                } catch (Exception e) {
                    callback.onFailure(e);
                }

            }
        });
    }

}
