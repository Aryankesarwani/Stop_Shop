package com.example.stop_shop;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class StopAndShop extends Application {

    static GridPane root;
    public Label email_error = new Label("");
    public Label contact_error = new Label("");
    public Label username_error = new Label("");
    public Label password_error = new Label("");
    public Label username_error_login = new Label("");
    public Label password_error_login = new Label("");
    public TextField entered_username = new TextField();
    public PasswordField entered_password = new PasswordField();
    public TextField entered_email = new TextField();
    public TextField entered_contact = new TextField();
    public Label signup_result = new Label("");
    public Label redirect = new Label("");
    public TextField usernametext = new TextField();
    public PasswordField passwordField = new PasswordField();
    public int detail_not_complete=0;
    public int detail_duplicate=0;
    public int login_fail=0;




    public void app_login(Stage stage,Scene scene) throws Exception
    {


        Database_Connection dbcon = new Database_Connection();

        usernametext.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1.length()>s.length())
                {
                    username_error_login.setText("");
                }
                if(t1.length()==0)
                {
                    username_error_login.setText("Username not Entered!");
                    username_error_login.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                    username_error_login.setTextFill(Color.RED);
                    login_fail=1;
                }

            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1.length()>s.length())
                {
                    password_error_login.setText("");
                }
                if(t1.length()==0)
                {
                    password_error_login.setText("Password not Entered!");
                    login_fail=1;
                }

            }
        });

        System.out.println(login_fail);
        if(login_fail==0) {


            String login_username = usernametext.getText().strip();
            //String login_username ="Aryan";
            System.out.println("username = "+login_username);
            String login_password = passwordField.getText().strip();
            //String login_password = "123";
            System.out.println("userpassword = "+login_password);

            //dashboard.star(stage,scene,login_username);

            String query = "select username,password from user where username='" + login_username + "' ";
            ResultSet resultSet = dbcon.getQueryTable(query);
            if (!resultSet.next()) {
                username_error_login.setText("Profile does not exist");
                username_error_login.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                username_error_login.setTextFill(Color.RED);
            } else
            {
                String retrieved_username;
                String retrieved_password;

                retrieved_username = resultSet.getString("username");
                retrieved_password = resultSet.getString("password");
                System.out.println(retrieved_username);
                System.out.println(retrieved_password);

                if (retrieved_username.equals(login_username) && retrieved_password.equals(login_password))
                {
                    System.out.println(login_username);

                    dashboard.start(stage,scene,login_username);
                    //Cart.cart(stage,scene,login_username);
                }
                else {
                    password_error_login.setText("Incorrect Password!");
                    password_error_login.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                    password_error_login.setTextFill(Color.RED);
                }
            }
        }
    }


    public void sign_up(Stage stage,Scene scene)
      {
        GridPane root_signup = new GridPane();
        Database_Connection dbcon = new Database_Connection();


        Label username = new Label("Username: ");

        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        entered_username.setPrefWidth(200);

        Label password = new Label("Password: ");

        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        entered_password.setPrefWidth(200);

        Label email = new Label("Email: ");

        email.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        entered_email.setPrefWidth(200);

        Label contactno = new Label("Mobile Number: ");

        contactno.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));


        entered_contact.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(s.length()<t1.length())
                {
                    if(t1.length()>10) {

                        contact_error.setText("Contact cannot be greater than 10 digits!");
                        contact_error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                        contact_error.setTextFill(Color.RED);
                        detail_not_complete=1;
                    }
                    else if(t1.length()==10)
                    {
                        contact_error.setText("");
                        for(int i=0;i<t1.length();i++)
                        {
                            if(!Character.isDigit(t1.charAt(i)))
                            {
                                contact_error.setText("Contact can only contain numerical digits!");
                                contact_error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                                contact_error.setTextFill(Color.RED);
                                detail_not_complete=1;
                                break;

                            }
                        }

                    }
                    else if(t1.length()<10)
                    {
                        contact_error.setText("Contact should contain 10 digits!");
                        contact_error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
                        contact_error.setTextFill(Color.RED);
                        detail_not_complete=1;


                    }
                    else
                    {
                        contact_error.setText("");
                    }
                }


            }
        });
        entered_email.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1.length()>s.length())
                {
                    for(int i=0;i<t1.length();i++)
                    {
                        if(!(Character.isDigit(t1.charAt(i))||Character.isAlphabetic(t1.charAt(i))||t1.charAt(i)=='_'||t1.charAt(i)=='.'||t1.charAt(i)=='@'))
                        {
                            email_error.setText("Email not entered correctly!");
                            email_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                            email_error.setTextFill(Color.RED);
                            detail_not_complete=1;
                            break;
                        }
                    }
                    int num_of_at=0;
                    for(int i=0;i<t1.length();i++)
                    {

                        if(t1.charAt(i)=='@')
                        {
                            num_of_at+=1;
                        }

                    }
                    if(num_of_at>1)
                    {
                        email_error.setText("Email not entered correctly!");
                        email_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                        email_error.setTextFill(Color.RED);
                        detail_not_complete=1;


                    }
                    else if(num_of_at==0)
                    {
                        email_error.setText("Email not entered correctly!");
                        email_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                        email_error.setTextFill(Color.RED);
                        detail_not_complete=1;

                    }
                    else
                    {
                        email_error.setText("");
                    }
                }


            }
        });

        Button back = new Button("BACK");
        back.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");
        back.setAlignment(Pos.TOP_LEFT);

        //back.setTranslateY(-120);
        back.setTranslateX(-480);

        Button reset = new Button("Reset");
        reset.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");


          reset.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                entered_username.clear();
                entered_password.clear();
                entered_contact.clear();
                entered_email.clear();
                username_error.setText("");
                password_error.setText("");
                contact_error.setText("");
                email_error.setText("");
                redirect.setText("");
                signup_result.setText("");


            }
        });

        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color: #00FF00; -fx-text-fill: white;");



        VBox hBox = new VBox(8);
        VBox hBox1 = new VBox(8);
        VBox hBox2 = new VBox(8);
        VBox hBox3 = new VBox(13);
        HBox hBox4 = new HBox(10);
        hBox4.setAlignment(Pos.CENTER_LEFT);
        reset.setTranslateX(110);
        submit.setTranslateX(130);
        reset.setPrefWidth(60);
        submit.setPrefWidth(60);


        hBox.getChildren().addAll(username,entered_username);
        hBox1.getChildren().addAll(password,entered_password);
        hBox2.getChildren().addAll(email,entered_email);
        hBox3.getChildren().addAll(contactno,entered_contact);
        hBox4.getChildren().addAll(reset,submit);

        VBox vBox = new VBox(8);

        Label Signup= new Label("Create Account");




        Signup.setFont(Font.font("Verdana", FontPosture.ITALIC,35));
        Signup.setTextFill(Color.BLACK);
        
        Signup.setTranslateY(-20);

        username_error.setTranslateX(110);
        password_error.setTranslateX(110);
        email_error.setTranslateX(110);
        contact_error.setTranslateX(110);
        String imagePath = "Images/Stop&shop.jpg";
        //Image image = new Image(imagePath);
        Image image=new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\Stop&shop.jpg");
        ImageView imgview=new ImageView(image);
        imgview.setFitHeight(130);
        imgview.setFitWidth(300);
        //imgview.setTranslateX(40);
        imgview.setTranslateY(-50);
        VBox vBox1 = new VBox(8);
        vBox.getChildren().addAll(Signup,hBox,username_error,hBox1,password_error,hBox2,email_error,hBox3,contact_error,hBox4,signup_result,redirect);
        //vBox.setTranslateX(500);
        vBox.setStyle("-fx-padding: 20px;");
        BorderStroke borderStroke = new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT);
        Border border = new Border(borderStroke);
        vBox.setBorder(border);

      // Set the border to the VBox
       root.setBorder(border);
        vBox1.getChildren().addAll(imgview, vBox);


        root_signup.add(back,0,0);
        root_signup.add(vBox1,2,2);


        image = new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\pexels-andrea-piacquadio-3775602.jpg");

        //BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        double width = 1600; // Set your desired width
        double height = 800; // Set your desired height

        root_signup.setAlignment(Pos.CENTER);

          entered_contact.setPrefWidth(200);

          entered_username.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER) {
                  entered_password.requestFocus(); // Move focus to password field
              }
          });
          entered_password.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER) {
                  entered_email.requestFocus(); // Move focus to password field
              }
          });
          entered_email.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER) {
                  entered_contact.requestFocus(); // Move focus to password field
              }
          });
          entered_contact.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER) submit.fire();// Move focus to password field
          });

        scene.setRoot(root_signup);
        stage.setMinWidth(1000);
        stage.setMinHeight(800);

        stage.show();

        back.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {

                entered_contact.clear();
                entered_email.clear();
                entered_password.clear();
                entered_username.clear();
                signup_result.setText("");
                redirect.setText("");
                scene.setRoot(root);
                stage.setScene(scene);
                stage.show();



            }
        });

        entered_username.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1.length()>s.length())
                {
                    username_error.setText("");
                }

            }
        });

        entered_password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if(t1.length()>s.length())
                {
                    password_error.setText("");
                }


            }
        });


        submit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                detail_not_complete=0;
                detail_duplicate=0;
                String username = entered_username.getText().strip();
                String password = entered_password.getText().strip();
                String email = entered_email.getText().strip();
                String contact = entered_contact.getText().strip();


                if(entered_username.getText().isEmpty())
                {
                    username_error.setText("Username not Entered!");
                    username_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                    username_error.setTextFill(Color.RED);
                    detail_not_complete=1;
                }
                else
                {
                    try
                    {


                        String query="select username from user where username='"+username+"'";

                        ResultSet resultSet = dbcon.getQueryTable(query);

                        if(resultSet.next())
                        {
                            detail_duplicate=1;
                            username_error.setText("Username already taken");
                            username_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                            username_error.setTextFill(Color.RED);

                        }



                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
                if(entered_password.getText().isEmpty())
                {
                    password_error.setText("Password not Entered!");
                    password_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                    password_error.setTextFill(Color.RED);
                    detail_not_complete=1;
                }
                if(entered_email.getText().isEmpty())
                {
                    email_error.setText("Email not Entered!");
                    email_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                    email_error.setTextFill(Color.RED);
                    detail_not_complete=1;
                }
                if (entered_contact.getText().isEmpty())
                {
                    contact_error.setText("Contact not Entered!");
                    contact_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                    contact_error.setTextFill(Color.RED);
                    detail_not_complete=1;
                }
                else
                {
                    try
                    {

                        String query="select contact_no from user where contact_no='"+contact+"'";

                        ResultSet resultSet = dbcon.getQueryTable(query);

                        if(!(resultSet.next()==false))
                        {
                            detail_duplicate=1;
                            contact_error.setText("Number linked to another account!");
                            contact_error.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                            contact_error.setTextFill(Color.RED);


                        }



                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                }


                if(detail_not_complete==0 && detail_duplicate==0)
                {
                    try
                    {
                        user_details();
                    } catch (SQLException throwables)
                    {
                        throwables.printStackTrace();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if(detail_not_complete==1)
                {
                    signup_result.setText("Fill in the details!!");
                    signup_result.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
                    signup_result.setTextFill(Color.RED);
                    signup_result.setTranslateX(130);

                    redirect.setText("");
                }



            }
        });




    }

    private void user_details() throws SQLException, InterruptedException
    {
        String username = entered_username.getText().strip();
        String password = entered_password.getText().strip();
        String email = entered_email.getText().strip();
        String contact = entered_contact.getText().strip();
        Database_Connection dbcon = new Database_Connection();
        String query="insert into user values('"+username+"','"+email+"','"+password+"',"+contact+");";
        dbcon.insertUpdate(query);

        signup_result.setText("Sign Up Successful!");
        signup_result.setFont(Font.font("Tahoma",FontWeight.NORMAL,13));
        signup_result.setTextFill(Color.GREEN);
        signup_result.setTranslateX(130);


        redirect.setText("Return to the Main Page and Login!");
        redirect.setFont(Font.font("Aerial",FontWeight.NORMAL,FontPosture.ITALIC,13));
        redirect.setTranslateX(85);





    }

    public void start(Stage stage) throws Exception
    {
        stage.setTitle("STOP & SHOP");
        
        GridPane rootnode = new GridPane();
        rootnode.setAlignment(Pos.CENTER_RIGHT);
        Scene scene = new Scene(rootnode);
        root=rootnode;

        rootnode.setHgap(1000);
        rootnode.setVgap(1000);

        rootnode.setPadding(new Insets(25, 25, 25, 25));


        HBox hBox = new HBox(5);
        HBox hBox1 = new HBox(5);

        Label username = new Label("Username :");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));


        usernametext.setPrefWidth(200);
        usernametext.clear();

        username_error_login.setTranslateX(125);

        hBox.getChildren().addAll(username, usernametext);

        Label password = new Label("Password  :");
        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        passwordField.setPrefWidth(200);
        passwordField.clear();

        password_error_login.setTranslateX(125);

        hBox1.getChildren().addAll(password, passwordField);
        Label forgot = new Label("Forgot Password");
        forgot.setAlignment(Pos.CENTER);
        forgot.setTranslateX(100);
        forgot.setTextFill(Color.RED);

        VBox vBox = new VBox(5);
        vBox.setTranslateX(-170);

        Button login = new Button("Login");
        login.setPrefWidth(200);
        login.setTextFill(Color.WHITE);
        login.setBackground(Background.fill(Color.DARKCYAN));
        login.getStyleClass().add("login");
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

        login.setTranslateX(40);


        usernametext.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus(); // Move focus to password field
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login.fire(); // Move focus to password field
            }
        });

        Label not_member = new Label("Not a member yet?");
        not_member.setAlignment(Pos.CENTER);
        not_member.setTranslateX(100);
        not_member.setTextFill(Color.GREEN);

        Label signup = new Label("SignUp");
        signup.setTranslateX(110);
        signup.setTextFill(Color.DARKCYAN);

        Label login_text = new Label("LogIn");
        login_text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,35));
        login_text.setTextFill(Color.DARKCYAN);
        login_text.setTranslateX(120);
        login_text.setTranslateY(-20);

        HBox hBox2 = new HBox(5);

        hBox2.getChildren().addAll(login);
        hBox2.setAlignment(Pos.CENTER);


        HBox hBox3 = new HBox(2);
        hBox3.getChildren().addAll(not_member,signup);

        Image image=new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\Stop&shop.jpg");
        ImageView imgview=new ImageView(image);
        imgview.setFitHeight(150);
        imgview.setFitWidth(300);
        imgview.setTranslateX(40);
        imgview.setTranslateY(-50);

        vBox.getChildren().addAll(imgview,login_text,hBox,username_error_login, hBox1,password_error_login, hBox2,forgot,hBox3);


        rootnode.add(vBox, 0, 0);

        image = new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\pexels-andrea-piacquadio-3775602.jpg");

        double width = 1600; // Set your desired width
        double height = 800; // Set your desired height

        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        rootnode.setBackground(background);



        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();



        signup.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                try{
                    sign_up(stage,scene);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        login.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                try {
                    app_login(stage,scene);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public static void main(){
        launch();
    }
}