package com.foodapp.model;

public class MenuItem {

    private int itemId;
    private int restaurantId;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private String foodType;
    private String type;   // VEG or NON_VEG
    // ✅ ITEM ID
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    // ✅ RESTAURANT ID
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    // ✅ NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ✅ DESCRIPTION
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ✅ PRICE
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ✅ AVAILABLE
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}