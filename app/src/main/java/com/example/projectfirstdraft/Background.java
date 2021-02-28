package com.example.projectfirstdraft;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private int bgX, bgY, speedY;
    private Bitmap image;
    private int screenWidth;
    private int screenHeight;

    public Background(Bitmap bitmap3, int screenWidth, int screenHeight){
        image = Bitmap.createScaledBitmap(bitmap3,screenWidth,screenHeight,false);
        bgX = 0;
        bgY = 0;
        speedY = 0;
    }

    public void update() {
        bgY += speedY;

        if (bgY <= -1920){
            bgY += 3840;
        }
    }

    public int getBgX() {
        return bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,bgX,bgY,null);
    }
}
