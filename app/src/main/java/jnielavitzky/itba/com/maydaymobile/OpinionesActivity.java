package jnielavitzky.itba.com.maydaymobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jnielavitzky.itba.com.maydaymobile.API.Review.ReviewPoster;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static jnielavitzky.itba.com.maydaymobile.MainActivity.TAG;


public class OpinionesActivity extends Fragment implements SearchView.OnQueryTextListener {


    String flight_id;

    SearchView sv;

    TableLayout opinions;

    ScrollView scroll;

    MenuItem item;

    boolean yes_recommend;

    int amabilidad, confort, relacion, puntualidad, programa, comida;

    Toast seis, ingrese, letras, numeros;

    ProgressDialog pd;

    private static final int SIN_METODO = 1;
    private static final int METODO_INVALIDO = 2;
    private static final int REVIEW_INVALIDO = 3;
    private static final int ERROR_DESCONOCIDO = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yes_recommend = true;
        amabilidad = comida = programa = puntualidad = confort = relacion = 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

        ToggleButton toggle = (ToggleButton) getView().findViewById(R.id.recToggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yes_recommend = true;
                } else {
                    yes_recommend = false;
                }
            }
        });

        Button button = (Button) getView().findViewById(R.id.listo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processReview(flight_id);
            }
        });

        RatingBar rb_amabilidad = (RatingBar) getView().findViewById(R.id.amabilidad_ratingBar);
        rb_amabilidad.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                amabilidad = (int) rating;
            }
        });

        RatingBar rb_comida = (RatingBar) getView().findViewById(R.id.comida_ratingBar);
        rb_comida.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                comida = (int) rating;
            }
        });

        RatingBar rb_puntualidad = (RatingBar) getView().findViewById(R.id.puntualidad_ratingBar);
        rb_puntualidad.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                puntualidad = (int) rating;
            }
        });

        RatingBar rb_programa = (RatingBar) getView().findViewById(R.id.programa_ratingBar);
        rb_programa.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                programa = (int) rating;
            }
        });

        RatingBar rb_confort = (RatingBar) getView().findViewById(R.id.confort_ratingBar);
        rb_confort.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                confort = (int) rating;
            }
        });

        RatingBar rb_relacion = (RatingBar) getView().findViewById(R.id.relacion_ratingBar);
        rb_relacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                relacion = (int) rating;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        item = menu.add("Search");
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

        opinions = (TableLayout) rootView.findViewById(R.id.review_scroll);

        LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) infl.inflate(R.layout.rating_template, null);

        scroll = (ScrollView) rootView.findViewById(R.id.scroll);

        scroll.setVisibility(View.INVISIBLE);

        opinions.addView(layout);

        opinions.setVisibility(View.INVISIBLE);

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (query.length() == 1 || query.length() == 2) {

            if (ingrese != null) {
                ingrese.cancel();
            }
            ingrese = Toast.makeText(getContext(), R.string.query_hint, Toast.LENGTH_SHORT);
            ingrese.show();

            return false;
        }

        String airline_code_1 = query.substring(0);

        if (!Character.isLetter(airline_code_1.charAt(0)) || Character.isSpaceChar(airline_code_1.charAt(0))) {

            if (letras != null) {
                letras.cancel();
            }
            letras = Toast.makeText(getContext(), R.string.error_codigo_aereo, Toast.LENGTH_SHORT);
            letras.show();

            return false;
        }

        String airline_code_2 = query.substring(1);

        if (!Character.isLetter(airline_code_2.charAt(0)) || Character.isSpaceChar(airline_code_2.charAt(0))) {

            if (letras != null) {
                letras.cancel();
            }
            letras = Toast.makeText(getContext(), R.string.error_codigo_aereo, Toast.LENGTH_SHORT);
            letras.show();

            return false;
        }

        String flight_number = query.substring(2);

        for (int i = 0; i < flight_number.length(); i++) {
            if (!Character.isDigit(flight_number.charAt(i))) {

                if (numeros != null) {
                    numeros.cancel();
                }
                numeros = Toast.makeText(getContext(), R.string.error_numero_vuelo, Toast.LENGTH_SHORT);
                numeros.show();

                return false;
            }
        }

        flight_id = query;

        TextView flightTitle = (TextView) getView().findViewById(R.id.estado_title);
        flightTitle.setText(getText(R.string.titulo_vuelo) + " " + query.toUpperCase());

        ToggleButton toggleButton = (ToggleButton) getView().findViewById(R.id.recToggle);
        toggleButton.setChecked(true);

        scroll.setVisibility(View.VISIBLE);

        opinions.setVisibility(View.VISIBLE);

        sv.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.length() > 6) {

            if (seis != null) {
                seis.cancel();
            }
            seis = Toast.makeText(getContext(), R.string.no_mas_6_caracs, Toast.LENGTH_SHORT);
            seis.show();

            sv.setQuery(newText.substring(0, 6), false);
            return false;
        }

        return true;
    }

    private static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void processReview(String query) {

        ReviewPoster rp = new ReviewPoster();

        EditText editText = (EditText) getView().findViewById(R.id.comentarios);
        String comentarios = editText.getText().toString();

        rp.post(getResources().getString(R.string.review_post_url), rp.bowlingJson(query.substring(0, 2).toUpperCase(), Integer.parseInt(query.substring(2)),
                amabilidad, comida, puntualidad, programa, confort, relacion, yes_recommend, comentarios), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.post_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseStr = response.body().string();
                    hideKeyboard(getContext());

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd = new ProgressDialog(getActivity());
                            pd.setMessage(getString(R.string.espere_por_favor));
                            pd.setCancelable(false);
                            pd.show();

                            processResponse(responseStr);
                            System.out.println(responseStr);
                        }
                    });

                } else {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), R.string.post_fracaso, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    private void processResponse(String responseStr) {
        JSONObject jresult;

        try {
            jresult = new JSONObject(responseStr);

            if (jresult.has("error")) {
                JSONObject error = jresult.getJSONObject("error");
                int code = error.getInt("code");

                switch (code) {
                    case 1: {
                        error(SIN_METODO);
                        break;
                    }
                    case 100: {
                        error(METODO_INVALIDO);
                        break;
                    }
                    case 122: {
                        error(REVIEW_INVALIDO);
                        break;
                    }
                    case 999: {
                        error(ERROR_DESCONOCIDO);
                        break;
                    }


                }
            } else {

                if (pd.isShowing()){
                    pd.dismiss();
                }

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), R.string.post_exito, Toast.LENGTH_LONG).show();
                        sv.clearFocus();
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void error(int code) {
        Log.d(TAG, "error: code: " + code);

        if (pd.isShowing()) {
            pd.dismiss();
        }

        Context context = getContext();
        String error_s = "";

        switch (code) {
            case SIN_METODO: {
                error_s = getString(R.string.SIN_METODO);
                break;
            }
            case METODO_INVALIDO: {
                error_s = getString(R.string.METODO_INVALIDO);
                break;
            }
            case REVIEW_INVALIDO: {
                error_s = getString(R.string.REVIEW_INVALIDO);
                break;
            }
            case ERROR_DESCONOCIDO:
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
        sv.clearFocus();

    }
}
