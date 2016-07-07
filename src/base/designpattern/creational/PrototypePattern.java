package base.designpattern.creational;


/**
 * 原型模式
 * <p>
 * 案例:盗版书
 * Created by yutianran on 16/7/1.
 */
public class PrototypePattern {

    //除了:基本类型/String/包装类,其余的都是浅拷贝,只传递引用,没有传递对象,此时若要深拷贝,需要让其实现Cloneable接口并复写clone方法
    public static void main(String[] args) {
        //正版书
        Book src = new Book("设计模式之禅");
        src.setPreface(new Preface("这是一本介绍设计模式的书"));

        //盗版书
        Book pirate = src.clone();
        //修改书名
        pirate.setName("设计模式之禅-Clone");
        //修改序言
        Preface preface = pirate.getPreface();
        preface.setDesc("这是一本介绍设计模式的书-Clone");

        System.out.println("正版书:" + src.toString());
        System.out.println("盗版书:" + pirate.toString());
    }

    //书
    public static class Book implements Cloneable {
        private String name;//书名
        private Preface preface;//序言

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Preface getPreface() {
            return preface;
        }

        public void setPreface(Preface preface) {
            this.preface = preface;
        }


        @Override
        protected Book clone() {
            Book clone = null;
            try {
                clone = (Book) super.clone();
                //这里必须同时调用引用对象的clone方法
                clone.preface = this.preface.clone();
            } catch (CloneNotSupportedException e) {
                System.out.println("克隆出错:" + e.getMessage());
            }
            return clone;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", preface=" + preface +
                    '}';
        }
    }

    //序言
    public static class Preface implements Cloneable {
        private String desc;//介绍

        public Preface(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        protected Preface clone() {
            Preface clone = null;
            try {
                clone = (Preface) super.clone();
            } catch (CloneNotSupportedException e) {
                System.out.println("克隆出错:" + e.getMessage());
            }
            return clone;
        }

        @Override
        public String toString() {
            return "Preface{" +
                    "desc='" + desc + '\'' +
                    '}';
        }
    }
}
