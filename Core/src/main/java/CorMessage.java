import java.io.Serializable;

public class CorMessage implements Serializable {

    private String userName;
    private String text;
    private Byte file;

    public void setFile(Byte file) {
        this.file = file;
    }

    public Byte getFile() {
        return file;
    }

    public CorMessage(String userName, String text) {
        this.userName = userName;
        this.text = text;

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
