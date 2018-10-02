package start;

import java.util.Stack;

public class Histogram {

    private static class Node{
        public int height;
        public int weight;

        public Node(int height, int weight) {
            this.height = height;
            this.weight = weight;
        }
        public int getAreaSize(){
            return height*weight;
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println(solve(new int[]{2,1,4,5,1,3,3}));
        System.out.println(solve(new int[]{1000,1000,1000,1000}));
    }

    public static int solve(int[] array){
        int max = 0;
        Stack<Node> stack = new Stack<>();
        int index = 0;
        stack.push(new Node(array[index++],1));
        Node temp;
        int areaSize ,count;
        for(;index<array.length;index++) {
            count = 0;
            while (!stack.empty() && stack.peek().height > array[index]) {
                temp = stack.pop();
                count++;
                areaSize = temp.getAreaSize();
                max = max > areaSize ? max : areaSize;
                if (!stack.empty()) {
                    stack.peek().weight += count;
                }
            }
            if(stack.empty()||array[index]>stack.peek().height){
                stack.push(new Node(array[index],count+1));
            }
            else {
                stack.peek().weight++;
            }
        }
        while (!stack.empty()){
            temp = stack.pop();
            areaSize = temp.getAreaSize();
            max = max > areaSize ? max : areaSize;
            if (!stack.empty()) {
                stack.peek().weight++;
            }
        }
        return max;
    }
}
