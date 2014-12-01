package hw4_part1.main;

public class Room {

	protected int number;
	protected double rate;
	
	private final Double LOWER_RATE = 109.95;
	private final Double UPPER_RATE = 169.95 ;
	private final int ROOM_THRESHOLD = 199;
	
	public final int getNumber() {
		return number;
	}

	public final double getRate() {
		return rate;
	}

	/**
	 * @desc The constructor sets the room rate based on the room number; 
	 * rooms equal to or below the ROOM_THRESHOLD are set at the LOWER_RATE per night, and 
	 * rooms above the ROOM_THRESHOLD are set at the UPPER_RATE per night.
	 * 
	 * @param number
	 */
	public Room(int number){
		this.number = number;	
		if (number <= 0)
			this.number = 1;
		this.rate = (number <= ROOM_THRESHOLD) ? LOWER_RATE : UPPER_RATE;
	}
	
	/**
	 * @desc empty constructor sets default values
	 */
	public Room() {
		number = 1;
		rate = 109.95;
	}

}
