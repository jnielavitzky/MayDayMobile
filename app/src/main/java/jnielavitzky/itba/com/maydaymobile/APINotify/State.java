package jnielavitzky.itba.com.maydaymobile.APINotify;

import jnielavitzky.itba.com.maydaymobile.API.Meta;

/**
 * Created by Cappa on 25/6/2017.
 */

public class State {

    private Meta meta;
    private Status status;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
