package com.foodapp.controller;

import java.io.IOException;
import java.sql.*;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {

    // 🔹 OPEN LOGIN PAGE
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/partials/login.jsp")
           .forward(req, resp);
    }

    // 🔹 HANDLE LOGIN
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println("Login Attempt: " + email);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // ✅ FIXED SESSION (IMPORTANT)
                HttpSession session = req.getSession();

                session.setAttribute("user", rs.getString("name"));   // for UI
                session.setAttribute("userId", rs.getInt("id"));      // 🔥 REQUIRED FOR ORDERS

                resp.sendRedirect("home");

            } else {

                System.out.println("Login Failed");

                req.setAttribute("error", "Invalid Email or Password");
                req.getRequestDispatcher("/WEB-INF/views/partials/login.jsp")
                   .forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();

            resp.setContentType("text/html");
            resp.getWriter().println("<h3 style='color:red;'>ERROR: " + e.getMessage() + "</h3>");
        }
    }
}
