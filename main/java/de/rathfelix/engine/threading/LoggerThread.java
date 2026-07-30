package de.rathfelix.engine.threading;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggerThread extends Thread{

    private static LoggerThread instance;

    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private volatile boolean running = true;

    private LoggerThread(){}

    @Override
    public void run() {
        while (running) {
            debugLoop();
            threadSleep();
        }
    }

    // Handles debug que, list, ...
    private void debugLoop() {
        String msg;
        StringBuilder sb = new StringBuilder();
        while ((msg = queue.poll()) != null) {
            sb.append(msg).append("\n");
        }

        if (sb.length() > 0) {
            System.out.print(sb);
        }
    }

    // Thread sleep/wait
    private void threadSleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            running = false;
        }
    }

    // Add a string to the debug message que
    public void addQue(String msg) {
        queue.add(msg);
    }

    public static LoggerThread getInstance() {
        if (instance == null) {
            instance = new LoggerThread();
            instance.start();
        }
        return instance;
    }
}
