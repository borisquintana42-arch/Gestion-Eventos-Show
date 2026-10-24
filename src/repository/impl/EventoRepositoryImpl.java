package repository.impl;

import model.Evento;
import repository.EventoRepository;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC de EventoRepository.
 * Todas las consultas con datos variables usan PreparedStatement (evita inyección SQL).
 */
public class EventoRepositoryImpl implements EventoRepository {

    private static final String SQL_INSERT =
            "INSERT INTO eventos (nombre, fecha, hora, capacidad_maxima) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL =
            "SELECT id, nombre, fecha, hora, capacidad_maxima FROM eventos ORDER BY id";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id, nombre, fecha, hora, capacidad_maxima FROM eventos WHERE id = ?";
    private static final String SQL_UPDATE =
            "UPDATE eventos SET nombre=?, fecha=?, hora=?, capacidad_maxima=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM eventos WHERE id=?";
    private static final String SQL_CONTAR_ENTRADAS =
            "SELECT COALESCE(SUM(cantidad), 0) FROM ventas WHERE evento_id=?";

    @Override
    public void crear(Evento evento) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, evento.getNombre());
            ps.setDate(2, Date.valueOf(evento.getFecha()));
            ps.setTime(3, Time.valueOf(evento.getHora()));
            ps.setInt(4, evento.getCapacidadMaxima());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se insertó el evento, ninguna fila afectada.");
            }

            // Asignar el ID autogenerado por MySQL al objeto Evento
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    evento.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Evento> obtenerTodos() throws Exception {
        List<Evento> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearEvento(rs));
            }
        }
        return lista;
    }

    @Override
    public Optional<Evento> buscarPorId(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearEvento(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean actualizar(Evento evento) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, evento.getNombre());
            ps.setDate(2, Date.valueOf(evento.getFecha()));
            ps.setTime(3, Time.valueOf(evento.getHora()));
            ps.setInt(4, evento.getCapacidadMaxima());
            ps.setInt(5, evento.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int contarEntradasVendidas(int eventoId) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR_ENTRADAS)) {

            ps.setInt(1, eventoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Evento mapearEvento(ResultSet rs) throws SQLException {
        return new Evento(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDate("fecha").toLocalDate(),
                rs.getTime("hora").toLocalTime(),
                rs.getInt("capacidad_maxima")
        );
    }
}
