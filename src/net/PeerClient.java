package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class PeerClient extends Peer {
    private InetAddress address ; 
    private int port ; 
    private Socket socket ; // represent connection to server

    public PeerClient(InetAddress address , int port) {
        this.mode = Mode.CLIENT ; 
        this.address = address ; 
        this.port = port ; 
    }
    public void start() {
        try {
            // connecting to server
            socket = new Socket(address , port); // this becomes reference for server
            // init actions
            startSendMessageThread();
            startReceiveMessageThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        if(socket != null) {
            try {
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Sending
    private void startSendMessageThread() {
        Thread thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(!socket.isClosed()) {
                // input message
                System.out.print("Client : ");
                String myMessage = scanner.nextLine();
                // write to client
                sendMessage(myMessage);
            }
            scanner.close();
        });
        thread.start();
    }
    private void sendMessage(String message) {
        try {
            // NOTE : 
            // OutputStream -> write data to a destination (server)
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(message);
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    // Receiving
    private void startReceiveMessageThread() {
        // thread for receiving as long as 
        // client still connects to the server
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread receiveThread = new Thread(() -> {
               try {
                    String message ;
                    while((message = reader.readLine()) != null) {
                        System.out.println("Server : " + message);
                    }
               } catch(IOException e) {
                    e.printStackTrace();
               }
            });
            receiveThread.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
