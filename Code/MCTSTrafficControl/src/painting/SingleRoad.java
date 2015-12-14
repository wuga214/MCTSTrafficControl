package painting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class SingleRoad extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int CellLength=10;
	private Cord m_Road=new Cord();
	private int[/*Number of Cells*/] CellDensity;
	private int NumCells=20;
	private boolean Horizon=true;
	
	public void setDensity(int[] cellDensity){
		CellDensity=cellDensity;
	}
	
	public void setDirection(boolean h){
		Horizon=h;
	}
	
	public void setStartPosition(int x, int y){
		m_Road.setXY(x, y);
	}
	public void fillBoxes(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(255, 255, 255));
		int x=m_Road.findX();
		int y=m_Road.findY();
		for(int j=0;j<NumCells;j++){
			g2d.drawRect(x, y, CellLength, CellLength);
			if(CellDensity[j]<10){
				g2d.setColor(new Color(255, 255, 255));
				g2d.drawRect(x, y, CellLength, CellLength);
				g2d.setColor(new Color(0, 255, 0));
				g2d.fillRect(x+1, y+1, CellLength-1, CellLength-1);
			}else if(CellDensity[j]<14){
				g2d.setColor(new Color(255, 255, 255));
				g2d.drawRect(x, y, CellLength, CellLength);
				g2d.setColor(new Color(255, 255, 0));
				g2d.fillRect(x+1, y+1, CellLength-1, CellLength-1);
			}else if(CellDensity[j]<25){
				g2d.setColor(new Color(255, 255, 255));
				g2d.drawRect(x, y, CellLength, CellLength);
				g2d.setColor(new Color(255, 150, 0));
				g2d.fillRect(x+1, y+1, CellLength-1, CellLength-1);
			}else{
				g2d.setColor(new Color(255, 255, 255));
				g2d.drawRect(x, y, CellLength, CellLength);
				g2d.setColor(new Color(255, 0, 0));
				g2d.fillRect(x+1, y+1, CellLength-1, CellLength-1);
			}
			if(Horizon==true){
			x=x+CellLength;
			}else{
				y=y+CellLength;
			}
		}	
	}
	
	public SingleRoad(){
		m_Road=new Cord();
	}
	
	public void step(){
		repaint();
	}
	
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
//        drawBoxes(g);
        if(CellDensity!=null){
        	fillBoxes(g);
        }
    }

}
