package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uct.TreeNode;
import uct.UpperConfidenceTree;


public class UCTListener implements ActionListener{

	public UCTListener(){
	}
	
	public void runUCT(){
		 TreeNode root=new TreeNode();
	      root.setStateFeatures(ListenerPipe.Carincell, ListenerPipe.Lights, ListenerPipe.Seeds, 0);
	      UpperConfidenceTree uct=new UpperConfidenceTree();
	      ListenerPipe.Action=uct.uctSearch(root);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		runUCT();
	}
}
