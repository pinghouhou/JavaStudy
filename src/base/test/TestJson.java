package base.test;

import com.alibaba.fastjson.JSON;

/**
 * Created by yutianran on 16/7/6.
 */
public class TestJson {

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        Person person = new Person();
        person.setName("zhangsan");
        System.out.println(JSON.toJSONString(person));
    }

    private static void test2() {
        Person person = new Person();
        person.setName("xiaoming");
        person.setAge(13);
        System.out.println(JSON.toJSONString(person));
    }
}
