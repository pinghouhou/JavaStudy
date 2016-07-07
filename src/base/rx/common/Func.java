package base.rx.common;

/**
 * 类型变换泛型接口
 */
public interface Func<T, R> {
    R call(T t);
}
