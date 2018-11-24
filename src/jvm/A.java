package jvm;

public class A {
  public static final String aaaa = "abc";
  public String bbbbb = new String("sd");
  public static String cccc = "eeeee";
  static{
    System.out.println(1);
  }
  public void method2() {
    this.method1();
  }
  public void method1() {
    System.out.println("A.1");
  }
}
