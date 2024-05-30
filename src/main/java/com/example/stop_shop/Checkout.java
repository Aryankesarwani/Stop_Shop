package com.example.stop_shop;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

public class Checkout {



    public static TextField entered_username = new TextField();
    public static TextField entered_mobile = new TextField();
    public static TextField entered_pincode = new TextField();
    public static TextField entered_flat = new TextField();
    public static TextField entered_area = new TextField();
    public static TextField entered_landmark = new TextField();
    public static Label username_error = new Label();
    public static Label mobile_error = new Label();
    public static Label pincode_error = new Label();
    public static Label flat_error = new Label();
    public static Label area_error = new Label();
    public static Label landmark_error = new Label();
    public static Label checkout_result = new Label();
    public static int detail_not_complete = 0;

    public static void Check(Stage primaryStage, Scene rootscene) {
        // Create the labels and text fields for the checkout form
        System.out.println("checkout called");
        GridPane root_checkout = new GridPane();
        Database_Connection dbcon = new Database_Connection();

        Label country_region = new Label("Country/Region");
        Label username = new Label("Full Name");
        Label mobile = new Label("Mobile Number");
        Label pincode = new Label("Pincode");
        Label flat = new Label("Flat, House no., Building, Company, Apartment");
        Label area = new Label("Area, Street, Sector, Village");
        Label landmark = new Label("Landmark");

        entered_username.setPrefWidth(200);
        entered_mobile.setPrefWidth(200);
        entered_pincode.setPrefWidth(200);
        entered_flat.setPrefWidth(200);
        entered_area.setPrefWidth(200);
        entered_landmark.setPrefWidth(200);

        entered_mobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (s.length() < t1.length()) {
                    if (t1.length() > 10) {
                        mobile_error.setText("Mobile number cannot be greater than 10 digits!");
                        detail_not_complete = 1;
                    } else if (t1.length() == 10) {
                        mobile_error.setText("");
                        for (int i = 0; i < t1.length(); i++) {
                            if (!Character.isDigit(t1.charAt(i))) {
                                mobile_error.setText("Mobile number can only contain numerical digits!");
                                detail_not_complete = 1;
                                break;
                            }
                        }
                    } else if (t1.length() < 10) {
                        mobile_error.setText("Mobile number should contain 10 digits!");
                        detail_not_complete = 1;
                    } else {
                        mobile_error.setText("");
                    }
                }
            }
        });

        Button back = new Button("BACK");
        back.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");
        back.setAlignment(Pos.TOP_LEFT);
        back.setTranslateX(-480);

        Button reset = new Button("Reset");
        reset.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");

        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color: #00FF00; -fx-text-fill: white;");


        back.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                entered_username.clear();
                entered_mobile.clear();
                entered_pincode.clear();
                entered_flat.clear();
                entered_area.clear();
                entered_landmark.clear();
                System.out.println("Back button called");
                primaryStage.setScene(rootscene);
                primaryStage.setMaximized(true); // Maximize the stage
                primaryStage.setFullScreen(true);

            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                entered_username.clear();
                entered_mobile.clear();
                entered_pincode.clear();
                entered_flat.clear();
                entered_area.clear();
                entered_landmark.clear();
                landmark_error.setText("");
                area_error.setText("");
                flat_error.setText("");
                pincode_error.setText("");
                mobile_error.setText("");
                username_error.setText("");
            }
        });

        submit.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                detail_not_complete = 0;
                System.out.println("submit called");

                if (entered_username.getText().isEmpty()) {
                    username_error.setText("Full Name not Entered!");
                    detail_not_complete = 1;
                }

                if (entered_mobile.getText().isEmpty()) {
                    mobile_error.setText("Mobile Number not Entered!");
                    detail_not_complete = 1;
                }

                if (entered_pincode.getText().isEmpty()) {
                    pincode_error.setText("Pincode not Entered!");
                    detail_not_complete = 1;
                }

                if (entered_flat.getText().isEmpty()) {
                    flat_error.setText("Flat details not Entered!");
                    detail_not_complete = 1;
                }

                if (entered_area.getText().isEmpty()) {
                    area_error.setText("Area details not Entered!");
                    detail_not_complete = 1;
                }

                if (entered_landmark.getText().isEmpty()) {
                    landmark_error.setText("Landmark not Entered!");
                    detail_not_complete = 1;
                }

                if (detail_not_complete == 0) {
                    try {
                        dashboard.place_order();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Order Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Your order has been placed successfully!");

                        // Wait for user confirmation
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // User clicked OK, you can perform any further action here
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (detail_not_complete == 1) {
                    checkout_result.setText("Fill all the details!!");
                }
                else{
                    landmark_error.setText("");
                    area_error.setText("");
                    flat_error.setText("");
                    pincode_error.setText("");
                    mobile_error.setText("");
                    username_error.setText("");
                    System.out.println("submit done");
                    try {
                        String user = entered_username.getText();
                        System.out.println(user);
                        dashboard.start(primaryStage,rootscene,user);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        VBox hBox = new VBox(8);
        VBox hBox1 = new VBox(8);
        VBox hBox2 = new VBox(8);
        VBox hBox3 = new VBox(8);
        VBox hBox4 = new VBox(8);
        VBox hBox5 = new VBox(8);
        VBox hBox6 = new VBox(13);
        HBox hBox7 = new HBox(10);
        hBox7.setAlignment(Pos.CENTER_LEFT);
        reset.setTranslateX(110);
        submit.setTranslateX(130);
        reset.setPrefWidth(60);
        submit.setPrefWidth(60);

        hBox.getChildren().addAll(username, entered_username);
        hBox1.getChildren().addAll(mobile, entered_mobile);
        hBox2.getChildren().addAll(pincode, entered_pincode);
        hBox3.getChildren().addAll(flat, entered_flat);
        hBox4.getChildren().addAll(area, entered_area);
        hBox5.getChildren().addAll(landmark, entered_landmark);


        entered_username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                entered_mobile.requestFocus(); // Move focus to password field
            }
        });
        entered_mobile.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                entered_pincode.requestFocus(); // Move focus to password field
            }
        });
        entered_pincode.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                entered_flat.requestFocus(); // Move focus to password field
            }
        });
        entered_flat.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                entered_area.requestFocus(); // Move focus to password field
            }
        });
        entered_area.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                entered_landmark.requestFocus(); // Move focus to password field
            }
        });








        ComboBox<String> countryComboBox = new ComboBox<>();
        ObservableList<String> countries = FXCollections.observableArrayList();

