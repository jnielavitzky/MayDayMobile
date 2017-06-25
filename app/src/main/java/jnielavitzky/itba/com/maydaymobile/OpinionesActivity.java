package jnielavitzky.itba.com.maydaymobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.IOException;

import jnielavitzky.itba.com.maydaymobile.API.Review.ReviewPoster;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class OpinionesActivity extends Fragment implements SearchView.OnQueryTextListener {


    String flight_number;

    SearchView sv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.logo);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sv = new SearchView(getActivity());
        sv.setQueryHint(getString(R.string.query_hint));
        sv.setOnQueryTextListener(this);
        item.setActionView(sv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.opiniones));

        View rootView = inflater.inflate(R.layout.opiniones_fragment, container, false);

        TableLayout opinions = (TableLayout) rootView.findViewById(R.id.review_scroll);

        LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) infl.inflate(R.layout.rating_template, null);

        opinions.addView(layout);

        opinions.setVisibility(View.INVISIBLE);

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (query.length() == 1 || query.length() == 2) {
            Toast.makeText(getContext(), R.string.query_hint, Toast.LENGTH_SHORT).show();
            return false;
        }

        String airline_code_1 = query.substring(0);

        if (!Character.isLetter(airline_code_1.charAt(0)) || Character.isSpaceChar(airline_code_1.charAt(0))) {
            Toast.makeText(getContext(), R.string.error_codigo_aereo, Toast.LENGTH_SHORT).show();
            return false;
        }

        String airline_code_2 = query.substring(1);

        if (!Character.isLetter(airline_code_2.charAt(0)) || Character.isSpaceChar(airline_code_2.charAt(0))) {
            Toast.makeText(getContext(), R.string.error_codigo_aereo, Toast.LENGTH_SHORT).show();
            return false;
        }

        String flight_number = query.substring(2);

        for (int i = 0; i < flight_number.length(); i++) {
            if (!Character.isDigit(flight_number.charAt(i))) {
                Toast.makeText(getContext(), R.string.error_numero_vuelo, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        ReviewPoster rp = new ReviewPoster();

        rp.post("http://hci.it.itba.edu.ar/v1/api/review.groovy?method=reviewairline", rp.bowlingJson("AA", 1490, 2, 2, 2, 2, 2, 2, true, "ANDROID!"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
                e.printStackTrace();
                System.out.println("ERRRROR");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    System.out.println(responseStr);
                } else {
                    System.out.println("NOT SUCCEESSFULLL");
                }
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 6) {
            Toast.makeText(getContext(), R.string.no_mas_6_caracs, Toast.LENGTH_SHORT).show();
            sv.setQuery(newText.substring(0, 6), false);
            return false;
        }

        return true;
    }

//    // These are the Contacts rows that we will retrieve.
//    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
//            ContactsContract.Contacts._ID,
//            ContactsContract.Contacts.DISPLAY_NAME,
//            ContactsContract.Contacts.CONTACT_STATUS,
//            ContactsContract.Contacts.CONTACT_PRESENCE,
//            ContactsContract.Contacts.PHOTO_ID,
//            ContactsContract.Contacts.LOOKUP_KEY,
//    };

//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        // This is called when a new Loader needs to be created.  This
//        // sample only has one Loader, so we don't care about the ID.
//        // First, pick the base URI to use depending on whether we are
//        // currently filtering.
//        Uri baseUri;
//        if (mCurFilter != null) {
//            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
//                    Uri.encode(mCurFilter));
//        } else {
//            baseUri = ContactsContract.Contacts.CONTENT_URI;
//        }
//
//        // Now create and return a CursorLoader that will take care of
//        // creating a Cursor for the data being displayed.
//        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
//                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
//                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
//        return new CursorLoader(getActivity(), baseUri,
//                CONTACTS_SUMMARY_PROJECTION, select, null,
//                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
//    }
//
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        // Swap the new cursor in.  (The framework will take care of closing the
//        // old cursor once we return.)
//        mAdapter.swapCursor(data);
//
//        // The list should now be shown.
//        if (isResumed()) {
////            setListShown(true);
//        } else {
////            setListShownNoAnimation(true);
//        }
//    }
//
//    public void onLoaderReset(Loader<Cursor> loader) {
//        // This is called when the last Cursor provided to onLoadFinished()
//        // above is about to be closed.  We need to make sure we are no
//        // longer using it.
//        mAdapter.swapCursor(null);
//    }
}
