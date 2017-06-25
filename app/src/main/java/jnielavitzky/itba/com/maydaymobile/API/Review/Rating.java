package jnielavitzky.itba.com.maydaymobile.API.Review;

/**
 * Created by JuanmaAlonso on 25/6/17.
 */

class Rating {

    private int friendliness;
    private int food;
    private int punctuality;
    private int mileage_program;
    private int comfort;
    private int quality_price;

    public int getFriendliness() {
        return friendliness;
    }

    public void setFriendliness(int friendliness) {
        this.friendliness = friendliness;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(int punctuality) {
        this.punctuality = punctuality;
    }

    public int getMileage_program() {
        return mileage_program;
    }

    public void setMileageprogram(int mileage_program) {
        this.mileage_program = mileage_program;
    }

    public int getComfort() {
        return comfort;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public int getQuality_price() {
        return quality_price;
    }

    public void setQualityPrice(int quality_price) {
        this.quality_price = quality_price;
    }
}
