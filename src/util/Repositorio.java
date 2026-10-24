package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio genérico para almacenar y administrar elementos de un tipo T.
 * Encapsula una lista interna y expone operaciones básicas de un repositorio:
 * agregar, consultar todos, eliminar y contar.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Repositorio<T> {

    private final List<T> elementos = new ArrayList<>();

    /**
     * Agrega un elemento al repositorio.
     *
     * @param elemento elemento a agregar
     * @throws IllegalArgumentException si el elemento es nulo
     */
    public void agregar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }
        elementos.add(elemento);
    }

    /**
     * Retorna una copia de la lista de elementos almacenados,
     * de modo que la lista interna no pueda ser modificada desde afuera.
     *
     * @return copia de la lista de elementos
     */
    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    /**
     * Elimina la primera ocurrencia del elemento indicado.
     *
     * @param elemento elemento a eliminar
     * @return {@code true} si el elemento existía en el repositorio
     */
    public boolean eliminar(T elemento) {
        return elementos.remove(elemento);
    }

    /**
     * Retorna la cantidad de elementos almacenados en el repositorio.
     *
     * @return cantidad de elementos
     */
    public int cantidad() {
        return elementos.size();
    }
}