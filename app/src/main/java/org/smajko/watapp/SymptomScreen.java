package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


public class SymptomScreen extends Activity {
    CheckBox cb;
    CheckBox[] sens = new CheckBox[7];
    CheckBox[] cols = new CheckBox[6];
    CheckBox[] oths = new CheckBox[19];

    CheckBox[][] checkboxes = {sens, cols, oths};

    String sensations[] = {"pain", "itchiness", "numbness", "sore", "sensitive", "burning", "tingling"};
    String colors[] = {"redness", "brown", "fleshy", "white", "pink", "tan"};
    String misc[] = {"pus", "fever", "pimples", "whiteheads", "blackheads", "oval-shaped", "scabs", "mole", "lesion",
            "irregular-shape", "grainy", "swelling", "scales", "sores", "difficulty breathing", "wheezing", "blister", "sore throat", "bumps"};

    String[][] strings = {sensations, colors, misc};

    String acne[] = {"whiteheads", "blackheads", "redness", "bumps", "pimples", "pain", "pus"};
    String hives[] = {"wheezing", "difficulty breathing", "swelling", "redness", "oval-shaped", "itchiness"};
    String shingles[] = {"burning", "tingling", "numbness", "sensitive", "itchiness"};
    String chickenpox[] = {"redness", "itchiness", "bumps", "scabs", "fever"};
    String skincancer[] = {"brown", "mole", "lesion", "irregular-shape"};
    String coldsore[] = {"sores", "fever", "swelling", "redness"};
    String wart[] = {"grainy", "fleshy", "white", "pink", "tan"};
    String poisonivyrash[] = {"redness", "itchiness", "swelling", "blister"};
    String scabies[] = {"itchiness", "blister", "scales", "sores"};

    String[][] conditions = {acne, hives, shingles, chickenpox, skincancer, coldsore, wart, poisonivyrash, scabies};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        //Programmatically set style due to dynamic layout/instantiation of symptom checkboxes
        final LinearLayout sensationsLayout = (LinearLayout) findViewById(R.id.sensations);
        final LinearLayout colorsLayout = (LinearLayout) findViewById(R.id.colors);
        final LinearLayout othersLayout = (LinearLayout) findViewById(R.id.others);

        LinearLayout[] layouts = {sensationsLayout, colorsLayout, othersLayout};

        final Button finish = (Button) findViewById(R.id.btnDone);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });

        for (int i = 0; i < checkboxes.length; i++)
        {
            for (int j = 0; j < checkboxes[i].length; j++)
            {
                cb = new CheckBox(this);
                cb.setText(strings[i][j]);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        // TODO Auto-generated method stub
                        if (isChecked) {
                            System.out.println("Checked");
                        } else {
                            System.out.println("Un-Checked");
                        }
                    }
                });
                checkboxes[i][j] = cb;
                layouts[i].addView(checkboxes[i][j]);
            }
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