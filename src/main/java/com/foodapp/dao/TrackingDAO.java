package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Tracking;

public interface TrackingDAO {

    Tracking getTrackingByOrder(int orderId);

    boolean updateTrackingStatus(int orderId, String status);

    boolean addTracking(Tracking tracking);

    List<Tracking> getTrackingHistory(int orderId);

    String getCurrentStatus(int orderId);

    boolean trackingExists(int orderId);
}