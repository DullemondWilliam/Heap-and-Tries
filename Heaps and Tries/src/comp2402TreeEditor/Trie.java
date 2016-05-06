package comp2402TreeEditor;

import java.awt.Point;

import javax.swing.JOptionPane;


public class Trie extends Tree{
	public Trie(){
		System.out.println("Made a new Trie");
	}
	
	public void insert(String dataString){
		if(isEmpty()){
			TreeNode newRoot = new TrieNode(" ");
			setRoot(newRoot);
			newRoot.setLocation(getOwner().getDefaultRootLocation());
		}
		TreeNode parent = getRoot();
		((TrieNode) parent).add(dataString);
	}
	public void remove(String dataString){
		TrieNode toDelete = ((TrieNode) root()).search(dataString);
		if(toDelete==null) return;
		
		if(toDelete.getChildren().isEmpty()==false){//something below dont delete
			toDelete.changeShapeOval();
			toDelete.setWord(null);
		}else{
			TrieNode hold = toDelete;
			while(((TrieNode) hold.parent()).getWord()==null&&((TrieNode) hold.parent())!=root()&&
					((TrieNode) hold.parent()).getChildren().size()==1){
				hold = (TrieNode) hold.parent();
			}
			((TreeNode) hold.parent()).removeChildNode(hold);
		}
	}
	
	public DataADT find(String aKeyString) {
		TrieNode toSelect = ((TrieNode) root()).search(aKeyString);
		clearSelections();
		if(toSelect==null){
			return null;
		}else{
			toSelect.selectWord();
		}
	       
	    return  null;
    }
	
	
	public void createChildForNode(TreeNode aNode, Point aLocation){
		/*Graphical creating of nodes is not allowed for a Trie
		 *since it must control where Nodes are placed.
		 */
		JOptionPane.showMessageDialog(getOwner(), 
				"MUST use ADT Insert to add nodes to Trie", 
				"Operation Not allowed for Trie", 
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean allowsGraphicalDeletion(){ 
		//Tries  do not allow the deletion of arbitrary nodes since the Trie
		//must get a chance to restore itself. The TreeADT "remove" method should be used
		//to delete nodes
		return false;
	}
}

