package base.designpattern.creational;

/**
 * 抽象工厂模式
 * <p>
 * Created by yutianran on 16/7/1.
 */
public class AbstractFactoryPattern {

    public static void main(String[] args) {
        AbstractFactory factory = new ConcreteFactory1();
        AbstractProductA producta1 = factory.produceA();
        AbstractProductB productb1 = factory.produceB();
        producta1.methodA();
        productb1.methodB();
    }

    //抽象产品族
    public static abstract class AbstractProductA {
        public abstract void methodA();
    }

    public static abstract class AbstractProductB {
        public abstract void methodB();
    }

    //抽象工厂
    public static abstract class AbstractFactory {
        public abstract AbstractProductA produceA();

        public abstract AbstractProductB produceB();
    }

    //具体产品
    public static class ProductA1 extends AbstractProductA {
        @Override
        public void methodA() {
            System.out.println("Im ProductA1!");
        }
    }

    public static class ProductA2 extends AbstractProductA {
        @Override
        public void methodA() {
            System.out.println("Im ProductA2!");
        }
    }

    public static class ProductB1 extends AbstractProductB {
        @Override
        public void methodB() {
            System.out.println("Im ProductB1!");
        }
    }

    public static class ProductB2 extends AbstractProductB {
        @Override
        public void methodB() {
            System.out.println("Im ProductB2!");
        }
    }

    //具体工厂
    public static class ConcreteFactory1 extends AbstractFactory {
        @Override
        public AbstractProductA produceA() {
            return new ProductA1();
        }

        @Override
        public AbstractProductB produceB() {
            return new ProductB1();
        }
    }

    public static class ConcreteFactory2 extends AbstractFactory {
        @Override
        public AbstractProductA produceA() {
            return new ProductA2();
        }

        @Override
        public AbstractProductB produceB() {
            return new ProductB2();
        }
    }
}
