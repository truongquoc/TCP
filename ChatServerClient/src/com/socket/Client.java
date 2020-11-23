package com.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.*;
public class Client extends JFrame implements ActionListener {
private ObjectOutputStream oos = null;
private ObjectInputStream ois = null;
private JFrame login, main;
private Label label1, label2;
private JPanel loginPanel, mainPanel;
private JButton loginButton, sendButton;
private TextField username, chat;
private JScrollPane sp;
private JTextArea content;
String temp="";
public Client(String title) throws IOException, ClassNotFoundException {
    super(title);
    GUI();
    Socket socket = new Socket("127.0.0.1", 3306);
    oos = new ObjectOutputStream(socket.getOutputStream());
    ois = new ObjectInputStream(socket.getInputStream());
    while (true) {

        String serverResponse = (String) ois.readObject();
        temp = temp + serverResponse+ "\n";
        content.setText(temp);
        System.out.println(serverResponse);
    }
}
public void GUI() {
    loginPanel = new JPanel();
    loginPanel.setLayout(null);
    login = new JFrame();
    login.setTitle("Login");
    login.setSize(600,200);

    mainPanel = new JPanel();
    main = new JFrame();
    main.setTitle("Main");
    main.setSize(600, 500);
    main.getContentPane().setLayout(null);
    main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    label1 = new Label("Enter your name");
    label1.setBounds(37, 13, 127,16);
    username = new TextField();
    username.setBounds(176, 10, 169, 22);

    loginButton = new JButton("Enter");
    loginButton.addActionListener(this);
    loginButton.setBounds(413, 9, 97, 25);
    loginPanel.add(loginButton);
    loginPanel.add(label1);
    loginPanel.add(username);
    login.add(loginPanel);

    content = new JTextArea();
    chat = new TextField();
    chat.setBounds(37, 400, 400, 30);

    sendButton = new JButton("Send");
    sendButton.addActionListener(this);
    sendButton.setBounds(450, 400, 80 ,40);
    sp = new JScrollPane(content);
    sp.setBounds(50, 50, 500, 200);
    main.add(chat);
    main.add(sendButton);
    main.add(sp);
    login.setVisible(true);
    main.setVisible(false);

}
    public static void main(String[] args) throws Exception {
       new Client("Chat");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            login.setVisible(false);
            main.setVisible(true);
        }
        if(e.getSource() == sendButton) {
            temp+= username.getText() + ": " + chat.getText() + "\n";
            content.setText(temp);
            try {
                oos.writeObject(username.getText()+": "+ chat.getText());
                chat.setText("");
                chat.requestFocus();
                content.setVisible(false);
                content.setVisible(true);
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}

