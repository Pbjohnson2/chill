package com.chill.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.chill.R;
import com.chill.main.MainActivity;
import com.chill.model.local.Chill;
import com.chill.model.local.ChillManager;
import com.chill.model.local.ChillPreference;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.local.chills.ChillDefinitionConstants;
import com.chill.util.ModelSerializer;
import com.chill.util.TextValidator;
import lombok.Data;

public class EditChillActivity extends Activity {
    private LinearLayout mEditChillLayout;
    private Button mSaveButton;
    private ImageButton mSelectChillTypeButton;

    private EditText mLocationEditText;
    private EditText mDateEditText;

    private ChillManager mChillManager;
    private TextValidator mTextValidator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chill);

        mEditChillLayout = (LinearLayout) findViewById(R.id.linear_layout_edit_chill);
        mLocationEditText = (EditText) findViewById(R.id.edit_text_location);
        mDateEditText = (EditText) findViewById(R.id.edit_text_date);

        mSelectChillTypeButton = (ImageButton) findViewById(R.id.button_select_chill_type);

        mSaveButton = (Button) findViewById(R.id.button_save);

        mChillManager = new ChillManager(EditChillActivity.this);
        mTextValidator = new TextValidator();

        initializeUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeUI();
    }

    private void initializeUI(){
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffButtons();
                new SaveTask().execute();
            }
        });
        mSelectChillTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(EditChillActivity.this, SelectChillActivity.class);
                startActivity(intent);
            }
        });
        initializeChillView();
    }

    private void initializeChillView() {
        final ChillPreference preference = mChillManager.getChillPreference();
        final ChillDefinition definition = mChillManager.getChillDefinition(preference.getChill().getId());
        setLayoutColor(mEditChillLayout, definition);
        mSelectChillTypeButton.setBackground(getResources().getDrawable(definition.getLayout()));
    }

    @Data
    private class SaveResult{
        private final boolean success;
        private final String message;
    }

    private class SaveTask extends AsyncTask<Void, Void, SaveResult> {
        @Override
        protected SaveResult doInBackground(Void... params) {
            if (!mTextValidator.isValidEditText(mLocationEditText) ||
                    !mTextValidator.isValidEditText(mDateEditText)) {
                return new SaveResult(false, "Please enter a location and time.");
            }

            final String location = mLocationEditText.getText().toString();
            final String date = mDateEditText.getText().toString();

            final ChillPreference preference = new ChillPreference(
                    new Chill(mChillManager.getChillPreference().getChill().getId()),
                    location,
                    date);
            mChillManager.saveChillPreferenceInBackground(preference);
            return new SaveResult(true, "");
        }

        @Override
        protected void onPostExecute(final SaveResult result) {
            super.onPostExecute(result);
            processResult(result);
        }
    }

    private void processResult (final SaveResult result){
        if (result.isSuccess()){
            navigateToMainActivity();
        } else {
            turnOnButtons();
            toast(result.getMessage());
        }
    }

    private void toast (final String message){
        final Toast toast = Toast.makeText(EditChillActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void navigateToMainActivity() {
        final Intent intent = new Intent(EditChillActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void turnOffButtons() {
        mSaveButton.setClickable(false);
    }

    private void turnOnButtons(){
        mSaveButton.setClickable(true);
    }

    private void setLayoutColor(final LinearLayout linearLayout, final ChillDefinition definition) {
        linearLayout.setBackgroundColor(0xff000000 + Integer.parseInt(Integer.toHexString(definition.getColor()), 16));
    }
}