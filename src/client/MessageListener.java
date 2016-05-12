package client;

import util.FileMessage;
import util.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                    String line = inputStreamScanner.next();
                    if(line.charAt(0)=='/') {
                        switch(line.substring(1)) {
                            case "sendFile":
                                String filename = inputStreamScanner.next();
                                if(filename == null) {
                                    System.out.println("Please specify a file with the second parameter");
                                    break;
                                }
                                if(filename.contains("\\")) {
                                    System.out.println(filename);
                                } else {
                                    String fullPath = String.format("%s\\%s", Paths.get("").toAbsolutePath().toString(), filename);
                                    this.outputStream.writeObject(new FileMessage(filename, Files.readAllBytes(Paths.get(fullPath))));
                                }
                        }
                    } else {
                        this.outputStream.writeObject(new TextMessage(line));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
