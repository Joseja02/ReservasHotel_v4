package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private List<Huesped> coleccionHuespedes;

    public Huespedes() {
        coleccionHuespedes = new ArrayList<>();
    }

    public List<Huesped> get() {
        return copiaProfundaHuespedes();
    }

    private List<Huesped> copiaProfundaHuespedes() {

        List<Huesped> copiaHuespedes = new ArrayList<>();

        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()) {
            Huesped huesped = iterador.next();
            copiaHuespedes.add(new Huesped(huesped));
        }
        return copiaHuespedes;
    }

    public int getTamano() {
        int counter = 0;
        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()){
            iterador.next();
            counter++;
        }
        return counter;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }

        if (coleccionHuespedes.contains(huesped)) {
            int i = coleccionHuespedes.indexOf(huesped);
            huesped = coleccionHuespedes.get(i);
            return new Huesped(huesped);
        } else {
            return null;
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        if (!coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        coleccionHuespedes.remove(huesped);
    }
}
