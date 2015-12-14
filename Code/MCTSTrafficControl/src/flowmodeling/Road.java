package flowmodeling;

import java.util.Random;

public class Road {
	private int[] CarInCell;
	private int LargestDensity;
	private int LargestFlow;
	private static int Velocity=1;
	private double Wavespeed;
	private int NumCell;
	private int TravelThrou;
	private boolean InputIntersection;
	private boolean OutputIntersection;
	private int NextRoadFirstCellDensity;
	private int RampOn;
	private int RampOff;
	private int Seed=20;
	private boolean Block=false;
	
	public void randInit(){
		Random rand=new Random();
		for(int i=0;i<NumCell;i++){
			CarInCell[i]=rand.nextInt(LargestFlow);
		}
	}
	
	public void setRandomSeed(int seed){
		Seed=seed;
	}
	
	public int getRandomSeed(){
		return Seed;
	}
	
	public void redLight(){
		Block=true;
	}
	
	public void greenLight(){
		Block=false;
	}
	
	public boolean getLight(){
		return Block;
	}
	
	public void rampOn(int input){
		RampOn=input;
	}
	
	public int rampOff(){
		return RampOff;
	}
	
	public Road(int numcell, int largestflow, int largestdensity){
		NumCell=numcell;
		LargestDensity=largestdensity;
		LargestFlow=largestflow;
		TravelThrou=0;
		Wavespeed=(double)largestflow/(largestdensity-largestflow);
		CarInCell=new int[NumCell];
		randInit();
		InputIntersection=false;
		OutputIntersection=false;
	}
	
	public int[] currentState(){
		return CarInCell;
	}
	
	public void setCurrentState(int[] state){
		CarInCell=state;
	}
	
	public int getTravelNum(){
		return TravelThrou;
	}
	
	public void setInputIntersection(){
		InputIntersection=true;
	}
	
	public int getFirstCellDensity(){
		return CarInCell[0];
	}
	
	public void setOutIntersection(){
		OutputIntersection=true;
	}
	
	public void setFollowingCellCapacity(int density){
		NextRoadFirstCellDensity=density;
	}
	
	public void transByStep(){
		int[] NextCarInCell=new int[NumCell];
		int TransOut=0;
		int TransIn=0;
		for(int i=0;i<NumCell;i++){
			if(i==0){
				int IntoRoad=0;
				if(InputIntersection==true){
					IntoRoad=RampOn;
				}else{
					Random rand=new Random();
					IntoRoad=rand.nextInt(Seed)+1;//prevent to be 0	
				}
				TransIn=minValue(IntoRoad,LargestFlow,(int)((Wavespeed/Velocity)*(LargestDensity-CarInCell[i])));
				TransOut=minValue(CarInCell[i],LargestFlow,(int)((Wavespeed/Velocity)*(LargestDensity-CarInCell[i+1])));
				NextCarInCell[i]=CarInCell[i]+TransIn-TransOut;
			}else if(i==(NumCell-1)){
				TransIn=minValue(CarInCell[i-1],LargestFlow,(int)((Wavespeed/Velocity)*(LargestDensity-CarInCell[i])));
				if(OutputIntersection==false){
					TransOut=minValue(CarInCell[i],LargestFlow,99999);
				}else if(OutputIntersection==true&&Block==false){
					TransOut=minValue(CarInCell[i],LargestFlow,LargestDensity-NextRoadFirstCellDensity);
				}else{
					TransOut=minValue(CarInCell[i],LargestFlow,0);
				}
				RampOff=TransOut;
				TravelThrou=TransOut;
				NextCarInCell[i]=CarInCell[i]+TransIn-TransOut;
			}else {
				int NextCellCapa=(int)((Wavespeed/Velocity)*(LargestDensity-CarInCell[i+1]));
				int ThisCellCapa=(int)((Wavespeed/Velocity)*(LargestDensity-CarInCell[i]));	
				TransIn=minValue(CarInCell[i-1],LargestFlow,ThisCellCapa);
				TransOut=minValue(CarInCell[i],LargestFlow,NextCellCapa);
				NextCarInCell[i]=CarInCell[i]+TransIn-TransOut;	
			}
		}
		CarInCell=null;
		CarInCell=NextCarInCell;
	}
	
	public int minValue(int preCellNum,int largestFlow, int emptySpace){
		int Mini=0;
		if(preCellNum<largestFlow){
			Mini=preCellNum;
		}else{
			Mini=largestFlow;
		}
		if(emptySpace<Mini){
			Mini=emptySpace;
		}
		return Mini;
	}
}
