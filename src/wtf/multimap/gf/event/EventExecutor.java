package wtf.multimap.gf.event;

import wtf.multimap.gf.event.listener.EventHandler;
import wtf.multimap.gf.event.listener.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventExecutor {

    public void execute(Listener listener, Event event) {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            try {
                method.invoke(listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
