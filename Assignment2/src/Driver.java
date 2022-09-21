// This Driver file will be replaced by ours during grading
// Do not include this file in your final submission

import java.io.File;
import java.util.*;

public class Driver {
    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    private static boolean testDijkstra; // set to true by -d flag
    private static boolean testMST; // set to true by -m flag
    private static Program2 testProgram2; // instance of your graph
    private static ArrayList<City> cities;

    private static void usage() { // error message
        System.err.println("usage: java Driver [-h] [-d] [-m] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-d\tTest Dijkstra implementation");
        System.err.println("\t-m\tTest MST implementation");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        cities = new ArrayList<City>();
        parseArgs(args);
        parseInputFile(filename);
        testRun();
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }

        filename = "";

        testMST = false;
        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testHeap = true;
            } else if (s.equals("-d")) {
                flagsPresent = true;
                testDijkstra = true;
            } else if (s.equals("-m")) {
                flagsPresent = true;
                testMST = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testHeap = true;
            testDijkstra = true;
            testMST = true;
        }
    }

    public static void parseInputFile(String filename)
            throws Exception {
        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
        testProgram2 = new Program2(numV);
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] weightPairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            City currCity = new City(currNode);
            cities.add(currNode, currCity);
            ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer weightVal = Integer.parseInt(weightPairs[k]);
                currNeighbors.add(new NeighborWeightTuple(neighborVal, weightVal));
            }
        }
        for (int i = 0; i < cities.size(); ++i) {
            City currCity = cities.get(i);
            ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
            for (NeighborWeightTuple neighbor : neighbors) {
                testProgram2.setEdge(currCity, cities.get(neighbor.neighborID), neighbor.weight);
            }
        }

        testProgram2.setAllNodesArray(cities);
    }

    // feel free to alter this method however you wish, we will replace it with our own version during grading
    public static void testRun() {
        if (testHeap) {
            Heap h = new Heap();
            ArrayList<City> c = new ArrayList<City>();
            for(int i=0;i<8;i++){
                City add = new City(i);
                add.setMinDist(i);
                c.add(add);
                Collections.shuffle(c);
            }
            System.out.println(c);
            h.buildHeap(c);
            System.out.println(h);
            City city = new City(6);
            city.setMinDist(2);
            h.insertNode(city);
            System.out.println(h);
            h.changeKey(c.get(1), 3);
            System.out.println(h);
            System.out.println(c);
        }

        if (testDijkstra) {
            // test out Program2.java findMinimumRouteDistance here
            System.out.println("\nGiven graph: ");
            System.out.println(testProgram2);
            System.out.println("Length of shortest path from start to dest: \n" +
                    testProgram2.findMinimumRouteDistance(cities.get(4), cities.get(5)));
        }

        if (testMST) {
            // test out Program2.java findMinimumLength here
            System.out.println("\nGiven graph: ");
            System.out.println(testProgram2);
            System.out.println("Minimum total optical line distance: \n" + testProgram2.findMinimumLength());
        }
    }

    private static class NeighborWeightTuple {
        public Integer neighborID;
        public Integer weight;

        NeighborWeightTuple(Integer neighborID, Integer weight) {
            this.neighborID = neighborID;
            this.weight = weight;
        }
    }
}
