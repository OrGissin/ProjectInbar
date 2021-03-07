package com.example.projectfirstdraft;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import androidx.core.app.NotificationCompatSideChannelService;

import static android.graphics.Rect.intersects;

public class Sprite
{
    private int x, dx;
    private int y, dy;
    private Bitmap image;
    private Bitmap currentImage;
    private int screenWidth;
    private int screenHeight;
    private Rect rect;
    private boolean airborne;
    private String whereWall;
    private boolean dJump;
    private boolean onBox;
    public Sprite(Bitmap bitmap, int screenWidth, int screenHeight)
    {
        image = bitmap;
        image = Bitmap.createScaledBitmap(bitmap,150,200,false);
        currentImage = image;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        dx = 30;
        dy = 0;
        x = 0;
        y = screenHeight/2;
        airborne = true;
        dJump = true;
        onBox = false;
        whereWall = "right";
        rect = new Rect(x,y,x+currentImage.getWidth(),y+currentImage.getHeight());
    }

    public Bitmap getCurrentImage() {return currentImage;}
    public void setCurrentImage(Bitmap bitmap)
    {currentImage = Bitmap.createScaledBitmap(bitmap,150,200,false);}
    public void draw(Canvas canvas) {canvas.drawBitmap(currentImage,x,y,null);}
    public void setX(int n) {x = n;}
    public int getX() {return x;}
    public int getDy() {return dy;}
    public void setAirborne(boolean b) {airborne=b;}
    public void setOnBox(boolean onBox) {this.onBox = onBox;}
    public void move(Box box1, Box box2)
    {
        if (!airborne)
        {
            dJump = true;
            if (!onBox)
            switch (whereWall)
            {
                case "right":
                    dx = -30;
                    break;
                case "left":
                    dx = 30;
                    break;
            }
        }
        x += dx;
        if (airborne)
            dy += 3;
        else
            dy = 0;
        y += dy;

        if ((intersects(this.rect, box1.getRect()) || intersects(this.rect, box2.getRect())) && airborne && !onBox)
        {
            if (Math.abs(this.rect.top - box1.getRect().bottom) < 15) {y = box1.getRect().bottom; dy = 12;}
            else if (this.rect.left < box1.getRect().left) {x += dx*-1; dx = 0; whereWall = "right";}
            else if (this.rect.right > box1.getRect().right) {x += dx*-1; dx = 0; whereWall = "left";}
        }

        if(x > 950)
        {
            if (!airborne)
                dx *= -1;
            else
            {
                dx = 0;
                whereWall = "right";
                dJump = true;
            }
            //currentImage = rotateBitmap(-90);
        }
        if(y> screenHeight && dy>0)
            y=0;
        if(x < 0)
        {
            if (!airborne)
                dx *= -1;
            else
            {
                dx = 0;
                whereWall = "left";
                dJump = true;
            }
            //currentImage = rotateBitmap(90)
        }
        if(y<0 && dy<0)
            y = screenHeight;
        rect.set(x, y, x+currentImage.getWidth(), y+currentImage.getHeight() );
    }
    public Bitmap rotateBitmap(float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }
    public void jump()
    {
        if (!dJump && airborne)
            return;
        dy = -40;
        if (airborne)
            dJump = false;
        airborne = true;
        if (dx == 0) {
            switch (whereWall) {
                case "right":
                    dx = -30;
                    break;
                case "left":
                    dx = 30;
                    break;
            }
            dJump = true;
        }
    }
    public Rect getRect(){return rect;}
}