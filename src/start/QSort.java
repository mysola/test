package start;

import java.util.Stack;

public class QSort {
	public static int j=0;
	public static void main(String[] args) {
		noRecqSort(new int[]{5, 8, 4, 3, 2, 9, 1}, 0, 6);
	}
	public static void qSort(int[] a,int st,int ed){
		int p=0;
		if(st<ed){
			p=sort(a,st,ed);
			qSort(a,st,p-1);
			qSort(a,p+1,ed);
		}
	}

	public static void noRecqSort(int[] a,int st,int ed){

		class Node{
			int s;
			int e;

			public Node(int s, int e) {
				this.s = s;
				this.e = e;
			}
		}
		Stack<Node> stack = new Stack<>();
		int p;
		stack.push(new Node(st, ed));
		while (st < ed || !stack.empty()) {
			
			while (st < ed) {

				Node node = stack.pop();
				p = sort(a,st, ed);
				stack.push(new Node(p + 1, ed));
				ed = p - 1;
			}
			Node node = stack.pop();
			st = node.s;
			ed = node.e;
		}
	}
	
	public static int sort(int[] a,int st,int ed){
		int s=st,e=ed;
		int temp;
		while(s<e){
			while(s<e&&a[s]<=a[e])
				e--;
			if(s<e){
				temp=a[s];
				a[s]=a[e];
				a[e]=temp;
				s++;
			}
			while(s<e&&a[s]<=a[e])
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
