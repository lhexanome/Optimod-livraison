package lhexanome.optimodlivraison.ui.component;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Delivery celle renderer.
 */
public class DeliveryCellRenderer implements ListCellRenderer<Delivery> {

    /**
     * Font used for title.
     * Display bold text.
     */
    private final Font font;
    /**
     * Road map used to find street names.
     */
    private RoadMap roadMap;

    /**
     * Current context.
     */
    private Context currentContext;

    /**
     * used for scaling a displayed integer.
     */
    private static final long SCALE_DELIVERY = 100;

    /**
     * used for scaling a displayed integer.
     */
    private static final long MILLISECONDS_TO_SECONDS = 60000;

    /**
     * waiting time text color.
     */
    private static final Color WAITING_TIME_COLOR = Color.ORANGE;

    /**
     * warning text color.
     */
    private static final Color WARNING_COLOR = Color.RED;

    /**
     * Constructor.
     *
     * @param currentContext Current context.
     */
    public DeliveryCellRenderer(Context currentContext) {
        this.currentContext = currentContext;
        this.font = new JLabel().getFont();
    }

    /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @SuppressWarnings({"checkstyle:magicnumber"})
    @Override
    public Component getListCellRendererComponent(JList<? extends Delivery> list, Delivery value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);


        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup();
        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();

        // Index line
        if (hasContext(Context.TOUR)) {
            setIndexLine(index, parallelGroup, layout, sequentialGroup, value);
        }

