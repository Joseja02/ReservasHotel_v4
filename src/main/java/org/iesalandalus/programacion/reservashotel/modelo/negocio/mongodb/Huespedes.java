package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import javax.print.Doc;
import java.util.*;

public class Huespedes implements IHuespedes {
    private MongoCollection<Document> coleccionHuespedes;
    private final String COLECCION = "huespedes";

    public Huespedes() {

    }

    public List<Huesped> get() {
        List<Huesped> listaHuespedes = new ArrayList<>();
        coleccionHuespedes.find().forEach((docHuesped) -> {
            Huesped huesped = MongoDB.getHuesped(docHuesped);
            listaHuespedes.add(huesped);
        });
        listaHuespedes.sort(Comparator.comparing(Huesped::getDni));

        return listaHuespedes;
    }

    public int getTamano() { return (int) coleccionHuespedes.countDocuments(); }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }

        if (coleccionHuespedes.find(Filters.eq(huesped.getDni())).first().isEmpty()) {
            coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }

        Document documentoHuesped = coleccionHuespedes.find(MongoDB.getDocumento(huesped)).first();

        if (!documentoHuesped.isEmpty()) {
            return MongoDB.getHuesped(documentoHuesped);
        } else {
            System.out.println("Huésped no encontrado");
            return null;
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        Document documentoHuesped = coleccionHuespedes.find(Filters.eq(huesped.getDni())).first();

        if (documentoHuesped.isEmpty()) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        coleccionHuespedes.deleteOne(documentoHuesped);
    }
    public void comenzar(){
        MongoDatabase database = MongoDB.getBD();
        coleccionHuespedes = database.getCollection(COLECCION);
        System.out.println("Colección huespedes obtenida");
    }
    public void terminar(){
        MongoDB.cerrarConexion();
        System.out.println("Conexión con MongoDB cerrada con éxito.");
    }
}
