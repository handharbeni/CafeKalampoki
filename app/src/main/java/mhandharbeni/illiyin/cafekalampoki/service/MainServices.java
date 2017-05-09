package mhandharbeni.illiyin.cafekalampoki.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import mhandharbeni.illiyin.cafekalampoki.MainActivity;
import mhandharbeni.illiyin.cafekalampoki.service.intentService.BlogService;
import mhandharbeni.illiyin.cafekalampoki.service.intentService.MagzService;
import mhandharbeni.illiyin.cafekalampoki.service.intentService.MenuCafeService;
import mhandharbeni.illiyin.cafekalampoki.service.intentService.ToolService;

/**
 * Created by root on 03/05/17.
 */

public class MainServices extends Service{
    public static Boolean serviceRunning = false;
    public static final long NOTIFY_INTERVAL = 10 * 1000;
    private Handler handler = new Handler();
    private Timer timer = null;
    public static final String
            ACTION_LOCATION_BROADCAST = MainActivity.class.getName();

    @Override
    public void onCreate() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRunning = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceRunning = false;
        super.onDestroy();
    }
    class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    /*TOOL APPLICATION*/
                    Intent tool = new Intent(getApplicationContext(), ToolService.class);
                    startService(tool);
                    /*TOOL APPLICATION*/

                    /*BLOG SYNCING*/
                    Intent blog = new Intent(getApplicationContext(), BlogService.class);
                    startService(blog);
                    /*BLOG SYNCING*/

                    /*MAGZ SYNCING*/
                    Intent magz = new Intent(getApplicationContext(), MagzService.class);
                    startService(magz);
                    /*MAGZ SYNCING*/

                    /*MENU SYNCING*/
                    Intent menuCafe = new Intent(getApplicationContext(), MenuCafeService.class);
                    startService(menuCafe);
                    /*MENU SYNCING*/
                }
            });
        }
    }

}
