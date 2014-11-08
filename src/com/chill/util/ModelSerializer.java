package com.chill.util;

import com.google.gson.Gson;

public class ModelSerializer {
    private static final Gson mGson = new Gson();

    public ModelSerializer() {
    }

    public String serialize(final Object object) {
            return mGson.toJson(object);
    }

    public <T> T deserialize(String json, Class<T> classOfT){
        return mGson.fromJson(json, classOfT);
    }
}
