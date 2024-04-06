# Tarea: Reservas Hotel V4
## Profesor: Andrés Rubio del Río
## Alumno: Jose Javier Sierra Berdún

Nuestro cliente está muy satisfecho de como está quedando la aplicación. Sin embargo, nos acaba de comentar que por favor 
añadamos persistencia a los datos introducidos, ya que tal y como está ahora la aplicación no es **funcional**. Por tanto, en 
este **quinto spring** añadiremos persistencia a los datos utilizando para ello bases de datos no relacionales. Por tal motivo, 
añadiremos al modelo que teníamos de memoria, un modelo de base de datos. Además, el cliente ha añadido un nuevo requerimiento: 
quiere que la aplicación no permita borrar un huésped ni una habitación si existe alguna reserva para dicho húesped o para dicha habitación.

En definitiva, lo que nos pide el cliente es lo siguiente:

1. Que la aplicación almacene los datos en una base de datos creada para tal efecto. 
2. La base de datos debe estar implementada en MongoDB.
3. Acomodar el proyecto para que gradle gestione la dependencia con el driver para java de MongoDB en su última vesión.
4. Modificar el proyecto para que se puedan ejecutar las dos versiones: memoria con vista textual y BD con vista textual.
5. Gestionar los huéspedes para que su persistencia se lleve a cabo por medio de dicha BD.
6. Gestionar las habitaciones para que su persistencia se lleve a cabo por medio de dicha BD.
7. Gestionar las reservas para que su persistencia se lleve a cabo por medio de dicha BD.
8. Impedir el borrado de un húesped si éste tiene ya una reserva.
9. Impedir el borrado de una habitación si ésta tiene ya una reseva.

Para ello te muestro un diagrama de clases para el mismo y poco a poco te iré explicando los diferentes pasos a realizar:

