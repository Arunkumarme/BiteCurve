package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Cart;

public interface CartDAO {

    boolean addToCart(Cart cart);

    boolean updateCart(Cart cart);

    boolean updateQuantity(int cartId, int quantity);

    boolean removeFromCart(int cartId);

    boolean removeItemByUser(int userId, int itemId);

    List<Cart> getCartByUser(int userId);

    Cart getCartItem(int userId, int itemId);

    boolean clearCart(int userId);

    int getCartItemCount(int userId);

    double getTotalAmount(int userId);
}