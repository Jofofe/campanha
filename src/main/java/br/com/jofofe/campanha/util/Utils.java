package br.com.jofofe.campanha.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static String getDataVigencia(Date dataInicio, Date datFim) {
        return formataData(dataInicio, "dd/MM/yyyy") + " a "
                + formataData(datFim, "dd/MM/yyyy");
    }

    public static final String formataData(Date data, String format){
        if (data == null) {return null;}
        return new SimpleDateFormat(format).format(data);
    }

    public static Date adicionaQuantidadeDiasData(Date date, Integer dias) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, +dias);
        return cal.getTime();
    }

    public static Date retonarDataSemHoras(final Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
