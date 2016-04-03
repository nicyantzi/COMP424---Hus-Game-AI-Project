package student_player.nodes;

import java.util.ArrayList;
import java.util.List;
import hus.HusBoardState;
import hus.HusMove;

public class Nodes {
	private HusBoardState board = null;
	private ArrayList<Nodes> children = new ArrayList<Nodes>();
	private int score = 0;
	private int move = 0;
	private int num = 0;
	private int den = 0;
	
	public Nodes(HusBoardState board) {
		this.board = board;
	}
	
	public Nodes(HusBoardState board, int score) {
		this.board = board;
		this.score = score;
	}
	
	public Nodes getChildAt(int i) {
		return children.get(i);
	}
	
	public void addChild(Nodes child) {
		this.children.add(child);
	}
	
	public void setWin() {
		this.num = this.num + 1;
		this.den = this.den + 1;
	}
	
	public int getWin() {
		return this.num;
	}
	
	public void setLoss() {
		this.den = this.den + 1;
	}
	
	public int getLoss() {
		return this.den;
	}
	
	public void setWinLoss(Nodes n) {
		this.num = this.num + n.getWin();
		this.den = this.den + n.getLoss();
	}
	
	public double getWinLoss() {
		double fract = this.getWin() / (double) this.getLoss();
		return fract;
	}
	
	public void removeChildAt(int i) {
		this.children.remove(i);
	}
	
	public HusBoardState getBoard() {
		return this.board;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setMove(int i) {
		this.move = i;
	}
	
	public int getMove() {
		return this.move;
	}
	
	public int numChildren() {
		return this.children.size();
	}
}
	