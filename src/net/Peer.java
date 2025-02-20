package net;

// Using TCP because it is turn-based
public abstract class Peer {
    protected Mode mode ; 
    // need overwrite
    protected abstract void start();
    protected abstract void close();
}
