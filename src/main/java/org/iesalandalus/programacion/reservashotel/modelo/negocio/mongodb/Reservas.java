package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {
    private MongoCollection<Document> coleccionReservas;
    private final String COLECCION = "reservas";

    public Reservas() {
        comenzar();
    }

    public List<Reserva> get() {
        List<Reserva> listaReservas = new ArrayList<>();
        coleccionReservas.find().forEach((docReservas) -> {
            Reserva reserva = MongoDB.getReserva(docReservas);
            listaReservas.add(reserva);
        });
        listaReservas.sort(Comparator.comparing(Reserva::getFechaInicioReserva));

        return listaReservas;
    }
    public int getTamano() { return (int) coleccionReservas.countDocuments(); }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        Bson filtro = Filters.and(
                Filters.eq("habitacion.identificador", reserva.getHabitacion().getIdentificador()),
                Filters.eq("fecha_inicio_reserva", reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        );

        Document documentoReservaColeccion = coleccionReservas.find(filtro).first();
        Document documentoReservaParametro = MongoDB.getDocumento(reserva);

        if (documentoReservaColeccion == null){
            coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
        } else {
            if (documentoReservaColeccion.get("habitacion").equals(documentoReservaParametro.get("habitacion")) && documentoReservaColeccion.get("fecha_inicio_reserva").equals(documentoReservaParametro.get("fecha_inicio_reserva"))) {
                throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
            } else {
                coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
            }
        }
    }

    public Reserva buscar(Reserva reserva) {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }

        Bson filtro = Filters.and(
                Filters.eq("habitacion.identificador", reserva.getHabitacion().getIdentificador()),
                Filters.eq("fecha_inicio_reserva", reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        );

        Document documentoReservaColeccion = coleccionReservas.find(filtro).first();
        Document documentoReservaParametro = MongoDB.getDocumento(reserva);

        if (documentoReservaColeccion == null){
            return null;
        }

        if (documentoReservaColeccion.get("habitacion").equals(documentoReservaParametro.get("habitacion")) && documentoReservaColeccion.get("fecha_inicio_reserva").equals(documentoReservaParametro.get("fecha_inicio_reserva"))) {
            return MongoDB.getReserva(documentoReservaColeccion);
        } else {
            return null;
        }
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        Bson filtro = Filters.and(
                Filters.eq("habitacion.identificador", reserva.getHabitacion().getIdentificador()),
                Filters.eq("fecha_inicio_reserva", reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        );

        Document documentoReservaColeccion = coleccionReservas.find(filtro).first();
        Document documentoReservaParametro = MongoDB.getDocumento(reserva);

        if (documentoReservaColeccion == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (documentoReservaColeccion.get("habitacion").equals(documentoReservaParametro.get("habitacion")) && documentoReservaColeccion.get("fecha_inicio_reserva").equals(documentoReservaParametro.get("fecha_inicio_reserva"))) {
            coleccionReservas.deleteOne(documentoReservaColeccion);
        }
    }

    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHuesped().getDni().equals(huesped.getDni())) {
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion() instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE){
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }

    public List<Reserva> getReservas(Habitacion habitacion){

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        List<Reserva> reservasHabitacion = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;

        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion().getIdentificador().equals(habitacion.getIdentificador())) {
                reservasHabitacion.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHabitacion;
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");

        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()){
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion().getIdentificador().equals(habitacion.getIdentificador()) &&
                    reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {

        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva de un Checkin no puede ser nula.");
        }
        if (fecha == null){
            throw new NullPointerException("ERROR: La fecha de un Checkin no puede ser nula.");
        }
        reserva.setCheckIn(fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva de un Checkout no puede ser nula.");
        }
        if (fecha == null){
            throw new NullPointerException("ERROR: La fecha de un Checkout no puede ser nula.");
        }
        if (reserva.getCheckIn() == null){
            throw new IllegalArgumentException("ERROR: No se puede realizar el Checkout sin haber hecho antes el Checkin.");
        }
        reserva.setCheckOut(fecha);
    }
    public void comenzar(){
        MongoDatabase database = MongoDB.getBD();
        coleccionReservas = database.getCollection(COLECCION);
        System.out.println("Colección reservas obtenida");
    }
    public void terminar(){
        MongoDB.cerrarConexion();
        System.out.println("Conexión con MongoDB cerrada con éxito.");
    }
}
