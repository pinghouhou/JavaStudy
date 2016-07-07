package base.designpattern.creational;

/**
 * 单例模式
 * <p>
 * 案例:臣子觐见皇帝
 * Created by yutianran on 16/7/1.
 */
public class SingletonPattern {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //每次都有一个臣子觐见皇帝
            Emperor emperor = Emperor.getInstance();
            Minister minister = new Minister();
            minister.see(emperor);
        }
    }

    //皇帝
    public static class Emperor {
        private static Emperor instance;

        //禁止外部创建
        private Emperor() {
        }

        //双重锁定式
        public static Emperor getInstance() {
            if (null == instance) {
                synchronized (Emperor.class) {
                    if (null == instance) {
                        instance = new Emperor();
                    }
                }
            }
            return instance;
        }
    }

    //臣子
    public static class Minister {

        public void see(Emperor emperor) {
            System.out.println("臣子:" + this + "\t觐见皇帝:" + emperor);
        }
    }
}
