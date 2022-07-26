import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;



public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;

    public Client(Socket sk, String name) {
        try {
            this.socket=sk;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=name;
        }catch(IOException e) {
            closeEverything(socket, reader, writer);
        }
    }

    public void sendMessage() {
        try {
            writer.write(username);
            writer.newLine();
            writer.flush();
            Scanner sc = new Scanner(System.in);
            while(socket.isConnected()) {
                String messageToSend= sc.nextLine();
                writer.write(username+": "+messageToSend);
                writer.newLine();
                writer.flush();
            }
        }catch(IOException e) {
            closeEverything(socket, reader, writer);
        }
    }
    public void listenForMessage() {
        new Thread (new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()) {
                    try {
                        msgFromGroupChat=reader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e) {
                        closeEverything(socket, reader, writer);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket,BufferedReader read, BufferedWriter write) {
        try {
            if(read!=null) {
                reader.close();
            }
            if(write!=null) {
                write.close();
            }
            if(socket!=null) {
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {


        Scanner sc= new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username =sc.nextLine();
        Socket socket= new Socket("localhost", 1234);
        Client client= new Client(socket,username);
        client.listenForMessage();
        client.sendMessage();
    }
}
