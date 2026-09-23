package com.foodapp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 2 * 1024 * 1024,
        maxRequestSize = 4 * 1024 * 1024
)
public class ProfileServlet extends HttpServlet {

    private static final String PROFILE_UPLOAD_PATH = "/uploads/profiles";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("login");
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT name, email, phone, address FROM users WHERE id=?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    req.setAttribute("name", rs.getString("name"));
                    req.setAttribute("email", rs.getString("email"));
                    req.setAttribute("phone", rs.getString("phone"));
                    req.setAttribute("address", rs.getString("address"));
                } else {
                    session.invalidate();
                    resp.sendRedirect("login");
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to load profile. Please try again.");
        }

        req.setAttribute("profileImage", findProfileImage(req, userId));
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("login");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String password = req.getParameter("password");

        boolean changePassword = password != null && !password.trim().isEmpty();
        String sql = changePassword
                ? "UPDATE users SET name=?, email=?, phone=?, address=?, password=? WHERE id=?"
                : "UPDATE users SET name=?, email=?, phone=?, address=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);

            if (changePassword) {
                ps.setString(5, password);
                ps.setInt(6, userId);
            } else {
                ps.setInt(5, userId);
            }

            int rows = ps.executeUpdate();

            if (rows > 0) {
                session.setAttribute("user", name);

                String imageError = saveProfileImage(req, userId);
                if (imageError == null) {
                    req.setAttribute("success", "Profile updated successfully.");
                } else {
                    req.setAttribute("error", imageError);
                }
            } else {
                req.setAttribute("error", "Profile was not updated.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to update profile. Please check your details.");
        }

        doGet(req, resp);
    }

    private String saveProfileImage(HttpServletRequest req, int userId)
            throws IOException, ServletException {

        Part imagePart = req.getPart("profileImage");

        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        String extension = getAllowedExtension(imagePart.getSubmittedFileName());
        if (extension == null) {
            return "Please upload a JPG, PNG, WEBP, or GIF image.";
        }

        File uploadDir = getProfileUploadDirectory();
        if (uploadDir == null) {
            return "Profile image upload is not available in this deployment.";
        }

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return "Unable to create the profile image folder.";
        }

        deleteExistingProfileImages(uploadDir, userId);

        File imageFile = new File(uploadDir, "user-" + userId + "." + extension);

        try (InputStream input = imagePart.getInputStream()) {
            Files.copy(input, imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return null;
    }

    private String findProfileImage(HttpServletRequest req, int userId) {
        File uploadDir = getProfileUploadDirectory();

        if (uploadDir == null || !uploadDir.exists()) {
            return null;
        }

        File[] matches = uploadDir.listFiles((dir, name) ->
                name.startsWith("user-" + userId + "."));

        if (matches == null || matches.length == 0) {
            return null;
        }

        return req.getContextPath() + PROFILE_UPLOAD_PATH + "/" + matches[0].getName();
    }

    private File getProfileUploadDirectory() {
        String realPath = getServletContext().getRealPath(PROFILE_UPLOAD_PATH);
        return realPath == null ? null : new File(realPath);
    }

    private void deleteExistingProfileImages(File uploadDir, int userId) {
        File[] matches = uploadDir.listFiles((dir, name) ->
                name.startsWith("user-" + userId + "."));

        if (matches == null) {
            return;
        }

        for (File match : matches) {
            match.delete();
        }
    }

    private String getAllowedExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return null;
        }

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg", "png", "webp", "gif" -> extension;
            default -> null;
        };
    }
}
