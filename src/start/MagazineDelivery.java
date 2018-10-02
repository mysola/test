package start;

import java.util.Scanner;

public class MagazineDelivery {
	
	public static void main(String[] args) {
		int testSum, i, j, k, colSumPerRow[],locationsSum,arc[][], status[][];	
		//����̨������
		Scanner sc = new Scanner(System.in);
		//������������
		testSum = sc.nextInt();
		//ÿһ�е���Ч����
		colSumPerRow = new int[31];
		for (i = 1; i <= 30; i++)
			colSumPerRow[i] = (i - 1) * (i - 2) / 2 + 1;
		//ÿһ�ж�Ӧ��״̬
		status = generateStatus(30, colSumPerRow[30]);
		//���������ľ���
		arc = new int[31][31];
		for (i = 0; i < testSum; i++){
			//��ǰ���������е������
			locationsSum = sc.nextInt();
			// ���ζ�ȡÿһ��
			for (j = 1; j < locationsSum; j++) {
				// ����ÿһ������
				for (k = j + 1; k <= locationsSum; k++) {
					arc[j][k] = sc.nextInt();
				}
			}
			//�����ǰ���������ʱ��
			System.out.println(minimumTime(locationsSum,arc,colSumPerRow,status));
		}	
		//�ر�������
		sc.close();
	}
	/**
	 * ���ʱ���㷨
	 * @param locationsSum  �������
	 * @param arc  ��ά���飬���������֮��ľ���
	 * @param colSumPerRow  һά���飬����ÿһ�ж�Ӧ����Ч����
	 * @param status  ��ά���飬�������Զ��ĳ��⣬������������λ�����
	 * @return
	 */
	public static int minimumTime(int locationsSum, int[][] arc,int[] colSumPerRow,int[][] status) {
		int i, j, k,min, temp = 0,col,row,nextLocationToMove;
		//dp���������
		row = locationsSum+1;
		//dp����������Ч����
		col = colSumPerRow[locationsSum];
		int[][] dp = new int[row][col] ;
		//��һ�д���ֻ�轫��ֽ��������һ���ص�,ʱ��Ϊ0(��ʼ״̬)
		dp[1][0]=0;
		for (i = 2; i <= locationsSum; i++) {
			// �Ե�i��������дÿһ��
			for (j = 0; j < colSumPerRow[i]; j++) {				
				min = 999;
				// ������һ�е�ÿ��ֵ
				for (k = 0; k < colSumPerRow[i - 1]; k++) {
					status[k][2] = i-1;
					//�ж϶��ڵ�ǰ״̬a��status[j]��i�����Ƿ������һ״̬b(status[k],i-1)
					//ʹ�ô�״̬bת�Ƶ�״̬aֻ��Ҫ��һ�����ӵ�nextLocationToMove�ƶ�����j
					nextLocationToMove = contain(status[k], status[j]);
					//����nextLocationToMove����
					if (nextLocationToMove != -1) {
						//�������з�����������������е���״̬a��������ʱ��
						temp = arc[nextLocationToMove][i] + dp[i - 1][k];
						if (min > temp) {
							min = temp;
						}
					}
				}
				// ��дdp����
				dp[i][j] = min;
			}
		}
		// �ҳ����һ��(������ÿ��״̬����ͬ�Ҿ�������������)�е���Сֵ
		min = dp[row-1][0];
		for (i = 0; i < col; i++) {
			if (min > dp[row-1][i])
				min = dp[row-1][i];
		}
		return min;
	}
	/**
	 * ����N�����Ӧ������(length��)״̬,ÿ��״̬������ֽ������i��1~N��ʱ�����˵�i�����������������������ڵĵ�
	 * @param N  �������
	 * @param length ״̬����
	 * @return ����״̬�Ķ�ά����
	 */
	public static int[][] generateStatus(int N, int length) {
		int[][] y = new int[length][3];
		int cur = 0;// �����±�
		y[cur][0] = y[cur][1] = 1; 
		cur++;
		//�������ɵ㼯��1��2��3��������N-1������Ϊ2���Ӽ�
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < i; j++) {
				y[cur][0] = j;
				y[cur++][1] = i;
			}
		}
		return y;
	}
	//�ж�����a������Ϊ3���Ƿ��������b������Ϊ2�����������򷵻�a�����в�����������b�е��������򷵻�-1
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
