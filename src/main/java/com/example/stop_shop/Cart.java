package com.example.stop_shop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart {
    static Database_Connection dbcon;



    public static void cart(Stage primaryStage, Scene rootscene, String username) throws Exception {

        System.out.println("cRT ME AA GYE");
        dbcon = new Database_Connection();
        String inCartquery = "select product_id,count(*) as qty from in_cart where username='" + username + "' group by product_id;";
        ResultSet inCartResult = dbcon.getQueryTable(inCartquery);
        int CartProd = 0;
        while (inCartResult.next()) {
            CartProd += 1;
        }
        System.out.println("cart prod " + CartProd);
        String[] prod_id = new String[CartProd];
        int[] prod_qty = new int[CartProd];
        int pos = 0;
        ResultSet inCartResult1 = dbcon.getQueryTable(inCartquery);

        while (inCartResult1.next()) {
            String id = inCartResult1.getString("product_id");
            int quant = inCartResult1.getInt("qty");
            prod_id[pos] = id;
            prod_qty[pos] = quant;
            pos += 1;
        }

        Label Header = new Label("YOUR CART");
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:15px;");
        Header.setStyle("-fx-font-size:30px;-fx-background-color:#0a043c;");
        Header.setTextFill(Color.WHITE);
        HBox CartHeader = new HBox(20, back, Header);
        CartHeader.setAlignment(Pos.TOP_CENTER);
        back.setAlignment(Pos.BASELINE_LEFT);
        back.setTranslateX(-520);
        Header.setAlignment(Pos.CENTER);
        CartHeader.setPadding(new Insets(20));

        String[] prod_names = new String[CartProd];
        String[] prod_prices = new String[CartProd];
        String[] prod_imgs = new String[CartProd];
        String[] prod_descp = new String[CartProd];

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(90));
        tilePane.setVgap(70);
        tilePane.setPrefColumns(1);
        tilePane.setStyle("-fx-background-color:#0a043c;");
        HBox[] tiles = new HBox[CartProd];

        Button[] removeButton = new Button[CartProd];
        Label[] removeLabel = new Label[CartProd];

        for (int i = 0; i < CartProd; i++) {
            String Cquery = "select product_id,product_name,price,product_img,description from products where product_id='" + prod_id[i] + "';";
            ResultSet CresultSet = dbcon.getQueryTable(Cquery);
            CresultSet.next();
            String pid = CresultSet.getString("product_id");
            String pname = CresultSet.getString("product_name");
            String pprice = CresultSet.getString("price");
            String pimg = CresultSet.getString("product_img");
            String pdes = CresultSet.getString("description");

            Image image = new Image(pimg);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(200);
            imageView.setSmooth(true);
            VBox vbxImg = new VBox(5);
            vbxImg.setAlignment(Pos.BASELINE_LEFT);
            Text text_1 = new Text(pname);
            TextFlow prod_name = new TextFlow(text_1);
            text_1.setFont(Font.font("Verdana", FontPosture.ITALIC, 16));
            vbxImg.getChildren().addAll(imageView, prod_name);

            VBox details = new VBox(10);
            Label prod_price = new Label("Price: ₹" + pprice);
            prod_price.setStyle("-fx-font-size:15");
            Text text_2 = new Text("Description:" + pdes);
            TextFlow description = new TextFlow(text_2);
            description.setStyle("-fx-font-size:15");
            Label quantity = new Label("Quantity:" + prod_qty[i]);
            quantity.setStyle("-fx-font-size:15");
            removeLabel[i] = new Label("");
            removeButton[i] = new Button("Remove from Cart");
            details.getChildren().addAll(description, prod_price, quantity, removeButton[i], removeLabel[i]);

            int finalI = i;
            removeButton[i].setOnAction(actionEvent -> {
                for (int j = 0; j < prod_qty[finalI]; j++) {
                    String query = "delete from in_cart where product_id='" + pid + "' and username='" + username + "';";
                    dbcon.executeUpdate(query);
                }
                removeLabel[finalI].setText("Product has been deleted from cart");
                removeLabel[finalI].setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                removeLabel[finalI].setTextFill(Color.BLACK);
            });

            tiles[i] = new HBox(vbxImg, details);
            tiles[i].setAlignment(Pos.CENTER);
            tiles[i].setTranslateX(70);
            tiles[i].setStyle("-fx-border-color: black;-fx-background-color:#e8dcf6;");
            tiles[i].setPrefWidth(1000);
            tiles[i].setPrefHeight(220);
            tiles[i].setPadding(new Insets(10, 10, 50, 10));
            tilePane.getChildren().add(tiles[i]);
        }

        tilePane.setOrientation(Orientation.HORIZONTAL);
        Button place_order = new Button("PLACE ORDER");
        place_order.setAlignment(Pos.CENTER);
        Label message = new Label("");
        VBox place = new VBox(5, place_order, message);
        place.setStyle("-fx-background-color:#0a043c;");
        place.setAlignment(Pos.CENTER);
        place.setPadding(new Insets(20));

        VBox box = new VBox();
        if (CartProd == 0) {
            VBox no_items = new VBox(50);
            Label zero_items = new Label("There are no items in your cart!");
            zero_items.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
            zero_items.setTextFill(Color.WHITE);
            no_items.getChildren().add(zero_items);
            no_items.setAlignment(Pos.CENTER);
            no_items.setPadding(new Insets(100));
            box.getChildren().addAll(CartHeader, no_items);
        } else {
            box.getChildren().addAll(CartHeader, tilePane, place);
        }
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color:#0a043c;");

        javafx.scene.control.ScrollPane sp1 = new ScrollPane();
        sp1.setFitToWidth(true);
        sp1.setFitToHeight(true);
        Scene cart_scene =new Scene(sp1, 800, 600);
        primaryStage.setScene(cart_scene);
        cart_scene.setRoot(sp1);
        sp1.setContent(box);
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.setFullScreen(true); // Set full screen
        primaryStage.show();

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("call ho gya");
                primaryStage.setScene(rootscene);
                //dashboard.dashboard_scene.setRoot(sp1);
                primaryStage.setMaximized(true); // Maximize the stage
                primaryStage.setFullScreen(true); // Set full screen
                primaryStage.show();

            }
        });

        place_order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    dashboard.place_order();
                    message.setText("YOUR ORDER HAS BEEN PLACED!");
                    message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                    message.setTextFill(Color.WHITE);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
