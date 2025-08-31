/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Category {

    private int categoryId;
    private String categoryName;
    private int capacity;
    private double pricePerNight;
    private String description;
    private String image;
    private String formattedPrice;
    private int numberOfRooms;
    private double size;
    private int bed;

    public Category() {
    }

    public Category(int categoryId, String categoryName, int capacity, double pricePerNight, String description, String image) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.image = image;
    }

    public Category(String categoryName, double pricePerNight) {
        this.categoryName = categoryName;
        this.pricePerNight = pricePerNight;
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.categoryId = CategoryId;
    }

    public String getRoomType() {
        return categoryName;
    }

    public void setRoomType(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
