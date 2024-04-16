package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;

public enum FactoriaFuenteDatos {
    MEMORIA{
        public IFuenteDatos crear(){
            return new FuenteDatosMemoria();
        }
    },
    MONGODB{
        public IFuenteDatos crear(){
            return new FuenteDatosMongoDB();
        }
    };
    public abstract IFuenteDatos crear();
}
