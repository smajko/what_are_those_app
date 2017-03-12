package org.turntotech.photocapture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;


public class SymptomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        final LinearLayout ll = (LinearLayout) findViewById(R.id.linear_main);

        String[] symptoms = {"Itchiness", "Redness", "Bumps", "Tenderness", "Hotness", "Burning", "Fever", "Sneezing"};
        CheckBox[] cbs = new CheckBox[8];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
        for (int i = 0; i < 8; i++)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(symptoms[i]);
            cbs[i] = cb;

            ll.addView(cbs[i]);
        }

        Button btn = new Button(this);
        btn.setText("Get Results");
        btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.addView(btn);
    }


}