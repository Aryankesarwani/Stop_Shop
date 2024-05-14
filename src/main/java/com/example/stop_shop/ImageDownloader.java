package com.example.stop_shop;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class ImageDownloader {

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stopandshop";
    private static final String USER = "root";
    private static final String PASS = "Aryan@123";

    // Directory to save images
    private static final String IMAGE_SAVE_DIRECTORY = "C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images";

    public static void main(String[] args) {
        createDirectory(IMAGE_SAVE_DIRECTORY);
        fetchAndSaveProductImages();
    }

    private static void createDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static void fetchAndSaveProductImages() {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query to fetch product image links
            stmt = conn.createStatement();
            String sql = "SELECT product_id, product_img FROM products";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set and save images
            while (rs.next()) {
                String productId = rs.getString("product_id");
                String imageUrl = rs.getString("product_img");
                //saveImage(productId, imageUrl);
                updatePath(productId, conn);
            }

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                // Nothing we can do
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void saveImage(String productId, String imageUrl) {
        BufferedInputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(imageUrl);
            in = new BufferedInputStream(url.openStream());
            String imagePath = IMAGE_SAVE_DIRECTORY + File.separator + productId + ".jpg";
            out = new FileOutputStream(imagePath);

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Saved image for product " + productId);
        } catch (IOException e) {
            System.out.println("Failed to download image for product " + productId + ": " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private  static void updatePath(String product_id, Connection conn){
        PreparedStatement psmt =  null;
        String imagePath = IMAGE_SAVE_DIRECTORY + File.separator + product_id + ".jpg";
        String sql = "UPDATE products SET product_img = ? WHERE product_id = ?";
        try {
            psmt  = conn.prepareStatement(sql);
            psmt.setString(1, imagePath);  // Set the product image path
            psmt.setString(2, product_id);
            int rowsAffected = psmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("No row Affected"+e.getMessage());
        }
    }
}

