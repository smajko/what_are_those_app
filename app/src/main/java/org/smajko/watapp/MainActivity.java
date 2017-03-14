package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Set the user interface elements
		final Button btnCameraActivity = (Button) findViewById(R.id.btnCameraActivity);
		btnCameraActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, CropActivity.class));
			}
		});

		final Button btnAgeActivity = (Button) findViewById(R.id.btnAgeActivity);
		btnAgeActivity.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, AgeActivity.class));
			}
		});

		final Button btnSymptomActivity = (Button) findViewById(R.id.btnSymptomActivity);
		btnSymptomActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SymptomActivity.class));
			}
		});

		final Button btnResults = (Button) findViewById(R.id.btnResults);
		btnResults.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Endpoint not implemented",
						Toast.LENGTH_LONG).show();
			}
		});

	}
}