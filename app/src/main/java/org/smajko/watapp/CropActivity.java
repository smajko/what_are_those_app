package org.smajko.watapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
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
import android.widget.ToggleButton;

import com.edmodo.cropper.CropImageView;


public class CropActivity extends Activity {
    private static final int GUIDELINES_ON_TOUCH = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crop);

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
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        cropButton.setText("Crop");

        try {
            Matrix matrix = new Matrix();
            ExifInterface ei = new ExifInterface(path);

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

            Bitmap bitmap = BitmapFactory.decodeFile(path,
                    options);

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            //Bitmap bitmap = BitmapFactory.decodeFile(path);
            cropImageView.setImageBitmap(rotatedBitmap);


        } catch (Exception e) {
            e.printStackTrace();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
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
            }
        });

        // Initialize the Done button.
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CropActivity.this, AgeActivity.class));
            }
        });
    }
}
