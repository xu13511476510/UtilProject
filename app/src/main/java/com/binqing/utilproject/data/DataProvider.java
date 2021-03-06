package com.binqing.utilproject.data;

import com.binqing.utilproject.Callback;
import com.binqing.utilproject.data.object.GoodObject;
import com.binqing.utilproject.data.object.UserObject;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataProvider {

    private static volatile DataProvider mInstance;

    public static DataProvider getInstance() {
        if (mInstance == null) {
            synchronized (DataProvider.class) {
                if (mInstance == null) {
                    mInstance = new DataProvider();
                }
            }
        }
        return mInstance;
    }

    private Executor mExecutor;
    private ThreadPoolExecutor mThreadPoolExecutor;

    private DataProvider() {
        mExecutor = new Executor();
        mThreadPoolExecutor = getThreadPoolExecutor();
    }

    public void runInExecutor(Runnable command) {
        if (mExecutor == null) {
            return;
        }
        mExecutor.execute(command);
    }

    public void searchGood(final GoodObject goodObject, final Callback<GoodObject> callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                DataCenter.getInstance().searchGood(goodObject, callback);
            }
        });
    }

    public void register(final String account, final String password, final Callback<UserObject> callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                DataCenter.getInstance().register(account, password, callback);
            }
        });
    }










    private ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    private class Executor implements java.util.concurrent.Executor {
        @Override
        public void execute( Runnable command) {
            mThreadPoolExecutor.execute(command);
        }
    }
}
