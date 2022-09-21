/*
 * Name: Afnan Mir
 * EID: amm23523
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Heap {
    private ArrayList<City> minHeap;
    private int levels;

    public Heap() {
        minHeap = new ArrayList<City>();
        levels = 0;
    }

    private int getParentIndex(int index){
        return (index - 1)/2;
    }

    // private int getChildIndex(int index){
    //     return 2*index + 1;
    // }

    private void heapifyUp(int index){
        int currIndex = index;
        while(currIndex > 0){
            int parentInd = getParentIndex(currIndex);
            if(minHeap.get(parentInd).getMinDist() > minHeap.get(currIndex).getMinDist()){
                City temp = minHeap.get(currIndex);
                minHeap.set(currIndex, minHeap.get(parentInd));
                minHeap.get(currIndex).setIndex(currIndex);
                minHeap.set(parentInd,temp);
                temp.setIndex(parentInd);
            }else if(minHeap.get(parentInd).getMinDist() == minHeap.get(currIndex).getMinDist() && minHeap.get(parentInd).getName() > minHeap.get(currIndex).getName()){
                City temp = minHeap.get(currIndex);
                minHeap.set(currIndex, minHeap.get(parentInd));
                minHeap.get(currIndex).setIndex(currIndex);
                minHeap.set(parentInd,temp);
                temp.setIndex(parentInd);
            }else{
                break;
            }
            currIndex = parentInd;
        }
    }

    private void heapifyDown(int index){
        int currIndex = index;
        while(currIndex < minHeap.size()/2){
            int child1Index = ((currIndex+1)*2) - 1;
            int child2Index = 2*(currIndex+1);
            int minIndex = 0;
            if(child1Index >= minHeap.size() && child2Index >= minHeap.size()){
                break;
            } else if(child2Index >= minHeap.size()){
                minIndex = child1Index;
            }else{
                if(minHeap.get(child1Index).getMinDist() <= minHeap.get(child2Index).getMinDist()){
                    minIndex = child1Index;
                }else{
                    minIndex = child2Index;
                }
            }
            if(minHeap.get(currIndex).getMinDist() > minHeap.get(minIndex).getMinDist()){
                City temp = minHeap.get(currIndex);
                minHeap.set(currIndex, minHeap.get(minIndex));
                minHeap.get(currIndex).setIndex(currIndex);
                minHeap.set(minIndex,temp);
                minHeap.get(minIndex).setIndex(minIndex);
            }else if(minHeap.get(currIndex).getMinDist() == minHeap.get(minIndex).getMinDist() && minHeap.get(currIndex).getName() > minHeap.get(minIndex).getName()){
                City temp = minHeap.get(currIndex);
                minHeap.set(currIndex, minHeap.get(minIndex));
                minHeap.get(currIndex).setIndex(currIndex);
                minHeap.set(minIndex,temp);
                minHeap.get(minIndex).setIndex(minIndex);
            }else{
                break;
            }
            currIndex = minIndex;
        }
    }

    private int getNumLevels(){
        int levels = 0;
        int size = 1;
        while(size <= minHeap.size()){
            levels++;
            size = (int)Math.pow(2, levels) - 1;
        }
        return levels-1;
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        // TODO: implement this method
        minHeap.clear();
        for(int i=0;i<cities.size();i++){
            minHeap.add(cities.get(i));
            cities.get(i).setIndex(i);
        }
        int level = getNumLevels() - 1;
        // System.out.println(level);
        int start = (int)Math.pow(2, level);
        for(int i = start; i >= 0; i--){
            heapifyDown(i);
        }
        // System.out.println("Hello");
        // System.out.println(this);
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        // TODO: implement this method
        minHeap.add(in);
        int index = minHeap.size()-1;
        in.setIndex(index);
        heapifyUp(minHeap.size() - 1);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public City findMin() {
        // TODO: implement this method
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
        City minCity = minHeap.get(0);
        minHeap.set(0, minHeap.get(minHeap.size()-1));
        minHeap.remove(minHeap.size()-1);
        if(!minHeap.isEmpty()){
            minHeap.get(0).setIndex(0);
            heapifyDown(0);
        }
        // System.out.println(this);
        return minCity;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
        minHeap.set(index, minHeap.get(minHeap.size()-1));
        minHeap.remove(minHeap.size()-1);
        heapifyDown(index);
    }

    /**
     * changeKey(City r, int newDist)
     * Changes minDist of City s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the City in the heap that needs to be updated.
     * @param newDist - the new cost of City r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(City r, int newDist) {
        int index = r.getIndex();
        int prevDistance = r.getMinDist();
        minHeap.get(index).setMinDist(newDist);
        if(newDist > prevDistance){
            heapifyDown(index);
        }else{
            heapifyUp(index);
        }
    }

    public boolean isEmpty(){
        return minHeap.isEmpty();
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
