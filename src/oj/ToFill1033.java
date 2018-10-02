package oj;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToFill1033 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream("e:\\test.txt")));
        String[] line = bf.readLine().split(" ");
        capacity = Double.valueOf(line[0]);
        sumDistance = Double.valueOf(line[1]);
        speed = Double.valueOf(line[2]);
        sumOfStation = Integer.valueOf(line[3]);
        farthestDistanceOfTank = capacity*speed;
        station = new double[sumOfStation][2];
        for (int i=0;i<sumOfStation;i++){
            line = bf.readLine().split(" ");
            station[i][0] = Double.valueOf(line[0]);
            station[i][1] = Double.valueOf(line[1]);
        }
        System.out.println(f());
    }

    private static double capacity;
    private static double sumDistance;
    private static double speed;
    private static double[][] station;

    private static int sumOfStation;

    private static double farthestDistanceOfTank;

    private static double price;

    public static double f(){
        int curStation = 0,temp = 0,minStation = 0;
        double restGas = 0.0,curDisance = 0.0;
        double minPrice = 0.0;
        while(curStation<sumOfStation - 1){
            temp = -1;
            minPrice = 10000000.0;
            for(int i = curStation+1; i < sumOfStation&&
                    station[i][1]-station[curStation][1]<=farthestDistanceOfTank; i++){
                if(station[i][0]<=station[curStation][0]){
                    temp = i;
                    break;
                }
                else {
                    if(station[i][0]<minPrice){
                        minPrice = station[i][0];
                        minStation = i;
                    }
                }
            }
            //找到比当前站便宜
            if(temp!=-1){
                price += (station[temp][1] - station[curStation][1] - restGas*speed)/speed*station[curStation][0];
                curStation = temp;
                restGas = 0;
            }
            else if(minStation < sumOfStation - 1){
                price += (capacity-restGas)*station[curStation][0];
                restGas = capacity - (station[minStation][1] - station[curStation][1])/speed;
                curStation = minStation;
            }
            else {
                price += (station[minStation][1] - station[curStation][1] - restGas*speed)/speed*station[curStation][0];
                restGas = 0;
                curStation = minStation;
            }
        }
        return price;
    }
}
