package start;

public class HeapSort{
	public static int len=8;
	public static int[] h = {3,6,5,7,9,1,9,8};
	public static void main(String[] args) {	
		for(int i=len/2-1;i>=0;i--){
			siftHeap(h, i, len);
		}
		for(int i=1;i<len;i++){
			int temp = h[0];
			h[0]=h[len-i];
			h[len-i]=temp;
			siftHeap(h, 0, len-i);
		}
		for(int i:h){
			System.out.print(i+" ");
		}
	}
	static void siftHeap(int[] h,int k,int n){
		int i,j,temp;
		i=k;
		j=2*i+1;
		while(j<n){
			if(j<n-1&&h[j]<h[j+1])
				j++;
			if(h[i]>h[j])
				break;
			else{
				temp=h[i];
				h[i]=h[j];
				h[j]=temp;
				i=j;
				j=2*i+1;
			}
		}
	}
}
