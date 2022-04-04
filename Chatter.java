import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
/**
 *    Gabriel Nugent
 *    Assignment: Chatty
 *    CS 3354.255
 *    04-03-2022
 */


/**
 *    chatter client from which users send messages to the server
 */
public class Chatter extends JFrame implements ActionListener,Runnable {

  private int port;
  private String name;
  private Socket client;
  private DataOutputStream outStream;
  private DataInputStream inStream;
  private JButton button;
  private JTextField field;
  private JTextArea chatBox;

  Chatter(String chatterName,int portNum) throws IOException {
    name = chatterName;
    port = portNum;

    createChatter(chatterName);
  }

  /**
   *    -checks for messages in the input stream
   *    and appends them to the text area
   */
  public void run() {
    try {
      while (true) {
        chatBox.append(inStream.readUTF());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   *    -creates client
   *    -connects client to server
   *    -sends welcome message to open clients
   */
  private void createChatter(String chatterName) throws IOException {
    try{

      client =  new Socket("127.0.0.1",port);
      outStream = new DataOutputStream(client.getOutputStream());
      inStream = new DataInputStream(client.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setTitle(chatterName);
    this.setSize(300, 385);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new FlowLayout());

    button = new JButton("Send");
    button.addActionListener(this);

    field = new JTextField();
    field.setPreferredSize(new Dimension(200, 30));

    chatBox = new JTextArea();
    chatBox.setPreferredSize(new Dimension(260,300));

    this.add(chatBox);
    this.add(field);
    this.add(button);
    this.setVisible(true);

    outStream.writeUTF("[" + name + " has entered the server]\n");
  }

  /**
   *    -checks for button press
   *    -sends message in text field to all clients
   */
  public void actionPerformed(ActionEvent e){
    if (e.getSource() == button) {
      String message = "[" + name + "]: " + field.getText() + "\n";
      field.setText("");
      try {
        outStream.writeUTF(message);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

}
