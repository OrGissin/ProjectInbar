    package com.example.projectfirstdraft;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

import static android.graphics.Rect.intersects;

public class CharView extends SurfaceView implements Runnable
{
    int width;
    int height;
    private Paint bgPaint;
    private Background bg1, bg2;
    private Sprite sprite;
    private Platform platform;
    private Box box, box2;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private int interval = 50;
    boolean isRunning;
    public CharView(Context context, int width, int height)
    {
        super(context);
        this.width = width;
        this.height = height;
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.first_char);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.platform);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.scbg);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.box);
        sprite = new Sprite(bitmap,width,height);
        bg1 = new Background(bitmap3,width, height);
        bg2 = new Background(bitmap3,width, height);
        box = new Box(bitmap4, width, height);
        box2 = new Box(bitmap4, width, height);
        box2.setX(box.getX()*2);
        isRunning = true;
        platform = new Platform(bitmap2,width,height);
        thread = new Thread(this);
        thread.start();
        holder = getHolder();
    }
    public void jump(){
        sprite.jump();}
    public void drawSurface()
    {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            bg1.draw(canvas);
            bg2.draw(canvas);
            box.draw(canvas);
            box2.draw(canvas);
            platform.draw(canvas);
            sprite.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    @Override
    public void run()
    {
        while (isRunning)
        {
            drawSurface();
            if (checkTouch(sprite.getRect(),platform.getRect(),sprite.getDy()))
                sprite.setAirborne(false);
            else
                sprite.setAirborne(true);
            sprite.move(box, box2);
            bg1.update();
            bg2.update();
            synchronized (this)
            {
                try {
                    wait(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean changeState()
    {
        isRunning= !isRunning;
        if (isRunning)
        {
            thread = new Thread(this); thread.start();
        }
        return isRunning;
    }
    public boolean checkTouch(Rect rect1, Rect rect2, int dy)
    {return (intersects(rect1,rect2) && rect1.bottom < rect2.bottom && dy >= 0);}
}