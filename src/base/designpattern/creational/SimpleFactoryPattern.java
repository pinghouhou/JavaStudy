package base.designpattern.creational;

/**
 * 简单工厂模式
 * <p>
 * Created by yutianran on 16/7/1.
 */
public class SimpleFactoryPattern {

    public static void main(String args[]) {
        IProduct product = SimpleFactory.produce(ProductA.class);
        product.method();
    }

    //抽象产品
    public static abstract class IProduct {
        public abstract void method();
    }

    //具体产品A
    public static class ProductA extends IProduct {
        @Override
        public void method() {
            System.out.println("I'm ProductA!");
        }
    }

    //具体产品B
    public static class ProductB extends IProduct {
        @Override
        public void method() {
            System.out.println("I'm ProductB!");
        }
    }

    //静态工厂
    public static class SimpleFactory {

        public static <T extends IProduct> T produce(Class<T> clazz) {
            IProduct human = null;
            try {
                human = (IProduct) Class.forName(clazz.getName()).newInstance();
            } catch (Exception e) {
                System.out.println("创建错误:" + e.getMessage());
            }
            return (T) human;
        }
    }


}