        // Time slot line
        TimeSlot timeSlot = value.getSlot();
        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR) && timeSlot != null) {

            setTimeSlotLine(parallelGroup, layout, sequentialGroup, value);
        }

        // Duration label
        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR)) {
            setDurationLabel(parallelGroup, layout, sequentialGroup, value);
        }

        // Waiting time label
        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR) && value.getSlot() != null
                && value.getEstimateDate() != null) {

            setWaitingTime(value, parallelGroup, layout, sequentialGroup);
        }

        // TimeSlot warning label
        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR) && value.getSlot() != null
                && value.getEstimateDate() != null) {

            setTimeSlotWarning(value, parallelGroup, layout, sequentialGroup);
        }

        // Address line
        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR)) {
            setAddressLine(layout, value, parallelGroup, sequentialGroup);
        }

        layout.setVerticalGroup(sequentialGroup);
        layout.setHorizontalGroup(parallelGroup);

        // Separator

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        // Selection

        if (isSelected) {
            panel.setBackground(new Color(0, 0, 100, 70));
        } else if (cellHasFocus) {
            panel.setBackground(new Color(0, 0, 0, 40));
        } else {
            panel.setOpaque(false);
        }

        return panel;
    }

    /**
     * Renders a warning if a timslot constraint is not respected during the tour.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     */
    private void setTimeSlotWarning(Delivery value, GroupLayout.ParallelGroup parallelGroup, GroupLayout layout,
                                    GroupLayout.SequentialGroup sequentialGroup) {

        JLabel timeSlotLabel = new JLabel("Plage horaire non respectée.");
        timeSlotLabel.setFont(getDerivedFont());
        timeSlotLabel.setForeground(WARNING_COLOR);

        double lateTime = getLateTime(value);
        long minutes = getMinutes(lateTime);
        long seconds = getSeconds(lateTime);

        if (lateTime > 0) {
            JLabel timeSlotValue = new JLabel(
                    "Retard de " + String.valueOf(minutes) + " min " + String.valueOf(seconds) + "s"
            );

            parallelGroup.addGroup(layout.createSequentialGroup()
                    .addComponent(timeSlotLabel)
                    .addComponent(timeSlotValue)
            );

            sequentialGroup.addGroup(layout.createParallelGroup()
                    .addComponent(timeSlotLabel)
                    .addComponent(timeSlotValue)
            );
        }
    }

    /**
     * Renders the time slot line of a cell.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     */
    private void setTimeSlotLine(GroupLayout.Group parallelGroup, GroupLayout layout,
                                 GroupLayout.Group sequentialGroup, Delivery value) {
        JLabel timeSlotLabel = new JLabel("Plage horaire :");
        timeSlotLabel.setFont(getDerivedFont());

        JLabel timeSlotValue = new JLabel(value.getSlot().toString());

        parallelGroup.addGroup(layout.createSequentialGroup()
                .addComponent(timeSlotLabel)
                .addComponent(timeSlotValue)
        );

        sequentialGroup.addGroup(layout.createParallelGroup()
                .addComponent(timeSlotLabel)
                .addComponent(timeSlotValue)
        );
    }

    /**
     * Renders the address line of a delivery cell.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     */
    private void setAddressLine(GroupLayout layout, Halt value, GroupLayout.Group parallelGroup,
                                GroupLayout.Group sequentialGroup) {
        JLabel addressLabel = new JLabel("Adresses à proximitées :");

        addressLabel.setFont(getDerivedFont());

        GroupLayout.SequentialGroup addressGroupSeq = layout.createSequentialGroup();
        GroupLayout.ParallelGroup addressGroupParallel = layout.createParallelGroup();

        Set<String> streetNames = new HashSet<>();    for (Vector vector : roadMap.getVectorsFromIntersection(value.getIntersection())) {
                streetNames.add(vector.getStreetName());
            }
            for (String streetName : streetNames) {
                if (streetName .isEmpty()) streetName = "Rue sans nom";

            JLabel line = new JLabel("- " + streetName);

            addressGroupParallel.addComponent(line);
            addressGroupSeq.addComponent(line);
        }

        parallelGroup
                .addComponent(addressLabel)
                .addGroup(addressGroupParallel);

        sequentialGroup
                .addComponent(addressLabel)
                .addGroup(addressGroupSeq);
    }

    /**
     * Renders the delivery index on the cell.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     * @param index           index
     */
    private void setIndexLine(int index, GroupLayout.Group parallelGroup, GroupLayout layout,
                              GroupLayout.Group sequentialGroup, Halt value) {
        JLabel indexLabel = new JLabel("N° :");
        indexLabel.setFont(getDerivedFont());
        JLabel indexValue = new JLabel(String.valueOf(index));

        JLabel estimateDateValue = new JLabel(DateUtil.formatDate(" (HH:mm)", value.getEstimateDate()));

        parallelGroup.addGroup(layout.createSequentialGroup()
                .addComponent(indexLabel)
                .addComponent(indexValue)
                .addComponent(estimateDateValue)
        );

        sequentialGroup.addGroup(layout.createParallelGroup()
                .addComponent(indexLabel)
                .addComponent(indexValue)
                .addComponent(estimateDateValue)
        );
    }

    /**
     * Renders the duration label on the cell.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     */
    private void setDurationLabel(GroupLayout.Group parallelGroup, GroupLayout layout,
                                  GroupLayout.Group sequentialGroup, Delivery value) {
        JLabel durationLabel = new JLabel("Durée de livraison :");
        durationLabel.setFont(getDerivedFont());

        JLabel durationValue = new JLabel(String.valueOf(value.getDuration() / SCALE_DELIVERY) + " min");

        parallelGroup.addGroup(layout.createSequentialGroup()
                .addComponent(durationLabel)
                .addComponent(durationValue)
        );

        sequentialGroup.addGroup(layout.createParallelGroup()
                .addComponent(durationLabel)
                .addComponent(durationValue)
        );
    }

    /**
     * Renders the waiting time on the cell.
     *
     * @param value           value
     * @param parallelGroup   parallelGroup
     * @param layout          layout
     * @param sequentialGroup sequentialGroup
     */
    private void setWaitingTime(Delivery value, GroupLayout.Group parallelGroup, GroupLayout layout,
                                GroupLayout.Group sequentialGroup) {

        double waitingTime = getWaitingTime(value);

        long minutes = getMinutes(waitingTime);
        long seconds = getSeconds(waitingTime);

        if (waitingTime > 0) {
            JLabel waitingTimeLabel = new JLabel("Temps d'attente :");
            waitingTimeLabel.setFont(getDerivedFont());
            waitingTimeLabel.setForeground(WAITING_TIME_COLOR);

            JLabel waitingTimeValue = new JLabel(String.valueOf(minutes) + " min " + String.valueOf(seconds) + " s");
            waitingTimeValue.setForeground(WAITING_TIME_COLOR);
            parallelGroup.addGroup(layout.createSequentialGroup()
                    .addComponent(waitingTimeLabel)
                    .addComponent(waitingTimeValue)
            );

            sequentialGroup.addGroup(layout.createParallelGroup()
                    .addComponent(waitingTimeLabel)
                    .addComponent(waitingTimeValue)
            );
        }
    }

    /**
     * returns the 2d part of the waiting time in seconds <= 60 seconds.
     *
     * @param waitingTime waiting time
     * @return number of waiting time seconds.
     */
    private long getSeconds(double waitingTime) {
        return (long) Math.floor((waitingTime - getMinutes(waitingTime)) * 60);
    }

    /**
     * returns number of waiting time minutes.
     *
     * @param waitingTime waiting time
     * @return number of waiting time minutes.
     */
    private long getMinutes(double waitingTime) {
        return (long) Math.floor(waitingTime);
    }

    /**
     * returns the waiting time of a delivery during the tour.
     *
     * @param delivery the delivery
     * @return waiting time in seconds
     */
    private double getWaitingTime(Delivery delivery) {
        long estimatedTime = delivery.getEstimateDate().getTime();
        long wantedTime = delivery.getSlot().getStart().getTime();

        return (wantedTime - estimatedTime) / (double) MILLISECONDS_TO_SECONDS;
    }

    /**
     * returns the late time of a delivery during the tour.
     *
     * @param delivery the delivery
     * @return waiting time in seconds
     */
    private double getLateTime(Delivery delivery) {
        long estimatedTime = delivery.getEstimateDate().getTime();
        long maxTime = delivery.getSlot().getEnd().getTime();

        return (estimatedTime - maxTime) / (double) MILLISECONDS_TO_SECONDS;
    }

    /**
     * Check if the cell has one of the provided contexts.
     * Util method
     *
     * @param contexts Contexts to check
     * @return True if the renderer has this context
     */
    public boolean hasContext(Context... contexts) {
        return Arrays.stream(contexts).anyMatch(context -> context == currentContext);
    }

    /**
     * Return a new derived font.
     *
     * @return Bold font
     */
    @SuppressWarnings("unchecked")
    private Font getDerivedFont() {
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);

        return font.deriveFont(attributes);
    }

    /**
     * Road map setter.
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }

    /**
     * Context of the renderer.
     * Needed to adapt the display
     */
    public enum Context {
        /**
         * Display only delivery order fields.
         */
        DELIVERY_ORDER,

        /**
         * Display only tour fields.
         */
        TOUR
    }
}
