package base.rx;

import base.rx.bean.Uri;
import base.rx.common.AsyncWork;
import base.rx.common.Callback;
import base.rx.helper.LogUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * 测试场景类
 */
public class TestClient {

    private static TestClient testClient = new TestClient();
    private static RxJavaStudy service = new RxJavaStudy();

    public static void main(String[] args) {
        //普通版
//        testClient.testSync();
//        testClient.testAysnc();

        //AsyncWork版
//        testClient.testWork();
//        testClient.testTransform();
//        testClient.testOperator();
//        testClient.testLambda();
//        testClient.testChain();

//        //RxJava版
//        testClient.testRxJava();
        testClient.testRxLambda();
    }

    //1.测试同步方式
    private void testSync() {
        String desc = "同步方式";
        LogUtil.print("START-" + desc);
        Uri uri = service.getUriSync("Java");
        LogUtil.print("END-" + desc + ":" + uri.toString());
    }

    //2.测试异步回调方式
    private void testAysnc() {
        String desc = "异步回调方式";
        LogUtil.print("START-" + desc);
        service.getUriAsync("Java", new Callback<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                LogUtil.print("END-" + desc + ":" + uri.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //3.测试异步任务方式-原始
    private void testWork() {
        String desc = "异步任务方式-原始";
        LogUtil.print("START-" + desc);
        AsyncWork<Uri> uriWork = service.getUriWork("Java");
        uriWork.start(new Callback<Uri>() {
            @Override
            public void onSuccess(Uri result) {
                LogUtil.print("END-异步任务方式-原始:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //4.测试异步任务方式-事件变换
    private void testTransform() {
        String desc = "异步任务方式-事件变换";
        LogUtil.print("START-" + desc);
        AsyncWork<Uri> uriWork = service.getUriTransform("Java");
        uriWork.start(new Callback<Uri>() {
            @Override
            public void onSuccess(Uri result) {
                LogUtil.print("END-异步任务方式-事件变换:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //5.测试异步任务方式-操作符控制事件变换
    private void testOperator() {
        String desc = "异步任务方式-操作符控制事件变换";
        LogUtil.print("START-" + desc);
        AsyncWork<Uri> uriWork = service.getUriOperator("Java");
        uriWork.start(new Callback<Uri>() {
            @Override
            public void onSuccess(Uri result) {
                LogUtil.print("END-异步映射方式:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //6.异步任务方式-Lambda简写事件变换
    private void testLambda() {
        String desc = "Lambda简写事件变换";
        LogUtil.print("START-" + desc);
        AsyncWork<Uri> uriWork = service.getUriLambda("Java");
        uriWork.start(new Callback<Uri>() {
            @Override
            public void onSuccess(Uri result) {
                LogUtil.print("END-异步Lambda方式:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //7.测试异步任务方式-事件链式变换
    private void testChain() {
        String desc = "事件链式变换";
        LogUtil.print("START-" + desc);
        Callback<Uri> callback = new Callback() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.print("END-异步链式方式:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        AsyncWork<Uri> uriWork = service.getUriChain("Java");
        uriWork.start(callback);
    }

    //8.测试RxJava方式-原始
    private void testRxJava() {
        String desc = "RxJava方式-原始";
        LogUtil.print("START-" + desc);
        Observable<Uri> uriObservable = service.getUriRxJava("Java");
        uriObservable.subscribe(new Action1<Uri>() {
            @Override
            public void call(Uri uri) {
                LogUtil.print("END-" + desc + ":" + uri.toString());
            }
        });
    }

    //9.测试RxJava方式-Lambda+链式
    private void testRxLambda() {
        String desc = "RxJava方式-Lambda+链式";
        LogUtil.print("START-" + desc);
        Observable<Uri> uriObservable = service.getUriRxJava("Java");
        uriObservable.subscribe(new Action1<Uri>() {
            @Override
            public void call(Uri uri) {
                LogUtil.print("END-" + desc + ":" + uri.toString());
            }
        });
    }
}
