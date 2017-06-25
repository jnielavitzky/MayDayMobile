package jnielavitzky.itba.com.maydaymobile;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ioninielavitzky on 6/25/17.
 */

public class ServerJobService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("ServerChecker", "onCreate: start!");
//        Intent service = new Intent(getApplicationContext(), ServerChecker.class);
//        getApplicationContext().startService(service);

        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
