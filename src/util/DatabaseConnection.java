package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestor de conexión a MySQL.
 * Encapsula la obtención de la conexión JDBC para que el resto
 * de la aplicación no dependa de la URL ni de las credenciales directamente.
 */
public final class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_eventos_show?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";        // Cambiar según su entorno
    private static final String PASSWORD = "root";        // Cambiar según su entorno

    private DatabaseConnection() {
        // Utilidad: no se instancia
    }

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return objeto Connection abierto
     * @throws SQLException si no se puede conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
