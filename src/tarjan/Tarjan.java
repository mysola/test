package tarjan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

public class Tarjan {
    static short[][] arcs;

    static class super1{

        static void f(){

        }
    }
    static class sub extends super1{
        static {
            System.out.println(1);
        }
    }

    public static void main(String[] args) throws Exception {
        sub.f();
//        readInput();
    }

    public static void readInput() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("src/tarjan/input"));
        String temp = null;
        while((temp = br.readLine())!=null){
            String[] strings = temp.split(" ");
            sum = Integer.valueOf(strings[0]);
            edge = Integer.valueOf(strings[1]);
            arcs = new short[sum][sum];
            dfu = new int[sum];
            low = new int[sum];
            flag = new boolean[sum];
            index = 0;
            result = 0;
            resultFlag = 0;
            for(int i=0;i< edge;i++){
                strings = br.readLine().split(" ");
                arcs[Integer.valueOf(strings[0])-1][Integer.valueOf(strings[1])-1] = 1;
            }
            solve(0);
            if(resultFlag==1)
                System.out.println("YES");
            else
                System.out.println("NO");
        }

    }

    public static Stack<Integer> stack = new Stack<>();

    public static int sum = 0;
    public static int edge = 0;
    public static int index = 0;
    public static int result = 0;
    public static int resultFlag = 0;

    //时间戳，不变
    public static int[] dfu;
    //若点i属于强连通分量j，则low[i]等于min{dfu[k]}点k为j中任意点
    public static int[] low ;
    public static boolean[] flag;


    public static void solve(int i) {
        dfu[i] = low[i] = ++index;
        stack.push(i);
        flag[i] = true;
        for (int j = 0; j < sum; j++) {
            if (arcs[i][j] != 0) {
                if (dfu[j] == 0) {
                    solve(j);
                    low[i] = Integer.min(low[i], low[j]);
                } else if (flag[j]) {
                    low[i] = Integer.min(low[i], dfu[j]);
                }
            }
        }
        if (low[i] == dfu[i]) {
            int k = 0;
            while (true) {
                k = stack.pop();
                System.out.print(k);
                flag[k] = false;
                result++;
                if (k == i)
                    break;
            }
            System.out.println();
            if(result==index)
                resultFlag = 1;
            else
                result = 0;
        }
    }
}
