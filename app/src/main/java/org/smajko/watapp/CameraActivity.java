package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;



public class CameraActivity extends Activity {
    boolean firstText = true;
    Intent intent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void yesClicked(View view){
        if (firstText) {
            TextView tv = (TextView) findViewById(R.id.question);
            tv.setText(R.string.cameraYes);
            tv.setGravity(Gravity.CENTER);
            firstText = false;
        } else {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void noClicked(View view){
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
