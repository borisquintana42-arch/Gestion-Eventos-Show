package repository.impl;

import model.Boleto;
import model.BoletoGeneral;
import model.BoletoMeetGreet;
import model.BoletoVIP;
import model.Evento;
import model.Venta;
import repository.VentaRepository;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de VentaRepository.
 * Todas las consultas con datos variables usan PreparedStatement.
 */
public class VentaRepositoryImpl implements VentaRepository {

    private static final String SQL_INSERT =
            "INSERT INTO ventas (evento_id, tipo_boleto, cantidad, total_pagar) VALUES (?, ?, ?, ?)";
    private static final String SQL_POR_EVENTO =
            "SELECT id, evento_id, tipo_boleto, cantidad, total_pagar, fecha_venta FROM ventas WHERE evento_id=? ORDER BY fecha_venta DESC";
    private static final String SQL_HISTORIAL =
            "SELECT id, evento_id, tipo_boleto, cantidad, total_pagar, fecha_venta FROM ventas ORDER BY fecha_venta DESC";
    private static final String SQL_CONTAR_TIPO_EVENTO =
            "SELECT COALESCE(SUM(cantidad), 0) FROM ventas WHERE evento_id=? AND tipo_boleto=?";

    @Override
    public void registrar(Venta venta) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, venta.getEvento().getId());
            ps.setString(2, venta.getTipoBoleto());
            ps.setInt(3, venta.getCantidad());
            ps.setDouble(4, venta.getTotalPagar());

            ps.executeUpdate();
        }
    }

    @Override
    public List<Venta> obtenerPorEvento(int eventoId) throws Exception {
        List<Venta> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_POR_EVENTO)) {

            ps.setInt(1, eventoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Venta> obtenerHistorial() throws Exception {
        List<Venta> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_HISTORIAL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearVenta(rs));
            }
        }
        return lista;
    }

    @Override
    public int contarPorTipoYEvento(int eventoId, String tipoBoleto) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR_TIPO_EVENTO)) {

            ps.setInt(1, eventoId);
            ps.setString(2, tipoBoleto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        // Evento ligero con los datos esenciales; se usa el ID real de MySQL (evento_id)
        // para que la FK sea consistente al reconstruir ventas desde la base de datos.
        // La capacidad máxima se pone en 1 (valor válido) ya que este objeto solo se usa
        // como contenedor ligero para ventas persistidas; no afecta lógica de negocio.
        Evento evento = new Evento(
                rs.getInt("evento_id"),
                "Evento #" + rs.getInt("evento_id"),
                LocalDate.now(),
                LocalTime.now(),
                1
        );

        double precioUnitario = rs.getDouble("total_pagar") / rs.getInt("cantidad");
        Boleto boleto = switch (rs.getString("tipo_boleto")) {
            case "GENERAL" -> new BoletoGeneral(precioUnitario);
            case "VIP" -> new BoletoVIP(precioUnitario);
            case "MEET & GREET" -> new BoletoMeetGreet(precioUnitario);
            default -> new BoletoGeneral(precioUnitario);
        };

        return new Venta(evento, boleto, rs.getInt("cantidad"));
    }
}
