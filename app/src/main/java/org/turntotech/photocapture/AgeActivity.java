package org.turntotech.photocapture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
    }

    public void next(View view){
        startActivity(new Intent(this, SymptomActivity.class));
    }
}
