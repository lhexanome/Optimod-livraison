package lhexanome.optimodlivraison.platform.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represent a time slot.
 */
public class TimeSlot {

    /**
     * Starting time of the slot.
     */
    private Date start;

    /**
     * Ending time of the slot.
     */
    private Date end;

    /**
     * Default constructor.
     * Take two date in parameters.
     * Will inverse the date if needed.
     *
     * @param start Start time
     * @param end   End time
     */
    public TimeSlot(Date start, Date end) {
        if (start.before(end)) {
            this.start = start;
            this.end = end;
        } else {
            this.end = start;
            this.start = end;
        }
    }

    /**
     * Start getter.
     *
     * @return Start time
     */
    public Date getStart() {
        return start;
    }

    /**
     * Start setter.
     * Must be before the current end. Otherwise it will do nothing
     *
     * @param start Start time
     */
    public void setStart(Date start) {
        if (start.before(end)) {
            this.start = start;
        }
    }

    /**
     * End getter.
     *
     * @return End time
     */
    public Date getEnd() {
        return end;
    }

    /**
     * End setter.
     * Must be after the current start. Otherwise it will do nothing
     *
     * @param end End time
     */
    public void setEnd(Date end) {
        if (end.after(start)) {
            this.end = end;
        }
    }

    /**
     * Check if a date is included in the time slot.
     *
     * @param date Date to check
     * @return True if the date is included
     */
    public boolean isIncluded(Date date) {
        return date.equals(start)
                || date.equals(end)
                || (date.after(start) && date.before(end));
    }


    /**
     * To string method.
     *
     * @return String
     */
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm");

        return "De " + simpleDateFormat.format(start) + " à " + simpleDateFormat.format(end);
    }

    /**
     * get the time in millisecond between two dates.
     *
     * @param d1 first date
     * @param d2 second date
     * @return time returned
     */
    public static long getTimescaleBetween(Date d1, Date d2) {
        return d2.getTime() - d1.getTime();
    }

    /**
     * get the time in millisecond between a date and a time.
     *
     * @param t1 first time
     * @param d2 second date
     * @return time returned
     */
    public static long getTimescaleBetween(long t1, Date d2) {
        return d2.getTime() - t1;
    }
}
