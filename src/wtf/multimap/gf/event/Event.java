package wtf.multimap.gf.event;

public abstract class Event {

    private String eventName;
    private boolean cancelled;

    public String getEventName() {
        if (eventName == null) eventName = getClass().getCanonicalName();
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
