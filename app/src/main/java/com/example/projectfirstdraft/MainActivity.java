package com.example.projectfirstdraft;

import android.content.Context;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frm;
    private CharView charView;
    private ImageView pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frm = findViewById(R.id.frmMove);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean res = charView.changeState();
                if (res)
                    v.setBackgroundResource(R.drawable.pause);
                else
                    v.setBackgroundResource(R.drawable.play);
            }
        });
    }
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int w = frm.getWidth();
            int h = frm.getHeight();
            charView= new CharView(this, w, h);
            frm.addView((View) charView);
        }
    }
    public void jump(View view){
        charView.jump();}
}