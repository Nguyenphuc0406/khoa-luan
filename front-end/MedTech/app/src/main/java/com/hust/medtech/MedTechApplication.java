package com.hust.medtech;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.multidex.MultiDex;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MedTechApplication extends Application {

    private static Context mSelf;


    public static Activity currentActivity = null;

    private Activity mCurrentActivity;


    boolean isAppInBackground = true;
    public boolean appStopped = true;


    @Override
    public void onCreate() {
        super.onCreate();
        initSelf();
        setScreenOrientation();


    }


    private synchronized void initSelf() {
        mSelf = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


    public static Context self() {
        return mSelf;
    }


    public boolean isMyAppInBackGround() {
        return this.isAppInBackground;
    }


    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }


    public void setScreenOrientation() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                activity.setTitle(getResources().getString(R.string.app_name));
                setCurrentActivity(activity);

                currentActivity = activity;

                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            }


            @Override
            public void onActivityStarted(Activity activity) {
                setCurrentActivity(activity);

            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
                currentActivity = activity;
                isAppInBackground = false;
                appStopped = false;


            }

            @Override
            public void onActivityPaused(Activity activity) {
                clearReferences();
                isAppInBackground = true;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(final Activity activity) {
                boolean foreGround = false;
                try {
                    foreGround = new ForegroundCheckTask().execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (foreGround != true) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//
                        }
                    });
                }
                clearReferences();

            }


        });
    }

    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void clearReferences() {
        Activity currActivity = getCurrentActivity();
        if (this.equals(currActivity))
            setCurrentActivity(null);
    }


    public static Activity getCurrentAct() {
        return currentActivity;
    }


}
