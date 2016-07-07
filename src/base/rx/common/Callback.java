package base.rx.common;

/**
 * 泛型回调
 */
public interface Callback<T> {
    void onSuccess(T result);

    void onFailure(Exception e);
}