package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {

    SALIR("Salir") {
        @Override
        public void ejecutar() {
            System.out.print("¡Hasta luego! - Tarea Online 6 | Jose Javier Sierra Berdún");
        }
    },
    INSERTAR_HUESPED("Insertar Huésped") {
        @Override
        public  void ejecutar() {
            vista.insertarHuesped();
        }
    },
    BUSCAR_HUESPED("Buscar Huésped") {
        @Override
        public void ejecutar() {
            vista.buscarHuesped();
        }
    },
    BORRAR_HUESPED("Borrar Huésped") {
        @Override
        public void ejecutar() {
            vista.borrarHuesped();
        }
    },
    MOSTRAR_HUESPEDES("Mostar Huéspedes"){
        @Override
        public void ejecutar() {
            vista.mostrarHuespedes();
        }
    },
    INSERTAR_HABITACION("Insertar Habitación"){
        @Override
        public void ejecutar() {
            vista.insertarHabitacion();
        }
    },
    BUSCAR_HABITACION("Buscar Habitación"){
        @Override
        public void ejecutar() {
            vista.buscarHabitacion();
        }
    },
    BORRAR_HABITACION("Borrar Habitación"){
        @Override
        public void ejecutar() {
            vista.borrarHabitacion();
        }
    },
    MOSTRAR_HABITACIONES("Mostrar Habitaciones"){
        @Override
        public void ejecutar() {
            vista.mostrarHabitaciones();
        }
    },
    INSERTAR_RESERVA("Insertar Reserva"){
        @Override
        public void ejecutar() {
            vista.insertarReserva();
        }
    },
    ANULAR_RESERVA("Anular Reserva"){
        @Override
        public void ejecutar() {
            vista.anularReserva();
        }
    },
    MOSTRAR_RESERVAS("Mostrar Reservas"){
        @Override
        public void ejecutar() {
            vista.mostrarReservas();
        }
    },
    LISTAR_RESERVAS_HUESPED("Listar Reservas de un huésped"){
        @Override
        public void ejecutar() {
            vista. mostrarReservasHuesped();
        }
    },
    LISTAR_RESERVAS_TIPO_HABITACION("Listar Reservas de un tipo de habitación"){
        @Override
        public void ejecutar() {
            vista. mostrarReservasTipoHabitacion();
        }
    },
    CONSULTAR_DISPONIBILIDAD("Consultar Disponibilidad"){
        @Override
        public void ejecutar() {
            vista.comprobarDisponibilidad();
        }
    },
    REALIZAR_CHECKIN("Realizar Checkin"){
        @Override
        public void ejecutar() {
            vista.realizarCheckin();
        }
    },
    REALIZAR_CHECKOUT("Realizar Checkout"){
        @Override
        public void ejecutar() {
            vista.realizarCheckOut();
        }
    };
    private String mensajeAMostrar;
    private static Vista vista;

    public abstract void ejecutar();

    static void setVista(Vista vista) {
        if (vista == null) {
            throw new NullPointerException("ERROR: La vista no puede ser nula.");
        }
        Opcion.vista = vista;
    }
    private Opcion(String mensajeAMostrar) {
        this.mensajeAMostrar = mensajeAMostrar;
    }
    @Override
    public String toString() {
        return String.format("%d.- %s", ordinal(), mensajeAMostrar);
    }
}