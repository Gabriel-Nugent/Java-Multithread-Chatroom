import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *    Gabriel Nugent
 *    Assignment: Chatty
 *    CS 3354.255
 *    04-03-2022
 */

/**
 *    main class of application
 */
public class ChatRoom {

  private static ArrayList<Chatter> chatters;
  private static JTextField field;

  ChatRoom(){
    chatters = new ArrayList<Chatter>();
  }

  /**
   *   -creates main chatroom and starts server for transferring messages
   */
  public static void main(String [] args) throws IOException {
    chatters = new ArrayList<Chatter>();

    JFrame chatController = new JFrame("Chatroom");
    chatController.setSize(350, 80);
    chatController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    chatController.setLayout(new FlowLayout());

    field = new JTextField();
    field.setPreferredSize(new Dimension(200, 30));

    JButton button = new JButton("Add User");
    // starts new chatter client on button click
    button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            addChatter(field.getText(), 1000);
            field.setText("");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
    });

    chatController.add(field);
    chatController.add(button);
    chatController.setVisible(true);

    ChatServer server = new ChatServer(1000);
    server.start();
  }

  /**
   *    -Instantiates new chatter with thread for IO and starts it
   */
  public static void addChatter(String chatterName, int port) throws IOException {
    Chatter newChatter = new Chatter(chatterName, port);
    Thread clientThread = new Thread(newChatter);

    clientThread.start();
    chatters.add(newChatter);
  }
}
