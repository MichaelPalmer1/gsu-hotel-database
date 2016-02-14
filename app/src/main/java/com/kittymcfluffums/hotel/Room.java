package com.kittymcfluffums.hotel;

public class Room {
	private String roomType, roomDesc;

	public Room(String roomType, String roomDesc) {
		this.roomType = roomType;
		this.roomDesc = roomDesc;
	}

	public String getRoomType() {
		return roomType;
	}

	public String getRoomDesc() {
		return roomDesc;
	}
}
