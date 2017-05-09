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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class SymptomScreen extends Activity {
    CheckBox cb;
    CheckBox[] sens = new CheckBox[7];
    CheckBox[] cols = new CheckBox[6];
    CheckBox[] oths = new CheckBox[18];
    CheckBox[][] checkboxes = {sens, cols, oths};

    String sensations[] = {"pain", "itchiness", "numbness", "sore", "sensitive", "burning", "tingling"};
    String colors[] = {"redness", "brown", "fleshy", "white", "pink", "tan"};
    String misc[] = {"pus", "fever", "pimples", "whiteheads", "blackheads", "oval-shaped", "scabs", "mole", "lesion",
            "irregular-shape", "grainy", "swelling", "scales", "sores", "difficulty breathing", "wheezing", "blister", "bumps"};
    String[][] strings = {sensations, colors, misc};

    String acne[] = new String[] {"whiteheads", "blackheads", "redness", "bumps", "pimples", "pain", "pus"};
    String hives[] = new String[] {"wheezing", "difficulty breathing", "swelling", "redness", "oval-shaped", "itchiness"};
    String shingles[] = new String[] {"burning", "tingling", "numbness", "sensitive", "itchiness"};
    String chickenpox[] = new String[] {"redness", "itchiness", "bumps", "scabs", "fever"};
    String skincancer[] = new String[] {"brown", "mole", "lesion", "irregular-shape"};
    String coldsore[] = new String[] {"sore", "fever", "swelling", "redness"};
    String wart[] = new String[] {"grainy", "fleshy", "white", "pink", "tan"};
    String poisonivyrash[] = new String[] {"redness", "itchiness", "swelling", "blister"};
    String scabies[] = new String[] {"itchiness", "blister", "scales", "sores", "redness"};
    String[][] conditions = {acne, hives, shingles, chickenpox, skincancer, coldsore, wart, poisonivyrash, scabies};
    String[] conditionNames = {"Acne", "Hives", "Shingles", "Chicken pox", "Melanoma", "Cold sore", "Warts", "Poison Ivy Rash", "Scabies"};

    ArrayList<Integer> conditionIndex = new ArrayList<Integer>();
    ArrayList<String> current = new ArrayList<String>();
    ArrayList<String> possibleConditions = new ArrayList<String>();
    int counter;

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

        for (int z = 0; z < 9; z++)
        {
            conditionIndex.add(z);
        }

        for (int i = 0; i < checkboxes.length; i++)
        {
            for (int j = 0; j < checkboxes[i].length; j++)
            {
                cb = new CheckBox(this);
                cb.setText(strings[i][j]);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        String str = buttonView.getText().toString();
                        // TODO Auto-generated method stub
                        if (isChecked) {
                            current.add(str);
                            counter++;
                        } else {
                            current.remove(str);
                            counter--;
                        }
                        update();
                    }
                });
                checkboxes[i][j] = cb;
                layouts[i].addView(checkboxes[i][j]);
            }
        }
    }

    private void update(){
        for (int i = 0 ; i < checkboxes.length; i++)
        {
            for (int j = 0 ; j < checkboxes[i].length; j++)
            {
                checkboxes[i][j].setEnabled(false);
            }
            for (int k = 0; k < conditions.length; k++)
            {
                if (validate(conditions[k]))
                {
                    enable(conditions[k]);
                    if (!conditionIndex.contains(k))
                    {
                        conditionIndex.add(k);
                    }
                }
                else
                {
                    if (conditionIndex.indexOf(k) != -1) {
                        conditionIndex.remove(conditionIndex.indexOf(k));
                    }
                }
            }
        }
    }

    private boolean validate(String[] arg)
    {
        boolean valid;
        for (int i = 0; i < current.size(); i++)
        {
            valid = false;
            for (int j = 0; j < arg.length; j++)
            {
                if (arg[j].equals(current.get(i)))
                {
                    valid = true;
                }
            }
            if (!valid)
            {
                return false;
            }
        }
        return true;
    }

    private void enable(String[] arg)
    {
        for (int i = 0; i < arg.length; i++)
        {
            for (int j = 0; j < checkboxes.length; j++)
            {
                for (int k = 0; k < checkboxes[j].length; k++)
                {
                    if (arg[i].equals(checkboxes[j][k].getText().toString()))
                    {
                        checkboxes[j][k].setEnabled(true);
                    }
                }
            }
        }
    }

    private void setResult(){
        Intent intent = new Intent();
        if (counter > 0){
            for (int i = 0; i < conditionIndex.size(); i++)
            {
                possibleConditions.add(conditionNames[conditionIndex.get(i)]);
                System.out.println(conditionNames[conditionIndex.get(i)]);
            }
            intent.putStringArrayListExtra("conditions",(ArrayList<String>)possibleConditions);
            setResult(RESULT_OK, intent);
        } else {
            Toast.makeText(SymptomScreen.this, "No symptoms selected!",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }
}