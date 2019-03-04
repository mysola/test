package oj;

public class Leetcode_130 {

  private int[] parent;

  public int find(int i) {
    while (i != parent[i]) {
      parent[i] = parent[parent[i]];
      i = parent[i];
    }
    return i;
  }

  public void union(int i, int j) {
    int ip = find(i);
    int jp = find(j);
    if (ip != jp) {
      parent[ip] = jp;
    }
  }

  public void init(int length) {
    parent = new int[length];
    for (int i = 0; i < length; i++) {
      parent[i] = i;
    }
  }

  public static void main(String[] args) {
    new Leetcode_130().solve(new char[][]{{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'O'}});
  }

  public void solve(char[][] board) {
    if(board.length==0||board[0].length==0)
      return;
    int m=board.length;
    int n=board[0].length;
    int parentLen=m*n+1;
    init(parentLen);
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(board[i][j]=='X')
          continue;
        int index=i*n+j;
        if(i==0||i==m-1||j==0||j==n-1){
          union(index,parentLen-1);
          continue;
        }
        if(i>0 && board[i-1][j]=='O'){
          union(index,index-n);
        }
        if(i<m-1 && board[i+1][j]=='O'){
          union(index,index+n);
        }
        if(j>0 && board[i][j-1]=='O'){
          union(index,index-1);
        }
        if(j<n-1 && board[i][j+1]=='O'){
          union(index,index+1);
        }
      }
    }

    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        System.out.print(find(i*n+j)+" ");
        if(board[i][j]=='O'&&find(i*n+j)!=find(parentLen-1))
          board[i][j]='X';
      }
    }
  }

}


