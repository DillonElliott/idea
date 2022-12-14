package com.redbuffalo.ideas;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private boolean playedOnce;
    private long delay;

    public void setFrames(Bitmap[] frames)
    {
        this.frames=frames;
        currentFrame =0;
        startTime = System.nanoTime();

    }
    public void setDelay(long d) {delay = d;}
    public void setFrame(int frame){ currentFrame=frame;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed> delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame >= frames.length)
        {
            currentFrame=0;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getFrame() {return currentFrame;}
    public boolean isPlayedOnce() {return playedOnce;}
}
