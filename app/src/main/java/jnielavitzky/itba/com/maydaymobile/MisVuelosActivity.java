package jnielavitzky.itba.com.maydaymobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.cards.BigImageButtonsCard;
import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.view.MaterialListView;

/**
 * Created by ioninielavitzky on 6/23/17.
 */

public class MisVuelosActivity extends Fragment {

    MaterialListView takeOffCardList;

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







        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.mis_vuelos));




        draw(view);

        return view;
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

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        draw(getView());
    }

    private void draw(View view) {
        takeOffCardList = (MaterialListView) view.findViewById(R.id.CardList);
        takeOffCardList.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int i) {

            }
        });
        takeOffCardList.getLayoutManager().offsetChildrenVertical(30);
        takeOffCardList.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int i) {
                //Toast.makeText(MainActivity.this,)
            }
        });


        for (int i = 0; i < 10; i++) {
            BigImageButtonsCard card = new BigImageButtonsCard(((MainActivity) getActivity()));

            card.setTitle("GOla gotosd");
            card.setDescription("ajshdgjhagsd"+"\n\n" + "Departure : "+"2873624"+"\t\t\t\t"+" Arrival : "+"4273864"+"\n\n"+"Duration : "+"238764");
            card.setRightButtonText("ADf;adjflajshflkahsdflkjhasdf");


            card.setOnRightButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {

                }
            });
            card.setOnLeftButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {

                }
            });


            card.setLeftButtonText("alskdj ads,fjas,d as,dfbm, asdf");
            //card.setRightButtonText(ob.getString("class"));
            card.setDividerVisible(true);
            card.setDrawable(R.drawable.back3);
            takeOffCardList.add(card);

        }
    }

}
