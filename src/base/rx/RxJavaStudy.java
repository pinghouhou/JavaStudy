package base.rx;

import base.rx.bean.News;
import base.rx.bean.Uri;
import base.rx.common.AsyncWork;
import base.rx.common.Callback;
import base.rx.common.Func;
import base.rx.helper.ApiAsync;
import base.rx.helper.ApiRx;
import base.rx.helper.ApiWork;
import base.rx.jar.Api;
import base.rx.jar.ApiImpl;
import base.rx.helper.LogUtil;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collections;
import java.util.List;

/**
 * 自定义RxJava
 * ------------------------------
 * 描述:
 * 我们有个 Web API，获取指定标签的所有新闻列表,
 * 每条新闻包含时间和内容,
 * 我们的任务就是获取新闻列表,选择最新的新闻,然后保存在本地,得到最新的新闻的Uri
 * ------------------------------
 * 分解:
 * 1.下载数据
 * 2.处理数据
 * 3.保存数据
 * ------------------------------
 * RxJava特性:
 * 1.事件变换
 * 2.链式调用
 * 3.观察者模式
 * 4.异常传递
 * 5.线程控制
 * ------------------------------
 * 参考:
 * 1. [NotRxJava懒人专用指南](http://www.devtf.cn/?p=323)
 * 2. [给Android开发者的RxJava详解](http://gank.io/post/560e15be2dca930e00da1083)
 * 3. [深入浅出RxJava](http://blog.csdn.net/lzyzsd/article/details/41833541)
 * 4. [用RxJava实现事件总线(Event Bus)](http://www.jianshu.com/p/ca090f6e2fe2/)
 * 5. [用RxJava实现事件总线RxBus并实现同类型事件的区分](http://www.loongwind.com/archives/264.html)
 * 6. [RxBus升级篇](http://www.loongwind.com/archives/277.html)
 * ------------------------------
 */
public class RxJavaStudy {

    //1.同步方式
    public Uri getUriSync(String tag) {
        Api api = new ApiImpl();//原始接口
        //第1步,得到List<News>
        List<News> newsList = api.getNewsList(tag);
        //第2步,将List<News>变换成News
        News latestNews = getLatestNews(newsList);
        //第3步,将News变换成Uri
        Uri uri = api.save(latestNews);
        return uri;
    }

