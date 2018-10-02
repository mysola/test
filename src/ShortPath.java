
public class ShortPath {
	static int MAX=1000;
	static int[][] arcs ={{MAX,MAX,10,MAX,30,100},
						   {MAX,MAX,5,MAX,MAX,MAX},
						   {MAX,MAX,MAX,50,MAX,MAX},
						   {MAX,MAX,MAX,MAX,MAX,10},
						   {MAX,MAX,MAX,20,MAX,60},
						   {MAX,MAX,MAX,MAX,MAX,MAX}};
	static int sum=6;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(shortPath());
	}
	public static int shortPath(){
		int i,j,temp;
		int[] cost=new int[sum];
		int[] path=new int[sum];
		for(i=1;i<sum;i++){
			cost[i]=MAX;
			path[i]=-1;
		}
		//0ÎªÔ­µã
		cost[0]=0;
		path[0]=-1;
		for(i=1;i<sum;i++){
			for(j=0;j<sum;j++){
				if(j!=i&&arcs[j][i]+cost[j]<cost[i]){
					cost[i]=arcs[j][i]+cost[j];
					path[i]=j;
				}
			}
		}
		System.out.print(sum-1);
		i=sum-1;
		while(path[i]>=0){
			System.out.print("<-"+path[i]);
			i=path[i];
		}
		System.out.println();
		return cost[sum-1];
	}
}
