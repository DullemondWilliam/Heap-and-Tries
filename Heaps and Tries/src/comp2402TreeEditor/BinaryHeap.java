package comp2402TreeEditor;



//import bTreeNode;

import java.util.*;
import java.awt.*;
import java.io.*;

import javax.swing.JOptionPane;

//DISCLAIMER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//==========
//This code is designed for classroom illustration
//It may have intentional omissions or defects that are
//for illustration or assignment purposes

//This code is based on hierarchy that still requires lots of casting
//
//That being said: Please report any bugs to me so I can fix them
//...Lou Nel (ldnel@scs.carleton.ca)
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


public class BinaryHeap extends BTree implements BTreeADT{
	//       =====
    //This class represents a binary heap
	
	private BinaryHeapNode lastNode = null;
    
	//CONSTRUCTORS==================================================================
	public BinaryHeap() {
    }
	
	public DataADT top(){
		//Answer the top (root) data item in the heap which should be the smallest
		//This operation allows us to inspect the top of the heap
		
		if(this.isEmpty()) return null;
		else return root().getData();
	}
	
	public DataADT removeTop(){
		//This is the main operation that a heap is designed for.
		//Remove the top (root) node from heap and return its data item
		//which should be the one with the smallest key value
		//The heap should re-adjust in O(log(n)) time.
		
	   if(this.isEmpty()) return null;
	   
	   DataADT rootData = root().getData();
	   removeNode((BinaryHeapNode) root());
	   return rootData;
	}



    //BTreeADT inteface methods =========================================================
     public void insert(String dataString){

    	 Data data = new Data(dataString);

    	 TreeNode newChildNode = new BinaryHeapNode(data);

    	 if(isEmpty()) {
    		 setRoot(newChildNode);
    		 newChildNode.setLocation(getOwner().getDefaultRootLocation());
    		 lastNode = (BinaryHeapNode) newChildNode;
    	 }
    	 else{ 
    		 //System.out.println("making new Node ");
    		 BinaryHeapNode whereToAdd;
    		 if(lastNode.parent() == null&& (lastNode.leftChild ==null || lastNode.leftChild == null)){
    			 whereToAdd=(BinaryHeapNode) root();
    		 }else{
    			 whereToAdd = lastNode.nextUp();
    		 }
    		 
    		 if(data.compare(whereToAdd.getData())<0){
    			 //System.out.println("this node is unworthy");
    			 DataADT temp = whereToAdd.getData();
    			 whereToAdd.setData(data);
    			 newChildNode.setData(temp);
    		 }
    		 
    		 //System.out.println("making new Node ");
    		 whereToAdd.insertNode(newChildNode);
    		 lastNode = (BinaryHeapNode) newChildNode; 
    	 }
    }
     
     public void remove(String aKeyString){//Change Mabey
    	DataADT look = new Data(aKeyString);
    	BinaryHeapNode toDelete = ((BinaryHeapNode) root()).search(look);
    	removeNode(toDelete);
    	
     }
    
    private void removeNode(BinaryHeapNode nodeToRemove){
    	if(nodeToRemove==null){
			return ;
		}else if(nodeToRemove!=null){
			nodeToRemove.setData(lastNode.getData());//Reset the found node's data to the last entry
			BinaryHeapNode tempLast = lastNode.lastUp();//obtain the last last node	
			if(lastNode == null){
				return;
			}			
			//cut off last
			if((((BinaryHeapNode) lastNode.parent()).leftChild()) == lastNode){
				((BTreeNode) lastNode.parent()).setLeftChild(null);
			}
			if((((BinaryHeapNode) lastNode.parent()).rightChild()) == lastNode){
				((BTreeNode) lastNode.parent()).setRightChild(null);
			}
			//set last proper
			lastNode = tempLast;			
			nodeToRemove.cleanDown();		 
		}
    }
    
    //===================================================================================
    
    public void createNewRoot(Point aLocation){
    	//create a new root for the tree    	
    	setRoot(new BinaryHeapNode(aLocation));
    }    
   
    public void createChildForNode(TreeNode aNode, Point aLocation){
    	/*Graphical creating of nodes is not allowed for a binary heap
    	 *since the heap
    	 *wants to control where Nodes are placed.
    	*/
         
        JOptionPane.showMessageDialog(getOwner(), 
        "MUST use ADT Insert to add nodes to Binary Heap", 
        "Not allowed for Binary Heap", 
        JOptionPane.ERROR_MESSAGE);
  
    	
    }

    public boolean allowsGraphicalDeletion(){ 
    //Binary Heaps  do not allow the deletion of arbitrary nodes since the heap
    //must get a chance to restore itself. The TreeADT remove method should be used
    //to delete nodes
      return false;
    }

}
