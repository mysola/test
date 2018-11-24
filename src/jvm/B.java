package jvm;

public class B extends A{
  private String bbbbb;

  @Override
  public void method1() {
    System.out.println("B.1");
  }


  public static void main(String[] args) throws InterruptedException {
    System.out.println("a,b".replaceAll("(a)", "\1"));
    A a = new B();
    a.method2();
    System.out.println(a.bbbbb);
  }
}
