package org.smajko.watapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Uri fileUri; // file URI to store image/video
	private String outputFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// We'll store photos in a file. We set the file name here.
		outputFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myimage.jpg";
		fileUri = Uri.fromFile(new File(outputFilePath));

		// Set the user interface elements
		final Button btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// This method is called to start camera.
				captureImage();
			}
		});

	}

	// Capturing Camera Image will launch camera app request image capture
	private void captureImage() {
		// 4. First check if we have a camera
		boolean deviceHasCamera = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA);

		// Our phone has a camera. Lets start the native camera
		if (deviceHasCamera) {
			// Create intent to take a picture
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			// Tell the intent that we need the camera to store the photo in
			// our file defined earlier
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

			// start the activity with the intent created above. When this
			// activity finishes, the method onActivityResult(...) is called
			startActivityForResult(intent, 1);

		} else {

			Log.i("CAMERA_APP", "No camera found");

		}

	}
	// Receiving activity result method will be called after closing the
	// camera
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			try {
				Intent myIntent = new Intent(MainActivity.this, CropActivity.class);
				myIntent.putExtra("path",outputFilePath);
				MainActivity.this.startActivity(myIntent);

				final Button testButton = (Button) findViewById(R.id.btnNext);
				testButton.setText("Next");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void next(View view){
		startActivity(new Intent(this, AgeActivity.class));
	}

}