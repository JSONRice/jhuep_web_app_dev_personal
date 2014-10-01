package hw4_part1.main;

public class Suite extends Room {

	/**
	 * @desc Requires a room number and adds a $40 surcharge to 
	 * the regular hotel room rate, which again is based 
	 * on the room number
	 * 
	 * @param roomNum
	 */
	public Suite(int roomNum){
		super(roomNum);
		rate += 40;
	}
}
