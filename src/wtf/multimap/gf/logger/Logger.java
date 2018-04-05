package wtf.multimap.gf.logger;

public class Logger {

    private String loggerName;

    public Logger(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public void log(LogType logType, String message) {
        System.out.println(String.format("[%s / %s] %s", getLoggerName(), logType.name(), message));
    }

}
