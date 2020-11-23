package com;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

public class client extends JFrame implements ActionListener{
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    Panel contentPane;
    TextField textField;
    Button btnNewButton;
    TextArea textArea;
    Choice choice;
    Button btnNewButton_1;
    public client(String title) {
        super(title);
        GUI();
    }
    public void GUI() {
        setBounds(100, 100, 581, 299);
        contentPane = new Panel();
        contentPane.setLayout(null);
        add(contentPane);
        Label lblNewLabel = new Label("Nhập số tự nhiên n:");
        lblNewLabel.setBounds(37, 13, 127, 16);
        contentPane.add(lblNewLabel);

        textField = new TextField();
        textField.setBounds(176, 10, 169, 22);
        contentPane.add(textField);
        textField.setColumns(10);
        textArea = new TextArea();
        btnNewButton = new Button("Enter");
        btnNewButton.addActionListener(this);
        btnNewButton.setBounds(413, 9, 97, 25);
        contentPane.add(btnNewButton);


        JLabel lblNewLabel_2 = new JLabel("Result: ");
        lblNewLabel_2.setBounds(37, 127, 97, 16);
        contentPane.add(lblNewLabel_2);
        textArea.setBounds(176, 124, 300, 92);
        contentPane.add(textArea);
        setVisible(true);
    }
    public static void main(String[] args) {
        new client("Infix to PostFix");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewButton) {
            textArea.getText();
//            textArea.setText("");
            String msg = textField.getText();

           try {
               Socket socket = new Socket("127.0.0.1", 3306);
               BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
               oos = new ObjectOutputStream(socket.getOutputStream());
               ois = new ObjectInputStream(socket.getInputStream());
               oos.writeObject(msg);
               String serverResponse = (String) ois.readObject();
               System.out.println(serverResponse);
               textArea.setText(serverResponse);
           } catch (IOException ex) {
               System.out.println(ex);
           } catch (ClassNotFoundException ex) {

           }
        }
    }
}
