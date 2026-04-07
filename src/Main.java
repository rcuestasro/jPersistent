import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url      = "jdbc:mysql://localhost:3306/la_meva_bd?useSSL=false&serverTimezone=UTC";
        String usuari   = "rafa";
        String password = "rafa";

        try {
            // Connectar
            Connection con = DriverManager.getConnection(url, usuari, password);
            System.out.println("✅ Connexió exitosa!");

            // Consultar
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alumnes");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("nom") + " | " +
                        rs.getString("cicle"));
            }

            // Tancar
            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
