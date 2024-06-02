package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import com.sun.source.tree.InstanceOfTree;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {

    public static final int MAX_NUMERO_MESES_RESERVA = 6;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    public static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yyyy HH:mm";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas) {
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
    }

    public Reserva(Reserva reservaCopia) {
        if (reservaCopia == null) {
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        }
        this.huesped = reservaCopia.getHuesped();
        this.habitacion = reservaCopia.getHabitacion();
        this.regimen = reservaCopia.getRegimen();
        this.fechaInicioReserva = reservaCopia.getFechaInicioReserva();
        this.fechaFinReserva = reservaCopia.getFechaFinReserva();
        this.numeroPersonas = reservaCopia.getNumeroPersonas();
        this.precio = reservaCopia.getPrecio();
        this.checkIn = reservaCopia.getCheckIn();
        this.checkOut = reservaCopia.getCheckOut();
    }

    public Huesped getHuesped() {
        return new Huesped(huesped);
    }

    public void setHuesped(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        }
        this.huesped = new Huesped(huesped);
    }

    public Habitacion getHabitacion() {
        if (habitacion instanceof Simple){
            return new Simple(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio());
        }
        if (habitacion instanceof Doble){
            return new Doble(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Doble) habitacion).getNumCamasIndividuales(), ((Doble) habitacion).getNumCamasDobles());
        }
        if (habitacion instanceof Triple){
           return new Triple(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Triple) habitacion).getNumBanos(), ((Triple) habitacion).getNumCamasIndividuales(), ((Triple) habitacion).getNumCamasDobles());
        }
        if (habitacion instanceof Suite){
            return new Suite(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Suite) habitacion).getNumBanos(), ((Suite) habitacion).isTieneJacuzzi());
        }
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        }
        if (habitacion instanceof Simple){
            this.habitacion = new Simple(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio());
        }
        if (habitacion instanceof Doble){
            this.habitacion = new Doble(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Doble) habitacion).getNumCamasIndividuales(), ((Doble) habitacion).getNumCamasDobles());
        }
        if (habitacion instanceof Triple){
            this.habitacion = new Triple(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Triple) habitacion).getNumBanos(), ((Triple) habitacion).getNumCamasIndividuales(), ((Triple) habitacion).getNumCamasDobles());
        }
        if (habitacion instanceof Suite){
            this.habitacion = new Suite(habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Suite) habitacion).getNumBanos(), ((Suite) habitacion).isTieneJacuzzi());
        }
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen == null) {
            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        }
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if (fechaInicioReserva == null) {
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        }
        if (fechaInicioReserva.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
        }
        if (fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        }

        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {

        if (fechaFinReserva == null) {
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        }
        if (fechaFinReserva.isBefore(fechaInicioReserva) || fechaFinReserva.isEqual(fechaInicioReserva)) {
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        }
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {

        if (checkIn == null) {
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        }

        if (checkIn.isBefore(fechaInicioReserva.atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        }

        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {

        if (checkOut == null) {
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        }
        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        }
        if (checkOut.isAfter(fechaFinReserva.atTime(LocalTime.of(MAX_HORAS_POSTERIOR_CHECKOUT, 0, 0)))) {
            throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        }

        this.checkOut = checkOut;
    }

    public double getPrecio() {
        setPrecio();
        return precio;
    }

    private void setPrecio() {
        double precio = 0;
        double costeHabitacion = getHabitacion().getPrecio();
        double costeRegimen = getRegimen().getIncrementoPrecio() * getNumeroPersonas();

        Period period = Period.between(getFechaInicioReserva(), getFechaFinReserva());

        precio = (costeHabitacion + costeRegimen) * period.getDays();

        if (precio <= 0) {
            throw new IllegalArgumentException("ERROR: El precio no puede ser menor o igual a 0");
        }
        this.precio = precio;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas <= 0) {
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        }
        if (numeroPersonas > habitacion.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establecidas para el tipo de habitación reservada.");
        }

        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {
        return "Huesped: " + huesped.getNombre() + " " + huesped.getDni() +
                " Habitación:" + habitacion.getIdentificador() + " - " +
                " Fecha Inicio Reserva: " + getFechaInicioReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)) +
                " Fecha Fin Reserva: " + getFechaFinReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)) +
                " Checkin: " + (getCheckIn() == null ? "No registrado" : getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA))) +
                " Checkout: " + (getCheckOut() == null ? "No registrado" : getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA))) +
                " Precio: " + String.format("%.2f", getPrecio()) +
                " Personas: " + getNumeroPersonas();
    }
}
