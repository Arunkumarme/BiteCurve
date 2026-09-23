package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Payment;

public interface PaymentDAO {

    boolean processPayment(Payment payment);

    Payment getPaymentByOrder(int orderId);

    Payment getPaymentById(int paymentId);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByStatus(String status);

    boolean updatePaymentStatus(int paymentId, String status);

    boolean refundPayment(int paymentId);

    double getTotalPaymentsByUser(int userId);
}