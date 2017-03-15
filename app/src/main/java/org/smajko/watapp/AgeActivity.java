package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        final EditText ageEdit = (EditText) findViewById(R.id.age);

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

    public void done(View view){
        hideKeyboard(view);
        setResult();
    }

    void setResult() {
        EditText et = (EditText)findViewById(R.id.age);
        String checkEmpty = et.getText().toString();

        RadioGroup rg = (RadioGroup)findViewById(R.id.gender);
        RadioGroup rg2 = (RadioGroup)findViewById(R.id.reoccur);

        RadioButton rb1 = (RadioButton)findViewById(R.id.male);
        RadioButton rb2 = (RadioButton)findViewById(R.id.yes);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker stringPicker = (NumberPicker) findViewById(R.id.stringPicker);

        Intent intent = new Intent();

        if (checkEmpty.matches("")){
            Toast.makeText(AgeActivity.this, "Age not set!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        } else if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(AgeActivity.this, "Gender not selected!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        } else if (rg2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(AgeActivity.this, "Recurrence not selected!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        } else {
            //age
            intent.putExtra("age",Integer.parseInt(checkEmpty));

            //gender
            if (rb1.isChecked())
                intent.putExtra("gender","male");
            else
                intent.putExtra("gender","female");

            //reoccurring
            if (rb2.isChecked())
                intent.putExtra("reoccurring",true);
            else
                intent.putExtra("reoccurring",false);

            //days
            int days = numberPicker.getValue();
            int date = stringPicker.getValue();
            if (date == 2)
                days *= 28; //months selected
            else if (date == 3)
                days *= 365; //years selected
            intent.putExtra("days",days);

            setResult(RESULT_OK, intent);
        }

        finish();
    }
}