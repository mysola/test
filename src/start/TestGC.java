package start;

public class TestGC {
    public static class A{
        public B b ;

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("a");
        }
    }
    public static class B{
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("b");
        }
    }

    public static void main(String[] args){
        B b = new B();
        f(b);
        System.gc();
    }

    public static void f(B b){
        A a = new A();
        a.b = b;
    }
}
