package com.redbuffalo.ideas;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){
        image=res;
        dx =GamePanel.MOVESPEED;
    }
    public void update()
    {
        x=x+dx;
        if(x<-GamePanel.WIDTH) {
            x = 0;
        }
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
        canvas.drawBitmap(image, x+GamePanel.WIDTH,y, null);
    }
}
