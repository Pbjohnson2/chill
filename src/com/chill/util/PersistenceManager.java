package com.chill.util;

import android.content.Context;
import com.chill.model.local.ChillPreference;

/**
 * Created by Pierce on 10/26/2014.
 */
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
