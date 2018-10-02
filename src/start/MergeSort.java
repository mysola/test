package start;

public class MergeSort {
    public static int len=8;
    public static int[] h = {3,6,5,7,8,1,2,6};
    public static int count = 0;
    public static void main(String[] args) {
       mergeSort(h,0,5);

        System.out.println(count);

    }

    static void mergeSort(int[] h,int s,int e){
        int[] h1 = new int[e+1];
        if(s==e)
            return;
        int mid = (s+e)>>1;
        mergeSort(h,s,mid);
        mergeSort(h,mid+1,e);
        merge(h1,h,s,e,mid);
        for(int i=s;i<e+1;i++)
            h[i] = h1[i];
    }
    static void merge(int[] h1,int[] h2,int s,int e,int mid){
        int i = s ,j = mid+1;
        int index = s;
        while (i<mid+1&&j<e+1){
            if(h2[i]>h2[j]){
                h1[index++] = h2[j++];
                count += mid-i+1;
            }
            else
                h1[index++] = h2[i++];
        }
        while(i<mid+1){
            h1[index++] = h2[i++];
        }
        while(j<e+1){
            h1[index++] = h2[j++];
        }
    }
}
