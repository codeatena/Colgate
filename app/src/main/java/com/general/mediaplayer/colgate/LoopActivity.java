package com.general.mediaplayer.colgate;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoopActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.button1)
    ImageButton leftbottomBtn;

    @BindView(R.id.button2)
    ImageButton rightbottomBtn;

    @BindView(R.id.loop_imageView)
    ImageView loopImageView;

    int loopIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

        ButterKnife.bind(this);

        leftbottomBtn.setOnClickListener(this);
        rightbottomBtn.setOnClickListener(this);

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {

                //Perform background work here
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        loopIndex ++;
                        if (loopIndex > 3) loopIndex = 1;

                        ((BitmapDrawable)loopImageView.getDrawable()).getBitmap().recycle();
                        String mDrawableName = String.format(Locale.US , "digitalpanel_%d" ,loopIndex);
                        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        loopImageView.setImageResource(resID);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(doAsynchronousTask, 20000, 20000);

    }

    private long mLastClickTIme = 0;

    @Override
    public void onClick(View v) {

        if (SystemClock.elapsedRealtime() - mLastClickTIme < 500)
        {
            // show CSR app
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.general.mediaplayer.csr");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }

            return;
        }

        mLastClickTIme = SystemClock.elapsedRealtime();

    }
}
