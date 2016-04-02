package student_player.mytools;

import java.util.ArrayList;

import hus.HusBoardState;
import hus.HusMove;

public class MyTools{

    public static double getSomething(){
        return Math.random();
    }
    
  
    //evaluation function that I call from my StudentPlayer Class.
    public static double EvaluationFunction(HusBoardState current_board, int player_id, int opponent_id){
    	
    	//check here whether there were no valid moves again... if none and board.id = opponent id then
    	//return a massive number such that the original move that led to this path is chosen. 
    	
    	//if board.id = player_id then return a very small number such that this move is never chosen. 
    	
    	
    	//MONTECARLO
    	//Input a current_board, want to check the number of moves from this board that will result in winning montecarlo rollouts.
    	//Returns a double representing a percentage of random rollouts won on all possible moves from current board.
    	
    	
    	//want to lower the number of branches that are actually rolled out based on the score found. 
    	
    	
    	double monteCarloValue = MonteCarloEvaluation(current_board, player_id, opponent_id);
    
    	
    	double score = 0;
    	//turn number
    	//int turns = current_board.getTurnNumber();
    	
    	
    	//want to check number of seeds
    	int seedsTotal = 0;

    	//want to check the number of pits that contain 1 pit for opponent
    	int opponents01Pits = 0; 
    	
    	//want to check how many seeds are in capture locations total seeds, not pits
    	
    	
    	int[][] currentPits = current_board.getPits();
		int[] myPits = currentPits[player_id];
		int[] oppPits = currentPits[opponent_id];
		
		
		for(int i = 0; i < 32; i++){
			seedsTotal = seedsTotal + myPits[i];
			if (oppPits[i] == 0 || oppPits[i]==1){
				opponents01Pits++;
			}
		}
    	
    	score = 0.1*(opponents01Pits) + 0.6*seedsTotal + 0.3*monteCarloValue;
    	
    	
    	return score;
    	
    }

	public static double MonteCarloEvaluation(HusBoardState current_board, int player_id, int opponent_id){
		
		ArrayList<HusMove> moves = current_board.getLegalMoves();
		double myWins = 0;
		double oppWins = 0;
		
		for(int i = 0; i < moves.size(); i++){
			
			HusBoardState cloned_board_state = (HusBoardState) current_board.clone();
			cloned_board_state.move(moves.get(i));
			
			while(!(cloned_board_state.gameOver())){
				cloned_board_state.move(cloned_board_state.getRandomMove());
			}
			int winner = cloned_board_state.getWinner();
			
			if(winner == player_id){
				myWins++;
				//System.out.println("Monte-Carlo Rollout: I win.");
			} else if (winner == opponent_id){
				oppWins++;
				//System.out.println("Monte-Carlo Rollout: Opponent wins");
			} else{
				//System.out.println("Monte-Carlo Rollout: Draw or something else...");
			}

		}
		
		double percentageWins = myWins/moves.size()*100;
		//System.out.println("Monte-Carlo Rollout: Games Won: "+myWins+" of "+(moves.size())+". Percentage Wins = "+ percentageWins);
		
		return percentageWins;
	}



    
    
    
    
   
    
}
