package jnielavitzky.itba.com.maydaymobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONArray;
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

import static jnielavitzky.itba.com.maydaymobile.MainActivity.TAG;

/**
 * Created by ioninielavitzky on 6/24/17.
 */

public class AddFlightActivity extends AppCompatActivity{

    private static final int NO_FLIGHT = 4;
    EditText flight_num;
    Button add;
    ProgressDialog pd;

    private static final int NO_CONNECTION_ERROR = 1;
    private static final int ITBA_SERVER_ERROR = 2;
    private static final int TIMEOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_flight);

        setTitle(getString(R.string.agregar_vuelo));

        flight_num = (EditText) findViewById(R.id.flight_num);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditText yourEditText= (EditText) findViewById(R.id.flight_num);
                yourEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
                yourEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
            }
        }, 200);
        flight_num.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    add_flight();
                }
                return true;
            }
        });


        add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_flight();
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void add_flight() {

        String data = flight_num.getText().toString();

        String airline = data.substring(0, 2);
        String flight = data.substring(2);

        Log.d(TAG, "add_flight: " + airline + " " + flight);

        new JsonTask().execute("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id=" + airline + "&flight_number=" + flight);

        pd = new ProgressDialog(this);
        pd.setTitle(R.string.espere_por_favor);
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }







    private void error(int code) {
        Log.d(TAG, "error: code: " + code);
        if (pd.isShowing()){
            pd.dismiss();
        }

        Context context = this;
        String error_s = "";
        switch (code) {
            case 136:
            case ITBA_SERVER_ERROR:{
                error_s = getString(R.string.ITBA_SERVER_ERROR);
                break;
            }
            case NO_CONNECTION_ERROR:{
                error_s = getString(R.string.NO_CONNECTION_ERROR);
                break;
            }
            case TIMEOUT:{
                error_s = getString(R.string.TIMEOUT);
                break;
            }
            case NO_FLIGHT:{
                error_s = getString(R.string.no_flight);
                break;
            }
            case 999:
            default: {
                error_s = getString(R.string.UNKNOWN);
                break;
            }

        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(error_s);
        builder1.setTitle("ERROR");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
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
                    error(ITBA_SERVER_ERROR);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null || result.equals("")){
                error(NO_CONNECTION_ERROR);
                return;
            }

            Log.d(TAG, "onPostExecute: got: " + result);

            try {
                JSONObject jresult = new JSONObject(result);
                processData(jresult);
            } catch (JSONException | ParseException e) {
                error(ITBA_SERVER_ERROR);
            }
        }
    }

    private void processData(JSONObject data) throws JSONException, ParseException {


        if (pd.isShowing()){
            pd.dismiss();
        }

        if (data.has("error")) {
            JSONObject error = data.getJSONObject("error");
            int code = error.getInt("code");
            if (code == 136) {
                error(NO_FLIGHT);
                return;
            }
            error(ITBA_SERVER_ERROR);
            return;
        }



        SharedPreferences sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(flight_num.getText().toString(), flight_num.getText().toString());
        editor.commit();



        Intent i = new Intent();

        // Throw in some identifier
//        i.putExtra(1, 1);

        // Set the result with this data, and finish the activity
        setResult(1, i);
        finish();

    }

}
