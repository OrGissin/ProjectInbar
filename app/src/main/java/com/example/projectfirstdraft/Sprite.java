package com.example.projectfirstdraft;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Sprite
{
    private static final int INTERSECTION_MARGIN = 20;
    private int x, dx;
    private int y, dy;
    private Bitmap image;
    private Bitmap currentImage;
    private int screenWidth;
    private int screenHeight;
    private Rect rect;
    private boolean isInTheAir;
    private String whereWall;
    private boolean doubleJumpAvailable;
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
        isInTheAir = true;
        doubleJumpAvailable = true;
        onBox = false;
        whereWall = "left";
        rect = new Rect(x,y,x+currentImage.getWidth(),y+currentImage.getHeight());
    }

    public Bitmap getCurrentImage() {return currentImage;}
    public void setCurrentImage(Bitmap bitmap)
    {currentImage = Bitmap.createScaledBitmap(bitmap,150,200,false);}
    public void draw(Canvas canvas) {canvas.drawBitmap(currentImage,x,y,null);}
    public void setX(int n) {x = n;}
    public int getX() {return x;}
    public int getDy() {return dy;}
    public void setInTheAir(boolean b) {
        isInTheAir =b;}
    public void setOnBox(boolean onBox) {this.onBox = onBox;}
    public void move(Box box1, Box box2) {
        if (!isInTheAir)
        {
            doubleJumpAvailable = true;
            if (!onBox)
            {
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
        }
        x += dx;
        if (isInTheAir)
        {
            if (dy < 42)
                dy += 3;
        }
        else
            dy = 0;
        y += dy;

        if (this.intersects(box1.getRect()) && !onBox)
        {
            boxChangeDirection(box1);
        }
        if (this.intersects(box2.getRect()) && !onBox)
        {
            boxChangeDirection(box2);
        }

        if(this.rect.right > screenWidth)
        {
            whereWall = "right";
            if (isInTheAir)
                dx = 0;
            //currentImage = rotateBitmap(-90);
        }
        if(y> screenHeight && dy>0)
            y=0;
        if(x <= 0)
        {
            whereWall = "left";
            if (isInTheAir)
                dx = 0;
            //currentImage = rotateBitmap(90)
        }
        if(y<0 && dy<0)
            y = screenHeight;
        rect.set(x, y, x+currentImage.getWidth(), y+currentImage.getHeight() );
    }

    private boolean intersects(Rect rect) {
        if (this.rect.right < rect.left){
            return false;
        }
        if (this.rect.left > rect.right){
            return false;
        }
        if (this.rect.top > rect.bottom){
            return false;
        }
        if (this.rect.bottom < rect.top){
            return false;
        }
        return true;
    }

    private void boxChangeDirection(Box box) {
        if (Math.abs(this.rect.top - box.getRect().bottom) < INTERSECTION_MARGIN) {
            y = rect.bottom;
            dy = 6;
        } else if (this.rect.left < box.getRect().left) {
            //x += dx * -1;
            dx = 0;
            whereWall = "right";
        } else if (this.rect.right > box.getRect().right) {
            //x += dx * -1;
            dx = 0;
            whereWall = "left";
        }
    }

    public Bitmap rotateBitmap(float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }
    public void jump()
    {
        if (!doubleJumpAvailable)
            return;
        dy = -40;
        if (isInTheAir)
            doubleJumpAvailable = false;
        isInTheAir = true;
        if (isOnWall(dx))
        {
            switch (whereWall)
            {
                case "right":
                    dx = -30;
                    break;
                case "left":
                    dx = 30;
                    break;
            }
            doubleJumpAvailable = true;
        }
    }


    private boolean isOnWall(int dx) {
        return dx == 0;
    }

    public Rect getRect(){return rect;}
}