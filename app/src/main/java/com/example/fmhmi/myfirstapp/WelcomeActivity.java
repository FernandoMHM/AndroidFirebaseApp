//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by FMHMI on 5/15/2018.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static int BLINK_TIME_OUT = 4000;
    private static int SCROLLOUT_TIME_OUT = 7000;
    private static int ANIMATION_TIME_OUT = 8000;

    private ImageView welcomeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeImage = (ImageView) findViewById(R.id.splash_image_view);

        scrollDownAnimation();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                blinkingAnimation();

            }
        }, BLINK_TIME_OUT);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                scrollOutAnimation();

            }
        }, SCROLLOUT_TIME_OUT);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);

            }
        }, ANIMATION_TIME_OUT);
    }

    private void scrollDownAnimation() {

        Animation scrollDown_anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        scrollDown_anim.reset();

        RelativeLayout splashRelativeLayout = (RelativeLayout) findViewById(R.id.splash_relative_layout);
        splashRelativeLayout.clearAnimation();
        splashRelativeLayout.startAnimation(scrollDown_anim);

        scrollDown_anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        scrollDown_anim.reset();

        welcomeImage.clearAnimation();
        welcomeImage.startAnimation(scrollDown_anim);
    }

    private void blinkingAnimation() {

        Animation blink_anim = AnimationUtils.loadAnimation(this, R.anim.blink);
        blink_anim.reset();

        welcomeImage.clearAnimation();
        welcomeImage.startAnimation(blink_anim);
    }

    private void scrollOutAnimation() {

        Animation scrollOut_anim = AnimationUtils.loadAnimation(this, R.anim.translate2);
        scrollOut_anim.reset();

        welcomeImage.clearAnimation();
        welcomeImage.startAnimation(scrollOut_anim);

    }
}


