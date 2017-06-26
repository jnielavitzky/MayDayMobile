package jnielavitzky.itba.com.maydaymobile;

import android.content.Context;
import android.net.Uri;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.util.Log;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jnielavitzky.itba.com.maydaymobile.API.Deals;
import jnielavitzky.itba.com.maydaymobile.API.Offer;

import jnielavitzky.itba.com.maydaymobile.API.Offer;

//import jnielavitzky.itba.com.maydaymobile.API.Offer;

/**
 * Created by ioninielavitzky on 6/23/17.
 *
 * TODO: Usar: https://github.com/etsy/AndroidStaggeredGrid
 */

public class OfertasActivity extends Fragment {

    public static Offer myOffers;

    public OfertasActivity() {
    }

//    public static MisVuelosActivity newInstance(String param1, String param2) {
//        MisVuelosActivity fragment = new MisVuelosActivity();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.ofertas_fragment, container, false);

//        Button fab = (Button) view.findViewById(R.id.buttonTest);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Test", "onClick: click!");
//                Snackbar.make(view, "Hola boton!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.ofertas));

        TableLayout offersList = (TableLayout) view.findViewById(R.id.ofertas_listview);

        JSONObject data = new JSONObject();

        for(Deals deal : myOffers.getDeals()){

            LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout) infl.inflate(R.layout.ofertas_template, null);

            try{
                data.put("city", deal.getCity().getName());
                data.put("country", deal.getCity().getCountry().getName());
                data.put("currency", myOffers.getCurrency().getId());
                data.put("price", deal.getPrice().toString());
                fillData(layout,data);
            }catch(JSONException e){
                e.printStackTrace();
            }

            ImageButton b = (ImageButton) layout.findViewById(R.id.like);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create card
                    //add to mis_vuelos
                    //change color
                }
            });

//            try{
//                fillData(layout,data);
//            }catch (JSONException e){
//                e.printStackTrace();
//            }

            offersList.addView(layout);
        }

        return view;
    }

    public void fillData(LinearLayout offer, JSONObject data) throws JSONException{

        TextView city = (TextView)offer.findViewById(R.id.city);
        city.setText(data.getString("city"));

        TextView country = (TextView)offer.findViewById(R.id.country);
        country.setText(data.getString("country"));

        TextView currency = (TextView)offer.findViewById(R.id.currency);
        currency.setText(data.getString("currency"));

        TextView price = (TextView)offer.findViewById(R.id.price);
        price.setText(data.getString("price"));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}