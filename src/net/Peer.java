package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

// Using TCP because it is turn-based
public abstract class Peer {
    protected Mode mode ; 
    // need overwrite
    public abstract void start();
    public abstract void close();
    
    // communication methods
    public abstract Socket getOtherPeer();
    public abstract PrintWriter getWriter();
    public abstract BufferedReader getReader();
    
    public Mode getMode() {
    	return this.mode ; 
    }
}
