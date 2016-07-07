package base.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class ReadFile {

    public static void main(String[] args) throws IOException {
        String str = null;
        String path = "/Users/yutianran/A余天然/网盘/JavaStudy/txt/brand.json";
        System.out.println("START:");
        long time = System.currentTimeMillis();

        str = ReadFile.readFile(path);
        System.out.println("BufferedReader耗时:" + (System.currentTimeMillis() - time));
//        System.out.println(str);
        time = System.currentTimeMillis();

        str = ReadFile.readFileEncoding(path, "UTF-8");
        System.out.println("ByteArrayOutputStream耗时:" + (System.currentTimeMillis() - time));
//        System.out.println(str);
    }

    public static String readFile(String path) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(path));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = input.readLine()) != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        input.close();
        return sb.toString();
    }

    public static String readFileEncoding(String path, String encode)
            throws IOException {
        InputStream r = new FileInputStream(path);
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        byte tmp[] = new byte[1024];
        byte content[];
        int i = 0;
        while ((i = r.read(tmp)) > 0) {
            byteout.write(tmp, 0, i);
        }
        content = byteout.toByteArray();
        String str = new String(content, encode);
        r.close();
        byteout.close();
        return str;
    }

}
