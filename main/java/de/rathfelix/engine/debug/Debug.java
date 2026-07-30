package de.rathfelix.engine.debug;

import de.rathfelix.engine.threading.LoggerThread;

public class Debug {

    private static LoggerThread loggerThread = LoggerThread.getInstance();

    public static void log(String msg) {
        loggerThread.addQue(msg);
    }
}
