package student_player.mytree;

import hus.HusMove;

//Object that I will use when performing minimax algorithm to store the score and move associated with that score. 

public class Node {
	
	private HusMove move;
	private int score;
	
	public Node(){
		
	}
	
	
	public void setScore(int score){
		this.score = score;
	}
	public void setMove(HusMove move){
		this.move = move;
	}
	public int getScore(){
		return score;
	}
	public HusMove getMove(){
		return move;
	}
	
}
