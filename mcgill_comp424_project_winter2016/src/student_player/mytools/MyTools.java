package student_player.mytools;

import java.util.ArrayList;

import hus.HusBoardState;
import hus.HusMove;

public class MyTools{

	public static int greaterThanPer = 0;

    public static double getSomething(){
        return Math.random();
    }
        
    /* EvaluationFunction takes in a HusBoardState, both player id's and an int option. This option represents
     * what kind of evaluation function you want to run. 
     * If option = 0, then you are just perfomring an evaluation on the inputted board.
     * IF option = 1, then you are perfoming an evaluation on the inputted board and utilizing monte-carlo.
     */
    
    public static double EvaluationFunction(HusBoardState current_board, int player_id, int opponent_id, int option){
    	     	
    	//initialize the score we will return
    	double score = 0;
    	//turn number
    	int turns = current_board.getTurnNumber();
    	
    	if (option == 0){    	//Option 0: Heuristic Eval on current board. Using when want to calculate current board score,
    							//as well as when i am running MinimaxAB when i have less than 70% of the max seeds. 

    		int[] heuristicResults = HeuristicEvaluation(current_board, player_id, opponent_id);
        	int seedsTotal = heuristicResults[0];
        	int opponents01Pits = heuristicResults[1];
        	
        	
        	score = 0*opponents01Pits + 1*seedsTotal;
        	
        	return score;
    	
    	} else {		//Option 1: Minimax with MCTS, have more than a the cutoff, based on actual %, rollouts determined.
    		
    		int[] heuristicResults = HeuristicEvaluation(current_board, player_id, opponent_id);
        	int seedsTotal = heuristicResults[0];
        	int opponents01Pits = heuristicResults[1];
    		
    		//METHOD Choose Policy: Minimax with alpha beta pruning. Then when certain percentage of total game
    		//seeds is reached, use Monte Carlo Search.
        	
        	int rollout89 = 250;
        	int rollout79 = 100;
        	int rollout69 = 200;
    		        	
        	if(greaterThanPer == 1){
    			//System.out.println("Currently have more than 90% of the seeds so using "+ rollout89 + " rollouts.");
    	    	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, 20);
    	    	score = seedsTotal + monteCarloValue2;
        	} else if(greaterThanPer == 2){
        		//System.out.println("Currently have more than 80% of the seeds so using "+ rollout79 + " rollouts.");
    	    	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, 20);
    	    	score = seedsTotal + monteCarloValue2;
        	} else if(greaterThanPer == 3){
        		//System.out.println("Currently have more than 80% of the seeds so using "+ rollout79 + " rollouts.");
    	    	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, 20);
    	    	score = seedsTotal + monteCarloValue2;
        	} else {
        		score = seedsTotal;
        	}
    		return score;	
    	}
    	
    	
    }
    
    public static int[] HeuristicEvaluation(HusBoardState current_board, int player_id, int opponent_id){
    	
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
		
		int[] scoreResults = new int[2];
		scoreResults[0] = seedsTotal;
		scoreResults[1] = opponents01Pits;
		
		
		return scoreResults;
    	
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
	
	
	public static double MonteCarloEvaluationV2(HusBoardState current_board, int player_id, int opponent_id, int runs){
		
		double myWins = 0;

		for(int i = 0; i < runs; i++){
			
			HusBoardState cloned_board_state = (HusBoardState) current_board.clone();
			
			while(!(cloned_board_state.gameOver())){
				//System.out.println("Game is not over");
				cloned_board_state.move(cloned_board_state.getRandomMove());
			}
			int winner = cloned_board_state.getWinner();

			if(winner == player_id){
				myWins++;
			}
		}
		
		double percentageWins = myWins/runs*100;
		//System.out.println("Monte-Carlo Rollout: Games Won: "+myWins+" of "+runs+". Percentage Wins = "+ percentageWins);
		
		return percentageWins;
		
	}
}
