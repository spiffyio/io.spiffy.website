package io.spiffy.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public interface Callback {
        public void onSuccess();

        public void onFailure(final InterruptedException e);
    }

    public static final Callback BlockingCallback = new Callback() {
        public void onSuccess() {
        }

        public void onFailure(final InterruptedException e) {
        }
    };

    public static void run(final Runnable ... tasks) {
        if (tasks == null || tasks.length == 0) {
            return;
        }

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(tasks.length);
            for (final Runnable task : tasks) {
                executor.submit(task);
            }
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    public static void run(final Callback callback, final Runnable ... tasks) {
        if (tasks == null || tasks.length == 0) {
            return;
        }

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(tasks.length);
            for (final Runnable task : tasks) {
                executor.submit(task);
            }
        } finally {
            if (executor != null) {
                executor.shutdown();
                if (callback == null) {
                    return;
                }
                try {
                    executor.awaitTermination(30L, TimeUnit.SECONDS);
                } catch (final InterruptedException e) {
                    callback.onFailure(e);
                }
                callback.onSuccess();
            }
        }
    }

}