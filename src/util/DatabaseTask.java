package util;

import javafx.concurrent.Task;

/**
 * Tarea de fondo para operaciones de base de datos que puedan ser lentas.
 * Evita que el hilo de JavaFX se congele mientras se consulta MySQL.
 * Uso: envolver la operación en una lambda y lanzar en un hilo aparte.
 */
public class DatabaseTask<T> extends Task<T> {

    private final DatabaseOperation<T> operation;

    public DatabaseTask(DatabaseOperation<T> operation) {
        this.operation = operation;
    }

    @Override
    protected T call() throws Exception {
        return operation.execute();
    }

    @FunctionalInterface
    public interface DatabaseOperation<T> {
        T execute() throws Exception;
    }
}
