package student_player.mytools;


import hus.HusBoardState;
import java.util.Random;
import java.util.Collections;
import student_player.mytree.Node;
import java.util.ArrayList;
import hus.HusMove;
import hus.HusBoardState;
import hus.HusPlayer;


/****************************************
*										*
*	Nic Yantzi - 260467234				*
*	Hus Project 						*
*	COMP424 - Artificial Intelligence	*
*										*							
/***************************************/


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
    	
    	if (option == 0){    	//Option 0: Heuristic Eval on current board. Using when want to calculate current board score,
    							//as well as when i am running MinimaxAB when i have less than 70% of the max seeds. 

    		int[] heuristicResults = HeuristicEvaluation(current_board, player_id, opponent_id);
        	double seedsTotal = (double) heuristicResults[0];
        	
        	score = seedsTotal;
        	
        	return score;
    	
    	} else if (option ==1) {		//Option 1: My Player Minimax with MCTS, have more than a the cutoff, based on actual %, rollouts determined.
    		
    	
        	int boardWinner = EndBoardEvaluation(current_board,player_id,opponent_id);

        	//want to see if the minimax level i have run too has a game board winner, if it is me, then
        	//definitly want to chose the move. If opponent then definitly dont want to chose this move. 
        	//if no winner at this level then i continue with the evaluation of the game board using
        	//evaluation function only, or evaluation with monte carlo rollouts. 

        	if (boardWinner == 1){
        		score = 1000;
        		return score;
        	} else if (boardWinner == -1){
        		score = -1000;
        		return score;
        	} else {    //boardWinner == 0

                int[] heuristicResults = HeuristicEvaluation(current_board, player_id, opponent_id);
                double seedsTotal = (double) heuristicResults[0];
                //double seedsBack = (double) heuristicResults[2];
                //double seedsBackPer = seedsBack/seedsTotal *100;
                //System.out.println("Seeds Back Per = "+seedsBackPer);

	        	//METHOD Choose Policy: Minimax with alpha beta pruning. Then when certain percentage of total game
	    		//seeds is reached, use Monte Carlo Search.
	        	
	        	// int rollout89 = 10;
	        	// int rollout79 = 10;
	        	// int rollout69 = 10;
	        	// int rolloutOther = 2000;
	    		        
	  

	        	if(greaterThanPer == 1){				//0.59
	    	    	score = seedsTotal;	 

	        	// } else if(greaterThanPer == 2){			//0.69
	        	// 	//System.out.println("Currently have more than 70% of the seeds so using "+ rollout69 + " rollouts.");
	    	    // 	//double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, rollout69);
	    	    // 	score = seedsTotal;

	        	// } else if(greaterThanPer == 3){			//0.79
	        	// 	//System.out.println("Currently have more than 80% of the seeds so using "+ rollout79 + " rollouts.");
	    	    // 	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, rollout79);
	    	    // 	score = seedsTotal;
	        	// } else if(greaterThanPer == 4){			//0.89
	        	// 	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, rolloutOther);
	        	// 	score = seedsTotal + monteCarloValue2;

	        	} else {						//everything
	        		//double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, rolloutOther);
	        		score = seedsTotal;
	        		//System.out.println("Score = "+score);
	        	}
	    		return score;	
        	
    		}
    		
    	} else { 				//Option 2: My Opponent, used this for testing if I didnt want my opponennt to have a certain ability. 


			int[] heuristicResults = HeuristicEvaluation(current_board, player_id, opponent_id);
    		int seedsTotal = heuristicResults[0];

			//METHOD Choose Policy: Minimax with alpha beta pruning. Then when certain percentage of total game
    		//seeds is reached, use Monte Carlo Search.
        	
        	int rollout1 = 5;
        	int rollout2 = 20;
        	int rollout3 = 100;
    		        	
        	if(greaterThanPer == 4){
    			//System.out.println("Currently have more than 90% of the seeds so using "+ rollout89 + " rollouts.");
    	    	double monteCarloValue2 = MonteCarloEvaluationV2(current_board, player_id, opponent_id, rollout1);
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

    	// int seedsBack = 0;

    	// //want to check the number of pits that contain 1 pit for opponent
    	// int opponents01Pits = 0; 
    	
    	//want to check how many seeds are in capture locations total seeds, not pits
    	
    	int[][] currentPits = current_board.getPits();
		int[] myPits = currentPits[player_id];
		int[] oppPits = currentPits[opponent_id];
		
		
		for(int i = 0; i < 32; i++){
			seedsTotal = seedsTotal + myPits[i];
			// if (oppPits[i] == 0 || oppPits[i]==1){
			// 	opponents01Pits++;
			// }
		}
        //find the number of seeds that are in positions of attack, want to limit this. 

		// for (int i = 0; i <16; i++){
		// 	seedsBack = seedsBack + oppPits[i];
		// }


		int[] scoreResults = new int[3];
		scoreResults[0] = seedsTotal;
		// scoreResults[1] = opponents01Pits;
		// scoreResults[2] = seedsBack;
		
		
		return scoreResults;
    	
    }


    public static int EndBoardEvaluation(HusBoardState current_board, int player_id, int opponent_id){

    	//want to be able to run minimax on the end game board to find the exact move to pick. 
    	//evaluation based solely on whether winnning or not. 

    	if (current_board.getWinner() == player_id){
    		//System.out.println("End Game Board with Me: "+player_id+", Winning");
    		return 1;
    	} else if (current_board.getWinner() == opponent_id){
    		//System.out.println("End Game Board with Opponent: "+opponent_id+", Winning");
    		return -1;
    	} else return 0;
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


    public static ArrayList<Integer> NewSearchOrder(HusBoardState current_board, int myPlayer, int oppPlayer){

        //arrayList containing the scores and orginal index of each possible child of the root. 
        //going to sort these scores in increasing order. this will hopefully increase the number of branches that
        //we can prune using our alpha beta pruning. 


        ArrayList<Node> firstRowSort = new ArrayList<Node>();

        //new order of first row nodes (game boards) we will search....

        ArrayList<HusMove> moves = current_board.getLegalMoves();

        ArrayList<Integer> newSearchOrder = new ArrayList<Integer>();


        for(int i = 0; i < moves.size(); i++){

            HusBoardState cloned_board_state = (HusBoardState) current_board.clone();
            cloned_board_state.move(moves.get(i));
            int score = (int) MyTools.EvaluationFunction(cloned_board_state, myPlayer, oppPlayer, 0);

            //create new node that contains the index and the score associated with that index
            //will create a new arraylist of indexes to better sort the children. 
            Node newNode = new Node(score, i);
            firstRowSort.add(newNode);

        }

        Collections.sort(firstRowSort);

        for(int i = 0; i < firstRowSort.size(); i++){
            newSearchOrder.add(firstRowSort.get(i).getIndex());
        }


        return newSearchOrder;


    }



    /*


        perform a quiesence search on the tree.

            essentially want to do a alpha beta pruning search depth 3 and find the top 1/4 scores
            //then do a further depth search on these certain nodes with higher scores. 

            






    */


}
