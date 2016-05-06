package server;

import util.Message;
import util.TextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                    }
                }
            }
        } catch (IOException e) {
            if(e instanceof SocketException) {
                System.out.println(String.format("Client%d disconnected", ((InetSocketAddress)client.getRemoteSocketAddress()).getPort()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
