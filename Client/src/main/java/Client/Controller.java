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
    private boolean crutch=false;    //костыль
    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Button LogButton;

    @FXML
    private Button RegistrButton;

    public static String crutchLog="users";  //костыль

    public void send(ActionEvent actionEvent) throws IOException {
        os.writeObject(new CorMessage(crutchLog, in.getText(),null,false));
        os.flush();
    }
    public void passCheck(ActionEvent actionEvent) throws IOException, InterruptedException {
            System.out.println("ddd");
        String logAndPass;
        logAndPass="Login"+" "+LoginField.getText()+" "+PassField.getText();


     //******************************

        crutchLog=LoginField.getText();     //ОООООч неправильно работает, главная проблема что не безопасно для других пользователей
                                            //не понял как в netty работать с разными клиентами и реализовал такой костыль



    //***************************


        os.writeObject(new CorMessage(crutchLog, logAndPass,null,false)); // отправляем пароль и логин
        Thread.sleep(400);           //чере 400 милисекунд проверяем ответ- прав пароль или нет (true ore false)
                    if(crutch==true) {
                        startNewWindow();   //ес правильно открываем новое окно
                    }
        os.flush();
    }


    public void startNewWindow() throws IOException {
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("cloud.fxml"));
        loader.load();
        Parent root =loader.getRoot();
        Stage stage =new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();            //из проблем немного не понял как убрать старое окно , а то 2 окна висят
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
                            if (message.getAuthentication()==false){            //тоже не до конца понял как сделать что-бы этот класс запускался
                                                                    //при открытие нужноо окна, метод начинает работать сразу как я его запустил и
                                // пришлось делать проверку, мол пользователь прошел инициализацию или нет



                                String serverMessage=message.getText();



                                System.out.println(serverMessage);
                            }
                        if (message.getAuthentication()==true) {
                                crutch=message.getAuthentication();
                        }
                            Platform.runLater(() -> out.appendText(message.getText() + "\n"));
                        System.out.println(message.getUserName());
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
