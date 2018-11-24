package dynamicTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class DynamicProxy {
    interface A {
        void sayhello();

        void sayhello1();
    }

    static class B implements A {
        public int i = 1;
        @Override
        public void sayhello() {
            System.out.println(i);
            System.out.println("hello world");
        }

        public void sayhello1() {
            System.out.println("hello world1");
        }

        public B() {
            sayhello();
        }
    }

    static class DynamicHandle implements InvocationHandler {
        private Object obj;

        public DynamicHandle(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("pro");
            method.invoke(obj, args);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
      System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
      A a = new B();
        A objProxy = (A)Proxy.newProxyInstance(a.getClass().getClassLoader(),B.class.getInterfaces(),new DynamicHandle(a));
        objProxy.sayhello1();
    }
}