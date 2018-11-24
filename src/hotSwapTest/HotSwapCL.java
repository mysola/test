package hotSwapTest;

import java.io.*;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class HotSwapCL extends ClassLoader{
    private String baseDir;

    public HotSwapCL(String baseDir) {
        super();
        this.baseDir = baseDir;
    }

    private String getClassFile(String className){
        return baseDir+File.separator+className+".class";
    }

    @Override
    protected Class findClass(String className) throws ClassNotFoundException {
        Class clazz = this.findLoadedClass(className);
        if (null == clazz) {
            try {
                String classFile = getClassFile(className);
                FileInputStream fis = new FileInputStream(classFile);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                clazz = defineClass("hotSwapTest."+className, bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("can not load class "+className +" from DynamicLoader.");
            }
        }
        return clazz;
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        return loadClass(name,false);
    }

    @Override
    protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // First, check if the class has already been loaded
        Class re=findClass(name);
        if(re==null){
            return super.loadClass(name,resolve);
        }
        return re;
    }
}
