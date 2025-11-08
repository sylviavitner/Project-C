package src;

public interface UiObserver {
    void update(Object update);
    void onNotified(UiSubject ref, Channel ch);
    int getPriority();
}