package jnielavitzky.itba.com.maydaymobile.API.Review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by JuanmaAlonso on 25/6/17.
 */

public class ReviewPoster {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public String bowlingJson(String id, int fn, int fr, int fo, int p, int m, int c, int q, boolean r, String co) {

        ObjectMapper mapper = new ObjectMapper();

        Review review = new Review();

        Flight flight = new Flight();
        Airline airline = new Airline();
        Rating rating = new Rating();

        airline.setId(id);
        flight.setAirline(airline);
        flight.setNumber(fn);

        rating.setFood(fo);
        rating.setMileageprogram(m);
        rating.setComfort(c);
        rating.setFriendliness(fr);
        rating.setPunctuality(p);
        rating.setQualityPrice(q);

        review.setFlight(flight);
        review.setRating(rating);
        review.setYesRecommend(r);
        review.setComments(co);

        //Convert object to JSON string and pretty print
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInString);

        return jsonInString;
    }
}
