package jnielavitzky.itba.com.maydaymobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ioninielavitzky on 6/23/17.
 */

public class MisVuelosActivity extends Fragment {

    public MisVuelosActivity() {
    }

    public static MisVuelosActivity newInstance(String param1, String param2) {
        MisVuelosActivity fragment = new MisVuelosActivity();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mis_vuelos_fragment, container, false);

        return view;

//        // Inflate the layout for this fragment
//        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.mis_vuelos));
//
//
//        TableLayout cardList = (TableLayout)view.findViewById(R.id.material_listview);
//
//        //Sample data
//        JSONObject data = new JSONObject();
//        try {
//            data.put("desde", "ARG");
//            data.put("hasta", "wMIA");
//            data.put("estado", "Aterrizado");
//            data.put("info_from", "Silvio Pettirossi • Lun, May 29 ");
//            data.put("salida_time_from", "12:34 AM");
//            data.put("terminal_num_from", "K2");
//            data.put("gate_num_from", "Q12");
//            data.put("despegue_program_time", "22:23 AM");
//
//            data.put("info_to", "Ministro Pistarini • Lun, May 29 ");
//            data.put("salida_time_to", "22:34 AM");
//            data.put("terminal_num_to", "A12");
//            data.put("gate_num_to", "B234");
//            data.put("aterrizaje_program_time", "02:33 AM");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < 10; i++) {
//            //Inflador
//            LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            LinearLayout layout = (LinearLayout) infl.inflate(R.layout.card_template, null);
//
//            try {
//                fillData(layout, data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            cardList.addView(layout);
//
//        }
//        return view;
    }


    private void fillData(LinearLayout card, JSONObject data) throws JSONException {

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
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.mis_vuelos));


        TableLayout cardList = (TableLayout)view.findViewById(R.id.material_listview);

        //Sample data
        JSONObject data = new JSONObject();
        try {
            data.put("desde", "ARG");
            data.put("hasta", "MIA");
            data.put("estado", "Aterrizado");
            data.put("info_from", "Silvio Pettirossi • Lun, May 29 ");
            data.put("salida_time_from", "12:34 AM");
            data.put("terminal_num_from", "K2");
            data.put("gate_num_from", "Q12");
            data.put("despegue_program_time", "22:23 AM");

            data.put("info_to", "Ministro Pistarini • Lun, May 29 ");
            data.put("salida_time_to", "22:34 AM");
            data.put("terminal_num_to", "A12");
            data.put("gate_num_to", "B234");
            data.put("aterrizaje_program_time", "02:33 AM");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            //Inflador
            LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout) infl.inflate(R.layout.card_template, null);

            try {
                fillData(layout, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cardList.addView(layout);

        }
        return view;
    }


//    private void fillData(LinearLayout card, JSONObject data) throws JSONException {
//
//        TextView desde = (TextView)card.findViewById(R.id.from);
//        desde.setText(data.getString("desde"));
//
//        TextView hasta = (TextView)card.findViewById(R.id.to);
//        hasta.setText(data.getString("hasta"));
//
//        TextView estado = (TextView)card.findViewById(R.id.estado);
//        estado.setText(data.getString("estado"));
//
//        TextView info_from = (TextView)card.findViewById(R.id.info_from);
//        info_from.setText(data.getString("info_from"));
//
//        TextView salida_time_from = (TextView)card.findViewById(R.id.salida_time_from);
//        salida_time_from.setText(data.getString("salida_time_from"));
//
//        TextView terminal_num_from = (TextView)card.findViewById(R.id.terminal_num_from);
//        terminal_num_from.setText(data.getString("terminal_num_from"));
//
//        TextView gate_num_from = (TextView)card.findViewById(R.id.gate_num_from);
//        gate_num_from.setText(data.getString("gate_num_from"));
//
//        TextView despegue_program_time = (TextView)card.findViewById(R.id.despegue_program_time);
//        despegue_program_time.setText(data.getString("despegue_program_time"));
//
//
//        TextView info_to = (TextView)card.findViewById(R.id.info_to);
//        info_to.setText(data.getString("info_to"));
//
//        TextView salida_time_to = (TextView)card.findViewById(R.id.salida_time_to);
//        salida_time_to.setText(data.getString("salida_time_to"));
//
//        TextView terminal_num_to = (TextView)card.findViewById(R.id.terminal_num_to);
//        terminal_num_to.setText(data.getString("terminal_num_to"));
//
//        TextView gate_num_to = (TextView)card.findViewById(R.id.gate_num_to);
//        gate_num_to.setText(data.getString("gate_num_to"));
//
//        TextView aterrizaje_program_time = (TextView)card.findViewById(R.id.aterrizaje_program_time);
//        aterrizaje_program_time.setText(data.getString("aterrizaje_program_time"));
//    }

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

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        draw(getView());
    }

//    private void draw(View view) {
//        takeOffCardList = (MaterialListView) view.findViewById(R.id.CardList);
//        takeOffCardList.setOnDismissCallback(new OnDismissCallback() {
//            @Override
//            public void onDismiss(Card card, int i) {
//
//            }
//        });
//        takeOffCardList.getLayoutManager().offsetChildrenVertical(30);
//        takeOffCardList.setOnDismissCallback(new OnDismissCallback() {
//            @Override
//            public void onDismiss(Card card, int i) {
//                //Toast.makeText(MainActivity.this,)
//            }
//        });
//
//
//        for (int i = 0; i < 10; i++) {
//            BigImageButtonsCard card = new BigImageButtonsCard(((MainActivity) getActivity()));
//
//            card.setTitle("GOla gotosd");
//            card.setDescription("ajshdgjhagsd"+"\n\n" + "Departure : "+"2873624"+"\t\t\t\t"+" Arrival : "+"4273864"+"\n\n"+"Duration : "+"238764");
//            card.setRightButtonText("ADf;adjflajshflkahsdflkjhasdf");
//
//
//            card.setOnRightButtonPressedListener(new OnButtonPressListener() {
//                @Override
//                public void onButtonPressedListener(View view, Card card) {
//
//                }
//            });
//            card.setOnLeftButtonPressedListener(new OnButtonPressListener() {
//                @Override
//                public void onButtonPressedListener(View view, Card card) {
//
//                }
//            });
//
//
//            card.setLeftButtonText("alskdj ads,fjas,d as,dfbm, asdf");
//            //card.setRightButtonText(ob.getString("class"));
//            card.setDividerVisible(true);
//            card.setDrawable(R.drawable.back3);
//            takeOffCardList.add(card);
//
//        }
//    }

}