// Get the list of countries
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countries.add(locale.getDisplayCountry());
        }

        countryComboBox.setItems(countries);
        hBox6.getChildren().addAll(country_region, countryComboBox);
        hBox7.getChildren().addAll(reset, submit);


        entered_landmark.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                countryComboBox.requestFocus();
                countryComboBox.show();
            }
        });
        countryComboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) submit.fire();// Move focus to password field
        });
        VBox vBox = new VBox(8);

        Label checkoutLabel = new Label("Checkout");
        checkoutLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 35));
        checkoutLabel.setTextFill(Color.BLACK);
        checkoutLabel.setTranslateY(-20);

        vBox.getChildren().addAll(checkoutLabel, hBox, username_error, hBox1, mobile_error, hBox2, pincode_error, hBox3, flat_error, hBox4, area_error, hBox5, landmark_error, hBox6, hBox7, checkout_result);
        vBox.setStyle("-fx-padding: 20px;");
        BorderStroke borderStroke = new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT);
        Border border = new Border(borderStroke);
        vBox.setBorder(border);

        root_checkout.add(back, 0, 0);
        root_checkout.add(vBox, 2, 2);
        root_checkout.setAlignment(Pos.CENTER);

        javafx.scene.control.ScrollPane sp1 = new ScrollPane();
        sp1.setFitToWidth(true);
        sp1.setFitToHeight(true);


        Scene Checkout_scene =new Scene(sp1, 800, 600);
        primaryStage.setScene(Checkout_scene);
        Checkout_scene.setRoot(sp1);
        sp1.setContent(root_checkout);
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.setFullScreen(true); // Set full screen
        primaryStage.show();
    }
}

