package wtf.multimap.gf.listener;

import wtf.multimap.gf.event.events.GameTickEvent;
import wtf.multimap.gf.event.listener.EventHandler;
import wtf.multimap.gf.event.listener.Listener;

public class GameTickListener implements Listener {

    @EventHandler
    public void onGameTick(GameTickEvent gameTickEvent) {
        gameTickEvent.setCancelled(true);
    }

}
