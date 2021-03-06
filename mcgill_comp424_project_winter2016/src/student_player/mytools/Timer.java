package student_player.mytools;

import hus.HusMove;


/****************************************
*										*
*	Nic Yantzi - 260467234				*
*	Hus Project 						*
*	COMP424 - Artificial Intelligence	*
*										*							
/***************************************/


public class Timer {
	
	
	private long endTime;
	private HusMove timeoutMove;
	private double timeoutMoveScore;
	private long startTime;


	public Timer(){
		timeoutMoveScore = 0.0;
	}


	public void setEndTime(int option){
		startTime = System.nanoTime();

		if (option == 0) {		//turn 0
			long turnTime0 = 30000000000L;
			endTime = startTime + turnTime0;
			double timerLength = ((endTime - startTime));
			//System.out.println("\nMY TURN: "+option+", "+timerLength+" second Timer created, startTime = " + startTime + ", End Time = "+endTime);

		} else {					//turn > 0
			endTime = startTime + 2000000000L;
			//System.out.println("\nMY TURN: "+option+" 2 second Timer created, startTime = " + (double) startTime/1000000000 + ", End Time = "+(double)endTime/1000000000);

		}

	}


	public long getCurrentTime(){
		return System.nanoTime();
	}

	public long getEndTime(){
		return endTime;
	}

	public long getTimeUsed(){
		return System.nanoTime() - startTime;
	}

	public long getTimeLeft(){
		return endTime - System.nanoTime();

	}

	//Fail Safe Move, Get and Set Methods

	public void setTimeoutMove(HusMove newMove){
		this.timeoutMove = newMove;
	}

	public void setTimeoutMoveScore(double newScore){
		this.timeoutMoveScore = newScore;
	}

	public HusMove getTimeoutMove(){
		return timeoutMove;
	}

	public double getTimeoutMoveScore(){
		return timeoutMoveScore;
	}

}
