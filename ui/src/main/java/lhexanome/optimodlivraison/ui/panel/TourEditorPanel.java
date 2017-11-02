package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.ui.component.DeliveryCellRenderer;
import lhexanome.optimodlivraison.ui.controller.ControllerInterface;
import lhexanome.optimodlivraison.ui.controller.TourEditorController;
import lhexanome.optimodlivraison.ui.edition.DeliveryListTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Panel used to edit a tour.
 */
public class TourEditorPanel extends AbstractPanel implements DeliveryListTransferHandler.MoveDeliveryListener {
    /**
     * Content pane.
     */
    private JPanel contentPane;

    /**
     * List of the tour deliveries.
     */
    private JList<Delivery> deliveryList;

    /**
     * Button to add a new delivery.
     */
    private JButton addDeliveryButton;

    /**
     * Button to remove the selected delivery.
     */
    private JButton removeDeliveryButton;

    /**
     * Button to change the time slot of the selected delivery.
     */
    private JButton changeTimeSlotButton;

    /**
     * Tour.
     */
    private Tour tour;

    /**
     * Delivery cell renderer.
     */
    private DeliveryCellRenderer cellRenderer;

    /**
     * Road Map. Needed to find addresses.
     */
    private RoadMap roadMap;

    /**
     * Initialize a panel with a panel.
     *
     * @param controller Controller linked to the panel
     */
    public TourEditorPanel(ControllerInterface controller) {
        super(controller);
        setup();
    }

    /**
     * Initialization function.
     * WARNING: This function is not called by the constructor.
     * You have to call it after calling super() !
     * Explications : Intellij GUI designer use an initializer block to initialize components,
     * But the initializer block is called after the super() method.
     */
    @Override
    public void setup() {
        // Do not show , controller will set it visible later
        contentPane.setVisible(false);

        cellRenderer = new DeliveryCellRenderer();
        deliveryList.setCellRenderer(cellRenderer);
        deliveryList.setDragEnabled(true);
        deliveryList.setDropMode(DropMode.INSERT);
        deliveryList.setTransferHandler(new DeliveryListTransferHandler(this));
        deliveryList.setModel(new DefaultListModel<>());

        // TODO Add select listener
        addDeliveryButton.addActionListener(e -> ((TourEditorController) controller).addDelivery());
        removeDeliveryButton.addActionListener(e ->
                ((TourEditorController) controller).removeDelivery(deliveryList.getSelectedValue()));

        changeTimeSlotButton.addActionListener(e ->
                ((TourEditorController) controller).changeTimeSlot(deliveryList.getSelectedValue()));
    }


    /**
     * Road map setter.
     * Needed to find address of a delivery
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
        this.cellRenderer.setRoadMap(roadMap);
    }

    /**
     * Tour setter.
     *
     * @param tour new Tour
     */
    public void setTour(Tour tour) {
        this.tour = tour;

        DefaultListModel<Delivery> listModel = (DefaultListModel<Delivery>) deliveryList.getModel();
        listModel.clear();

        if (tour != null) {
            Vector<Delivery> haltList = tour.getOrderedDeliveryVector();
            Warehouse warehouse = tour.getWarehouse();

            haltList.forEach(listModel::addElement);
        }
    }


    /**
     * Call when a delivery is moved in the list of delivery.
     *
     * @param delivery Delivery moved
     * @param newIndex Destination index
     */
    @Override
    public void onMove(Delivery delivery, int newIndex) {
        ((TourEditorController) controller).moveDelivery(delivery, newIndex);
    }

    /**
     * Return the content panel.
     * The field is created by Intellij and must be declared in the child panel
     *
     * @return Content pane
     */
    @Override
    public JPanel getContentPane() {
        return contentPane;
        //CHECKSTYLE:OFF
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        final JScrollPane scrollPane1 = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        contentPane.add(scrollPane1, gbc);
        deliveryList = new JList();
        scrollPane1.setViewportView(deliveryList);
        changeTimeSlotButton = new JButton();
        changeTimeSlotButton.setText("Changer la plage horaire");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(changeTimeSlotButton, gbc);
        addDeliveryButton = new JButton();
        addDeliveryButton.setText("Ajouter une livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(addDeliveryButton, gbc);
        removeDeliveryButton = new JButton();
        removeDeliveryButton.setText("Supprimer la livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(removeDeliveryButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
