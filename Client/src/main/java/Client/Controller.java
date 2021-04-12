package Client;

import Server.CorMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField in;
    public TextArea out;
    private ObjectEncoderOutputStream os;
    private ObjectDecoderInputStream is;

    public void send(ActionEvent actionEvent) throws IOException {
        os.writeObject(new CorMessage("user", in.getText(),null));
        os.flush();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            os = new ObjectEncoderOutputStream(socket.getOutputStream());
            is = new ObjectDecoderInputStream(socket.getInputStream());

            new Thread(() -> {
                while (true) {
                    try {
                        CorMessage message = (CorMessage) is.readObject();
                        Platform.runLater(() -> out.appendText(message.getText() + "\n"));
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    //вывод файлов пользователя 
                }
            }).start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
