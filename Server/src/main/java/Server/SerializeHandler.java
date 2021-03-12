package Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SerializeHandler extends SimpleChannelInboundHandler<CorMessage> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {



    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CorMessage message) throws Exception {
        ComandController comandController=new ComandController();

        String comand=message.getText();

        if (comand.equals("ls")) {
            comandController.printDirComand(message,message.getUserName()); //message.getUserName() временная мера ,еще не придумал как сделать чтоб каждый пользователь мог зайти
            //в свою директорию , так что второй параметр у метода это папка где лежат файлы пользователя

        }else if (comand.startsWith("cd ")) {
            if(comandController.cdDirComand(comand,message.getUserName())){
                comandController.printDirComand(message,message.getUserName());
            }else{
                message.setText("err dir");
            }}else if (comand.startsWith("getFile")) {

        }
        else {
            message.setText("error comand");
        }




        // message.setText("dd");

        ctx.writeAndFlush(message); //то что отдал обратно
    }


}
