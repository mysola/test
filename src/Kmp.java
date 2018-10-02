
public class Kmp {

    public static void main(String[] args){
        System.out.println(new Integer(1)==new Integer(1));
        System.out.println(new Double(1)==new Double(1));
        if(kmp("abaa","aba"))
            System.out.println("true");
    }

    public static boolean kmp(String pattern,String target){
        int[] next = getNext(pattern);
        int j = 0;
        for(int i = 0;i < target.length();i++){
            while (j>0&&target.charAt(i)!=pattern.charAt(j)){
                j = next[j];
            }
            if(target.charAt(i)==pattern.charAt(j)){
                j++;
            }
            if(j==pattern.length())
                return true;
        }
        return false;
    }

    public static int[] getNext(String pattern){
        int[] next = new int[pattern.length()];
        next[0] = -1;
        int j = 0;
        for(int i = 1;i < pattern.length();i++){
            while (j>0&&pattern.charAt(i-1)!=pattern.charAt(j)){
                j = next[j];
            }
            if(pattern.charAt(i-1)==pattern.charAt(j)&&i-1!=j)
                j++;
            next[i] = j;
        }
        return next;
    }

}
