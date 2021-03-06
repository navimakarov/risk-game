package com.risk.gui.game_window.side_panels;

import com.risk.gui.game_window.GameWindow;
import com.risk.logic.Territory;
import com.risk.util.exceptions.DstNotStatedException;
import com.risk.util.exceptions.IllegalNumberOfFortifyTroopsException;
import com.risk.util.exceptions.SrcNotStatedException;
import com.risk.util.exceptions.WrongTerritoriesPairException;
import com.risk.util.resources.Fonts;
import com.risk.util.resources.SoundPlayer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FortifyPanel extends SidePanel {
    private JLabel srcLabel, srcTerritory, dstLabel, dstTerritory;

    private JSpinner troopsToTransferSpinner;
    private JButton fortifyButton, skipButton;

    public FortifyPanel(GameWindow gameWindow) {
        super(gameWindow);
        initLabels();
        initButtons();
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);
        if (src != null) {
            srcTerritory.setText(src.getName());
        } else {
            srcTerritory.setText("<none>");
        }
        if (dst != null) {
            dstTerritory.setText(dst.getName());
        } else {
            dstTerritory.setText("<none>");
        }

        if (src != null & dst != null) {
            int availableTroops = src.getTroops()-1;
            if (availableTroops >= 1) {
                fortifyButton.setEnabled(true);
                troopsToTransferSpinner.setModel(new SpinnerNumberModel(availableTroops,1, availableTroops, 1));
            } else {
                fortifyButton.setEnabled(false);
                troopsToTransferSpinner.setModel(new SpinnerNumberModel(0,0,0,0));
            }
        } else {
            fortifyButton.setEnabled(false);
            troopsToTransferSpinner.setModel(new SpinnerNumberModel(0,0,0,0));
        }
        ((JSpinner.DefaultEditor) troopsToTransferSpinner.getEditor()).getTextField().setEditable(false);
    }

    private void initLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();

        srcLabel = new JLabel("Transfer from:");
        srcTerritory = new JLabel("<none>");

        labels.add(srcLabel);
        labels.add(srcTerritory);

        dstLabel = new JLabel("Transfer to:");
        dstTerritory = new JLabel("<none>");

        labels.add(dstLabel);
        labels.add(dstTerritory);


        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(FONT_COLOR);
            label.setAlignmentX(0.5f);
            add(label);
        }
    }

    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        troopsToTransferSpinner = new JSpinner(new SpinnerNumberModel(0,0,0,0));
        Dimension troopsSpinnerSize = new Dimension(LABEL_WIDTH/5, LABEL_HEIGHT);
        troopsToTransferSpinner.setPreferredSize(troopsSpinnerSize);
        ((JSpinner.DefaultEditor) troopsToTransferSpinner.getEditor()).getTextField().setEditable(false);
        troopsToTransferSpinner.setMaximumSize(troopsSpinnerSize);
        troopsToTransferSpinner.setMinimumSize(troopsSpinnerSize);
        fortifyButton = new JButton("Fortify");
        fortifyButton.setFont(Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-10));
        fortifyButton.setPreferredSize(new Dimension(LABEL_WIDTH/3, LABEL_HEIGHT));
        fortifyButton.setEnabled(false);
        fortifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (fortifyButton.isEnabled()) {
                    SoundPlayer.buttonClickedSound();
                    fortify();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (fortifyButton.isEnabled()) {
                    SoundPlayer.optionChosenSound();
                }
            }
        });
        bottomPanel.add(troopsToTransferSpinner);
        bottomPanel.add(fortifyButton);
        bottomPanel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT+15));
        bottomPanel.setMaximumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT+15));
        bottomPanel.setMinimumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT+15));
        add(bottomPanel);

        skipButton = new JButton("Skip");
        skipButton.setFont(Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-10));
        skipButton.setPreferredSize(new Dimension(LABEL_WIDTH/3, LABEL_HEIGHT));
        skipButton.setAlignmentX(0.5f);
        skipButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                skipFortify();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
            }
        });
        add(skipButton);
    }

    private void skipFortify() {
        gameWindow.game.skipFortify();
    }

    private void fortify() {
        try {
            int troopsToTransfer = (int) troopsToTransferSpinner.getValue();

            gameWindow.game.fortify(src, dst, troopsToTransfer);
        } catch (DstNotStatedException | WrongTerritoriesPairException | IllegalNumberOfFortifyTroopsException | SrcNotStatedException e) {
            e.printStackTrace();
        }

    }
}
