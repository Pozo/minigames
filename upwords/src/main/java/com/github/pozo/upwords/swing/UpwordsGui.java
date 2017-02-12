package com.github.pozo.upwords.swing;

import com.github.pozo.upwords.Coordinate;
import com.github.pozo.upwords.IllegalCoordinateException;
import com.github.pozo.upwords.Player;
import com.github.pozo.upwords.Step;
import com.github.pozo.upwords.event.game.firstturn.FirstPlayerTurnEvent;
import com.github.pozo.upwords.event.game.firstturn.FirstTurnEventEventListener;
import com.github.pozo.upwords.event.game.secondturn.SecondPlayerTurnEvent;
import com.github.pozo.upwords.event.game.secondturn.SecondTurnEventEventListener;
import com.github.pozo.upwords.event.game.start.GameStartedEvent;
import com.github.pozo.upwords.event.game.start.GameStartedEventListener;
import com.github.pozo.upwords.game.UpWord;
import com.github.pozo.upwords.player.DefaultPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UpwordsGui {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpwordsGui.class);

    private final BorderLayout layoutManager = new BorderLayout(3, 3);
    private final JPanel gui = new JPanel(layoutManager);

    private JLabel[][] boardSquares = new JLabel[10][10];
    private final JLabel message = new JLabel("Upwords!");

    private Player actualPlayer;

    public UpwordsGui() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        final DefaultPlayer playerOne = new DefaultPlayer("Bela");
        final DefaultPlayer playerTwo = new DefaultPlayer("Otto");

        UpWord upWord = new UpWord(playerOne, playerTwo);

        JToolBar tools = createTools(upWord);
        gui.add(tools, BorderLayout.PAGE_START);

        JPanel board = createBoard();
        gui.add(board);

        JPanel tilesHolder = createTilesHolder(upWord);
        gui.add(tilesHolder, BorderLayout.PAGE_END);
    }

    private JToolBar createTools(UpWord upWord) {
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        JLabel currentPlayerLabel = new JLabel("Press start");
        JButton startButton = new JButton("Start");
        JButton nextPlayerButton = new JButton("Next player");
        JButton replaceAndPassButton = new JButton("Replace and pass");
        JButton resignButton = new JButton("Resign");
        tools.add(startButton);
        tools.add(nextPlayerButton);
        tools.add(replaceAndPassButton);
        tools.addSeparator();
        tools.add(resignButton);
        tools.addSeparator();
        tools.add(message);
        tools.add(currentPlayerLabel);

        upWord.addGameStartedEventListener(new GameStartedEventListener() {
            @Override
            public void gameStarted(GameStartedEvent gameStartedEvent) {
                Player firstPlayer = gameStartedEvent.getFirstPlayer();
                actualPlayer = firstPlayer;
                currentPlayerLabel.setText(firstPlayer.getName());
            }
        });
        upWord.addFirstPlayerTurnEventListener(new FirstTurnEventEventListener() {
            @Override
            public void firstPlayerTurn(FirstPlayerTurnEvent firstPlayerTurnEvent) {
                actualPlayer = firstPlayerTurnEvent.getPlayer();
                LOGGER.info("App.firstPlayerTurn: " + actualPlayer.getName());
            }
        });
        upWord.addSecondPlayerTurnEventListener(new SecondTurnEventEventListener() {
            @Override
            public void secondPlayerTurn(SecondPlayerTurnEvent secondPlayerTurnEvent) {
                actualPlayer = secondPlayerTurnEvent.getPlayer();
                LOGGER.info("App.firstPlayerTurn: " + actualPlayer.getName());
            }
        });
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                upWord.start();
            }
        });
        nextPlayerButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actualPlayer.finishTurn();
            }
        });
        upWord.start();

        return tools;
    }

    private JPanel createTilesHolder(UpWord upWord) {
        JPanel words = new JPanel(new GridLayout(1, 7));
        words.setBorder(new LineBorder(Color.BLACK));

        upWord.addGameStartedEventListener(new GameStartedEventListener() {
            @Override
            public void gameStarted(GameStartedEvent gameStartedEvent) {
                Player player = gameStartedEvent.getFirstPlayer();
                setupTiles(player, words);
                LOGGER.info("createTilesHolder.gameStarted: " + actualPlayer.getName());
            }
        });
        upWord.addFirstPlayerTurnEventListener(new FirstTurnEventEventListener() {
            @Override
            public void firstPlayerTurn(FirstPlayerTurnEvent firstPlayerTurnEvent) {
                Player player = firstPlayerTurnEvent.getPlayer();
                setupTiles(player, words);
                LOGGER.info("createTilesHolder.firstPlayerTurn: " + actualPlayer.getName());
            }
        });
        upWord.addSecondPlayerTurnEventListener(new SecondTurnEventEventListener() {
            @Override
            public void secondPlayerTurn(SecondPlayerTurnEvent secondPlayerTurnEvent) {
                Player player = secondPlayerTurnEvent.getPlayer();
                setupTiles(player, words);
                LOGGER.info("createTilesHolder.secondPlayerTurn: " + actualPlayer.getName());
            }
        });
        return words;
    }

    private void setupTiles(Player player, JPanel words) {
        List<String> tiles = player.getCharacters();

        for (String tile : tiles) {
            JButton word = new JButton(tile);
            word.setTransferHandler(new StringImportExportHandler(tile));
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
    }

    private JPanel createBoard() {
        JPanel board = new JPanel(new GridLayout(10, 10));
        board.setBorder(new LineBorder(Color.BLACK));

        for (int rowIndex = 0; rowIndex < boardSquares.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardSquares[rowIndex].length; columnIndex++) {
                final JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(60, 60));
                label.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));

                final StringImportExportHandler transferHandler = new StringImportExportHandler(label.getText());
                label.setTransferHandler(transferHandler);

                int finalRowIndex = rowIndex;
                int finalColumnIndex = columnIndex;
                label.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        JComponent c = (JComponent) e.getSource();
                        try {
                            Coordinate coordinate = new Coordinate(finalRowIndex, finalColumnIndex);
                            Step step = new Step(coordinate, label.getText());
                            actualPlayer.put(step);

                            transferHandler.setValue(label.getText());
                        } catch (IllegalCoordinateException e1) {
                            e1.printStackTrace();
                        }

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
        return board;
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
