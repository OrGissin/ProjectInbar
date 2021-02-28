package com.example.projectfirstdraft;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Platform
{
    private int x;
    private int y;
    private Bitmap image;
    private int screenWidth;
    private int screenHeight;
    private Rect rect;

    public Platform(Bitmap bitmap2, int screenHeight, int screenWidth)
    {
        image = Bitmap.createScaledBitmap(bitmap2,screenWidth,50,false);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        x = 0;
        y = 1400;
        rect = new Rect(x,y,x+image.getWidth(),y+image.getHeight());
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
    public int getY(){return y;}
    public void setY(int n){y = n;}
    public Rect getRect() {return rect;}
}