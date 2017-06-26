package jnielavitzky.itba.com.maydaymobile;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ioninielavitzky on 6/25/17.
 */

public class Util {
    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {


        SharedPreferences sharedPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String delay_s = sharedPref.getString("delay", "5");

        int delay = Integer.parseInt(delay_s)*1000*60;

        Log.d("Sheduler", "scheduleJob: " + delay);


        ComponentName serviceComponent = new ComponentName(context, ServerJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(delay); // wait at least
        builder.setOverrideDeadline(3 * delay); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
