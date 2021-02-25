package programacion.entregaut6.demo;
import programacion.entregaut6.modelo.CalendarioEventos;
import programacion.entregaut6.interfaz.IUConsola;
//comandos utilizados para crear el fichero jar: jar -cvf entregaut6.jar programacion
//comandos utilizados para ejecutar programa: java -cp .; z:\Respaldo\bluej\UT-6\ENTRE-CalendarioEventos
/**
 * Punto de entrad a la aplicación
 */
public class AppCalendarioEventos {

    public static void main(String[] args) {
        CalendarioEventos calendario = new CalendarioEventos();
        IUConsola interfaz = new IUConsola(calendario);
        interfaz.iniciar();

    }

}
