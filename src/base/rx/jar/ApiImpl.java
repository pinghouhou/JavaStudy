package base.rx.jar;

import base.rx.bean.News;
import base.rx.bean.Uri;
import base.rx.helper.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 第三方的接口实现类
 */
public class ApiImpl implements Api {

    private Random random = new Random();

    @Override
    public List<News> getNewsList(String tag) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<News> list = new ArrayList<>();
        //为了打印的方便,只假设有两个了
        for (int i = 0; i < 2; i++) {
            list.add(new News(random.nextInt(100), "这是" + tag + "标签的新闻" + i));
        }
        //为方便测试,这里打印下每一步的结果
        LogUtil.print("获取新闻列表:" + list.toString());
        return list;
    }

    @Override
    public Uri save(News news) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Uri uri = new Uri("地址" + news.hashCode());
        //为方便测试,这里打印下每一步的结果
        LogUtil.print("保存得到URI:" + uri.toString());
        return uri;
    }

}
