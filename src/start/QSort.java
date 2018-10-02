package start;

public class QSort {
	public static int j=0;
	public static void main(String[] args) {

	}
	public static void qSort(String[] a,int st,int ed){
		int p=0;
		if(st<ed){
			p=sort(a,st,ed);
			qSort(a,st,p-1);
			qSort(a,p+1,ed);
		}
	}
	public static int sort(String[] a,int st,int ed){
		int s=st,e=ed;
		String temp;
		while(s<e){
			while(s<e&&a[s].length()<=a[e].length())
				e--;
			if(s<e){
				temp=a[s];
				a[s]=a[e];
				a[e]=temp;
				s++;
			}
			while(s<e&&a[s].length()<=a[e].length())
				s++;
			if(s<e){
				temp=a[s];
				a[s]=a[e];
				a[e]=temp;
				e--;
			}
		}
		return s;
	}
}
