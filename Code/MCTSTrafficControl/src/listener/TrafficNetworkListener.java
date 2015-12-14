package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import painting.SingleRoad;
import painting.TrafficLight;
import uct.TreeNode;
import uct.UpperConfidenceTree;
import flowmodeling.Intersection;
import flowmodeling.Road;

public class TrafficNetworkListener implements ActionListener{
	private SingleRoad[] Platfrom;
	private Road[] RoadModel;
	private JLabel Label;
	private Intersection[] Cross;
	private int stepcounter=0;
	private TrafficLight[] Lights;
	private int counter=0;

	public TrafficNetworkListener(SingleRoad[] roadsetting,Road[] roadmodel,JLabel label,Intersection intersection[],TrafficLight[] lightsetting){
		Platfrom=roadsetting;
		RoadModel=roadmodel;
		Label=label;
		Cross=intersection;
		Lights=lightsetting;
	}
	
	public void fixTimeControl(){//if we use MCTS we should output the current state information every 10 seconds and read in some lights setting at same time.
		if(stepcounter%10==0){
			for(int i=0;i<Cross.length;i++){
				Cross[i].changePhrase();
				stepcounter=0;
			}
			System.out.print(counter+",");
		}
		stepcounter++;
	}
	
	public void UCTControl(){
		if(stepcounter%10==0){
			int action=ListenerPipe.Action;
			String actions=Integer.toBinaryString(action);
			int actionlength=actions.length();
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
				}else{
					Cross[i].changePhrase();
				}
			}
			int[][] carincell=new int[RoadModel.length][];
			int[] seed=new int[4];
			int seedid=0;
			for(int i=0;i<carincell.length;i++){
				carincell[i]=RoadModel[i].currentState();
				if(i==0||i==1||i==6||i==7){
					seed[seedid]=RoadModel[i].getRandomSeed();
					seedid++;
				}
			}
			ListenerPipe.Carincell=carincell;
			ListenerPipe.Seeds=seed;
			boolean[] lights=new boolean[Cross.length];
			for(int i=0;i<lights.length;i++){
				lights[i]=Cross[i].getLights();
			}
			ListenerPipe.Lights=lights;
		
			TreeNode root=new TreeNode();
			root.setStateFeatures(ListenerPipe.Carincell, ListenerPipe.Lights, ListenerPipe.Seeds, 0);
			UpperConfidenceTree uct=new UpperConfidenceTree();
			ListenerPipe.Action=uct.uctSearch(root);
			System.out.print(counter+",");
		}
		stepcounter++;
	}
	
	public void lightColorUpdate(){
//		if(stepcounter%10==0){
			for(int i=0;i<12;i++){
			Lights[i].setLightsColor(RoadModel[i].getLight());
			}
//		}
	}
	
	public void Connection(){
		RoadModel[0].setFollowingCellCapacity(RoadModel[2].getFirstCellDensity());
		RoadModel[1].setFollowingCellCapacity(RoadModel[3].getFirstCellDensity());
		RoadModel[2].setFollowingCellCapacity(RoadModel[4].getFirstCellDensity());
		RoadModel[3].setFollowingCellCapacity(RoadModel[5].getFirstCellDensity());
		RoadModel[2].rampOn(RoadModel[0].rampOff());
		RoadModel[3].rampOn(RoadModel[1].rampOff());
		RoadModel[4].rampOn(RoadModel[2].rampOff());
		RoadModel[5].rampOn(RoadModel[3].rampOff());
		RoadModel[6].setFollowingCellCapacity(RoadModel[8].getFirstCellDensity());
		RoadModel[7].setFollowingCellCapacity(RoadModel[9].getFirstCellDensity());
		RoadModel[8].setFollowingCellCapacity(RoadModel[10].getFirstCellDensity());
		RoadModel[9].setFollowingCellCapacity(RoadModel[11].getFirstCellDensity());
		RoadModel[8].rampOn(RoadModel[6].rampOff());
		RoadModel[9].rampOn(RoadModel[7].rampOff());
		RoadModel[10].rampOn(RoadModel[8].rampOff());
		RoadModel[11].rampOn(RoadModel[9].rampOff());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Connection();
//		fixTimeControl();
		UCTControl();
		lightColorUpdate();
		for(int i=0;i<RoadModel.length;i++){
		RoadModel[i].transByStep();
		if(i==2||i==3||i==8||i==9){
			counter+=RoadModel[i].getTravelNum();
		}
		Platfrom[i].setDensity(RoadModel[i].currentState());
		Platfrom[i].step();
		Lights[i].step();
		}
		Label.setText("Traveled Through: "+counter);
	}
}