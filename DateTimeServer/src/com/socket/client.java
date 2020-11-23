package com.socket;

import javax.swing.*;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Date;

public class client {
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        JFrame f = new JFrame("Server DateTime");
        JLabel l1, l2;
            Socket socket = new Socket(InetAddress.getLocalHost(), 5217);
//            BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String message = (String) ois.readObject();
            l1 = new JLabel(""+ message);
          l1.setBounds(50, 50, 300, 30);
            f.add(l1);
            f.setSize(600, 600);
            f.setLayout(null);
            f.setVisible(true);

    }
}
