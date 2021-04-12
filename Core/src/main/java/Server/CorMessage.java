package Server;

import java.io.Serializable;

public class CorMessage implements Serializable {

    private String userName;
    private String text;
    private Byte file;


    public CorMessage(String userName, String text,Byte file) {
        this.userName = userName;
        this.text = text;
        this.file=file;

    }

    public void setFile(byte file) {
        this.file = file;
    }

    public Byte getFile() {
        return file;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
