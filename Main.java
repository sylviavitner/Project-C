
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import src.*;

public class Main implements TimeController {
    private Timer timer;
    private boolean isPaused = false;
    private int speedMultiplier = 1;
    private WeatherSubject weatherSubject;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createGUI());
    }

    private void createGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Weather Monitoring System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        weatherSubject = new WeatherSubject();

        // Create observers with different priorities
        WeatherPanel highPriorityWeather = new WeatherPanel(3);
        WeatherPanel mediumPriorityWeather = new WeatherPanel(2);
        WeatherPanel lowPriorityWeather = new WeatherPanel(1);

        // Register observers
        weatherSubject.register(highPriorityWeather, Channel.WEATHER);
        weatherSubject.register(mediumPriorityWeather, Channel.WEATHER);
        weatherSubject.register(lowPriorityWeather, Channel.WEATHER);

        //AutoSubscribe 
        AutoSubscribe auto = new AutoSubscribe(weatherSubject, Channel.WEATHER, 5);


        // Layout setup
        JPanel weatherPanels = new JPanel(new GridLayout(3, 1));
        weatherPanels.add(highPriorityWeather);
        weatherPanels.add(mediumPriorityWeather);
        weatherPanels.add(lowPriorityWeather);

        frame.add(weatherPanels, BorderLayout.CENTER);

        // Add the time control panel
        TimePanel timePanel = new TimePanel(this);
        frame.add(timePanel, BorderLayout.SOUTH);

        startTimer();

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startTimer() {
        if (timer != null) timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    double temperature = 20 + Math.random() * 10;
                    double humidity = 40 + Math.random() * 30;
                    SwingUtilities.invokeLater(() -> weatherSubject.updateWeather(temperature, humidity));
                }
            }
        }, 0, 2000 / Math.max(1, speedMultiplier));
    }

    @Override
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    @Override
    public void setSpeed(int multiplier) {
        this.speedMultiplier = multiplier;
        startTimer();
    }
}
