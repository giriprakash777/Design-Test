package com.model;

import java.util.List;

public class TheatreRow {
	
	private int rowId;
	
	private List<TheatreSection> sectionList;

	public List<TheatreSection> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<TheatreSection> sectionList) {
		this.sectionList = sectionList;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
}
