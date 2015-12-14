package painting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class TrafficLight extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cord m_Light=new Cord();
	private boolean RedLight=false;
	
	public TrafficLight(){
		m_Light=new Cord();
	}
	
	public void step(){
		repaint();
	}
	
	public void setLightsColor(boolean red){
		RedLight=red;
	}
	
	public void drawLights(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		int x=m_Light.findX();
		int y=m_Light.findY();
		g2d.drawRect(x, y, 15, 15);
		g2d.fillRect(x, y, 15, 15);
		Ellipse2D.Double circle = new Ellipse2D.Double(x+2, y+2, 10, 10);
		if(RedLight==true){
			g2d.setColor(Color.RED);
		}else{
			g2d.setColor(Color.GREEN);
		}
		g2d.fill(circle);
	}
	
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        drawLights(g);
//        drawBoxes(g);
    }
}
