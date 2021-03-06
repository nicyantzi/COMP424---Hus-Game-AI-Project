package hus;

import hus.HusBoardState;
import hus.HusPlayer;
import student_player.mytools.MyTools;
import student_player.nodes.Nodes;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;

/****************************************
*                                       *
*   Nic Yantzi - 260467234              *
*   Hus Project                         *
*   COMP424 - Artificial Intelligence   *
*                                       *                           
/***************************************/


/** A random Hus player. */
public class RandomHusPlayer extends HusPlayer {
    Random rand = new Random();

    public RandomHusPlayer() { super("RandomHusPlayer"); }
    
public HusMove chooseMove(HusBoardState board_state){
        
        long startTime = System.currentTimeMillis();
        
        int turnNumber = board_state.getTurnNumber();

        //System.out.println("\nOpponents Turn "+player_id+"Turn Number: "+turnNumber);

        /////////////////////////////////////////////
        
        //V3 Minimax with Alpha Beta Pruning Using Monte Carlo Tree Search with
        //Varying depths and rollouts based on positioning in game
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        
        int alpha = -10000;
        int beta = 10000;
        
        HusMove bestMoveAB = moves.get(0);
        
        double totalSeedsCurrent= MyTools.EvaluationFunction(board_state, player_id, opponent_id, 0);
        double totalPossibleSeeds = 96;
        
        double percentageTotal = totalSeedsCurrent/totalPossibleSeeds;
        //System.out.println("Percentage Total = "+percentageTotal);
        

        //Various Policies depending on the set of states you are in. 
        //Set of states corresponds to the percentage of seeds you have in the board.
        //Depths for Different MinimaxAB methods based on percentage of seeds. 
            
        //MinimaxAB depths
        int depthL5 = 8;
        int depthL4 = 8;
        int depthL3 = 8;
        int depthL2 = 7;
        int depthMain = 6;

        //greaterThanPer is actually an option to run various MCTS methods or not.

        if(percentageTotal > 0.87 || percentageTotal < 0.10){
            MyTools.greaterThanPer = 0;
            //System.out.println("Running MinimaxAB "+depthL5);
            bestMoveAB = MinimaxAB(board_state, depthL5, alpha, beta);
        } else if (percentageTotal > 0.77 || percentageTotal < 0.20){
            //System.out.println("Running MinimaxAB "+depthL4);
            MyTools.greaterThanPer = 0;
            bestMoveAB = MinimaxAB(board_state, depthL4, alpha, beta);
        } else if (percentageTotal > 0.70 || percentageTotal < 0.30){
            //System.out.println("Running MinimaxAB "+depthL3);
            MyTools.greaterThanPer = 0;
            bestMoveAB = MinimaxAB(board_state, depthL3, alpha, beta);
        } else if(percentageTotal > 0.54 || percentageTotal < 0.46){
            MyTools.greaterThanPer = 0;
            //System.out.println("Running MinimaxAB "+depthL2);
            bestMoveAB = MinimaxAB(board_state, depthL2, alpha, beta);
        } else {
            MyTools.greaterThanPer = 0;
            //System.out.println("Running MinimaxAB "+depthMain+".");
            bestMoveAB = MinimaxAB(board_state, depthMain, alpha, beta);
        }
    
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
            double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 2);  //passing 2, as i want the opponents
            return score;                                                                       //evaluation function to differ possibly
            
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
            double score = MyTools.EvaluationFunction(board_state, player_id, opponent_id, 2);          
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
    //end of RandomHusPlayer
}
    