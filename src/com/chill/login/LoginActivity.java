package com.chill.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chill.ParseServiceAccessor;
import com.chill.R;
import com.chill.main.MainActivity;
import com.chill.model.remote.User;
import com.chill.util.TextValidator;
import com.chill.views.contracts.ServiceAccessor;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import lombok.Data;

public class LoginActivity extends Activity {
    private Button mLoginButton;
    private Button mRegisterButton;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private ServiceAccessor mServiceAccessor;
    private TextValidator mTextValidator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.button_login);
        mRegisterButton = (Button) findViewById(R.id.button_register);

        mUsernameEditText = (EditText) findViewById(R.id.edit_text_username);
        mPasswordEditText = (EditText) findViewById(R.id.edit_text_password);

        mServiceAccessor = new ParseServiceAccessor();

        if (mServiceAccessor.getCurrentUser() != null) {
            navigateToMainActivity();
        } else {
            mTextValidator = new TextValidator();
            initialize();
        }
    }

    public void initialize() {
        mUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mUsernameEditText.setHint("");
            }
        });
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPasswordEditText.setHint("");
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffButtons();
                new LoginTask().execute();
            }
        });
        //Friend view buttons
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffButtons();
                new RegisterTask().execute();
            }
        });
    }

    @Data
    private class ServiceAccessorResult{
        private final boolean success;
        private final String message;
    }

    private class LoginTask extends AsyncTask<Void, Void, ServiceAccessorResult> {
        @Override
        protected ServiceAccessorResult doInBackground(Void... params) {
            if (!mTextValidator.isValidEditText(mUsernameEditText) ||
                    !mTextValidator.isValidEditText(mPasswordEditText)) {
                return new ServiceAccessorResult(false, "Please enter a username and password");
            }

            final String username = mUsernameEditText.getText().toString();
            final String password = mPasswordEditText.getText().toString();

            if (mServiceAccessor.login(username, password)) {
                return new ServiceAccessorResult(true, "");
            }
            return new ServiceAccessorResult(false, "Username or password was incorrect.");
        }

        @Override
        protected void onPostExecute(final ServiceAccessorResult result) {
            super.onPostExecute(result);
            processResult(result);
        }
    }

    private class RegisterTask extends AsyncTask<Void, Void, ServiceAccessorResult> {
        @Override
        protected ServiceAccessorResult doInBackground(Void... params) {
            if (!mTextValidator.isValidEditText(mUsernameEditText) ||
                    !mTextValidator.isValidEditText(mPasswordEditText)) {
                return new ServiceAccessorResult(false, "Please enter a username and password");
            }

            final String username = mUsernameEditText.getText().toString();
            final String password = mPasswordEditText.getText().toString();

            if (mServiceAccessor.register(username, password)) {
                return new ServiceAccessorResult(true, "");
            }
            return new ServiceAccessorResult(false, "Username already exists.");
        }

        @Override
        protected void onPostExecute(final ServiceAccessorResult result) {
            super.onPostExecute(result);
            processResult(result);
        }
    }

    private void processResult (final ServiceAccessorResult result){
        if (result.isSuccess()){
            navigateToMainActivity();
        } else {
            turnOnButtons();
            toast(result.getMessage());
        }
    }

    private void navigateToMainActivity() {
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        ParsePush.subscribeInBackground(User.getCurrentUser().getUsername(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println("com.parse.push:: Exception ocurred while subscribing to Parse channel - " + e.getMessage());
                    e.printStackTrace();
                } else {
                    System.out.println("com.parse.push:: Parse channel subscribed successfully");
                }
            }
        });
        finish();
    }

    private void toast (final String message){
        final Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void turnOffButtons() {
        mLoginButton.setClickable(false);
        mRegisterButton.setClickable(false);
    }

    private void turnOnButtons(){
        mLoginButton.setClickable(true);
        mRegisterButton.setClickable(true);
    }
}