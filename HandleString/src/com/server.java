package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    private static final int PORT = 3306;
    private static ArrayList<Clienthandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) {
//        String test = "There are lots of wide variety small creatures";
        try {
            ServerSocket listener = new ServerSocket(PORT);
            System.out.println("Waiting for the client connection");
            while (true) {
                Socket socket = listener.accept();
                System.out.println("[SERVER] Connected to client");
//                String msg = (String) sInput.readObject();
//                StringHandler stringHandler = new StringHandler(ToUpperCase(msg), ToLowerCase(msg), ToLowerUpper(msg), NumberOfWords(msg), NumberOfVowel(msg));
//                sOutput.writeObject(stringHandler);
//                sInput.close();
//                sOutput.close();
                Clienthandler clientThread = new Clienthandler(socket);

                clients.add(clientThread);
                pool.execute(clientThread);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
//        System.out.println(ToLowerUpper(test));
    }


}

class Clienthandler implements Runnable {
    private Socket client;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;

    public Clienthandler(Socket clientSocket) {
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
    public static String ToUpperCase(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=97 && c<=122) {
                c-=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static String ToLowerCase(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=65 && c<=90) {
                c+=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static  String ToLowerUpper(String str) {
        String convStr = "";
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c>=65 && c<=90) {
                c+=32;
            }
            else if(c>=97 && c<=122) {
                c-=32;
            }
            convStr+=c;
        }
        return convStr;
    }

    public static int NumberOfWords(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(str);
        return tokens.countTokens();
    }

    public static  int NumberOfVowel(String str) {
        int count = 0;
        char c;
        for(int index=0; index< str.length(); index++) {
            c = str.charAt(index);
            if(c== 'a' || c=='e' || c=='i' || c=='o' || c=='u') {
                count++;
            }
        }
        return count;
    }

    @Override
    public void run()  {
        try {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    StringHandler stringHandler = new StringHandler(ToUpperCase(msg), ToLowerCase(msg), ToLowerUpper(msg), NumberOfWords(msg), NumberOfVowel(msg));
                    sOutput.writeObject(stringHandler);
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

}