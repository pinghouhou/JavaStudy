package base.rx.common;

import base.rx.bean.News;
import base.rx.bean.Uri;

/**
 * 异步任务
 */
public abstract class AsyncWork<T> {

    /**
     * start
     * 作用:AsyncWork<T> -> T
     * 说明:调用start方法,执行异步任务,通过接口回调,得到结果T
     */
    public abstract void start(Callback<T> callback);

    /**
     * map
     * 作用:AsyncWork<T> --> AsyncWork<R>
     * 说明:call中实现(T -> R)
     */
    public <R> AsyncWork<R> map(Func<T, R> func) {
        final AsyncWork<T> source = this;
        return new AsyncWork<R>() {
            @Override
            public void start(Callback<R> callback) {
                source.start(new Callback<T>() {
                    @Override
                    public void onSuccess(T result) {
                        R mapped = func.call(result);
                        callback.onSuccess(mapped);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callback.onFailure(e);
                    }
                });
            }
        };
    }

    /**
     * flatMap
     * 作用:AsyncWork<T> -> AsyncWork<R>
     * 说明:call中实现(T -> AsyncWork<R>)
     */
    public <R> AsyncWork<R> flatMap(Func<T, AsyncWork<R>> func) {
        final AsyncWork<T> source = this;
        return new AsyncWork<R>() {
            @Override
            public void start(Callback<R> callback) {
                source.start(new Callback<T>() {
                    @Override
                    public void onSuccess(T result) {
                        AsyncWork<R> mapped = func.call(result);
                        mapped.start(new Callback<R>() {
                            @Override
                            public void onSuccess(R result) {
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


}

