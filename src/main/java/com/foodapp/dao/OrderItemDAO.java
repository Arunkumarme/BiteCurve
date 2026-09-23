package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.OrderItem;

public interface OrderItemDAO {

    boolean addOrderItem(OrderItem item);

    boolean addMultipleItems(List<OrderItem> items);

    List<OrderItem> getItemsByOrder(int orderId);

    OrderItem getOrderItemById(int orderItemId);

    boolean updateOrderItem(OrderItem item);

    boolean removeOrderItem(int orderItemId);

    double getTotalAmountByOrder(int orderId);
}