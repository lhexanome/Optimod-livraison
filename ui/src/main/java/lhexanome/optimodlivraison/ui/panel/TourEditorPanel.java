package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
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
     * Button to undo last action.
     */
    private JButton undoButton;

    /**
     * Button to redo last action.
     */
    private JButton redoButton;

    /**
     * Label displaying the start date.
     */
    private JLabel tourStartLabel;

    /**
     * Label displaying the end date and the total time for a tour.
     */
    private JLabel tourEndLabel;

    /**
     * Button to reload a delivery order.
     */
    private JButton loadDeliveryOrderButton;

    /**
     * Delivery cell renderer.
     */
    private DeliveryCellRenderer cellRenderer;


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

        cellRenderer = new DeliveryCellRenderer(DeliveryCellRenderer.Context.TOUR);
        deliveryList.setCellRenderer(cellRenderer);
        deliveryList.setDragEnabled(true);
        deliveryList.setDropMode(DropMode.INSERT);
        deliveryList.setTransferHandler(new DeliveryListTransferHandler(this));
        deliveryList.setModel(new DefaultListModel<>());
        deliveryList.addListSelectionListener(e ->
                ((TourEditorController) controller).selectDeliveryFromList(deliveryList.getSelectedValue()));

        addDeliveryButton.addActionListener(e -> ((TourEditorController) controller).addDelivery());
        removeDeliveryButton.addActionListener(e ->
                ((TourEditorController) controller).removeDelivery(deliveryList.getSelectedValue()));

        changeTimeSlotButton.addActionListener(e ->
                ((TourEditorController) controller).changeTimeSlot(deliveryList.getSelectedValue()));

        undoButton.addActionListener(e ->
                ((TourEditorController) controller).undo());

        redoButton.addActionListener(e ->
                ((TourEditorController) controller).redo());

        loadDeliveryOrderButton.addActionListener(e ->
                ((TourEditorController) controller).reloadDeliveryOrder());
    }

    /**
     * Road map setter.
     * Needed to find address of a delivery
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        this.cellRenderer.setRoadMap(roadMap);
    }

    /**
     * Tour setter.
     *
     * @param tour new Tour
     */
    public void setTour(Tour tour) {
        DefaultListModel<Delivery> listModel = (DefaultListModel<Delivery>) deliveryList.getModel();

        if (tour == null) {
            listModel.clear();
            tourStartLabel.setText("");
            tourEndLabel.setText("");
            return;
        }

        tour.addObserver((o, arg) -> {
            if (o instanceof Tour) {
                updateTourList(tour, listModel);
            }
        });

        updateTourList(tour, listModel);
    }

    /**
     * Just update the display with new data.
     * <p>
     * Call when Tour is updated
     *
     * @param tour      Tour to display
     * @param listModel List model of the JList
     */
    private void updateTourList(Tour tour, DefaultListModel<Delivery> listModel) {
        listModel.clear();

        Vector<Delivery> haltList = tour.getOrderedDeliveryVector();
        Warehouse warehouse = tour.getWarehouse();

        tourStartLabel.setText("Début : " + DateUtil.formatDate("HH:mm", tour.getStart()));
        tourEndLabel.setText(String.format("Fin : %s (%02dh%02d)",
                DateUtil.formatDate("HH:mm", warehouse.getEstimateDate()),
                tour.getTime() / 60 / 60,
                tour.getTime() / 60 % 60
        ));

        haltList.forEach(listModel::addElement);
    }

    /**
     * selects an element in the list.
     *
     * @param selectValue the selected delivery.
     */
    public void selectDeliveryFromMap(Delivery selectValue) {
        if (selectValue == null) {
            deliveryList.clearSelection();
        } else {
            deliveryList.setSelectedValue(selectValue, true);
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
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        contentPane.add(scrollPane1, gbc);
        deliveryList = new JList();
        scrollPane1.setViewportView(deliveryList);
        removeDeliveryButton = new JButton();
        removeDeliveryButton.setText("Supprimer la livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 5);
        contentPane.add(removeDeliveryButton, gbc);
        redoButton = new JButton();
        redoButton.setText("Rétablir");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(redoButton, gbc);
        undoButton = new JButton();
        undoButton.setText("Annuler");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(undoButton, gbc);
        changeTimeSlotButton = new JButton();
        changeTimeSlotButton.setText("Changer la plage horaire");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 5);
        contentPane.add(changeTimeSlotButton, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(2);
        label1.setText("Resultat du calcul");
        label1.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(label1, gbc);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.PLAIN, 12, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setHorizontalAlignment(2);
        label2.setHorizontalTextPosition(2);
        label2.setText("Vous pouvez utiliser le drag and drop pour changer l'ordre");
        label2.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        contentPane.add(label2, gbc);
        addDeliveryButton = new JButton();
        addDeliveryButton.setText("Ajouter une livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 10);
        contentPane.add(addDeliveryButton, gbc);
        tourStartLabel = new JLabel();
        tourStartLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        contentPane.add(tourStartLabel, gbc);
        tourEndLabel = new JLabel();
        tourEndLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(tourEndLabel, gbc);
        loadDeliveryOrderButton = new JButton();
        loadDeliveryOrderButton.setHideActionText(false);
        loadDeliveryOrderButton.setHorizontalTextPosition(0);
        loadDeliveryOrderButton.setText("Recharger demande de livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 10);
        contentPane.add(loadDeliveryOrderButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
