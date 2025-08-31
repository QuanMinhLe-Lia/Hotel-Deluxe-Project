/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 *
 * @author Admin
 */

public class Room {
    private int id;
    private String roomNumber;
    private String RoomType;
    private Category category;
    private String status;

    public Room() {
    }

    public Room(int id, String roomNumber, Category category, String status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.category = category;
        this.status = status;
    }

    public Room(int id, String roomNumber, Category category) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public Room(int id, String roomNumber, String RoomType, Category category, String status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.RoomType = RoomType;
        this.category = category;
        this.status = status;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String RoomType) {
        this.RoomType = RoomType;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
