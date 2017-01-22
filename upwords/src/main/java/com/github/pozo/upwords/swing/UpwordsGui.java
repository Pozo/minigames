package com.github.pozo.upwords.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpwordsGui {
    private final BorderLayout layoutManager = new BorderLayout(3, 3);
    private final JPanel gui = new JPanel(layoutManager);

    private JLabel[][] boardSquares = new JLabel[10][10];
    private JPanel board;
    private JPanel words;

    private final JLabel message = new JLabel("Upwords!");

    public UpwordsGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("New")); // TODO - add functionality!
        tools.add(new JButton("Save")); // TODO - add functionality!
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

        board = new JPanel(new GridLayout(10, 10));
        board.setBorder(new LineBorder(Color.BLACK));

        for (int rowIndex = 0; rowIndex < boardSquares.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardSquares[rowIndex].length; columnIndex++) {
                final JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(60, 60));
                label.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));

                final StringImportExportHandler transferHandler = new StringImportExportHandler(label.getText());
                label.setTransferHandler(transferHandler);
                label.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        JComponent c = (JComponent) e.getSource();
                        transferHandler.setValue(label.getText());

                        TransferHandler handler = c.getTransferHandler();
                        handler.exportAsDrag(c, e, TransferHandler.MOVE);
                    }
                });
                boardSquares[columnIndex][rowIndex] = label;
            }
        }
        for (int rowIndex = 0; rowIndex < boardSquares.length; rowIndex++) {
            for (JLabel[] boardSquare : boardSquares) {
                board.add(boardSquare[rowIndex]);
            }
        }
        gui.add(board);

        words = new JPanel(new GridLayout(1, 7));
        words.setBorder(new LineBorder(Color.BLACK));

        String[] tiles = new String[]{"a", "b", "c", "d", "e", "f", "g"};

        for (int i = 0; i < tiles.length; i++) {
            JButton word = new JButton(tiles[i]);
            word.setTransferHandler(new StringImportExportHandler(tiles[i]));
            word.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    JComponent c = (JComponent) e.getSource();
                    System.out.println("e = [" + e + "]");
                    TransferHandler handler = c.getTransferHandler();
                    handler.exportAsDrag(c, e, TransferHandler.MOVE);
                }
            });
            words.add(word);
        }

        gui.add(words, BorderLayout.PAGE_END);
    }

    final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                UpwordsGui upwords = new UpwordsGui();

                JFrame frame = new JFrame("Upwords");
                frame.add(upwords.getGui());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                frame.pack();
                // ensures the minimum size is enforced.
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

}
