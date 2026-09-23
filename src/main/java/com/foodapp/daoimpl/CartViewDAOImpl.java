package com.foodapp.daoimpl;

import java.sql.*;
import java.util.*;

import com.foodapp.model.CartItem;
import com.foodapp.util.DBConnection;

public class CartViewDAOImpl {

    public List<CartItem> getCartItems(int userId) {

        List<CartItem> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT m.id, m.name, m.price, c.quantity " +
                         "FROM cart c JOIN menu_items m ON c.menu_item_id = m.id " +
                         "WHERE c.user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                CartItem item = new CartItem(id, name, price, quantity);

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}