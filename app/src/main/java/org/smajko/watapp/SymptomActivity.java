package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class SymptomActivity extends Activity {
    CheckBox[] cbs = new CheckBox[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


        //Programmatically set style due to dynamic layout/instantiation of symptom checkboxes
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linear_main);

        String[] symptoms = {"Itchiness", "Redness", "Bumps", "Tenderness", "Hotness", "Burning", "Fever", "Sneezing"};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
        for (int i = 0; i < 8; i++)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(symptoms[i]);
            cbs[i] = cb;

            ll.addView(cbs[i]);
        }

        Button btn = new Button(this);
        btn.setText("Done");
        btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult();
            }
        });
    }

    private void setResult(){
        boolean symptom = false;
        Intent intent = new Intent();
        for (int i = 0; i < 8; i++){
            if (cbs[i].isChecked()){
                symptom = true;
            }
        }

        if (symptom){
            setResult(RESULT_OK, intent);
        } else {
            Toast.makeText(SymptomActivity.this, "No symptoms selected!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }
}