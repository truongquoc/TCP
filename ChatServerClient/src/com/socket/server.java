package com.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    private static ArrayList<Clienthandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static final int PORT = 3306;

    public static void main(String[] args) {
       try {
           ServerSocket listener = new ServerSocket(PORT);
           System.out.println("Waiting for the client request");

           while (true) {
               //create socket and waiting for client connection
               Socket socket = listener.accept();
               System.out.println("[SERVER] Connected to client");
               Clienthandler clientThread = new Clienthandler(socket);
               clients.add(clientThread);
               pool.execute(clientThread);
           }
       }
       catch (IOException e) {
           System.out.println(e);
       }

    }

}
class Clienthandler extends JFrame implements Runnable, ActionListener {
    private Socket client;
    private Socket dataClientSocket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private JFrame main;
    private JButton sendButton;
    private TextField chat;
    private JScrollPane sp;
    private JTextArea content;
    String temp="";
    public Clienthandler(Socket clientSocket) {
        super("Server Side");
        GUI();
        this.client = clientSocket;
        try {
            sInput = new ObjectInputStream(client.getInputStream());
            sOutput = new ObjectOutputStream(client.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e);
            return;
        }

    }
    @Override
    public void run()  {
        try {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    temp = temp + msg+ "\n";
                    content.setText(temp);
                }
                catch (IOException e) {
                    System.out.println(e);
                    return;
                }
                catch (ClassNotFoundException e) {
                }
            }
        }
        finally {
            try {
                sInput.close();
                sOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void GUI() {
        main = new JFrame();
        main.setTitle("Server");
        main.setSize(600, 500);
        main.getContentPane().setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        main.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendButton) {
            temp+="[SERVER]: "+ chat.getText() + "\n";
            content.setText(temp);
            try {
                sOutput.writeObject("[SERVER]: "+ chat.getText());
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
