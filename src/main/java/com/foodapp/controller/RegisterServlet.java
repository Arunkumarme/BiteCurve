package com.foodapp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/register.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users(name,email,password,phone,address) VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);

            int rows = ps.executeUpdate();

            System.out.println("Inserted rows: " + rows); // 🔥 DEBUG

            resp.sendRedirect("login");  // 🔥 MUST REDIRECT

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("ERROR: " + e.getMessage()); // show error instead of blank
        }
    }
}