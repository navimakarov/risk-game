package com.risk.gui.game_window.side_panels;

import com.risk.gui.common_elements.ValueJLabel;
import com.risk.gui.game_window.GameWindow;
import com.risk.logic.Territory;
import com.risk.util.resources.Fonts;
import com.risk.util.resources.SoundPlayer;
import com.risk.util.exceptions.IllegalNumberOfReinforceTroopsException;
import com.risk.util.exceptions.SrcNotStatedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ReinforcementsPanel extends SidePanel {
    private JLabel territory;

    private JSpinner troopsLeftSpinner;
    private JButton deployTroopsButton;

    private static int playerBonus;
    private static int troopsLeft;
    private final ArrayList<String> labelsBonuses;

    public ReinforcementsPanel(GameWindow gameWindow) {
        super(gameWindow);
        troopsLeft = gameWindow.game.getCurrentPlayer().getBonus();
        this.labelsBonuses = gameWindow.game.getContinentsLabels();
        initLabels();
        initButtons();
    }

    public static void setBonus(int bonus) {
        playerBonus = bonus;
    }

    private void initLabels() {
        ValueJLabel reinforcementsGot = new ValueJLabel("Reinforcements got: ", playerBonus);
        ValueJLabel reinforcementsLeft = new ValueJLabel("Reinforcements Left: ", troopsLeft);

        JLabel fromContinentsControlled = new JLabel("From continents controlled: ");
        JLabel territoryChosen = new JLabel("Territory chosen:");
        territory = new JLabel("<none>");

        ArrayList<JLabel> labels = new ArrayList<>();

        labels.add(reinforcementsGot);
        labels.add(new JLabel(labelsBonuses.get(0)));
        if (labelsBonuses.size() > 1) {
            labels.add(fromContinentsControlled);
            for (int i = 1; i < labelsBonuses.size(); i++) {
                labels.add(new JLabel(labelsBonuses.get(i)));
            }
        }
        while (labels.size() < 10)  labels.add(new JLabel(" "));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);
        labels.add(territory);


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
        troopsLeftSpinner = new JSpinner(new SpinnerNumberModel(troopsLeft, 1, troopsLeft, 1));
        ((JSpinner.DefaultEditor) troopsLeftSpinner.getEditor()).getTextField().setEditable(false);
        Dimension troopsSpinnerSize = new Dimension(LABEL_WIDTH/5, LABEL_HEIGHT);
        troopsLeftSpinner.setPreferredSize(troopsSpinnerSize);
        troopsLeftSpinner.setMaximumSize(troopsSpinnerSize);
        troopsLeftSpinner.setMinimumSize(troopsSpinnerSize);
        deployTroopsButton = new JButton("Deploy troops");
        deployTroopsButton.setPreferredSize(new Dimension(LABEL_WIDTH/2, LABEL_HEIGHT));
        deployTroopsButton.setFont(Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-10));
        deployTroopsButton.setEnabled(false);
        deployTroopsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (deployTroopsButton.isEnabled()) {
                    SoundPlayer.buttonClickedSound();
                    reinforce();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
            }
        });
        bottomPanel.add(troopsLeftSpinner);
        bottomPanel.add(deployTroopsButton);
        add(bottomPanel);
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);
        deployTroopsButton.setEnabled(true);
        if (src == null) {
            territory.setText("<none>");
            deployTroopsButton.setEnabled(false);
        } else {
            territory.setText(src.getName());
            deployTroopsButton.setEnabled(true);
        }
    }

    public void reinforce() {
        int reinforcedTroops = (int) troopsLeftSpinner.getValue();

        try {
            gameWindow.game.reinforce(reinforcedTroops, src);
        } catch (SrcNotStatedException | IllegalNumberOfReinforceTroopsException e) {
            e.printStackTrace();
        }
    }
}
