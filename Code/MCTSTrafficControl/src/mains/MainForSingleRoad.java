package mains;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import flowmodeling.Road;
import painting.SingleRoad;
import listener.SingleRoadListener;


public class MainForSingleRoad{

    public static void main(String[] args) {

        JFrame frame = new JFrame("Single Free Road CTM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new FlowLayout());
//        frame.setLayout(new BorderLayout());
        JPanel newPanel = new JPanel(null,true);
        SingleRoad roadsetting=new SingleRoad();
        JLabel label = new JLabel("Traveled Through: 0");
        roadsetting.setBounds(10, 100, 300,10);
        newPanel.add(roadsetting);
        label.setBounds(250,10,1000,10);
        newPanel.add(label);
        frame.add(newPanel);
        Road x=new Road(20,12,30);
        
        SingleRoadListener listener = new SingleRoadListener(roadsetting,x,label);

        Timer timer = new Timer(1000/4, listener);
        timer.start();

        frame.setSize(575, 200);
        frame.setVisible(true);
    }
}