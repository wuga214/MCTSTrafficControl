package mains;

import java.util.Random;

import uct.TreeNode;
import uct.UpperConfidenceTree;
import flowmodeling.Intersection;
import flowmodeling.Road;

public class MainForSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] carincell=new int[12][];
		int[] seeds=new int[4];
		boolean[] lights=new boolean[4];
		Road[] roads=new Road[12];
        for(int i=0;i<roads.length;i++){
        	roads[i]=new Road(20,12,30);
        	carincell[i]=roads[i].currentState();
        }
        Random rand=new Random();
        roads[0].setRandomSeed(rand.nextInt(30));
        roads[1].setRandomSeed(rand.nextInt(30));
        roads[6].setRandomSeed(rand.nextInt(30));
        roads[7].setRandomSeed(rand.nextInt(30));
        seeds[0]=roads[0].getRandomSeed();
        seeds[1]=roads[1].getRandomSeed();
        seeds[2]=roads[6].getRandomSeed();
        seeds[3]=roads[7].getRandomSeed();
        Intersection[] intersection=new Intersection[4];
        intersection[0]=new Intersection(roads[0],roads[6]);
        intersection[1]=new Intersection(roads[1],roads[8]);
        intersection[2]=new Intersection(roads[2],roads[7]);
        intersection[3]=new Intersection(roads[3],roads[9]);
        for(int i=0;i<lights.length;i++){
        	lights[i]=intersection[i].getLights();
        }
//        int action=15;
//        Simulator sim=new Simulator();
//        sim.runSimulator(carincell,seeds,lights,action);
//        System.out.println("out through:"+sim.getReward());
        TreeNode root=new TreeNode();
        root.setStateFeatures(carincell, lights, seeds, 0);
        UpperConfidenceTree uct=new UpperConfidenceTree();
        uct.uctSearch(root);
        System.out.println("finished");
	}

}
