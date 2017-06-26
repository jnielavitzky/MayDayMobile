package jnielavitzky.itba.com.maydaymobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dexafree.materialList.card.CardLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static jnielavitzky.itba.com.maydaymobile.MainActivity.TAG;

/**
 * Created by ioninielavitzky on 6/23/17.
 */

public class MisVuelosActivity extends Fragment {

    private static final int NO_CONNECTION_ERROR = 1;
    private static final int ITBA_SERVER_ERROR = 2;
    private static final int TIMEOUT = 3;
    private String lastPressedCard;

    ProgressDialog pd;

    private View view;

    private int askedTickets = 0;


    public MisVuelosActivity() {}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
//            Intent refresh = new Intent(this, MainActivity.class);
//            startActivity(refresh);
            draw();
            Log.d(TAG, "onActivityResult: TEFDSXAGFDSEGF");

        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.mis_vuelos_fragment, container, false);
        this.view = view;




        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddFlightActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.mis_vuelos));


        draw();
        return view;
    }

    private void draw(){
        TextView no_vuelos = (TextView) view.findViewById(R.id.no_vuelos);

        TableLayout cardList = (TableLayout)view.findViewById(R.id.material_listview);
        cardList.removeAllViews();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
//        sharedPref.edit().clear().commit();
        Map<String, ?> allEntries = sharedPref.getAll();

        askedTickets = allEntries.size();
        Log.d(TAG, "onCreateView: ticketsL   aoafyhkjahf" + askedTickets);
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String data = entry.getKey();
            String airline = data.substring(0, 2);
            String flight = data.substring(2);

            new JsonTask().execute("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id=" + airline + "&flight_number=" + flight);
        }


        if (askedTickets != 0) {
            if (no_vuelos != null)
                no_vuelos.setVisibility(View.GONE);
            pd = new ProgressDialog(getActivity());
            pd.setMessage(getString(R.string.espere_por_favor));
            pd.setCancelable(false);
            pd.show();


            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeout();
                }
            }, 6000L);
        }
    }
    
    private void timeout(){
        Log.d(TAG, "timeout: timeout");
        if (askedTickets != 0){
            error(TIMEOUT);
        }
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.card_optiones, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete: {
                        removeCard(lastPressedCard);
                        break;
                    }
                }

                return false;
            }
        });
        popup.show();
    }

    private void removeCard(String card_id) {

        TableLayout cardList = (TableLayout)getView().findViewById(R.id.material_listview);

        for (int i = 0; i < cardList.getChildCount(); i++) {
            final View child = cardList.getChildAt(i);
            if (child instanceof CardLayout && child.getTag().equals(card_id)) {
                cardList.removeView(child);
                SharedPreferences sharedPref = getActivity().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
                sharedPref.edit().remove(card_id).commit();
            }
        }
    }

    private void fillData(LinearLayout card, JSONObject data, String status_code) throws JSONException {

        TextView desde = (TextView)card.findViewById(R.id.from);
        desde.setText(data.getString("desde"));

        TextView hasta = (TextView)card.findViewById(R.id.to);
        hasta.setText(data.getString("hasta"));

        TextView estado = (TextView)card.findViewById(R.id.estado);
        estado.setText(data.getString("estado"));

        TextView info_from = (TextView)card.findViewById(R.id.info_from);
        info_from.setText(data.getString("info_from"));

        TextView salida_time_from = (TextView)card.findViewById(R.id.salida_time_from);
        salida_time_from.setText(data.getString("salida_time_from"));

        TextView terminal_num_from = (TextView)card.findViewById(R.id.terminal_num_from);
        terminal_num_from.setText(data.getString("terminal_num_from"));

        TextView gate_num_from = (TextView)card.findViewById(R.id.gate_num_from);
        gate_num_from.setText(data.getString("gate_num_from"));

        TextView despegue_program_time = (TextView)card.findViewById(R.id.despegue_program_time);
        despegue_program_time.setText(data.getString("despegue_program_time"));

        TextView info_to = (TextView)card.findViewById(R.id.info_to);
        info_to.setText(data.getString("info_to"));

        TextView salida_time_to = (TextView)card.findViewById(R.id.salida_time_to);
        salida_time_to.setText(data.getString("salida_time_to"));

        TextView terminal_num_to = (TextView)card.findViewById(R.id.terminal_num_to);
        terminal_num_to.setText(data.getString("terminal_num_to"));

        TextView gate_num_to = (TextView)card.findViewById(R.id.gate_num_to);
        gate_num_to.setText(data.getString("gate_num_to"));

        TextView aterrizaje_program_time = (TextView)card.findViewById(R.id.aterrizaje_program_time);
        aterrizaje_program_time.setText(data.getString("aterrizaje_program_time"));

        if (status_code.equals("C") || status_code.equals("R")) {
            salida_time_from.setTextColor(Color.RED);
            terminal_num_from.setTextColor(Color.RED);
            gate_num_from.setTextColor(Color.RED);

            salida_time_to.setTextColor(Color.RED);
            terminal_num_to.setTextColor(Color.RED);
            gate_num_to.setTextColor(Color.RED);
        }
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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



        if (data.has("error")){
            JSONObject error = data.getJSONObject("error");
            int code = error.getInt("code");
            if (code != 136)
                error(error.getInt("code"));
            else
                askedTickets--;
            return;
        }


        JSONObject status = data.getJSONObject("status");
        if (status == null){
            error(ITBA_SERVER_ERROR);
            return;
        }

        String status_code = status.getString("status");
        String status_string = getStatusString(status_code);

        String number = status.getString("number");


        JSONObject departure = status.getJSONObject("departure");
        JSONObject departure_airport = departure.getJSONObject("airport");
        String departure_airport_id = departure_airport.getString("id");
        String departure_terminal = departure_airport.getString("terminal");
        String departure_gate = departure_airport.getString("gate");
        String departure_airport_desc = simplify(departure_airport.getString("description"));

        String departure_time = departure.getString("scheduled_time");
        String departure_date_formated = getDate(departure_time, "EE MMMM, yyyy");
        String departure_time_formated = getTime(departure_time, "H:mm a");

        String departure_scheduled_time = departure.getString("scheduled_time");
        String departure_scheduled_time_formated = getTime(departure_scheduled_time, "H:mm a");

        if (departure_terminal == null || departure_terminal.equals("null"))
            departure_terminal = "-";

        if (departure_gate == null || departure_gate.equals("null"))
            departure_gate = "-";




        JSONObject arrival = status.getJSONObject("arrival");
        JSONObject arrival_airport = arrival.getJSONObject("airport");
        String arrival_airport_id = arrival_airport.getString("id");
        String arrival_terminal = arrival_airport.getString("terminal");
        String arrival_gate = arrival_airport.getString("gate");
        String arrival_airport_desc = simplify(arrival_airport.getString("description"));

        String arrival_time = arrival.getString("scheduled_time");
        String arrival_date_formated = getDate(arrival_time, "EE MMMM, yyyy");
        String arrival_time_formated = getTime(arrival_time, "H:mm a");


        String arrival_scheduled_time = arrival.getString("scheduled_time");
        String arrival_scheduled_time_formated = getTime(arrival_scheduled_time, "H:mm a");


        if (arrival_terminal == null || arrival_terminal.equals("null"))
            arrival_terminal = "-";

        if (arrival_gate == null || arrival_gate.equals("null"))
            arrival_gate = "-";





        JSONObject status_data = new JSONObject();
        status_data.put("desde", departure_airport_id);
        status_data.put("hasta", arrival_airport_id);
        status_data.put("estado", " " + status_string);
        status_data.put("info_from", departure_airport_desc + " • " + departure_date_formated);
        status_data.put("salida_time_from", departure_time_formated);
        status_data.put("terminal_num_from", departure_terminal);
        status_data.put("gate_num_from", departure_gate);
        status_data.put("despegue_program_time", departure_scheduled_time_formated);
        status_data.put("info_to", arrival_airport_desc + " • " + arrival_date_formated);
        status_data.put("salida_time_to", arrival_time_formated);
        status_data.put("terminal_num_to", arrival_terminal);
        status_data.put("gate_num_to", arrival_gate);
        status_data.put("aterrizaje_program_time", arrival_scheduled_time_formated);




        TableLayout cardList = (TableLayout)view.findViewById(R.id.material_listview);
        LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) infl.inflate(R.layout.card_template, null);

        fillData(layout, status_data, status_code);



        ImageButton b = (ImageButton) layout.findViewById(R.id.card_options);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPressedCard = (String)v.getTag();
                showPopupMenu(v);
            }
        });


        layout.setTag(number);
        b.setTag(number);


        cardList.addView(layout);

        askedTickets--;
        if (askedTickets == 0){
            if (pd.isShowing()){
                pd.dismiss();
            }
        }

    }

    private String getDate(String string, String new_format) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));
        Date d = format.parse(string);

        DateFormat newformat = new SimpleDateFormat(new_format, new Locale("es", "ES"));
        return newformat.format(d);
    }

    private String getTime(String string, String new_format) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));
        Date d = format.parse(string);

        DateFormat newformat = new SimpleDateFormat(new_format, Locale.US);
        return newformat.format(d);
    }

    private String simplify(String s) {
        int max = 30;
        if (s.length() > max) {
            s = s.substring(0, max-3) + "...";
        }
        return s;
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
                error(ITBA_SERVER_ERROR);
                return "";
            }
        }
    }

    AlertDialog alert11;
    private void error(int code) {
        askedTickets = 0;
        Log.d(TAG, "error: code: " + code);
        if (pd.isShowing()){
            pd.dismiss();
        }

        if (alert11 != null && alert11.isShowing())
            alert11.dismiss();

        Context context = getContext();
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


        alert11 = builder1.create();
        alert11.show();
    }

}
