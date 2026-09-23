package com.foodapp.controller;

import java.io.IOException;
import java.util.List;

import com.foodapp.daoimpl.MenuItemDAOImpl;
import com.foodapp.model.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private MenuItemDAOImpl dao = new MenuItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 PRINT ID (CHECK HERE)
        String idStr = request.getParameter("restaurantId");
        System.out.println("Restaurant ID from URL: " + idStr);

        int restaurantId = Integer.parseInt(idStr);

        // 🔥 Fetch menu items
        List<MenuItem> items = dao.getMenuByRestaurant(restaurantId);

        // 🔥 PRINT SIZE (VERY IMPORTANT)
        System.out.println("Items fetched: " + items.size());

        request.setAttribute("items", items);

        request.getRequestDispatcher("/WEB-INF/views/partials/menu.jsp")
               .forward(request, response);
    }
}