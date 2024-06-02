package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import javax.naming.OperationNotSupportedException;
import javax.print.Doc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MongoDB {
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");


    private static final String SERVIDOR = "reservashotel.alippb6.mongodb.net/";

    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";

    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLE = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";
    private static MongoClient conexion;

    private MongoDB(){

    }
    public static MongoDatabase getBD(){
        if (conexion == null) {
            establecerConexion();
        }

        return conexion.getDatabase(BD);
    }

    private static void establecerConexion()
    {

        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;

        if (!SERVIDOR.equals("localhost"))
        {
            connectionString = "mongodb+srv://reservashotel:" + CONTRASENA + "@reservashotel.alippb6.mongodb.net/";
            serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
        }
        else
        {
            connectionString="mongodb://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + ":" + PUERTO ;
            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .credential(credenciales)
                    .build();
        }


        //Creamos la conexión con el serveridos según el setting anterior
        conexion = MongoClients.create(settings);

        try
        {
            if (!SERVIDOR.equals("localhost"))
            {
                MongoDatabase database = conexion.getDatabase(BD);
                database.runCommand(new Document("ping", 1));
            }
        }
        catch (MongoException e)
        {
            e.printStackTrace();

        }

        System.out.println("Conexión a MongoDB realizada correctamente.");

    }
    public static void cerrarConexion(){
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
    public static Document getDocumento(Huesped huesped){
        if (huesped == null) {
            return null;
        }
        String nombre = huesped.getNombre();
        String dni = huesped.getDni();
        String telefono = huesped.getTelefono();
        String correo = huesped.getCorreo();
        LocalDate fechaNacimiento = huesped.getFechaNacimiento();
        return new Document().append(NOMBRE, nombre).append(DNI, dni).append(TELEFONO, telefono).append(CORREO, correo).append(FECHA_NACIMIENTO, fechaNacimiento.format(FORMATO_DIA));
    }
    public static Huesped getHuesped(Document documentoHuesped){
        if (documentoHuesped == null) {
            return null;
        }
        LocalDate fechaLocalDate = LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA);
        return new Huesped(documentoHuesped.getString(NOMBRE), documentoHuesped.getString(DNI), documentoHuesped.getString(CORREO), documentoHuesped.getString(TELEFONO), fechaLocalDate);
    }
    public static Document getDocumento(Habitacion habitacion){
        if (habitacion == null) {
            return null;
        }
        String identificador = habitacion.getIdentificador();
        int planta = habitacion.getPlanta();
        int puerta = habitacion.getPuerta();
        double precio = habitacion.getPrecio();
        int numeroMaximoPersonas = habitacion.getNumeroMaximoPersonas();
        Document dHabitacion =  new Document().append(IDENTIFICADOR, identificador).append(PLANTA, planta).append(PUERTA, puerta).append(PRECIO, precio).append(NUMERO_PERSONAS, numeroMaximoPersonas);

        if (habitacion instanceof Simple){
            dHabitacion.append(TIPO, TIPO_SIMPLE);
        } else if (habitacion instanceof Doble){
            int numCamasIndividuales = ((Doble) habitacion).getNumCamasIndividuales();
            int numCamasDobles = ((Doble) habitacion).getNumCamasDobles();
            dHabitacion.append(TIPO, TIPO_DOBLE).append(CAMAS_INDIVIDUALES, numCamasIndividuales).append(CAMAS_DOBLE, numCamasDobles);
        } else if (habitacion instanceof Triple) {
            int numCamasIndividuales = ((Triple) habitacion).getNumCamasIndividuales();
            int numCamasDobles = ((Triple) habitacion).getNumCamasDobles();
            int numBanos = ((Triple) habitacion).getNumBanos();
            dHabitacion.append(TIPO, TIPO_TRIPLE).append(CAMAS_INDIVIDUALES, numCamasIndividuales).append(CAMAS_DOBLE, numCamasDobles).append(BANOS, numBanos);
        } else if (habitacion instanceof Suite) {
            int numBanos = ((Suite) habitacion).getNumBanos();
            boolean tieneJacuzzi = ((Suite) habitacion).isTieneJacuzzi();
            dHabitacion.append(TIPO, TIPO_SUITE).append(BANOS, numBanos).append(JACUZZI, tieneJacuzzi);
        }
        return dHabitacion;
    }
    public static Habitacion getHabitacion(Document documentoHabitacion){
        Habitacion habitacion = null;

        if (documentoHabitacion == null) {
            return null;
        }

        String tipo = documentoHabitacion.getString(TIPO);
        if (tipo.equals(TIPO_SIMPLE)){
            habitacion = new Simple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA), documentoHabitacion.getDouble(PRECIO));
        } else if (tipo.equals(TIPO_DOBLE)) {
            habitacion = new Doble(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA), documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES), documentoHabitacion.getInteger(CAMAS_DOBLE));
        } else if (tipo.equals(TIPO_TRIPLE)) {
            habitacion = new Triple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA), documentoHabitacion.getDouble(PRECIO),documentoHabitacion.getInteger(BANOS), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES), documentoHabitacion.getInteger(CAMAS_DOBLE));
        } else if (tipo.equals(TIPO_SUITE)) {
            habitacion = new Suite(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA), documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS), documentoHabitacion.getBoolean(JACUZZI));
        }
        return habitacion;
    }
    public static Reserva getReserva(Document documentoReserva){
        if (documentoReserva == null){
            return null;
        }

        Document dHuesped = (Document) documentoReserva.get(HUESPED);
        Huesped huesped = getHuesped(dHuesped);

        Document dHabitacion = (Document) documentoReserva.get(HABITACION);
        Habitacion habitacion = getHabitacion(dHabitacion);
        Regimen regimen = Regimen.SOLO_ALOJAMIENTO;

        if (documentoReserva.get(REGIMEN).equals(Regimen.SOLO_ALOJAMIENTO.toString())){
            regimen = Regimen.SOLO_ALOJAMIENTO;
        }
        if (documentoReserva.get(REGIMEN).equals(Regimen.ALOJAMIENTO_DESAYUNO.toString())){
            regimen = Regimen.ALOJAMIENTO_DESAYUNO;
        }
        if (documentoReserva.get(REGIMEN).equals(Regimen.MEDIA_PENSION.toString())){
            regimen = Regimen.MEDIA_PENSION;
        }
        if (documentoReserva.get(REGIMEN).equals(Regimen.PENSION_COMPLETA.toString())){
            regimen = Regimen.PENSION_COMPLETA;
        }

        LocalDate fechaInicioReserva = LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA), FORMATO_DIA);
        LocalDate fechaFinReserva = LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA), FORMATO_DIA);
        Reserva reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, documentoReserva.getInteger(NUMERO_PERSONAS));

        if (!(documentoReserva.getString(CHECKIN) == null)){
            LocalDateTime checkIn = LocalDateTime.parse(documentoReserva.getString(CHECKIN), FORMATO_DIA_HORA);
            reserva.setCheckIn(checkIn);
        }
        if (!(documentoReserva.getString(CHECKOUT) == null)){
            LocalDateTime checkOut = LocalDateTime.parse(documentoReserva.getString(CHECKOUT), FORMATO_DIA_HORA);
            reserva.setCheckOut(checkOut);
        }
        return reserva;
    }
    public static Document getDocumento(Reserva reserva){
        if (reserva == null) {
            return null;
        }
        Huesped huesped = reserva.getHuesped();
        Habitacion habitacion = reserva.getHabitacion();

        Document dHuesped = getDocumento(huesped);
        Document dHabitacion = getDocumento(habitacion);
        Regimen regimen = reserva.getRegimen();
        LocalDate fechaInicioReserva = reserva.getFechaInicioReserva();
        LocalDate fechaFinReserva = reserva.getFechaFinReserva();
        LocalDateTime checkIn = reserva.getCheckIn();
        LocalDateTime checkOut = reserva.getCheckOut();
        double precioReserva = reserva.getPrecio();
        int numeroPersonas = reserva.getNumeroPersonas();

        return new Document().append(HUESPED, dHuesped).append(HABITACION, dHabitacion).append(REGIMEN, regimen.toString()).append(FECHA_INICIO_RESERVA, fechaInicioReserva.format(FORMATO_DIA)).append(FECHA_FIN_RESERVA, fechaFinReserva.format(FORMATO_DIA)).append(CHECKIN, checkIn).append(CHECKOUT, checkOut).append(PRECIO_RESERVA, precioReserva).append(NUMERO_PERSONAS, numeroPersonas);
    }

}
