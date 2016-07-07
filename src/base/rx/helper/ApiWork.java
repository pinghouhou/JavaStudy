package base.rx.helper;

import base.rx.common.AsyncWork;
import base.rx.common.Callback;
import base.rx.bean.News;
import base.rx.bean.Uri;

import java.util.List;

/**
 * 接口的异步任务式封装
 */
public class ApiWork {
    private ApiAsync api = new ApiAsync();

    //将异步方式变换成异步任务方式
    public AsyncWork<List<News>> getNewsList(String tag) {
        return new AsyncWork<List<News>>() {
            @Override
            public void start(Callback<List<News>> callback) {
                api.getNewsList(tag, callback);
            }
        };
    }

    public AsyncWork<Uri> save(News news) {
        return new AsyncWork<Uri>() {
            @Override
            public void start(Callback<Uri> callback) {
                api.save(news, callback);
            }
        };
    }

}
