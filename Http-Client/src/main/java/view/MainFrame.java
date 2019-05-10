package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.Connection;
import exception.BsuirException;
import model.HttpRequest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame {

    private static Logger lOGGER = LogManager.getLogger(MainFrame.class.getName());
    private HttpRequest request = new HttpRequest();//initialized port, host and etc
    private Connection connection = new Connection();
    private  String[] args;

    private Stage primaryStage;
    private VBox vBox;
    private HBox hBox;
    private HBox hBoxContent;
    private HBox[] hBox1;
    private HBox settings;
    private VBox vBoxParams;
    private VBox out;
    private VBox input;

    private TextField urlField;
    private TextField numParams;
    private TextField[] params;
    private TextField[] vals;

    private TextArea memo;
    private ToggleGroup group;
    private Button createParamFields;
    private Button sentRequest;
    private RadioButton post;
    private RadioButton head;
    private RadioButton  get;

    public MainFrame() {
        ScrollPane sp = new ScrollPane();

        primaryStage = new Stage();
        //sp.setContent(prim);
        vBox = new VBox();
        hBox = new HBox();
        hBoxContent = new HBox();
        settings = new HBox();
        vBoxParams = new VBox();
        out = new VBox();
        createParamFields = new Button("Create fields");
        numParams = new TextField();

        input = new VBox();
        input.getChildren().addAll(settings, vBoxParams);
        input.setSpacing(30);

        settings.getChildren().addAll(numParams, createParamFields);
        settings.setSpacing(20);
        settings.setVisible(false);

        //numParams.setMinSize(25,25);
        numParams.setMaxSize(30,35);
        createParamFields.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int nums = Integer.parseInt(numParams.getText());
                createParamFields(nums);
                vBoxParams.setVisible(true);
            }
        });

        urlField = new TextField();
        urlField.setMaxSize(600,25);

        memo = new TextArea("");
        memo.setMinSize(500, 500);
        sp.setContent(memo);

        group = new ToggleGroup();
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(post.isSelected()){
                    settings.setVisible(true);
                    vBoxParams.setVisible(true);
                } else {
                    settings.setVisible(false);
                    vBoxParams.setVisible(false);
                }
            }
        });

        get = new RadioButton("GET");
        get.setToggleGroup(group);
        get.setUserData("GET");

        sentRequest = new Button("Send request");
        createParamFields.setMinSize(100,35);

        post = new RadioButton ("POST");
        post.setToggleGroup(group);
        post.setUserData("POST");

        head = new RadioButton("HEAD");
        head.setToggleGroup(group);
        head.setUserData("HEAD");

        get.setSelected(true);

        sentRequest.setMinSize(100, 35);

        hBox.getChildren().addAll(get, head, post, sentRequest);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.TOP_CENTER);

        out.getChildren().addAll(urlField, memo);
        out.setSpacing(30);

        hBoxContent.getChildren().addAll(input, out);
        hBoxContent.setSpacing(50);

        vBox.setPadding(new Insets(20, 40, 20, 40));
        vBox.getChildren().addAll(hBox, hBoxContent);
        vBox.setSpacing(20);
    }

    public void drawWindow() {

        sentRequest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String queryParams = "";
                if(post.isSelected()){
                    for (int i = 0; i < Integer.parseInt(numParams.getText()); i++) {
                        if (i == 0) {
                            queryParams += params[i].getText() + "=" + vals[i].getText();
                        } else {
                            queryParams += "&" + params[i].getText() + "=" + vals[i].getText();
                        }
                    }
                    memo.setText("");
                    memo.setText(connection.connection(group.getSelectedToggle().getUserData().toString(), queryParams, request, urlField.getText()));
                }else {
                        memo.setText(connection.connection(group.getSelectedToggle().getUserData().toString(), queryParams, request, urlField.getText()));
                    }
                } catch (BsuirException | IllegalArgumentException e) {
                    lOGGER.debug("IllegalAccessException | InterruptedException | IOException | IllegalArgumentException | ParseException in connection");
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Http - Client");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void setMemo(String s) {
        memo.setText(memo.getText() + "\n" + s);
    }

    public void createParamFields(int count){
        params = new TextField[count];
        vals = new TextField[count];
        hBox1 = new HBox[count];
        for(int i = 0; i < count; i++){
            params[i] = new TextField();
            vals[i] = new TextField();
            hBox1[i] = new HBox();
            params[i].setMaxSize(170,25);
            vals[i].setMaxSize(170,25);
            hBox1[i].getChildren().addAll(params[i], vals[i]);
            hBox1[i].setSpacing(15);
        }
        vBoxParams.getChildren().addAll(hBox1);
        vBoxParams.setSpacing(10);
    }
}

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class view.MainFrame {
//
//    private static Logger lOGGER = LogManager.getLogger(view.MainFrame.class.getName());
//    private HttpRequest request = new HttpRequest();//initialized port, host and etc
//	private Connection connection = new Connection();
//
//    private JScrollPane sp;
//
//    private JFrame frame;
//    private JPanel pane;
//
//    private JTextArea memo;
//
//    private ButtonGroup group;
//
//    private JButton sentRequest;
//    private JButton clear;
//
//    private JRadioButton post;
//    private JRadioButton get;
//    private JRadioButton head;
//
//    public view.MainFrame() {
//        buildContent();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//    }
//
//    public void buildContent(){
//        frame = new JFrame("Http-Client app");
//        frame.setSize(500,700);
//
//        sp = new JScrollPane(memo);
//        frame.getContentPane().add(sp);
//
//        sentRequest = new JButton("Sent request");
//        clear = new JButton("Clear");
//
//        pane = new JPanel();
//        frame.getContentPane().add(pane);
//
//        memo = new JTextArea("", 20,60);
//        get = new JRadioButton("GET");
//        get.setActionCommand("GET");
//        post = new JRadioButton("POST");
//        post.setActionCommand("POST");
//        head = new JRadioButton("HEAD");
//        head.setActionCommand("HEAD");
//        group = new ButtonGroup();
//
//        group.add(get);
//        group.add(head);
//        group.add(post);
//
//        pane.add(get, BorderLayout.PAGE_START);
//        pane.add(head, BorderLayout.PAGE_START);
//        pane.add(post, BorderLayout.PAGE_START);
//        pane.add(clear, BorderLayout.PAGE_START);
//        pane.add(sentRequest, BorderLayout.PAGE_START);
//        pane.add(memo, BorderLayout.CENTER);
//
//    }
//
//    public void drawWindow() {
//
//        clear.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                memo.setText("");
//            }
//        });
//
//        sentRequest.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//
//                try {
//                   /* memo.setText(*/connection.connection(group.getSelection().getActionCommand(), request, memo.getText());//);
//                } catch (BsuirException | IllegalArgumentException e) {
//                    lOGGER.debug("IllegalAccessException | InterruptedException | IOException | IllegalArgumentException | ParseException in connection");
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
