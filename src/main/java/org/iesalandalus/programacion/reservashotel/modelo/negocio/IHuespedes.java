package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IHuespedes {
    List<Huesped> get();
    int getTamano();
    void insertar(Huesped huesped) throws OperationNotSupportedException;
    Huesped buscar(Huesped huesped);
    void borrar(Huesped huesped) throws OperationNotSupportedException;
}
