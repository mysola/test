package start;

public class Fibonacci {
    public static void main(String[] args) {
        fibonacci(10);
    }
    public static void fibonacci(int n){
        int[][] res = {{1,1},{1,0}};
        for(int i = 0; i < n; i++)
            System.out.println(indexN(res,i)[0][0]);
        for(int i = 1; i < n; i++)
            System.out.println(recIndexN(res,i)[0][0]);
    }

    public static int[][] indexN(int[][] a, int n){
        //初值为单位矩阵
        int[][] res = {{1,0},{0,1}};
        //a存放当前位的“权值”，每处理一位，权值 = 权值的平方
        //将n转换为2进制，依次从低位向高位处理每个1，结果乘上当前权值，0不做任何处理
        while(n!=0){
            if((n&1)==1)
                res = mul(res,a);
            a = mul(a,a);
            n >>= 1;
        }
        return res;
    }

    public static int[][] recIndexN(int[][] a, int n){
        if(n==1)
            return a;
        int[][] res = {{1,0},{0,1}};
        if((n&1)==1)
            res = mul(a,res);
        return mul(res,mul(recIndexN(a,n>>1),recIndexN(a,n>>1)));
    }

    public static int[][] mul(int[][] a, int[][] b){
        int[][] res = new int[2][2];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }
}
