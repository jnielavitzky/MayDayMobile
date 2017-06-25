package jnielavitzky.itba.com.maydaymobile;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by ioninielavitzky on 6/25/17.
 */

public class Util {
    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, ServerJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least //TODO: Aca meter el lapso de cuato esperar entre cada check.
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
