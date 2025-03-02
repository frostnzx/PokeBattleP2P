package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import game.GameSystem;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PeerServer extends Peer {
    private ServerSocket serverSocket;
    private Socket client;
    private String serverAddress;
    private final int PORT = 8085;
    
    private PrintWriter writer;
    private BufferedReader reader;

    private OnClientConnectedListener clientConnectedListener; // Callback for client connection

    // Define the interface for the listener
    public interface OnClientConnectedListener {
        void onClientConnected(Socket client);
    }

    // Set the listener to update UI
    public void setOnClientConnectedListener(OnClientConnectedListener listener) {
        this.clientConnectedListener = listener;
    }

    public PeerServer() {
        this.mode = Mode.SERVER;
        try {
            this.serverSocket = new ServerSocket(PORT);
            // Get public IP
            URL url = new URL("http://checkip.amazonaws.com");
            Scanner scanner = new Scanner(url.openStream());
            this.serverAddress = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Fail to initialize server");
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(() -> {
            try {
                client = serverSocket.accept(); // Wait for client to connect

                // After accepting the connection, notify the listener
                if (clientConnectedListener != null) {
                    Platform.runLater(() -> {
                        clientConnectedListener.onClientConnected(client);
                    });
                }

                // Set up I/O streams
                this.writer = new PrintWriter(this.getOtherPeer().getOutputStream());
                this.reader = new BufferedReader(new InputStreamReader(this.getOtherPeer().getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (Exception e) {
                System.out.println("Fail to close server");
                e.printStackTrace();
            }
        }
    }

    public Socket getOtherPeer() {
        return this.client;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public String getIp() {
        return this.serverAddress;
    }

    public int getPort() {
        return this.PORT;
    }
}
