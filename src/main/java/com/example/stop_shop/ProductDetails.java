package com.example.stop_shop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetails {

    static Database_Connection dbcon = new Database_Connection();

    public static void displayProductDetails(Stage primaryStage, Scene rootscene, String prodName, String prodPrice, String prodImg, String username) {
        System.out.println("I am in the product details page");

        // Create a VBox for the left side (product image)
        VBox leftBox = new VBox(20);
        leftBox.setPadding(new Insets(20));
        leftBox.setAlignment(Pos.CENTER_RIGHT);

        // Load the image
        Label abc = new Label("sdwemd");
        Image image = new Image(prodImg);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(2000);  // Set a larger width for the product image
        imageView.setFitHeight(800); // Set a larger height for the product image
        imageView.setPreserveRatio(true); // Preserve the image aspect ratio

        leftBox.getChildren().add(imageView);

        // Create a VBox for the right side (product details, buttons, and additional image)
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(20));
        rightBox.setAlignment(Pos.CENTER);
        //rightBox.setStyle("-fx-background-color: #333; -fx-border-color: lightgray; -fx-border-radius: 10; -fx-padding: 20;");

        // Create labels for product details
        Label nameLabel = new Label("Product Name: " + prodName);
        //nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        Label priceLabel = new Label("Price: $" + prodPrice);
       // priceLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        Label added_to_cart = new Label("Added To Cart");
        // Create buttons for buy now and add to cart
        Button buyNowButton = new Button("Buy Now");
        //buyNowButton.setStyle("-fx-font-size: 15px; -fx-border-color: lightgray; -fx-border-radius: 10; -fx-padding: 20;");
        buyNowButton.setPrefWidth(200);
        buyNowButton.setTextFill(Color.WHITE);
        buyNowButton.setBackground(Background.fill(Color.DARKCYAN));
        buyNowButton.getStyleClass().add("login");
        rootscene.getStylesheets().add(ProductDetails.class.getResource("Style.css").toExternalForm());
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-font-size: 15px; -fx-border-radius:30;");

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 15px;");
        backButton.setOnAction(event -> {
            System.out.println("Back button called");
            primaryStage.setScene(rootscene);
            primaryStage.setMaximized(true); // Maximize the stage
            primaryStage.setFullScreen(true); // Set full screen
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {

                    String prodIdquery = "select product_id from products where product_name='"+ prodName+"';";
                    ResultSet prodIdresultSet = dbcon.getQueryTable(prodIdquery);
                    prodIdresultSet.next();
                    String product_id = prodIdresultSet.getString("product_id");
                    System.out.println(product_id + " " + username);

                    System.out.println(dbcon.insertUpdate("INSERT IGNORE INTO in_cart VALUES('" + username + "','" + product_id + "')"));


                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
        });
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {

                    String prodIdquery = "select product_id from products where product_name='"+prodName+"';";
                    ResultSet prodIdresultSet = dbcon.getQueryTable(prodIdquery);
                    prodIdresultSet.next();
                    String product_id = prodIdresultSet.getString("product_id");
                    System.out.println(product_id + " " + username);

                    System.out.println(dbcon.insertUpdate("INSERT IGNORE INTO in_cart VALUES('" + username + "','" + product_id + "')"));


                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
                try {
                    Cart.cart(primaryStage, rootscene, username);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Load the additional image
        Image additionalImage = new Image(prodImg); // Replace "path_to_your_additional_image.jpg" with the path to your image
        ImageView additionalImageView = new ImageView(additionalImage);
        additionalImageView.setFitWidth(200);  // Set the width for the additional image
        additionalImageView.setPreserveRatio(true); // Preserve the image aspect ratio

        rightBox.getChildren().addAll(backButton, additionalImageView, nameLabel, priceLabel, addToCartButton, buyNowButton);

        HBox xyz = new HBox(2);
        xyz.getChildren().addAll(leftBox,rightBox);
        // Create the ScrollPane and add the VBox to it
        ScrollPane scrollPane = new ScrollPane(xyz);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        //scrollPane.setStyle("-fx-background-color: black;");

        // Create the scene and set it to the stage
        Scene scene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Details");
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.setFullScreen(true); // Set full screen
        primaryStage.show();
    }
}
