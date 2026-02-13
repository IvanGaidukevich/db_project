import java.sql.*;
import java.util.Scanner;

public class InjectionExample {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:inject.db";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            Scanner scanner = new Scanner(System.in)) {

            String table = """
                    CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT,
                    password TEXT
                    );
                    """;
            stmt.execute(table);

            String user = """
                    INSERT INTO users (username, password)
                    SELECT 'admin', '1234'
                    WHERE NOT EXISTS (
                    SELECT 1 FROM users WHERE username = 'admin');
                    """;

            stmt.execute(user);

            System.out.println("username: ");
            String username = scanner.nextLine();
            System.out.println("password: ");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM users WHERE username = '"
                    + username + "' AND password = '"
                    +password + "'";

            System.out.println("Executing: " + sql);

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Login successful");
            }
            else{
                System.out.println("Login denied!");
            }
        }

    }
}
