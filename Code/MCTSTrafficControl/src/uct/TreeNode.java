package uct;

public class TreeNode {
	private double Value;
	private double Reward;
	private int NumOfVisits;
//	private int[/*Action ID*/] NumOfActionVisits;
	private TreeNode ParentNode;
	private int ParentAction;
	private int[/*Road ID*/][/*Cell ID*/] CarInCell;
	private boolean[/*Intersection ID*/] LightSettings;
	private int[/*Road ID*/] RandomSeeds;
	private TreeNode[][] ChildrenId;
	
	public TreeNode(TreeNode parentnode,int parentaction){
		ParentNode=parentnode;
		ParentAction=parentaction;
		NumOfVisits=0;
		Value=0;
//		NumOfActionVisits=new int[16];
		ChildrenId=new TreeNode[16][5];
	}
	
	public TreeNode(){
		NumOfVisits=0;
		Value=0;
		ChildrenId=new TreeNode[16][5];
	}
	
	public TreeNode getParent(){
		return ParentNode;
	}
	
	public int getParentAction(){
		return ParentAction;
	}
	
	public void setReward(double reward){
		Reward=reward;
	}
	
	public double getReward(){
		return Reward;
	}
	
	public void addChildNode(TreeNode newnode,int action){
		for(int i=0;i<5;i++){
			if(ChildrenId[action][i]==null){
				ChildrenId[action][i]=newnode;
				break;
			}
		}
	}
	
	public TreeNode[][] getChildNodes(){
		return ChildrenId;
	}
	
	public void visit(){
		NumOfVisits++;
//		NumOfActionVisits[action]++;
	}
	
	public int getVisits(){
		return NumOfVisits;
	}
	
	public void setRandomSeeds(int[] seeds){
		RandomSeeds=seeds;
	}
	
	public int[] getRandomSeeds(){
		return RandomSeeds;
	}
	
	public void setStateFeatures(int[][] caronroad,boolean[] lights,int[] seeds,double reward){
		CarInCell=caronroad;
		LightSettings=lights;
		RandomSeeds=seeds;
		Reward=reward;
	}
	
	public int[][] getRoadCondition(){
		return CarInCell;
	}
	
	public boolean[] getLights(){
		return LightSettings;
	}
	
	public void setValue(double value){
		Value=value;
	}
	
	public double getValue(){
		return Value;
	}
}
