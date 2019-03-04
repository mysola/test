package tarjan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Stack;

public class LCA {
    public static void main(String[] args) throws Exception {
        readInput();
    }

    public static void readInput() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("src/tarjan/inputLCA"));
        String temp = null;
        while((temp = br.readLine())!=null){
            String[] strings = temp.split(" ");
            sum = Integer.parseInt(strings[0]);
            edge = Integer.valueOf(strings[1]);
            arcs = new short[sum][sum];
            find = new int[sum];
            visit = new boolean[sum];
            for(int i=0;i< edge;i++){
                strings = br.readLine().split(" ");
                arcs[Integer.valueOf(strings[0])-1][Integer.valueOf(strings[1])-1] = 1;
            }
            solve(0);
        }
    }

    public static int sum = 0;
    public static int edge = 0;
    public static int temp = 0;

    public static int[][] q = {{8,7},{3,5},{6,4},{4,2}};
    public static boolean[] visit;
    public static int[] find;
    public static short[][] arcs;

    public static void solve(int i) {
        find[i] = i;
        for (int j = 0; j < sum; j++) {
            if (arcs[i][j] != 0) {
                //若点i的子节点j的所有子节点全部搜索完，将j合并到i
                solve(j);
                find[j] = i;
                visit[j] = true;
            }
        }
        for (int j = 0; j < q.length; j++) {
            if(i == q[j][0]&&visit[q[j][1]]){
                temp = q[j][1];
                while(find[temp]!=temp){
                    temp = find[temp];
                }
                System.out.println(i+","+q[j][1]+"->"+temp);
            }
            if(i == q[j][1]&&visit[q[j][0]]){
                temp = q[j][0];
                while(find[temp]!=temp){
                    temp = find[temp];
                }
                System.out.println(i+","+q[j][0]+"->"+temp);
            }
        }
    }
}
