package server;

import util.Config;
import util.FileMessage;
import util.Message;
import util.TextMessage;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author mreilaender
 * @version 03.05.2016
 */
public class ClientHandler implements Runnable {
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.objectInputStream = new ObjectInputStream(this.client.getInputStream());
            this.objectOutputStream = ClientManager.getClientOutputstream(client);

            System.out.println("Waiting for client messages");
            while(true) {
                Message message = null;
                while((message = (Message) this.objectInputStream.readObject()) != null) {
                    switch (message.getType()) {
                        case TEXTMESSAGE:
                            System.out.println(String.format("Text message received from client %s", this.client.getRemoteSocketAddress().toString()));
                            TextMessage textMessage = (TextMessage) message;
                            textMessage.setSender(String.format("Client%d", ((InetSocketAddress)client.getRemoteSocketAddress()).getPort()));
                            ClientManager.sendBroadcastMessage(textMessage);
                            break;
                        case FILE:
                            FileMessage fileMessage = (FileMessage)message;
                            System.out.println("File received from client");
                            System.out.println("Filename: " + fileMessage.getFilename());
                            FileManager.saveFile((FileMessage)message);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            if(e.getMessage().contains("Connection reset"))
                System.out.println(String.format("Client%d disconnected", ((InetSocketAddress)client.getRemoteSocketAddress()).getPort()));
            else
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
