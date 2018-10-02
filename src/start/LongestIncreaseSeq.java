package start;

public class LongestIncreaseSeq {
    public static Integer[] array = {1,5,3,6,4,7,9,2,8};
    public static Integer length = 9;

    public static void main(String[] args){
        System.out.println(longestIncreaseSeq(array,length));
    }

    public synchronized static Integer longestIncreaseSeq(Integer[] array,Integer length){
        int[] dp = new int[length];
        int i = 0 ,j = 0 ,k = 0;
        int max = 0;
        dp[0] = 1;
        for(i=1;i<length;i++){
            max = 0;
            for(j=0;j<i;j++){
                if(max<dp[j]+1&&array[i]>array[j]){
                    max = dp[j]+1;
                }
            }
            dp[i] = max;
        }

        return dp[length-1];
    }
}
