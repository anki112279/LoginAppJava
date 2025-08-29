package com.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String uname = request.getParameter("username");
        String pass = request.getParameter("password");

        // RDS DB details
        String jdbcURL = "";
        String dbUser = "";
        String dbPass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPass);
            
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            stmt.setString(1, uname);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               
                RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
                rd.forward(request, response);
            } else {
               
                RequestDispatcher rd = request.getRequestDispatcher("failure.jsp");
                rd.forward(request, response);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
