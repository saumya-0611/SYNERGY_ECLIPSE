package com.smartstreetlight.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Servlet implementation for Maintenance Page
 */
@WebServlet("/MaintenanceServlet")
public class MaintenanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/smart_light?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"; // Adjust your DB URL
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = "root"; // Replace with your DB password

    public MaintenanceServlet() {
        super();
    }

    /**
     * Handles GET requests to fetch maintenance details
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Connect to the database and retrieve data
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT street_name, incharge_name, power_consumption, light_intensity, abnormality, malfunction_alert " +
                         "FROM maintenance_details WHERE incharge_email = ?";
            String inchargeEmail = request.getParameter("email"); // Email of the logged-in admin

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inchargeEmail);
            ResultSet rs = pstmt.executeQuery();

            // Generate HTML dynamically for table
            StringBuilder htmlResponse = new StringBuilder("<html><head><title>Maintenance Page</title></head><body>");
            htmlResponse.append("<h1>Maintenance Details</h1>");
            htmlResponse.append("<table border='1'><tr><th>Street Name</th><th>In-charge</th><th>Power Consumption</th><th>Light Intensity</th><th>Abnormality</th><th>Malfunction Alert</th><th>Notify</th></tr>");

            while (rs.next()) {
                String streetName = rs.getString("street_name");
                String inchargeName = rs.getString("incharge_name");
                String powerConsumption = rs.getString("power_consumption");
                String lightIntensity = rs.getString("light_intensity");
                String abnormality = rs.getString("abnormality");
                String malfunctionAlert = rs.getString("malfunction_alert");

                htmlResponse.append("<tr>");
                htmlResponse.append("<td>").append(streetName).append("</td>");
                htmlResponse.append("<td>").append(inchargeName).append("</td>");
                htmlResponse.append("<td>").append(powerConsumption).append("</td>");
                htmlResponse.append("<td>").append(lightIntensity).append("</td>");
                htmlResponse.append("<td>").append(abnormality).append("</td>");
                htmlResponse.append("<td>").append(malfunctionAlert).append("</td>");
                htmlResponse.append("<td><button onclick=\"alert('Notification sent to ").append(inchargeName).append("')\">Send Notification</button></td>");
                htmlResponse.append("</tr>");
            }

            htmlResponse.append("</table></body></html>");
            response.getWriter().write(htmlResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<p>Error retrieving maintenance details: " + e.getMessage() + "</p>");
        }
    }

    /**
     * Handles POST requests to schedule maintenance or other tasks
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example: Handle maintenance scheduling (future expansion)
        response.getWriter().write("<p>POST request handled successfully.</p>");
    }
}
