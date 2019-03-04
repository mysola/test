package start;

public class TSP {
	//???????
	public static int[][] arc = {{99,3,6,7},
								  {5,99,2,3},
								  {6,4,99,2},
								  {3,7,5,99}};
	public static int cityNum = 4;
	//???0???????
	public static char[] s = {'1','2','3'};
	//???????
	public static int chLen = (int)Math.pow(2, s.length);
	//???????
	public static int[] t = new int[s.length+1];	
	
	public static void main(String[] args) {
		String[] ch = new String[100];

		//???????????
		createSubset(ch, s, s.length);
		
		//????????????????????
//		QSort.qSort(ch, 0, chLen-1);
	
		
		DpTsp();
	}
	//????œý???
	public static int DpTsp(){
		int i,j;
		Stage[] st = new Stage[4];
		st[0] = new Stage();
		String[] s1 = {""};
		int[][] d = new int[cityNum][1];
		for(i=1;i<cityNum;i++)
			d[i][0]=arc[i][0];
		st[0].setCurArc(d);
		st[0].setCurCitys(s1);
		
		st[1] = new Stage();
		String[] s2 = {"1","2","3"};
		st[1].setCurCitys(s2);
		int[][] d1 = new int[cityNum][3];
		st[1].setCurArc(d1);
		st[0].writeNext(st[1]);
		st[1].print();
 		return 0;
	}
	//???????
	public static void createSubset(String[] ch,char[] s,int len){
		ch[0]="";
		int temp;
		for(int i=0;i<len;i++){
			temp=(int)Math.pow(2, i);
			for(int j=temp;j<2*temp;j++){
				ch[j]=ch[j-temp];
				ch[j]+=s[i];
			}
		}
	}
}
class Stage{
	//???????
	public static int[][] arc = {{99,3,6,7},
								  {5,99,2,3},
								  {6,4,99,2},
								  {3,7,5,99}};
	public static int cityNum = 4;
	
	public static char[] city = {'0','1','2','3'};
	//???????
	public int[][] curArc;
	//???¦Ì???
	public String[] curCitys;

	
	public static int getCityNum() {
		return cityNum;
	}
	public static void setCityNum(int cityNum) {
		Stage.cityNum = cityNum;
	}
	public static char[] getCity() {
		return city;
	}
	public static void setCity(char[] city) {
		Stage.city = city;
	}
	
	public int[][] getCurArc() {
		return curArc;
	}
	public void setCurArc(int[][] curArc) {
		this.curArc = curArc;
	}
	public String[] getCurCitys() {
		return curCitys;
	}
	public void setCurCitys(String[] curCitys) {
		this.curCitys = curCitys;
	}
	public int getCitysIndex(String[] a,String b){
		for(int i=0;i<a.length;i++){		
			if(a[i].equals(b))
				return i;
		}
		return -1;
	}
	public int getArcIndex(char a){
		for(int i=0;i<city.length;i++){		
			if(city[i]==a)
				return i;
		}
		return -1;
	}
	
	public void print(){
		for(int i=0;i<curCitys.length;i++){
			for(int j=0;j<cityNum;j++){
				System.out.print(curArc[j][i]+" ");
			}
			System.out.println();
		}
	}
	public void writeNext(Stage nextSta){
		String[] nextCitys = nextSta.getCurCitys();
		int nextCitysLen = nextCitys.length;
		int arcIndex,citysIndex,i,j,k,min;
		StringBuffer sb;
		for(i=0;i<nextCitysLen;i++){
			for(j=1;j<cityNum;j++){
				String curCity = String.valueOf(city[j]);
				if(!nextCitys[i].contains(curCity)){
					min=999;
					for(k=0;k<nextCitys[i].length();k++){
						sb = new StringBuffer(nextCitys[i]);
						citysIndex = getCitysIndex(curCitys,sb.deleteCharAt(k).toString());
						arcIndex = getArcIndex(nextCitys[i].charAt(k));
						if(min>curArc[arcIndex][citysIndex]+arc[j][arcIndex]){
							min = curArc[arcIndex][citysIndex]+arc[j][arcIndex];
						}
					}
					nextSta.getCurArc()[j][i] = min;				
				}
			}
		}
	}
}
