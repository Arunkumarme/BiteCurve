package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.TrackingDAO;
import com.foodapp.model.Tracking;
import com.foodapp.util.DBConnection;

public class TrackingDAOImpl implements TrackingDAO {

    // 🔥 SQL CONSTANTS
    private static final String INSERT =
            "INSERT INTO tracking(order_id, current_status) VALUES (?, ?)";

    private static final String GET_BY_ORDER =
            "SELECT * FROM tracking WHERE order_id=?";

    private static final String UPDATE_STATUS =
            "UPDATE tracking SET current_status=? WHERE order_id=?";

    private static final String GET_HISTORY =
            "SELECT * FROM tracking WHERE order_id=? ORDER BY updated_at ASC";

    private static final String GET_STATUS =
            "SELECT current_status FROM tracking WHERE order_id=?";

    // ✅ Add Tracking
    @Override
    public boolean addTracking(Tracking tracking) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, tracking.getOrderId());
            ps.setString(2, tracking.getCurrentStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get Tracking by Order
    @Override
    public Tracking getTrackingByOrder(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTracking(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Update Status
    @Override
    public boolean updateTrackingStatus(int orderId, String status) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get Tracking History
    @Override
    public List<Tracking> getTrackingHistory(int orderId) {
        List<Tracking> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_HISTORY)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTracking(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Get Current Status
    @Override
    public String getCurrentStatus(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_STATUS)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Exists Check
    @Override
    public boolean trackingExists(int orderId) {
        return getTrackingByOrder(orderId) != null;
    }

    // 🔥 Mapping
    private Tracking mapTracking(ResultSet rs) throws Exception {
        Tracking t = new Tracking();
        t.setTrackingId(rs.getInt("tracking_id"));
        t.setOrderId(rs.getInt("order_id"));
        t.setCurrentStatus(rs.getString("current_status"));
        t.setUpdatedAt(rs.getTimestamp("updated_at"));
        return t;
    }
}