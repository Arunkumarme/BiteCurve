package com.foodapp.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.model.CartItem;
import com.google.gson.Gson;

@WebServlet("/cart-data")
public class CartDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        response.setContentType("application/json");

        Gson gson = new Gson();
        String json = gson.toJson(cart);

        response.getWriter().write(json);
    }
}