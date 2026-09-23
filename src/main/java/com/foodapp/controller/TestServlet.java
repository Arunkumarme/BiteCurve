package com.foodapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;
import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private RestaurantDAOImpl dao = new RestaurantDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>DB Connection Test</h1>");

        try {
            // 🔥 CHECK WHICH DB IS CONNECTED
            Connection con = DBConnection.getConnection();
            String dbName = con.getCatalog();

            out.println("<p>Connected Database: " + dbName + "</p>");
            System.out.println("Connected DB: " + dbName);

            // 🔥 FETCH DATA
            List<Restaurant> list = dao.getAllRestaurants();

            out.println("<p>Total Restaurants Found: " + list.size() + "</p>");
            System.out.println("List size: " + list.size());

            if (list.isEmpty()) {
                out.println("<h3 style='color:red;'>No data found ❌</h3>");
            } else {
                out.println("<h3 style='color:green;'>Restaurants:</h3>");
                for (Restaurant r : list) {
                    out.println("<p>" + r.getId() + " - " + r.getName() + " (Rating: " + r.getRating() + ")</p>");
                }
            }

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}