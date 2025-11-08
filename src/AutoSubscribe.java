package src;
public class AutoSubscribe implements UiObserver
{
    private UiSubject subject;
    private Channel channel;
    private int priority;

    public AutoSubscribe(UiSubject subject)
    {
        this.subject = subject;
        subject.register(this, channel);
    }

    public void AutoUnsubscribe()
    {
        subject.unregister(this,channel);
    }

public void update(Object update) {
        // Handle updates depending on subject type
        if (update instanceof WeatherSubject) {
            WeatherSubject weather = (WeatherSubject) update;
            System.out.printf("AutoSubscribe received update: %.1f°C, %.1f%% humidity%n",
                              weather.getTemperature(), weather.getHumidity());
        } else {
            System.out.println("AutoSubscribe received update: " + update);
        }
    }



    public void onNotified(UiSubject ref, Channel ch) {
        // Only react if the notification is for the channel we subscribed to
        if (ch == channel) {
            update(ref);
        }
    }



    @Override
    public int getPriority()
    {
       return priority;
        
    }
}