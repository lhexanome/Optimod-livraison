package lhexanome.optimodlivraison.ui.component;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

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
    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList<? extends Delivery> list, Delivery value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Index line

        JLabel indexLabel = new JLabel("N° :");

        Font font = indexLabel.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);
        indexLabel.setFont(font.deriveFont(attributes));

        gbc.weightx = 0.2;

        panel.add(indexLabel, gbc);

        JLabel indexValue = new JLabel(String.valueOf(index));
        gbc.weightx = 0.8;
        gbc.gridx = 1;

        panel.add(indexValue, gbc);
        gbc.gridy++;


        // Time slot line

        TimeSlot timeSlot = value.getSlot();

        if (timeSlot != null) {
            JLabel timeSlotLabel = new JLabel("Plage horaire :");
            timeSlotLabel.setFont(font.deriveFont(attributes));

            gbc.weightx = 0.2;
            gbc.gridx = 0;

            panel.add(timeSlotLabel, gbc);

            JLabel timeSlotValue = new JLabel(value.getSlot().toString());

            gbc.weightx = 0.8;
            gbc.gridx = 1;

            panel.add(timeSlotValue, gbc);

            gbc.gridy++;
        }


        // Address line

        JLabel addressLabel = new JLabel("Adresses à proximitées :");

        addressLabel.setFont(font.deriveFont(attributes));

//        gbc.weightx = 0.2;
        gbc.gridx = 0;

        panel.add(addressLabel, gbc);
        gbc.gridy++;
        gbc.weightx = 0.8;
        gbc.gridx = 1;

        for (Vector street : roadMap.getTronconsFromIntersection(value.getIntersection())) {
            String streetName = street.getNameStreet();
            if (streetName == null) streetName = "Rue sans nom";

            JLabel line = new JLabel(streetName);

            panel.add(line, gbc);
            gbc.gridy++;
        }

        // Duration label

        JLabel durationLabel = new JLabel("Durée de livraison :");
        durationLabel.setFont(font.deriveFont(attributes));
        gbc.gridx = 0;
        gbc.weightx = 0.2;

        panel.add(durationLabel, gbc);

        JLabel durationValue = new JLabel(String.valueOf(value.getDuration() / 100) + "min");

        gbc.weightx = 0.8;
        gbc.gridx = 1;

        panel.add(durationValue, gbc);

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

    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }
}
