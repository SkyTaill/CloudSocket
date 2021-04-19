package Server;


import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;



public class SerializeHandler extends SimpleChannelInboundHandler<CorMessage> {

    private static final int SIZE_BUFFER = 1024;


    //private static HashMap<ChannelId, String> users = new HashMap<io.netty.channel.ChannelId, String>();// думал по id канала
                                                                    // раздичать пользователей но не работало




    @Override
    public void channelRegistered(ChannelHandlerContext ctx)  {

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CorMessage message) throws Exception {
        byte[] buffer;
        ComandController comandController=new ComandController();
        String serverDir = "C:\\Users\\Максим\\Desktop\\NettyCloudV0.1.1\\UsersCloud\\";
       // boolean userIsInichialaiz=false;
        String comand=message.getText();



        if(comand.startsWith("Login")){

            String[] data = comand.split(" +");



            //проверяем в бд лог и пароль
            System.out.println(data[1]);
            System.out.println(data[2]);

            if(DataBase.checkPass(data[1],data[2])) {

                message.setUserName(data[1]);
          //      users.put(ctx.channel().id(),data[1]);

                message.setAuthentication(true);
        }}else {
            //if (userIsInichialaiz) { //не вышло реализовать флаг через который определялся бы человек прошел регистрацию
                //                                                  или нет (что-бы ограничить доступ к след ниже командам)
                // message.setUserName(users.get(ctx.channel().id()));
                // думал по id канала
                // раздичать пользователей но не работало


                if (comand.equals("ls")) {
                    comandController.printDirComand(message, message.getUserName()); //message.getUserName() временная мера ,еще не придумал как сделать чтоб каждый пользователь мог зайти
                    //в свою директорию , так что второй параметр у метода это папка где лежат файлы пользователя

                } else if (comand.startsWith("cd ")) {
                    if (comandController.cdDirComand(comand, message.getUserName())) {
                        comandController.printDirComand(message, message.getUserName());
                    } else {
                        message.setText("err dir");
                    }
                } else if (comand.startsWith("getFile")) {

/////////////////////////////////////////////
                    buffer = new byte[SIZE_BUFFER];

                    String[] data = comand.split(" +");
                    String fileName = data[1];
                    File file = new File(serverDir + "\\" + fileName);
                    if (!file.exists()) {
                        System.out.println("user: File not exists\n");
                        System.out.println(file);
                    } else {
                        System.out.println("file");
                        System.out.println(fileName);
                        long length = file.length();
                        System.out.println(length);
                        try (InputStream inputStream = new FileInputStream(file)) {
                            int count = (int) (file.length() - 1) / SIZE_BUFFER + 1;
                            for (int i = 0; i < count; i++) {
                                int read = inputStream.read(buffer);
                                byte[] tmp = new byte[read];

                                //  message.setFile(     );

                            }

                        }
                    }


                    /////////////////////     ////////////
                } else if (comand.startsWith("delete")) {
                    String[] data = comand.split(" +");
                    String fileName = data[1];
                    File file = new File(serverDir + "\\" + fileName);

                    if (file.isFile()) {
                        file.delete();
                        System.out.println("Удаление");
                    }
                } else {
                    message.setText("error comand");
                }
          //  }
        }




        // message.setText("dd");

        ctx.writeAndFlush(message); //то что отдал обратно
    }


}
