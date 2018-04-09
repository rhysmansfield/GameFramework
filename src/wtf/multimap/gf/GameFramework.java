package wtf.multimap.gf;

import wtf.multimap.gf.event.EventManager;
import wtf.multimap.gf.event.events.GameTickEvent;
import wtf.multimap.gf.listener.GameTickListener;
import wtf.multimap.gf.logger.LogType;
import wtf.multimap.gf.logger.Logger;
import wtf.multimap.gf.thread.GameRunnable;
import wtf.multimap.gf.thread.GameThread;

import java.util.HashMap;
import java.util.Map;

public class GameFramework {

    /**
     * Initialise variables for the main game class
     * Logger logger - Main logger object, used for client output
     * EventManager eventManager - The event manager class
     * Map<String, Thread> processThreads - Mapping game threads,
     *
     * @param String - Name of the game thread
     * @param Thread - The thread object
     */
    private Logger logger;
    private EventManager eventManager;
    private Map<String, Thread> processThreads;

    /**
     * Initialising the main GameFramework class
     *
     * @param gameName - (TEMPORARY) Used for the logger name
     */
    GameFramework(String gameName) {
        logger = new Logger(gameName);
        eventManager = new EventManager();
        eventManager.registerEvents(new GameTickListener());
        getLogger().log(LogType.INFO, "Starting game setup process...");
        getLogger().log(LogType.INFO, "Initialising multithreading environment...");
        Long timeToStartMultithreading = System.currentTimeMillis();
        processThreads = new HashMap<String, Thread>();
        loadThreads();
        startThreads();
        Long timeFinishedStartingMultithreading = System.currentTimeMillis();
        getLogger().log(LogType.INFO, String.format("Initialised multithreading environment (Took: %d millis)", timeFinishedStartingMultithreading - timeToStartMultithreading));
    }

    /**
     * Starting the game client
     */
    public static void main(String[] args) {
        new GameFramework("New Game");
    }

    /**
     * Return the Logger object
     */
    Logger getLogger() {
        return logger;
    }

    /**
     * Return the game threads map
     */
    Map<String, Thread> getProcessThreads() {
        return processThreads;
    }

    /**
     * Put the required threads into the game threads map
     */
    void loadThreads() {
        GameThread tick_thread = new GameThread("Tick Thread");
        tick_thread.setGameRunnable(new GameRunnable() {
            @Override
            public void run() {
                while (true) {
                    GameTickEvent gameTickEvent = (GameTickEvent) eventManager.callEvent(new GameTickEvent("Hello"));
                    // if tick event is cancelled, the game will close;
                    if (gameTickEvent.isCancelled()) System.exit(0);
                    try {
                        Thread.sleep(2000);
                        getLogger().log(LogType.INFO, "Game is ticking at 0.5tps");
                    } catch (InterruptedException e) {
                        getLogger().log(LogType.ERROR, "Game has failed to tick! closing...");
                        System.exit(0);
                    }
                }
            }
        });
        getProcessThreads().put("Tick Thread", tick_thread);
    }

    /**
     * Start all the threads that are currently in the game threads map
     * Thread will not start if already started
     */
    void startThreads() {
        for (String threadName : processThreads.keySet()) {
            if (getThread(threadName).isAlive()) continue;
            getThread(threadName).start();
        }
    }

    /**
     * Get thread from game thread map
     *
     * @param threadName - The name of thread you wish to retrieve
     */
    Thread getThread(String threadName) {
        return processThreads.get(threadName);
    }

}
