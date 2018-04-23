package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.model.OrderBook;
import com.model.Theatre;
import com.model.TheatreRow;
import com.model.TheatreSection;
import com.model.UserRequest;


/**
 * @author Giri Prakash
 *
 */
public class TheatreMaintenance {

	private static Theatre theatre;

	private static OrderBook orderBook;

	public static void main(String[] args) {		
		//start reading input
		String line;
		StringBuilder theatrelayout = new StringBuilder();
		StringBuilder userRequests = new StringBuilder();
		boolean isLayoutProcessed = false;
		System.out.println("Please enter Theater Layout and User requests and then enter 'done'.\n");
		Scanner input = new Scanner(System.in);

		while((line = input.nextLine()) != null && !line.equals("done")){            
			if(line.length() == 0){                
				isLayoutProcessed = true;
				continue;                
			}            
			if(!isLayoutProcessed){                
				theatrelayout.append(line + System.lineSeparator());                
			}else{                
				userRequests.append(line + System.lineSeparator());                
			}            
		}
		input.close();  
		//end reading input

		//begin processing requests
		try{			
			int totalOpenSeats=getTheatreLayout(theatrelayout.toString());
			getUserRequestList(userRequests.toString());
			processRequests(totalOpenSeats);
			printOutput();
		}catch(Exception e){
			e.printStackTrace();
		}
		//end processing requests
	}

	/**
	 * prints the output in Desired Format
	 */
	private static void printOutput(){
		for(UserRequest UserRequest:getOrderBook().getUserRequestList()){
			if(UserRequest.isSeatsAllocated()){
				System.out.println(UserRequest.getName()+ " Row "+UserRequest.getRowId()+" Section "+UserRequest.getSectionId() );
			}else{
				System.out.println(UserRequest.getName()+ " "+UserRequest.getUserMessage());
			}			
		}
	}


	/**
	 * TheatreLayout contains Row as Key and Each row contains List of Sections
	 * method generates the layout from the input
	 */
	private static int getTheatreLayout(String layout){	
		String[] rows = layout.split(System.lineSeparator());
		String[] sections;
		List<TheatreRow> theatreRowList = new ArrayList<TheatreRow>();
		int openSeats=0;
		int index=0;	    
		for(String row:rows){
			TheatreRow theatreRow=new TheatreRow();
			index++;	    	
			sections = row.split(" ");	
			List<TheatreSection> theatreSectionList = new ArrayList<TheatreSection>();
			for(String sect:sections){
				TheatreSection section=new TheatreSection();
				section.setOpenSeat(Integer.valueOf(sect));
				openSeats+=section.getOpenSeat();
				theatreSectionList.add(section);
			}	    	
			theatreRow.setRowId(index);
			theatreRow.setSectionList(theatreSectionList);
			theatreRowList.add(theatreRow);
		}
		setTheatre(new Theatre(theatreRowList));
		return openSeats;
	}

	/**
	 * maps UserRequest to model so as to process individual requests
	 */
	private static void getUserRequestList(String userRequest){
		List<UserRequest> userRequestList=new ArrayList<UserRequest>();
		String[] rows = userRequest.split(System.lineSeparator());
		String[] userRequests;
		for(String row:rows){
			userRequests = row.split(" ");
			UserRequest request=new UserRequest();
			request.setName(userRequests[0]);
			request.setRequestedSeats(Integer.parseInt(userRequests[1]));
			userRequestList.add(request);
		}		
		setOrderBook(new OrderBook(userRequestList));
	}

	/**
	 * @param theatreLayoutMap
	 * @param listOfUserRequest
	 * perfoms the below functions
	 *  1) first checks if we can handle request based on capacity
	 *  2) if section=requestedSeats then assign section
	 *  3) if section.openSeats<requestedSeats then continue to next section
	 *  4) if seciton.openSeats>requestedSeats then
	 *  	4.a) check whether we can split the section among users
	 *  	4.b) if there are any other users with requestedSeat=(openSeats-requestedSeat) then assign to that user
	 */	
	private static void processRequests(int totalOpenSeats){
		for(UserRequest userRequest:getOrderBook().getUserRequestList()){			
			for(TheatreRow theatreRow:getTheatre().getTheatreRow()){
				List<TheatreSection> theatreSections=theatreRow.getSectionList();
				if(!canHandleRequest(userRequest,totalOpenSeats)){
					break;
				}
				for(int section=0;section<theatreSections.size();section++){
					if(theatreSections.get(section).getOpenSeat()==userRequest.getRequestedSeats()){
						userRequest.setSeatsAllocated(true);
						userRequest.setRowId(theatreRow.getRowId());
						userRequest.setSectionId(section+1);
						totalOpenSeats-=userRequest.getRequestedSeats();
						theatreSections.get(section).setOpenSeat(0);
						break;
					}else if(theatreSections.get(section).getOpenSeat()<userRequest.getRequestedSeats()){
						continue;
					}else if(theatreSections.get(section).getOpenSeat()>userRequest.getRequestedSeats()){						
						if(getOrderBook().canSplitSectionAmongUserRequest(theatreSections.get(section).getOpenSeat()-userRequest.getRequestedSeats(),theatreRow.getRowId())){
							int openSeats=theatreSections.get(section).getOpenSeat();
							userRequest.setSeatsAllocated(true);
							userRequest.setRowId(theatreRow.getRowId());
							userRequest.setSectionId(section+1);
							totalOpenSeats-=userRequest.getRequestedSeats();
							theatreSections.get(section).setOpenSeat(openSeats-userRequest.getRequestedSeats());
							break;
						}
					}
				}
				if(userRequest.isSeatsAllocated())
					break;
			}
			userRequest.setRequestProcessed(true);
		}		
	}

	/**
	 * returns a boolean based on below conditions
	 *  1) if requestedSeat>maxAvailableSeats - cant handle party
	 *  2) if requestedSeat>maxAvailableSectionCapacity - split party
	 */
	private static boolean canHandleRequest(UserRequest userRequest,int totalAvailableSeats){
		if(userRequest.getRequestedSeats()>totalAvailableSeats){
			userRequest.setUserMessage("Sorry, we can't handle your party.");
			return false;
		}else if(userRequest.getRequestedSeats()>getTheatre().getMaxAvailableSectionCapacity()){
			userRequest.setUserMessage("Call to split party.");
			return false;
		}
		return true;		
	}
	
	

	public static OrderBook getOrderBook() {
		return orderBook;
	}

	public static void setOrderBook(OrderBook orderBook) {
		TheatreMaintenance.orderBook = orderBook;
	}

	public static Theatre getTheatre() {
		return theatre;
	}

	public static void setTheatre(Theatre theatre) {
		TheatreMaintenance.theatre = theatre;
	}

}