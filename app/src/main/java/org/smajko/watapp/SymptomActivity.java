package org.smajko.watapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
//import android.widget.ScrollView;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


public class SymptomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


        /* Programmatically set style due to dynamic layout/instantiation of symptom checkboxes */
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linear_main);

        String[] symptoms = {"Itchiness", "Redness", "Bumps", "Tenderness", "Hotness", "Burning", "Fever", "Sneezing"};
        CheckBox[] cbs = new CheckBox[8];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
        for (int i = 0; i < 8; i++)
        {
            CheckBox cb = new CheckBox(this);
            //cb.setHeight(300);
            cb.setText(symptoms[i]);
            cbs[i] = cb;

            ll.addView(cbs[i]);
        }

        Button btn = new Button(this);
        btn.setText("Get Results");
        btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(SymptomActivity.this, "Endpoint not implemented",
                        Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, SymptomActivity.class));
            }
        });
    }

    //public void next(View view){
    //Toast.makeText(SymptomActivity.this, "Endpoint not implemented",
    //Toast.LENGTH_LONG).show();
    //startActivity(new Intent(this, SymptomActivity.class));
}