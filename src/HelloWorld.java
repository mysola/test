import java.util.*;

public class HelloWorld {
    public final static int len = 3;

    int anInt = 2;
    void test(){
        System.out.println(anInt);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Integer> queue = new LinkedList<>();
        Stack stack = new Stack();
        int[] array = new int[len];
        int[] seq = new int[len];
        int[] flag = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = i;
        }
        f(seq,0, flag);
    }

    static void f1(int index, int[] flag) {
        if(index==len){
            for (int i = 0; i < index; i++) {
                System.out.print(flag[i]);
            }
            System.out.println();
            return;
        }
        flag[index] = 0;
        f1(index+1,flag);
		flag[index] = 1;
		f1(index+1,flag);
    }

    static void f(int[] seq, int index, int[] flag) {
        if (index == len) {
            for (int i = 0; i < len; i++) {
                System.out.print(seq[i]);
            }
            System.out.println();
            return;
        }

        for (int i = 0; i < len; i++) {
            if (flag[i] == 0) {
                seq[index++] = i;
                flag[i] = 1;
                f(seq, index, flag);
                index--;
                flag[i] = 0;
            }
        }
    }
}

