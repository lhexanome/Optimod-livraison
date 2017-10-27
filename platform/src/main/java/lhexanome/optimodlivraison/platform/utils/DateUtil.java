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
     *
     * @param hours   hours given
     * @param minutes minutes given
     * @return Date wanted
     */
    public static Date getDate(int hours, int minutes) {
        DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            return simpleDateFormat.parse(hours + ":" + minutes);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Parse a string with a certain format.
     *
     * @param format Format of the string, {@see SimpleDateFormat}
     * @param value  String to parse
     * @return A date object
     * @throws ParseException if unable to parse
     */
    public static Date parseDate(String format, String value) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.parse(value);
    }
}
