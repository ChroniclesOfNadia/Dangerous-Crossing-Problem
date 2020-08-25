package dangerousCrossingProblem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;


public class Crossing {

    private LinkedList<Node> dataStructure;

    private int finiteState[];
    private int state[];
    private int costs[];
    private int time;


    private int createdNodesCounter;
    private int visitedNodesCounter;

    public Crossing(int state[], int costs[], int algorithm, int time) {

        dataStructure = new LinkedList<>();
        this.createdNodesCounter = 0;
        this.visitedNodesCounter = 0;

        this.finiteState = new int[state.length];
        Arrays.fill(this.finiteState, 1);
        this.state = state;
        this.costs = costs;
        this.time=time;

        if(algorithm == 1)
            AStar();
        else if(algorithm == 2)
            BFS();
        else if(algorithm == 3)
            DFS();
        else
            System.out.println("ERROR WRONG INPUT.");

    }

    private void BFS() {
        this.dataStructure.add(new Node("root", this, null, this.state, 0, 0));

        while (!dataStructure.isEmpty()) {
            this.visitedNodesCounter++;
            System.out.println("\n" + this.visitedNodesCounter);

            Node node = this.dataStructure.getFirst();

            System.out.println("node " + node.getName() + " depth: " + node.getDepth());
            this.dataStructure.removeFirst();

            if (!node.checkFiniteState()) {
                node.createChildren();

                // new nodes at the end of the queue
                for (Node childNode : node.getChildren())
                    this.dataStructure.addLast(childNode);
            } else {
                printOutput(node, "Breadth First Search Algorithm");
                return;
            }
        }


    }

    private void DFS() {
        this.dataStructure.add(new Node("root", this, null, this.state, 0, 0));

        while (!dataStructure.isEmpty()) {
            this.visitedNodesCounter++;
            System.out.println("\n" + this.visitedNodesCounter);

            Node node = this.dataStructure.getFirst();

            System.out.println("node " + node.getName() + " depth: " + node.getDepth());
            this.dataStructure.removeFirst();

            if (!node.checkFiniteState()) {
                node.createChildren();

                // new nodes at the top of the stack
                for (int i = node.getChildren().size() - 1; i >= 0; i--) {
                    Node childNode = node.getChildren().get(i);
                    this.dataStructure.addFirst(childNode);
                }

            } else {
                printOutput(node, "Depth First Search Algorithm");
                return;
            }
        }

    }
    
    private void AStar() {
    	LinkedList<Node> closedList = new LinkedList<>();
    	
    	this.dataStructure.add(new Node("root", this, null, this.state, 0, 0));

    	this.dataStructure.getFirst().setG(0);
    	


    	while(!dataStructure.isEmpty()) {
    		this.visitedNodesCounter++;
            System.out.println("\n" + this.visitedNodesCounter);

            Node node = this.dataStructure.getFirst();
            closedList.add(node);

            System.out.println("node " + node.getName() + " depth: " + node.getDepth());
            
            this.dataStructure.removeFirst();
            
            if (!node.checkFiniteState()) {
                node.createChildren();

                for (int i = 0; i < node.getChildren().size(); i++) {
                    Node childNode = node.getChildren().get(i);

                    if(! this.dataStructure.contains(childNode)) {
                    	childNode.calculateHeuristic();
                    	int G = childNode.getCost();
                    	childNode.setG(G);
                    	childNode.calculateF();
                    	this.dataStructure.add(childNode);
                    }
                    

                }
  
                for(int j=1; j< this.dataStructure.size()-1; j++) {
                	if(this.dataStructure.get(j).getF()<this.dataStructure.get(0).getF()) {
                		Node temp =this.dataStructure.get(j);
                		this.dataStructure.remove(j);
                		this.dataStructure.addFirst(temp);                		
                	}
                	
                }
                
              

            } else {
                printOutput(node, "A Star Algorithm");
                return;
            }
            

    		
    	}
    	
    	
    	
    	
    	
    	
    	
    }


    private void printOutput(Node node, String algorithName) {
        if(node.getCost()<=time) {
        	System.out.println("\n" + algorithName + " found a solution to the problem!");
	        System.out.println("The final cost is " + node.getCost() + " minutes.");
	        System.out.println("The number nodes created were " + this.createdNodesCounter + ", with " + this.visitedNodesCounter
	                + " of them being visited.\n");
	
	        ArrayList<String> output = new ArrayList<>();
	
	        while (node.getDepth() != 0) {
	            if (node.getName().length() > 1) {
	                String s = "Person " + node.getName().charAt(0) + " and Person " + node.getName().charAt(1)
	                        + " crossed the bridge in " + (node.getCost() - node.getParentNode().getCost()) + " minute(s)";
	
	                output.add(s);
	            } else {
	                String s = "Person " + node.getName() + " returns back in "
	                        + (node.getCost() - node.getParentNode().getCost()) + " minute(s)";
	
	                output.add(s);
	            }
	            node = node.getParentNode();
	        }
	
	        for (int i = output.size() - 1; i >= 0; i--)
	            System.out.println(output.get(i));
        }else {
        	System.out.println(algorithName + " could not find a valid solution to the problem :(");
        	
        }
    }

    public int getCrossingTime(int i) {
        return this.costs[i];
    }

    public int calculateCrossingTime(int i, int j) {
        if (this.costs[i] > this.costs[j])
            return this.costs[i];
        else
            return this.costs[j];
    }
    
    public int getTime() {
    	return this.time;
    }

    public int[] getFiniteState() {
        return this.finiteState;
    }

    public void incrementCreatedNodesCounter() {
        this.createdNodesCounter++;
    }

    public static void main(String[] args) {
        int state[];
        int costs[];

        Scanner sc = new Scanner(System.in);
        System.out.println("Number of people wanting to cross the bridge:");

        int numberOfPeople = sc.nextInt();
        state = new int[numberOfPeople + 1];
        costs = new int[numberOfPeople];

        for (int i = 0; i < costs.length; i++) {
            int n=i+1;
        	System.out.println("Please enter the crossing time of Person " + n + ":");
            costs[i] = sc.nextInt();
        }
        
        System.out.println("Please enter the total crossing time:");
        int maxTime = sc.nextInt();


        System.out.println("Which algorithm you would like to execute: \n1) Astar\n2) BFS\n3) DFS");
        int algorithm = sc.nextInt();
        if(algorithm ==1) {
        	System.out.println("A Star Algorithm it is!!");
        } else if(algorithm ==2) {
        	System.out.println("Breadth First Search Algorithm it is!!");
        }else if(algorithm ==3) {
        	System.out.println("Depth First Search Algorithm it is!!");
        }

        sc.close();
        @SuppressWarnings("unused")
		Crossing cross = new Crossing(state, costs, algorithm, maxTime);
        

    }

}
