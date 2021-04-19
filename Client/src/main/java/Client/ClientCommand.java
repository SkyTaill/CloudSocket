package Client;

import Server.CorMessage;

import java.io.File;

public class ClientCommand {
    public static String serverDir = "C:\\Users\\Максим\\Desktop\\";
    // private String serverDir = "./";
    private static String userName = "user";
    private static final int BUFFER_SIZE = 1024;
    public static String  printDirComand() {
        File dir = new File(serverDir);
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
        return sb.toString();
    }
}
