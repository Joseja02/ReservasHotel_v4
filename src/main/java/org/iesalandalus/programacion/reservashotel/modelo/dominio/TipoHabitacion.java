package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("SUITE"),
    SIMPLE("SIMPLE"),
    DOBLE("DOBLE"),
    TRIPLE("TRIPLE");

    private final String cadenaAMostrar;
    private TipoHabitacion(String cadenaAMostrar){
        this.cadenaAMostrar = cadenaAMostrar;
    }
    @Override
    public String toString() {
        return String.format("%d.- %s", ordinal(), cadenaAMostrar);
    }
}
