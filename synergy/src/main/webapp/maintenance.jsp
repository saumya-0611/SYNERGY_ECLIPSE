<%@ page import="java.sql.*" %>
<%@ page import="com.smartstreetlight.utils.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Maintenance Dashboard</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Maintenance Dashboard</h1>
    <table>
        <tr>
            <th>Street Name</th>
            <th>Power Consumption</th>
            <th>Average Light Intensity</th>
            <th>Abnormality Detected</th>
            <th>Malfunction Alert</th>
            <th>Incharge</th>
        </tr>
        <%
            try (Connection connection = DatabaseConnection.initializeDatabase()) {
                String query = "SELECT * FROM maintenance";
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("street_name") %></td>
            <td><%= rs.getDouble("power_consumption") %></td>
            <td><%= rs.getDouble("avg_light_intensity") %></td>
            <td><%= rs.getBoolean("abnormality_detected") %></td>
            <td><%= rs.getBoolean("malfunction_alert") %></td>
            <td><%= rs.getString("incharge_name") %></td>
        </tr>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </table>
</body>
</html>
