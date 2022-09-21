/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "response" field set to the optimal response time
     */
    @Override
    public TownPlan OptimalResponse(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Float> housePos = new ArrayList<Float>(town.getPositionHouses());
        float[][] dpTable = new float[n][k+1];
        fillBaseCases(dpTable, housePos);
        for(int i = 0;i < dpTable.length; i++){
            for(int j = 1;j<=Math.min(k,i+1);j++){
                if(j != 1){
                    float min = Float.MAX_VALUE;
                    for(int l=0;l<i;l++){
                        float kMinus1Distance = dpTable[l][j-1];
                        float kDistance = (housePos.get(i) + housePos.get(l+1))/2 - housePos.get(l+1);
                        float r = Math.max(kMinus1Distance, kDistance);
                        min = Math.min(min,r);
                    }
                    dpTable[i][j] = min;
                } 
            }
        }
        TownPlan ret = new TownPlan(town);

        ret.setResponse(dpTable[n-1][k]);
        return ret;
    }


    private void fillBaseCases(float[][] table, ArrayList<Float> housePos){
        for(int i=0;i<table[0].length;i++){
            table[i][0] = Float.MAX_VALUE;
        }
        for(int i=1;i<table[0].length;i++){
            table[0][i] = (float)0.0;
        }
        for(int i=0;i<table.length;i++){
            if(i == 0){
                table[i][1] = (float)0.0;
            }else{
                float dist = (housePos.get(i) + housePos.get(0))/2 - housePos.get(0);
                table[i][1] = dist;
            }
        }

    }

    /**
     * Determines the solution of the set of food station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "position_food_stations" field set to the optimal food station positions
     */
    @Override
    public TownPlan OptimalPosFoodStations(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Float> housePos = new ArrayList<Float>(town.getPositionHouses());
        float[][] dpTable = new float[n][k+1];
        PositionObj[][] positionsTable = new PositionObj[n][k+1];
        fillBaseCases(dpTable, housePos);
        for(int i = 0;i < dpTable.length; i++){
            for(int j = 1;j<=Math.min(k,i+1);j++){
                if(j != 1){
                    float min = Float.MAX_VALUE;
                    int minSubProblem = -1;
                    float minPosition = Float.MAX_VALUE;
                    for(int l=0;l<i;l++){
                        float kMinus1Distance = dpTable[l][j-1];
                        float kDistance = (housePos.get(i) + housePos.get(l+1))/2 - housePos.get(l+1);
                        float r = Math.max(kMinus1Distance, kDistance);
                        // min = Math.min(min,r);
                        if(r < min){
                            min = r;
                            minSubProblem = l;
                            minPosition = (housePos.get(i) + housePos.get(l+1))/2;
                        }
                    }
                    dpTable[i][j] = min;
                    positionsTable[i][j] = new PositionObj(minPosition,minSubProblem);
                }else{
                    dpTable[i][j] = (housePos.get(i) + housePos.get(0))/2 - housePos.get(0);
                    positionsTable[i][j] = new PositionObj((housePos.get(i) + housePos.get(0))/2, -1);

                } 
            }
        }
        ArrayList<Float> pos = new ArrayList<Float>();
        for(int i=0;i<k;i++){
            pos.add((float)0);
        }
        int nextSubProblem = n - 1;
        for(int i = k; i>0; i--){
            pos.set(i-1,positionsTable[nextSubProblem][i].getPosition());
            nextSubProblem = positionsTable[nextSubProblem][i].getPreviousProblem();
        }
        TownPlan ret = new TownPlan(town);
        ret.setResponse(dpTable[n-1][k]);
        ret.setPositionFoodStations(pos);
        return ret;
    }
}
