package client;

import util.Config;
import util.FileMessage;
import util.Message;
import util.TextMessage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                    switch (message.getType())
                    {
                        case TEXTMESSAGE:
                            TextMessage textMessage = (TextMessage) message;
                            System.out.println(String.format("%s: %s", textMessage.getSender(), textMessage.getMessage()));
                            break;
                    }
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