![Diagrama de clases para reservasHotel](https://raw.githubusercontent.com/andresrubiodelrio/ReservasHotel_v4/master/src/main/resources/uml/ReservasHotel_v4.png)

#### Primeros Pasos
1. Lo primero que debes hacer es realizar un **fork** del repositorio donde he colocado el esqueleto de este proyecto.
2. Clona tu repositorio remoto recién copiado en GitHub a un repositorio local que será donde irás realizando lo que a continuación se te pide.
3. Modifica el archivo `README.md` para que incluya el nombre de tu profesor en el apartado "Profesor" y tu nombre en el apartado "Alumno".
4. Realiza tu **primer commit**.

#### Creación de base de datos NoSQL (MongoDB) en la nube
1. Crea un nuevo perfil en [DB Atlas](https://www.mongodb.com/es/cloud/atlas/efficiency) que te permita desplegar un cluster con una base de datos MongoDB en la nube.
2. Crea una base de datos llamada **reservashotel**.
3. Crea tres colecciones en dicha base de datos llamadas **huespedes**, **habitaciones** y **reservas**.
4. Crea un nuevo usuario llamado **reservashotel** y con password **reservashotel-2024**. Este usuario deberá tener **permisos de lectura y escritura** sobre la base de datos.


#### Paquete mongodb.utilidades
Crea la clase `MongoDB` para que se ajuste a lo mostrado a continuación:
1. Atributos
   
![MongoDB Atributos](https://github.com/andresrubiodelrio/ReservasHotel_v4/blob/master/src/main/resources/imagenes/MongoDB_Atibutos.png)
![Valores constantes 3](https://github.com/andresrubiodelrio/ReservasHotel_v4/blob/master/src/main/resources/imagenes/Valores_Constantes_3.png)
![Valores constantes](https://github.com/andresrubiodelrio/ReservasHotel_v4/blob/master/src/main/resources/imagenes/Valores_Constantes.png)
![Valores constantes 2](https://github.com/andresrubiodelrio/ReservasHotel_v4/blob/master/src/main/resources/imagenes/Valores_Constantes_2.png)

En la constante **SERVIDOR** deberás poner la URL que asigne DB Atlas al cluster donde se va a desplegar la base de datos MongoDB.

2. Métodos

![Métodos MongoDB](https://github.com/andresrubiodelrio/ReservasHotel_v4/blob/master/src/main/resources/imagenes/MetodosMongoDB.png)

3. Descripción de los métodos
      1. `getBD`: en caso de que la conexión sea nula, deberá establecer la conexión y devolver la base de datos MongoDB a la que se conecta.
      2. `establececerConexion`: Permite conectarse a una base de datos MongoDB.
      3. `cerrarConexion`: Cierra la conexión con una base de datos MongoDB.
      4. `getDocumento`: Método sobrecargado que en función del objeto recibido como parámetro, devuelve el objeto convertido en un documento de una colección MongoDB concreta.
      5. `getHuesped`: Método que convierte un documento de la colección Huespedes de MongoDB en un objeto de tipo Huesped.
      6. `getHabitacion`: Método que convierte un documento de la colección Habitaciones de MongoDB en un objeto de tipo Habitacion.
      7. `getReserva`: Método que convierte un documento de la colección Reservas de MongoDB en un objeto de tipo Reserva.
4. Realiza el **commit** correspondiente.

#### Paquete negocio
1. Implementa en la clase `Reservas` el método `getReservas` que recibe como parámetro un objeto de tipo habitación y devuelve la lista de reservas de dicha habitación.
2. Añade a las tres interfaces `IHuespedes`, `IHabitaciones` e `IReservas` los métodos `comenzar` y `terminar`. Esto provocará que tengas que implementar dichos métodos en las clases ya existentes que implementan dicha interfaz. Los cuerpos de ambos métodos en las tres clases del paquete memoria estarán vacíos.
3. Crea la interfaz `IFuenteDatos` tal y como aparece en el diagrama de clases. 
4. Realiza el **commit** correspondiente.

#### Paquete mongodb
1. Añade la clase `Huespedes` tal y como aparece en el diagrama de clases teniendo en cuenta que:
   1. El atributo `COLECCION` tiene como valor el nombre de la colección asociada a Huespedes en la base de datos MongoDB. En este caso, **huéspedes**.
   2. El método `comenzar` deberá obtener la colección correspondiente de la base de datos MongoDB.
   3. El método `terminar` deberá cerrar la conexión con la base de datos.
   4. El método `get` deberá devolver una lista formada por todos los huéspedes existentes en la colección correspondiente de MongoDB ordenados por **dni**.
   5. El método `getTamano` deberá devolver el número de documentos existentes en la colección correspondiente de MongoDB.
   6. El método `insertar` deberá insertar un nuevo huésped en la colección correspondiente de MongoDB.
   7. El método `buscar` deberá devolver el resultado de encontrar al huésped pasado como parámetro en la colección correspondiente de MongoDB.
   8. El método `borrar` deberá eliminar al huésped pasado como parámetro de la colección correspondiente de MongoDB.
2. Añade la clase `Habitaciones` tal y como aparece en el diagrama de clases teniendo en cuenta que:
   1. El atributo `COLECCION` tiene como valor el nombre de la colección asociada a Habitaciones en la base de datos MongoDB. En este caso, **habitaciones**.
   2. El método `comenzar` deberá obtener la colección correspondiente de la base de datos MongoDB.
   3. El método `terminar` deberá cerrar la conexión con la base de datos.
   4. El método `get` deberá devolver una lista formada por todos las habitaciones existentes en la colección correspondiente de MongoDB ordenadas por **identificador**.
   5. El método `getTamano` deberá devolver el número de documentos existentes en la colección correspondiente de MongoDB.
   6. El método `insertar` deberá insertar una nueva habitación en la colección correspondiente de MongoDB.
   7. El método `buscar` deberá devolver el resultado de encontrar la habitación pasada como parámetro en la colección correspondiente de MongoDB.
   8. El método `borrar` deberá eliminar la habitación pasada como parámetro de la colección correspondiente de MongoDB.
3. Añade la clase `Reservas` tal y como aparece en el diagrama de clases teniendo en cuenta que:
   1. El atributo `COLECCION` tiene como valor el nombre de la colección asociada a Reservas en la base de datos MongoDB. En este caso, **reservas**.
   2. El método `comenzar` deberá obtener la colección correspondiente de la base de datos MongoDB.
   3. El método `terminar` deberá cerrar la conexión con la base de datos.
   4. El método `get` deberá devolver una lista formada por todos las reservas existentes en la colección correspondiente de MongoDB ordenadas por fecha de inicio de la reserva.
   5. El método `getTamano` deberá devolver el número de documentos existentes en la colección correspondiente de MongoDB.
   6. El método `insertar` deberá insertar una nueva reserva en la colección correspondiente de MongoDB.
   7. El método `buscar` deberá devolver el resultado de encontrar la reserva pasada como parámetro en la colección correspondiente de MongoDB.
   8. El método `borrar` deberá eliminar la reserva pasada como parámetro de la colección correspondiente de MongoDB.
   9. El método `getReservas` de un huésped deberá devolver una lista de todas las reservas del huésped pasado como parámetro.
   10. El método `getReservas` de un tipo de habitación deberá devolver una lista de todas las reservas del tipo de habitación pasado como parámetro.
   11. El método `getReservas` de una habitación deberá devolver una lista de todas las reservas de la habitación pasada como parámetro.
   12. El método `getReservasFuturas` de una habitación que devolverá una lista de las reservas realizadas para la habitación indicada como parámetro y que sean posteriores a la fecha de hoy.
   13. El método `realizarCheckin` que realizará el checkin de la reserva indicada como parámetro en la fecha que establezca el parámetro.
   14. El método `realizarCheckOut` que realizará el checkout de la reserva indicada como parámetro en la fecha que establezca el parámetro.
4. Realiza el **commit** correspondiente.

#### Fuente de datos para el modelo
1. Crea la clase `FuenteDatosMongoDB` que deberá implementar la interaz `IFuenteDatos`, tal y como se indica en el diagrama. Esta clase será la encargada de implementar el **patrón fábrica**, devolviendo en cada caso el resultado de crear la colección a la que hace referencia su nombre:
   1. Método `crearHuespedes` devolverá una nueva colección de tipo Huespedes del paquete mongoDB.
   2. Método `crearHabitaciones` devolverá una nueva colección de tipo Habitaciones del paquete mongoDB. 
   3. Método `crearReservas` devolverá una nueva colección de tipo Reservas del paquete mongoDB.
2. Crea la clase `FuenteDatosMemoria` que deberá implementar la interaz `IFuenteDatos` pero en el paquete memoria. Esta clase será la encargada de implementar el **patrón fábrica**, devolviendo en cada caso el resultado de crear la colección a la que hace referencia su nombre:
   1. Método `crearHuespedes` devolverá una nueva colección de tipo Huespedes del paquete memoria.
   2. Método `crearHabitaciones` devolverá una nueva colección de tipo Habitaciones del paquete memoria.
   3. Método `crearReservas` devolverá una nueva colección de tipo Reservas del paquete memoria.
3. Crea el enumerado `FactoriaFuenteDatos` tal y como se muestra en el diagrama de clases. Este enumerado implementa el **patrón método de fabricación** para las dos fuentes de datos que se van a tener: **MEMORIA** y **MONGODB**.
4. Realiza el **commit** correspondiente.

#### Modelo
1. Añade el atributo `fuenteDatos` tal y como aparece en el diagrama de clases.
2. Modifica el constructor de la clase para que reciba y establezca la fuente de datos de la aplicación y llame al método `comenzar` para que cree las colecciones correspondientes a la fuente de datos a utilizar.
3. Modifica el método `comenzar` para que cree las colecciones en función de la fuente de datos a utilizar.
4. Modifica el método `terminar` para que llame a terminar de cada una de las colecciones.
5. Implementa el método `setter` correspondiente a la fuente de datos.
6. Implementa el método `getReservas` que recibe como parámetro un objeto de tipo habitación, debiendo llamar al método correspondiente de la clase `Reservas`.
7. Crea la interfaz `IModelo` tal y como aparece en el diagrama de clases.
8. Realiza el **commit** correspondiente.

#### Controlador
1. Implementa el método `getReservas` que recibe como parámetro un objeto de tipo habitación y deberá llamar al método correspondiente de la clase `Modelo`.
2. Modifica la clase para que el atributo `modelo` sea de tipo `IModelo`, tal y como refleja el diagrama de clases. 
3. Realiza el **commit** correspondiente.

#### Main
1. Implementa el método `procesarArgumentosFuenteDatos` que creará un modelo cuya fuente de datos será la que se indique a través de los parámetros de la aplicación. Si el parámetro es **-fdmemoria**, se creará un modelo cuya fuente de datos será de tipo **MEMORIA**. En cambio, si el parámetro es **-fdmongodb**, se creará un modelo cuya fuente de datos será de tipo **MONGODB**. 
2. Modifica el método main para que se cree el modelo indicado a través de los parámetros de la aplicación. 
3. Realiza el **commit** corresopndiente.
3. finalmente, realiza el **push** hacia tu repositorio remoto en GitHub.

#### Se valorará:

- La indentación debe ser correcta en cada uno de los apartados.
- El nombre de las variables debe ser adecuado.
- Se debe utilizar la clase `Entrada` para realizar la entrada por teclado.
- El programa debe pasar todas las pruebas que van en el esqueleto del proyecto y toda entrada del programa será validada, para evitar que el programa termine abruptamente debido a una excepción. Además, que ni decir tiene, el programa no debe contener ningún error léxico, sintáctico, de dependencias, etc.
- La corrección ortográfica tanto en los comentarios como en los mensajes que se muestren al usuario.

