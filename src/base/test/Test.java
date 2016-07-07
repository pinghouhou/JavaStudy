package base.test;

/**
 * Created by yutianran on 16/7/5.
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = new int[]{8, 3, 0, 9, 1, 4, 2, 6};
        int[] index = new int[]{4, 1, 6, 1, 7, 3, 1, 5, 2, 6, 0};
        String tel = "";
        for (int i : index) {
            tel += arr[i];
        }
        System.out.println("联系方式:" + tel);
    }
}
