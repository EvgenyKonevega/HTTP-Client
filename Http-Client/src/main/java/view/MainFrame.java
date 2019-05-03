package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.Connection;
import exception.BsuirException;
import model.HttpRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private static Logger lOGGER = LogManager.getLogger(MainFrame.class.getName());
    private HttpRequest request = new HttpRequest();//initialized port, host and etc 
	private Connection connection = new Connection();

    private JScrollPane sp;

    private JFrame frame;
    private JPanel pane;

    private JTextArea memo;

    private ButtonGroup group;

    private JButton sentRequest;
    private JButton clear;

    private JRadioButton post;
    private JRadioButton get;
    private JRadioButton head;

    public MainFrame() {
        buildContent();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void buildContent(){
        frame = new JFrame("Http-Client app");
        frame.setSize(500,700);

        sp = new JScrollPane(memo);
        frame.getContentPane().add(sp);

        sentRequest = new JButton("Sent request");
        clear = new JButton("Clear");

        pane = new JPanel();
        frame.getContentPane().add(pane);

        memo = new JTextArea("", 20,60);
        get = new JRadioButton("GET");
        get.setActionCommand("GET");
        post = new JRadioButton("POST");
        post.setActionCommand("POST");
        head = new JRadioButton("HEAD");
        head.setActionCommand("HEAD");
        group = new ButtonGroup();

        group.add(get);
        group.add(head);
        group.add(post);

        pane.add(get, BorderLayout.PAGE_START);
        pane.add(head, BorderLayout.PAGE_START);
        pane.add(post, BorderLayout.PAGE_START);
        pane.add(clear, BorderLayout.PAGE_START);
        pane.add(sentRequest, BorderLayout.PAGE_START);
        pane.add(memo, BorderLayout.CENTER);

    }

    public void drawWindow() {

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memo.setText("");
            }
        });

        sentRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                try {
                   /* memo.setText(*/connection.connection(group.getSelection().getActionCommand(), request, memo.getText());//);
                } catch (BsuirException | IllegalArgumentException e) {
                    lOGGER.debug("IllegalAccessException | InterruptedException | IOException | IllegalArgumentException | ParseException in connection");
                    e.printStackTrace();
                }
            }
        });
    }
}