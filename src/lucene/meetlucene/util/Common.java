package lucene.meetlucene.util;

import java.io.UnsupportedEncodingException;

public class Common {
    public static String indexDir;
    public static String dataDir;

    static {
        try {
            indexDir = new String(
                    "E:\\项目文件\\IdeaProjects\\Test\\src\\lucene\\meetlucene\\indexs".getBytes("gbk"),"utf-8");
            dataDir = new String(
                    "E:\\项目文件\\IdeaProjects\\Test\\src\\lucene\\meetlucene\\data".getBytes("gbk"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
