package com.foodapp.controller;

import java.io.IOException;
import java.util.List;

import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private RestaurantDAOImpl dao = new RestaurantDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Restaurant> list = dao.getAllRestaurants();

        request.setAttribute("restaurants", list);

        request.getRequestDispatcher("/WEB-INF/views/partials/home.jsp").forward(request, response);
    }
}