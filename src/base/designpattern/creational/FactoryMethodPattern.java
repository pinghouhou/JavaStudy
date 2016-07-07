package base.designpattern.creational;

/**
 * 工厂方法模式
 * <p>
 * Created by yutianran on 16/7/1.
 */
public class FactoryMethodPattern {

    public static void main(String[] args) {
        AbstractFactory factoryA = new ConcreteFactoryA();
        AbstractProduct product = factoryA.produce();
        product.method();
    }

    //抽象产品
    public static abstract class AbstractProduct {
        public abstract void method();
    }

    //抽象工厂
    public static abstract class AbstractFactory {
        public abstract AbstractProduct produce();
    }


    //具体产品
    public static class ProductA extends AbstractProduct {
        @Override
        public void method() {
            System.out.println("I'm ProductA!");
        }
    }

    public static class ProductB extends AbstractProduct {
        @Override
        public void method() {
            System.out.println("I'm ProductB!");
        }
    }

    //具体工厂
    public static class ConcreteFactoryA extends AbstractFactory {
        @Override
        public ProductA produce() {
            return new ProductA();
        }
    }

    public static class ConcreteFactoryB extends AbstractFactory {
        @Override
        public ProductB produce() {
            return new ProductB();
        }
    }


}
