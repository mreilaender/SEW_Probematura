package util;

/**
 * @author mreilaender
 * @version 11.05.2016
 */
public class FileMessage implements Message {
    private byte[] bytes;
    private String filename;

    public FileMessage(String filename, byte[] bytes) {
        this.bytes = bytes;
        this.filename = filename;
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFilename() {
        return filename;
    }
}
