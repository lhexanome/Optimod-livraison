package lhexanome.optimodlivraison.platform.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe pour avoir une date facilement.
 */
public final class DateUtil {

    /**
     * Private constructor.
     */
    private DateUtil() {
    }

    /**
     * static function giving a Date from hour and minute.
     * @param hours hours given
     * @param minutes minutes given
     * @return Date wanted
     */
    public static Date getDate(int hours, int minutes) {
        Date sortie = new Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            return simpleDateFormat.parse(hours + ":" + minutes);
        } catch (ParseException e) {
            return null;
        }
    }
}
