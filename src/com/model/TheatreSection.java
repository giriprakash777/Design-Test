package com.model;

/**
 * @author Giri Prakash
 *
 */
public class TheatreSection implements Comparable<TheatreSection>{
	
	private int openSeat;

	public int getOpenSeat() {
		return openSeat;
	}

	public void setOpenSeat(int openSeat) {
		this.openSeat = openSeat;
	}

	/**
	 * sort sections in decending order
	 */
	public int compareTo(TheatreSection theatreSection) {
		return theatreSection.openSeat-this.openSeat;
	}
	

}