    //2.异步回调方式
    public void getUriAsync(String tag, Callback<Uri> callback) {
        ApiAsync api = new ApiAsync();//异步回调的接口包装类
        //第1步,通过接口回调,得到List<News>
        api.getNewsList(tag, new Callback<List<News>>() {
            @Override
            public void onSuccess(List<News> newsList) {
                //第2步,将List<News>变换成News
                News latestNews = getLatestNews(newsList);
                //第3步,通过接口回调,得到Uri
                api.save(latestNews, new Callback<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onSuccess(uri);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    //3.异步任务方式-原始
    public AsyncWork<Uri> getUriWork(String tag) {
        ApiWork apiWork = new ApiWork();//异步任务的接口包装类
        return new AsyncWork<Uri>() {
            @Override
            public void start(Callback<Uri> callback) {
                //第1步,得到AsyncWork<List<News>>
                AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
                //第2步,调用start,通过接口回调,得到List<News>
                newsListWork.start(new Callback<List<News>>() {
                    @Override
                    public void onSuccess(List<News> newsList) {
                        //第3步,将List<News>变换成News
                        News latestNews = getLatestNews(newsList);
                        //第4步,得到AsyncWork<Uri>
                        AsyncWork<Uri> uriWork = apiWork.save(latestNews);
                        //第5步,调用start,通过接口回调,得到Uri
                        uriWork.start(new Callback<Uri>() {
                            @Override
                            public void onSuccess(Uri result) {
                                callback.onSuccess(result);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                callback.onFailure(e);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }
        };
    }

    //4.异步任务方式-事件变换
    public AsyncWork<Uri> getUriTransform(String tag) {
        ApiWork apiWork = new ApiWork();
        //第1步,得到AsyncWork<List<News>>
        AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
        //第2步,得到AsyncWork<News>:先new对象,再在start方法中,调用callback的回调方法
        AsyncWork<News> latestNewsWork = new AsyncWork<News>() {
            @Override
            public void start(Callback<News> callback) {
                newsListWork.start(new Callback<List<News>>() {
                    @Override
                    public void onSuccess(List<News> result) {
                        News latestNews = getLatestNews(result);
                        callback.onSuccess(latestNews);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }
        };
        //第3步,得到AsyncWork<Uri>
        AsyncWork<Uri> uriWork = new AsyncWork<Uri>() {
            @Override
            public void start(Callback<Uri> callback) {
                latestNewsWork.start(new Callback<News>() {
                    @Override
                    public void onSuccess(News cutest) {
                        apiWork.save(cutest)
                                .start(new Callback<Uri>() {
                                    @Override
                                    public void onSuccess(Uri result) {
                                        callback.onSuccess(result);
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        callback.onFailure(e);
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }
        };
        return uriWork;
    }


    //5.异步任务方式-操作符控制事件变换
    public AsyncWork<Uri> getUriOperator(String tag) {
        ApiWork apiWork = new ApiWork();
        //第1步,得到AsyncWork<List<News>>
        AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
        //第2步,调用map,将AsyncWork<List<News>>变换成AsyncWork<News>
        AsyncWork<News> newsWork = newsListWork.map(new Func<List<News>, News>() {
            @Override
            public News call(List<News> newsList) {
                News latestNews = getLatestNews(newsList);
                return latestNews;
            }
        });

        //3.调用flatMap,将AsyncWork<News>变换成AsyncWork<Uri>
        AsyncWork<Uri> uriWork = newsWork.map(new Func<News, Uri>() {
            @Override
            public Uri call(News news) {
                return null;
            }
        });
        return uriWork;
    }

    //6.异步任务方式-Lambda简写事件变换
    public AsyncWork<Uri> getUriLambda(String tag) {
        ApiWork apiWork = new ApiWork();
        //第1步,得到AsyncWork<List<News>>
        AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
        //第2步,得到AsyncWork<News>
        AsyncWork<News> newsWork = newsListWork.map(newsList -> getLatestNews(newsList));
        //第3步,得到AsyncWork<Uri>
        AsyncWork<Uri> uriWork = newsWork.flatMap(latestNews -> apiWork.save(latestNews));
        return uriWork;
    }

    //7.异步任务方式-事件链式变换
    public AsyncWork<Uri> getUriChain(String tag) {
        ApiWork apiWork = new ApiWork();
        AsyncWork<Uri> uriWork = apiWork
                .getNewsList(tag)//第1步,得到AsyncWork<List<News>>
                .map(newsList -> getLatestNews(newsList))//第2步,得到AsyncWork<News>
                .flatMap(latestNews -> apiWork.save(latestNews));//第3步,得到AsyncWork<Uri>
        return uriWork;
    }

    //8.RxJava方式-原始
    public Observable<Uri> getUriRxJava(String tag) {
        ApiRx apiRx = new ApiRx();//Rxjava的接口包装类
        //第1步,得到Observable<List<News>>
        Observable<List<News>> newsListObservable = apiRx.getLatestNews(tag);
        //第2步,得到Observable<News>
        Observable<News> latestNewsObservable = newsListObservable.map(new Func1<List<News>, News>() {
            @Override
            public News call(List<News> cats) {
                return getLatestNews(cats);
            }
        });
        //第3步,得到Observable<Uri>
        Observable<Uri> uriObservable = latestNewsObservable.flatMap(new Func1<News, Observable<? extends Uri>>() {
            @Override
            public Observable<? extends Uri> call(News cat) {
                return apiRx.save(cat);
            }
        });
        return uriObservable;
    }

    //9.RxJava方式-Lambda+链式
    public Observable<Uri> getUriRxLambda(String tag) {
        ApiRx apiRx = new ApiRx();//Rxjava的接口包装类
        Observable<Uri> uriObservable = apiRx
                .getLatestNews(tag)//第1步,得到Observable<List<News>>
                .map(cats -> getLatestNews(cats))//第2步,得到Observable<News>
                .flatMap(cat -> apiRx.save(cat));//第3步,得到Observable<Uri>
        return uriObservable;
    }

    private News getLatestNews(List<News> newsList) {
        News latestNews = Collections.max(newsList);
        //为方便测试,这里打印下每一步的结果
        LogUtil.print("获取最新的新闻:" + latestNews.toString());
        return Collections.max(newsList);
    }

}
