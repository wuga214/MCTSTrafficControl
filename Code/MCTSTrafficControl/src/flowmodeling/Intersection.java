package flowmodeling;

public class Intersection {
	private boolean Phrase=false;
	private Road UpperRoad;
	private Road LeftRoad;
	
	public void changePhrase(){
		Phrase=!Phrase;
		if(Phrase==true){
			UpperRoad.redLight();
			LeftRoad.greenLight();
		}else{
			UpperRoad.greenLight();
			LeftRoad.redLight();
		}
	}
	
	public Intersection(Road a, Road b){
		UpperRoad=a;
		LeftRoad=b;
		changePhrase();
	}
	
	public void setLights(boolean light){
		if(light==true){
			UpperRoad.redLight();
			LeftRoad.greenLight();
		}else{
			UpperRoad.greenLight();
			LeftRoad.redLight();
		}
	}
	
	public boolean getLights(){
		if(UpperRoad.getLight()==true){
			return true;
		}else{
			return false;
		}
	}
}
