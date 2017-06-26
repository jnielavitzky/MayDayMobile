package jnielavitzky.itba.com.maydaymobile;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import jnielavitzky.itba.com.maydaymobile.APINotify.APINotify;
import jnielavitzky.itba.com.maydaymobile.APINotify.State;

/**
 * Created by ioninielavitzky on 6/25/17.
 */

public class ServerJobService extends JobService {
    private static final String TAG = "SyncService";
    public static State state;

    @Override
    public boolean onStartJob(JobParameters params) {

//        Intent service = new Intent(getApplicationContext(), ServerChecker.class);
//        getApplicationContext().startService(service);

       //TODO:LE TENES QUE MANDAR UN AIRLINE ID Y UN  FLIGHT NUMBER.
       new APINotify("8R", 8700).execute();

       //Status va a tener estos valores: S (programado), A (activo), R (desviado), L(aterrizado) y C (cancelado)
        String status =  state.getStatus().getStatus();


        Log.d("ServerChecker", "onCreate: start! " + status);


        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
