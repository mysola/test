package hotSwapTest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class Main implements Runnable{
    public String baseDir = new String("E:/项目文件/IdeaProjects/Test/src/hotSwapTest".getBytes("gbk"),"utf-8");

    public Main() throws UnsupportedEncodingException {
    }


    @Override
    public void run() {
                try{
                    HotSwapCL hotSwapCL = new HotSwapCL(baseDir);
                    Class cls = hotSwapCL.loadClass("Foo");
                    Object foo = cls.newInstance();
                    Method method = foo.getClass().getMethod("sayHello",new Class[]{});
                    method.invoke(foo);
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        Thread thread = new Thread(new Main());
        thread.start();
    }
}
