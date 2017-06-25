//package jnielavitzky.itba.com.maydaymobile.API;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.util.LogPrinter;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Map;
//
//import jnielavitzky.itba.com.maydaymobile.OfertasActivity;
//
///**
// * Created by Cappa on 24/6/2017.
// */
//
//public class API extends AsyncTask<Void, Void, Offer> {
//
//    @Override
//    protected Offer doInBackground(Void... voids) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Offer offers = mapper.readValue(new URL("http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getlastminuteflightdeals&from=BUE"), Offer.class);
//            return offers;
//        } catch (IOException e) {
//            Log.d("Error API", "API not responding.");
//            Offer of = new Offer();
//            //TODO: AGREGAR UN OFFER DEFAULT POR SI HAY ERRORES EN LA API.
//            return of;
//        }
//    }
//
//    protected void onPreExecute() {
//        // progressBar.setVisibility(View.VISIBLE);
//        //
//        // responseView.setText("");
//    }
//
//    @Override
//    protected void onPostExecute(Offer offer) {
//        super.onPostExecute(offer);
//        OfertasActivity.myOffers = offer;
//    }
//}
