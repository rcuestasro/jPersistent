import java.sql.*;
import java.util.Scanner;

public class Main {
    static String url     = "jdbc:mysql://localhost:3306/la_meva_bd?useSSL=false&serverTimezone=UTC";
    static String usuari  = "rafa";
    static String password = "rafa";


    public static void mainHolaMundo(String[] args) {


        try {
            // Connectar
            Connection con = DriverManager.getConnection(url, usuari, password);
            System.out.println("Connexió exitosa!");

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
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
    * CREATE TABLE alumnes (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    nom   VARCHAR(100),
    cicle VARCHAR(100)
    );*/
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcio;

        do {
            System.out.println("\n=== MENÚ ALUMNES ===");
            System.out.println("1. Llistar tots els alumnes");
            System.out.println("2. Afegir alumne");
            System.out.println("3. Actualitzar alumne");
            System.out.println("4. Eliminar alumne");
            System.out.println("0. Sortir");
            System.out.print("Tria una opció: ");
            opcio = sc.nextInt();
            sc.nextLine(); // netejar buffer

            switch (opcio) {
                case 1 -> llistarAlumnes();
                case 2 -> {
                    System.out.print("Nom: ");
                    String nom = sc.nextLine();
                    System.out.print("Cicle: ");
                    String cicle = sc.nextLine();
                    afegirAlumne(nom, cicle);
                }
                case 3 -> {
                    System.out.print("ID de l'alumne a actualitzar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nou nom: ");
                    String nom = sc.nextLine();
                    System.out.print("Nou cicle: ");
                    String cicle = sc.nextLine();
                    actualitzarAlumne(id, nom, cicle);
                }
                case 4 -> {
                    System.out.print("ID de l'alumne a eliminar: ");
                    int id = sc.nextInt();
                    eliminarAlumne(id);
                }
                case 0 -> System.out.println("Fins aviat!");
                default -> System.out.println("Opció no vàlida.");
            }
        } while (opcio != 0);

        sc.close();
    }

    // ── READ ────────────────────────────────────────────────
    static void llistarAlumnes() {
        String sql = "SELECT * FROM alumnes";

        try (Connection con = DriverManager.getConnection(url, usuari, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID  | NOM              | CICLE");
            System.out.println("----+------------------+------------------");
            while (rs.next()) {
                System.out.printf("%-4d| %-17s| %s%n",
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("cicle"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── CREATE ──────────────────────────────────────────────
    static void afegirAlumne(String nom, String cicle) {
        String sql = "INSERT INTO alumnes (nom, cicle) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, usuari, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, cicle);
            ps.executeUpdate();
            System.out.println("Alumne afegit correctament!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── UPDATE ───────────────────────────────────────────────
    static void actualitzarAlumne(int id, String nom, String cicle) {
        String sql = "UPDATE alumnes SET nom = ?, cicle = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, usuari, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, cicle);
            ps.setInt(3, id);
            int files = ps.executeUpdate();

            if (files > 0) System.out.println("Alumne actualitzat correctament!");
            else           System.out.println("No s'ha trobat cap alumne amb id " + id);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── DELETE ───────────────────────────────────────────────
    static void eliminarAlumne(int id) {
        String sql = "DELETE FROM alumnes WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, usuari, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int files = ps.executeUpdate();

            if (files > 0) System.out.println("Alumne eliminat correctament!");
            else           System.out.println("No s'ha trobat cap alumne amb id " + id);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
