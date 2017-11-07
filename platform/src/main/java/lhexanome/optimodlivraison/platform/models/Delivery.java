package lhexanome.optimodlivraison.platform.models;


/**
 * Contains a duration for the delivery, an intersection and a time slot.
 * If the time slot is null, then the delivery can be at any time.
 */
public class Delivery extends Halt {

    /**
     * Duration of the delivery
     * In seconds.
     */
    private int duration;


    /**
     * Time slot during which the delivery can be done.
     */
    private TimeSlot slot;


    /**
     * Delivery constructor.
     *
     * @param intersection Intersection of the delivery
     * @param duration     Delivery duration in seconds
     * @see #duration
     * @see #intersection
     * @see #slot
     * @see #Delivery(Intersection, int, TimeSlot)
     */
    public Delivery(Intersection intersection, int duration) {
        this(intersection, duration, null);
    }

    /**
     * Constructor.
     *
     * @param intersection Intersection of the delivery
     * @param duration     Delivery duration
     * @param slot         Time slot during which the delivery can be done
     * @see #duration
     * @see #intersection
     * @see #slot
     * @see #Delivery(Intersection, int)
     */
    public Delivery(Intersection intersection, int duration, TimeSlot slot) {
        super(intersection);
        this.duration = duration;
        this.slot = slot;
    }

    /**
     * Duration getter.
     *
     * @return Duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Duration setter.
     *
     * @param duration Delivery duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Time slot getter.
     *
     * @return Time slot
     */
    public TimeSlot getSlot() {
        return slot;
    }

    /**
     * Time Slot setter.
     *
     * @param slot Time slot
     */
    public void setSlot(TimeSlot slot) {
        this.slot = slot;
    }


}
