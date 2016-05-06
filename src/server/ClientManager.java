package server;

import util.TextMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mreilaender
 * @version 03.05.2016
 */
public class ClientManager {
    private static ConcurrentHashMap<Socket, ObjectOutputStream> clients = new ConcurrentHashMap<>();

    public void printClients() {
        for (Map.Entry<Socket, ObjectOutputStream> client: clients.entrySet()) {
            System.out.println(client.getKey().getRemoteSocketAddress().toString());
        }
    }

    public static void addClient(Socket client) throws IOException {
        clients.put(client, new ObjectOutputStream(client.getOutputStream()));
    }

    public static ObjectOutputStream getClientOutputstream(Socket client) {
        return clients.get(client);
    }

    public static void removeClient(Socket client) {
        clients.remove(client);
    }

    public static synchronized void sendBroadcastMessage(TextMessage message) throws IOException {
        for (Map.Entry<Socket, ObjectOutputStream> client: clients.entrySet()) {
            client.getValue().writeObject(message);
        }
    }
}
