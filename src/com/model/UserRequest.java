package com.model;

/**
 * @author Giri Prakash
 *
 */
public class UserRequest {
	
	private String name;
	
	private int requestedSeats;
	
	private boolean seatsAllocated;
	
	private boolean requestProcessed;
	
	private String userMessage;
	
	private int rowId;
	
	private int sectionId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRequestedSeats() {
		return requestedSeats;
	}

	public void setRequestedSeats(int requestedSeats) {
		this.requestedSeats = requestedSeats;
	}

	public boolean isSeatsAllocated() {
		return seatsAllocated;
	}

	public void setSeatsAllocated(boolean seatsAllocated) {
		this.seatsAllocated = seatsAllocated;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public boolean isRequestProcessed() {
		return requestProcessed;
	}

	public void setRequestProcessed(boolean requestProcessed) {
		this.requestProcessed = requestProcessed;
	}
	

}
