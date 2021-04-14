package Client;

import Server.CorMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField in;
    public TextArea out;
    private ObjectEncoderOutputStream os;
    private ObjectDecoderInputStream is;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Button LogButton;

    @FXML
    private Button RegistrButton;


    public void send(ActionEvent actionEvent) throws IOException {
        os.writeObject(new CorMessage("user", in.getText(),null,false));
        os.flush();
    }
    public void passCheck(ActionEvent actionEvent)throws IOException{

            System.out.println("ddd");
        String logAndPass;
        logAndPass="Login"+" "+LoginField.getText()+" "+PassField.getText();
        os.writeObject(new CorMessage("user", logAndPass,null,false));


                     startNewWindow();   //пофиксить что б нормс работало


        os.flush();

    }
    public void startNewWindow() throws IOException {
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("cloud.fxml"));
        loader.load();
        Parent root =loader.getRoot();
        Stage stage =new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
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
                            if (message.getAuthentication()==false){



                                String serverMessage=message.getText();



                                System.out.println(serverMessage);
                            }
                     //   if (message.getAuthentication()==true){ // если оставить условие то текс не пишется в area
                            Platform.runLater(() -> out.appendText(message.getText() + "\n"));
                        //}






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
