package com.model;

import java.util.List;

public class OrderBook {
	
	public OrderBook(List<UserRequest> userRequestList){
		this.userRequestList = userRequestList;
	}
	
	private List<UserRequest> userRequestList;

	public List<UserRequest> getUserRequestList() {
		return userRequestList;
	}

	public void setUserRequestList(List<UserRequest> userRequestList) {
		this.userRequestList = userRequestList;
	}
	
	public boolean canSplitSectionAmongUserRequest(int capacity,int rowId){
		if(null!=getUserRequestByCapacity(capacity, rowId)){
			return true;
		}		
		return false;
	}
	
	private UserRequest getUserRequestByCapacity(int capacity, int rowId){
		for(UserRequest userRequest:getUserRequestList()){
			if(userRequest.getRequestedSeats()==capacity && !userRequest.isRequestProcessed() 
					&& userRequest.getRowId()!=rowId){
				return userRequest;
			}
		}		
		return null;
	}

}
