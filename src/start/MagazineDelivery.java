package start;

import java.util.Scanner;

public class MagazineDelivery {
	
	public static void main(String[] args) {
		int testSum, i, j, k, colSumPerRow[],locationsSum,arc[][], status[][];	
		//控制台输入流
		Scanner sc = new Scanner(System.in);
		//测试用例数量
		testSum = sc.nextInt();
		//每一行的有效列数
		colSumPerRow = new int[31];
		for (i = 1; i <= 30; i++)
			colSumPerRow[i] = (i - 1) * (i - 2) / 2 + 1;
		//每一列对应的状态
		status = generateStatus(30, colSumPerRow[30]);
		//保存点与点间的距离
		arc = new int[31][31];
		for (i = 0; i < testSum; i++){
			//当前测试用例中点的数量
			locationsSum = sc.nextInt();
			// 依次读取每一行
			for (j = 1; j < locationsSum; j++) {
				// 保存每一行数据
				for (k = j + 1; k <= locationsSum; k++) {
					arc[j][k] = sc.nextInt();
				}
			}
			//输出当前用例的最短时间
			System.out.println(minimumTime(locationsSum,arc,colSumPerRow,status));
		}	
		//关闭输入流
		sc.close();
	}
	/**
	 * 最短时间算法
	 * @param locationsSum  点的数量
	 * @param arc  二维数组，保存各个点之间的距离
	 * @param colSumPerRow  一维数组，保存每一行对应的有效列数
	 * @param status  二维数组，保存除最远点的车外，另外两辆车的位置组合
	 * @return
	 */
	public static int minimumTime(int locationsSum, int[][] arc,int[] colSumPerRow,int[][] status) {
		int i, j, k,min, temp = 0,col,row,nextLocationToMove;
		//dp数组的行数
		row = locationsSum+1;
		//dp数组的最大有效列数
		col = colSumPerRow[locationsSum];
		int[][] dp = new int[row][col] ;
		//第一行代表只需将报纸发送至第一个地点,时间为0(初始状态)
		dp[1][0]=0;
		for (i = 2; i <= locationsSum; i++) {
			// 对第i行依次填写每一列
			for (j = 0; j < colSumPerRow[i]; j++) {				
				min = 999;
				// 遍历上一列的每个值
				for (k = 0; k < colSumPerRow[i - 1]; k++) {
					status[k][2] = i-1;
					//判断对于当前状态a（status[j]，i），是否存在上一状态b(status[k],i-1)
					//使得从状态b转移到状态a只需要将一辆车从点nextLocationToMove移动到点j
					nextLocationToMove = contain(status[k], status[j]);
					//若点nextLocationToMove存在
					if (nextLocationToMove != -1) {
						//计算所有符合上述条件的情况中到达状态a所需的最短时间
						temp = arc[nextLocationToMove][i] + dp[i - 1][k];
						if (min > temp) {
							min = temp;
						}
					}
				}
				// 填写dp数组
				dp[i][j] = min;
			}
		}
		// 找出最后一行(该行中每个状态均不同且均满足题设条件)中的最小值
		min = dp[row-1][0];
		for (i = 0; i < col; i++) {
			if (min > dp[row-1][i])
				min = dp[row-1][i];
		}
		return min;
	}
	/**
	 * 生成N个点对应的所有(length个)状态,每个状态代表将报纸送至点i（1~N）时，除了点i处的汽车，另外两辆车所在的点
	 * @param N  点的数量
	 * @param length 状态总数
	 * @return 保存状态的二维数组
	 */
	public static int[][] generateStatus(int N, int length) {
		int[][] y = new int[length][3];
		int cur = 0;// 数组下标
		y[cur][0] = y[cur][1] = 1; 
		cur++;
		//以下生成点集｛1，2，3，……，N-1｝长度为2的子集
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < i; j++) {
				y[cur][0] = j;
				y[cur++][1] = i;
			}
		}
		return y;
	}
	//判断数组a（长度为3）是否包含数组b（长度为2），若包含则返回a数组中不存在于数组b中的数，否则返回-1
	public static int contain(int[] a,int[] b) {
		int i = 0, j = 0, temp = -1;
		int[] ar = { 1, 1, 1 };
		boolean f1 = false, f2 = false;	
		for (i = 0; i < 3; i++) {
			if (a[i] == b[0]) {
				ar[i] = 0;
				f1 = true;
				break;
			}
		}
		if (!f1)
			return temp;
		for (j = 0; j < 3; j++) {
			if (j != i && a[j] == b[1]) {
				ar[j] = 0;
				f2 = true;
				break;
			}
		}
		if (!f2)
			return temp;
		for (int k = 0; k < 3; k++) {
			if (ar[k] != 0) {
				temp = a[k];
			}
		}
		return temp;
	}
}
