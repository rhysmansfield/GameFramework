package wtf.multimap.gf.event.events;

import wtf.multimap.gf.event.Event;

public class GameTickEvent extends Event {

    private String someString;

    public GameTickEvent(String someString) {
        this.someString = someString;
    }

    public String getSomeString() {
        return someString;
    }
}
