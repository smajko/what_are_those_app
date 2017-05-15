package org.smajko.watapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroScreen extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("Welcome to WatApp", "Symptom checker made easy and intuitive", R.drawable.ic_screen1, Color.TRANSPARENT));
        addSlide(AppIntroFragment.newInstance("Having symptoms but don't know what they mean?", "Just give us some information, and we will try to give an accurate diagnosis", R.drawable.ic_screen1, Color.TRANSPARENT));
        addSlide(AppIntroFragment.newInstance("Enjoy!", "GET STARTED", R.drawable.ic_screen1, Color.TRANSPARENT));

        // Override bar/separator color.
        setBarColor(Color.parseColor("#000000"));
        setSeparatorColor(Color.parseColor("#000000"));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Disclaimer");
        builder.setMessage("By pressing Accept, you acknowledge and accept the following:\n\n" +
                "1) The information contained in this application is for entertainment purposes only.\n\n" +
                "2) WatApp is in no way affiliated with any medical authority.\n\n" +
                "3) WatApp is not to be used for factual diagnosis. Seek the advice of your physician or other qualified health care provider for actual diagnosis.\n\n" +
                "4) We are not liable in any way for misleading information.");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Accept",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Decline",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED,intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // prototype
    }
}