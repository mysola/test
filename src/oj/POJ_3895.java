package oj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class POJ_3895 {
    private static Node[] arc;
    private static int ans = 0;

    private static class Node {
        int node;
        Node next;

        public Node(int node, Node next) {
            this.node = node;
            this.next = next;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testSum = Integer.valueOf(br.readLine().trim());
        int len, edge, s, e;
        int[] stepSum;
        boolean[] visit;
        String[] temp;
        for (int i = 0; i < testSum; i++) {
            temp = br.readLine().split(" ");
            len = Integer.valueOf(temp[0]);
            edge = Integer.valueOf(temp[1]);
            arc = new Node[len];
            for (int j = 0; j < edge; j++) {
                temp = br.readLine().split(" ");
                s = Integer.valueOf(temp[0]);
                e = Integer.valueOf(temp[1]);
                if (arc[s - 1] == null) {
                    arc[s - 1] = new Node(e - 1, null);
                } else {
                    Node n1 = arc[s - 1];
                    while (n1.next != null) {
                        n1 = n1.next;
                    }
                    n1.next = new Node(e - 1, null);
                }
                if (arc[e - 1] == null) {
                    arc[e - 1] = new Node(s - 1, null);
                } else {
                    Node n1 = arc[e - 1];
                    while (n1.next != null) {
                        n1 = n1.next;
                    }
                    n1.next = new Node(s - 1, null);
                }
            }
            stepSum = new int[len];
            visit = new boolean[len];
            ans = 0;
            for(int j=0;j< len;j++){
                if(!visit[j]){
                    solve(j, 0, stepSum, visit);
                }
            }
            System.out.println(ans>2?ans:0);
        }
    }

    public static void solve(int node, int step, int[] stepSum, boolean[] visit) {
        visit[node] = true;
        stepSum[node] = step;
        Node n1 = arc[node];
        while (n1 != null) {
            if (visit[n1.node]) {
                ans = ans > step - stepSum[n1.node] + 1 ? ans : step - stepSum[n1.node] + 1;
            } else {
                solve(n1.node, step + 1, stepSum, visit);
            }
            n1 = n1.next;
        }
    }
}
