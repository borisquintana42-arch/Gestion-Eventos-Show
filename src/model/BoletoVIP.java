package model;

import java.util.List;

/**
 * Boleto con acceso VIP al evento.
 * Su precio por defecto es de {@value #PRECIO_DEFAULT} unidades monetarias.
 */
public class BoletoVIP extends Boleto {

    public static final String TIPO = "VIP";
    public static final double PRECIO_DEFAULT = 100.0;

    public BoletoVIP() {
        super(TIPO, PRECIO_DEFAULT);
    }

    /**
     * Permite crear un boleto VIP con un precio personalizado,
     * por ejemplo para promociones o descuentos.
     */
    public BoletoVIP(double precio) {
        super(TIPO, precio);
    }

    @Override
    public List<String> getBeneficios() {
        return List.of("Asiento reservado en zona exclusiva",
                "Fila rápida de acceso",
                "Bebida de cortesía");
    }
}