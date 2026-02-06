import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        // подключение к базе
        String url = "jdbc:sqlite:test.db";
        Connection conn = DriverManager.getConnection(url);

        String query = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)";
        Statement stmt = conn.createStatement();
        stmt.execute(query);

        String insert = "INSERT INTO users(name, email) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement((insert))) {
            ps.setString(1, "Peter");
            ps.setString(2, "peter@kremlin.com");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе");

        }

        String select = "SELECT * FROM users";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(select);

        System.out.println("------------------------");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            System.out.println(id + " | " + name + " | " + email);
            System.out.println("------------------------");
        }


    }
}
