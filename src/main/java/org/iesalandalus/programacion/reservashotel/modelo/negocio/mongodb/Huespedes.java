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
        comenzar();
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

        Document documentoHuespedColeccion = coleccionHuespedes.find(Filters.eq("dni", huesped.getDni())).first();
        Document documentoHuespedParametro = MongoDB.getDocumento(huesped);

        if (documentoHuespedColeccion == null){
            coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
        } else {
            if (documentoHuespedColeccion.get("dni").equals(documentoHuespedParametro.get("dni"))) {
                throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
            } else {
                coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
            }
        }
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }

        Document documentoHuespedColeccion = coleccionHuespedes.find(Filters.eq("dni", huesped.getDni())).first();
        Document documentoHuespedParametro = MongoDB.getDocumento(huesped);

        if (documentoHuespedColeccion == null){
            return null;
        }

        if (documentoHuespedColeccion.get("dni").equals(documentoHuespedParametro.get("dni"))) {
            return MongoDB.getHuesped(documentoHuespedColeccion);
        } else {
            return null;
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        Document documentoHuespedColeccion = coleccionHuespedes.find(Filters.eq("dni", huesped.getDni())).first();
        Document documentoHuespedParametro = MongoDB.getDocumento(huesped);

        if(documentoHuespedColeccion == null){
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        if (documentoHuespedColeccion.get("dni").equals(documentoHuespedParametro.get("dni"))) {
            coleccionHuespedes.deleteOne(documentoHuespedColeccion);
        }
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
