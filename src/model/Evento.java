package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

    private static int contadorId = 1;

    private final int id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private int capacidadMaxima;
    private int entradasVendidas;

    public Evento(String nombre, LocalDate fecha, LocalTime hora, int capacidadMaxima) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacia.");
        }

        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede estar vacia.");
        }

        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }

        this.id = contadorId++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.capacidadMaxima = capacidadMaxima;
        this.entradasVendidas = 0;
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacia.");
        }

        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede estar vacia.");
        }

        this.hora = hora;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {

        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        if (capacidadMaxima < entradasVendidas) {
            throw new IllegalArgumentException(
                    "La capacidad no puede ser menor que las entradas ya vendidas."
            );
        }

        this.capacidadMaxima = capacidadMaxima;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public int getEntradasDisponibles() {
        return capacidadMaxima - entradasVendidas;
    }

    public boolean hayAforo(int cantidad) {
        return cantidad > 0 && cantidad <= getEntradasDisponibles();
    }

    public void registrarVenta(int cantidad) {

        if (!hayAforo(cantidad)) {
            throw new IllegalArgumentException(
                    "No hay suficiente aforo disponible."
            );
        }

        entradasVendidas += cantidad;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
