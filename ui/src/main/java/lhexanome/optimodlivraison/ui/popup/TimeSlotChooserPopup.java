package lhexanome.optimodlivraison.ui.popup;

import lhexanome.optimodlivraison.platform.models.TimeSlot;

import javax.swing.*;
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
    public TimeSlotChooserPopup() {
        initHours();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        timeSlotStartComboBox.setModel(new DefaultComboBoxModel<>(hours));
        timeSlotEndComboBox.setModel(new DefaultComboBoxModel<>(hours));

        buttonOK.addActionListener(e -> onOK());

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
        buttonNone.addActionListener(e -> onNone());
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
    }
}
