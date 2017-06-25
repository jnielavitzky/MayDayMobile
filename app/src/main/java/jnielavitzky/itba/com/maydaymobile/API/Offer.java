package jnielavitzky.itba.com.maydaymobile.API;

import java.util.ArrayList;

/**
 * Created by Cappa on 24/6/2017.
 */

public class Offer {

    private Meta meta;
    private Currency currency;
    private ArrayList<Deals> deals;

    public String getAlgo(){
        return getDeals().get(10).getPrice().toString();
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ArrayList<Deals> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<Deals> deals) {
        this.deals = deals;
    }
}