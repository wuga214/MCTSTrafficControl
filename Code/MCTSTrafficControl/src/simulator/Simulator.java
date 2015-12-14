package simulator;
import java.util.Random;

import uct.TreeNode;
import uct.UpperConfidenceTree;
import flowmodeling.Intersection;
import flowmodeling.Road;


public class Simulator {
	private int[][] OutputCarInCell;
	private boolean[] OutputLights;
	private Road[] roads;
	private Intersection[] intersection;
	private int Reward;
	
	public void runSimulator(int[][] carincell,int[] seeds,boolean[] lights,int action){		
		Reward=0;
		int seedid=0;
		OutputLights=intBoolConvert(action,lights);
		for(int i=0;i<roads.length;i++){
			roads[i].setCurrentState(carincell[i]);
			if(i==0||i==1||i==6||i==7){
				roads[i].setRandomSeed(seeds[seedid]);
				seedid++;
			}
		}
		for(int i=0;i<intersection.length;i++){
			intersection[i].setLights(OutputLights[i]);
		}
		for(int i=0;i<10;i++){
			singleStepSimulation();
		}
		OutputCarInCell=new int[roads.length][];
		for(int i=0;i<roads.length;i++){
			OutputCarInCell[i]=roads[i].currentState();
		}
	}
	
	public boolean[] intBoolConvert(int action,boolean[] orinlights){
		String actions=Integer.toBinaryString(action);
		int actionlength=actions.length();
		boolean[] light=new boolean[4];
		switch(actionlength){
			case 1: actions="000"+actions;
					break;
			case 2: actions="00"+actions;
					break;
			case 3: actions="0"+actions;
					break;
			default:break;
		}
		for(int i=0;i<4;i++){
			if(actions.charAt(i)=='0'){
				light[i]=orinlights[i];
			}else{
				light[i]=!orinlights[i];
			}
		}
		return light;
		
	}
	
	public int[][] getCarInCell(){
		return OutputCarInCell;
	}
	
	public boolean[] getLights(){
		return OutputLights;
	}
	
	public int getReward(){
		return Reward;
	}
	
	public void singleStepSimulation() {
		Connection();
		for(int i=0;i<roads.length;i++){
			roads[i].transByStep();
			if(i==2||i==3||i==8||i==9){
				Reward+=roads[i].getTravelNum();
			}
		}
	}
	
	public void enviInitial(){
		roads=new Road[12];
        for(int i=0;i<roads.length;i++){
        	roads[i]=new Road(20,12,30);
        }
        roads[0].setOutIntersection();
        roads[1].setOutIntersection();
        roads[2].setInputIntersection();
        roads[3].setInputIntersection();
        roads[2].setOutIntersection();
        roads[3].setOutIntersection();
        roads[4].setInputIntersection();
        roads[5].setInputIntersection();
        roads[0].setFollowingCellCapacity(roads[2].getFirstCellDensity());
        roads[1].setFollowingCellCapacity(roads[3].getFirstCellDensity());
        roads[2].setFollowingCellCapacity(roads[4].getFirstCellDensity());
        roads[3].setFollowingCellCapacity(roads[5].getFirstCellDensity());
        roads[2].rampOn(roads[0].rampOff());
        roads[3].rampOn(roads[1].rampOff());
        roads[4].rampOn(roads[2].rampOff());
        roads[5].rampOn(roads[3].rampOff());
        roads[6].setOutIntersection();
        roads[7].setOutIntersection();
        roads[8].setInputIntersection();
        roads[9].setInputIntersection();
        roads[8].setOutIntersection();
        roads[9].setOutIntersection();
        roads[10].setInputIntersection();
        roads[11].setInputIntersection();
        roads[6].setFollowingCellCapacity(roads[8].getFirstCellDensity());
        roads[7].setFollowingCellCapacity(roads[9].getFirstCellDensity());
        roads[8].setFollowingCellCapacity(roads[10].getFirstCellDensity());
        roads[9].setFollowingCellCapacity(roads[11].getFirstCellDensity());
        roads[8].rampOn(roads[6].rampOff());
        roads[9].rampOn(roads[7].rampOff());
        roads[10].rampOn(roads[8].rampOff());
        roads[11].rampOn(roads[9].rampOff());
        Random rand=new Random();
        roads[0].setRandomSeed(rand.nextInt(30));
        roads[1].setRandomSeed(rand.nextInt(30));
        roads[6].setRandomSeed(rand.nextInt(30));
        roads[7].setRandomSeed(rand.nextInt(30));
        intersection=new Intersection[4];
        intersection[0]=new Intersection(roads[0],roads[6]);
        intersection[1]=new Intersection(roads[1],roads[8]);
        intersection[2]=new Intersection(roads[2],roads[7]);
        intersection[3]=new Intersection(roads[3],roads[9]);
	}
	
	public void Connection(){
		roads[0].setFollowingCellCapacity(roads[2].getFirstCellDensity());
		roads[1].setFollowingCellCapacity(roads[3].getFirstCellDensity());
		roads[2].setFollowingCellCapacity(roads[4].getFirstCellDensity());
		roads[3].setFollowingCellCapacity(roads[5].getFirstCellDensity());
		roads[2].rampOn(roads[0].rampOff());
		roads[3].rampOn(roads[1].rampOff());
		roads[4].rampOn(roads[2].rampOff());
		roads[5].rampOn(roads[3].rampOff());
		roads[6].setFollowingCellCapacity(roads[8].getFirstCellDensity());
		roads[7].setFollowingCellCapacity(roads[9].getFirstCellDensity());
		roads[8].setFollowingCellCapacity(roads[10].getFirstCellDensity());
		roads[9].setFollowingCellCapacity(roads[11].getFirstCellDensity());
		roads[8].rampOn(roads[6].rampOff());
		roads[9].rampOn(roads[7].rampOff());
		roads[10].rampOn(roads[8].rampOff());
		roads[11].rampOn(roads[9].rampOff());
	}
	
	public Simulator(){
		enviInitial();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulator simu=new Simulator();
		int[][] carincell=new int[12][];
		System.out.println("\\\\Road Setting..Number of cars in each cell.");
		System.out.println("\\\\-------------------------------------");
		for(int i=0;i<simu.roads.length;i++){
			simu.roads[i].randInit();
			carincell[i]=simu.roads[i].currentState();
			String InitialSetting="\\\\Road["+i+"]:";
			for(int j=0;j<simu.roads[i].currentState().length;j++){
				InitialSetting+=simu.roads[i].currentState()[j]+",";
			}
			System.out.println(InitialSetting.substring(0, InitialSetting.length()-1));
		}
		int[] Seeds=new int[]{5,25,16,7};
		boolean[] Lights=new boolean[]{false,false,false,false};
		System.out.println("\\\\-------------------------------------");
		for(int action=0;action<16;action++){
			simu.runSimulator(carincell, Seeds, Lights, action);
			System.out.println("\\\\Rewards of action "+action+" is: "+simu.Reward);
//			System.out.println("lights setting of this action:"+simu.getLights()[0]+" "+simu.getLights()[1]+" "+simu.getLights()[2]+" "+simu.getLights()[3]+" ");
		}
		TreeNode root=new TreeNode();
		root.setStateFeatures(carincell,Lights, Seeds, 0);
		UpperConfidenceTree uct=new UpperConfidenceTree();
		int act=uct.uctSearch(root);
		System.out.println("\\\\-------------------------------------");
		System.out.println("\\\\The action recommendated by UCT:"+act);
	}

}
