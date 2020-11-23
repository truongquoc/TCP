package com.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(5217);
            while (true) {
                System.out.println(("Waiting for Connection ... "));
                //Creating socket and waiting for client connection
                Socket soc = socket.accept();
//                DataOutputStream out = new DataOutputStream(soc.getOutputStream());
//                out.writeBytes("Server Date: "+ (new Date().toString())+"\n");
                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                System.out.println((new Date().toString()));
                out.writeObject("Server Date: "+ (new Date().toString()));
                out.close();
                soc.close();
            }
        }catch (IOException ex) {
            System.out.println((ex));
        } finally {

        }
    }
}
