
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class DFS {
	static int[][] arcs1 ={{0,1,0,1,0,0,0,1,0},
						   {1,0,1,0,1,0,1,0,0},
						   {0,1,0,1,1,0,0,0,0},
						   {1,0,1,0,0,0,0,0,0},
						   {0,1,1,0,0,0,0,0,0},
						   {0,0,0,0,0,0,0,0,0},
						   {0,1,0,0,0,0,0,0,1},
						   {1,0,0,0,0,0,0,0,0},
						   {0,0,0,0,0,0,1,0,0}};  
		static int[][] arcs2 = {{0,1,0,1,0,0}, 
							   {1,0,1,0,1,1},
							   {0,1,0,1,0,1},
							   {1,0,1,0,0,0},
							   {0,1,0,0,0,1},
							   {0,1,1,0,1,0}
							   };
	static int sum = 6;
	static boolean[] visited = new boolean[sum];
	//深度优先递归，连通图
	static void DFS(int[][] g,int v){
		System.out.print(v);
		visited[v]=true;
		for(int i=0;i<sum;i++){
			if(!visited[i]&&g[v][i]==1){
				DFS(g,i);
			}
		}
	}
	//深度优先非递归，连通图
	static void DFS_u(int[][] g){
		Stack<Integer> st = new Stack<Integer>();
		st.push(0);
		visited[0] = true;
		while(!st.empty()){
			int x = st.pop(),i;
			System.out.print(x);
			//标志是否找到相邻且未被访问的点
			for(i=1;i<sum;i++){
				if(!visited[i]&&g[x][i]==1){
					st.push(i);
					visited[i] = true;
				}
			}
		}
	}

	//广度优先非递归，连通图
	static void BFS(int[][] g){
		Queue<Integer> qu = new LinkedList<Integer>();
		visited[0]=true;
		qu.add(0);
		while(!qu.isEmpty()){
			int x=qu.remove(),i;
			System.out.print(x);
			//标志是否找到相邻且未被访问的点
			for(i=1;i<sum;i++){
				if(!visited[i]&&g[x][i]==1){
					qu.add(i);
					visited[i] = true;
				}
			}

		}
	}
	public static void main(String[] args){
//		BFS(arcs2);
		DFS(arcs2,0);
		System.out.println();
		DFS_u(arcs2);
	}
}
