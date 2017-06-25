package jnielavitzky.itba.com.maydaymobile.APINotify;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

import jnielavitzky.itba.com.maydaymobile.API.API;

public class APINotify extends AsyncTask<Void, Void, State>{

    @Override
    protected State doInBackground(Void... voids) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            State state;
            URL url = new URL("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id=8R&flight_number=8700");
            state = mapper.readValue(url,State.class);
            return state;
        } catch(IOException e){
            Log.d("Error API", "API not responding.");
            return new State();
        }

    }
}