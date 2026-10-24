package model;

/**
 * Interfaz que define el contrato para generar un detalle de impresión.
 * Permite que cualquier clase que la implemente produzca un texto
 * estructurado y formateado, por ejemplo para un ticket o recibo.
 */
public interface Imprimible {

    /**
     * Genera una cadena con el detalle estructurado de la impresión.
     *
     * @return texto con el detalle formateado
     */
    String generarDetalleImpresion();
}