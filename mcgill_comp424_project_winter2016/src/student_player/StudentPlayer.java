package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;

import student_player.mytools.MyTools;
import student_player.mytree.Node;
import student_player.mytools.Timer;
import java.util.Collections;


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
    private int myPlayer;
    private int oppPlayer;


    /** This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class hus.RandomHusPlayer
     * for another example agent. */
    public HusMove chooseMove(HusBoardState board_state){

        Runtime runtime = Runtime.getRuntime();
        turnNumber = board_state.getTurnNumber();
        myPlayer = player_id;
        oppPlayer = opponent_id;

        //System.out.println("\nMy Player = "+myPlayer);
        //System.out.println("Opponenet = "+oppPlayer);

        husTimer.setEndTime(turnNumber);
        husTimer.setTimeoutMoveScore(0.0);

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
            //System.out.println("Running Minimax Depth "+depthTurn0);     
            bestMoveAB = MinimaxAB(board_state, depthTurn0, alpha, beta, 5000000000L, 5); 


        } else {        //turnNumber greater than 0. 

            double totalSeedsCurrent= MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);
            double totalPossibleSeeds = 96;
        
            double percentageTotal = totalSeedsCurrent/totalPossibleSeeds;
            //System.out.println("Percentage Total = "+percentageTotal*100);

            //MinimaxAB depths
            int depthL6 = 11;       //10
            int depthL5 = 10;       //9     working values.
            int depthL4 = 9;        //8
            int depthL3 = 8;
            int depthL2 = 8;
            int depthL1 = 7;
            int depthMain = 6;

            //greaterThanPer is actually an option to run various MCTS methods or not.
            if(percentageTotal > 0.93 || percentageTotal < 0.05){
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthL6);
                bestMoveAB = MinimaxAB(board_state, depthL6, alpha, beta, 400000000L, 5);
            } else if(percentageTotal > 0.87 || percentageTotal < 0.10){
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthL5);
                bestMoveAB = MinimaxAB(board_state, depthL5, alpha, beta, 400000000L, 5);
            } else if (percentageTotal > 0.77 || percentageTotal < 0.20){
                //System.out.println("Running MinimaxAB "+depthL4);
                MyTools.greaterThanPer = 0;
                bestMoveAB = MinimaxAB(board_state, depthL4, alpha, beta, 400000000L, 5);
            } else if (percentageTotal > 0.70 || percentageTotal < 0.30){
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthL3);         
                bestMoveAB = MinimaxAB(board_state, depthL3, alpha, beta, 370000095L, 5);
            } else if(percentageTotal > 0.54 || percentageTotal < 0.40){
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthL2);
                bestMoveAB = MinimaxAB(board_state, depthL2, alpha, beta, 300000000L, 5);
            } else if(percentageTotal >0.515 || percentageTotal <0.45){
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthL2);
                bestMoveAB = MinimaxAB(board_state, depthL1, alpha, beta, 300000000L, 5);
            } else {
                MyTools.greaterThanPer = 0;
                //System.out.println("Running MinimaxAB "+depthMain+".");
                bestMoveAB = MinimaxAB(board_state, depthMain, alpha, beta, 300000000L, 5);
            }
        }
        
        //RETURN THE CHOSEN MOVE. 

        //METHOD V1
        //return move;
        
        //METHOD V2
        //return bestMove;
        
        //METHOD V3
        
        long timeTook = husTimer.getTimeUsed();
        
        //System.out.println("Time used this turn in nanoseconds "+timeTook);


        //long freeMemory = runtime.freeMemory();

        //System.out.println("Space left = "+ freeMemory/1024);


       
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
    
    public HusMove MinimaxAB(HusBoardState board_state, int depth, double alpha, double beta, long buffer, int quiescenceDepth){
    
        //method to resort the nodes on a level such that their evaluation function results sort them in an increasing index. 
        //ArrayList<Integer> newSearchOrder = MyTools.NewSearchOrder(board_state, myPlayer, oppPlayer);
        
        //method to use a Quiescence search to determine which nodes to look at deeper.
        ArrayList<Integer> newSearchOrder = QuiescenceSearch(board_state, quiescenceDepth, alpha, beta);
        //Collections.reverse(newSearchOrder);

        ArrayList<HusMove> moves = board_state.getLegalMoves();

    	double maxScore = 0;
    	HusMove maxMove = moves.get(0);
        husTimer.setTimeoutMove(maxMove);

    	for(int i = 0; i< newSearchOrder.size(); i++){
        //for(int i = moves.size()-1; i > 0; i=i-1){
            long timeLeft = husTimer.getTimeLeft();
            //System.out.println("Time Left : "+timeLeft);
            if(timeLeft > buffer){

                long bufferStart = System.nanoTime();

                //System.out.println("Greater than buffer, looking at root child: "+i);

                HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
                cloned_board_state.move(moves.get(newSearchOrder.get(i)));
                
                double score = MinValue(cloned_board_state, depth-1, alpha, beta);
                
                if (score > maxScore){
                    maxScore = score;
                    maxMove = moves.get(newSearchOrder.get(i));
                }

                if (maxScore > (husTimer.getTimeoutMoveScore())){
                    husTimer.setTimeoutMove(maxMove);
                    husTimer.setTimeoutMoveScore(maxScore);
                }

                buffer = Math.max(System.nanoTime() - bufferStart + ((long) 0.15*(System.nanoTime() - bufferStart)), 40000000L);

            } else {
                //System.out.println("Running out of time... Returning the best move so far");
                return husTimer.getTimeoutMove();
            }
    	}
		//System.out.println("Whole Tree Built. The best move: "+maxMove.getPit());

    	return maxMove;
    }

    //method to look for quiet or noisy regions in the upper portion of the game tree
    public ArrayList<Integer> QuiescenceSearch(HusBoardState board_state, int depth, double alpha, double beta){
    
        ArrayList<HusMove> moves = board_state.getLegalMoves();

        ArrayList<Node> quiescenceResults = new ArrayList<Node>();

        for(int i = 0; i< moves.size(); i++){
            
            //System.out.println("Greater than buffer, looking at root child: "+i);

            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(moves.get(i));
            
            int score = (int) MinValue(cloned_board_state, depth-1, alpha, beta);

            Node childResult = new Node(score, i);

            quiescenceResults.add(childResult);
            
        }

        Collections.sort(quiescenceResults);

        ArrayList<Integer> scoresList = new ArrayList<Integer>();
        for(int i = 0; i < quiescenceResults.size(); i++){
            scoresList.add(quiescenceResults.get(i).getScore());
        }
        //System.out.println("Quiescence Results: "+scoresList);

        int selectionAmount = Math.min(8, quiescenceResults.size());

        ArrayList<Integer> newSearchNodes = new ArrayList<Integer>();

        for (int i = 0; i < selectionAmount; i++){
            int index = quiescenceResults.size()-1-i;
            newSearchNodes.add(quiescenceResults.get(index).getIndex());
        }

        //System.out.println("After Selection : "+ newSearchNodes);
        
        return newSearchNodes;
    }


    public double MaxValue(HusBoardState board_state, int depth, double alpha, double beta){

    	if(depth ==0 || board_state.getLegalMoves() == null){
    		double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 1);	
    		return score;
    		
    	} else {
    		
    		ArrayList<HusMove> moves = board_state.getLegalMoves();
      //       ArrayList<Integer> newSearchOrder = MyTools.NewSearchOrder(board_state, myPlayer, oppPlayer);
            //System.out.println("New Search Order: "+newSearchOrder);

    		
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
            //this has returned an asscending list. we want to reverse its direction
            // ArrayList<Integer> newSearchOrder = MyTools.NewSearchOrder(board_state, myPlayer, oppPlayer);
            // Collections.reverse(newSearchOrder);
            //System.out.println("New Search Order: "+newSearchOrder);

    		
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
