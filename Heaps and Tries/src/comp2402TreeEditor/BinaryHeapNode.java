package comp2402TreeEditor;

//import bTreeNode;

//import bTreeNode;

//import bTreeNode;

import java.util.*;
import java.awt.*;
import java.io.*;

public class BinaryHeapNode extends BTreeNode implements BTreeNodeADT{
	//This class represents a node in a Binary Heap
	

	// CONSTRUCTORS ========================================================
	public BinaryHeapNode() {
	}

	public BinaryHeapNode(Point aPoint) {
		super(aPoint);
	}
	
	public BinaryHeapNode(Data data) {
		super(data);
	}

	public void insertNode(TreeNode newNode){
		//Constant time
		if(!(newNode instanceof BinaryHeapNode)) return;//Stops if trying add something else
		if(this.leftChild==null){//Left Child null add there
			this.leftChild = (BTreeNode) newNode;
			newNode.setParent(this);
		    this.bubbleUpSort();
			
		}else if(this.rightChild==null){//if Left Child not null add to right
			this.rightChild = (BTreeNode) newNode;
			newNode.setParent(this);
			this.bubbleUpSort();
		}
	}
	
	public void bubbleUpSort(){//compares data to parent and recursive calls it self
		//Runs at max log(n)
		if(this.parent()!=null && this.getData().compare(parent().getData())<0){
			DataADT temp = this.parent().getData();
			this.parent().setData(this.getData()); 
			this.setData(temp);
			((BinaryHeapNode) this.parent()).bubbleUpSort();
		}
	}

 	public BinaryHeapNode nextUp(){//used to find the node to add to
 		//Runs at max 2log(n)
		if(this.parent()==null){//Hit Root
			BinaryHeapNode c = this;
			while(true){//is LeftSide
				c = (BinaryHeapNode) c.leftChild;
				if(c.leftChild==null){return c;}
			}
		}
		if(((BTreeNode)parent()).leftChild() == this){
			if(((BTreeNode)parent()).rightChild==null){
				return (BinaryHeapNode) this.parent();
			}
			return ((BinaryHeapNode) ((BinaryHeapNode)parent()).rightChild).nextDown();
		}
		if(((BTreeNode)parent()).rightChild == this){
			return ((BinaryHeapNode) this.parent()).nextUp();
		}
		return null;
	}
	
	public BinaryHeapNode nextDown(){//used to find the node to add to
		//Runs at max log(n)
		if(this.leftChild==null||this.rightChild==null){return this;}
		BinaryHeapNode check = ((BinaryHeapNode) this.leftChild).nextDown();
		if(check!=null){return check;}
		check = ((BinaryHeapNode) this.rightChild).nextDown();
		if(check!=null){return check;}
		return null;
	}
     
	public BinaryHeapNode lastUp(){//Finds the previous last node(deleting)
		//Runs at max 2log(n)
		if(this.parent()==null){
			BinaryHeapNode c = this;
			while(true){//is LeftSide
				c = (BinaryHeapNode) c.rightChild;
				if(c.rightChild==null){return c;}
			}
		}
		if(((BinaryHeapNode) parent()).leftChild() == this){
			//currently at a left child has to be up one
			return ((BinaryHeapNode) this.parent()).lastUp();
		}
		if(((BinaryHeapNode) parent()).rightChild() == this){
			//currently at right child// it is going to be in the left of my parent
			return ((BinaryHeapNode) ((BinaryHeapNode)parent()).leftChild()).lastDown();
		}
		return null;
	}
	
	public BinaryHeapNode lastDown(){//Finds the previous last node(deleting)
		//runs at max log(n)
		if(this.leftChild==null && this.rightChild==null){
			return this;
		}
		if(this.rightChild!=null){
			return ((BinaryHeapNode) this.rightChild).lastDown();
		}else{
			return (BinaryHeapNode) this.leftChild;
		}
	}
	
	public BinaryHeapNode search(DataADT want){//Search every crevice 
		//runs at max (n) 
		if(this.getData().compare(want)==0){
			//System.out.println("it is equal");
			return this;
		}else if(this.getData().compare(want)>0){
			return null;
		}else if(this.getData().compare(want)<0){
			BinaryHeapNode a;
			if(this.leftChild!=null){
				a = ((BinaryHeapNode) this.leftChild).search(want);
				if(a!=null){return a;}
			}
			if(this.rightChild!=null){
				a = ((BinaryHeapNode) this.rightChild).search(want);
				if(a!=null){return a;}
			}
			if(this.leftChild==null&&this.rightChild==null){
				return null;
			}
			return null;
		}
		return null;
	}
	
	public void cleanDown(){//compares against children and swaps as needed
		//Runs at log(n)
		if((this.leftChild!=null && this.getData().compare(this.leftChild.getData())>0) ||
				(this.rightChild!=null && this.getData().compare(this.rightChild.getData())>0)){		
			if(this.leftChild!=null&&this.rightChild!=null){
				if(this.leftChild.getData().compare(this.rightChild.getData())<0){
					DataADT temp = this.getData();
					this.setData(this.leftChild.getData());
					this.leftChild.setData(temp);
					((BinaryHeapNode) this.leftChild).cleanDown();
				}else{
					DataADT temp = this.getData();
					this.setData(this.rightChild.getData());
					this.rightChild.setData(temp);
					((BinaryHeapNode) this.rightChild).cleanDown();					
				}
			}else if(this.leftChild!=null&&this.rightChild==null){
				DataADT temp = this.getData();
				this.setData(this.leftChild.getData());
				this.leftChild.setData(temp);
				((BinaryHeapNode) this.leftChild).cleanDown();
			}
			else if(this.leftChild==null&&this.rightChild!=null){
				DataADT temp = this.getData();
				this.setData(this.rightChild.getData());
				this.rightChild.setData(temp);
				((BinaryHeapNode) this.rightChild).cleanDown();	
			}
		}
	}
}