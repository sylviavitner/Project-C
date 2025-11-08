package src;

public interface UiSubject {
    void register(UiObserver observer, Channel channel);
    void unregister(UiObserver observer, Channel channel);
    void notify(Channel channel);
}