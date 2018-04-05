package wtf.multimap.gf.thread;

public class GameThread extends Thread {

    private String threadName;
    private GameRunnable gameRunnable;

    public GameThread(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public void setGameRunnable(GameRunnable gameRunnable) {
        this.gameRunnable = gameRunnable;
    }

    @Override
    public void run() {
        getGameRunnable().run();
    }

}
