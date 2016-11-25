
package organizadorfotografias;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


public class Fotografia {
    private File archivo;
    private GregorianCalendar fecha;

    public Fotografia(File archivo, GregorianCalendar fecha) {
        this.archivo = archivo;
        this.fecha = fecha;
    }

    public File getArchivo() {
        return archivo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }
    public static GregorianCalendar obtenerCalendario(String fecha) throws ParseException{
        //Formato 2016:07:12 18:26:00
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        GregorianCalendar nuevoCalendario = new GregorianCalendar();
        nuevoCalendario.setTime(formatoFecha.parse(fecha));
        return nuevoCalendario;
    }
    public static String obtenerMes(int numDeMes){
        switch (numDeMes) {
            case 0:
                return (numDeMes+1)+". Enero";
            case 1:
                return (numDeMes+1)+". Febrero";
            case 2:
                return (numDeMes+1)+". Marzo";
            case 3:
                return (numDeMes+1)+". Abril";
            case 4:
                return (numDeMes+1)+". Mayo";
            case 5:
                return (numDeMes+1)+". Junio";
            case 6:
                return (numDeMes+1)+". Julio";
            case 7:
                return (numDeMes+1)+". Agosto";
            case 8:
                return (numDeMes+1)+". Septiembre";
            case 9:
                return (numDeMes+1)+". Octubre";
            case 10:
                return (numDeMes+1)+". Noviembre";
            case 11:
                return (numDeMes+1)+". Diciembre";
            default:
                return (numDeMes+1)+". Desconocido";
        }
    }
}
