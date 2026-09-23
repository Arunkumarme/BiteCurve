package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.MenuItemDAO;
import com.foodapp.model.MenuItem;
import com.foodapp.util.DBConnection;

public class MenuItemDAOImpl implements MenuItemDAO {

    // ✅ Get Menu by Restaurant (MAIN FIX)
    @Override
    public List<MenuItem> getMenuByRestaurant(int restaurantId) {

        List<MenuItem> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM menu_items WHERE restaurant_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MenuItem item = new MenuItem();

                // 🔥 IMPORTANT: match DB column names EXACTLY
                item.setItemId(rs.getInt("id"));
                item.setRestaurantId(rs.getInt("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));

                // 🔥 VERY IMPORTANT (for veg/non-veg filter)
                item.setType(rs.getString("type"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ------------------- BELOW METHODS SAFE DEFAULT -------------------

    @Override
    public MenuItem getItemById(int itemId) {
        return null;
    }

    @Override
    public List<MenuItem> searchItems(String keyword) {
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> filterByPrice(double minPrice, double maxPrice) {
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> filterByAvailability(boolean isAvailable) {
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> filterByRestaurant(int restaurantId) {
        return getMenuByRestaurant(restaurantId);
    }

    @Override
    public List<MenuItem> sortByPriceAsc() {
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> sortByPriceDesc() {
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> getAvailableItems() {
        return new ArrayList<>();
    }

    @Override
    public boolean itemExists(int itemId) {
        return false;
    }
}