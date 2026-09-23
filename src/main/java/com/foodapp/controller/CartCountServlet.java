package com.foodapp.controller;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart-count")
public class CartCountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        List cart = (List) session.getAttribute("cart");

        int count = (cart == null) ? 0 : cart.size();

        response.getWriter().print(count);
    }
}