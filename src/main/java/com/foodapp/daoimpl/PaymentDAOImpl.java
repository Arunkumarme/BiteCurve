package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.PaymentDAO;
import com.foodapp.model.Payment;
import com.foodapp.util.DBConnection;

public class PaymentDAOImpl implements PaymentDAO {

    // 🔥 SQL CONSTANTS
    private static final String INSERT =
            "INSERT INTO payments(order_id, payment_method, payment_status, amount) VALUES (?, ?, ?, ?)";

    private static final String GET_BY_ORDER =
            "SELECT * FROM payments WHERE order_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM payments WHERE payment_id=?";

    private static final String GET_ALL =
            "SELECT * FROM payments";

    private static final String GET_BY_STATUS =
            "SELECT * FROM payments WHERE payment_status=?";

    private static final String UPDATE_STATUS =
            "UPDATE payments SET payment_status=? WHERE payment_id=?";

    private static final String TOTAL_BY_USER =
            "SELECT SUM(amount) FROM payments p JOIN orders o ON p.order_id = o.order_id WHERE o.user_id=?";

    // ✅ Process Payment
    @Override
    public boolean processPayment(Payment payment) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getPaymentStatus());
            ps.setDouble(4, payment.getAmount());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get Payment by Order
    @Override
    public Payment getPaymentByOrder(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Get Payment by ID
    @Override
    public Payment getPaymentById(int paymentId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, paymentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Get All Payments
    @Override
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapPayment(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Get Payments by Status
    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        List<Payment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_STATUS)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapPayment(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Update Status
    @Override
    public boolean updatePaymentStatus(int paymentId, String status) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {

            ps.setString(1, status);
            ps.setInt(2, paymentId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Refund Payment
    @Override
    public boolean refundPayment(int paymentId) {
        return updatePaymentStatus(paymentId, "REFUNDED");
    }

    // ✅ Total Payments by User
    @Override
    public double getTotalPaymentsByUser(int userId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(TOTAL_BY_USER)) {

            ps.setInt(1, userId);

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
    private Payment mapPayment(ResultSet rs) throws Exception {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("payment_id"));
        p.setOrderId(rs.getInt("order_id"));
        p.setPaymentMethod(rs.getString("payment_method"));
        p.setPaymentStatus(rs.getString("payment_status"));
        p.setAmount(rs.getDouble("amount"));
        p.setPaymentTime(rs.getTimestamp("payment_time"));
        return p;
    }
}