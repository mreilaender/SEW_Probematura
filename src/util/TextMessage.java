package util;

/**
 * @author mreilaender
 * @version 03.05.2016
 */
public class TextMessage implements Message {
    private String sender;
    private String message;

    public TextMessage(String message) {
        this.message = message;
    }

    @Override
    public Type getType() {
        return Type.TEXTMESSAGE;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

