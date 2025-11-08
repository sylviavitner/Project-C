
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import src.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Weather Monitoring System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create subjects
        WeatherSubject weatherSubject = new WeatherSubject();

        // Create observers with different priorities
        WeatherPanel highPriorityWeather = new WeatherPanel(3);
        WeatherPanel mediumPriorityWeather = new WeatherPanel(2);
        WeatherPanel lowPriorityWeather = new WeatherPanel(1);

        // Register observers
        weatherSubject.register(highPriorityWeather, Channel.WEATHER);
        weatherSubject.register(mediumPriorityWeather, Channel.WEATHER);
        weatherSubject.register(lowPriorityWeather, Channel.WEATHER);

        // Layout setup
        JPanel weatherPanels = new JPanel(new GridLayout(3, 1));
        weatherPanels.add(highPriorityWeather);
        weatherPanels.add(mediumPriorityWeather);
        weatherPanels.add(lowPriorityWeather);

        frame.add(weatherPanels, BorderLayout.CENTER);

        // Simulate weather updates
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double temperature = 20 + Math.random() * 10;
                double humidity = 40 + Math.random() * 30;
                SwingUtilities.invokeLater(() -> weatherSubject.updateWeather(temperature, humidity));
            }
        }, 0, 2000);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}