package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.OrderItemDAO;
import com.foodapp.model.OrderItem;
import com.foodapp.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    // 🔥 SQL CONSTANTS
    private static final String INSERT =
            "INSERT INTO order_items(order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

    private static final String GET_BY_ORDER =
            "SELECT * FROM order_items WHERE order_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM order_items WHERE order_item_id=?";

    private static final String UPDATE =
            "UPDATE order_items SET quantity=?, price=? WHERE order_item_id=?";

    private static final String DELETE =
            "DELETE FROM order_items WHERE order_item_id=?";

    private static final String TOTAL_AMOUNT =
            "SELECT SUM(quantity * price) FROM order_items WHERE order_id=?";

    // ✅ Add Single Item
    @Override
    public boolean addOrderItem(OrderItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getItemId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Add Multiple Items
    @Override
    public boolean addMultipleItems(List<OrderItem> items) {
        boolean status = true;

        for (OrderItem item : items) {
            if (!addOrderItem(item)) {
                status = false;
            }
        }

        return status;
    }

    // ✅ Get Items by Order
    @Override
    public List<OrderItem> getItemsByOrder(int orderId) {
        List<OrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrderItem(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Get by ID
    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrderItem(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Update Item
    @Override
    public boolean updateOrderItem(OrderItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, item.getQuantity());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getOrderItemId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Remove Item
    @Override
    public boolean removeOrderItem(int orderItemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, orderItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Total Amount
    @Override
    public double getTotalAmountByOrder(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(TOTAL_AMOUNT)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 🔥 Mapping
    private OrderItem mapOrderItem(ResultSet rs) throws Exception {
        OrderItem item = new OrderItem();
        item.setOrderItemId(rs.getInt("order_item_id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setItemId(rs.getInt("item_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getDouble("price"));
        return item;
    }
}