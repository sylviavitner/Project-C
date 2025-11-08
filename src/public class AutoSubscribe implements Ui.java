public class AutoSubscribe implements UiObserver
{
    private UiSubject subject;

    public AutoSubscribe(UiSubject subject)
    {
        this.subject = subject;
        subject.attach(this);
    }

    public void AutoUnsubscribe()
    {
        subject.detach(this);
    }
}