package util;

import java.io.Serializable;

/**
 * @author mreilaender
 * @version 03.05.2016
 */
public interface Message extends Serializable {
    public enum Type {
        TEXTMESSAGE,
        COMMAND,
        FILE,
        STATISTIC
    }
    public Type getType();
}
