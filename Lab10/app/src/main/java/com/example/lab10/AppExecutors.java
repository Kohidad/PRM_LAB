package com.example.lab10;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainthread;
    private final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor mainthread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainthread = mainthread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainthread() {
        return mainthread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
