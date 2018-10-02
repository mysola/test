import java.util.LinkedList;
import java.util.List;

class Test {
    public static void main(String[] args) throws Exception {

        List<List<Integer>> result = new LinkedList<>();
        List<Integer> temp = new LinkedList<>();
        int[] candidates = {2,3,6,7};
        int target = 7;
        res(candidates,candidates.length-1,target,result,temp);
        System.out.println(1);
    }


    public static void res(int[] candidates, int index, int rest, List<List<Integer>> result, List<Integer> temp){
        if(rest == 0){
            List<Integer> t = new LinkedList<>();
            t.addAll(temp);
            result.add(t);
            return ;
        }
        for(int i = index; i >= 0; i--){
            if(rest>=candidates[i]){
                temp.add(candidates[i]);
                res(candidates,i,rest - candidates[i],result,temp);
                temp.remove((Integer)candidates[i]);
            }
        }
    }
}