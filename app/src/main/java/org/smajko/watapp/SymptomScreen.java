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

public class SymptomScreen extends Activity {
    CheckBox[] sens = new CheckBox[7];
    CheckBox[] cols = new CheckBox[6];
    CheckBox[] oths = new CheckBox[19];

    String sensations[] = {"pain", "itchiness", "numbness", "sore", "sensitive", "burning", "tingling"};
    String colors[] = {"redness", "brown", "fleshy", "white", "pink", "tan"};
    String misc[] = {"pus", "fever", "pimples", "whiteheads", "blackheads", "oval-shaped", "scabs", "mole", "lesion",
            "irregular-shape", "grainy", "swelling", "scales", "sores", "difficulty breathing", "wheezing", "blister", "sore throat", "bumps"};

    String acne[] = {"whiteheads", "blackheads", "redness", "bumps", "pimples", "pain", "pus"};
    String hives[] = {"wheezing", "difficulty breathing", "swelling", "redness", "oval-shaped", "itchiness"};
    String shingles[] = {"burning", "tingling", "numbness", "sensitive", "itchiness"};
    String chickenpox[] = {"redness", "itchiness", "bumps", "scabs", "fever"};
    String skincancer[] = {"brown", "mole", "lesion", "irregular-shape"};
    String coldsore[] = {"sores", "fever", "swelling", "redness"};
    String wart[] = {"grainy", "fleshy", "white", "pink", "tan"};
    String poisonivyrash[] = {"redness", "itchiness", "swelling", "blister"};
    String scabies[] = {"itchiness", "blister", "scales", "sores"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        //Programmatically set style due to dynamic layout/instantiation of symptom checkboxes
        final LinearLayout sensationsLayout = (LinearLayout) findViewById(R.id.sensations);
        final LinearLayout colorsLayout = (LinearLayout) findViewById(R.id.colors);
        final LinearLayout othersLayout = (LinearLayout) findViewById(R.id.others);

        final Button finish = (Button) findViewById(R.id.btnDone);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });

        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
        for (int i = 0; i < 7; i++)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(sensations[i]);
            sens[i] = cb;
            sensationsLayout.addView(sens[i]);
        }
        for (int i = 0; i < 6; i++)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(colors[i]);
            cols[i] = cb;
            colorsLayout.addView(cols[i]);
        }
        for (int i = 0; i < 19; i++)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(misc[i]);
            oths[i] = cb;
            othersLayout.addView(oths[i]);
        }
    }

    private void setResult(){
        boolean symptom = true;
        Intent intent = new Intent();
        /*for (int i = 0; i < 8; i++){
            if (cbs[i].isChecked()){
                symptom = true;
                sb += symptoms[i];
                sb += ",";
            }
        }*/
        if (symptom){
            //intent.putExtra("symptoms",sb);
            setResult(RESULT_OK, intent);
        } else {
            Toast.makeText(SymptomScreen.this, "No symptoms selected!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }
}