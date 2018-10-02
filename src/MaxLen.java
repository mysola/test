
public class MaxLen {
	public static String a="acbefim";
	public static String b="abdcegi";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(maxLen(a,b));
	}
	//最长公告子序列
	public static int maxLen(String a,String b){
		int[][] t = new int[b.length()+1][a.length()+1];
		int[][] t1 = new int[b.length()+1][a.length()+1];
		char[] ac = a.toCharArray();
		char[] bc = b.toCharArray();
		for(int i=0;i<a.length()+1;i++)
			t[0][i]=0;
		for(int i=0;i<b.length()+1;i++)
			t[i][0]=0;
		for(int i=1;i<b.length()+1;i++){
			for(int j=1;j<a.length()+1;j++){
				if(bc[i-1]==ac[j-1]){
					t[i][j]=t[i-1][j-1]+1;
					t1[i][j]=1;
				}
				else{
					if(t[i-1][j]>t[i][j-1]){
						t[i][j]=t[i-1][j];
						t1[i][j]=2;
					}
					else{
						t[i][j]=t[i][j-1];
						t1[i][j]=3;
					}

				}
			}
		}
		int k=t[b.length()][a.length()];
		char[] m = new char[k];
		int a1=b.length(),a2=a.length();
		while(a1>0&&a2>0){
			if(t1[a1][a2]==1){
				m[k-1]=bc[a1-1];
				k--;
				a1--;
				a2--;
			}
			else if(t1[a1][a2]==2){
				a1--;
			}
			else a2--;
		}
		for(int i=0;i<b.length()+1;i++){
			for(int j=0;j<a.length()+1;j++){
				System.out.print(t[i][j]);
			}
			System.out.println();
		}
		System.out.println(m);
		return t[b.length()][a.length()];
	}
}
