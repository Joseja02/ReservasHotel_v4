package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.Reservas;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva.FORMATO_FECHA_RESERVA;

public class Vista {

    private static Controlador controlador;
    public Vista(){
        Opcion.setVista(this);
    }
    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: No se puede asignar un controlador nulo");
        }
        Vista.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        }
        while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.print("�Hasta luego! - Tarea Online 10 | Jose Javier Sierra Berd�n");
    }

    public static void insertarHuesped() {
        try {
            Huesped huesped = Consola.leerHuesped();
            controlador.insertar(huesped);
            System.out.print("Huesped ha sido insertado");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException |
                 DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void buscarHuesped() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            if (huesped != null) {
                System.out.println(huesped);
            } else {
                System.out.print("El huesped no existe");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

    }

    public static void borrarHuesped() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            List<Reserva> reservasHuesped = controlador.getReservas(huesped);
            for (int i = 0; i < reservasHuesped.size(); i++) {
                if (reservasHuesped.get(i).getHuesped().equals(huesped)){
                    throw new OperationNotSupportedException("ERROR: No se puede borrar un hu�sped que tiene, al menos, una reserva hecha.");
                }
            }
            controlador.borrar(huesped);
            System.out.print("El hu�sped ha sido borrado");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void mostrarHuespedes() {
        try {
            List<Huesped> huespedesAMostrar = controlador.getHuespedes();
            if (!huespedesAMostrar.isEmpty()) {
                System.out.println("Estos son los Huespedes existentes: ");
                System.out.println(" ");
                Collections.sort(huespedesAMostrar, Comparator.comparing(Huesped::getNombre));
                for (Huesped huesped : huespedesAMostrar) {
                    System.out.println(huesped);
                }
            } else {
                System.out.println("No existen hu�spedes ");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void insertarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacion();
            controlador.insertar(habitacion);
            System.out.print("La habitaci�n ha sido insertada");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void buscarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            habitacion = controlador.buscar(habitacion);
            if (habitacion != null) {
                System.out.println(habitacion);
            } else {
                System.out.print("La habitaci�n no existe");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void borrarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            List<Reserva> reservasHabitacion = controlador.getReservas(habitacion);
            for (int i = 0; i < reservasHabitacion.size(); i++) {
                if (reservasHabitacion.get(i).getHabitacion().equals(habitacion)){
                    throw new OperationNotSupportedException("ERROR: No se puede borrar una habitaci�n que tiene, al menos, una reserva asignada.");
                }
            }
            controlador.borrar(habitacion);
            System.out.print("La habitaci�n ha sido borrada");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void mostrarHabitaciones() {
        try {
            List<Habitacion> habitacionesAMostrar = controlador.getHabitaciones();
            if (!habitacionesAMostrar.isEmpty()) {
                System.out.println("Estas son las Habitaciones existentes: ");
                System.out.println(" ");
                Collections.sort(habitacionesAMostrar, Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta));
                for (Habitacion habitacion : habitacionesAMostrar) {
                    System.out.println(habitacion);
                }

            } else {
                System.out.println("No existen habitaciones ");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void insertarReserva() {
        try {
            Reserva reserva = Consola.leerReserva();

            Huesped huespedReserva = reserva.getHuesped();
            huespedReserva = controlador.buscar(huespedReserva);
            reserva.setHuesped(huespedReserva);

            Habitacion habitacionReserva = reserva.getHabitacion();
            habitacionReserva = controlador.buscar(habitacionReserva);
            reserva.setHabitacion(habitacionReserva);

            TipoHabitacion tipoHabitacionReserva = null;

            if (habitacionReserva instanceof Simple){
                tipoHabitacionReserva = TipoHabitacion.SIMPLE;
            }
            if (habitacionReserva instanceof Doble){
                tipoHabitacionReserva = TipoHabitacion.DOBLE;
            }
            if (habitacionReserva instanceof Triple){
                tipoHabitacionReserva = TipoHabitacion.TRIPLE;
            }
            if (habitacionReserva instanceof Suite){
                tipoHabitacionReserva = TipoHabitacion.SUITE;
            }

            Habitacion habitacionDisponible = consultarDisponibilidad(tipoHabitacionReserva, reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());

            if (habitacionDisponible != null) {
                Reserva reservaExistente = controlador.buscar(reserva);

                if (reservaExistente == null) {
                    controlador.insertar(reserva);
                    System.out.print("La reserva ha sido registrada");
                } else {
                    System.out.print("ERROR: No es posible registrar esta reserva porque ya existe otra reserva para la misma fecha y habitaci�n seleccionada");
                }
            } else {
                System.out.println("ERROR: La habitaci�n que intentas reservar no est� disponible");
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException |DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    public void mostrarReservasHuesped(){
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            listarReservas(huesped);
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException e){
            System.out.println(e.getMessage());
        }
    }

    public static void listarReservas(Huesped huesped) {
        try {
            if (!controlador.getReservas().isEmpty()) {
                System.out.println("Estas son las reservas para este hu�sped: ");
                System.out.println(" ");

                List<Reserva> reservasHuesped = controlador.getReservas(huesped);
                if (!reservasHuesped.isEmpty()) {
                    Collections.sort(reservasHuesped, Comparator.comparing(Reserva::getFechaInicioReserva).reversed());

                    boolean mismaFechaInicio = false;
                    for (int i = 0; i < reservasHuesped.size() - 1; i++) {
                        if (reservasHuesped.get(i).getFechaInicioReserva().equals(reservasHuesped.get(i + 1).getFechaInicioReserva())) {
                            mismaFechaInicio = true;
                        }
                    }
                    if (mismaFechaInicio) {
                        Comparator<Habitacion> comparadorHabitacion = Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta);
                        reservasHuesped.sort(Comparator.comparing(Reserva::getHabitacion, comparadorHabitacion));
                    }

                    Iterator<Reserva> iterador = reservasHuesped.iterator();
                    while (iterador.hasNext()) {
                        Reserva reserva = iterador.next();
                        System.out.println(reserva);
                    }

                } else {
                    System.out.println("No existen reservas para este hu�sped");
                }

            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public void mostrarReservasTipoHabitacion(){
        TipoHabitacion tipoHabitacion = null;
        Habitacion habitacion = Consola.leerHabitacionPorIdentificador();

        if (habitacion instanceof Simple) tipoHabitacion = TipoHabitacion.SIMPLE;
        if (habitacion instanceof Doble) tipoHabitacion = TipoHabitacion.DOBLE;
        if (habitacion instanceof Triple) tipoHabitacion = TipoHabitacion.TRIPLE;
        if (habitacion instanceof Suite) tipoHabitacion = TipoHabitacion.SUITE;

        listarReservas(tipoHabitacion);
    }

    public void comprobarDisponibilidad(){
        try {
            System.out.println("Introduce el tipo de Habitaci�n: ");
            for (TipoHabitacion opcion : TipoHabitacion.values()) {
                System.out.println(opcion);
            }
            int eleccionHabitacion = Entrada.entero();
            if (eleccionHabitacion < 0 || eleccionHabitacion > TipoHabitacion.values().length - 1) {
                throw new IllegalArgumentException("ERROR: El tipo de habitaci�n escogido no existe o est� fuera de rango.");
            }
            LocalDate fechaInicioReserva = null;
            LocalDate fechaFinReserva = null;
            TipoHabitacion tipoHabitacion = null;

            tipoHabitacion = TipoHabitacion.values()[eleccionHabitacion];
            System.out.print("Introduzca la fecha inicio de reserva(" + FORMATO_FECHA_RESERVA + "): ");
            fechaInicioReserva = LocalDate.parse(Entrada.cadena(), DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA));
            System.out.print("Introduzca la fecha inicio de reserva(" + FORMATO_FECHA_RESERVA + "): ");
            fechaFinReserva = LocalDate.parse(Entrada.cadena(), DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA));


            Habitacion habitacionDisponible = consultarDisponibilidad(tipoHabitacion, fechaInicioReserva, fechaFinReserva);
            Habitacion habitacion = controlador.buscar(habitacionDisponible);

            if (habitacionDisponible == null) {
                throw new NullPointerException("ERROR: El tipo de habitaci�n solicitado no est� disponible.");
            } else {
                System.out.println("La siguiente habitaci�n est� disponible:");
                System.out.println(habitacion);
            }

        } catch (DateTimeParseException e){
            System.out.println("ERROR: La fecha introducida tiene un formato err�neo.");
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public static void listarReservas(TipoHabitacion tipoHabitacion) {
        try {
            if (!controlador.getReservas().isEmpty()) {
                System.out.println("Estas son las reservas para este tipo de habitaci�n: ");
                System.out.println(" ");

                List<Reserva> reservasHuesped = controlador.getReservas(tipoHabitacion);
                if (!reservasHuesped.isEmpty()) {
                    Collections.sort(reservasHuesped, Comparator.comparing(Reserva::getFechaInicioReserva).reversed());

                    boolean mismaFechaInicio = false;
                    for (int i = 0; i < reservasHuesped.size() - 1; i++) {
                        if (reservasHuesped.get(i).getFechaInicioReserva().equals(reservasHuesped.get(i + 1).getFechaInicioReserva())) {
                            mismaFechaInicio = true;
                        }
                    }
                    if (mismaFechaInicio) {
                        Comparator<Huesped> comparadorHuesped = Comparator.comparing(Huesped::getNombre);
                        reservasHuesped.sort(Comparator.comparing(Reserva::getHuesped, comparadorHuesped));
                    }

                    Iterator<Reserva> iterador = reservasHuesped.iterator();
                    while (iterador.hasNext()) {
                        Reserva reserva = iterador.next();
                        System.out.println(reserva);
                    }
                } else {
                    System.out.println("No existen reservas para este tipo de habitaci�n");
                }

            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public static List<Reserva> getReservasAnulables(List<Reserva> reservasAAnular) throws OperationNotSupportedException {
        List<Reserva> reservasAnulables = new ArrayList<>();
        for (int i = 0; i < reservasAAnular.size(); i++) {
            LocalDate fechaActual = LocalDate.now();
            if (fechaActual.isBefore(reservasAAnular.get(i).getFechaInicioReserva())) {
                reservasAnulables.add(new Reserva(reservasAAnular.get(i)));
            }
        }
        return reservasAnulables;
    }

    public static void anularReserva() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            if (huesped != null) {
                List<Reserva> reservasHuesped = controlador.getReservas(huesped);
                if (!reservasHuesped.isEmpty()) {
                    List<Reserva> reservasAnulables = getReservasAnulables(reservasHuesped);
                    if (reservasAnulables.size() <= 0) {
                        System.out.println("No existen reservas anulables para este huesped");
                    } else {
                        if (reservasAnulables.size() > 1) {
                            int eleccion = -1;
                            do {
                                for (int j = 0; j < reservasAnulables.size(); j++) {
                                    System.out.println(j + " - " + reservasAnulables.get(j).toString());
                                }
                                System.out.print("Escoja la reserva que desea anular: ");
                                eleccion = Entrada.entero();
                            } while (eleccion < 0 || eleccion > reservasAnulables.size());
                            controlador.borrar(reservasAnulables.get(eleccion));
                        } else {
                            //Solo Existe una reserva anulable para este huesped
                            System.out.println(reservasAnulables.get(0).toString());
                            System.out.println("Est� seguro de que desea anular esta reserva (S/N): ");
                            char respuesta = Entrada.caracter();
                            if (Character.toString(respuesta).equalsIgnoreCase("s")) {
                                controlador.borrar(reservasAnulables.get(0));
                                System.out.println("Reserva anulada exitosamente.");
                            }
                        }
                    }
                } else {
                    System.out.println("No existen reservas para este huesped");
                }

            } else {
                System.out.print("El huesped no existe");
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void mostrarReservas() {
        try {
            if (controlador.getReservas() != null && !controlador.getReservas().isEmpty()) {
                System.out.println("Estas son las reservas existentes: ");
                System.out.println(" ");

                List<Reserva> reservasAMostrar = controlador.getReservas();

                if (!reservasAMostrar.isEmpty()) {
                    Collections.sort(reservasAMostrar, Comparator.comparing(Reserva::getFechaInicioReserva).reversed());
                }

                boolean mismaFechaInicio = false;
                for (int i = 0; i < reservasAMostrar.size() - 1; i++) {
                    if (reservasAMostrar.get(i).getFechaInicioReserva().equals(reservasAMostrar.get(i + 1).getFechaInicioReserva())) {
                        mismaFechaInicio = true;
                    }
                }

                if (mismaFechaInicio) {
                    Comparator<Habitacion> comparadorHabitacion = Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta);
                    reservasAMostrar.sort(Comparator.comparing(Reserva::getHabitacion, comparadorHabitacion));
                }

                Iterator<Reserva> iterador = reservasAMostrar.iterator();
                while (iterador.hasNext()) {
                    Reserva reserva = iterador.next();
                    System.out.println(reserva.toString());
                    System.out.println(" ");
                }
            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public static int getNumElementosNoNulos(List<Reserva> arrayReservas) {
        int noNulos = 0;
        for (int i = 0; i < arrayReservas.size(); i++) {
            if (arrayReservas.get(i) != null) {
                noNulos++;
            }
        }
        return noNulos;
    }

    public static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) {
        boolean tipoHabitacionEncontrada = false;
        Habitacion habitacionDisponible = null;
        int numElementos = 0;

        List<Habitacion> habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado == null)
            return habitacionDisponible;
        for (int i = 0; i < habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++) {

            if (habitacionesTipoSolicitado.get(i) != null) {
                List<Reserva> reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado.get(i));
                numElementos = getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0) {
                    if (habitacionesTipoSolicitado.get(i) instanceof Simple){
                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Doble){
                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Triple){
                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Suite){
                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                    }
                    tipoHabitacionEncontrada = true;
                } else {

                    reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        if (habitacionesTipoSolicitado.get(i) instanceof Simple){
                            habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Doble){
                            habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Triple){
                            habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Suite){
                            habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                        }
                        tipoHabitacionEncontrada = true;
                    }
                    if (!tipoHabitacionEncontrada) {

                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaInicioReserva));

                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            if (habitacionesTipoSolicitado.get(i) instanceof Simple){
                                habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Doble){
                                habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Triple){
                                habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Suite){
                                habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                            }
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    if (!tipoHabitacionEncontrada) {
                        for (int j = 1; j < reservasFuturas.size() && !tipoHabitacionEncontrada; j++) {
                            if (reservasFuturas.get(j) != null && reservasFuturas.get(j - 1) != null) {
                                if (fechaInicioReserva.isAfter(reservasFuturas.get(j - 1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    if (habitacionesTipoSolicitado.get(i) instanceof Simple){
                                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Doble){
                                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Triple){
                                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Suite){
                                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                    }
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return habitacionDisponible;
    }

    public static void realizarCheckin() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            boolean checkinFallido = false;

            List<Reserva> reservasDelHuesped = controlador.getReservas(huesped);

            for (int i = 0; i < reservasDelHuesped.size(); i++) {

                if (reservasDelHuesped.get(i).getFechaInicioReserva().isEqual(LocalDate.now())) {
                    controlador.realizarCheckin(reservasDelHuesped.get(i), LocalDateTime.now());
                    System.out.println("CheckIn de la reserva registrado.");
                } else {
                    checkinFallido = true;
                }
            }
            if (checkinFallido) {
                System.out.println("AVISO: Hay al menos 1 reserva de la que no se ha podido hacer un Checkin al ser de un d�a distinto");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public static void realizarCheckOut() {
        try {

            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            boolean checkinFallido = false;

            List<Reserva> reservasDelHuesped = controlador.getReservas(huesped);

            for (int i = 0; i < reservasDelHuesped.size(); i++) {

                if (reservasDelHuesped.get(i).getFechaFinReserva().isEqual(LocalDate.now())) {
                    controlador.realizarCheckout(reservasDelHuesped.get(i), LocalDateTime.now());
                    System.out.println("CheckOut de la reserva registrado.");
                } else {
                    checkinFallido = true;
                }
            }
            if (checkinFallido) {
                System.out.println("AVISO: Hay al menos 1 reserva de la que no se ha podido hacer un Checkout al ser de un d�a distinto");
            }

        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
