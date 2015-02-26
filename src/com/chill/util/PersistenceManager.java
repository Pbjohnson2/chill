package com.chill.util;

import android.content.Context;
import com.chill.model.local.ChillPreference;

public class PersistenceManager {
    private static final String CHILL_PREFERENCE_KEY = "chillPreference";
    private final PreferenceStore mPreferenceStore;
    private final ModelSerializer mModelSerializer;

    public PersistenceManager (final Context context) {
        mPreferenceStore = new PreferenceStore(context);
        mModelSerializer = new ModelSerializer();
    }

    public void saveChillPreference(final ChillPreference chillPreference) {
        final String chillPreferenceSerialized = mModelSerializer.serialize(chillPreference);
        mPreferenceStore.put(CHILL_PREFERENCE_KEY, chillPreferenceSerialized);
    }

    public ChillPreference getChillPreference() {
        final String chillPreferenceSerialized = mPreferenceStore.get(CHILL_PREFERENCE_KEY);
        if(chillPreferenceSerialized.isEmpty()){
            return null;
        }
        return mModelSerializer.deserialize(chillPreferenceSerialized, ChillPreference.class);
    }
}
