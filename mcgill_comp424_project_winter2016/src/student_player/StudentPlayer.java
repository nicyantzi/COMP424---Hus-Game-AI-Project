package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;

import student_player.mytools.MyTools;
import student_player.mytree.Node;
import student_player.mytools.Timer;

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

    private Timer husTimer = new Timer();
    private int turnNumber;


    /** This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class hus.RandomHusPlayer
     * for another example agent. */
    public HusMove chooseMove(HusBoardState board_state){

        turnNumber = board_state.getTurnNumber();

        husTimer.setEndTime(turnNumber);
        husTimer.setFailSafeScore(0.0);

        // Get the contents of the pits so we can use it to make decisions.
//        int[][] pits = board_state.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
//    	int[] my_pits = pits[player_id];
//      int[] op_pits = pits[opponent_id];


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


		//Various Policies depending on the turn number and set of states you are in. 
		//Set of states corresponds to the percentage of seeds you have in the board.
		//Depths for Different MinimaxAB methods based on percentage of seeds. 
		

        if(turnNumber == 0){

            int depthTurn0 = 8;
            System.out.println("Running Minimax Depth "+depthTurn0);     
            bestMoveAB = MinimaxAB(board_state, depthTurn0, alpha, beta, 4000000000L); 


        } else {        //turnNumber greater than 0. 

            double totalSeedsCurrent= MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);
            double totalPossibleSeeds = 96;
        
            double percentageTotal = totalSeedsCurrent/totalPossibleSeeds;
            System.out.println("Percentage Total = "+percentageTotal);

            //MinimaxAB depths
            int depth93 = 11;
            int depth83 = 10;
            int depth73 = 9;
            int depth63 = 8;
            int depth53 = 7;
            int depthOther = 6;

            //greaterThanPer is actually an option to run various MCTS methods or not.
            if(percentageTotal > 0.93 || percentageTotal < 0.05){
                MyTools.greaterThanPer = 1;
                System.out.println("Running MinimaxAB "+depth93);
                bestMoveAB = MinimaxAB(board_state, depth93, alpha, beta, 400000000L);
            } else if(percentageTotal > 0.87 || percentageTotal < 0.10){
                MyTools.greaterThanPer = 1;
                System.out.println("Running MinimaxAB "+depth83);
                bestMoveAB = MinimaxAB(board_state, depth83, alpha, beta, 400000000L);
            } else if (percentageTotal > 0.77 || percentageTotal < 0.20){
                System.out.println("Running MinimaxAB "+depth73);
                MyTools.greaterThanPer = 1;
                bestMoveAB = MinimaxAB(board_state, depth73, alpha, beta, 400000000L);
            } else if (percentageTotal > 0.69 || percentageTotal < 0.30){
                MyTools.greaterThanPer = 1;
                System.out.println("Running MinimaxAB "+depth63);
                bestMoveAB = MinimaxAB(board_state, depth73, alpha, beta, 2700095L);
            } else if(percentageTotal > 0.53 || percentageTotal < 0.47){
                MyTools.greaterThanPer = 1;
                System.out.println("Running MinimaxAB "+depth53);
                bestMoveAB = MinimaxAB(board_state, depth53, alpha, beta, 200000000L);
            } else {
                MyTools.greaterThanPer = 0;
                System.out.println("Running MinimaxAB "+depthOther+".");
                bestMoveAB = MinimaxAB(board_state, depthOther, alpha, beta, 200000000L);
            }
        }
        
        //RETURN THE CHOSEN MOVE. 

        //METHOD V1
        //return move;
        
        //METHOD V2
        //return bestMove;
        
        //METHOD V3
        
        long timeTook = husTimer.getTimeUsed();
        
        System.out.println("Time used this turn in nanoseconds "+timeTook);
       
        return bestMoveAB;
        
    }
   
    //VERSION 2.0 
    //minimax method that finds the best move to make based on a evaluation function that looks 
    //x levels deep, where x is a parameter (depth) passed in. 
    
//     public Node Minimax(int depth, int myTurn, HusBoardState board_state) {
    	
    	
// //    	if (myTurn == 0) System.out.println("My Turn");
// //    	if (myTurn == 1) System.out.println("Opponents Turn");
    	
//         if(depth == 0) {
       
//         	//System.out.println("Reached depth of 0");
//         	//depth desired reached, we want to evaluate the score of the gameboard at this depth
        
    		
//     		//run evaluation function on board. 
//     		int score = (int) MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);		
// 			Node currentNode = new Node();
// 			currentNode.setScore(score);
			
//         	return currentNode;
        	
//         } else {
        	
//         	//Depth desired not reached yet, depending on whose turn find min or max.
        	
//             //Game not over so get a list of possible moves, then try each possible move. 
        	
//         	ArrayList<HusMove> moves = board_state.getLegalMoves(); 
        	
