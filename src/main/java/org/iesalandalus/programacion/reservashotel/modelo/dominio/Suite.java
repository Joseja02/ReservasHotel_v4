package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Suite extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS = 4;
    static final int MIN_NUM_BANOS = 1;
    static final int MAX_NUM_BANOS = 2;
    private int numBanos;
    private boolean tieneJacuzzi;

    public Suite(int planta, int puerta, double precio, int numBanos, boolean tieneJacuzzi){
        super(planta, puerta, precio);
        setNumBanos(numBanos);
        setTieneJacuzzi(tieneJacuzzi);
    }
    public Suite (Suite habitacionSuite){
        super(habitacionSuite);
        this.numBanos = getNumBanos();
        this.tieneJacuzzi = isTieneJacuzzi();
    }
    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        if (numBanos < MIN_NUM_BANOS || numBanos > MAX_NUM_BANOS){
            throw new IllegalArgumentException("ERROR: El número de baños no puede ser inferior a 1 ni superior a 2");
        }
        this.numBanos = numBanos;
    }

    public boolean isTieneJacuzzi() {
        return tieneJacuzzi;
    }

    public void setTieneJacuzzi(boolean tieneJacuzzi) {
        this.tieneJacuzzi = tieneJacuzzi;
    }
    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }
    @Override
    public String toString() {
        return super.toString() + ", habitación suite, " + "capacidad=" + NUM_MAXIMO_PERSONAS +
                " personas," + " baños=" + getNumBanos() + (isTieneJacuzzi() ? ", con Jacuzzi" : ", sin Jacuzzi");
    }
}
