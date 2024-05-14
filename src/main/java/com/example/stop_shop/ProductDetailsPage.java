package com.example.stop_shop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductDetailsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        BorderPane root = new BorderPane();

        // Create the back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Handle the back button action (e.g., navigate to the previous screen)
        });

        // Top layout with back button
        HBox topLayout = new HBox(backButton);
        topLayout.setPadding(new Insets(10));
        root.setTop(topLayout);

        // Product image on the left side
        Image productImage = new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\Jeans-4.jpg"); // Replace with your image path
        ImageView imageView = new ImageView(productImage);
        imageView.setFitWidth(600);
        imageView.setFitHeight(800);// Set desired width
        imageView.setPreserveRatio(true);

        VBox leftLayout = new VBox(imageView);
        leftLayout.setAlignment(Pos.CENTER_LEFT);
        leftLayout.setPadding(new Insets(10));
        root.setLeft(leftLayout);

        // Product details on the right side
        VBox rightLayout = new VBox(10);
        rightLayout.setPadding(new Insets(10));

        Label productNameLabel = new Label("Product Name");
        Label productSizeLabel = new Label("Size: Medium");
        Label productAddressLabel = new Label("Address: 123 Example Street");
        Label productPriceLabel = new Label("Price: $99.99");

        Button buyNowButton = new Button("Buy Now");
        buyNowButton.setOnAction(e -> {
            // Handle buy now action
        });

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> {
            // Handle add to cart action
        });

        rightLayout.getChildren().addAll(
                productNameLabel,
                productSizeLabel,
                productAddressLabel,
                productPriceLabel,
                buyNowButton,
                addToCartButton
        );

        root.setCenter(rightLayout);

        // Create the scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Product Details");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

