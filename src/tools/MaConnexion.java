package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    final String url = "jdbc:mysql://localhost:3306/ranim";
    final String username = "root";
    final String pwd = "";
    private Connection conx;

    public static MaConnexion instance;

    public static MaConnexion getInstance() {
        if (instance == null)
            instance = new MaConnexion();
        return instance;

    }

    private MaConnexion() {

        try {
            conx = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Connection getCnx() {
        return conx;
    }

}
