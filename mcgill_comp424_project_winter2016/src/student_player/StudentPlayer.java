package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;

import student_player.mytools.MyTools;
import student_player.mytree.Node;

/****************************************
*										*
*	Nic Yantzi - 260467234				*
*	Hus Project 						*
*	COMP424 - Artificial Intelligence	*
*										*							
/***************************************/


/** A Hus player submitted by a student. */
public class StudentPlayer extends HusPlayer {

    /** You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else. */
    public StudentPlayer() { super("260467234"); }

    /** This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class hus.RandomHusPlayer
     * for another example agent. */
    public HusMove chooseMove(HusBoardState board_state)
    {
    	
    	long startTime = System.currentTimeMillis();
    	
        // Get the contents of the pits so we can use it to make decisions.
//        int[][] pits = board_state.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
//    	int[] my_pits = pits[player_id];
//      int[] op_pits = pits[opponent_id];
        int turnNumber = board_state.getTurnNumber();

        System.out.println("\nNewTurn - Turn Number: "+turnNumber);
        System.out.println("Player_Id = "+player_id);
        System.out.println("Opponent_Id = "+opponent_id);

        // Use code stored in ``mytools`` package.
        MyTools.getSomething();
        

        // Get the legal moves for the current board state.
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        
        //BASIC VERSION THAT WAS GIVEN. 
        HusMove move = moves.get(0);
        
        /////////////////////////////////////////////
        
        //V1 - Minimax 1 level deep.
        
        //move = MinimaxDecision(moves, board_state);
        
        /////////////////////////////////////////////
        
        //V2 - Minimax n levels deep
        
        //Node bestNode = Minimax(4, 0, board_state);
        
        //HusMove bestMove = bestNode.getMove();
        
        /////////////////////////////////////////////
        
        //V3 Minimax with Alpha Beta Pruning Using Monte Carlo Tree Search with
        //Varying depths and rollouts based on positioning in game

        
        int alpha = -10000;
        int beta = 10000;
    	
    	HusMove bestMoveAB = moves.get(0);
    	
		double totalSeedsCurrent= MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);
		double totalPossibleSeeds = 96;
		
		double percentageTotal = totalSeedsCurrent/totalPossibleSeeds;
		System.out.println("Percentage Total = "+percentageTotal);
		
		//Depths for Different MinimaxAB methods based on percentage of seeds. 
		int depth89 = 5;
		int depth79 = 4;
		int depth69 = 3;
		int depth59 = 7;
		int depthOther = 6;

		if (percentageTotal > 0.89){
			System.out.println("Running MinimaxAB with MCTS at Depth "+depth89+". Currently have more than 90% of the seeds.");
			MyTools.greaterThanPer = 1;
			bestMoveAB = MinimaxAB(board_state, depth89, alpha, beta);
		} else if( percentageTotal > 0.79){
			System.out.println("Running MinimaxAB with MCTS at Depth "+depth79+". Currently have more than 80% of the seeds.");
			MyTools.greaterThanPer = 2;
			bestMoveAB = MinimaxAB(board_state, depth79, alpha, beta);	
		} else if (percentageTotal > 0.69){
			System.out.println("Running MinimaxAB with MCTS at Depth "+depth69+". Currently have more than 70% of the seeds.");
			MyTools.greaterThanPer = 3;
			bestMoveAB = MinimaxAB(board_state, depth69, alpha, beta);
		} else if(percentageTotal > 0.59){
			MyTools.greaterThanPer = 0;
			System.out.println("Running Minimax AB at Depth "+depth59+". Currently have more than 60& of the seeds.");
			bestMoveAB = MinimaxAB(board_state, depth59, alpha, beta);	
		} else {
			MyTools.greaterThanPer = 0;
			System.out.println("Running Minimax AB at Depth "+depthOther+".");
			bestMoveAB = MinimaxAB(board_state, depthOther, alpha, beta);
		}

        ////////////////////////////////////////////

        //Code Given

        // We can see the effects of a move like this...
        HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
        cloned_board_state.move(move);

        // But since this is a placeholder algorithm, we won't act on that information.
       
        
        //RETURN THE CHOSEN MOVE. 

        //METHOD V1
        //return move;
        
        //METHOD V2
        //return bestMove;
        
        //METHOD V3
        
        long endTime = System.currentTimeMillis();
        
        long totalTime = endTime - startTime;
        System.out.println("Total Time (in milliseconds) for this turn: "+totalTime); 
        
