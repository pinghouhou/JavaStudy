package base.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by yutianran on 16/7/7.
 */
public class DimenTool {
    static DecimalFormat fnum = new DecimalFormat("#0.00");

    static String rootDir = "/Users/yutianran/Desktop";
    static String file1080 = "dimens1080.xml";
    static String file720 = "dimens720.xml";
    static String file480 = "dimens480.xml";
    static String file320 = "dimens320.xml";

    public static void main(String[] args) {
        File file = new File(rootDir);
        if (!file.exists()) {
            file.mkdir();
        }
        writeFile(file320, getAllPx(1f));//生成320x480
        writeFile(file480, getAllPx(1.5f));//生成480x800
        writeFile(file720, getAllPx(2f));//生成720x1280
        writeFile(file1080, getAllPx(2.75f));//生成1080x1920(XiaoMi)
        writeFile(file1080, getAllPx(3f));//生成1080x1920
    }


    public static void writeFile(String fileName, String st) {
        try {
            FileWriter fw = new FileWriter(new File(rootDir, fileName));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(st);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAllPx(float density) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<resources>" + "\r\n");
            for (int i = 1; i <= 1920; i++) {
                sb.append("<dimen name=\"px" + i + "\">" + fnum.format(i / density) + "dp</dimen>"
                        + "\r\n");
            }
            sb.append("</resources>" + "\r\n");
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
