package jnielavitzky.itba.com.maydaymobile.API.Review;

/**
 * Created by JuanmaAlonso on 25/6/17.
 */

public class Review {

    private Flight flight;
    private Rating rating;
    private boolean yes_recommend;
    private String comments;

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public boolean getYes_recommend() {
        return yes_recommend;
    }

    public void setYesRecommend(boolean yes_recommend) {
        this.yes_recommend = yes_recommend;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
