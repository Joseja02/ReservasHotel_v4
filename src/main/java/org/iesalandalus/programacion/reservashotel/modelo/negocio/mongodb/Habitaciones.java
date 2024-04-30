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
        comenzar();
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

        if (tipoHabitacion == null){
            throw new NullPointerException("ERROR: El tipo de habitación no puede ser nulo");
        }

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

        Document documentoHabitacionColeccion = coleccionHabitaciones.find(Filters.eq("identificador", habitacion.getIdentificador())).first();
        Document documentoHabitacionParametro = MongoDB.getDocumento(habitacion);

        if (documentoHabitacionColeccion == null){
            coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
        } else {
            if (documentoHabitacionColeccion.get("identificador").equals(documentoHabitacionParametro.get("identificador"))) {
                throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
            } else {
                coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
            }
        }
    }

    public Habitacion buscar(Habitacion habitacion) {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }

        Document documentoHabitacionColeccion = coleccionHabitaciones.find(Filters.eq("identificador", habitacion.getIdentificador())).first();
        Document documentoHabitacionParametro = MongoDB.getDocumento(habitacion);

        if (documentoHabitacionColeccion == null){
            return null;
        }

        if (documentoHabitacionColeccion.get("identificador").equals(documentoHabitacionParametro.get("identificador"))) {
            return MongoDB.getHabitacion(documentoHabitacionColeccion);
        } else {
            return null;
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }
        Document documentoHabitacionColeccion = coleccionHabitaciones.find(Filters.eq("identificador", habitacion.getIdentificador())).first();
        Document documentoHabitacionParametro = MongoDB.getDocumento(habitacion);

        if(documentoHabitacionColeccion == null){
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
        if (documentoHabitacionColeccion.get("identificador").equals(documentoHabitacionParametro.get("identificador"))) {
            coleccionHabitaciones.deleteOne(documentoHabitacionColeccion);
        }
    }
    public void comenzar(){
        MongoDatabase database = MongoDB.getBD();
        coleccionHabitaciones = database.getCollection(COLECCION);
    }
    public void terminar(){
        MongoDB.cerrarConexion();
        System.out.println("Conexión con MongoDB cerrada con éxito.");
    }
}
