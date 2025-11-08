package src;

import java.util.*;

public class WeatherSubject implements UiSubject {
    private Map<Channel, List<UiObserver>> observers;
    private double temperature;
    private double humidity;
    
    public WeatherSubject() {
        observers = new HashMap<>();
        for (Channel channel : Channel.values()) {
            observers.put(channel, new ArrayList<>());
        }
    }
    
    @Override
    public void register(UiObserver observer, Channel channel) {
        List<UiObserver> channelObservers = observers.get(channel);
        if (!channelObservers.contains(observer)) {
            channelObservers.add(observer);
            channelObservers.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        }
    }
    
    @Override
    public void unregister(UiObserver observer, Channel channel) {
        observers.get(channel).remove(observer);
    }
    
    @Override
    public void notify(Channel channel) {
        for (UiObserver observer : observers.get(channel)) {
            observer.onNotified(this, channel);
        }
    }
    
    public void updateWeather(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notify(Channel.WEATHER);
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public double getHumidity() {
        return humidity;
    }
}