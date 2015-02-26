package com.chill.model.local;

import android.content.Context;
import android.view.View;
import com.chill.R;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.local.chills.ChillDefinitionConstants;
import com.chill.util.PersistenceManager;
import com.google.common.collect.ImmutableMap;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ChillManager {
    public static final String CHILL_PREFERENCE = "chillPreference";
    private static final String DEFAULT_LOCATION = "My House";
    private static final String DATE_FORMAT = "%s:%s";
    private final PersistenceManager mPersistanceManager;

    public ChillManager(final Context context){
        //TODO add resource files for definitions
        mPersistanceManager = new PersistenceManager(context);
    }

    public ChillPreference getChillPreference() {
        ChillPreference chillPreference = mPersistanceManager.getChillPreference();
        if (chillPreference != null) {
            return chillPreference;
        }
        chillPreference = getDefaultChillPreference();
        saveChillPreferenceInBackground(chillPreference);
        return chillPreference;
    }

    private ChillPreference getDefaultChillPreference() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new ChillPreference(
                    new Chill(ChillDefinitionConstants.COFFEE),
                    DEFAULT_LOCATION,
                    String.format(DATE_FORMAT, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE))
                );
    }

    public void saveChillPreferenceInBackground(final ChillPreference chillPreference) {
        new Thread() {
            @Override
            public void run() {
                mPersistanceManager.saveChillPreference(chillPreference);
            }
        }.start();
    }

    public void updateChillPreference(final ChillDefinition chillDefinition) {
        final ChillPreference oldChillPreference = getChillPreference();
        final ChillPreference newChillPreference = new ChillPreference(new Chill(chillDefinition.getId()), oldChillPreference.getLocation(), oldChillPreference.getDate());
        mPersistanceManager.saveChillPreference(newChillPreference);
    }

    public ChillDefinition getChillDefinition(final String key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        if (!ChillDefinition.DEFINITIONS.containsKey(key)) {
            return getChillDefinition(ChillDefinitionConstants.SEX);
        }
        return ChillDefinition.DEFINITIONS.get(key);
    }
}
