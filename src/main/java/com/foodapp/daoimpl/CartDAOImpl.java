package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.foodapp.util.DBConnection;

public class CartDAOImpl {

    public void addToCart(int userId, int menuItemId, int quantity) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO cart (user_id, menu_item_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}