/*
 * Name: <your name>
 * EID: <your EID>
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<City> cities;  // this is a list of all Cities, populated by Driver class
    private Heap minHeap;

    // additional constructor fields may be added, but don't delete or modify anything already here
    public Program2(int numCities) {
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }


    /**
     * findMinimumRouteDistance(City start, City dest)
     *
     * @param start - the starting City.
     * @param dest  - the end (destination) City.
     * @return the minimum distance possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(City start, City dest) {
        for(City c : cities){
            c.setMinDist(Integer.MAX_VALUE);
        }
        start.setMinDist(0);
        minHeap.buildHeap(cities);
        while(!minHeap.isEmpty()){
            City v = minHeap.extractMin();
            for(int i=0;i<v.getNeighbors().size();i++){
                City neighbor = v.getNeighbors().get(i);
                int weight = v.getWeights().get(i);
                int minDist = Math.min(neighbor.getMinDist(), weight+v.getMinDist());
                if(minDist != neighbor.getMinDist()){
                    minHeap.changeKey(neighbor, minDist);
                }
            }
        }
        return dest.getMinDist();
    }


    /**
     * findMinimumLength()
     *
     * @return The minimum total optical line length required to connect (span) each city on the given graph.
     * Assume the given graph is always connected.
     */
    public int findMinimumLength() {
        // TODO: implement this function
        int minLength = 0;
        for(int i=0;i<cities.size();i++){
            cities.get(i).setMinDist(Integer.MAX_VALUE);
            cities.get(i).setVisited(false);
        }
        City start = cities.get(0);
        start.setMinDist(0);
        minHeap.buildHeap(cities);
        while(!minHeap.isEmpty()){
            City v = minHeap.extractMin();
            v.setIndex(-1);
            minLength += v.getMinDist();
            for(int i=0;i<v.getNeighbors().size();i++){
                City neighbor = v.getNeighbors().get(i);
                if(!neighbor.isVisited() && neighbor.getIndex() != -1 && v.getWeights().get(i) < neighbor.getMinDist()){
                    // System.out.println("Name: "+neighbor.getName() + " Weight : " + v.getWeights().get(i));
                    // neighbor.setMinDist(v.getWeights().get(i));
                    minHeap.changeKey(neighbor, v.getWeights().get(i));
                }
            }
        }
        return minLength;
    }

    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with distances ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    // used by Driver class to populate each City with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    // used by Driver.java and sets cities to reference an ArrayList of all RestStops
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
