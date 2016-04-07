package student_player.mytree;


import hus.HusMove;
import java.util.Comparator;

//Nic Yantzi
//Object that I will use when performing Minimax algorithm to store the score and move associated with that score.

/****************************************
*										*
*	Nic Yantzi - 260467234				*
*	Hus Project 						*
*	COMP424 - Artificial Intelligence	*
*										*							
/***************************************/


public class Node implements Comparable<Node> {
	private int score;
	private int index;
	
	//CONSTRUCTOR FOR NODE
	public Node(int score, int index){
		this.score = score;	
		this.index = index;
	}
	
	//SETTER METHODS FOR NODE
	public void setScore(int score){
		this.score = score;
	}


	//GETTER METHODS for Node
	public int getScore(){
		return score;
	}
	public int getIndex(){
		return index;
	}

	public int compareTo(Node compareNode) {

		int compareScore = ((Node)compareNode).getScore();

		//sort in ascending order (i.e. increasing)

		return this.score - compareScore;
	}

}
