package start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitalDP {
    private static int[][] dp = new int[10][10];

    private static int[] digit = new int[8];

    private static int len = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        String[] ss = null;
        while((s=br.readLine())!=null){
            ss = s.split(" ");
            if(ss[0].equals("0")&&ss[1].equals("0"))
                break;
            System.out.println(dfs(Integer.valueOf(ss[1])-Integer.valueOf(ss[0])));
        }
    }

    private static void getDP(){
        dp = new int[10][10];
        len = 0;
        dp[0][0] = 1;
        for(int i=1;i<10;i++){
            for(int j=0;j<10;j++) {
                if (j == 4)
                    dp[i][j] = 0;
                else {
                    for (int k = 0; k < 10; k++)
                        dp[i][j] += dp[i - 1][k];
                    if (j == 6)
                        dp[i][j] -= dp[i - 1][2];
                }
            }
        }
    }

    public static int dfs(int n) {
        getDP();
        while(n>0)
        {
            digit[++len] = n%10;
            n/=10;
        }
        digit[len+1]=0;
        int ans = 0;
        for(int i=len;i>0;i--)
        {
            for(int j=0;j<digit[i];j++)
            {
                if(digit[i+1]!=6||j!=2)
                    ans+=dp[i][j];
            }
            if(digit[i]==4||(digit[i]==2&&digit[i+1]==6))
                break;
        }
        return  ans;
    }

    static void  init()
    {
        len = 0;
        dp = new int[10][10];
        dp[0][0] = 1;
        for(int i=1;i<=7;i++)
        {
            for(int j=0;j<10;j++)
            {
                for(int k=0;k<10;k++)
                {
                    if(j!=4&&!(j==6&&k==2))
                        dp[i][j]  += dp[i-1][k];
                }
            }
        }
    }

    static int solve(int n)//找的范围是<n
    {
        init();
        while(n>0)
        {
            digit[++len] = n%10;
            n/=10;
        }
        digit[len+1]=0;
        int ans = 0;
        for(int i=len;i>0;i--)
        {
            for(int j=0;j<digit[i];j++)
            {
                if(j!=4&&!(digit[i+1]==6&&j==2))
                    ans+=dp[i][j];
            }
            if(digit[i]==4||(digit[i]==2&&digit[i+1]==6))
                break;
        }
        return  ans;
    }

}
