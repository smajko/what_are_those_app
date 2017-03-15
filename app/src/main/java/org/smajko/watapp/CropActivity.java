package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;


public class CropActivity extends Activity {
    private static final int GUIDELINES_ON_TOUCH = 1;
    private Uri fileUri; // file URI to store image/video
    private String outputFilePath;
    boolean cropped;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myimage.png";
        fileUri = Uri.fromFile(new File(outputFilePath));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crop);
        cropped = false;
        captureImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                // Initialize Views.
                final ToggleButton fixedAspectRatioToggleButton = (ToggleButton) findViewById(R.id.fixedAspectRatioToggle);
                final TextView aspectRatioXTextView = (TextView) findViewById(R.id.aspectRatioX);
                final SeekBar aspectRatioXSeekBar = (SeekBar) findViewById(R.id.aspectRatioXSeek);
                final TextView aspectRatioYTextView = (TextView) findViewById(R.id.aspectRatioY);
                final SeekBar aspectRatioYSeekBar = (SeekBar) findViewById(R.id.aspectRatioYSeek);
                final Spinner guidelinesSpinner = (Spinner) findViewById(R.id.showGuidelinesSpin);
                final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
                final ImageView croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
                final Button cropButton = (Button) findViewById(R.id.Button_crop);
                final Button doneButton = (Button) findViewById(R.id.Button_done);

                // Initializes fixedAspectRatio toggle button.
                fixedAspectRatioToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        cropImageView.setFixedAspectRatio(isChecked);
                        cropImageView.setAspectRatio(aspectRatioXSeekBar.getProgress(), aspectRatioYSeekBar.getProgress());
                        aspectRatioXSeekBar.setEnabled(isChecked);
                        aspectRatioYSeekBar.setEnabled(isChecked);
                    }
                });

                try {
                    Matrix matrix = new Matrix();
                    ExifInterface ei = new ExifInterface(outputFilePath);

                    // Get orientation of the photograph
                    int orientation = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                    // In case image is rotated, we rotate it back
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

                    // Now we get bitmap from the photograph and apply the rotation matrix above

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down-sizing image as it can throw OutOfMemory Exception for
                    // larger images
                    options.inSampleSize = 2;

                    Bitmap bitmap = BitmapFactory.decodeFile(outputFilePath,
                            options);

                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);


                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    cropImageView.setImageBitmap(rotatedBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                    Bitmap bitmap = BitmapFactory.decodeFile(outputFilePath);
                    cropImageView.setImageBitmap(bitmap);
                }

                // Set seek bars to be disabled until toggle button is checked.
                aspectRatioXSeekBar.setEnabled(false);
                aspectRatioYSeekBar.setEnabled(false);

                aspectRatioXTextView.setText(String.valueOf(aspectRatioXSeekBar.getProgress()));
                aspectRatioYTextView.setText(String.valueOf(aspectRatioXSeekBar.getProgress()));

                // Initialize aspect ratio X SeekBar.
                aspectRatioXSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar aspectRatioXSeekBar, int progress, boolean fromUser) {
                        if (progress < 1) {
                            aspectRatioXSeekBar.setProgress(1);
                        }
                        cropImageView.setAspectRatio(aspectRatioXSeekBar.getProgress(), aspectRatioYSeekBar.getProgress());
                        aspectRatioXTextView.setText(String.valueOf(aspectRatioXSeekBar.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Do nothing.
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Do nothing.
                    }
                });

                // Initialize aspect ratio Y SeekBar.
                aspectRatioYSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar aspectRatioYSeekBar, int progress, boolean fromUser) {
                        if (progress < 1) {
                            aspectRatioYSeekBar.setProgress(1);
                        }
                        cropImageView.setAspectRatio(aspectRatioXSeekBar.getProgress(), aspectRatioYSeekBar.getProgress());
                        aspectRatioYTextView.setText(String.valueOf(aspectRatioYSeekBar.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Do nothing.
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Do nothing.
                    }
                });

                // Set up the Guidelines Spinner.
                guidelinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        cropImageView.setGuidelines(i);
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Do nothing.
                    }
                });
                guidelinesSpinner.setSelection(GUIDELINES_ON_TOUCH);

                // Initialize the Crop button.
                cropButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bitmap croppedImage = cropImageView.getCroppedImage();
                        croppedImageView.setImageBitmap(croppedImage);
                        setPicture(croppedImage);
                    }
                });

                // Initialize the Done button.
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        if (cropped){
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
            Toast.makeText(CropActivity.this, "Picture not cropped!",
                    Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void setPicture(Bitmap image) {
        try {
            FileOutputStream out = new FileOutputStream(outputFilePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            cropped = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void captureImage() {
        //  First check if we have a camera
        boolean deviceHasCamera = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);

        // Our phone has a camera. Lets start the native camera
        if (deviceHasCamera) {
            // Create intent to take a picture
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Tell the intent that we need the camera to store the photo in
            // our file defined earlier
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the activity with the intent created above
            startActivityForResult(intent, 1);

        } else {
            Toast.makeText(CropActivity.this, "Camera not found!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}