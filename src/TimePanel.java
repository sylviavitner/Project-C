package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// TimePanel class for controlling the simulation time
public class TimePanel extends JPanel {
    private JButton playPauseButton;
    private JComboBox<String> speedSelector;
    private boolean isPlaying = true;
    private TimeController timeController;

    public TimePanel(TimeController timeController) {
        this.timeController = timeController;
        initializeUI();
    }

    // Initializes time control UI
    private void initializeUI() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        playPauseButton = new JButton("Pause");
        playPauseButton.addActionListener(this::togglePlayPause);

        String[] speed = {"1x", "5x", "20x"};
        speedSelector = new JComboBox<>(speed);
        speedSelector.addActionListener(this::changeSpeed);

        add(playPauseButton);
        add(new JLabel("Speed:"));
        add(speedSelector);
    }

    // Toggle play/pause state
    private void togglePlayPause(ActionEvent e) {
        isPlaying = !isPlaying;
        if (isPlaying) {
            playPauseButton.setText("Pause");
        } else {
            playPauseButton.setText("Play");
        }
        timeController.setPaused(!isPlaying);
    }

    private void changeSpeed(ActionEvent e) {
        String selected = (String) speedSelector.getSelectedItem();
        int multiplier = switch (selected) {
            case "1x" -> 1;
            case "5x" -> 5;
            case "20x" -> 20;
            default -> 1;
        };
        timeController.setSpeed(multiplier);
    }
}
