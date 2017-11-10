package lhexanome.optimodlivraison.ui.popup;

import lhexanome.optimodlivraison.platform.models.TimeSlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Time slot chooser popup.
 */
public class TimeSlotChooserPopup extends JDialog {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TimeSlotChooserPopup.class.getName());

    /**
     * Content pane.
     */
    private JPanel contentPane;

    /**
     * OK button.
     */
    private JButton buttonOK;

    /**
     * Cancel button.
     */
    private JButton buttonCancel;

    /**
     * No time slot button.
     */
    private JButton buttonNone;

    /**
     * List of start time.
     */
    private JComboBox<String> timeSlotStartComboBox;

    /**
     * List of end time.
     */
    private JComboBox<String> timeSlotEndComboBox;


    /**
     * List of hours.
     */
    private String[] hours;

    /**
     * Chosen time slot.
     */
    private TimeSlot timeSlot;

    /**
     * If the popup has been canceled.
     */
    private boolean canceled;

    /**
     * Constructor.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public TimeSlotChooserPopup() {
        initHours();

        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        timeSlotStartComboBox.setModel(new DefaultComboBoxModel<>(hours));
        timeSlotEndComboBox.setModel(new DefaultComboBoxModel<>(hours));

        timeSlotStartComboBox.setSelectedIndex(10);
        timeSlotEndComboBox.setSelectedIndex(12);

        buttonNone.requestFocusInWindow();

        // Button OK and NONE were swap for convenience (focus)

        buttonOK.addActionListener(e -> onNone());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonNone.addActionListener(e -> onOK());

        pack();
    }

    /**
     * Init the hours field.
     */
    private void initHours() {
        hours = new String[24];

        for (int i = 0; i < hours.length; i++) {
            hours[i] = i + "h";
        }
    }

    /**
     * Called when no time slot is clicked.
     */
    private void onNone() {
        dispose();
    }

    /**
     * Called when a time slot is selected.
     */
    private void onOK() {
        // add your code here
        int start = timeSlotStartComboBox.getSelectedIndex();
        int end = timeSlotEndComboBox.getSelectedIndex();

        if (start == -1 || end == -1 || end <= start) {
            JOptionPane.showMessageDialog(contentPane, "Les données entrées ne sont pas valides !");
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");

            Date startDate = sdf.parse(String.valueOf(start));
            Date endDate = sdf.parse(String.valueOf(end));

            timeSlot = new TimeSlot(startDate, endDate);
            dispose();
        } catch (ParseException e) {
            LOGGER.severe(String.format("Unable to parse hour :%s", e.getLocalizedMessage()));
        }
    }

    /**
     * Called when the user cancel the popup.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
        this.canceled = true;
    }

    /**
     * Return true if the popup was canceled.
     *
     * @return True if the popup was canceled.
     */
    public boolean wasCanceled() {
        return canceled;
    }

    /**
     * Time slot getter.
     *
     * @return Time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        buttonCancel = new JButton();
        buttonCancel.setText("Annuler");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(buttonCancel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer1, gbc);
        buttonOK = new JButton();
        buttonOK.setText("Pas de plage horaire");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(buttonOK, gbc);
        buttonNone = new JButton();
        buttonNone.setText("Valider");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(buttonNone, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Choisissez une plage horaire pour la livraison");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        panel2.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Fin de la plage horaire");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel2.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Début de la plage horaire");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel2.add(label3, gbc);
        timeSlotEndComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel2.add(timeSlotEndComboBox, gbc);
        timeSlotStartComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel2.add(timeSlotStartComboBox, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer3, gbc);
        label2.setLabelFor(timeSlotEndComboBox);
        label3.setLabelFor(timeSlotStartComboBox);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
