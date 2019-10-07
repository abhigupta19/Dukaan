package com.example.rashan.digifresh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by sahil on 21/1/17.
 */

public class VRIntro extends AppIntro implements VRIntroFragment.OnFragmentInteractionListener{
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(VRIntroFragment.newInstance(R.layout.intro_page1));
        addSlide(VRIntroFragment.newInstance(R.layout.intro_page2));
        addSlide(VRIntroFragment.newInstance(R.layout.intro_page3));
        addSlide(VRIntroFragment.newInstance(R.layout.intro_page4));

        setBarColor(getResources().getColor(R.color.Theme_elements));
        Intent ir = new Intent(VRIntro.this, ZLogin.class);
        startActivity(ir);
        finish();
    }

    @Override
    public void onSkipPressed() {
        Intent ir = new Intent(VRIntro.this, ZLogin.class);
        startActivity(ir);
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        Intent ir = new Intent(VRIntro.this, ZLogin.class);
        startActivity(ir);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
