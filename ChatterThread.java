import java.io.*;
import java.net.*;
/**
 *    Gabriel Nugent
 *    Assignment: Chatty
 *    CS 3354.255
 *    04-03-2022
 */

/**
 *    separate thread in charge of IO for client to server interaction
 */
public class ChatterThread extends Thread{

  private Socket client;
  private ChatServer server;
  private DataOutputStream outStream;

  ChatterThread(Socket socket,ChatServer chatServer){
    client = socket;
    server = chatServer;
  }

  /**
   *    -continually checks for new messages in the input stream
   *    and sends them to the server for broadcasting
   */
  public void run() {
    try {
      DataInputStream inStream = new DataInputStream(client.getInputStream());
      outStream = new DataOutputStream(client.getOutputStream());

      while(true)
      {
        String message = inStream.readUTF();
        server.broadcast(message);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String chatMessage) throws IOException {
    outStream.writeUTF(chatMessage);
  }

}
