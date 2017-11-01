package lhexanome.optimodlivraison.ui.component;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
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

        // Index line

        JLabel indexLabel = new JLabel("N° :");

        Font font = indexLabel.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);
        indexLabel.setFont(font.deriveFont(attributes));

        JLabel indexValue = new JLabel(String.valueOf(index));


        // Time slot line

        TimeSlot timeSlot = value.getSlot();
        GroupLayout.SequentialGroup timeSlotGroupSeq = layout.createSequentialGroup();
        GroupLayout.ParallelGroup timeSlotGroupParallel = layout.createParallelGroup();

        if (timeSlot != null) {
            JLabel timeSlotLabel = new JLabel("Plage horaire :");
            timeSlotLabel.setFont(font.deriveFont(attributes));

            JLabel timeSlotValue = new JLabel(value.getSlot().toString());

            timeSlotGroupSeq
                    .addComponent(timeSlotLabel)
                    .addComponent(timeSlotValue);
            timeSlotGroupParallel
                    .addComponent(timeSlotLabel)
                    .addComponent(timeSlotValue);
        }


        // Address line

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

        // Duration label

        JLabel durationLabel = new JLabel("Durée de livraison :");
        durationLabel.setFont(font.deriveFont(attributes));

        JLabel durationValue = new JLabel(String.valueOf(value.getDuration() / 100) + " min");


        // Placement

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(indexLabel)
                        .addComponent(indexValue)
                )
                .addGroup(timeSlotGroupSeq)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(durationLabel)
                        .addComponent(durationValue)
                )
                .addComponent(addressLabel)
                .addGroup(addressGroupParallel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(indexLabel)
                        .addComponent(indexValue)
                )
                .addGroup(timeSlotGroupParallel)
                .addGroup(layout.createParallelGroup()
                        .addComponent(durationLabel)
                        .addComponent(durationValue)
                )
                .addComponent(addressLabel)
                .addGroup(addressGroupSeq)
        );

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
     * Road map setter.
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }
}
