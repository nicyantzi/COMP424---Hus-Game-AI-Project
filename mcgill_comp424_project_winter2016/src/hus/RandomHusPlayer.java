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
//    /** Choose moves randomly. */
//    public HusMove chooseMove(HusBoardState board_state)
//    {
//        // Pick a random move from the set of legal moves.
//        ArrayList<HusMove> moves = board_state.getLegalMoves();
//        HusMove move = moves.get(rand.nextInt(moves.size()));
//        return move;
//    }
    
    
    
    //LOU's method numero 1
    
    public HusMove chooseMove(HusBoardState board_state)
    {
    	HusMove move = minimax(board_state);
    	return move;
    }
    
    public HusMove minimax(HusBoardState board_state) {
    	int[][] pits = board_state.getPits();
    	int[] my_pits = pits[player_id];
        int[] op_pits = pits[opponent_id];
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        
        int[] no_seeds = {0,0};
        Boolean my_turn = true;
        
        for (int i = 0; i < moves.size(); i++) {
        	 HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
        	 HusMove clone_move = moves.get(i);
             cloned_board_state.move(clone_move);
             int[][] clone_pits = cloned_board_state.getPits();
             int[] clone_my_pits = clone_pits[player_id];
             //int[] clone_op_pits = clone_pits[opponent_id];
             int tmp_seeds = 0;
             
             for (int a=0; a<32; a++) {
            	 if (my_turn) {
            		 tmp_seeds = tmp_seeds + clone_my_pits[a];
            	 } //else {
            		// tmp_seeds =+ clone_op_pits[a];
            	// }
             }
		     if (tmp_seeds > no_seeds[1]) {
		   		 no_seeds[0] = i;
		   		 no_seeds[1] = tmp_seeds;
		     }
        }
        
        HusMove move = moves.get(no_seeds[0]);
        
    	return move;
    }
}
