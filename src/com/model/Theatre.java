package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theatre {
	
	public Theatre(List<TheatreRow> theatreRow){
		this.theatreRow=theatreRow;
	}
	
	private List<TheatreRow> theatreRow;

	public List<TheatreRow> getTheatreRow() {
		return theatreRow;
	}

	public void setTheatreRow(List<TheatreRow> theatreRow) {
		this.theatreRow = theatreRow;
	}
	
	public int getMaxAvailableSectionCapacity(){
		List<TheatreSection> theatreSections=new ArrayList<TheatreSection>();
		for(TheatreRow theatreRow:getTheatreRow()){
			for(TheatreSection theatreSection:theatreRow.getSectionList()){
				theatreSections.add(theatreSection);
			}
		}
		Collections.sort(theatreSections);
		return theatreSections.get(0).getOpenSeat();
	}

}
