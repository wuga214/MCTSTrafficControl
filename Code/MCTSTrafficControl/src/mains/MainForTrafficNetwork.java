package mains;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import listener.TrafficNetworkListener;
import painting.SingleRoad;
import painting.TrafficLight;
import flowmodeling.Intersection;
import flowmodeling.Road;

public class MainForTrafficNetwork {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Single Free Road CTM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel newPanel = new JPanel(null,true);
        SingleRoad[] roadsetting=new SingleRoad[12];
        TrafficLight[] lightsetting=new TrafficLight[12];
        for(int i=0;i<roadsetting.length;i++){
        	roadsetting[i]=new SingleRoad();
        	lightsetting[i]=new TrafficLight();
        }
        JLabel label = new JLabel("Traveled Through: 0");
        int length=200;
        int width=10;
        int x=200;
        int y=0;
        for(int k=0;k<6;k=k+2){
        	 roadsetting[k].setBounds(x, y, width,length);
        	 roadsetting[k].setDirection(false);
        	 roadsetting[k+1].setBounds(x+210, y, width,length);
        	 roadsetting[k+1].setDirection(false);
        	 if(k!=4){
        		 lightsetting[k].setBounds(x-15, y+170, 15, 15);
        		 lightsetting[k+1].setBounds(x+195, y+170, 15, 15);
        	 }
        	 y=y+210;
        }
        x=0;
        y=200;
        for(int k=0;k<6;k=k+2){
       	 roadsetting[k+6].setBounds(x, y, length,width);
       	 roadsetting[k+7].setBounds(x, y+210, length,width);
       	if(k!=4){
   		 lightsetting[k+6].setBounds(x+170, y+15, 15, 15);
   		 lightsetting[k+7].setBounds(x+170, y+225, 15, 15);
   	 }
       	 x=x+210;
       }
        for(int i=0;i<roadsetting.length;i++){
        	newPanel.add(roadsetting[i]);
        	newPanel.add(lightsetting[i]);
        }
        label.setBounds(0,0,1000,20);
        newPanel.add(label);
        frame.add(newPanel);
        Road[] roads=new Road[12];
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
//        Random rand=new Random();// we need to store this rand seed, so that we can compare different traffic control performance
//        roads[0].setRandomSeed(rand.nextInt(30));
//        roads[1].setRandomSeed(rand.nextInt(30));
//        roads[6].setRandomSeed(rand.nextInt(30));
//        roads[7].setRandomSeed(rand.nextInt(30));
        roads[0].setRandomSeed(1);
        roads[1].setRandomSeed(23);
        roads[6].setRandomSeed(1);
        roads[7].setRandomSeed(3);
        Intersection[] intersection=new Intersection[4];
        intersection[0]=new Intersection(roads[0],roads[6]);
        intersection[1]=new Intersection(roads[1],roads[8]);
        intersection[2]=new Intersection(roads[2],roads[7]);
        intersection[3]=new Intersection(roads[3],roads[9]);
		for(int i=0;i<12;i++){
			lightsetting[i].setLightsColor(roads[i].getLight());
		}
        
        TrafficNetworkListener listener = new TrafficNetworkListener(roadsetting,roads,label,intersection,lightsetting);
        Timer timer = new Timer(300, listener);
        timer.start();
        frame.setSize(610, 630);
        frame.setVisible(true);
	}

}