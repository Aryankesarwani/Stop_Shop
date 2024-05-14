package com.example.stop_shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dashboard
{

    static String usern;
    static String order_id;
    static ScrollPane sp = new ScrollPane();

    static Database_Connection dbcon = new Database_Connection();

    static Scene dashboard_scene =new Scene(sp, 800, 600);

    public static void start(Stage primaryStage, Scene scene, String username) throws Exception
    {

        javafx.scene.control.ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);

        primaryStage.setScene(dashboard_scene);
        scene.setRoot(sp);
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.setFullScreen(true); // Set full screen
        primaryStage.show();

        usern=username;
        System.out.println("dashboard aa gya");
        String styles =
                "-fx-font-size:25px;" +
                        "-fx-padding:10px;" +
                         " -fx-background-color : #C6DEF1;"+
                        " -fx-background-color : #b2c7d8;"+
                        "-fx-font-color:#FDFCDC;";
        String hoverstyle= "-fx-background-color: transparent;";

        String filterstyle =
                "-fx-font-size:15px;" +
                        "-fx-padding:10px;" +
                        " -fx-background-color : #b2c7d8;"+
                        "-fx-font-color:#FDFCDC;";
        String menuitemstyle= "-fx-font-size:15px;";

        Image LogoImage=new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\Stop&shop.jpg");
        ImageView LogoimageView=new ImageView(LogoImage);
        LogoimageView.setFitHeight(40);
        LogoimageView.setFitWidth(80);
        LogoimageView.setPreserveRatio(true);

        Image locationimg = new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\location.png");
        ImageView imageView = new ImageView(locationimg);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);



        MenuBar leftBar = new MenuBar();
        leftBar.setPrefHeight(20);
        Menu logoitem = new Menu("",LogoimageView);
        Menu location = new Menu("Allahabad", imageView);
        scene.getStylesheets().add(dashboard.class.getResource("Style.css").toExternalForm());
        leftBar.getMenus().add(logoitem);
        leftBar.getMenus().add(location);
        logoitem.setStyle(hoverstyle);


        Menu men = new Menu("MEN");
        men.setStyle(hoverstyle);
        MenuItem shirts=new MenuItem("SHIRTS");
        MenuItem formals=new MenuItem("FORMALS");
        shirts.setStyle(menuitemstyle);
        formals.setStyle(menuitemstyle);
        men.getItems().addAll(shirts,formals);


        Menu women = new Menu("WOMEN");
        women.setStyle(hoverstyle);
        leftBar.setStyle(styles);
        MenuItem tops=new MenuItem("TOPS");
        MenuItem dresses=new MenuItem("DRESSES");
        tops.setStyle(menuitemstyle);
        dresses.setStyle(menuitemstyle);
        women.getItems().addAll(tops,dresses);

        MenuBar rightBar = new MenuBar();
//Searchbar
        Image searchIconImage = new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\searchicon.jpg");
        ImageView searchIcon = new ImageView(searchIconImage);
        searchIcon.setFitWidth(40);
        searchIcon.setFitHeight(40);

        // Create search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.setPrefWidth(800);
        //chBar.setPrefSize(800,50);
        //searchBar.setAlignment(Pos.CENTER);

        // Create layout to hold the search icon and search bar
        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(searchIcon, searchBar);

//Searchbar

//Account
        Label label = new Label("Hello,"+usern+" ");
        label.setWrapText(true);
        label.setMaxWidth(400);
        Menu account = new Menu();
        account.setGraphic(label);
        account.setStyle(hoverstyle);
        MenuItem orders=new MenuItem("ORDERS");
        MenuItem signout=new MenuItem("SIGN OUT");
        orders.setStyle(menuitemstyle);
        signout.setStyle(menuitemstyle);
        account.getItems().addAll(orders,signout);

// Account

//Cart
        Image CartImg=new Image("C:\\Users\\18ary\\OneDrive\\Desktop\\Stop_Shop\\Images\\—Pngtree—cartoon shopping cart_4586974.png");
        ImageView CartImageView=new ImageView(CartImg);
        CartImageView.setFitHeight(50);
        CartImageView.setFitWidth(60);
        CartImageView.setPreserveRatio(true);
        Label Cartlabel= new Label();
        Cartlabel.setGraphic(CartImageView);
        Menu cartItem = new Menu();
        cartItem.setGraphic(Cartlabel);
