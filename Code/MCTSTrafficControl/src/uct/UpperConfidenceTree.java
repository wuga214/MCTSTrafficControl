package uct;

import java.util.Random;

import simulator.Simulator;

/**
 * @author Wuga
 * Current problems: haven't add new treenodes on the tree!! no connections withnodes
 */
public class UpperConfidenceTree {
	private double Parameter=1;
	private Simulator Simulation;
	private double TrajectoryReward;
	private static int TotalDepth=5;
	private int Depth;
	private boolean NewData=false;
	
	public UpperConfidenceTree(){
		Simulation=new Simulator();
		
	}
	
	public int uctSearch(TreeNode root){
		int action;
		long start=System.currentTimeMillis();
		long end=start+1*300; 
		Simulation.runSimulator(root.getRoadCondition(),root.getRandomSeeds(),root.getLights(),0);
		root.setStateFeatures(Simulation.getCarInCell(),Simulation.getLights(),root.getRandomSeeds(),0);
		while (System.currentTimeMillis()<end)
		{
			Depth=0;
			TrajectoryReward=0;
		    TreeNode node=treePolicy(root);
		    double value=defaultPolicy(node);
		    backPropagation(node,value);
		}
		action=bestChild(root,0).getParentAction();
		return action;
	}
	
	public TreeNode treePolicy(TreeNode node){
		TreeNode currentnode=node;
		int depth=0;
		while(depth<3){
			boolean fullyexpended=true;
			for(int i=0;i<currentnode.getChildNodes().length;i++){
				if(currentnode.getChildNodes()[i][0]==null){
					fullyexpended=false;
					break;
				}
			}
			if(fullyexpended==false){
				currentnode=expendTree(currentnode);
				depth++;
				Depth++;
				break;
			}else{
				currentnode=bestChild(currentnode, Parameter);// it is better to return best action and call other function to get node
				depth++;
				Depth++;
				if(NewData==true){
					break;
				}
			}
		}
		return currentnode;
	}
	
	public TreeNode expendTree(TreeNode node){
		int action=0;
		for(int i=0;i<node.getChildNodes().length;i++){
			if(node.getChildNodes()[i][0]==null){
				action=i;
				break;
			}
		}
		TreeNode newnode=new TreeNode(node,action);
		Simulation.runSimulator(node.getRoadCondition(),node.getRandomSeeds(),node.getLights(),action);
		newnode.setStateFeatures(Simulation.getCarInCell(),Simulation.getLights(),node.getRandomSeeds(),Simulation.getReward());
		TrajectoryReward+=Simulation.getReward();
		node.addChildNode(newnode, action);
		return newnode;
	}
	
	public TreeNode bestChild(TreeNode node, double para){
		NewData=false;
		double[] qvalues=new double[node.getChildNodes().length];
		int[] childvisits=new int[qvalues.length];
		int samples=node.getChildNodes()[0].length;
		for(int i=0;i<qvalues.length;i++){			
			for(int j=0;j<samples;j++){
				if(node.getChildNodes()[i][j]!=null){
					qvalues[i]+=node.getChildNodes()[i][j].getValue();//!
					childvisits[i]+=node.getChildNodes()[i][j].getVisits();
				}
			}
		}
		int bestaction=0;
		double max=Double.MIN_VALUE;
		double ucb;
		for(int i=0;i<qvalues.length;i++){
			ucb=qvalues[i]/childvisits[i]+para*Math.sqrt(2*Math.log(node.getVisits())/childvisits[i]);
			if(max<ucb){
				max=ucb;
				bestaction=i;
			}
		}
		int emptysampleslot=-1;
		for(int i=0;i<node.getChildNodes()[bestaction].length;i++){
			if(node.getChildNodes()[bestaction][i]==null){
				emptysampleslot=i;
				break;
			}
		}
		if(emptysampleslot!=-1){
			TreeNode newnode=new TreeNode(node,bestaction);
			Simulation.runSimulator(node.getRoadCondition(),node.getRandomSeeds(),node.getLights(),bestaction);
			newnode.setStateFeatures(Simulation.getCarInCell(),Simulation.getLights(),node.getRandomSeeds(),Simulation.getReward());
			TrajectoryReward+=Simulation.getReward();
			node.addChildNode(newnode, bestaction);
			NewData=true;
			return newnode;
		}else{
			Random rand=new Random();
			double x=rand.nextDouble();
			double cumulative=0;
			int bestsample=0;
			for(int i=0;i<childvisits.length;i++){
				cumulative+=0.2;
				if(x<cumulative){
					bestsample=i;
					break;
				}
			}
			TrajectoryReward+=node.getChildNodes()[bestaction][bestsample].getReward();
			return node.getChildNodes()[bestaction][bestsample];
		}
	}
	
	public double defaultPolicy(TreeNode node){
		int[][] roadcondition=node.getRoadCondition();
		int[] seed=node.getRandomSeeds();
		boolean[] lights=node.getLights();
		double value=0;
		Random rand=new Random();
		while(TotalDepth-Depth!=0){
			Simulation.runSimulator(roadcondition,seed,lights,rand.nextInt(16));
			TrajectoryReward+=Simulation.getReward();
			roadcondition=Simulation.getCarInCell();
			lights=Simulation.getLights();
			Depth++;
		}
		value=TrajectoryReward;
		return value;
	}
	
	public void backPropagation(TreeNode node, double value){
		TreeNode temp=node;
		while(temp!=null){
			temp.visit();
			temp.setValue((temp.getValue()*(temp.getVisits()-1)+value)/temp.getVisits());
			temp=temp.getParent();
		}
	}
}
