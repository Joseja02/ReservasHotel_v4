package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> copia = copiaProfundaHabitaciones();
        List<Habitacion> habitacionesTipo = new ArrayList<>();

        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Habitacion habitacion = copia.get(i);
            if (habitacion instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE) {
                habitacionesTipo.set(i, habitacion);
            } else if (habitacion instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE) {
                habitacionesTipo.set(i, habitacion);
            }else if (habitacion instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE) {
                habitacionesTipo.set(i, habitacion);
            }else if (habitacion instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE) {
                habitacionesTipo.set(i, habitacion);
            }
            i++;
        }
        return habitacionesTipo;
    }

    private List<Habitacion> copiaProfundaHabitaciones() {

        List<Habitacion> copiaHabitaciones = new ArrayList<>();

        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        while (iterador.hasNext()) {
            Habitacion habitacion = iterador.next();
            if (habitacion instanceof Simple) {
                copiaHabitaciones.add(new Simple((Simple) habitacion));
            } else if (habitacion instanceof Doble) {
                copiaHabitaciones.add(new Doble((Doble) habitacion));
            } else if (habitacion instanceof Triple) {
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            } else if (habitacion instanceof Suite) {
                copiaHabitaciones.add(new Suite((Suite) habitacion));
            }
        }
        return copiaHabitaciones;
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            int i = coleccionHabitaciones.indexOf(habitacion);
            return coleccionHabitaciones.get(i);
        } else {
            return null;
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        if (!coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
        coleccionHabitaciones.remove(habitacion);
    }

    public int getTamano() {
        int counter = 0;
        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        while (iterador.hasNext()){
            iterador.next();
            counter++;
        }
        return counter;
    }
}
