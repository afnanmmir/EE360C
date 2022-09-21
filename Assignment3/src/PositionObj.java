public class PositionObj {
    private float position;
    private int previousSubproblem;

    public PositionObj(float pos, int prev){
        position = pos;
        previousSubproblem = prev;
    }

    public float getPosition(){
        return position;
    }

    public int getPreviousProblem(){
        return previousSubproblem;
    }

    public void setPosition(float f){
        position = f;
    }

    public void setPreviousProblem(int prev){
        previousSubproblem = prev;
    }

    public String toString(){
        return "Position : "+ position + " Previous Problem: " + previousSubproblem;
    }
}
