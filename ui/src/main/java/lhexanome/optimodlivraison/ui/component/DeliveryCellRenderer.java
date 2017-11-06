package lhexanome.optimodlivraison.ui.component;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Arrays;
import java.util.Map;

/**
 * Delivery celle renderer.
 */
public class DeliveryCellRenderer implements ListCellRenderer<Delivery> {

    /**
     * Road map used to find street names.
     */
    private RoadMap roadMap;

    /**
     * Current context.
     */
    private Context currentContext;

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

    /**
     * Constructor.
     *
     * @param currentContext Current context.
     */
    public DeliveryCellRenderer(Context currentContext) {
        this.currentContext = currentContext;
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
    @SuppressWarnings({"unchecked", "checkstyle:magicnumber"})
    @Override
    public Component getListCellRendererComponent(JList<? extends Delivery> list, Delivery value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        Font font = new JLabel().getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);

        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup();
        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();


        // Index line

        if (hasContext(Context.TOUR)) {

            JLabel indexLabel = new JLabel("N° :");
            indexLabel.setFont(font.deriveFont(attributes));
            JLabel indexValue = new JLabel(String.valueOf(index));

            parallelGroup.addGroup(layout.createSequentialGroup()
                    .addComponent(indexLabel)
                    .addComponent(indexValue)
            );

            sequentialGroup.addGroup(layout.createParallelGroup()
                    .addComponent(indexLabel)
                    .addComponent(indexValue)
            );
        }


        // Time slot line
        TimeSlot timeSlot = value.getSlot();

        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR) && timeSlot != null) {

            JLabel timeSlotLabel = new JLabel("Plage horaire :");
            timeSlotLabel.setFont(font.deriveFont(attributes));

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


        // Duration label

        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR)) {
            JLabel durationLabel = new JLabel("Durée de livraison :");
            durationLabel.setFont(font.deriveFont(attributes));

            JLabel durationValue = new JLabel(String.valueOf(value.getDuration() / 100) + " min");

            parallelGroup.addGroup(layout.createSequentialGroup()
                    .addComponent(durationLabel)
                    .addComponent(durationValue)
            );

            sequentialGroup.addGroup(layout.createParallelGroup()
                    .addComponent(durationLabel)
                    .addComponent(durationValue)
            );
        }


        // Address line

        if (hasContext(Context.DELIVERY_ORDER, Context.TOUR)) {
            JLabel addressLabel = new JLabel("Adresses à proximitées :");

            addressLabel.setFont(font.deriveFont(attributes));

            GroupLayout.SequentialGroup addressGroupSeq = layout.createSequentialGroup();
            GroupLayout.ParallelGroup addressGroupParallel = layout.createParallelGroup();

            for (Vector street : roadMap.getTronconsFromIntersection(value.getIntersection())) {
                String streetName = street.getNameStreet();
                if (streetName == null) streetName = "Rue sans nom";

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
     * Road map setter.
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }
}
