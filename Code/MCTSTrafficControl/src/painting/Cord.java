package painting;

public class Cord {
	private int x;
	private int y;
	
	public void setXY(int X, int Y){
		x=X;
		y=Y;
	}
	
	public int findX(){
		return x;
	}
	
	public int findY(){
		return y;
	}
	
	public Cord(){
		x=0;
		y=0;
	}

}
