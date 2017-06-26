package jnielavitzky.itba.com.maydaymobile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;
import java.util.Random;

import jnielavitzky.itba.com.maydaymobile.APINotify.APINotify;
import jnielavitzky.itba.com.maydaymobile.APINotify.State;

import static jnielavitzky.itba.com.maydaymobile.MainActivity.TAG;

/**
 * Created by ioninielavitzky on 6/25/17.
 */

public class ServerJobService extends JobService {
    private static final String TAG = "SyncService";
    public static State state;


    private static final int NO_CONNECTION_ERROR = 1;
    private static final int ITBA_SERVER_ERROR = 2;
    private static final int TIMEOUT = 3;
    private static final int NO_FLIGHT = 4;

    @Override
    public boolean onStartJob(JobParameters params) {

//        Intent service = new Intent(getApplicationContext(), ServerChecker.class);
//        getApplicationContext().startService(service);

        SharedPreferences sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
//        sharedPref.edit().clear().commit();
        Map<String, ?> allEntries = sharedPref.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String data = entry.getKey();

            String airline = data.substring(0, 2);
            String flight = data.substring(2);

//            Log.d(TAG, "add_flight: " + airline + " " + flight);

            new JsonTask().execute("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id=" + airline + "&flight_number=" + flight);

        }



        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
//                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
//                    error(ITBA_SERVER_ERROR);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null || result.equals("")){
//                error(NO_CONNECTION_ERROR);
                return;
            }

//            Log.d(TAG, "onPostExecute: got: " + result);

            try {
                JSONObject jresult = new JSONObject(result);
                processData(jresult);
            } catch (JSONException | ParseException e) {
//                error(ITBA_SERVER_ERROR);
            }
        }
    }

    private void processData(JSONObject data) throws JSONException, ParseException {


        Log.d(TAG, "processData: Checking");
        if (data.has("error")) {
            JSONObject error = data.getJSONObject("error");
            int code = error.getInt("code");
            if (code == 136) {
//                error(NO_FLIGHT);
                return;
            }
//            error(ITBA_SERVER_ERROR);
            return;
        }


        JSONObject status = data.getJSONObject("status");
        if (status == null){
//            error(ITBA_SERVER_ERROR);
            return;
        }

        String status_code = status.getString("status");

        String number = status.getString("number");

        if (status_code.equals("C") || status_code.equals("R"))
            sendNotification(number, status_code);

    }

    private void sendNotification(String number, String code) {
        Log.d(TAG, "sendNotification: flight: " + number + " is " + getStatusString(code));



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.plane)
                        .setContentTitle(getString(R.string.alerta_vuelo))
                        .setContentText(getString(R.string.el_vuelo) + " " + number + " " + getString(R.string.esta) + " " + getStatusString(code));


        Random r = new Random();
        int mNotificationId = r.nextInt();
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

    private String getStatusString(String status_code) {
        switch (status_code) {
            case "S":
                return getString(R.string.programado);

            case "A":
                return getString(R.string.activo);

            case "R":
                return getString(R.string.desviado);

            case "L":
                return getString(R.string.aterrizado);

            case "C":
                return getString(R.string.cancelado);

            default: {
//                error(ITBA_SERVER_ERROR);
                return "";
            }
        }
    }

}
