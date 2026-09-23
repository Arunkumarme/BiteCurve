package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.MenuItem;

public interface MenuItemDAO {

    List<MenuItem> getMenuByRestaurant(int restaurantId);

    MenuItem getItemById(int itemId);

    List<MenuItem> searchItems(String keyword);

    List<MenuItem> filterByPrice(double minPrice, double maxPrice);

    List<MenuItem> filterByAvailability(boolean isAvailable);

    List<MenuItem> filterByRestaurant(int restaurantId);

    List<MenuItem> sortByPriceAsc();

    List<MenuItem> sortByPriceDesc();

    List<MenuItem> getAvailableItems();

    boolean itemExists(int itemId);
}