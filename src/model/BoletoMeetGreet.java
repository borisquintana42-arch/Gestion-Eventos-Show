package model;

/**
 * Boleto que incluye la experiencia "Meet & Greet" con los artistas del evento.
 * Su precio por defecto es de {@value #PRECIO_DEFAULT} unidades monetarias.
 */
public class BoletoMeetGreet extends Boleto {

    public static final String TIPO = "MEET & GREET";
    public static final double PRECIO_DEFAULT = 150.0;

    public BoletoMeetGreet() {
        super(TIPO, PRECIO_DEFAULT);
    }

    /**
     * Permite crear un boleto Meet & Greet con un precio personalizado,
     * por ejemplo para promociones o descuentos.
     */
    public BoletoMeetGreet(double precio) {
        super(TIPO, precio);
    }
}