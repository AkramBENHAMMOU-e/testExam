package ma.proj.testexam.connectionDB;

import java.sql.*;

public class SingletonConnexionDB {
    private static Connection connection;

    private SingletonConnexionDB() {}

    public static Connection getConnexion() {
        if (connection == null) {
            try {
                // Remplacez par vos paramètres de connexion MySQL
                String url = "jdbc:mysql://localhost:3307/cabinet";
                String user = "root";
                String password = "";
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion réussie à la base de données.");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur de connexion à la base de données.");
            }
        }
        return connection;
    }
}
