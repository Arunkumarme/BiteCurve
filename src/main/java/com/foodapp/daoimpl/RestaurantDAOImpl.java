package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.model.Restaurant;
import com.foodapp.util.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));

                // 🔥 IMPORTANT FIX (THIS WAS MISSING)
                r.setType(rs.getString("type"));

                list.add(r);
            }

            System.out.println("DAO fetched: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        Restaurant r = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));

                // 🔥 ALSO ADD HERE
                r.setType(rs.getString("type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));
                r.setType(rs.getString("type")); // ✅

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Restaurant> filterByRating(double minRating) {
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants WHERE rating >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, minRating);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));
                r.setType(rs.getString("type")); // ✅

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Restaurant> filterByLocation(String location) {
        return new ArrayList<>();
    }

    @Override
    public List<Restaurant> sortByRating() {
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants ORDER BY rating DESC";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));
                r.setType(rs.getString("type")); // ✅

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Restaurant> sortByName() {
        List<Restaurant> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM restaurants ORDER BY name ASC";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImageUrl(rs.getString("image_url"));
                r.setType(rs.getString("type")); // ✅
                System.out.println("TYPE: " + rs.getString("type"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean restaurantExists(int restaurantId) {
        return false;
    }
}