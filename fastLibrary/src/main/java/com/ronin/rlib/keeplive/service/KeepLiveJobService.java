package com.ronin.rlib.keeplive.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by ronindong on 2017/2/24.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class KeepLiveJobService extends JobService implements IKeepLiveListener{

    @Override
    public void onCreate() {
        super.onCreate();
        startJobSheduler();
    }

    public void startJobSheduler() {
        try {
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName()
                    , KeepLiveJobService.class.getName()));
            builder.setPeriodic(5);
            builder.setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) this.getSystemService(
                    Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void startKeepLiveService(Context cx) {
        Intent intent = new Intent(cx, KeepLiveJobService.class);
        cx.startService(intent);
    }
}
