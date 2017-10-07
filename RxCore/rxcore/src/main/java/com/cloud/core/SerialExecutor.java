package com.cloud.core;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/21
 * @Description:Runnable队列顺序执行
 * @Modifier:
 * @ModifyContent:
 */
public class SerialExecutor implements Executor {
    private final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
    private Executor executor = null;
    private Runnable active = null;
    private static SerialExecutor serialExecutor = null;

    public static SerialExecutor getInstance() {
        return serialExecutor == null ? serialExecutor = new SerialExecutor() : serialExecutor;
    }

    public SerialExecutor() {
        executor = new SQExctor();
    }

    private class SQExctor implements Executor {
        @Override
        public void execute(Runnable command) {
            if (command != null) {
                command.run();
            }
        }
    }

    public synchronized void execute(final Runnable r) {
        tasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (active == null) {
            scheduleNext();
        }
    }

    private synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
