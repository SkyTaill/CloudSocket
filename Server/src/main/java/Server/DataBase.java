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
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");
    }

    public static void createDB() throws SQLException {
        statement = conn.createStatement();
        statement.execute(
                "CREATE TABLE if not exists 'users'" +
                        "('id' INTEGER PRIMARY KEy AUTOINCREMENT, 'name' text, 'password' INT);");
    }

    public static void writeBD() throws SQLException {
        statement.execute("INSERT INTO 'users' ('name', 'password') VALUES ('Petya', 12345)");

    }

    public static void readBD() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int phone = resultSet.getInt("password");
            System.out.println(id + " " + name + " " + phone);
        }
    }

    public static void closeDB() throws SQLException {
        resultSet.close();
        statement.close();
        conn.close();
    }
}
