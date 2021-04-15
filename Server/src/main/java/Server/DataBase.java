package Server;

import java.sql.*;

public class DataBase {

    public static Connection conn;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        setConn();
       createDB();
        writeBD();
        readBD();
        closeDB();
    }

    public static void setConn() throws ClassNotFoundException, SQLException {
        conn = null;
        // загрузить драйвер в память виртуальной машины;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s4db");
    }

    public static void createDB() throws SQLException {
        statement = conn.createStatement();
        statement.execute(
                "CREATE TABLE if not exists 'users'" +
                        "('id' INTEGER PRIMARY KEy AUTOINCREMENT, 'name' text, 'password' text);");
    }

    public static void writeBD() throws SQLException {
        statement.execute("INSERT INTO 'users' ('name', 'password') VALUES ('Max', 123)");
        statement.execute("INSERT INTO 'users' ('name', 'password') VALUES ('Egor', 123)");

    }

    public static void readBD() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("password");
            System.out.println(id + " " + name + " " + phone);
        }
    }
    public static boolean checkPass(String login,String password) throws SQLException, ClassNotFoundException {
        setConn();
        createDB();
        resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {

            String loginFromDB = resultSet.getString("name");
            String passwordFromDB = resultSet.getString("password");

            if((passwordFromDB.equals(password)) && (loginFromDB.equals(login))){
                closeDB();
            return true;
        }
    }
        closeDB();
        return false;

    }

    public static void closeDB() throws SQLException {
        resultSet.close();
        statement.close();
        conn.close();
    }
}
