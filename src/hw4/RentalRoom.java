package hw4;

import java.util.Random;

public class RentalRoom {
	
	private Room  room;
	private Suite suite;
	private boolean suiteUpgrade; // indicates whether the room is a regular room or a suite upgrade
	private int numNights;
	private final int ROOM_THRESHOLD = 199;
	private final int ROOM_NUM_MIN = 1;
	private final int ROOM_NUM_MAX = 999;

	public RentalRoom(String roomType, boolean suiteUpgrade, int numNights){		
		this.suiteUpgrade = suiteUpgrade;
		this.numNights = numNights;
		
		/** 
		 * Assign a random room number based on a given range between 1-999. 
		 * The range represents the room numbers and is determined by the room type set by the user.
		 ***/
		int roomNumber = 0;
		if (roomType.equals("s")){
			roomNumber = getRand(ROOM_NUM_MIN,ROOM_THRESHOLD);
		}
		else if (roomType.equals("u")){
			roomNumber = getRand(ROOM_THRESHOLD,ROOM_NUM_MAX);			
		}
		
		if (suiteUpgrade){
			suite = new Suite(roomNumber);
		}
		else {
			room = new Room(roomNumber);
		}		
	}
	
	public final int getRoomNumber(){
		return (suiteUpgrade) ? suite.getNumber() : room.getNumber();
	}
	
	public final int getRand(int min, int max){ // called once during object construction
	    return new Random().nextInt((max - min) + 1) + min;
	}
	
	public Double getTotal(){
		return numNights * ((suiteUpgrade) ? suite.getRate() : room.getRate());
	}
	
	public Double getRate(){
		return (suiteUpgrade) ? suite.getRate() : room.getRate();
	}
}
