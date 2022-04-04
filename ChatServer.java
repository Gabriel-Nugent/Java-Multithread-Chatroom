import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 *    Gabriel Nugent
 *    Assignment: Chatty
 *    CS 3354.255
 *    04-03-2022
 */

/**
 *    server for chatroom, broadcasts messages to clients
 */
public class ChatServer extends Thread{

  private ArrayList<ChatterThread> chatterThreads;
  private int port;

  ChatServer(int portNum){
    port = portNum;

  }

  /**
   *    -continually checks for new clients and adds them to the server
   *    -creates new client IO thread and adds to arraylist
   */
  public void run(){
    try {
      ServerSocket server = new ServerSocket(port);
      chatterThreads = new ArrayList<ChatterThread>();
      // runs while chatroom frame remains open
      while (true) {
        Socket client = server.accept();
        ChatterThread thread = new ChatterThread(client,this);
        thread.start();
        chatterThreads.add(thread);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   *    -sends message from input stream to all clients in server
   */
  public void broadcast(String message) throws IOException {
    for (ChatterThread thread : chatterThreads){
      thread.sendMessage(message);
    }
  }
}


