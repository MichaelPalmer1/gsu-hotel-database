package com.kittymcfluffums.hotel;

import android.support.annotation.DrawableRes;

/**
 * Room object
 */
public class Room {
    private @DrawableRes int roomPicResource;
    private String roomType, roomDesc;
    private int roomTypeId;

    /**
     * Constructor
     * @param roomPicResource Room image resource
     * @param roomType Room type
     * @param roomDesc Room description
     * @param roomTypeId Room type id
     */
    public Room(@DrawableRes int roomPicResource, String roomType, String roomDesc, int roomTypeId) {
        this.roomPicResource = roomPicResource;
        this.roomType = roomType;
        this.roomDesc = roomDesc;
        this.roomTypeId = roomTypeId;
    }

    /**
     * Get the room image resource
     * @return Room image resource
     */
    public @DrawableRes int getRoomPicResource() {
        return roomPicResource;
    }

    /**
     * Get the room type
     * @return room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Get the room description
     * @return room description
     */
    public String getRoomDesc() {
        return roomDesc;
    }

    /**
     * Get the room type id
     * @return room type id
     */
    public int getRoomTypeId() {
        return roomTypeId;
    }
}