//Cart

        rightBar.getMenus().add(account);
        rightBar.getMenus().add(cartItem);

//        searchBar.setOnKeyReleased(event -> {
//            String userInput = searchBar.getText().toLowerCase();
//            Database_Connection dbcon = new Database_Connection();
//            dbcon.getQueryTable("Select ")
//        });

        Cartlabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                try
                {
                    System.out.println("enter into cart");
                    Cart.cart(primaryStage, dashboard_scene, username);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    all_orders.display_orders(primaryStage,username,dashboard_scene);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        signout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                primaryStage.setScene(scene);
                primaryStage.setMaximized(true); // Maximize the stage
                primaryStage.setFullScreen(true); // Set full screen
                primaryStage.show();
                StopAndShop stopAndShop = new StopAndShop();
                try {
                    stopAndShop.app_login(primaryStage,scene);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        });

        rightBar.setStyle(styles);
        Region spacer = new Region();
        spacer.getStyleClass().add("menu-bar");
        spacer.setStyle(styles);

        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, searchBox, spacer, rightBar);
        menubars.setPrefWidth(1600);
        menubars.setPrefHeight(40);
        menubars.setStyle(styles);


        MenuButton filters = new MenuButton("Filter");
        filters.setTranslateX(1200);
        MenuItem all = new MenuItem("All");
        filters.getItems().addAll(men, women, all);


        MenuButton filterButton = new MenuButton("Sort By");
        MenuItem HtoL=new MenuItem("High to Low");
        MenuItem LtoH=new MenuItem("Low to High");
        filterButton.getItems().addAll(HtoL,LtoH);
        HBox filter = new HBox();
        filter.getChildren().addAll(filters,filterButton);
        //filterButton.setTranslateX(110);
        filter.setStyle(" -fx-background-color : black;");
        filterButton.setStyle(filterstyle);
        filters.setStyle(filterstyle);
        filter.setPadding(new Insets(20));



        String query = "select product_name,price,product_img from products;";
        ResultSet resultSet = dbcon.getQueryTable(query);

        //String prod_ids[]=new String[90];

        String prod_names[]=new String[90];
        String prod_prices[]=new String[90];
        String prod_imgs[]=new String[90];
        int num=0;
        while(resultSet.next())
        {
            //prod_ids[num]=resultSet.getString("product_id");

            prod_names[num]=resultSet.getString("product_name");
            prod_prices[num]=resultSet.getString("price");
            prod_imgs[num]=resultSet.getString("product_img");
            num++;
        }
        int finalnum = num;
        all.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                display_products(primaryStage,prod_names,prod_prices,prod_imgs,finalnum,menubars,filter,username);
            }
        });


        String Shirtquery = "select product_name,price,product_img from products where product_category='Shirt';";
        ResultSet ShirtresultSet = dbcon.getQueryTable(Shirtquery);
        String Shirt_names[]=new String[70];
        String Shirt_prices[]=new String[70];
        String Shirt_imgs[]=new String[70];
        int Shirtnum=0;
        while(ShirtresultSet.next()) {
            Shirt_names[Shirtnum]=ShirtresultSet.getString("product_name");
            Shirt_prices[Shirtnum]=ShirtresultSet.getString("price");
            Shirt_imgs[Shirtnum]=ShirtresultSet.getString("product_img");
            Shirtnum++;
        }
        int finalShirtnum = Shirtnum;



        shirts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                display_products(primaryStage,Shirt_names,Shirt_prices,Shirt_imgs, finalShirtnum,menubars,filter,username);

                HtoL.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String HtoLquery = "select product_name,price,product_img from products where product_category='Shirt' order by price DESC ;";
                        try {
                            HightoLowFilter(primaryStage,menubars,filter,HtoLquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
                LtoH.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String LtoHquery = "select product_name,price,product_img from products where product_category='Shirt' order by price ;";
                        try {
                            LowToHighFilter(primaryStage,menubars,filter,LtoHquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });


            }

        });


        String Frquery = "select product_name,price,product_img from products where product_category='Formal Suit';";
        ResultSet FrresultSet = dbcon.getQueryTable(Frquery);
        String Fr_names[]=new String[70];
        String Fr_prices[]=new String[70];
        String Fr_imgs[]=new String[70];
        int Frnum=0;
        while(FrresultSet.next()) {
            Fr_names[Frnum]=FrresultSet.getString("product_name");
            Fr_prices[Frnum]=FrresultSet.getString("price");
            Fr_imgs[Frnum]=FrresultSet.getString("product_img");
            Frnum++;
        }
        int finalTrnum = Frnum;
        formals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent) {
                display_products(primaryStage,Fr_names,Fr_prices,Fr_imgs, finalTrnum,menubars,filter,username);

                HtoL.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String HtoLquery = "select product_name,price,product_img from products where product_category='formals' order by price DESC ;";
                        try {
                            HightoLowFilter(primaryStage,menubars,filter,HtoLquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
                LtoH.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String LtoHquery = "select product_name,price,product_img from products where product_category='formals' order by price ;";
                        try {
                            LowToHighFilter(primaryStage,menubars,filter,LtoHquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
            }
        });


        String Tpquery = "select product_name,price,product_img from products where product_category='tops';";
        ResultSet TpresultSet = dbcon.getQueryTable(Tpquery);
        String Tp_names[]=new String[70];
        String Tp_prices[]=new String[70];
        String Tp_imgs[]=new String[70];
        int Tpnum=0;
        while(TpresultSet.next()) {
            Tp_names[Tpnum]=TpresultSet.getString("product_name");
            Tp_prices[Tpnum]=TpresultSet.getString("price");
            Tp_imgs[Tpnum]=TpresultSet.getString("product_img");
            Tpnum++;
        }
        int finalTpnum = Tpnum;
        tops.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent) {
                display_products(primaryStage,Tp_names,Tp_prices,Tp_imgs, finalTpnum,menubars,filter,username);
                HtoL.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String HtoLquery = "select product_name,price,product_img from products where product_category='tops' order by price DESC ;";
                        try {
                            HightoLowFilter(primaryStage,menubars,filter,HtoLquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
                LtoH.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String LtoHquery = "select product_name,price,product_img from products where product_category='tops' order by price ;";
                        try {
                            LowToHighFilter(primaryStage,menubars,filter,LtoHquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });


            }
        });

        String Drquery = "select product_name,price,product_img from products where product_category='dresses';";
        ResultSet DrresultSet = dbcon.getQueryTable(Drquery);
        String Dr_names[]=new String[70];
        String Dr_prices[]=new String[70];
        String Dr_imgs[]=new String[70];
        int Drnum=0;
        while(DrresultSet.next()) {
            Dr_names[Drnum]=DrresultSet.getString("product_name");
            Dr_prices[Drnum]=DrresultSet.getString("price");
            Dr_imgs[Drnum]=DrresultSet.getString("product_img");
            Drnum++;
        }
        int finalDrnum = Drnum;
        dresses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent) {
                display_products(primaryStage,Dr_names,Dr_prices,Dr_imgs, finalDrnum,menubars,filter,username);
                HtoL.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String HtoLquery = "select product_name,price,product_img from products where product_category='dresses' order by price DESC ;";
                        try {
                            HightoLowFilter(primaryStage,menubars,filter,HtoLquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
                LtoH.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String LtoHquery = "select product_name,price,product_img from products where product_category='dresses' order by price ;";
                        try {
                            LowToHighFilter(primaryStage,menubars,filter,LtoHquery,username);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });



            }
        });


        HtoL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String HtoLquery = "select product_name,price,product_img from products order by price DESC;";
                    HightoLowFilter(primaryStage,menubars,filter,HtoLquery,username);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        LtoH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent) {
                try {
                    String LtoHquery = "select product_name,price,product_img from products order by price;";
                    LowToHighFilter(primaryStage,menubars,filter,LtoHquery,username);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });





        display_products(primaryStage,prod_names,prod_prices,prod_imgs,num,menubars,filter,username);

    }
    public static void display_products(Stage primaryStage, String prod_names[], String prod_prices[], String prod_imgs[], int pcount, HBox menubars, HBox filter, String username){


        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(70));
        tilePane.setVgap(20);
        tilePane.setHgap(20);
        tilePane.setPrefColumns(2);
        tilePane.setStyle("-fx-background-color:black;");

        VBox tiles[] = new VBox[pcount];
        Button add_to_cart_button[] = new Button[pcount];
        //final int[] lastClickedIndex = {-1};
        final int[] lastClickedIndex = {-1};

        for (int i = 0; i <pcount; i++)
        {
            final int buttonInd = i;

            Image image = new Image(prod_imgs[i]);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setPreserveRatio(true);
            HBox hbxImg = new HBox();
            hbxImg.setAlignment(Pos.CENTER);
            hbxImg.getChildren().add(imageView);

            Text text_1 = new Text(prod_names[i]);
            TextFlow prod_name = new TextFlow(text_1);
            text_1.setFont(Font.font("Verdana", FontPosture.ITALIC ,13));
            Label price = new Label("Price:"+prod_prices[i]);
            price.setAlignment(Pos.CENTER_LEFT);
            price.setTranslateY(20);
            price.setFont(Font.font(13));

            HBox cartFunctions= new HBox(20);

            add_to_cart_button[i] = new Button("Add to Cart");

            add_to_cart_button[i].setStyle("-fx-background-color: #b38de3;-fx-border-color: black;-fx-hovered-background:white");
            add_to_cart_button[i].setTranslateY(20);
            Label quantity= new Label();
            final int[] count = {1};

            int finalI = i;


            imageView.setOnMouseClicked(event -> {
                ProductDetails.displayProductDetails(primaryStage, dashboard_scene, prod_names[buttonInd], prod_prices[buttonInd], prod_imgs[buttonInd], username);
            });

            add_to_cart_button[i].setOnAction(actionEvent ->{
                for(int m=0;m<count[0];m++)
                {
                    try
                    {

                        String prodIdquery = "select product_id from products where product_name='"+ prod_names[finalI]+"';";
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



            } );

            HBox prodCount=new HBox();
            Button addProd=new Button("+");
            addProd.setStyle("-fx-border-color:grey;-fx-border-style: solid solid solid none;");
            Button reduceProd=new Button("- ");
            reduceProd.setTextAlignment(TextAlignment.CENTER);
            reduceProd.setStyle("-fx-border-color:grey;-fx-border-style: solid none solid solid;-fx-padding:5px 5px 3px 12px;");

            addProd.setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    count[0]++;
                    quantity.setText(Integer.toString(count[0]));
                }
            }));
            reduceProd.setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(count[0]!=1)
                    {
                        count[0]--;
                        quantity.setText(Integer.toString(count[0]));
                    }
                }
            }));
            int labelCount=count[0];


            quantity.setText(Integer.toString(labelCount));
            quantity.setStyle("-fx-border-color:grey;-fx-border-style: solid none solid none;-fx-padding:3px 3px 5px 3px;");
            quantity.setAlignment(Pos.CENTER);

            prodCount.getChildren().addAll(reduceProd,quantity,addProd);
            //prodCount.setTranslateX(65);
            prodCount.setTranslateY(20);

            cartFunctions.getChildren().addAll(add_to_cart_button[i],prodCount);

            tiles[i] = new VBox(10,hbxImg,prod_name,price,cartFunctions);
            //tiles[i].setAlignment(Pos.CENTER);
            tiles[i].setStyle("-fx-border-color: black;-fx-background-color:#e8dcf6;");
            tiles[i].setPrefWidth(200);
            tiles[i].setPrefHeight(250);
            tiles[i].setPadding(new Insets(10,10,50,10));
            tilePane.getChildren().add(tiles[i]);
        }
        tilePane.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().add(menubars);
        vbox.getChildren().add(filter);
        vbox.getChildren().add(tilePane);
        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        ScrollBar scroll = new ScrollBar();
        scroll.setMin(0);
        sp.setContent(vbox);
        Scene nscene =new Scene(sp, 800, 600);
        dashboard_scene = nscene;
        primaryStage.setScene(nscene);
        nscene.setRoot(sp);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    public  static void HightoLowFilter(Stage primaryStage,HBox menubars,HBox filter,String filter_query,String username) throws SQLException {
        //Statement HtoLstmt = con.createStatement();
        //Database_Connection dbcon = new Database_Connection();
        String HtoLquery= filter_query;
        ResultSet HtoLresultSet = dbcon.getQueryTable(HtoLquery);
        String HtoL_names[]=new String[700];
        String HtoL_prices[]=new String[700];
        String HtoL_imgs[]=new String[700];
        int HtoLnum=0;
        while(HtoLresultSet.next()) {
            HtoL_names[HtoLnum]=HtoLresultSet.getString("product_name");
            HtoL_prices[HtoLnum]=HtoLresultSet.getString("price");
            HtoL_imgs[HtoLnum]=HtoLresultSet.getString("product_img");
            HtoLnum++;
        }
        int finalHtoLnum = HtoLnum;
        display_products(primaryStage,HtoL_names,HtoL_prices,HtoL_imgs, finalHtoLnum,menubars,filter,username);
    }
    public  static void LowToHighFilter(Stage primaryStage,HBox menubars,HBox filter,String filter_query,String username) throws SQLException{
        //Statement LtoHstmt = con.createStatement();
        String LtoHquery = filter_query;
        ResultSet LtoHresultSet = dbcon.getQueryTable(LtoHquery);
        String LtoH_names[]=new String[700];
        String LtoH_prices[]=new String[700];
        String LtoH_imgs[]=new String[700];
        int LtoHnum=0;
        while(LtoHresultSet.next()) {
            LtoH_names[LtoHnum]=LtoHresultSet.getString("product_name");
            LtoH_prices[LtoHnum]=LtoHresultSet.getString("price");
            LtoH_imgs[LtoHnum]=LtoHresultSet.getString("product_img");
            LtoHnum++;
        }
        int finalLtoHnum = LtoHnum;
        display_products(primaryStage,LtoH_names,LtoH_prices,LtoH_imgs, finalLtoHnum,menubars,filter,username);

    }
    public static void place_order() throws SQLException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:SS");

        LocalDateTime now = LocalDateTime.now();

        String timestamp = dtf.format(now);
        String get_products = "select product_id,count(*) as qty from in_cart group by product_id";
        ResultSet prod_list = dbcon.getQueryTable(get_products);
        int count=0;

        while(prod_list.next())
        {
            count+=1;
        }
        //System.out.println(count);
        String[] product_id = new String[count];
        int[] qty =new int[count];

        int pos=0;

        ResultSet prod_list1 = dbcon.getQueryTable(get_products);

        while (prod_list1.next())
        {

            String prod_id = prod_list1.getString("product_id");
            int quant = prod_list1.getInt("qty");

            product_id[pos]=prod_id;
            qty[pos]=quant;

            System.out.println(prod_id);
            System.out.println(quant);

            pos+=1;
        }




        String find_order = "select order_id from orders where order_id regexp '^O_"+usern+"_';";

        ResultSet resultSet = dbcon.getQueryTable(find_order);
        int order_no=1;

        while (resultSet.next())
        {
            String orderid = resultSet.getString("order_id");
            order_no+=1;

        }
        String s_order_no=Integer.toString(order_no);

        String f_order_id = "O_"+usern+"_"+s_order_no;

        //Statement statement1 = con.createStatement();

        float total_amt=0;
        int total_prod=0;

        for(int i=0;i<product_id.length;i++)
        {
            String query = "select price from products where product_id='"+product_id[i]+"';";

            ResultSet resultSet1= dbcon.getQueryTable(query);

            float price=0;
            resultSet1.next();

            price = resultSet1.getFloat("price");
            System.out.println(product_id[i] + " "+ f_order_id);

            price = price*qty[i];
            total_amt+=price;

        }
        for(int i=0;i<qty.length;i++)
        {
            total_prod+=qty[i];
        }

        //Statement ins_order = con.createStatement();

        String ins_query = "insert ignore into orders values('"+f_order_id+"','"+usern+"',"+total_amt+","+total_prod+",'"+timestamp+"');";

        dbcon.insertUpdate(ins_query);

        for(int i=0;i<qty.length;i++)
        {
            for(int j=0;j<qty[i];j++) {


                String contains_query = "insert ignore into contains values('" + product_id[i] + "','" + f_order_id + "');";

                System.out.println("contains query "+ dbcon.insertUpdate(contains_query));
            }
        }


        for(int i=0;i<qty.length;i++)
        {
            for(int j=0;j<qty[i];j++)
            {
                //Statement statement2 = con.createStatement();
                String q = "delete from in_cart where username='" + usern + "' and product_id='" + product_id[i] + "';";
                dbcon.executeUpdate(q);
            }
        }
    }

}
