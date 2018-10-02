package start;

public class Bag0_1 {
    public static int sum = 5;
    public static int[][] arc = {
            {2,6},
            {2,3},
            {6,5},
            {5,4},
            {4,6}
    };
    public static int maxV = 10;

    public static void main(String[] args){
        int[] result = maxValue(sum,arc,maxV);
        for(int i:result)
            System.out.println(i);
    }

    public static int[] maxValue(int sum,int arc[][],int maxV){
        int[] result = new int[sum];
        int[][] dp = new int[sum+1][maxV+1];
        int i=0,j=0;
        for(i=0;i<sum+1;i++){
            dp[i][0] = 0;
        }
        for(j=0;j<maxV+1;j++){
            dp[0][j] = 0;
        }
        for(j=1;j<maxV+1;j++){
            for(i=1;i<sum+1;i++){
                if(arc[i-1][0]>j){
                    dp[i][j] = dp[i-1][j];
                }
                else {
                    dp[i][j] = max(dp[i-1][j],dp[i-1][j-arc[i-1][0]]+arc[i-1][1]);
                }
            }
        }
        int x=sum,y=maxV;
        while(x>0){
            if(dp[x][y]>dp[x-1][y]){
                result[x-1]=1;
                y = y-arc[x-1][0];
            }
            x--;
        }
        return result;
    }
    public static int max(int a,int b){
        if(a>b)
            return a;
        else
            return b;
    }
}
