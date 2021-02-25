import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Set;
/**
 * Esta clase modela un sencillo calendario de eventos.
 * 
 * Por simplicidad consideraremos que los eventos no se solapan
 * y no se repiten
 * 
 * El calendario guarda en un map los eventos de una serie de meses
 * Cada mes (la clave en el map, un enumerado Mes) tiene asociados 
 * en una colección ArrayList los eventos de ese mes
 * 
 * Solo aparecen los meses que incluyen algún evento
 * 
 * Las claves se recuperan en orden alfabético
 * 
 * Author David Sena
 */
public class CalendarioEventos {
    private Mes mes;
    private TreeMap<Mes, ArrayList<Evento>> calendario;

    /**
     * el constructor
     */
    public CalendarioEventos() {
        this.calendario = new TreeMap<>();
    }

    /**
     * añade un nuevo evento al calendario
     * Si la clave (el mes del nuevo evento) no existe en el calendario
     * se creará una nueva entrada con dicha clave y la colección formada
     * por ese único evento
     * Si la clave (el mes) ya existe se añade el nuevo evento insertándolo de forma
     * que quede ordenado por fecha y hora de inicio
     * 
     * Pista! Observa que en la clase Evento hay un método antesDe() que vendrá
     * muy bien usar aquí
     */
    public void addEvento(Evento nuevo) {
        Mes mes = nuevo.getMes();
        if(calendario.containsKey(nuevo.getMes())){
            calendario.put(mes, new ArrayList<Evento>());
            calendario.get(mes).add(nuevo);
        }
        else{
            ArrayList<Evento> eventos = new ArrayList<>();
            calendario.put(mes, eventos);
        }      
         
    }

    /**
     * Representación textual del calendario
     * Hacer de forma eficiente 
     * Usar el conjunto de entradas  
     */
    public String toString() {
        Set<Mes> rep = calendario.keySet();
        StringBuilder sb = new StringBuilder();
        for(Mes mes: rep){
            sb.append(mes + "\n");
            ArrayList<Evento> esc = calendario.get(mes);
            for(int i = 0; i < esc.size(); i++){
                sb.append(esc.get(i).toString());
            }
        }
        return sb.toString();       
    }

    /**
     * Dado un mes devolver la cantidad de eventos que hay en ese mes
     * Si el mes no existe se devuelve 0
     */
    public int totalEventosEnMes(Mes mes) {
        if (calendario.containsKey(mes)){
             return calendario.size();
            }
        return 0;
    }

    /**
     * Devuelve un conjunto (importa el orden) 
     * con los meses que tienen mayor nº de eventos
     * Hacer un solo recorrido del map con el conjunto de claves
     *  
     */
    public TreeSet<Mes> mesesConMasEventos() {
        Set<Mes> masEventos = calendario.keySet();
        TreeSet<Mes> conjunto = new TreeSet<>();
        int numEvento = 0;
        for(Mes mes : masEventos){
            if(numEvento < calendario.get(mes).size()){
                numEvento = calendario.get(mes).size();
                conjunto = new TreeSet<>();
                conjunto.add(mes);
            }
        }
        return conjunto;
    }
    
    /**
     * Devuelve el nombre del evento de mayor duración en todo el calendario
     * Se devuelve uno solo (el primero encontrado) aunque haya varios
     */
    public String eventoMasLargo() {
        int eventoMayor = 0;
        String nombre = "";
        Set<Mes> event = calendario.keySet();
        for(Mes largo: event){
            ArrayList<Evento> mayor = calendario.get(event);
            for(int i = 0; i < mayor.size(); i++){
                if(mayor.get(i).getDuracion() > eventoMayor){
                    nombre = mayor.get(i).getNombre();                
                }
            }           
        }
        return nombre;
    }

    /**
     * Borrar del calendario todos los eventos de los meses indicados en el array
     * y que tengan lugar el día de la semana proporcionado (se entiende día de la
     * semana como 1 - Lunes, 2 - Martes ..  6 - Sábado, 7 - Domingo)
     * 
     * Si alguno de los meses del array no existe el el calendario no se hace nada
     * Si al borrar de un mes los eventos el mes queda con 0 eventos se borra la entrada
     * completa del map
     */
    public int cancelarEventos(Mes[] meses, int dia) {  
        int cancelados = 0;
        for(int i = 0; i < meses.length; i++){
            if(calendario.containsKey(meses[i])){
                ArrayList<Evento> eventosDeMes = calendario.get(meses[i]);
                int aux = eventosDeMes.size();
                for(int j = 0; j<eventosDeMes.size(); j++ ){
                    if (eventosDeMes.get(j).getDia() == dia){
                        calendario.get(meses[i]).remove(j);
                    }
                }  
                cancelados += (aux - eventosDeMes.size());
                if(calendario.get(meses[i]).size() == 0){
                    calendario.remove(meses[i]);
                }
                else{
                    calendario.put(meses[i], eventosDeMes);                    
                }
            }
        }
        return cancelados;
    }

    /**
     * Código para testear la clase CalendarioEventos
     */
    public static void main(String[] args) {
        CalendarioEventos calendario = new CalendarioEventos();
        CalendarioIO.cargarEventos(calendario);
        System.out.println(calendario);

        System.out.println();

        Mes mes = Mes.FEBRERO;
        System.out.println("Eventos en " + mes + " = "
                            + calendario.totalEventosEnMes(mes));
        mes = Mes.MARZO;
        System.out.println("Eventos en " + mes + " = "
                            + calendario.totalEventosEnMes(mes));
        System.out.println("Mes/es con más eventos "
                            + calendario.mesesConMasEventos());

        System.out.println();
        System.out.println("Evento de mayor duración: "
                            + calendario.eventoMasLargo());

        System.out.println();
        Mes[] meses = {Mes.FEBRERO, Mes.MARZO, Mes.MAYO, Mes.JUNIO};
        int dia = 6;
        System.out.println("Cancelar eventos de " + Arrays.toString(meses));
        int cancelados = calendario.cancelarEventos(meses, dia);
        System.out.println("Se han cancelado " + cancelados +
                            " eventos");
        System.out.println();
        System.out.println("Después de cancelar eventos ...");
        System.out.println(calendario);
    }

}
