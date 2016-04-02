package student_player.mytree;

import hus.HusMove;

//Nic Yantzi
//Object that I will use when performing Minimax algorithm to store the score and move associated with that score. 

public class Node {
	
	private HusMove move;
	private int score;
	private int alpha;
	private int beta;
	
	
	//CONSTRUCTOR FOR NODE
	public Node(){
		alpha = -100000;
		beta = 100000;
		
	}
	
	//SETTER METHODS FOR NODE
	public void setScore(int score){
		this.score = score;
	}
	public void setMove(HusMove move){
		this.move = move;
	}
	public void setAlpha(int alpha){
		this.alpha = alpha;
	}
	public void setBeta(int beta){
		this.beta = beta;
	}
	
	//GETTER METHODS for Node
	public int getScore(){
		return score;
	}
	public HusMove getMove(){
		return move;
	}
	public int getAlpha(){
		return alpha;
	}
	public int getBeta(){
		return beta;
	}
}
