package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private MongoCollection<Document> coleccionHabitaciones;
    private final String COLECCION = "habitaciones";

    public Habitaciones() {
    }

    public List<Habitacion> get() {
        List<Habitacion> listaHabitaciones = new ArrayList<>();
        coleccionHabitaciones.find().forEach((docHabitacion) -> {
            Habitacion habitacion = MongoDB.getHabitacion(docHabitacion);
            listaHabitaciones.add(habitacion);
        });
        listaHabitaciones.sort(Comparator.comparing(Habitacion :: getIdentificador));

        return listaHabitaciones;
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> listaHabitaciones = new ArrayList<>();
        coleccionHabitaciones.find(Filters.eq(tipoHabitacion)).forEach((docHabitacion) -> {
            Habitacion habitacion = MongoDB.getHabitacion(docHabitacion);
            listaHabitaciones.add(habitacion);
        });
        listaHabitaciones.sort(Comparator.comparing(Habitacion :: getIdentificador));

        return listaHabitaciones;
    }

    public int getTamano() { return (int) coleccionHabitaciones.countDocuments(); }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }

        if (coleccionHabitaciones.find(Filters.eq(habitacion.getIdentificador())).first().isEmpty()) {
            coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
        } else {
            throw new NullPointerException("ERROR: Ya existe una habitación con ese identificador.");
        }
    }

    public Habitacion buscar(Habitacion habitacion) {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }

        Document documentoHabitacion = coleccionHabitaciones.find(MongoDB.getDocumento(habitacion)).first();

        if (!documentoHabitacion.isEmpty()) {
            return MongoDB.getHabitacion(documentoHabitacion);
        } else {
            System.out.println("Habitación no encontrada");
            return null;
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        Document documentoHabitacion = coleccionHabitaciones.find(Filters.eq(habitacion.getIdentificador())).first();

        if (documentoHabitacion.isEmpty()) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
        coleccionHabitaciones.deleteOne(documentoHabitacion);
    }
    public void comenzar(){
        MongoDatabase database = MongoDB.getBD();
        coleccionHabitaciones = database.getCollection(COLECCION);
        System.out.println("Colección habitaciones obtenida");
    }
    public void terminar(){
        MongoDB.cerrarConexion();
        System.out.println("Conexión con MongoDB cerrada con éxito.");
    }
}
