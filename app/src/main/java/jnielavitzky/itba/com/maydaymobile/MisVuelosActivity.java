package jnielavitzky.itba.com.maydaymobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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


import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ioninielavitzky on 6/23/17.
 */

public class MisVuelosActivity extends Fragment {

    private String lastPressedCard;

    public MisVuelosActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.mis_vuelos_fragment, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddFlightActivity.class);
                getActivity().startActivity(intent);
            }
        });





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

        for (int i = 0; i < 10; i++) { //TODO: Aca iterar sobre los vuelos favoritos.
            //Inflador
            LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout) infl.inflate(R.layout.card_template, null);

            ImageButton b = (ImageButton) layout.findViewById(R.id.card_options);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastPressedCard = (String)v.getTag();
                    showPopupMenu(v);
                }
            });


            //TODO: Aca poner el ID del vuelo, asi se puede seguirle la onda para eliminar.
            layout.setTag(i+"");
            b.setTag(i+"");

            try {
                data.put("desde", "AR" + i);
                fillData(layout, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cardList.addView(layout);

        }
        return view;
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
                //TODO: Eliminar de aca el vuelo favorito.
            }
        }
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
    }


    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
