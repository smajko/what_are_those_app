package org.turntotech.photocapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;

public class AgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        final EditText ageEdit = (EditText) findViewById(R.id.age);
        /*ageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                ageEdit.setCursorVisible(true);
            }
        });*/

        ageEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    ageEdit.setCursorVisible(false);
                } else {
                    showKeyboard(v);
                    ageEdit.setCursorVisible(true);
                }

            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(28);
        numberPicker.setMinValue(1);

        NumberPicker stringPicker = (NumberPicker) findViewById(R.id.stringPicker);
        stringPicker.setMaxValue(2);
        stringPicker.setMinValue(0);
        stringPicker.setDisplayedValues( new String[] { "Days", "Months", "Years" } );

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void next(View view){
        startActivity(new Intent(this, SymptomActivity.class));
    }
}
