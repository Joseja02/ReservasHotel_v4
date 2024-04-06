package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("SOLO_ALOJAMIENTO", 0.0),
    ALOJAMIENTO_DESAYUNO("ALOJAMIENTO_DESAYUNO", 15.0),
    MEDIA_PENSION("MEDIA_PENSION", 30.0),
    PENSION_COMPLETA("PENSION_COMPLETA", 50.0);

    private final String cadenaAMostrar;
    private final double incrementoPrecio;

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }
    private Regimen (String cadenaAMostrar, double incrementoPrecio){
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }
    @Override
    public String toString() {
        return String.format("%d.- %s", ordinal(), cadenaAMostrar);
    }
}
