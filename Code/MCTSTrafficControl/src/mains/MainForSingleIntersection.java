package mains;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import listener.SingleIntersectionListener;
import painting.SingleRoad;
import flowmodeling.Intersection;
import flowmodeling.Road;

public class MainForSingleIntersection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Single Free Road CTM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel newPanel = new JPanel(null,true);
        SingleRoad[] roadsetting=new SingleRoad[4];
        for(int i=0;i<roadsetting.length;i++){
        	roadsetting[i]=new SingleRoad();
        }
        JLabel label = new JLabel("Traveled Through: 0");
        roadsetting[0].setBounds(200, 0, 10,200);
        roadsetting[0].setDirection(false);
        roadsetting[1].setBounds(0, 200, 200, 10);
        roadsetting[2].setBounds(210, 200, 200, 10);
        roadsetting[3].setBounds(200, 210, 10, 200);
        roadsetting[3].setDirection(false);
        newPanel.add(roadsetting[0]);
        newPanel.add(roadsetting[1]);
        newPanel.add(roadsetting[2]);
        newPanel.add(roadsetting[3]);
        label.setBounds(0,0,1000,20);
        newPanel.add(label);
        frame.add(newPanel);
        Road[] roads=new Road[4];
        for(int i=0;i<roads.length;i++){
        	roads[i]=new Road(20,12,30);
        }
        roads[0].setOutIntersection();
        roads[1].setOutIntersection();
        roads[2].setInputIntersection();
        roads[3].setInputIntersection();
        roads[0].setFollowingCellCapacity(roads[3].getFirstCellDensity());
        roads[1].setFollowingCellCapacity(roads[2].getFirstCellDensity());
        roads[2].rampOn(roads[1].rampOff());
        roads[3].rampOn(roads[0].rampOff());
        Random rand=new Random();
        roads[0].setRandomSeed(rand.nextInt(30));
        roads[0].setRandomSeed(rand.nextInt(30));
        Intersection intersection=new Intersection(roads[0],roads[1]);
        
        
        SingleIntersectionListener listener = new SingleIntersectionListener(roadsetting,roads,label,intersection);
        Timer timer = new Timer(100, listener);
        timer.start();
        frame.setSize(610, 630);
        frame.setVisible(true);
	}

}
