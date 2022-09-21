import java.util.*;

public class Company {
    private int numPositions;
    private List<Integer> preferenceList;
    private int id;
    public Company(List<Integer> pl, int i){
        // numPositions = np; 
        preferenceList = pl;
        id = i;
    }

    public List<Integer> getPreferenceList(){
        return preferenceList;
    }

    public int getNumPositions(){
        return numPositions;
    }

    public void setNumPositions(int pos){
        numPositions = pos;
    }

    public int getId(){
        return id;
    }
}
