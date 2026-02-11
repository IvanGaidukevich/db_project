import java.sql.*;
import java.util.Scanner;

public class DatabaseUI {
    public static void main(String[] args) throws SQLException {

        Connection conn = dbInit("jdbc:sqlite:task.db");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("SQL QUERY: ");
            String query = scanner.nextLine();
            if (query.equals("0")) {
                break;
            }
            Statement stmt = conn.createStatement();
            String queryType = query.split(" ")[0];
            if (queryType.equals("SELECT")) {
                stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);

                System.out.println("------------------------------------------------------------");
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("title");
                    boolean is_completed = rs.getBoolean("is_completed");
                    String created_at = rs.getString("created_at");
                    System.out.println(id + " | " + name + " | " + is_completed + " | " + created_at);
                    System.out.println("--------------------------------------------------------");
                }
            }
            else {
                stmt.execute(query);
            }


        }

    }

    public static Connection dbInit(String url) throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        String query = "CREATE TABLE IF NOT EXISTS tasks " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "is_completed BOOLEAN NOT NULL DEFAULT 0," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP);";
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        return conn;
    }
}
