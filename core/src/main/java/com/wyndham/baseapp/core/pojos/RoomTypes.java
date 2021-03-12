package com.wyndham.baseapp.core.pojos;

import java.util.List;

public class RoomTypes {
	private String roomType;
	private List<String> roomCategory;
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public List<String> getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(List<String> roomCategory) {
		this.roomCategory = roomCategory;
	}
}
