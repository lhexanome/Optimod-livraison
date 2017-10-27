package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.ui.controller.TourController;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing all the components needed for a tour.
 */
public class TourPanel extends AbstractPanel {

    /**
     * Content panel.
     */
    private JPanel contentPane;

    /**
     * Button to compute a tour.
     */
    private JButton computeTourButton;

    /**
     * Constructor.
     *
     * @param controller Tour controller
     */
    public TourPanel(TourController controller) {
        super(controller);
        setup();
    }

    /**
     * {@link AbstractPanel#setup()}.
     */
    @Override
    public void setup() {
        computeTourButton.addActionListener(e -> ((TourController) controller).newComputation());
    }

    /**
     * {@link AbstractPanel#getContentPane()}.
     */
    @Override
    public JPanel getContentPane() {
        return contentPane;
        // Disable Checkstyle for generated code
        //CHECKSTYLE:OFF
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
        computeTourButton = new JButton();
        computeTourButton.setText("Calculer Tournée");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(computeTourButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    // Disable Checkstyle for generated code
    //CHECKSTYLE:OFF

}
