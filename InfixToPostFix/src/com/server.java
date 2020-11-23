package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server implements Runnable{
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

    @Override
    public void run() {

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
    static int Prec(char ch) {
        switch (ch) {
            case '+' :
            case '-' :
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return -1;
    }
    static String InfixToPostFix(String str) {
        String result = new String("");
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if(Character.isDigit(c)) {
                result+=c;
            }
            else {
                if (c=='(') {
                    stack.push(c);
                }
                else if (c==')') {
                    while(!stack.isEmpty() && stack.peek() !='(') {
                        result+=stack.pop();
                    }
                    stack.pop();
                }
                //Encountered an Operator
                else {
                    while(!stack.isEmpty() && Prec(c)<= Prec(stack.peek())) {
                        result+=stack.pop();
                    }
                    stack.push(c);
                }
            }
        }
        //Pop all the operator from stack
        while(!stack.isEmpty()) {
            result+=stack.pop();
        }
        return result;
    }

    static int evaluatePostfixed(String exp) {
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<exp.length(); i++) {
            char c = exp.charAt(i);
            if(Character.isDigit(c)) {
                stack.push(c - '0');
            }
            else {
                int val1 = stack.pop();
                int val2 = stack.pop();
                switch (c) {
                    case '+':
                        stack.push(val1+val2);
                        break;
                    case '-':
                        stack.push(val2-val1);
                        break;
                    case '*':
                        stack.push(val1*val2);
                        break;
                    case  '/':
                        stack.push(val2/val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    @Override
    public void run()  {
        try {
            while(true) {
                try {
                    String result = "";

                    String msg = (String) sInput.readObject();
                        String PostFix = InfixToPostFix(msg);

                    sOutput.writeObject("PostFix: "+PostFix+"\n"+"result: "+evaluatePostfixed(PostFix));
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