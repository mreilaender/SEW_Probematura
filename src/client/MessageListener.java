package client;

import util.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author mreilaender
 * @version 06.05.2016
 */
public class MessageListener implements Runnable {
    private Scanner inputStreamScanner;
    private ObjectOutputStream outputStream;
    private boolean running;

    public MessageListener(InputStream inputStream, ObjectOutputStream outputStream) {
        inputStreamScanner = new Scanner(inputStream);
        this.outputStream = outputStream;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            while (inputStreamScanner.hasNext()) {
                try {
                    this.outputStream.writeObject(new TextMessage(inputStreamScanner.next()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
