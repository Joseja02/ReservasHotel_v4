package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Doble;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Simple;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Suite;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitacionesTest {
    private static final String ERROR_INSERTAR_HABITACION_NULA = "ERROR: No se puede insertar una habitación nula.";
    private static final String ERROR_BORRAR_HABITACION_NULA = "ERROR: No se puede borrar una habitación nula.";
    private static final String ERROR_HABITACION_EXISTE = "ERROR: Ya existe una habitación con ese identificador.";
    private static final String ERROR_HABITACION_BORRAR_NO_EXISTE = "ERROR: No existe ninguna habitación como la indicada.";

    private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
    private static final String HABITACION_NULA = "Debería haber saltado una excepción indicando que no se puede operar con un habitación nula.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String TIPO_EXCEPCION_NO_CORRECTO = "El tipo de la excepción no es correcto.";
    private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
    private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
    private static final String HABITACIONES_NO_CREADAS = "Debería haber creado las habitaciones correctamente.";
    private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
    private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
    private static final String HABITACION_NO_ESPERADA = "La habitación devuelta no es la que debería ser.";

    private static final String MENSAJE_ERROR_BUSCAR_HABITACION_NULA = "ERROR: No se puede buscar una habitación nula.";



    private static Habitacion habitacion1;
    private static Habitacion habitacion2;
    private static Habitacion habitacion3;
    private static Habitacion habitacionRepetida1;

    private static final int PLANTA_1=1;
    private static final int PLANTA_2=2;
    private static final int PLANTA_3=3;
    private static final int PUERTA_0=0;
    private static final int PUERTA_10=10;

    private static final double PRECIO_HABITACION_VALIDO=50;


    @BeforeAll
    public static void asignarValoresAtributos() {
        habitacion1 = new Simple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO);
        habitacion2 = new Doble(PLANTA_2, PUERTA_10, PRECIO_HABITACION_VALIDO,2,0);
        habitacion3 = new Suite(PLANTA_3, PUERTA_10, PRECIO_HABITACION_VALIDO, 2, true);
        habitacionRepetida1 =new Simple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO);

    }

    @Test
    public void constructorCreaHabitacionesCorrectamente() {
        Habitaciones habitaciones = new Habitaciones();
        assertNotEquals(null, habitaciones, HABITACIONES_NO_CREADAS);
        assertEquals(0, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
    }

    @Test
    public void insertarHabitacionValidaInsertaHabitacionCorrectamente() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);

            List<Habitacion> copiaHabitaciones = habitaciones.get();
            assertEquals(1, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion1, copiaHabitaciones.get(0), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion1, copiaHabitaciones.get(0), OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarDosHabitacionesValidasInsertaHabitacionesCorrectamente() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);

            List<Habitacion> copiaHabitaciones = habitaciones.get();
            assertEquals(2, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion1, copiaHabitaciones.get(0), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion1, copiaHabitaciones.get(0), OPERACION_NO_REALIZADA);
            assertEquals(habitacion2, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion2, copiaHabitaciones.get(1), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion2, copiaHabitaciones.get(1), OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarTresHabitacionesValidasInsertaHabitacionesCorrectamente() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);

            List<Habitacion> copiaHabitaciones = habitaciones.get();

            assertEquals(3, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion1, copiaHabitaciones.get(0), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion1, copiaHabitaciones.get(0), OPERACION_NO_REALIZADA);
            assertEquals(habitacion2, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion2, copiaHabitaciones.get(1), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion2, copiaHabitaciones.get(1), OPERACION_NO_REALIZADA);
            assertEquals(habitacion3, habitaciones.buscar(habitacion3), HABITACION_NO_ESPERADA);
            assertNotSame(habitacion3, copiaHabitaciones.get(2), REFERENCIA_NO_ESPERADA);
            assertEquals(habitacion3, copiaHabitaciones.get(2), OPERACION_NO_REALIZADA);

        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarHabitacionNulaLanzaExcepcion() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(null);
            fail(HABITACION_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_INSERTAR_HABITACION_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(0, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarHabitacionRepetidaLanzaExcepcion() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.insertar(habitacionRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_HABITACION_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion3);
            habitaciones.insertar(habitacionRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_HABITACION_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacionRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_HABITACION_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    void buscarHabitacionExistenteDevuelveHabitacionCorrectamente() {
        Habitaciones habitaciones = new Habitaciones();
        assertDoesNotThrow(() -> habitaciones.insertar(habitacion1));
        assertEquals(habitacion1, habitaciones.buscar(habitacion1));
        assertSame(habitacion1, habitaciones.buscar(habitacion1));
    }

    @Test
    void buscarHabitacionNoExistenteDevuelveHabitacionNula() {
        Habitaciones habitaciones = new Habitaciones();
        assertNull(habitaciones.buscar(habitacion1));
    }

    @Test
    void buscarHabitacionNulaLanzaExcepcion() {
        Habitaciones habitaciones = new Habitaciones();

        assertDoesNotThrow(() -> habitaciones.insertar(habitacion1));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> habitaciones.buscar(null));
        assertEquals(MENSAJE_ERROR_BUSCAR_HABITACION_NULA, npe.getMessage());
    }

    @Test
    public void borrarHabitacionExistenteBorraHabitacionCorrectamente() throws OperationNotSupportedException {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);
            habitaciones.borrar(habitacion1);
            assertEquals(0, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(null, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.borrar(habitacion1);
            assertEquals(1, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(habitacion2, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertEquals(null, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.borrar(habitacion2);
            assertEquals(1, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertEquals(null, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.borrar(habitacion1);
            assertEquals(2, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(null, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertEquals(habitacion2, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertEquals(habitacion3, habitaciones.buscar(habitacion3), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.borrar(habitacion2);
            assertEquals(2, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(null, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertEquals(habitacion3, habitaciones.buscar(habitacion3), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.borrar(habitacion3);
            assertEquals(2, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(null, habitaciones.buscar(habitacion3), HABITACION_NO_ESPERADA);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertEquals(habitacion2, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.insertar(habitacion3);
            habitaciones.borrar(habitacion2);
            assertEquals(2, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(null, habitaciones.buscar(habitacion2), HABITACION_NO_ESPERADA);
            assertEquals(habitacion1, habitaciones.buscar(habitacion1), HABITACION_NO_ESPERADA);
            assertEquals(habitacion3, habitaciones.buscar(habitacion3), HABITACION_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void borrarHabitacionNoExistenteLanzaExcepcion() {
        Habitaciones habitaciones = new Habitaciones();

        try {
            habitaciones.insertar(habitacion1);
            habitaciones.borrar(habitacion2);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_HABITACION_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, habitaciones.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }


        habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            habitaciones.borrar(habitacion3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_HABITACION_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, habitaciones.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarHabitacionNulaLanzaExcepcion() {
        Habitaciones habitaciones = new Habitaciones();
        try {
            habitaciones.insertar(habitacion1);
            habitaciones.borrar(null);
            fail(HABITACION_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_BORRAR_HABITACION_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, habitaciones.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }
}
