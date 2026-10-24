package model;

import java.util.List;

/**
 * Boleto de entrada general.
 * Su precio por defecto es de {@value #PRECIO_DEFAULT} unidades monetarias.
 */
public class BoletoGeneral extends Boleto {

    public static final String TIPO = "GENERAL";
    public static final double PRECIO_DEFAULT = 50.0;

    public BoletoGeneral() {
        super(TIPO, PRECIO_DEFAULT);
    }

    /**
     * Permite crear un boleto general con un precio personalizado,
     * por ejemplo para promociones o descuentos.
     */
    public BoletoGeneral(double precio) {
        super(TIPO, precio);
    }

    @Override
    public List<String> getBeneficios() {
        return List.of("Acceso al área general del evento");
    }
}