package com.chill.util;

import android.widget.EditText;

public class TextValidator {

    public TextValidator() {

    }

    public boolean isValidEditText(final EditText editText) {
        if(editText == null){
            return false;
        }
        final String text = editText.getText().toString();
        if (text == null) {
            return false;
        }
        if(text.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isValidInput(final String text) {
        if (text == null) {
            return false;
        }
        if (text.isEmpty()) {
            return false;
        }
        return true;
    }
}
