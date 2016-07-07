package base.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yutianran on 16/7/6.
 */
public class TestLiu {

    private static final int HHHH = 0;
    private static String[] str1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] str2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] str3 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] str4 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] str5 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void main(String[] args) {
        String[] liu = getData(str1, str2, str3, str4, str5);
        for (int j = 0; j < liu.length; j++) {
            System.out.println("lgy::" + liu[j]);
        }
        System.out.println("size=" + liu.length);

        test();
    }

    private static String[] getData(String[] str1, String[] str2, String[] str3, String[] str4, String[] str5) {
        int n = str1.length * str2.length * str3.length * str4.length * str5.length;
        int m = 0;
        String[] test = new String[n];
        for (int i = 0; i < str1.length; i++)
            for (int k = 0; k < str2.length; k++)
                for (int l = 0; l < str3.length; l++)
                    for (int u = 0; u < str4.length; u++)
                        for (int y = 0; y < str5.length; y++) {
                            test[m] = str1[i] + str2[k] + str3[l] + str4[u] + str5[y];
                            m++;
                        }
        while (m != n - 1) {

        }
        return test;
    }


    private static void test() {
        //源数据
        List<String[]> srcList = new ArrayList<>();
        //目标数据
        List<String[]> destList = getRecursionData(srcList);
    }

    //迭代合成,直到size=1
    private static List<String[]> getRecursionData(List<String[]> srcList) {
        if (srcList.size() > 1) {
            recursive();
            return getRecursionData(srcList);
        } else if (srcList.size() == 1) {
            return srcList;
        } else {
            throw new RuntimeException("srcList.size至少为1");
        }
    }

    private static void recursive() {

    }

    //合成
    private static String[] merge(String[] str1, String[] str2) {
        int n = str1.length * str2.length;
        int m = 0;
        String[] test = new String[n];
        for (int i = 0; i < str1.length; i++) {
            for (int k = 0; k < str2.length; k++) {
                test[m] = str1[i] + str2[k];
                m++;
            }
        }
        return test;
    }


}
