package client;

import util.Config;
import util.Message;
import util.TextMessage;

import javax.xml.soap.Text;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author mreilaender
 * @version 06.05.2016
 */
public class Client {
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean running;
    private InputStream localInput;

    public Client() {
        running = true;
        this.localInput = System.in;
    }

    public void start() {
        try {
            client = new Socket(Config.serverIp, Config.serverPort);
            System.out.println("Connected to server");
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            new Thread(new MessageListener(System.in, outputStream)).start();
            while (running) {
                Message message = null;
                while((message = (Message) inputStream.readObject()) != null) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println(String.format("%s: %s", textMessage.getSender(), textMessage.getMessage()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
