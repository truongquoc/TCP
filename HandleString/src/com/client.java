package com;

import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            while (true) {
                Socket socket = new Socket("127.0.0.1", 3306);
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("Enter message>");
                String command = keyboard.readLine();
                oos.writeObject(command);
                StringHandler stringHandler = (StringHandler) ois.readObject();
                System.out.println(stringHandler.toString());
                oos.close();
                ois.close();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {

        }
        finally {
            try {
                oos.close();
            }catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
