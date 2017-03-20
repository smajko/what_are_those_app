package org.smajko.watapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Uri fileUri; // file URI to store image/video
	private String outputFilePath;

	static final int CAMERA_RESULT_CODE = 1;
	static final int INFO_RESULT_CODE = 2;
	static final int SYMPTOM_RESULT_CODE = 3;

	/********************************
	 * 	  Params for json request
	 ********************************/
	String symptoms;
	String gender;
	int age;
	int days;
	boolean reoccurring;

	/********************************
	 * Storage Permission (for API 23+)
	 ********************************/
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
	};

	/*********************************
	 * Checks if the app has permission to write to device storage
	 * If the app does not has permission then the user will be prompted to grant permissions
	 *********************************/
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					activity,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			);
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		verifyStoragePermissions(this);

		Intent intent = new Intent(MainActivity.this, IntroActivity.class);
		startActivity(intent);

		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Set the user interface elements
		final Button btnCameraActivity = (Button) findViewById(R.id.btnCameraActivity);
		btnCameraActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CropActivity.class);
				startActivityForResult(intent, CAMERA_RESULT_CODE);
			}
		});

		final Button btnAgeActivity = (Button) findViewById(R.id.btnAgeActivity);
		btnAgeActivity.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AgeActivity.class);
				startActivityForResult(intent, INFO_RESULT_CODE);
			}
		});

		final Button btnSymptomActivity = (Button) findViewById(R.id.btnSymptomActivity);
		btnSymptomActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SymptomActivity.class);
				startActivityForResult(intent, SYMPTOM_RESULT_CODE);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//setContentView(R.layout.activity_main);
		switch (requestCode) {
			case CAMERA_RESULT_CODE:
				if (resultCode == RESULT_OK) {
					final CheckBox cb1 = (CheckBox) findViewById(R.id.cbCameraActivity);
					final TextView tv1 = (TextView) findViewById(R.id.tvCameraActivity);
					cb1.setChecked(true);
					tv1.setTextColor(Color.parseColor("#40E42F"));
				}
				break;
			case INFO_RESULT_CODE:
				if (resultCode == RESULT_OK) {
					final CheckBox cb2 = (CheckBox) findViewById(R.id.cbAgeActivity);
					final TextView tv2 = (TextView) findViewById(R.id.tvAgeActivity);
					cb2.setChecked(true);
					tv2.setTextColor(Color.parseColor("#40E42F"));

					Intent extras = this.getIntent();
					gender = extras.getStringExtra("gender");
					age = extras.getIntExtra("age",0);
					days = extras.getIntExtra("days",0);
					reoccurring = extras.getBooleanExtra("reoccurring",true);
				}
				break;
			case SYMPTOM_RESULT_CODE:
				if (resultCode == RESULT_OK) {
					final CheckBox cb3 = (CheckBox) findViewById(R.id.cbSymptomActivity);
					final TextView tv3 = (TextView) findViewById(R.id.tvSymptomActivity);
					cb3.setChecked(true);
					tv3.setTextColor(Color.parseColor("#40E42F"));

					Intent extras = this.getIntent();
					symptoms = extras.getStringExtra("symptoms");
				}
				break;
		}
	}
}