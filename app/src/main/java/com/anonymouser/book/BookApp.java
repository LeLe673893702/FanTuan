package com.anonymouser.book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.anonymouser.book.bean.BookCaseBean;
import com.anonymouser.book.bean.BookCaseBeanDao;
import com.anonymouser.book.bean.BookContentDao;
import com.anonymouser.book.bean.BookInfoDao;
import com.anonymouser.book.bean.DaoMaster;
import com.anonymouser.book.bean.PaintInfo;
import com.anonymouser.book.bean.UserInfoDao;
import com.anonymouser.book.bean.ZhuiShuBookContentDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Locale;

import cn.bmob.v3.Bmob;

/**
 * Created by YandZD on 2017/7/13.
 */

public class BookApp extends MultiDexApplication {
    public static Context mContext = null;

    public static boolean isSimple = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());

        //设置全局繁简体
        initLanguage();

        LeakCanary.install(this);

        initOkGo();

        initDataBase();

        initLogger();

        Bmob.initialize(this, "a1bf3072183ffa27e465048d43061e93");
    }

    public static Context getContext() {
        return mContext;
    }

    /* 判断手机字体繁简体 */
    private void initLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String region = locale.getCountry();
        if (region.contains("TW") || region.contains("HK")) {
            isSimple = false;
        } else {
            isSimple = true;
        }

        if (getSharedPreferences("info", MODE_PRIVATE).getBoolean("isFirst", true)) {
            PaintInfo.INSTANCE.setSimple(isSimple);
            getSharedPreferences("info", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirst", false)
                    .commit();
        }
    }

    /* 初始化okgo */
    private void initOkGo() {
        OkGo.getInstance().init(this);
        try {
            OkGo.getInstance().init(this)                       //必须调用初始化
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                    .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }


    private void initDataBase() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "db",
                null);
        new DaoMaster(helper.getWritableDatabase());

    }

    ArrayList<BookCaseBean> beans;

    /* 增加追书api阅读，数据库进行相应升级，从版本3到版本4 */
    public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, final int oldVersion, final int newVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                        @Override
                        public void onCreateAllTables(Database db, boolean ifNotExists) {
                            DaoMaster.createAllTables(db, ifNotExists);
                            if (oldVersion == 3 && newVersion == 4) {
                                for (BookCaseBean bean : beans) {
                                    db.execSQL(String.format("INSERT INTO BOOK_CASE_BEAN('BOOK_NAME','AUTHER','READ_PROGRESS','READ_PAGE_INDEX','READ_CHAPTER_TITLE','IS_THE_CACHE','BASE_LINK','USE_SOURCE','IMG','IS_ZHUI_SHU') VALUES ('%s','%s','%d','%d','%s','%d','%s','%s','%s','%b')"
                                            , bean.getBookName(), bean.getAuther(), bean.getReadProgress(), bean.getReadPageIndex(), bean.getReadChapterTitle(), 0, bean.getBaseLink(), bean.getUseSource(), bean.getImg(), false));
                                }
                            }
                        }

                        @Override
                        public void onDropAllTables(Database db, boolean ifExists) {
//                            if (oldVersion == 3 && newVersion == 4) {
                            beans = new ArrayList<>();
                            BookCaseBean bean;
                            Cursor cursor = db.rawQuery("select * from BOOK_CASE_BEAN", null);
                            while (cursor.moveToNext()) {
                                bean = new BookCaseBean();
                                bean.setBookName(cursor.getString(0));
                                bean.setAuther(cursor.getString(1));
                                bean.setReadProgress(cursor.getInt(2));
                                bean.setReadPageIndex(cursor.getInt(3));
                                bean.setReadChapterTitle(cursor.getString(4));
                                bean.setBaseLink(cursor.getString(6));
                                bean.setUseSource(cursor.getString(7));
                                bean.setImg(cursor.getString(8));
                                bean.setIsZhuiShu(false);
                                beans.add(bean);
                            }
//                            }


                            DaoMaster.dropAllTables(db, ifExists);
                        }
                    }
                    , UserInfoDao.class
                    , BookInfoDao.class
                    , BookCaseBeanDao.class
                    , BookContentDao.class
                    , ZhuiShuBookContentDao.class);
        }
    }
}
