import java.util.concurrent.locks.LockSupport;

public class ClassLoadTest {
    public static void main(String[] args){
        Thread t =new Thread(() -> {

                   synchronized (ClassLoadTest.class){
//                       try {
//                           Thread.sleep(1000);
//                       } catch (InterruptedException e) {
//                           Thread.currentThread().interrupt();
//                       }
                       try {
                           ClassLoadTest.class.wait();
                       } catch (InterruptedException e) {
                           System.out.println(Thread.interrupted());
                           e.printStackTrace();
                       }
                       try {
                           Thread.sleep(3000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
        });
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();

        synchronized (ClassLoadTest.class){
            System.out.println(2);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        new Sub();
    }
    static class Sup{

//        static Sup sup= new Sub();
        static {
            System.out.println("sup");
            Sub sub1= new Sub();
            sub1.t();
            System.out.println("sup1");
        }
    }
    static class Sub extends Sup{
        {
            System.out.println(1);
        }
        void t(){
            System.out.println("t");
        }
        static {
            System.out.println("sub");
        }
    }
}
