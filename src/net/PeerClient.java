package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class PeerClient extends Peer {
	private String ipaddress;
	private int port;
	private Socket socket; // represent connection to server
	private PrintWriter writer ; 
	private BufferedReader reader ; 
	

	public PeerClient(String ipaddress, int port) {
		this.mode = Mode.CLIENT;
		this.ipaddress = ipaddress;
		this.port = port;
	}

	public void start() {
		try {
			// connecting to server
			socket = new Socket(ipaddress, port); // this becomes reference for server
			// initialize writer & reader
			this.writer = new PrintWriter(this.getOtherPeer().getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(this.getOtherPeer().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Socket getOtherPeer() {
		return this.socket ; 
	}
	public PrintWriter getWriter() {
		return this.writer ; 
	}
	public BufferedReader getReader() {
		return this.reader ; 
	}



	// Sending
//	private void startSendMessageThread() {
//		Thread thread = new Thread(() -> {
//			Scanner scanner = new Scanner(System.in);
//			while (!socket.isClosed()) {
//				// input message
//				System.out.print("Client : ");
//				String myMessage = scanner.nextLine();
//				// write to client
//				sendMessage(myMessage);
//			}
//			System.out.println("Sending message thread done.");
//			scanner.close();
//		});
//		thread.start();
//	}
//
//	private void sendMessage(String message) {
//		try {
//			// NOTE :
//			// OutputStream -> write data to a destination (server)
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			writer.write(message);
//			writer.newLine();
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	// Receiving
//	private void startReceiveMessageThread() {
//		// thread for receiving as long as
//		// client still connects to the server
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			Thread receiveThread = new Thread(() -> {
//				try {
//					String message;
//					while ((message = reader.readLine()) != null) {
//						if (message.isEmpty()) {
//							System.out.println("Empty message");
//						} else {
//							System.out.println("\nServer : " + message);
//							System.out.print("Client : ");
//						}
//					}
//					System.out.println("Receiving message thread done.");
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
