package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PeerServer extends Peer {
    private ServerSocket serverSocket;
    private InetAddress serverAddress ; 
    private final int PORT = 8080 ; 

    public PeerServer() {
        this.mode = Mode.SERVER ; 
        try {
            this.serverSocket = new ServerSocket(PORT);
            this.serverAddress = InetAddress.getLocalHost() ; // get server's ip
        } catch (Exception e) {
            System.out.println("Fail to initialize server");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            
            // get client to connect
            Socket client = serverSocket.accept();
            // init actions
            startSendMessageThread(client);
            startReceiveMessageThread(client);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch(Exception e) {
                System.out.println("Fail to close server");
                e.printStackTrace();
            }
        }
    }

    // Sending
    private void startSendMessageThread(Socket client) {
        Thread thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(!serverSocket.isClosed()) {
                // input message
                System.out.print("Server : ");
                String myMessage = scanner.nextLine();
                // write to client
                sendMessage(myMessage , client);
            }
            scanner.close();
        });
        thread.start();
    }
    private void sendMessage(String message , Socket client) {
        try {
            // NOTE : 
            // OutputStream -> write data to a destination (client)
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write(message);
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Receiving
    private void startReceiveMessageThread(Socket client) {
        // thread for receiving as long as 
        // client still connects to the server
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Thread receiveThread = new Thread(() -> {
               try {
                    String message ;
                    while((message = reader.readLine()) != null) {
                        System.out.println("Client : " + message);
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