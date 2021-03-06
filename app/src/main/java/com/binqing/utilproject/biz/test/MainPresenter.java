package com.binqing.utilproject.biz.test;

import android.database.Cursor;
import android.os.Looper;
import android.util.Log;

import com.binqing.utilproject.Activity.MainActivity;
import com.binqing.utilproject.Callback;
import com.binqing.utilproject.MainLoopHandler;
import com.binqing.utilproject.Utils.LogUtils;
import com.binqing.utilproject.data.DataProvider;
import com.binqing.utilproject.data.db.DBManager;
import com.binqing.utilproject.data.db.SqliteOpenHelperUtil;
import com.binqing.utilproject.data.entry.EntryTest;
import com.binqing.utilproject.data.entry.interfaceEntry.AbsEntry;
import com.binqing.utilproject.data.object.GoodObject;
import com.binqing.utilproject.data.object.TestObject;
import com.binqing.utilproject.data.object.UserObject;
import com.binqing.utilproject.data.parse.AnnoParse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainActivity mView;

    public MainPresenter(MainActivity activity) {
        bindView(activity);
    }

    private void bindView(MainActivity activity) {
        mView = activity;
        MainLoopHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("[MainPresenter]" ,String.valueOf(Looper.getMainLooper().getThread() == Thread.currentThread()));
            }
        });
    }

    public void testAnnotation() {
        LogUtils.e("[MainPresenter]", AnnoParse.initTableInfo(EntryTest.class).toString());
        List<Object> testObjects = new ArrayList<>();
        final TestObject testObject = new TestObject(1, "123123123", "11111");
        testObjects.add(testObject);
        AbsEntry entryTest = new EntryTest();
//        DBManager.getInstance().insert(mView, entryTest, testObjects);
//        DBManager.getInstance().delete(mView, entryTest, "t_id = ?", new String[]{"2"});
//        SqliteOpenHelperUtil.OnPackageDataCallback<TestObject> callback = new SqliteOpenHelperUtil.OnPackageDataCallback<TestObject>() {
//            @Override
//            public List<TestObject> onPackage(Cursor cursor) {
//                AbsEntry entry = new EntryTest();
//                return AnnoParse.fillCursor(cursor, entry, TestObject.class);
//            }
//        };
//        Callback<List<TestObject>> callback1 = new Callback<List<TestObject>>() {
//            @Override
//            public void onResult(List<TestObject> object) {
//
//                for (TestObject testObject1 : object) {
//                    LogUtils.e("[MainPresenter]", testObject1.toString());
//                }
//            }
//            @Override
//            public void onException(String code, String reason) {
//
//            }
//        };
//        DBManager.getInstance().query(mView, entryTest, null, null, callback, callback1);

        Callback<UserObject> callback = new Callback<UserObject>() {
            @Override
            public void onResult(UserObject object) {
                if (object == null){
                    Log.e("[MainPresenter]", "账号注册过了");
                } else {
                    Log.e("[MainPresenter]", object.toString());
                }

            }

            @Override
            public void onException(String code, String reason) {
                Log.e(code, reason);
            }
        };
//        GoodObject object = new GoodObject();
//        object.setGoodName("欧舒丹");
//        object.setPage("0");
//        DataProvider.getInstance().searchGood(object, callback);

        DataProvider.getInstance().register("qing14", "qing1234", callback);
    }

}
