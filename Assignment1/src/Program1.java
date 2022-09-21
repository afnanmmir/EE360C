/*
 * Name: Afnan Mir
 * EID: amm23523
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
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        /* TODO implement this function */
        ArrayList<Integer> internMatching = problem.getInternMatching();
        for(int i=0;i<problem.getInternMatching().size();i++){
            int internCompany = internMatching.get(i);
            if(internCompany == -1){
                for(int j=0;j<internMatching.size();j++){
                    int company = internMatching.get(j);
                    if(internMatching.get(j) != -1){
                    ArrayList<Integer> compPref = problem.getCompanyPreference().get(company);
                        if(compPref.indexOf(i) < compPref.indexOf(j)){
                            return false;
                        }
                    }
                }
            }else{
                ArrayList<Integer> internPreference = problem.getInternPreference().get(i);
                int index = internPreference.indexOf(internCompany);
                for(int j=0;j<index;j++){
                    int company = internPreference.get(j);
                    ArrayList<Integer> compPreference = problem.getCompanyPreference().get(company);
                    for(int k=0;k<internMatching.size();k++){
                        if(internMatching.get(k) == company){
                            if(compPreference.indexOf(i) < compPreference.indexOf(k)){
                                // System.out.println(compPreference.indexOf(i)); //rank of current current intern on company list
                                // System.out.println(compPreference.indexOf(k)); //rank of other intern on company list.
                                // System.out.println(internPreference.get(company));
                                // System.out.println(index);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    public int[][] getInverseMatching(ArrayList<ArrayList<Integer>> preferences){
        int[][] invPref = new int[preferences.size()][preferences.get(0).size()];
        for(int i=0;i<preferences.size();i++){
            ArrayList<Integer> intern = preferences.get(i);
            for(int j = 0;j<intern.size();j++){
                invPref[i][intern.get(j)] = j;
            }
        }
        return invPref;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_companyoptimal(Matching problem) {
        /* TODO implement this function */
        ArrayList<Integer> positions =  new ArrayList<Integer>(problem.getCompanyPositions());
        LinkedList<Integer> q = new LinkedList<Integer>();
        int[] pointers = new int[problem.getCompanyCount()];
        Arrays.fill(pointers, 0);
        ArrayList<Integer> internMatching = new ArrayList<Integer>();
        int[][] inversePreferences = getInverseMatching(problem.getInternPreference());
        for(int i=0;i<problem.getInternCount();i++){
            internMatching.add(-1);
        }
        for(int i=0;i<problem.getCompanyCount();i++){
            if(positions.get(i) > 0){
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int current = q.removeFirst();
            ArrayList<Integer> pref = problem.getCompanyPreference().get(current);
            // int id = current.getId();
            int proposedIntern = pref.get(pointers[current]);
            if(internMatching.get(proposedIntern) < 0){
                internMatching.set(proposedIntern,current);
                positions.set(current, positions.get(current) - 1);
                // System.out.println("Hello");
            }else{
                int proposedRank = inversePreferences[proposedIntern][current];
                int currRank = inversePreferences[proposedIntern][internMatching.get(proposedIntern)];
                if(proposedRank < currRank){
                    positions.set(internMatching.get(proposedIntern), positions.get(internMatching.get(proposedIntern))+1);
                    if(positions.get(internMatching.get(proposedIntern)) == 1){
                        q.add(internMatching.get(proposedIntern));
                    }
                    internMatching.set(proposedIntern,current);
                    positions.set(current, positions.get(current) - 1);
                }
            }
            pointers[current]++;
            if(positions.get(current) > 0){
                q.add(current);
            }
        }
        problem.setInternMatching(internMatching);
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_internoptimal(Matching problem) {
        /* TODO implement this function */
        int[][] inversePreferences = getInverseMatching(problem.getCompanyPreference());
        ArrayList<ArrayList<Integer>> internPreferences = problem.getInternPreference();
        ArrayList<Integer> positions =  new ArrayList<Integer>(problem.getCompanyPositions());
        int[] pointers = new int[problem.getInternCount()];
        Arrays.fill(pointers,0);
        ArrayList<Integer> internMatching = new ArrayList<Integer>();
        for(int i=0;i<problem.getInternCount();i++){
            internMatching.add(-1);
        }
        LinkedList<Integer> queue = new LinkedList<Integer>();
        for(int i=0;i<problem.getInternCount();i++){
            queue.add(i);
        }
        while(!queue.isEmpty()){
            int intern = queue.removeFirst();
            int company = internPreferences.get(intern).get(pointers[intern]);
            if(positions.get(company) > 0){
                internMatching.set(intern, company);
                positions.set(company, positions.get(company)-1);
                // System.out.println(internMatching);
            }else{
                int companyPreferenceCurrent = inversePreferences[company][intern];
                // System.out.println("Current Preference: "+ companyPreferenceCurrent);
                int minPreference = -1;
                int internMinPreference = -1;
                for(int i=0;i<problem.getInternCount();i++){
                    if(internMatching.get(i) == company){
                        int preference = inversePreferences[company][i];
                        // System.out.println("Preference: " + preference);
                        if(preference > minPreference){
                            minPreference = preference;
                            internMinPreference = i;
                        }
                    }
                }
                if(minPreference > companyPreferenceCurrent){
                    // System.out.println("Intern with the worst preference: " + internMinPreference);
                    // System.out.println("Company: " + company);
                    internMatching.set(intern, company);
                    internMatching.set(internMinPreference, -1);
                    if(pointers[internMinPreference] < problem.getCompanyCount()){
                        queue.add(internMinPreference);
                    }
                }
            }
            pointers[intern]++;
            if(internMatching.get(intern) == -1 && pointers[intern] < problem.getCompanyCount()){
                queue.add(intern);
            }
        }
        problem.setInternMatching(internMatching);
        return problem;
    }
}