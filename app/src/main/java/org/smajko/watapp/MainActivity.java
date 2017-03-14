package org.smajko.watapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {

	private Uri fileUri; // file URI to store image/video

	private ImageView imgPreview;
	private Button btnCapturePicture;
	private String outputFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// 1. We'll store photos in a file. We set the file name here.
		outputFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myimage.jpg";
		fileUri = Uri.fromFile(new File(outputFilePath));

		// 2. Set the user interface elements
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 3. This method is called to start camera.
				captureImage();
			}
		});

	}

	// Capturing Camera Image will launch camera app request image capture
	private void captureImage() {
		// 4. First check if we have a camera
		boolean deviceHasCamera = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA);

		// 5. Our phone has a camera. Lets start the native camera
		if (deviceHasCamera) {
			// 6.Create intent to take a picture
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			// 7. Tell the intent that we need the camera to store the photo in
			// our file defined earlier
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

			// 8. start the activity with the intent created above. When this
			// activity finishes, the method onActivityResult(...) is called
			startActivityForResult(intent, 1);

		} else {

			Log.i("CAMERA_APP", "No camera found");

		}

	}

	// 9. Receiving activity result method will be called after closing the
	// camera
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			Intent myIntent = new Intent(MainActivity.this, CropActivity.class);
			myIntent.putExtra("path",outputFilePath);
			MainActivity.this.startActivity(myIntent);
			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// 10. We've successfully captured the image. Now we'll show it in
			// the image view created earlier

			try {

				// 11. Now we need to ensure our photo is not unnecessarily
				// rotated
				Matrix matrix = new Matrix();
				ExifInterface ei = new ExifInterface(fileUri.getPath());

				// 12. Get orientation of the photograph
				int orientation = ei.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);
				// 13. In case image is rotated, we rotate it back
				switch (orientation) {

					case ExifInterface.ORIENTATION_ROTATE_90:
						matrix.postRotate(90);
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						matrix.postRotate(270);
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						matrix.postRotate(270);
						break;
					default:
						matrix.postRotate(90);
						break;
				}

				// 14. Now we get bitmap from the photograph and apply the rotation matrix above

				BitmapFactory.Options options = new BitmapFactory.Options();
				// down-sizing image as it can throw OutOfMemory Exception for
				// larger images
				options.inSampleSize = 2;

				Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
						options);

				Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);



				//15. Now display the image on the UI
				//imgPreview.setImageBitmap(rotatedBitmap);

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