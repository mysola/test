package apriori;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Apriori {
    public String[] strSet;
    public String[][] transactionSet;

    public static void main(String[] args){
        Apriori apriori = new Apriori();
        apriori.strSet = new String[]{"a","b","c","d","e"};
        apriori.transactionSet = new String[][]{{"a","b","d"},
                                                {"a","b","c"},
                {"b","c","d"},
                {"a","b","e"},
                {"b","e"},
                {"c","e"}};
        List<List<String>> f1 = apriori.genF1(apriori.initPass(),0.5);
        List<List<String>> f2 = apriori.genCandidate(f1);
        List<List<String>> f3 = apriori.genCandidate(f2);
    }

    public boolean strIsContain(String[] source,String target){
        boolean res = false;
        int low = 0,high = source.length-1,mid = 0;
        while(high>=low){
            mid = (high+low)>>1;
            if(source[mid].equals(target)){
                res = true;
                break;
            }
            if(source[mid].compareTo(target)<0){
                low = mid+1;
            }
            if(source[mid].compareTo(target)>0){
                high = mid-1;
            }
        }
        return res;
    }

    public int[] initPass(){
        int[] res = new int[strSet.length];
        for(int i = 0; i < strSet.length; i++){
            for(String[] strs : transactionSet)
                if(strIsContain(strs,strSet[i]))
                    res[i]++;
        }
        return res;
    }

    public List<List<String>> genF1(int[] countStr, double minsup){
        List<List<String>> res = new ArrayList<>(countStr.length);
        int index = 0;
        List<String> temp = null;
        for(int i = 0; i < countStr.length; i++){
            if((double)countStr[i]/transactionSet.length>=minsup){
                temp = new ArrayList<>();
                temp.add(strSet[i]);
                res.add(temp);
            }
        }
        return res;
    }

    public boolean isMergeable(List<String> set1,List<String> set2){
        for(int i = 0; i < set1.size()-1; i++){
            if(!set1.get(i).equals(set2.get(i)))
                return false;
        }
        return true;
    }

    public boolean strSetIsContain(List<List<String>> source,List<String> target,int i){
        int j = 0,k = 0;
        boolean flag = false;
        for(List<String> strings : source){
            j = 0;
            k = 0;
            flag = true;
            if (j==i)
                j++;
            while(j<target.size()){
                if(!target.get(j).equals(strings.get(k))){
                    flag = false;
                    break;
                }
                j++;
                if (j==i)
                    j++;
                k++;
            }
            if(flag)
               return true;
        }
        return false;
    }

    public List<List<String>> genCandidate(List<List<String>> lastSet){
        List<List<String>> nextSet = new ArrayList<>(lastSet.size());
        List<String> set1 = null, set2 = null, temp = null;
        //合并只有一个项不同的子集
        for(int i = 0; i < lastSet.size(); i++){
            for(int j = i+1; j < lastSet.size(); j++){
                set1 = lastSet.get(i);
                set2 = lastSet.get(j);
                if(isMergeable(set1,set2)){

                    if(set1.get(set1.size()-1).compareTo(set2.get(set2.size()-1))<0){
                        temp = new ArrayList<>(set1.size()+1);
                        temp.addAll(set1);
                        temp.add(set2.get(set2.size()-1));
                        nextSet.add(temp);
                    }
                    else {
                        temp = new ArrayList<>(set2.size()+1);
                        temp.addAll(set2);
                        temp.add(set1.get(set1.size()-1));
                        nextSet.add(temp);
                    }
                }
            }
        }
        //去除存在子集不在上一阶段集合中的项集
        for(int i = 0; i < nextSet.size();i++){
            for(int j = 0; j < nextSet.get(i).size();j++){
                if(!strSetIsContain(lastSet,nextSet.get(i),j))
                    nextSet.remove(i);
            }
        }
        return nextSet;
    }
}
