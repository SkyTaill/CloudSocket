package Server;

import java.io.File;

public class ComandController {
    String serverDir = "C:\\Users\\Максим\\Desktop\\NettyCloudV0.1.1\\UsersCloud\\";
         // private String serverDir = "./";
    private String userName = "user";
    private static final int BUFFER_SIZE = 1024;
    public void  printDirComand(CorMessage message, String userDir) {
        File dir = new File(serverDir+"/"+userDir);
        StringBuilder sb = new StringBuilder(userName).append(" files -> \n");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file == null) {
                    continue;
                }
                sb.append(file.getName()).append(" | ");
                if (file.isFile()) {
                    sb.append("[FILE] | ").append(file.length()).append(" bytes.\n");
                } else {
                    sb.append("[DIR]\n");
                }
            }
        }
       // return sb.toString();
        System.out.println(sb.toString());
        message.setText(sb.toString());
    }
    public Boolean cdDirComand(String inputDir,String userDir){
        String path = inputDir.split(" ", 2)[1];
        File dir = new File(path);
        File dirAcc = new File(serverDir+"/"+userDir + "/" + path);
        if (dir.exists()) {
            serverDir = path;
        } else if (dirAcc.exists()) {
            serverDir = serverDir + "/" + path;
        } else if (path.equals("..")) {
            serverDir = new File(serverDir).getParent();
        } else {
            return false;

        }
        return true;
    }
    public void getFileCommand(String comand, CorMessage message){
        String[] data = comand.split(" +");
        String fileName = data[1];
        File file = new File(serverDir + fileName);
        if (!file.exists()) {
            message.setText("user: File not exists\n");
        } else {
         //передача файла еще не доделал
        }

    }

}
