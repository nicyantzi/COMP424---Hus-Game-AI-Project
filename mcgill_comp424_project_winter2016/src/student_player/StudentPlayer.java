package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;

import student_player.mytools.MyTools;
import student_player.mytree.Node;

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
        // Get the contents of the pits so we can use it to make decisions.
//        int[][] pits = board_state.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
//        int[] my_pits = pits[player_id];
//        int[] op_pits = pits[opponent_id];
        
        System.out.println("Player_Id = "+player_id);

        // Use code stored in ``mytools`` package.
        MyTools.getSomething();
        

        // Get the legal moves for the current board state.
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        
        //BASIC VERSION THAT WAS GIVEN. 
        HusMove move = moves.get(0);
        
        
        //System.out.println("Running minimax method...");
        /////////////////////////////////////////////
        //V1
        
        //move = MinimaxDecision(moves, board_state);
        
        /////////////////////////////////////////////
        //V2
        
        Node bestNode = Minimax(3, 0, board_state);
        
        HusMove bestMove = bestNode.getMove();
        
        //System.out.println("Move Chosen = " +bestMove.getPit());
        
        
        //will return bestMove. 
       
        
        ////////////////////////////////////////////
        //Code Given

        // We can see the effects of a move like this...
        HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
        cloned_board_state.move(move);

        // But since this is a placeholder algorithm, we won't act on that information.
       
        
        //return move;
        
        return bestMove;
    }
    
    
    //VERSION 1.0 
    //method that finds the best move to make based on a evaluation function that looks only one level deep. 
    
    public HusMove MinimaxDecision(ArrayList<HusMove> moves, HusBoardState board_state){
		
    	//for each legal operator o, apply the opperator o and obtain the new game state s. 
    	//value[o] = MinixmaxValue(s)
    	//Return the operator with the highest value Value[o].
    	
    	HusMove move = moves.get(0);
    	HusMove maxMove = moves.get(0);
    	
    	//Seed Counters for each possible move. 
    	int seedsAdded = 0;
    	int seedsMax = 0;
    	
    	//each moves.get(i) represents a legal move. 
    	for(int i = 0; i < moves.size(); i++){
    		move = moves.get(i);
    		HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
    		
    		cloned_board_state.move(move);
    		
    		//Find the number of seeds in each pit on the game board. 
    		int[][] currentPits = cloned_board_state.getPits();
    		
    		//Find the number of seeds in my side
    		int[] myPits = currentPits[player_id];
    		
    		
    		//Loop over the pits to check the total number of seeds in the pits. 
    		for (int j = 0; j < 32; j++){
    			seedsAdded = seedsAdded + myPits[j];
    			
    			
    		}
    		if (seedsAdded > seedsMax){
    			seedsMax = seedsAdded;
    			maxMove = moves.get(i);
    		}
    		seedsAdded = 0;
    	}
    	return maxMove;
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
        	
        	//evaluation function - currently only looking at the number of seeds on my side. 
        	//afterwards create evaluation function method that will pass current gameboard and evaluate it. 
        	
        	int seedsTotal = 0;
        	
        	int[][] currentPits = board_state.getPits();
    		int[] myPits = currentPits[player_id];
			
			
			for(int i = 0; i < 32; i++){
				seedsTotal = seedsTotal + myPits[i];
			}
		
			//System.out.println("Evaluation function: Seeds total = " +seedsTotal);
			
			Node currentNode = new Node();
			
			currentNode.setScore(seedsTotal);
			
			
        	return currentNode;
        	
        	
        } else {
        	
        	//Depth desired not reached yet, depending on whose turn find min or max.
        	
            //Game not over so get a list of possible moves, then try each possible move. 
        	
        	ArrayList<HusMove> moves = board_state.getLegalMoves(); 
        	
        	if (myTurn == 0) System.out.println("My Turn, find Max move from "+moves.size()+" children\n");
        	if (myTurn == 1) System.out.println("Opponents Turn, find Min from "+moves.size()+" children\n");
        	
        	//Create node for minimax tree
        	
			Node currentNode = new Node();
			
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
    					System.out.println(scores);
    					//get max value in scores list
    					for (int j = 0; j < scores.size(); j++){
    						int current = scores.get(j);
    						if(current > max){
    							max = scores.get(j);
    							System.out.println("Max: "+max+" is move "+ j +" at depth of "+depth+".");
    							//System.out.println("");
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
    					System.out.println(scores);
    					//get the min value in scores list
    					for(int j = 0; j < scores.size(); j++){
    						int current = scores.get(j);
    						if(current < min){
    							min = scores.get(j);
    							System.out.println("Min: "+min+" is move "+j+" at depth of "+depth+".");
    							//System.out.println("");
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
    
    
    //end of StudentPlayer
}