//         	//if (myTurn == 0) System.out.println("My Turn, find Max move from "+moves.size()+" children\n");
//         	//if (myTurn == 1) System.out.println("Opponents Turn, find Min from "+moves.size()+" children\n");
        	
//         	//Create node for minimax tree
// 			Node currentNode = new Node();
			
// 			//ArrayList of scores for each possible move from the root of the tree. 
//     		ArrayList<Integer> scores = new ArrayList<Integer>();

//         	for(int i = 0; i < moves.size(); i++){
        		        		
//         		//list that will contain all the scores of curentNode's children
   
//         		//clone the current board, then try the move i. 
//         		HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//     			cloned_board_state.move(moves.get(i));
			
//     			if(myTurn == 0) { //maximizing function
//     				//System.out.println("My Turn find maximum. Depth = "+depth + " Current Move = "+i);
    				
//     				//recursive call to find value at node
//     				Node lowerNode = new Node();
    				
//     				lowerNode = Minimax((depth-1), 1, cloned_board_state);
//     				int lowerScore = lowerNode.getScore();
    				
//     				scores.add(lowerScore);
    				
//     				//once you are on the last possible move on that group of moves.
// 					int max = -100;
    				
//     				if(i == (moves.size()-1)){
//     					//System.out.println(scores);
//     					//get max value in scores list
//     					for (int j = 0; j < scores.size(); j++){
//     						int current = scores.get(j);
//     						if(current > max){
//     							max = scores.get(j);
//     							//System.out.println("Max: "+max+" is move "+ j +" at depth of "+depth+".");
//     							//int value = moves.get(j).getPit();
//     							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());

//     	        				currentNode.setMove(moves.get(j));
//     	        				currentNode.setScore(max);
//     						}
//     					}
//     				}
    				
//     			} else if (myTurn == 1) { //minimizing function
//     				//System.out.println("Opponents Turn, find minimum. Depth = "+ depth+" Current Move = "+i);
    				
//     				//recursive call to find value at node
    				
//     				Node lowerNode = new Node();
    				
//     				lowerNode = Minimax((depth-1), 0, cloned_board_state);
//     				int lowerScore = lowerNode.getScore();
    				
//     				scores.add(lowerScore);
    				
//     				//once you are on the last possible move on that group of moves.
//     				int min = 100;
    				
//     				if(i == (moves.size()-1)){
//     					//System.out.println(scores);
//     					//get the min value in scores list
//     					for(int j = 0; j < scores.size(); j++){
//     						int current = scores.get(j);
//     						if(current < min){
//     							min = scores.get(j);
//     							//System.out.println("Min: "+min+" is move "+j+" at depth of "+depth+".");
//     							//int value = moves.get(j).getPit();
//     							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());
//     							currentNode.setMove(moves.get(j));
//     							currentNode.setScore(min);
//     						}
//     					}
//     				}	
//     			}
    			
//         	}
//         	return currentNode;
//         }       	
//     }
         
    //VERSION3.0 - Alpha-Beta Pruning Version
    
    public HusMove MinimaxAB(HusBoardState board_state, int depth, double alpha, double beta, long buffer){
    
    	
    	ArrayList<HusMove> moves = board_state.getLegalMoves();
    	double maxScore = 0;
    	HusMove maxMove = moves.get(0);
        husTimer.setFailSafe(maxMove);

    	for(int i = 0; i< moves.size(); i++){
        //for(int i = moves.size()-1; i > 0; i=i-1){
            long timeLeft = husTimer.getTimeLeft();
            //System.out.println("Time Left : "+timeLeft);
            if(timeLeft > buffer){

                //System.out.println("Greater than buffer, looking at root child: "+i);

                HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
                cloned_board_state.move(moves.get(i));
                
                double score = MinValue(cloned_board_state, depth-1, alpha, beta);
                
                if (score > maxScore){
                    maxScore = score;
                    maxMove = moves.get(i);
                }

                if (maxScore > (husTimer.getFailSafeScore())){
                    husTimer.setFailSafe(maxMove);
                    husTimer.setFailSafeScore(maxScore);
                }
            } else {
                System.out.println("Running out of time... Returning the best move so far");
                return husTimer.getFailSafe();
            }
    	}
		System.out.println("Whole Tree Built. The best move: "+maxMove.getPit());

    	return maxMove;
    }

    public double MaxValue(HusBoardState board_state, int depth, double alpha, double beta){

    	if(depth ==0 || board_state.getLegalMoves() == null){
    		double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 1);	
    		return score;
    		
    	} else {
    		
    		ArrayList<HusMove> moves = board_state.getLegalMoves();
    		
    		for(int i=0; i< moves.size(); i++){
            //for(int i = moves.size()-1; i > 0; i=i-1){
    	
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
            //for(int i = moves.size()-1; i > 0; i=i-1){    
    			
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
