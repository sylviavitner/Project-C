package src;

import javax.swing.*;

public class WeatherPanel extends JPanel implements UiObserver {
    private final int priority;
    private JLabel temperatureLabel;
    private JLabel humidityLabel;
    
    public WeatherPanel(int priority) {
        this.priority = priority;
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Weather Information"));
        
        temperatureLabel = new JLabel("Temperature: --°C");
        humidityLabel = new JLabel("Humidity: --%");
        
        add(temperatureLabel);
        add(humidityLabel);
    }
    
    @Override
    public void update(Object update) {
        if (update instanceof WeatherSubject) {
            WeatherSubject weather = (WeatherSubject) update;
            temperatureLabel.setText(String.format("Temperature: %.1f°C", weather.getTemperature()));
            humidityLabel.setText(String.format("Humidity: %.1f%%", weather.getHumidity()));
        }
    }
    
    @Override
    public void onNotified(UiSubject ref, Channel ch) {
        if (ch == Channel.WEATHER && ref instanceof WeatherSubject) {
            update(ref);
        }
    }
    
    @Override
    public int getPriority() {
        return priority;
    }
}