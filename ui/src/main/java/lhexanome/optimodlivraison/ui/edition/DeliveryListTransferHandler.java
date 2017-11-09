package lhexanome.optimodlivraison.ui.edition;

import lhexanome.optimodlivraison.platform.models.Delivery;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Delivery list transfer handler.
 * Drag and drop support to reorder the list.
 */
public class DeliveryListTransferHandler extends TransferHandler {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeliveryListTransferHandler.class.getName());

    /**
     * Data flavor.
     * Type of object to use
     */
    private final DataFlavor localObjectFlavor;

    /**
     * On move listener.
     */
    private MoveDeliveryListener listener;

    /**
     * Constructor.
     */
    public DeliveryListTransferHandler() {
        localObjectFlavor = new ActivationDataFlavor(
                Delivery.class, DataFlavor.javaJVMLocalObjectMimeType, "Delivery");
    }

    /**
     * Constructor.
     *
     * @param listener Listener
     */
    public DeliveryListTransferHandler(MoveDeliveryListener listener) {
        this();
        this.listener = listener;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        @SuppressWarnings("unchecked")
        JList<Delivery> list = (JList<Delivery>) c;
        Delivery transferredObjects = list.getSelectedValue();
        return new DataHandler(transferredObjects, localObjectFlavor.getMimeType());
    }

    @Override
    public boolean canImport(TransferSupport info) {
        return info.isDrop() && info.isDataFlavorSupported(localObjectFlavor);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean importData(TransferSupport info) {
        if (!canImport(info)) return false;

        // Get the drop location
        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();

        try {
            Delivery deliveryToMove = (Delivery) info.getTransferable().getTransferData(localObjectFlavor);
            if (listener != null) listener.onMove(deliveryToMove, dl.getIndex());

            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            LOGGER.warning(String.format("Error when drag and dropping delivery: %s", e.getMessage()));
        }

        return false;
    }

    /**
     * Listener interface.
     */
    public interface MoveDeliveryListener {
        /**
         * Call when a delivery is moved in the list of delivery.
         *
         * @param delivery Delivery moved
         * @param newIndex Destination index
         */
        void onMove(Delivery delivery, int newIndex);
    }
}
