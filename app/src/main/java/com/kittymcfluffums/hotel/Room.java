package com.kittymcfluffums.hotel;

public class Room {
	private int roomPicResource;
	private String roomType, roomDesc;

	public Room(int roomPicResource, String roomType, String roomDesc) {
		this.roomPicResource = roomPicResource;
		this.roomType = roomType;
		this.roomDesc = roomDesc;
	}

	public int getRoomPicResource() {
		return roomPicResource;
	}

	public String getRoomType() {
		return roomType;
	}

	public String getRoomDesc() {
		return roomDesc;
	}
}
