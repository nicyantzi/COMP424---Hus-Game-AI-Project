package student_player.mytools;

import hus.HusMove;

public class Timer {
	
	
	private long endTime;
	private HusMove failSafeMove;
	private double failSafeMoveScore;
	private long startTime;


	public Timer(){
		failSafeMoveScore = 0.0;
	}


	public void setEndTime(int option){
		startTime = System.nanoTime();

		if (option == 0) {		//turn 0
			long turnTime0 = 30000000000L;
			endTime = startTime + turnTime0;
			double timerLength = ((endTime - startTime));
			System.out.println("\nMY TURN: "+option+", "+timerLength+" second Timer created, startTime = " + startTime + ", End Time = "+endTime);

		} else {					//turn > 0
			endTime = startTime + 2000000000L;
			System.out.println("\nMY TURN: "+option+" 2 second Timer created, startTime = " + (double) startTime/1000000000 + ", End Time = "+(double)endTime/1000000000);

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

	public void setFailSafe(HusMove newMove){
		this.failSafeMove = newMove;
	}

	public void setFailSafeScore(double newScore){
		this.failSafeMoveScore = newScore;
	}

	public HusMove getFailSafe(){
		return failSafeMove;
	}

	public double getFailSafeScore(){
		return failSafeMoveScore;
	}

}
