package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.ui.controller.CurrentIntersectionController;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Delivery order panel.
 */
public class CurrentIntersectionPanel extends AbstractPanel {

    /**
     * Content panel.
     */
    private JPanel contentPane;

    /**
     * displayed intersection infos on the screen.
     */
    private JLabel displayedInformations;

    /**
     * Content panel.
     */
    private Intersection displayedIntersection;

    /**
     * Presentation text for displayed informations.
     */
    private static final String PRESENTATION_TEXT = "Rues à proximité de l'intersection sélectionnée : ";

    /**
     * Constructor.
     *
     * @param controller Delivery order controller
     */
    public CurrentIntersectionPanel(CurrentIntersectionController controller) {
        super(controller);
        $$$setupUI$$$();
        setup();
    }

    /**
     * {@link AbstractPanel#setup()}.
     */
    @Override
    public void setup() {
    }

    /**
     * Intersection information display.
     * Called by the controller.
     *
     * @param intersectionToDisplay intersectionToDisplay
     * @param roadMap               roadMap
     */
    public void setData(Intersection intersectionToDisplay, RoadMap roadMap) {
        displayedIntersection = intersectionToDisplay;
        String lineReturn = "<br>";
        String bulletedListHeader = "-";
        String s = "";
        if (displayedIntersection != null) {
            s += "<html>";
            s += lineReturn + PRESENTATION_TEXT + lineReturn;
            Set<String> streetNames = new HashSet<>();

            for (Vector vector : roadMap.getTronconsFromIntersection(intersectionToDisplay)) {
                streetNames.add(vector.getNameStreet());
            }
            for (String streetName : streetNames) {
                s += bulletedListHeader + " " + (streetName.isEmpty() ? "rue sans nom" : streetName) + "," + lineReturn;
            }
            s += "</html>";
        }
        displayedInformations.setText(s);
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

    private void createUIComponents() {
        displayedInformations = new JLabel();
        displayedInformations.setText("");
        displayedInformations.setVisible(true);
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        displayedInformations.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(displayedInformations, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
