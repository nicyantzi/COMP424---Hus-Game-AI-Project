package hus;

import hus.HusBoardState;
import hus.HusPlayer;
import student_player.mytools.MyTools;
import student_player.nodes.Nodes;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;

/** A random Hus player. */
public class RandomHusPlayer extends HusPlayer {
    Random rand = new Random();

    public RandomHusPlayer() { super("RandomHusPlayer"); }
    
public HusMove chooseMove(HusBoardState board_state)
    {
        
        long startTime = System.currentTimeMillis();
        
        int turnNumber = board_state.getTurnNumber();

        System.out.println("\n\nOpponents Turn "+opponent_id+"\nTurn Number: "+turnNumber+"\n");
        // System.out.println("Player_Id = "+player_id);
        // System.out.println("Opponent_Id = "+opponent_id);

        // Use code stored in ``mytools`` package.
        MyTools.getSomething();
        
        // Get the legal moves for the current board state.
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        
        //BASIC VERSION THAT WAS GIVEN. 
        HusMove move = moves.get(0);

        
        /////////////////////////////////////////////
        
        //V3 Minimax with Alpha Beta Pruning Using Monte Carlo Tree Search with
        //Varying depths and rollouts based on positioning in game

        
        int alpha = -10000;
        int beta = 10000;
        
        HusMove bestMoveAB = moves.get(0);
        
        double totalSeedsCurrent= MyTools.EvaluationFunction(board_state, opponent_id, player_id, 0);
        double totalPossibleSeeds = 96;
        
        double percentageTotal = totalSeedsCurrent/totalPossibleSeeds;
        //System.out.println("Percentage Total = "+percentageTotal);
        

        //Various Policies depending on the set of states you are in. 
        //Set of states corresponds to the percentage of seeds you have in the board.
        //Depths for Different MinimaxAB methods based on percentage of seeds. 
        int depth89 = 5;
        int depth79 = 4;
        int depth69 = 3;
        int depth59 = 2;
        int depthOther = 6;

        // if (percentageTotal > 0.89){
        //     System.out.println("Running MinimaxAB with MCTS at Depth "+depth89+". Currently have more than 90% of the seeds.");
        //     MyTools.greaterThanPer = 1;
        //     bestMoveAB = MinimaxAB(board_state, depth89, alpha, beta);
        // } else if( percentageTotal > 0.79){
        //     System.out.println("Running MinimaxAB with MCTS at Depth "+depth79+". Currently have more than 80% of the seeds.");
        //     MyTools.greaterThanPer = 2;
        //     bestMoveAB = MinimaxAB(board_state, depth79, alpha, beta);  
        // } else if (percentageTotal > 0.69){
        //     System.out.println("Running MinimaxAB with MCTS at Depth "+depth69+". Currently have more than 70% of the seeds.");
        //     MyTools.greaterThanPer = 3;
        //     bestMoveAB = MinimaxAB(board_state, depth69, alpha, beta);
        if(percentageTotal > 0){
            MyTools.greaterThanPer = 0;
            //System.out.println("Running Minimax AB at Depth "+depth59+". Currently have more than 60% of the seeds.");
            bestMoveAB = MinimaxAB(board_state, depth59, alpha, beta);  
        } else {
            MyTools.greaterThanPer = 0;
            //System.out.println("Running Minimax AB at Depth "+depthOther+".");
            bestMoveAB = MinimaxAB(board_state, depthOther, alpha, beta);
        }

        
        //METHOD V3
        
        long endTime = System.currentTimeMillis();
        
        long totalTime = endTime - startTime;
        //System.out.println("Total Time (in milliseconds) for this turn: "+totalTime); 
        
        return bestMoveAB;
        
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
        //System.out.println("The best move: "+maxMove.getPit());

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

    
    
   //LOU's Method
    
    
//    public HusMove chooseMove(HusBoardState board_state)
//    {
//    	
//    	/*
//    	createTree(id);
//    	Integer idFirstPlayer = board_state.firstPlayer();
//    	String firstPlayer = idFirstPlayer.toString();
//    	
//    	Node<String> root = new Node<String>(firstPlayer);
//    	*/
//    	int turn = board_state.getTurnPlayer();
//    	if (turn == 0) {
//	    	int depth = 6;
//	    	int height = 0;
//	    	
//	    	
//	    	//abPruning using Nodes instead of arrayList
//	    	int min = -10000;
//	    	int max = 10000;
//	    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//	    	Nodes root = new Nodes(board_state);
//	    	
//	    	int temp = -10000;
//	    	root = abPruning(root, turn, depth, min, max);
//	    	HusMove move = null;
//	    	
//	    	for (int i=0; i<root.numChildren(); i++) {
//	    		if (temp < root.getChildAt(i).getScore()) {
//	    			temp = root.getChildAt(i).getScore();
//	    			move = moves.get(i);
//	    		}
//	    	}
//	    	return move;
//    	} else {
//    		int depth = 5;
//	    	int height = 0;
//	    	
//	    	
//	    	//abPruning using Nodes instead of arrayList
//	    	int min = -10000;
//	    	int max = 10000;
//	    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//	    	Nodes root = new Nodes(board_state);
//	    	
//	    	int temp = -10000;
//	    	root = abPruning(root, turn, depth, min, max);
//	    	HusMove move = null;
//	    	
//	    	for (int i=0; i<root.numChildren(); i++) {
//	    		if (temp < root.getChildAt(i).getScore()) {
//	    			temp = root.getChildAt(i).getScore();
//	    			move = moves.get(i);
//	    		}
//	    	}
//	    	return move;
//    	}
//    	
//    	/*
//    	//alpha beta pruning
//    	int min = -10000;
//    	int max = 10000;
//    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//    	Node root = new Node(board_state);
//    	int turn = board_state.getTurnPlayer();
//    	int[] temp = aBPTopLevel(root, turn, depth, min, max);
//    	HusMove move = moves.get(temp[0]);
//    	return move;
//    	*/
//    	/*
//    	 * Monte Carlo Search
//    	int turn = board_state.getTurnPlayer();
//    	Node n = new Node(board_state);
//    	n = monteCarlo(n, depth, turn);
//    	double[] temp = new double[2];
//    	temp[1] = 0.0;
//    	
//    	for (int i = 0; i < n.numChildren(); i++) {
//    		if (temp[1] < n.getChildAt(i).getWinLoss()) {
//    			temp[1] = n.getChildAt(i).getWinLoss();
//    			temp[0] = i;
//    		}
//    	}
//    	ArrayList<HusMove> moves = n.getBoard().getLegalMoves();
//    	HusMove move = moves.get((int) temp[0]);
//    	return move;
//    	*/
//    }
//    
//    public Nodes abPruning(Nodes n, int turn, int depth, int min, int max) {
//    	ArrayList<HusMove> legalMoves = n.getBoard().getLegalMoves();
//    	if (depth == 0 || legalMoves.size() == 0) {
//    		n.setScore(evaluate(n, turn));
//    		return n;
//    	}
//    	//maximize turn
//    	if (turn == n.getBoard().getTurnPlayer()) {
//    		n.setScore(min);
//    		
//    		for (int i = 0; i < legalMoves.size(); i++) {
//    			HusBoardState cloned_board_state = (HusBoardState) n.getBoard().clone();
//    			HusMove clone_move = n.getBoard().getLegalMoves().get(i);
//    			cloned_board_state.move(clone_move);
//    			
//    			Nodes child = new Nodes(cloned_board_state);
//        		n.addChild(abPruning(child, turn, depth-1, n.getScore(), max));
//        		//n.setMove(i);
//        		
//        		if (n.getChildAt(i).getScore() > n.getScore()) {
//        			n.setScore(n.getChildAt(i).getScore());
//        		} 
//        		if (n.getScore() >= max) {
//        			n.setScore(max);
//        			return n;
//        		}
//        	}
//    		return n;
//    	//minimize turn
//    	} else {
//    		n.setScore(max);
//    		
//    		for(int i = 0; i < legalMoves.size(); i++) {
//    			HusBoardState cloned_board_state = (HusBoardState) n.getBoard().clone();
//    			HusMove clone_move = n.getBoard().getLegalMoves().get(i);
//    			cloned_board_state.move(clone_move);
//    			
//    			Nodes child = new Nodes(cloned_board_state);
//    			n.addChild(abPruning(child, turn, depth-1, min, n.getScore()));
//    			
//    			if (n.getChildAt(i).getScore() < n.getScore()) {
//    				n.setScore(n.getChildAt(i).getScore());
//    			}
//    			if (n.getScore() <= min) {
//    				n.setScore(min);
//    				return n;
//    			}
//    		}
//    		return n;
//    	}
//    	
//    }
//    
//    public int evaluate(Nodes n, int turn) {
//    	int seeds = 0;
//    	int[][] noSeeds = n.getBoard().getPits();
//    	for (int i = 0; i<noSeeds[turn].length; i++) {
//    		seeds = seeds + noSeeds[turn][i];
//    	}
//    	return seeds;
//    }



// //Julia's Method Uno

//     public HusMove chooseMove(HusBoardState board_state){

//    		HusBoardState cloned_board_state_levelA = (HusBoardState) board_state.clone();
//     	HusBoardState cloned_board_state_levelB = (HusBoardState) board_state.clone();
//     	HusBoardState cloned_board_state_levelC = (HusBoardState) board_state.clone();
//     	HusBoardState cloned_board_state_levelD = (HusBoardState) board_state.clone();
//     	HusBoardState cloned_board_state_levelE = (HusBoardState) board_state.clone();
    	
//     	int addedSeeds = 0;
//     	ArrayList<HusMove> movesA = board_state.getLegalMoves();
//     	ArrayList<HusMove> movesB;// = board_state.getLegalMoves();
//     	ArrayList<HusMove> movesC;
//     	ArrayList<HusMove> movesD;
//     	ArrayList<HusMove> movesE;
    	
//     	HusMove bestHusMoveC = movesA.get(0);
//     	HusMove bestHusMoveB = movesA.get(0);
//     	HusMove bestHusMoveA = movesA.get(0);
//     	HusMove bestHusMoveD = movesA.get(0);
//     	HusMove bestHusMoveE = movesA.get(0);
    	 
//     	int maxSeedsA = 0;
//     	int minSeedsB = 92;
//     	int maxSeedsC = 0;
//     	int minSeedsD = 92;
//     	int maxSeedsE = 0;
    	
//     	int addedSeedsA = 0;
//     	int addedSeedsB = 0;
//     	int addedSeedsC = 0;
//     	int addedSeedsD = 0;
//     	int addedSeedsE = 0;
    	
//     	int[][] pits;
//     	int[] my_pits;
//     	int[][] testPits;
//     	int[] op_pits;
    	
//     	for(int levelA = 0; levelA < movesA.size(); levelA ++){
//     		cloned_board_state_levelA = (HusBoardState) board_state.clone();
//     		cloned_board_state_levelA.move(movesA.get(levelA));
//     		movesB = cloned_board_state_levelA.getLegalMoves();
//     	//	System.out.print("At Player 1 move ");
//     	//	System.out.println(movesA.get(levelA).getPit());
//     		minSeedsB = 92;
//     		loopB:
//     		for(int levelB = 0;levelB < movesB.size(); levelB++){
//     			//System.out.print("At player 2 move : ");
//     			//System.out.print(movesB.get(levelB).getPit());
//     			//System.out.print(" with minSeedsB : ");
//     			//System.out.println(minSeedsB);
//     			cloned_board_state_levelB = (HusBoardState) cloned_board_state_levelA.clone();
//     			cloned_board_state_levelB.move(movesB.get(levelB));
//     			movesC = cloned_board_state_levelB.getLegalMoves();
//     			maxSeedsC = 0;
//     			//bestHusMoveC = movesB.get(levelB)
//     			loopC:
//     			for(int levelC = 0; levelC < movesC.size(); levelC++){
    				
//         			//cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//         			//cloned_board_state_levelC.move(movesC.get(levelC));
//         			/*addedSeedsC = 0;
//         			pits = cloned_board_state_levelC.getPits();
//         			my_pits = pits[player_id];
//         	      	for(int i = 0; i < 32; i++){
//                 		addedSeedsC = addedSeedsC + my_pits[i];
//                 	}
//         	      	*/
//         			cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//         			cloned_board_state_levelC.move(movesC.get(levelC));
//         			movesD = cloned_board_state_levelC.getLegalMoves();
//         			minSeedsD = 92;
//         			loopD:
//         	      	for(int levelD = 0; levelD < movesD.size(); levelD++){

//             			cloned_board_state_levelD = (HusBoardState) cloned_board_state_levelC.clone();
//             			cloned_board_state_levelD.move(movesD.get(levelD));
//             			maxSeedsE = 0;
//             			movesE = cloned_board_state_levelD.getLegalMoves();
//             			loopE:
//             			for(int levelE = 0; levelE < movesE.size(); levelE++){
//             	      	//	System.out.print("in level E loop: ");
//             	      	//	System.out.println(maxSeedsE);
//             	      		cloned_board_state_levelE = (HusBoardState) cloned_board_state_levelD.clone();
//             	      		cloned_board_state_levelE.move(movesE.get(levelE));
//                 			pits = cloned_board_state_levelE.getPits();
//                 			my_pits = pits[player_id];
//                 			addedSeedsE = 0;
//                 	      	for(int i = 0; i < 32; i++){
//                         		addedSeedsE = addedSeedsE + my_pits[i];
//                         	}	
//                 	      	if(addedSeedsE > maxSeedsE){
//                 	      	//	System.out.print("found a better move 5 than ");
//                 	      	//	System.out.print(maxSeedsE);
//                 	      	//	System.out.print(" it gives you : ");
//                 	      		//System.out.println(addedSeedsE);
//                 	      		bestHusMoveE = movesE.get(levelE);
//                 	      		maxSeedsE = addedSeedsE;
//                 	      	}
//                 	      	if(maxSeedsE >= minSeedsD){
//                 	      		//System.out.println("-- breaking out of loopE");
//                 	      		break loopE;
//                 	      	}  
                	      	
//             			}
//             			/*
//             			pits = cloned_board_state_levelD.getPits();
//             			my_pits = pits[player_id];
            			
//             			addedSeedsD = 0;
//             	      	for(int i = 0; i < 32; i++){
//                     		addedSeedsD = addedSeedsD + my_pits[i];
//                     	}
//             	      	*/
//             	      	if(maxSeedsE < minSeedsD){
//             	  //    		System.out.print("found a better move 4 than ");
//             	  //    		System.out.print(minSeedsD);
//             	//      		System.out.print(" it gives you : ");
//             	//      		System.out.println(maxSeedsE);
//             	      		bestHusMoveD = movesD.get(levelD);
//             	      		minSeedsD = maxSeedsE;
//             	      	}
//             	      	if(minSeedsD <= maxSeedsC){
//             	      //		System.out.println("-- breaking out of loopD");
//             	      		break loopD;
//             	      	}  
            	      	
//         	      	}
        	      	
//         	      	if(minSeedsD > maxSeedsC){
//         	    //  		System.out.print("found a better move 3 than ");
//         	    //  		System.out.print(maxSeedsC);
//         	   //   		System.out.print(" it gives you : ");
//         	    //  		System.out.println(minSeedsD);
//         	      		bestHusMoveC = movesC.get(levelC);
//         	      		maxSeedsC = minSeedsD;
        	      		
//         	      	}
//         	      	if(maxSeedsC >= minSeedsB){
//         //	      		System.out.println("-- breaking out of loopC");
//         	      		break loopC;
//         	      	}  	
//     			}	 
//     			/*pits = cloned_board_state_levelB.getPits();
//     			my_pits = pits[player_id];
//     			addedSeedsB = 0;
//     	      	for(int i = 0; i < 32; i++){
//             		addedSeedsB = addedSeedsB + my_pits[i];
//             	} 
//     	      	System.out.print(addedSeedsB);
//     	      	System.out.println(" seeds on your side");
//     	      	*/
//     			if(maxSeedsC < minSeedsB){
//     				minSeedsB = maxSeedsC;
//     				bestHusMoveB = movesB.get(levelB);
//     		//		System.out.print("-> move by player two of pit ");
//     		//		System.out.print(bestHusMoveB.getPit());
//     				//System.out.print(" with a number of beads ");
//     				//int[] op_pits = pits[opponent_id];
//     				//testPits = cloned_board_state_levelA.getPits();
//     				//op_pits = testPits[opponent_id];
//     				//System.out.print(op_pits[bestHusMoveB.getPit()]);
    				       
//     		//		System.out.print(" after you make the move ");
//     		//		System.out.print(movesA.get(levelA).getPit());
//     		//		System.out.print(" gets you ");
//     		//		System.out.println(minSeedsB);
//     			}
    			
//      	      	if(maxSeedsA >= minSeedsB){
//     	      //		System.out.println("-- breaking out of loopB");
//     	      		break loopB;
//     	      	}  
    			
//     		}
    		
// 			if(minSeedsB > maxSeedsA){
// 				maxSeedsA = minSeedsB;
// 				bestHusMoveA = movesA.get(levelA);
// 			//	System.out.print("BEST MOVE ALERT: If you play pit ");
// 			//	System.out.print(bestHusMoveA.getPit());
// 			//	System.out.print(" then they play ");
// 			//	System.out.print(bestHusMoveB.getPit());
			
// 			///	System.out.print(" you will have ");
// 			//	System.out.println(maxSeedsA);
// 			}
//     	}
    	
// 		//System.out.println(" next move will be ");
// 		//System.out.println(bestHusMoveA.getPit());
// 		//System.out.println(" then the move player B");
// 		//System.out.println(bestHusMoveB.getPit());
		
// 		//System.out.println(maxSeedsA);
		
//         return bestHusMoveA;//moves.get(chooseMin(board_state));
//     }
}
    