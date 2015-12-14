package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import flowmodeling.Road;
import painting.SingleRoad;


public class SingleRoadListener implements ActionListener{

	private SingleRoad Platfrom;
	private Road RoadModel;
	private JLabel Label;

	public SingleRoadListener(SingleRoad roadsetting,Road roadmodel,JLabel label){
		Platfrom=roadsetting;
		RoadModel=roadmodel;
		Label=label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		RoadModel.transByStep();
		Label.setText("Traveled Through: "+RoadModel.getTravelNum());
		Platfrom.setDensity(RoadModel.currentState());
		Platfrom.step();
	}
}
