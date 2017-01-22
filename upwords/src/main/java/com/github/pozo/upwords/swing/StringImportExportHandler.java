package com.github.pozo.upwords.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.io.IOException;

public class StringImportExportHandler extends TransferHandler {
    private static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
    private static final String EMPTY_VALUE = "";

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public StringImportExportHandler(String value) {
        this.value = value;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new StringSelection(value);
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // Decide what to do after the drop has been accepted
        if (action != DnDConstants.ACTION_NONE) {
            setTextAndValue(source, EMPTY_VALUE);
            super.exportDone(source, data, action);
        }
    }

    private boolean setTextAndValue(Component component, String text) {
        if (component instanceof JButton) {
            final JButton button = (JButton) component;
            button.setText(text);
            setValue(text);
            return true;
        } else if (component instanceof JLabel) {
            final JLabel label = (JLabel) component;
            label.setText(text);
            setValue(text);
            return true;
        }

        return false;
    }

    private String getText(Component source) {
        if (source instanceof JButton) {
            return ((JButton) source).getText();
        } else if (source instanceof JLabel) {
            return ((JLabel) source).getText();
        }
        return EMPTY_VALUE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        try {
            final Transferable transferable = support.getTransferable();
            final Object transferableText = transferable.getTransferData(SUPPORTED_DATE_FLAVOR);

            if (transferableText instanceof String) {
                final String currentText = getText(support.getComponent());
                boolean currentTextIsEmpty = EMPTY_VALUE.equals(currentText);
                boolean transferableTextIsNotEmpty = !transferableText.equals(EMPTY_VALUE);
                boolean transferableIsSupported = support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);

                return currentTextIsEmpty
                        && transferableIsSupported
                        && transferableTextIsNotEmpty;
            }

        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean importData(TransferSupport support) {
        boolean accept = false;
        if (canImport(support)) {
            try {
                Transferable transferable = support.getTransferable();
                Object value = transferable.getTransferData(SUPPORTED_DATE_FLAVOR);
                if (value instanceof String) {
                    Component component = support.getComponent();
                    accept = setTextAndValue(component, value.toString());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        return accept;
    }
}
