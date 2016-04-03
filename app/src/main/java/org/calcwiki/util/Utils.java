package org.calcwiki.util;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.jude.utils.JUtils;

import org.calcwiki.R;
import org.calcwiki.data.storage.CurrentUser;
import org.calcwiki.ui.drawer.MainDrawer;

import cn.com.caoyue.util.time.Time;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Utils {

    static Context context;
    static Handler handler;

    /**
     * 在 App 类中初始化工具
     * @param context App 类实例
     */
    public static void init(Context context) {
        Utils.context = context;
        handler = new Handler(context.getMainLooper());
    }

    /**
     * 获取本机 IP 地址，请勿在主线程调用
     * @return 本机 IP 地址
     */
    public static String getIP() {
        return JUtils.sendPost("https://api.caoyue.com.cn/getIP.php", "");
    }

    /**
     * 异步获取本机 IP 地址
     */
    public static void getIP(final Action1<String> callback) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(getIP());
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.call("");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        callback.call(s);
                    }
                });
    }

    /**
     * 将 ISO 8601 时间戳转换为 Haruue 时间工具
     * @param iso8601time ISO 8601 时间戳
     * @return 时间工具实例
     * @see <a href="http://haruue.github.io/Time_Class_Util/">http://haruue.github.io/Time_Class_Util/</a>
     */
    public static Time isoTimeToTime(String iso8601time) {
        return new Time(iso8601time, "yyyy-MM-ddThh:mm:ssZ");
    }

    /**
     * 获取 Application
     * @return Application 类的实例
     */
    public static Context getApplication() {
        return context;
    }

    public static void runOnUIThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static void runOnNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

}
