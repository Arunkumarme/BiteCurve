package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Restaurant;

public interface RestaurantDAO {

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(int restaurantId);

    List<Restaurant> searchRestaurants(String keyword);

    List<Restaurant> filterByRating(double minRating);

    List<Restaurant> filterByLocation(String location);

    List<Restaurant> sortByRating();

    List<Restaurant> sortByName();

    boolean restaurantExists(int restaurantId);
}