package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.foodapp.dao.OrderDAO;
import com.foodapp.model.Order;
import com.foodapp.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int placeOrder(Order order) {

        int generatedId = 0;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    // 👉 You can leave other methods empty for now
    public Order getOrderById(int orderId) { return null; }
    public java.util.List<Order> getOrdersByUser(int userId) { return null; }
    public java.util.List<Order> getAllOrders() { return null; }
    public boolean updateOrderStatus(int orderId, String status) { return false; }
    public boolean cancelOrder(int orderId) { return false; }
    public java.util.List<Order> getOrdersByStatus(String status) { return null; }
    public double getTotalOrderAmount(int orderId) { return 0; }
}