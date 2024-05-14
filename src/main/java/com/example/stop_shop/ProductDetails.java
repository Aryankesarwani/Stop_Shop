package com.example.stop_shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProductDetails {

    static ScrollPane sp = new ScrollPane();

    static Database_Connection dbcon = new Database_Connection();
    static Scene details_scene =  new Scene(sp,800,600);

    public static void displayProductDetails(Stage primaryStage, Scene rootscene, String prodName, String prodPrice, String prodImg, String username) {
        System.out.println("I am in product details page");

//        javafx.scene.control.ScrollPane sp = new ScrollPane();
//        sp.setFitToWidth(true);
//        sp.setFitToHeight(true);
//
//        primaryStage.setScene(details_scene);
//        rootscene.setRoot(sp);



        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(70));
        tilePane.setVgap(20);
        tilePane.setHgap(20);
        tilePane.setPrefColumns(2);
        tilePane.setStyle("-fx-background-color:black;");

        // Create a VBox to hold all the components
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        BorderStroke borderStroke = new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT);
        Border border = new Border(borderStroke);
        vbox.setBorder(border);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setMinWidth(300);

        // Load the image
        Image image = new Image(prodImg);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);

        // Create a back button
        Button back = new Button("Back");
        back.setAlignment(Pos.TOP_LEFT);
        back.setTranslateY(0);
        back.setStyle("-fx-font-size:15px;");
        back.setOnAction(event -> {
            System.out.println("Back button called");
            primaryStage.setScene(rootscene);
            primaryStage.setMaximized(true); // Maximize the stage
            primaryStage.setFullScreen(true); // Set full screen
        });

        vbox.getChildren().addAll(imageView);

        VBox vBox1 = new VBox(10);
        vBox1.setBorder(border);
        vBox1.setMinWidth(300);

        // Create labels for product details
        javafx.scene.control.Label nameLabel = new javafx.scene.control.Label("Product Name: " + prodName);
        javafx.scene.control.Label priceLabel = new javafx.scene.control.Label("Price: $" + prodPrice);
        vBox1.getChildren().addAll(nameLabel, priceLabel);

        // Create buttons for buy now and add to cart
        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        VBox vBox2 = new VBox(10);
        vBox2.setBorder(border);
        vBox2.getChildren().addAll(addToCartButton, buyNowButton);
        vBox2.setMinWidth(300);
        // Add components to the HBox
        HBox header = new HBox(8);
        header.getChildren().add(back);
        header.setPadding(new Insets(20));

        HBox root = new HBox(3);
        root.setPadding(new Insets(20));
        root.setBorder(border);
        root.getChildren().addAll(vbox, vBox1, vBox2);
        root.setAlignment(Pos.CENTER);

        // Create the scene and set it to the stage
        Scene scene;
        scene = new Scene(root, 800, 600);

        // Set the scene and adjust the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Details");
        tilePane.getChildren().add(root);
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.setFullScreen(true); // Set full screen
        primaryStage.show();
    }
}
