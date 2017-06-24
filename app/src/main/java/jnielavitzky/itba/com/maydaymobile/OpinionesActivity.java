package jnielavitzky.itba.com.maydaymobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lapism.searchview.SearchView;

public class OpinionesActivity extends Fragment {

    SearchView mSearchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.opinion_toolbar);

        DrawerLayout drawer = (DrawerLayout)  getView().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);


//        mSearchView = (SearchView) getView().findViewById(R.id.searchView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.opiniones));


        View rootView = inflater.inflate(R.layout.opiniones_fragment, container, false);

        return rootView;
    }

}
