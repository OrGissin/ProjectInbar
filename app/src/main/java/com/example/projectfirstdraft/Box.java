package com.example.projectfirstdraft;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Box {
    private int x;
    private int y;
    private Bitmap image;
    private int screenWidth;
    private int screenHeight;
    private Rect rect;

    public Box(Bitmap bitmap2, int screenHeight, int screenWidth)
    {
        image = Bitmap.createScaledBitmap(bitmap2,200,200,false);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        x = screenWidth/3;
        y = 700;
        rect = new Rect(x,y,x+image.getWidth(),y+image.getHeight());
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
    public int getX(){return x;}
    public void setX(int n){x = n;}
    public Rect getRect() {return rect;}
}
