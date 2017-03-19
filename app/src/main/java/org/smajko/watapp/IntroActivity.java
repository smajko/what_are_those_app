package org.smajko.watapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide fragments here.
        //addSlide(firstFragment);
        //addSlide(secondFragment);
        //addSlide(thirdFragment);
        //addSlide(fourthFragment);

        addSlide(AppIntroFragment.newInstance("Welcome to WatApp", "Symptom checker made easy and intuitive", R.drawable.ic_launcher, Color.TRANSPARENT));
        addSlide(AppIntroFragment.newInstance("Having symptoms but don't know what they mean?", "Just give us some information, and we will try to give an accurate diagnosis", R.drawable.ic_launcher, Color.TRANSPARENT));
        addSlide(AppIntroFragment.newInstance("Enjoy!", "GET STARTED", R.drawable.ic_launcher, Color.TRANSPARENT));

        // Override bar/separator color.
        setBarColor(Color.parseColor("#000000"));
        setSeparatorColor(Color.parseColor("#000000"));
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
        // Do something when the slide changes.
    }
}