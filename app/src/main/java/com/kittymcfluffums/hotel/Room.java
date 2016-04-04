package com.kittymcfluffums.hotel;

import android.support.annotation.DrawableRes;

public class Room {
    private @DrawableRes int roomPicResource;
    private String roomType, roomDesc;

    public Room(@DrawableRes int roomPicResource, String roomType, String roomDesc) {
        this.roomPicResource = roomPicResource;
        this.roomType = roomType;
        this.roomDesc = roomDesc;
    }

    public @DrawableRes int getRoomPicResource() {
        return roomPicResource;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomDesc() {
        return roomDesc;
    }
}
