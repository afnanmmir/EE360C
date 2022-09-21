/*
 * Name: Afnan Mir
 * EID: amm23523
 */

// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.*;

public class City {
    private int minDist;
    private int name;
    private ArrayList<City> neighbors;
    private ArrayList<Integer> weights;
    private int index;
    private boolean minSpanTreeVisited;

    public City(int x) {
        name = x;
        minDist = Integer.MAX_VALUE;
        neighbors = new ArrayList<City>();
        weights = new ArrayList<Integer>();
    }

    public void setNeighborAndWeight(City n, Integer w) {
        neighbors.add(n);
        weights.add(w);
    }

    public ArrayList<City> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Integer> getWeights() {
        return weights;
    }

    public int getMinDist() { return minDist; }

    public void setMinDist(int x) {
        minDist = x;
    }

    public void resetMinDist() {
        minDist = Integer.MAX_VALUE;
    }

    public int getName() {
        return name;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int ind){
        index = ind;
    }

    public boolean isVisited(){
        return minSpanTreeVisited;
    }

    public void setVisited(boolean visited){
        minSpanTreeVisited = visited;
    }

    public String toString(){
        return "{" + name+", " + index +"}";
    }
}
