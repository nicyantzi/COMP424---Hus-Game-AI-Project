package hus;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;

/** A random Hus player. */
public class RandomHusPlayer extends HusPlayer {
    Random rand = new Random();

    public RandomHusPlayer() { super("RandomHusPlayer"); }

    
    //original random method
    
    //** Choose moves randomly. */
    public HusMove chooseMove(HusBoardState board_state)
    {
        // Pick a random move from the set of legal moves.
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        HusMove move = moves.get(rand.nextInt(moves.size()));
        return move;
    }
    
    
    
//    //LOU's method numero 1
// 
//    
//
//}



//Lou's Method Dos

//package hus;
//
//import hus.HusBoardState;
//import hus.HusPlayer;
//import hus.HusMove;
//
//import java.util.ArrayList;
//import java.util.Random;
//
///** A random Hus player. */
//public class RandomHusPlayer extends HusPlayer {
//    Random rand = new Random();
//
//    public RandomHusPlayer() { super("RandomHusPlayer"); }
//
//    /** Choose moves randomly. */
//    public HusMove chooseMove(HusBoardState board_state)
//    {
//    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//    	int depth = 5;
//    	int height = 0;
//    	int[] temp = new int[2];
//    	temp = miniMaxTopLevel(board_state, height, depth);
//    	HusMove move = moves.get(temp[0]);
//    	return move;
//    }
//
//public int[] miniMaxTopLevel(HusBoardState board_state, int height, int depth) {
//
//    	ArrayList<HusMove> legalMoves = board_state.getLegalMoves();
//    	ArrayList<Integer> scores = new ArrayList<Integer>();
//    	int turn = board_state.getTurnPlayer();
//    	
//    	int[] temp = new int[2];
//    	for (int i = 0; i < legalMoves.size(); i++) {
//
//			HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//			HusMove clone_move = legalMoves.get(i);
//			cloned_board_state.move(clone_move);
//
//			scores.add(i, miniMaxTree(turn, cloned_board_state, height+1, depth));
//			if (i == legalMoves.size() - 1) {
//				//max player's turn
//				//trying to maximize score
//				temp[0] = 0;
//				temp[1] = -10000;
//				for (int j = 0; j <scores.size(); j++) {
//					//if stored value is greater than next indexed score, keep last move
//					if (temp[1] > scores.get(j)) {
//						continue;
//					} else {
//						//stores the move which led to highest value
//						temp[0] = j;
//						//stores the score of highest value
//						temp[1] = scores.get(j);
//					}	
//				}
//			}
//    	}
//		return temp;
//    }
//
//public int miniMaxTree(int turn, HusBoardState board_state, int height, int depth) {
//    			
//    	ArrayList<HusMove> legalMoves = board_state.getLegalMoves();
//	    ArrayList<Integer> scores = new ArrayList<Integer>();
//    	
//	    while (height != depth && legalMoves.size() != 0) {		
//		    
//	    //get all possible moves from current board state
//			for (int i = 0; i < legalMoves.size(); i++) {
//
//				HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//				HusMove clone_move = legalMoves.get(i);
//				cloned_board_state.move(clone_move);
//
//				scores.add(i, miniMaxTree(turn, cloned_board_state, height+1, depth));
//				
//				if (i == legalMoves.size() - 1) {
//					//max player's turn
//					//trying to maximize score
//					if (height%2 - 1 == 0) {
//						int[] temp = new int[2];
//						temp[0] = 0;
//						temp[1] = -10000;
//						for (int j = 0; j <scores.size(); j++) {
//							//if stored value is greater than next indexed score, keep last move
//							if (temp[1] > scores.get(j)) {
//								continue;
//							} else {
//								//stores the move which led to highest value
//								temp[0] = j;
//								//stores the score of highest value
//								temp[1] = scores.get(j);
//							}	
//						}
//						return temp[1];
//					//min player's turn
//					//trying to minimize score
//					} else {
//						int[] temp = new int[2];
//						temp[0] = 0;
//						temp[1] = 10000;
//						for (int j = 0; j <scores.size(); j++) {
//							//if stored value is greater than next indexed score, keep last move
//							if (temp[1] < scores.get(j)) {
//								continue;
//							} else {
//								//stores the move which led to highest value
//								temp[0] = j;
//								//stores the score of highest value
//								temp[1] = scores.get(j);
//							}
//						}
//						return temp[1];
//					}
//				}
//			}
//	    }
//	    return eval(board_state, turn, height);
//    }
//    
//    public int eval(HusBoardState board, int player, int height) {
//    	int[][] boardPits = board.getPits();
//    	int score = 0;
//    	for (int i = 0; i < boardPits[0].length; i++){
//        	score = score + boardPits[player][i];
//        }
//        return score;
//    }
//}



//Julia's Method Uno

//    public HusMove chooseMove(HusBoardState board_state){
//
//   		HusBoardState cloned_board_state_levelA = (HusBoardState) board_state.clone();
//    	HusBoardState cloned_board_state_levelB = (HusBoardState) board_state.clone();
//    	HusBoardState cloned_board_state_levelC = (HusBoardState) board_state.clone();
//    	HusBoardState cloned_board_state_levelD = (HusBoardState) board_state.clone();
//    	HusBoardState cloned_board_state_levelE = (HusBoardState) board_state.clone();
//    	
//    	int addedSeeds = 0;
//    	ArrayList<HusMove> movesA = board_state.getLegalMoves();
//    	ArrayList<HusMove> movesB;// = board_state.getLegalMoves();
//    	ArrayList<HusMove> movesC;
//    	ArrayList<HusMove> movesD;
//    	ArrayList<HusMove> movesE;
//    	
//    	HusMove bestHusMoveC = movesA.get(0);
//    	HusMove bestHusMoveB = movesA.get(0);
//    	HusMove bestHusMoveA = movesA.get(0);
//    	HusMove bestHusMoveD = movesA.get(0);
//    	HusMove bestHusMoveE = movesA.get(0);
//    	 
//    	int maxSeedsA = 0;
//    	int minSeedsB = 92;
//    	int maxSeedsC = 0;
//    	int minSeedsD = 92;
//    	int maxSeedsE = 0;
//    	
//    	int addedSeedsA = 0;
//    	int addedSeedsB = 0;
//    	int addedSeedsC = 0;
//    	int addedSeedsD = 0;
//    	int addedSeedsE = 0;
//    	
//    	int[][] pits;
//    	int[] my_pits;
//    	int[][] testPits;
//    	int[] op_pits;
//    	
//    	for(int levelA = 0; levelA < movesA.size(); levelA ++){
//    		cloned_board_state_levelA = (HusBoardState) board_state.clone();
//    		cloned_board_state_levelA.move(movesA.get(levelA));
//    		movesB = cloned_board_state_levelA.getLegalMoves();
//    	//	System.out.print("At Player 1 move ");
//    	//	System.out.println(movesA.get(levelA).getPit());
//    		minSeedsB = 92;
//    		loopB:
//    		for(int levelB = 0;levelB < movesB.size(); levelB++){
//    			//System.out.print("At player 2 move : ");
//    			//System.out.print(movesB.get(levelB).getPit());
//    			//System.out.print(" with minSeedsB : ");
//    			//System.out.println(minSeedsB);
//    			cloned_board_state_levelB = (HusBoardState) cloned_board_state_levelA.clone();
//    			cloned_board_state_levelB.move(movesB.get(levelB));
//    			movesC = cloned_board_state_levelB.getLegalMoves();
//    			maxSeedsC = 0;
//    			//bestHusMoveC = movesB.get(levelB)
//    			loopC:
//    			for(int levelC = 0; levelC < movesC.size(); levelC++){
//    				
//        			//cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//        			//cloned_board_state_levelC.move(movesC.get(levelC));
//        			/*addedSeedsC = 0;
//        			pits = cloned_board_state_levelC.getPits();
//        			my_pits = pits[player_id];
//        	      	for(int i = 0; i < 32; i++){
//                		addedSeedsC = addedSeedsC + my_pits[i];
//                	}
//        	      	*/
//        			cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//        			cloned_board_state_levelC.move(movesC.get(levelC));
//        			movesD = cloned_board_state_levelC.getLegalMoves();
//        			minSeedsD = 92;
//        			loopD:
//        	      	for(int levelD = 0; levelD < movesD.size(); levelD++){
//
//            			cloned_board_state_levelD = (HusBoardState) cloned_board_state_levelC.clone();
//            			cloned_board_state_levelD.move(movesD.get(levelD));
//            			maxSeedsE = 0;
//            			movesE = cloned_board_state_levelD.getLegalMoves();
//            			loopE:
//            			for(int levelE = 0; levelE < movesE.size(); levelE++){
//            	      	//	System.out.print("in level E loop: ");
//            	      	//	System.out.println(maxSeedsE);
//            	      		cloned_board_state_levelE = (HusBoardState) cloned_board_state_levelD.clone();
//            	      		cloned_board_state_levelE.move(movesE.get(levelE));
//                			pits = cloned_board_state_levelE.getPits();
//                			my_pits = pits[player_id];
//                			addedSeedsE = 0;
//                	      	for(int i = 0; i < 32; i++){
//                        		addedSeedsE = addedSeedsE + my_pits[i];
//                        	}	
//                	      	if(addedSeedsE > maxSeedsE){
//                	      	//	System.out.print("found a better move 5 than ");
//                	      	//	System.out.print(maxSeedsE);
//                	      	//	System.out.print(" it gives you : ");
//                	      		//System.out.println(addedSeedsE);
//                	      		bestHusMoveE = movesE.get(levelE);
//                	      		maxSeedsE = addedSeedsE;
//                	      	}
//                	      	if(maxSeedsE >= minSeedsD){
//                	      		//System.out.println("-- breaking out of loopE");
//                	      		break loopE;
//                	      	}  
//                	      	
//            			}
//            			/*
//            			pits = cloned_board_state_levelD.getPits();
//            			my_pits = pits[player_id];
//            			
//            			addedSeedsD = 0;
//            	      	for(int i = 0; i < 32; i++){
//                    		addedSeedsD = addedSeedsD + my_pits[i];
//                    	}
//            	      	*/
//            	      	if(maxSeedsE < minSeedsD){
//            	  //    		System.out.print("found a better move 4 than ");
//            	  //    		System.out.print(minSeedsD);
//            	//      		System.out.print(" it gives you : ");
//            	//      		System.out.println(maxSeedsE);
//            	      		bestHusMoveD = movesD.get(levelD);
//            	      		minSeedsD = maxSeedsE;
//            	      	}
//            	      	if(minSeedsD <= maxSeedsC){
//            	      //		System.out.println("-- breaking out of loopD");
//            	      		break loopD;
//            	      	}  
//            	      	
//        	      	}
//        	      	
//        	      	if(minSeedsD > maxSeedsC){
//        	    //  		System.out.print("found a better move 3 than ");
//        	    //  		System.out.print(maxSeedsC);
//        	   //   		System.out.print(" it gives you : ");
//        	    //  		System.out.println(minSeedsD);
//        	      		bestHusMoveC = movesC.get(levelC);
//        	      		maxSeedsC = minSeedsD;
//        	      		
//        	      	}
//        	      	if(maxSeedsC >= minSeedsB){
//        //	      		System.out.println("-- breaking out of loopC");
//        	      		break loopC;
//        	      	}  	
//    			}	 
//    			/*pits = cloned_board_state_levelB.getPits();
//    			my_pits = pits[player_id];
//    			addedSeedsB = 0;
//    	      	for(int i = 0; i < 32; i++){
//            		addedSeedsB = addedSeedsB + my_pits[i];
//            	} 
//    	      	System.out.print(addedSeedsB);
//    	      	System.out.println(" seeds on your side");
//    	      	*/
//    			if(maxSeedsC < minSeedsB){
//    				minSeedsB = maxSeedsC;
//    				bestHusMoveB = movesB.get(levelB);
//    		//		System.out.print("-> move by player two of pit ");
//    		//		System.out.print(bestHusMoveB.getPit());
//    				//System.out.print(" with a number of beads ");
//    				//int[] op_pits = pits[opponent_id];
//    				//testPits = cloned_board_state_levelA.getPits();
//    				//op_pits = testPits[opponent_id];
//    				//System.out.print(op_pits[bestHusMoveB.getPit()]);
//    				       
//    		//		System.out.print(" after you make the move ");
//    		//		System.out.print(movesA.get(levelA).getPit());
//    		//		System.out.print(" gets you ");
//    		//		System.out.println(minSeedsB);
//    			}
//    			
//     	      	if(maxSeedsA >= minSeedsB){
//    	      //		System.out.println("-- breaking out of loopB");
//    	      		break loopB;
//    	      	}  
//    			
//    		}
//    		
//			if(minSeedsB > maxSeedsA){
//				maxSeedsA = minSeedsB;
//				bestHusMoveA = movesA.get(levelA);
//			//	System.out.print("BEST MOVE ALERT: If you play pit ");
//			//	System.out.print(bestHusMoveA.getPit());
//			//	System.out.print(" then they play ");
//			//	System.out.print(bestHusMoveB.getPit());
//			
//			///	System.out.print(" you will have ");
//			//	System.out.println(maxSeedsA);
//			}
//    	}
//    	
//		System.out.println(" next move will be ");
//		System.out.println(bestHusMoveA.getPit());
//		System.out.println(" then the move player B");
//		System.out.println(bestHusMoveB.getPit());
//		
//		System.out.println(maxSeedsA);
//		
//        return bestHusMoveA;//moves.get(chooseMin(board_state));
//    }
}
    