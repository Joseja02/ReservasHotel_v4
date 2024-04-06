package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IHabitaciones {

    List<Habitacion> get();
    List<Habitacion> get(TipoHabitacion tipoHabitacion);
    int getTamano();
    void insertar(Habitacion habitacion) throws OperationNotSupportedException;
    Habitacion buscar(Habitacion habitacion);
    void borrar(Habitacion habitacion) throws OperationNotSupportedException;
}
