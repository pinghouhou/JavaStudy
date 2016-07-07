package base.rx.bean;

/**
 * 新闻实体
 */
public class News implements Comparable<News> {
    private int time;//发布时间
    private String content;//内容

    public News(int time, String content) {
        this.time = time;
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(News another) {
        return Integer.compare(time, another.time);
    }

    @Override
    public String toString() {
        return "News{" +
                "time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}