        return bestMoveAB;
        
    }
   
    //VERSION 2.0 
    //minimax method that finds the best move to make based on a evaluation function that looks 
    //x levels deep, where x is a parameter (depth) passed in. 
    
    public Node Minimax(int depth, int myTurn, HusBoardState board_state) {
    	
    	
//    	if (myTurn == 0) System.out.println("My Turn");
//    	if (myTurn == 1) System.out.println("Opponents Turn");
    	
        if(depth == 0) {
       
        	//System.out.println("Reached depth of 0");
        	//depth desired reached, we want to evaluate the score of the gameboard at this depth
        
    		
    		//run evaluation function on board. 
    		int score = (int) MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);		
			Node currentNode = new Node();
			currentNode.setScore(score);
			
        	return currentNode;
        	
        } else {
        	
        	//Depth desired not reached yet, depending on whose turn find min or max.
        	
            //Game not over so get a list of possible moves, then try each possible move. 
        	
        	ArrayList<HusMove> moves = board_state.getLegalMoves(); 
        	
        	//if (myTurn == 0) System.out.println("My Turn, find Max move from "+moves.size()+" children\n");
        	//if (myTurn == 1) System.out.println("Opponents Turn, find Min from "+moves.size()+" children\n");
        	
        	//Create node for minimax tree
			Node currentNode = new Node();
			
			//ArrayList of scores for each possible move from the root of the tree. 
    		ArrayList<Integer> scores = new ArrayList<Integer>();

        	for(int i = 0; i < moves.size(); i++){
        		        		
        		//list that will contain all the scores of curentNode's children
   
        		//clone the current board, then try the move i. 
        		HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
    			cloned_board_state.move(moves.get(i));
			
    			if(myTurn == 0) { //maximizing function
    				//System.out.println("My Turn find maximum. Depth = "+depth + " Current Move = "+i);
    				
    				//recursive call to find value at node
    				Node lowerNode = new Node();
    				
    				lowerNode = Minimax((depth-1), 1, cloned_board_state);
    				int lowerScore = lowerNode.getScore();
    				
    				scores.add(lowerScore);
    				
    				//once you are on the last possible move on that group of moves.
					int max = -100;
    				
    				if(i == (moves.size()-1)){
    					//System.out.println(scores);
    					//get max value in scores list
    					for (int j = 0; j < scores.size(); j++){
    						int current = scores.get(j);
    						if(current > max){
    							max = scores.get(j);
    							//System.out.println("Max: "+max+" is move "+ j +" at depth of "+depth+".");
    							//int value = moves.get(j).getPit();
    							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());

    	        				currentNode.setMove(moves.get(j));
    	        				currentNode.setScore(max);
    						}
    					}
    				}
    				
    			} else if (myTurn == 1) { //minimizing function
    				//System.out.println("Opponents Turn, find minimum. Depth = "+ depth+" Current Move = "+i);
    				
    				//recursive call to find value at node
    				
    				Node lowerNode = new Node();
    				
    				lowerNode = Minimax((depth-1), 0, cloned_board_state);
    				int lowerScore = lowerNode.getScore();
    				
    				scores.add(lowerScore);
    				
    				//once you are on the last possible move on that group of moves.
    				int min = 100;
    				
    				if(i == (moves.size()-1)){
    					//System.out.println(scores);
    					//get the min value in scores list
    					for(int j = 0; j < scores.size(); j++){
    						int current = scores.get(j);
    						if(current < min){
    							min = scores.get(j);
    							//System.out.println("Min: "+min+" is move "+j+" at depth of "+depth+".");
    							//int value = moves.get(j).getPit();
    							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());
    							currentNode.setMove(moves.get(j));
    							currentNode.setScore(min);
    						}
    					}
    				}	
    			}
    			
        	}
        	return currentNode;
        }       	
    }
         
    //VERSION3.0 - Alpha-Beta Pruning Version
    
    public HusMove MinimaxAB(HusBoardState board_state, int depth, double alpha, double beta){
    
    	
    	ArrayList<HusMove> moves = board_state.getLegalMoves();
    	double maxScore = 0;
    	HusMove maxMove = moves.get(0);
    	
    	for(int i = 0; i< moves.size(); i++){
    		
    		HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
    		cloned_board_state.move(moves.get(i));
    		
    		double score = MinValue(cloned_board_state, depth-1, alpha, beta);
    		
    		if (score > maxScore){
    			maxScore = score;
    			maxMove = moves.get(i);
    		}
    	}
		System.out.println("The best move: "+maxMove.getPit());

    	return maxMove;
    }

    public double MaxValue(HusBoardState board_state, int depth, double alpha, double beta){

    	if(depth ==0 || board_state.getLegalMoves() == null){
    		double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 1);	
    		return score;
    		
    	} else {
    		
    		ArrayList<HusMove> moves = board_state.getLegalMoves();
    		
    		for(int i=0; i< moves.size(); i++){
    	
    			HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
    			cloned_board_state.move(moves.get(i));
    			
    			alpha = Math.max(alpha, MinValue(cloned_board_state, depth-1, alpha, beta));
    			if (alpha >= beta) {
    				//System.out.println("MaxValue : Cutoff Occured, Alpha ("+alpha+") >= Beta ("+beta+"), Returning Beta");
    				return beta;
    			}
    			
    		}
    		return alpha;	
    	}	
    }
    
    public double MinValue(HusBoardState board_state, int depth, double alpha, double beta){
    
    	if (depth == 0){
    		double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 1);			
    		return score;
    		
    	} else {
    		
    		ArrayList<HusMove> moves = board_state.getLegalMoves();
    		
    		for(int i=0; i < moves.size(); i++){
    			
    			HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
    			cloned_board_state.move(moves.get(i));
    			beta = Math.min(beta, MaxValue(cloned_board_state, depth-1, alpha, beta));
    			
    			if (alpha >= beta) {
    				//System.out.println("Min Vaue : Cutoff Occured, Alpha ("+alpha+") >= Beta ("+beta+") , Returning Alpha");
    				return alpha;
    			}
    		}
    		return beta;
    	}
    }
    //end of StudentPlayer
}
