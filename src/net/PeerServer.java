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
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PeerServer extends Peer {
	private ServerSocket serverSocket;
	private Socket client;
	private String serverAddress;
	private final int PORT = 8081;
	
	private PrintWriter writer ; 
	private BufferedReader reader ; 

	public PeerServer() {
		this.mode = Mode.SERVER;
		try {
			this.serverSocket = new ServerSocket(PORT);
			InetAddress localHost = InetAddress.getLocalHost();
			this.serverAddress = localHost.getHostAddress(); // get server's ip
		} catch (Exception e) {
			System.out.println("Fail to initialize server");
			e.printStackTrace();
		}
	}

	public void start() {
		new Thread(() -> {
			try {
				client = serverSocket.accept(); // is await so use in thread(async)

				startSendingPacketThread();
				startReceivePacketThread();

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

	protected void startSendingPacketThread() {

	}

	protected void startReceivePacketThread() {

	}

	public Socket getOtherPeer() {
		return this.client;
	}

	public PrintWriter getWriter() {
		return this.writer ; 
	}
	public BufferedReader getReader() {
		return this.reader ; 
	}
	public String getIp() {
		return this.serverAddress ; 
	}
	public int getPort() {
		return this.PORT ; 
	}

//	// Sending
//	private void startSendMessageThread(Socket client) {
//		Thread thread = new Thread(() -> {
//			Scanner scanner = new Scanner(System.in);
//			while (!serverSocket.isClosed()) {
//				// input message
//				System.out.print("Server : ");
//				String myMessage = scanner.nextLine();
//				// write to client
//				sendMessage(myMessage, client);
//			}
//			scanner.close();
//		});
//		thread.start();
//	}
//
//	private void sendMessage(String message, Socket client) {
//		try {
//			// NOTE :
//			// OutputStream -> write data to a destination (client)
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//			writer.write(message);
//			writer.newLine();
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// Receiving
//	private void startReceiveMessageThread(Socket client) {
//		// thread for receiving as long as
//		// client still connects to the server
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			Thread receiveThread = new Thread(() -> {
//				try {
//					String message;
//					while ((message = reader.readLine()) != null) {
//						if (message.isEmpty()) {
//							System.out.println("Empty message");
//						} else {
//							System.out.println("\nClient : " + message);
//							System.out.print("Server : ");
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			});
//			receiveThread.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}