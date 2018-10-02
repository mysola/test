package oj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PAT_Str {

    public static void main(String[] args) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        System.out.println(f(bf.readLine()));
    }

    public static long f(String s){
        long countP = 0;
        long countPA = 0;
        long countPAT = 0;

        for(char ch : s.toCharArray()){
            if(ch=='P')
                countP++;
            if(ch=='A'){
                countPA+=countP;
            }
            if(ch=='T'){
                countPAT+=countPA;
            }
        }
        return countPAT%1000000007;
    }
}
