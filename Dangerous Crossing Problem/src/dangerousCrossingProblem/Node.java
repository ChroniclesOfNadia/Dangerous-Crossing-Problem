package dangerousCrossingProblem;

import java.util.ArrayList;
import java.util.Arrays;
 
public class Node {
    private String name;

    private Crossing cross;

    private Node parentNode;
    private ArrayList<Node> children;
    private int state[];

    private int depth;
    private int cost;
    
    private int h;
    private int g;
    private int f;

    public Node(String name, Crossing cross, Node parentNode, int state[], int depth, int cost) {
        this.name = name;
        
        this.cross = cross;

        this.parentNode = parentNode;
        this.children = null;
        this.state = state;

        this.depth = depth;
        this.cost = cost;

        this.cross.incrementCreatedNodesCounter();
    }

    public boolean checkFiniteState(){
        if(Arrays.equals(this.state, this.cross.getFiniteState())) 
            return true;
        else
            return false;
    }

    public void createChildren() {
        this.children = new ArrayList<>();

        // if the torch is on the left side
        if (state[state.length - 1] == 0)
            
            // create all the possible pairs of indexes
            for (int i = 0; i < state.length - 1; i++)
                for (int j = i + 1; j < state.length - 1; j++){

                    if (this.state[i] == 0 && this.state[j] == 0) {
                        int childState[] = this.state.clone();
                        childState[i] = 1;
                        childState[j] = 1;
                        childState[childState.length - 1] = 1; // torch goes right

                        int crossingTime = cross.calculateCrossingTime(i, j);

                        children.add(new Node(j+""+i, cross, this, childState, this.depth + 1, this.cost + crossingTime));
                        
                        System.out.println("child created:- torch goes right");
                        /*for(int k=0; k<state.length-1;k++) {
                        	System.out.print("state :"+ state[k]+"  ");
                        	System.out.println("child state :"+childState[k]);
                        }*/
                    }
                }

        // if the torch is on the right side
        if (state[state.length - 1] == 1)

            for (int i = 0; i < state.length - 1; i++)

                if (this.state[i] == 1) {
                    int childState[] = this.state.clone();
                    childState[i] = 0;
                    childState[state.length - 1] = 0; // torch goes left

                    int crossingTime = cross.getCrossingTime(i);

                    this.children.add(new Node(i+"", cross, this, childState, this.depth + 1, this.cost + crossingTime));
                    
                    System.out.println("child created:- torch goes left");
                    /*for(int k=0; k<state.length-1;k++) {
                    	System.out.print("state :"+ state[k]+"  ");
                    	System.out.println("child state :"+childState[k]);
                    }*/
                }

    }
    
    public void calculateHeuristic() {
    	h=0;
    	for (int i = 0; i < state.length - 1; i++) {
    		if(state[i]==0){
    			h++;
    		}
    	}
    }

    
    public void calculateF() {
    	f=g+h;
    
    }
    
    public int getF() {
    	return f;
    } 
    
    public int getG() {
    	return g;
    }
    
    
    public void setG(int g) {
    	this.g=g;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }


}