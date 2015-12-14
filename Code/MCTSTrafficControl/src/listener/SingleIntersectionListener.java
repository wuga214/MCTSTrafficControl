package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import painting.SingleRoad;
import flowmodeling.Intersection;
import flowmodeling.Road;

public class SingleIntersectionListener implements ActionListener{
	private SingleRoad[] Platfrom;
	private Road[] RoadModel;
	private JLabel Label;
	private Intersection Cross;
	private int stepcounter=0;

	public SingleIntersectionListener(SingleRoad[] roadsetting,Road[] roadmodel,JLabel label,Intersection intersection){
		Platfrom=roadsetting;
		RoadModel=roadmodel;
		Label=label;
		Cross=intersection;
	}
	
	public void fixTimeControl(){
		stepcounter++;
		if(stepcounter%10==0){
			Cross.changePhrase();
			stepcounter=0;
		}
	}
	
	public void Connection(){
		RoadModel[0].setFollowingCellCapacity(RoadModel[3].getFirstCellDensity());
		RoadModel[1].setFollowingCellCapacity(RoadModel[2].getFirstCellDensity());
		RoadModel[2].rampOn(RoadModel[1].rampOff());
		RoadModel[3].rampOn(RoadModel[0].rampOff());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int counter=0;
		Connection();
		fixTimeControl();
		for(int i=0;i<RoadModel.length;i++){
		RoadModel[i].transByStep();
		counter+=RoadModel[i].getTravelNum();
		Platfrom[i].setDensity(RoadModel[i].currentState());
		Platfrom[i].step();
		}
		Label.setText("Traveled Through: "+counter);
	}
}
