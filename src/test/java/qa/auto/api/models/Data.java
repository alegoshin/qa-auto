package qa.auto.api.models;

import com.google.gson.Gson;

public abstract class Data {

    public String toString() {
        return new Gson().toJson(this);
    }
}
