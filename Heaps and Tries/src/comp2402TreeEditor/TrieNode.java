package comp2402TreeEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;



public class TrieNode extends TreeNode implements TreeNodeADT{
	private String word;
	public void setWord(String a){word=a;}
	public String getWord(){return word;}
	
	public TrieNode(){}

	public TrieNode(String a){
		this.setData( new Data(a));
	}
	
	public TrieNode(DataADT a){
		this.setData(a);
	}

	public void add(String a){
		//Runs in (a.length) time
		String firstChar =String.valueOf(a.charAt(0));
		DataADT temp = new Data(firstChar);
		Boolean check = false;

		for(int i=0;i<getChildren().size();i++){
			if(getChildren().get(i).getData().compare(temp)==0){
				check =true;
				if(a.length()>1){
					((TrieNode) getChildren().get(i)).add(a.substring(1));
				}else{
					((TrieNode) getChildren().get(i)).changeShapeSquare();
					((TrieNode) getChildren().get(i)).setWord(((TrieNode) getChildren().get(i)).getLetters());
				}
			}
		}
		if(check==false){
			TrieNode newNode = new TrieNode(firstChar);
			insertNode(newNode);			
			if(a.length()==1){
				newNode.changeShapeSquare();
				newNode.setWord(newNode.getLetters());
			}
			if(a.length()>1){
				int index = getChildren().indexOf(newNode);
				((TrieNode) getChildren().get(index)).add(a.substring(1));
			}
		}
	}

	public String getLetters(){//Obtains all the letters above to make a word
	//runs in (string.length) time
		if(this.parent()!=null){
			return ((TrieNode) this.parent()).getLetters() + this.getData().key();
		}
		return "";
	}
	
	public void selectWord(){
		if(this.parent()!=null){
			((TrieNode) this.parent()).selectWord();
			this.setSelection(true);
		}
	}
	
	public TrieNode search(String a){//searches for matching chars
		//runs in (string.length) time
		String firstChar =String.valueOf(a.charAt(0));
		DataADT compareTo = new Data(firstChar);
		
		for(int i=0;i<getChildren().size();i++){
			if(getChildren().get(i).getData().compare(compareTo)==0){
				if(a.length()>1){
					return ((TrieNode) (this.getChildren()).get(i)).search(a.substring(1));
				}
				else {
					if(((TrieNode) (this.getChildren()).get(i)).getWord()!=null){
						return (TrieNode) (this.getChildren()).get(i);
					}
				}
			}
		}
		return null;
	}
	
	
	public void drawNodeLabel(Graphics aPen) {
		// Draw the node label
		Font oldFont = aPen.getFont(); //cache any font currently in use
		Color oldColor = aPen.getColor();

		aPen.setFont(labelFont);
		FontMetrics metrics = aPen.getFontMetrics();
		String label = key();
		int labelWidth = metrics.stringWidth(label);
		int stringHeightOffset = labelPointSize/4; //rough estimate

		aPen.setColor(Color.black);
		aPen.drawString(label, location.x - labelWidth/2, location.y + stringHeightOffset);

		if(TreeEditor.displayDataValues && value() != null && value().length() > 0){
			aPen.drawString(value(), location.x+ RADIUS, location.y - RADIUS);

		}
		//if(this.getWord!=null){
			
		//}

		if(TreeEditor.displayNodeLabels && getNodeLabel() != null && getNodeLabel().length() > 0){
			Color currentPenColor = aPen.getColor();
			aPen.setColor(NODE_LABEL_COLOR);
			aPen.drawString(getNodeLabel(), location.x- RADIUS, location.y - RADIUS);
			aPen.setColor(currentPenColor);

		}

		aPen.setFont(oldFont);
		aPen.setColor(oldColor);
		if(this.getWord()!=null){
			drawWord(aPen);
		}
		
	}
	
	
	public void drawWord(Graphics aPen) {
		// Draw the node label
		Font oldFont = aPen.getFont(); //cache any font currently in use
		Color oldColor = aPen.getColor();

		aPen.setFont(wordFont);
		FontMetrics metrics = aPen.getFontMetrics();
		String label = this.getWord();
		int labelWidth = metrics.stringWidth(label);
		int stringHeightOffset = wordPointSize/4; //rough estimate

		aPen.setColor(Color.black);
		aPen.drawString(label, location.x+ (labelWidth/2) , location.y + stringHeightOffset);

		if(TreeEditor.displayDataValues && value() != null && value().length() > 0){
			aPen.drawString(value(), location.x+ RADIUS, location.y - RADIUS);

		}

		aPen.setFont(oldFont);
		aPen.setColor(oldColor);
		
	}
}