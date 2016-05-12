package server;

import util.Config;
import util.FileMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author mreilaender
 * @version 11.05.2016
 */
public class FileManager {
    private static String dir = Config.serverTmpDirectory;
    private static ArrayList<String> filenames = new ArrayList<>();

    public static void saveFile(FileMessage fileMessage) throws IOException {
        filenames.add(fileMessage.getFilename());
        File file = new File(String.format(dir + "\\%s", fileMessage.getFilename()));
        if(!file.exists())
            if(!file.createNewFile())
                System.out.println("File already exists, so not created");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(fileMessage.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static FileMessage getFile(String filename) throws IOException {
        return new FileMessage(filename, Files.readAllBytes(Paths.get(String.format(dir + "\\%s", filename))));
    }

    /**
     *
     * @return String[] Filenames as array or null if there haven't been any files saved
     */
    public static String[] getFilenames() {
        if(filenames.size()==0)
            return null;
        String[] files = new String[filenames.size()];
        for (String element: filenames) {
            files[filenames.indexOf(element)] = element;
        }
        return files;
    }
}
