package wtf.multimap.gf.event;

import wtf.multimap.gf.event.listener.EventHandler;
import wtf.multimap.gf.event.listener.Listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private EventExecutor executor = new EventExecutor();
    private Map<Listener, List<Class>> eventClassMap = new HashMap<Listener, List<Class>>();

    public void registerEvents(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            Parameter parameter = method.getParameters()[0];
            Class<?> event = null;
            try {
                event = Class.forName(parameter.toString().split(" ")[0]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ;
            if (event == null) continue;
            eventClassMap.computeIfAbsent(listener, k -> new ArrayList<Class>());
            eventClassMap.get(listener).add(event);
        }

    }

    public Event callEvent(Event event) {
        for (Listener listener : eventClassMap.keySet()) {
            for (Class clazz : eventClassMap.get(listener)) {
                for (Method method : listener.getClass().getMethods()) {
                    if (!method.isAnnotationPresent(EventHandler.class)) continue;
                    Parameter parameter = method.getParameters()[0];
                    Class<?> eventParam = null;
                    try {
                        eventParam = Class.forName(parameter.toString().split(" ")[0]);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (event == null) continue;
                    if (!event.getClass().equals(eventParam)) continue;
                    executor.execute(listener, event);
                }
            }
        }
        return event;
    }

}
